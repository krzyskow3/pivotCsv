package pl.pkpik.bilkom.pivotcsv.functions;

import java.util.Stack;

public class FResult {

    private final Stack<String> stack = new Stack<>();

    public String pop() {
        if (stack.isEmpty()) {
//            System.out.println("=> empty stack");
            return null;
        } else {
            String value = stack.pop();
//            System.out.println("=> pop: " + value);
            return value;
        }
    }

    public void push(String value) {
//        System.out.println("=> push: " + value);
        stack.push(value);
    }

}
