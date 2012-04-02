import java.io.File;
import java.util.Scanner;
import java.util.Stack;

/**
 * Prefix Expressions
 * http://www.codeeval.com/open_challenges/7/ 
 * @author dracoli
 *
 */
public class prefix {
  
  /**
   * Solve the problem by constructing a prefix tree,
   *  then traverse through all nodes to evaluate expression
   * @param input values and operations of the prefix expression 
   */
  public static double solve(String[] input) {
    
    // Extract values and operations into a stack
    // Top of the stack should be the first element of the array
    Stack<String> vals = new Stack<String>();
    for (int i = input.length - 1; i >= 0; i--) {
      vals.push(input[i]);
    }
    
    // Build an expression tree using input
    TreeNode root = buildExpressionTree(vals);
    
    // Debug: Show the prefix of the expression tree
    // showPrefix(root); System.out.println("");
    
    return evaluateExpression(root);
  }
  
  /**
   * Build an expression tree using a prefix expression
   * The leafs are values, all other nodes are operations
   */
  public static TreeNode buildExpressionTree(Stack<String> data) {
    TreeNode node = null;
    boolean leaf = false;
    String value = data.pop();
    if (value.matches("[0-9]+")) {
      // Tree end at leaf nodes
      leaf = true;
      node = new TreeNode(leaf, '\0', Double.parseDouble(value));
    }else {
      // Add operation node and recurse child nodes
      char op = value.charAt(0);
      node = new TreeNode(leaf, op, 0.0);
      node.left = buildExpressionTree(data);
      node.right = buildExpressionTree(data);
    }
    return node;
  }
  
  /**
   * Evaluate the expression tree by traversing though it in preorder
   * @param node
   * @return
   */
  public static double evaluateExpression(TreeNode node) {
    double result = 0;
    
    // Base leaf case
    if (node.leaf) return node.value;
    
    // This node is an expression, save the current node value
    double left, right;
    char op = node.op;
    
    // Figure out value of left tree and right tree
    left = evaluateExpression(node.left);
    right = evaluateExpression(node.right);
    
    // Perform the operation on left and right values
    switch (op) {
    case '-': result = left - right; break;
    case '*': result = left * right; break;
    case '/': result = left / right; break;
    case '+': result = left + right; break;
    case '^': result = Math.pow(left, right); break;
    default: System.out.println("Unrecognized operator " + 
               op);
    }
    
    return result;
  }
  
  /**
   * Display a tree in preorder
   */
  public static void showPrefix(TreeNode node) {
    if (node != null) {
      System.out.print(node + " ");
      showPrefix(node.left);
      showPrefix(node.right); // or node = node.right and change if loop to while loop;
    }
  }
  
  private static class TreeNode {
    private final boolean leaf;
    private final char    op;
    private double        value;
    private TreeNode      left, right;
    
    private TreeNode(boolean leaf, char op, double value) {
      this.leaf = leaf;
      this.op = op;
      this.value = value;
      this.left = null;
      this.right = null;
    }
    
    public String toString() {
      return leaf ? String.valueOf((int)value) : Character.toString(op);
    }
  }
  
  public static void main(String[] args) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("prefix.txt"));
    }catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    while(scanner.hasNextLine()) {
      String[] input = scanner.nextLine().split(" ");
      System.out.println(String.format("%.0f", solve(input))); 
    }
  }
}

/**
 * References:
 * http://penguin.ewu.edu/cscd300/Topic/ExpressionTree/index.html (best source)
 * http://en.wikipedia.org/wiki/Polish_notation
 * http://www.cs.man.ac.uk/~pjj/cs212/fix.html
 */