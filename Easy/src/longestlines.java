import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Longest Lines
 * http://www.codeeval.com/open_challenges/2/
 * @author dracoli
 *
 */
public class longestlines {
  
  /*
   * The actual merge function. Uses only an aux array to save space
   */
  public static void merge(ArrayList<String> data, String[] aux, int low, int mid, int high) {
    
    // copy data to be sorted to aux
    for (int i = low; i <= high; i++) {
      aux[i] = data.get(i);
    }
    
    int i = low, j = mid + 1;
    for (int k = low; k <= high; k++) {
      if (i > mid) data.set(k, aux[j++]);
      else if (j > high) data.set(k, aux[i++]);
      else if (aux[i].length() < aux[j].length()) data.set(k, aux[i++]);
      else data.set(k, aux[j++]);
    }
  }
  
  /*
   * In place merge sort
   * Time complexity = nlogn
   * Space complexity = n
   */
  public static void mergeSort(ArrayList<String> data, String[] aux, int low, int high) {
    if (low >= high) return;
    
    int mid = (low + high) / 2;
    mergeSort(data, aux, low, mid);
    mergeSort(data, aux, mid + 1, high);
    merge(data, aux, low, mid, high);
  }

  public static String solve(ArrayList<String> strs, int amount) {
    String[] aux = new String[strs.size()];
    ArrayList<String> sorted = (ArrayList<String>) strs.clone();
    mergeSort(sorted, aux, 0, sorted.size() - 1);
    
    // Convert answer to string
    StringBuilder sb = new StringBuilder();
    int index = sorted.size() - 1;
    while (amount-- > 0) {
      sb.append(sorted.get(index));
      if (index > 0) sb.append("\n");
      index--;
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("longestlines.txt")); 
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    // Get inputs
    int amount = scanner.nextInt();
    ArrayList<String> inputs = new ArrayList<String>();
    while (scanner.hasNextLine()) {
      String str = scanner.nextLine();
      if (str.trim().length() > 0) {
        inputs.add(str);
      }
    }
    
    System.out.println(solve(inputs, amount));
  }
}
