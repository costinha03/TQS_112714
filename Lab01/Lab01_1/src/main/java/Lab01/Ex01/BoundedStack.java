package Lab01.Ex01;

public class BoundedStack extends Stack {
    private final int capacity;

    public BoundedStack(int capacity) {
        super();
        this.capacity = capacity;
    }

    @Override
    public void push(int value) {
        if (size() == capacity) {
            throw new IndexOutOfBoundsException("Stack is full");
        }
        super.push(value);
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return size() == capacity;
    }

}
