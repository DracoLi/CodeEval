import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class prime_less {

  // Dynamic programming
  public static HashMap<Integer, ArrayList<Integer>> primeMap = new HashMap<Integer, ArrayList<Integer>>();
  
  public static boolean isPrime(int n) {
    if (n < 2) return false;
    if (n == 2 || n == 3) return true; // Base primes
    if (n % 2 == 0 || n % 3 == 0) return false; // Most common
    for (int i = 5; i <= Math.sqrt(n); i += 2) { // Skip even numbers
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
  
  // Prime out all primes less than n
  public static String solve(int n) {
    ArrayList<Integer> results = new ArrayList<Integer>();
    
    // Get max prime number from cache
    int maxKey = -1;
    for (Entry<Integer, ArrayList<Integer>> oneList : primeMap.entrySet()) {
      int key = oneList.getKey();
      if (key > maxKey) maxKey = key;
    }
    
    // Populate results with cached values
    if (maxKey > 0) {
      results.addAll(primeMap.get(maxKey));
    }
    
    // Start from our last populated prime number for from 2 none
    int start = 2;
    if (results.size() > 0) {
      start = results.get(results.size() - 1) + 1;
    }
    for (int i = start; i < n; i++) {
      if (isPrime(i)) {
        results.add(i);
      }
    }
    primeMap.put(n, results);
    return printArray(results);
  }
  
  public static String printArray(ArrayList<Integer> input) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < input.size(); i++) {
      int a = input.get(i);
      sb.append(a);
      if (i != input.size() - 1) {
        sb.append(","); 
      }
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("primeless.txt")); 
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    while (scanner.hasNext()) {
      int n = scanner.nextInt();
      System.out.println(solve(n));
    }
  }
}
