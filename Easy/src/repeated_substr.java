import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Repeated Substr
 * @author dracoli
 *
 * Solving using both brute force O(N^2) and suffix arrays O(N) - O(N^2)
 * Since suffix arrays are used, space complexity is N since Java's substring 
 *  implementation uses pointers instead of string copies
 */
public class repeated_substr {
  
  /**
   * Solve the repeated substring problem by using suffix arrays.
   * Since problem is no overlapping, we also handled that through some additional comparisons.
   * Complexity is on average O(N), but about O(N^2) for overlapping words such as 'aaaaaaaa' 
   */
  public static String solve2(String word) {
    String lrc = lrs2(word);
    return lrc.trim().length() > 0 ? lrc : "NONE";
  }
  
  /**
   * Search for repeated words by looping through the word and do a check if we come across any duplicates.
   * If a duplicate is found, we add more characters to the word until no duplicates are found.
   * Complexity analysis:
   * About O(N^2) 
   */
  public static String solve(String word) {
    String result = "";
    
    // Loop through all words, search if there are duplicate chars
    for (int i = 0; i < word.length(); i++) {
      // Repeated word cannot start with empty stuff
      if (word.charAt(i) == ' ') continue;
      
      // If there are duplicates we check word
      if (word.substring(i+1).contains(String.valueOf(word.charAt(i)))) {
        String str = checkIndex(word, i);
        // Check if this repeated word is longer than what we have currently
        if (str.length() > result.length()) {
          result = str;
        }
      }
    }
    return result.length() <= 0 ? "NONE" : result.trim();
  }
  
  /**
   * Check for repeated words from a starting index
   * Returns the repeated word
   */
  public static String checkIndex(String word, int index) {
    String str = String.valueOf(word.charAt(index)); 
    int charCount = (word.length() - index) / 2; // The most words we can compare
    String result = str;
    for (int i = 1; i < charCount; i++) {
      str += word.charAt(index+i);
      // Check if we have repeats after the observed string by doing a string search
      if (word.substring(index + i + 1).contains(str)) {
        result = str;
      }
    }
    return result;
  }
  
  /**
   * Find the longest repeated string using suffix arrays
   * Complexity: O(N)
   */
  public static String lrs(String word) {
    SuffixArray sa = new SuffixArray(word);
    String result = "";
    for (int i = 1; i < sa.length(); i++) {
      int len = sa.lcp(i);
      if (len > result.length()) {
        result = sa.select(i).substring(0, len);
      }
    }
    return result;
  }
  
  /**
   * Find the longest repeated string with no overlap
   * Complexity: About O(N) - O(N^2) when a lot of repeats.
   */
  public static String lrs2(String word) {
    SuffixArray sa = new SuffixArray(word);
    String result = "";
    for (int i = 1; i < sa.length(); i++) {
      int len = sa.lcp(i);
      
      if (len > 1) {
        // Check if there's an overlap
        if (sa.isOverlap(i, i-1)) {
          
          // Since this overlapped, we check for more neighbours 
          len -= sa.overlapCount(i, i-1);
          len = handleOverlap(sa, i, len);
        }
      }
      
      if (len > result.length()) {
        result = sa.select(i).substring(0, len);
      }
    }
    return result;
  }
  
  /**
   * We handle overlap by looking at prev neighbour and getting the repeated prefix of those
   * We stop checking neighbours once we see that possible repeats are less than the prev repeats provided
   * This way the complexity of this is reduced.
   */
  public static int handleOverlap(SuffixArray sa, int i, int prevLen) {
    int j = i - 2;
    int len = prevLen;
    while (j >= 0) {
      int minLength = Math.min(sa.select(j).length(), sa.select(i).length());
      int maxLength = Math.max(sa.select(j).length(), sa.select(i).length());
      
      // Stop checking prev neighbor if the possible repeats are less than our prevlen
      // We can prove that possible repeats are in decreasing order.
      if (minLength <= prevLen || Math.abs(sa.index(i) - sa.index(j)) < prevLen) {
        return len;
      }
      
      int hlcp = lcp(sa.select(i), sa.select(j));
      hlcp -= sa.overlapCount(i, j);
      
      if (hlcp > len) {
        len = hlcp;
      }
      j--;
    }
    return len;
  }
  
  /**
   * Calculate the longest common substring of two strings.
   * This is different than the longest common subsequence.
   */
  public static String lcs(String a, String b) {
    
    // Remove all consecutive blocks of whitespace with single space
    a = a.replaceAll("\\s+", " ");
    b = b.replaceAll("\\s+", " ");
    
    int N2 = b.length();
    String lcs = "";
    SuffixArray sa = new SuffixArray(a + '\0' + b); // Assuming \0 does not exist in both strings
    
    // Loop through all suffixes
    for (int i = 1; i < sa.length(); i++) {
      
      // Skip if adjacent suffixes are both from second string
      if (sa.select(i).length() < N2 && sa.select(i-1).length() < N2) continue;
      
      // Skip if adjacent suffixes are both from first string
      if (sa.select(i).length() > N2+1 && sa.select(i-1).length() > N2+1) continue;
      
      int length = sa.lcp(i);
      if (length > lcs.length()) {
        lcs = sa.select(i).substring(0, length);
      }
    }
    return lcs;
  }
  
  /**
   * Get parts of the text with the query word in it.
   * Context determines how many words before and after the query you want to display.
   */
  public static ArrayList<String> KWIC(String text, String query, int context) {
    ArrayList<String> result = new ArrayList<String>();
    SuffixArray sa = new SuffixArray(text);
    int N = text.length();
    for (int i = sa.rank(query); i < N && sa.select(i).startsWith(query); i++) {
      int from = Math.max(0, sa.index(i) - context);
      int to = Math.min(N - 1, from + query.length() + context * 2);
      result.add(text.substring(from, to));
    }
    return result;
  }
  
  /**
   * A suffix array used for find longest repeated substring,
   *  longest common substring, and keyword in context search
   */
  private static class SuffixArray {
    private final String[] suffixes;
    private final int N;
    
    private SuffixArray(String word) {
      // Construct the suffix array
      N = word.length();
      suffixes = new String[N];
      for (int i = 0; i < N; i++) {
        suffixes[i] = word.substring(i);
      }
      Arrays.sort(suffixes);
    }
    
    public int index(int i) {
      return N - suffixes[i].length();
    }
    
    public int length() { return N; }
    
    public String select(int i) { return suffixes[i]; }
    
    /**
     * Do a binary search on the index with the key
     * If key does not exist, return the index it should be inserted
     */
    public int rank(String key) {
      int low = 0, high = N - 1;
      
      while (low <= high) {
        int mid = (low + high) / 2;
        int cmp = suffixes[mid].compareTo(key); 
        if (cmp < 0) high = mid - 1;
        else if (cmp > 0) low = mid + 1;
        else return mid;
      }
      
      // low will be the position this key should be in
      return low;
    }
    
    public int lcp(int i) {
      return repeated_substr.lcp(select(i), select(i-1));
    }
    
    public boolean isOverlap(int i, int j) {
      int minIndex = Math.min(index(i), index(j));
      int maxIndex = Math.max(index(i), index(j));
      int lcp = repeated_substr.lcp(select(i), select(j));
      return (minIndex + lcp) > maxIndex ? true : false;
    }
    
    /**
     * Returns amount of characters that are overlapped
     */
    public int overlapCount(int i, int j) {
      int minIndex = Math.min(index(i), index(j));
      int maxIndex = Math.max(index(i), index(j));
      int lcp = repeated_substr.lcp(select(i), select(j));
      return (minIndex + lcp) > maxIndex ? (minIndex + lcp) - maxIndex : 0;
    }
  }
  
  /**
   * Returns the length of the longest common prefix of a and b
   */
  public static int lcp(String a, String b) {
    int N = Math.min(a.length(), b.length());
    for (int i = 0; i < N; i++) {
      if (a.charAt(i) != b.charAt(i)) return i;
    }
    return N;
  }
  
  public static void main(String[] args) {
    Scanner c = null;
    try {
      c = new Scanner(new File("repeat.txt"));
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    while (c.hasNextLine()) {
      String input = c.nextLine();
      if (input.length() == 0) continue;
      System.out.println(solve2(input));
    }
  }
}
