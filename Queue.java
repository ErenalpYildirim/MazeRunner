/**
 * Implementation of a Queue data structure for managing agent turns
 * @param <T> Type of elements stored in the queue
 */
public class Queue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;
    
    /**
     * Node class for linked list implementation of queue
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
     * Constructor for an empty queue
     */
    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }
    
    /**
     * Adds an element to the rear of the queue
     * @param data Element to be enqueued
     */
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        
        rear = newNode;
        size++;
    }
    
    /**
     * Removes and returns the front element
     * @return The front element
     * @throws IllegalStateException if queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue from an empty queue");
        }
        
        T data = front.data;
        front = front.next;
        size--;
        
        if (front == null) {
            rear = null;
        }
        
        return data;
    }
    
    /**
     * Returns the front element without removing it
     * @return The front element
     * @throws IllegalStateException if queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty queue");
        }
        return front.data;
    }
    
    /**
     * Checks if the queue is empty
     * @return true if queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return front == null;
    }
    
    /**
     * Returns the number of elements in the queue
     * @return Size of the queue
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns a string representation of the queue
     * @return String representation of all elements
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = front;
        
        while (current != null) {
            sb.append(current.data);
            current = current.next;
            if (current != null) {
                sb.append(", ");
            }
        }
        
        sb.append("]");
        return sb.toString();
    }
}