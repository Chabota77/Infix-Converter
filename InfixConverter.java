import java.util.Stack;

public class InfixConverter {

    // Returns operator precedence
    private static int precedence(char op) {
        if (op == '^')         return 3;
        if (op == '*' || op == '/') return 2;
        if (op == '+' || op == '-') return 1;
        return 0;
    }

    private static boolean isOperand(char c) {
        return Character.isLetterOrDigit(c);
    }

    // ── Infix → Postfix ──────────────────────────────────────────
    public static String infixToPostfix(String expr) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char token : expr.toCharArray()) {
            if (token == ' ') continue;

            if (isOperand(token)) {
                output.append(token);

            } else if (token == '(') {
                stack.push(token);

            } else if (token == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // remove '('

            } else { // operator
                while (!stack.isEmpty()
                        && stack.peek() != '('
                        && precedence(stack.peek()) >= precedence(token)) {
                    output.append(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }

        return output.toString();
    }

    // ── Infix → Prefix ───────────────────────────────────────────
    public static String infixToPrefix(String expr) {
        // Step 1: reverse the expression
        String reversed = new StringBuilder(expr).reverse().toString();

        // Step 2: swap brackets
        StringBuilder swapped = new StringBuilder();
        for (char c : reversed.toCharArray()) {
            if      (c == '(') swapped.append(')');
            else if (c == ')') swapped.append('(');
            else               swapped.append(c);
        }

        // Step 3: convert to postfix
        String postfix = infixToPostfix(swapped.toString());

        // Step 4: reverse postfix → prefix
        return new StringBuilder(postfix).reverse().toString();
    }

    // ── Main ─────────────────────────────────────────────────────
    public static void main(String[] args) {
        String[] testCases = {
            "A+B*C",
            "(A+B)*(C-D)",
            "A+B*(C^D-E)^(F+G*H)-I"
        };

        System.out.printf("%-30s %-20s %-20s%n", "Infix", "Postfix", "Prefix");
        System.out.println("-".repeat(70));

        for (String expr : testCases) {
            String postfix = infixToPostfix(expr);
            String prefix  = infixToPrefix(expr);
            System.out.printf("%-30s %-20s %-20s%n", expr, postfix, prefix);
        }
    }
}
