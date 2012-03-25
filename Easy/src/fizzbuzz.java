import java.io.File;
import java.util.Scanner;

/**
 * Fizz Buzz
 * http://www.codeeval.com/open_challenges/1/
 * @author dracoli
 *
 */
public class fizzbuzz {

	public static String solve(int A, int B, int total) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= total; i++) {
			if (i % A == 0 && i % B == 0) {
				sb.append("FB");
			}else if (i % A == 0) {
				sb.append("F");
			}else if (i % B == 0) {
				sb.append("B");
			}else {
				sb.append(i);
			}
			
			if (i != total) sb.append(" ");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(args[0]));
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// scan = new Scanner(System.in);
		
		while (scan.hasNext()) {
			int A = scan.nextInt();
			int B = scan.nextInt();
			int total = scan.nextInt();
			System.out.println(solve(A, B, total));
		}
	}
}
