
public class sum_prime {

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
  
  public static void main(String[] args) {
    
    long sum = 0; // add basic prime
    int count = 0;
    int current = 2;
    
    // Skip even numbers for speeed!
    while (count < 1000) {
      if (isPrime(current)) {
        sum += current;
        count++;
      }
      current++;
    }
    
    System.out.println(sum);
  }
}