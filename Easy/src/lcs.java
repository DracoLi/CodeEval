import java.io.File;
import java.util.Scanner;

/**
 * Longest Common Subsequence
 * http://www.codeeval.com/open_challenges/6/
 * @author dracoli
 *
 */
public class lcs {
  
  // Using the bottom up approach to solve the longest common subsequence problem
  // Complexity O(M*N), using dynamic programming.
  static String solve(String[] input) {
    
    String word1 = input[0];
    String word2 = input[1];
    
    int[][] L = lcsdyn(word1, word2);
    
    return printLongestSubsequence(L, word1, word2);
  }
  
  /**
   * Using the recursive approach to solve the LCS problem
   * Complexity O(2^N) where N is the longest string length
   */
  static String solve2(String[] input) {
    String word1 = input[0];
    String word2 = input[1];
    
    return lcsrec(word1, word2, 0, 0);
  }
  
  /**
   * Starting from the front of every char, recursively check for common subsequence of remains.
   * Complexity is O(2^N) as every recursive call can potentially create 2 more recursive calls if the current character does not match 
   */
  static String lcsrec(String a, String b, int i, int j) {
    int alen=a.length();
    int blen=b.length();
    if (alen == i || blen == j) {
      return "";
    }
    else if (a.charAt(i) == b.charAt(j)) {
      return a.charAt(i) + lcsrec(a, b, i+1, j+1);
    }
    else {
      return maxString(lcsrec(a, b, i, j+1), lcsrec(a, b, i+1, j));
    }
  }
  
  static String maxString(String a, String b) {
    return a.length() > b.length() ? a : b;
  }
  
  /**
   * Bottom up approach using dynamic programming
   */
  static int[][] lcsdyn(String a, String b) {
    int[][] L = new int[a.length()+1][b.length()+1];
    int m = a.length() - 1;
    int n = b.length() - 1;
    
    for (int i = m; i >= 0; i--) {
      for (int j = n; j >= 0; j--) {
        if (i == m || j == n) 
          L[i][j] = 0;
        else if (a.charAt(i) == b.charAt(j))
          L[i][j] = L[i+1][j+1] + 1;
        else
          L[i][j] = Math.max(L[i+1][j], L[i][j+1]);
      }
    }
    return L;
  }
  
  /**
   * Print the longest subsequence using the array created through the
   *  lcs solution using dynamic programming
   */
  static String printLongestSubsequence(int[][] L, String a, String b) {
    StringBuilder sb = new StringBuilder();
    int i = 0, j = 0;
    
    // We loop through the strings until one reaches the end
    while (i < (L.length - 1) && j < (L[0].length - 1)) {
      
      // If the current chars match, we add the char to our final string
      if (a.charAt(i) == b.charAt(j)) {
        sb.append(a.charAt(i));
        i++; j++;
      // If the current chars don't match we increase the one with the larger size value
      // http://www.ics.uci.edu/~eppstein/161/960229.html
      }else if (L[i+1][j] > L[i][j+1]) {
        i++;
      }else {
        j++;
      }
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    Scanner in = null;
    try {
      in = new Scanner(new File("lcs.txt"));
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    while(in.hasNextLine()) {
      String input = in.nextLine();
      if (input.trim().length() == 0) continue;
      System.out.println(solve2(input.split(";")));
    }
  }
  
}

/**
 * References:
 * http://www.ics.uci.edu/~eppstein/161/960229.html (Best one)
 * https://github.com/rxaviers/codeeval/blob/master/open_challenges/hard/longest_common_subsequence.rb (Done with combinations)
 * http://en.wikipedia.org/wiki/Longest_common_subsequence_problem
 * http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Longest_common_subsequence
 * http://knowledgeexplosion.info/2010/12/longest-common-subsequence-java-implementation/
 * https://gist.github.com/1587335
 */