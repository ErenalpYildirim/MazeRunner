/**
 * Implementation of a Stack data structure for storing agent movement history
 * @param <T> Type of elements stored in the stack
 */
public class Stack<T> {
    private Node<T> top;
    private int size;
    
    /**
     * Node class for linked list implementation of stack
     * @param <T> Type of data stored in the node
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * Constructor for an empty stack
     */
    public Stack() {
        top = null;
        size = 0;
    }
    
    /**
     * Pushes a new element onto the stack
     * @param data Element to be pushed
     */
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    /**
     * Removes and returns the top element
     * @return The top element
     * @throws IllegalStateException if stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }
    
    /**
     * Returns the top element without removing it
     * @return The top element
     * @throws IllegalStateException if stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty stack");
        }
        return top.data;
    }
    
    /**
     * Checks if the stack is empty
     * @return true if stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return top == null;
    }
    
    /**
     * Returns the number of elements in the stack
     * @return Size of the stack
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns a string representation of the stack
     * @param limit Maximum number of elements to include
     * @return String representation of up to 'limit' elements from the top
     */
    public String toString(int limit) {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = top;
        int count = 0;
        
        while (current != null && count < limit) {
            sb.append(current.data);
            current = current.next;
            if (current != null && count < limit - 1) {
                sb.append(", ");
            }
            count++;
        }
        
        if (count < size) {
            sb.append("...");
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Returns full string representation of the stack
     * @return String representation of all elements
     */
    @Override
    public String toString() {
        return toString(size);
    }
}