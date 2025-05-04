public class CircularLinkedList<T> {
    private Node<T> head;
    private int size;
    
    public CircularLinkedList() {
        head = null;
        size = 0;
    }
    
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            head.next = head;
        } else {
            newNode.next = head.next;
            head.next = newNode;
            head = newNode;
        }
        size++;
    }
    
    public void rotateClockwise() {
        if (head == null || size <= 1) {
            return;
        }
        head = head.next;
    }
    
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }
    
    private class Node<T> {
        T value;
        Node<T> next;
        
        Node(T value) {
            this.value = value;
            this.next = null;
        }
    }
    
    public void display() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        Node<T> current = head;
        do {
            System.out.print(current.value + " ");
            current = current.next;
        } while (current != head);
        System.out.println();
    }
}