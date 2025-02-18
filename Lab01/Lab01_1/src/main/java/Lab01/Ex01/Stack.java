package Lab01.Ex01;

import java.util.ArrayList;

public class Stack {
    private final ArrayList<Integer> stack;
    private int top;

    public Stack() {
        stack = new ArrayList<>();
        top = -1;
    }

    public void push(int value) {
        stack.add(value);
        top++;
    }

    public int pop() {
        if (stack.isEmpty()) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        top -= 1;
        return stack.remove(top + 1);
    }

    public int peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        return stack.get(top);
    }


    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
    
}