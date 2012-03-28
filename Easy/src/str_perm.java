import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class str_perm {
  
  public static String makeString(String word, String str, int index) {
    String first = "";
    if (index > 0) {
      first = word.substring(0, index);
    }
    String rest = word.substring(index);
    
    // Make the string. Use strinbuilder for efficiency
    StringBuilder sb = new StringBuilder();
    sb.append(first);
    sb.append(str);
    sb.append(rest);
    
    return sb.toString();
  }
  
  public static ArrayList<String> getPerms(String str) {
    if (str == null) return null;
    
    // base case
    ArrayList<String> results = new ArrayList<String>();
    if (str.length() == 0) {
      results.add("");
      return results;
    }
    
    // Loop through every subset, add one string to each perm
    String first = str.substring(0, 1);
    String rest = str.substring(1);
    ArrayList<String> prevPerms = getPerms(rest);
    for (String word : prevPerms) {
      for (int i = 0; i <= rest.length(); i++) {
        String oneResult = makeString(word, first, i);
        results.add(oneResult);
      }
    }
    
    Collections.sort(results);
    return results;
  }
  
  public static String solve(String str) {
    ArrayList<String> perms = getPerms(str);
    
    // format perms
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < perms.size(); i++) {
      sb.append(perms.get(i));
      if (i != perms.size() - 1) sb.append(",");
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("str_perm.txt")); 
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    while (scanner.hasNextLine()) {
      String input = scanner.nextLine();
      if (input.length() == 0) continue;
      System.out.println(solve(input));
    }
  }
}
