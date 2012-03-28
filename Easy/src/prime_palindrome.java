/**
 * Prime Palindrome
 * @author dracoli
 * 
 */
public class prime_palindrome {

	/**
	 * Simple method to check if number is prime
	 * Is optimized to skip all even numbers and check until sqrt(n)
	 */
	public static boolean isPrime(int n) {
		if (n < 2) return false;
		if (n == 2 || n == 3) return true; // Base primes
		if (n % 2 == 0 || n % 3 == 0) return false; // Most common
		for (int i = 5; i <= Math.sqrt(n); i += 2) { // Skip even numbers
			if (n % i == 0) {
				return false;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		// Generate palindrome numbers from 100 to 999
		for (int i = 9; i > 0; i--) {
			for (int j = 9; j >= 0; j--) {
				int num = i*100 + j*10 + i;
				if (isPrime(num)) {
					System.out.println(num);
					return;
				}
			}
		}
		
		// Generate palindromes under 100
		for (int i = 9; i <= 0; i--) {
			int num = i*10 + i;
			if (isPrime(num)) {
				System.out.println(num);
				return;
			}
		}
	}
}
