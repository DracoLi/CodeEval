import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class cycle_detection {

  /**
   * Once we know the key and index of the first cycle
   *  we loop back to store the loop and display it 
   */
  public static String getCycle(int[] data, int key, int index) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = index - 1; i >= 0; i--) {
      int n = data[i];
      if (n != key) {
        result.add(n);
      }else {
        result.add(n);
        return listToString(result);
      }
    }
    return null;
  }
  
  /**
   * Turn our cycle into array of strings
   */
  public static String listToString(ArrayList<Integer> data) {
    StringBuilder sb = new StringBuilder();
    for (int i = data.size() - 1; i >= 0 ; i--) {
      sb.append(data.get(i));
      if (i != 0) sb.append(" ");
    }
    return sb.toString();
  }
  
  public static String solve(int[] data) {
    HashSet<Integer> history = new HashSet<Integer>();
    for (int i = 0; i < data.length; i++) {
      int n = data[i];
      if (!history.contains(n)) {
        history.add(n);
      }else {
        return getCycle(data, n, i);
      }
    }
    return null;
  }
  
  /**
   * array of strings into array of integers!
   */
  public static int[] stringToIntArray(String[] data) {
    int[] results = new int[data.length];
    for (int i = 0; i < data.length;i++) {
      results[i] = Integer.parseInt(data[i]);
    }
    return results;
  }
  
  public static void main(String[] args) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("cycle.txt"));
    }catch (Exception e) {
      System.err.println(e.getMessage());
    }
   
    while (scanner.hasNextLine()) {
      String[] temp = scanner.nextLine().split(" ");
      int[] inputs = stringToIntArray(temp);
      System.out.println(solve(inputs));
    }
  }
}
