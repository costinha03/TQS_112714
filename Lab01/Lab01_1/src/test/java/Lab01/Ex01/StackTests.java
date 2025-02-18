package Lab01.Ex01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTests {
    private Stack stack;
    private BoundedStack boundedStack;
    @BeforeEach
    void setUp() {
        stack = new Stack();
    }

    @DisplayName("Test push method")
    @Test
    void testPush() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size());
    }

    @DisplayName("Test pop method")
    @Test
    void testPop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertEquals(0, stack.size());
    }

    @DisplayName("Test peek method")
    @Test
    void testPeek() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.peek());
        assertEquals(3, stack.size());
    }

    @DisplayName("Test isEmpty method")
    @Test
    void testIsEmpty() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @DisplayName("Test size method")
    @Test
    void testSize() {
        assertEquals(0, stack.size());
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertFalse(stack.isEmpty());
        assertEquals(3, stack.size());
    }

    @DisplayName("Test pop method on empty stack")
    @Test
    void testPopOnEmptyStack() {
        assertThrows(IndexOutOfBoundsException.class, stack::pop);
    }

    @DisplayName("Test peek method on empty stack")
    @Test
    void testPeekOnEmptyStack() {
        assertThrows(IndexOutOfBoundsException.class, stack::peek);
    }

    @DisplayName("Test BoundedStack methods")
    @Test
    void testBoundedStack() {
        boundedStack = new BoundedStack(3);
        assertEquals(3, boundedStack.getCapacity());
        assertFalse(boundedStack.isFull());
        boundedStack.push(1);
        boundedStack.push(2);
        boundedStack.push(3);
        assertEquals(3, boundedStack.size());
        assertTrue(boundedStack.isFull());
        assertThrows(IndexOutOfBoundsException.class, () -> boundedStack.push(4));
        assertEquals(3, boundedStack.pop());
        assertEquals(2, boundedStack.pop());
        assertEquals(1, boundedStack.pop());
        assertEquals(0, boundedStack.size());
    }



}