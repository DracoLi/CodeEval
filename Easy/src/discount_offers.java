import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class discount_offers {

  /**
   * Find GCD
   */
  public static int getGCD(int n, int m) {
    if (m == 0) return -1;
    
    if (m <= n && n % m == 0) {
      return m;
    }else if (n < m) {
      return getGCD(m, n);
    }else {
      return getGCD(m, n % m);
    } 
  }
  
  // Count the number of letter in a string
  public static int getLetters(String str) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      if (isLetter(str.charAt(i))) {
        count++;
      }
    }
    return count;
  }
  
  // Check if a char is a letter
  public static boolean isLetter(char chr) {
    if ((chr >= 'a' && chr <= 'z') || 
        (chr >= 'A' && chr <= 'Z')) {
      return true;
    }
    return false;
  }
  
  /*
   * Check if a chr is a vowel
   */
  public static boolean isVowel(char chr) {
    chr = Character.toLowerCase(chr);
    for (char c : VOWELS) {
      if (chr == c) {
        return true;
      }
    }
    return false;
  }
  
  // Caching for each word
  public static HashMap<String, Integer> customerVowels = new HashMap<String, Integer>();
  public static HashMap<String, Integer> commonGCD = new HashMap<String, Integer>();
  public static HashMap<String, Integer> customerConsonants = new HashMap<String, Integer>();
  public static HashMap<String, Integer> customerLetters = new HashMap<String, Integer>();
  public static HashMap<String, Integer> productLetters = new HashMap<String, Integer>();
  
  public static char[] VOWELS = {'a', 'e', 'i', 'o', 'u', 'y'};
  
  // Get the SS score of a pair - uses caching
  public static float getSS(String customer, String product) {
    float result = 0;
    
    int customersChars = 0;
    int productsChars = 0;
    if (customerLetters.containsKey(customer)) {
      customersChars = customerLetters.get(customer);
    }else {
      customersChars = getLetters(customer);
      customerLetters.put(customer, customersChars);
    }
    if (productLetters.containsKey(product)) {
      productsChars = productLetters.get(product);
    }else {
      productsChars = getLetters(product);
      productLetters.put(product, productsChars);
    }
    
    // Rule #2 - turbo
    boolean hasTurbo = false;
    int gcd = getGCD(customersChars, productsChars);
    if (gcd > 1) {
      // commonGCD.put(customer + product, gcd);
      hasTurbo = true;
    }
    
    // Rule #1 - even
    if (productsChars % 2 == 0) {
      if (customerVowels.containsKey(customer)) {
        result = customerVowels.get(customer);
      }else {
        result = getVowels(customer);
        customerVowels.put(customer, (int)result);
      }
      result = (float) (result * 1.5);
    }else {
      // Rule #2
      if (customerConsonants.containsKey(customer)) {
        result = customerConsonants.get(customer);
      }else {
        result = getConsonants(customer);
        customerConsonants.put(customer, (int)result);
      }
    }
    
    if (hasTurbo) result = (float) (result * 1.5);
    return result;
  }
  
  /**
   * count all vowels of a string
   */
  public static int getVowels(String str) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      char chr = str.charAt(i);
      if (isLetter(chr) && isVowel(chr)) {
        count++;
      } 
    }
    return count;
  }
  
  /**
   * count all consonants of a string
   */
  public static int getConsonants(String str) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      char chr = str.charAt(i);
      if (isLetter(chr) && !isVowel(chr)) {
        count++;
      } 
    }
    return count;
  }
  
  /*
   * Complexity n!
   */
  public static float solve(String[] customers, String[] products) {
    
    // Base Case
    if (customers.length == 0 || products.length == 0) {
      return 0; 
    }
    
    // Loop through all combinations of current customers and products.
    // Then recurse to figure out total SS for each combination
    float maxSS = 0;
    for (int i = 0; i < customers.length; i++) {
      String[] newC = removeIndex(customers, i);
      for (int j = 0; j < products.length; j++) {
        float thisSS = getSS(customers[i], products[j]);
        String[] newP = removeIndex(products, j);
        thisSS += solve(newC, newP); // recurse the rest of the products and customers
        if (thisSS > maxSS) {
          maxSS = thisSS;
        }
      }
    }
    //int[] products = new int[customers.length][products.length];
    return maxSS;
  }
  
  /**
   * Return an array with an index removed
   * Complexity O(n), Space O(n)
   */
  public static String[] removeIndex(String[] data, int index) {
    if (data.length == 0) return data;
    if (data.length == 1 && index == 0) return new String[0];
    
    String[] results = new String[data.length - 1];
    int resultIndex = 0;
    for (int i = 0; i < data.length; i++) {
      if (i != index) {
        results[resultIndex] = data[i];
        resultIndex++; 
      }
    }
    return results;
  }
  
  public static void main(String[] args) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("discount.txt"));
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    while (scanner.hasNextLine()) {
      String[] inputs = scanner.nextLine().split(";");
      String[] customers = inputs[0].split(",");
      String[] products = inputs[1].split(",");
      System.out.println(solve(customers, products));
    }
  }
}
