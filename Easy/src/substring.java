import java.io.File;
import java.util.Scanner;

/**
 * String Searching
 * http://www.codeeval.com/open_challenges/28/ 
 * @author dracoli
 *
 * My solution traverses the source string searching for a character that
 *  matches the first character of the target string.
 *  If there's a match then we search the source string for the target string at that
 *  specific location.
 * The worst cast complexity of this solution is less than O(M*N)
 */
public class substring {

  public static String solve(String source, String target) {  
    boolean result = searchString(source, target);
    return result ? "true" : "false";
  }
  
  public static boolean searchString(String source, String target) {
    // Only need to search up till source.length - target.length
    // If target.length > source.length, then no loop will happen.
    int lastIndex = source.length() - target.length();
    for (int i = 0; i < lastIndex + 1; i++) {
      if (source.charAt(i) == target.charAt(0)) {
        // Check if target equals source at this index
        if (isWord(source, target, i)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static boolean isWord(String source, String target, int index) {
    int i = index, j = 0;
    
    // Loop through target, see if source matches
    while (j < target.length()) {
      
      // Return false if there's no more source to match
      if (i >= source.length()) return false;
      
      char sourceChr = source.charAt(i);
      char tarChr = target.charAt(j);
      
      // Handle wild cards
      if (tarChr == '*') {
        // Check if target's next char matches
        if (target.charAt(j+1) == source.charAt(i)) {
          i++; 
          j += 2; // advance target by wildcard and next word
        }else {
          // if no just skip source's character
          i++;
        }
      }else if (sourceChr == tarChr) {
        i++;j++;
      }else {
        return false;
      }
    }
    
    // All target characters matched
    return true;
  }
  
  public static void main(String[] args) {
    Scanner c = null;
    try {
      c = new Scanner(new File("search.txt"));
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    while (c.hasNextLine()) {
      String[] inputs = c.nextLine().split(",");
      System.out.println(solve(inputs[0], inputs[1]));
    }
  }
}