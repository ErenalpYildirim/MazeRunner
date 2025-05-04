public class CircularLinkedList<T> {
    private  class Node<T> {
        T value;
        Node<T> next;


        Node(T value) {
        this.value = value;
        this.next = null;
        }
    
    }
    private Node<T> head;
    private int size;
    
    
    public CircularLinkedList() {
        head = null;
        size = 0;
        }
   
   
    public void add(T value) {  //adding new node to list
        Node<T> newNode = new Node<>(value);
            if (head == null) {
                head = newNode;
                head.next= head;
    } 
    else {
        newNode.next= head.next;
        head.next= newNode;  
        head = newNode;
    }
        size++;
    }

    public Node<T> search(T value) {  // in built node class
        if (head != null) {
        Node<T> curr = head.next;
        while (curr != head && curr.value != value) {
            curr = curr.next;
    }
        if (curr.value == value) {
        return curr;
        }
    }
        return null;
}
    public void rotateClockwise() {  // for list rotating
      if (head == null) {
        
            return;
        }
        Node<T> current =  head.next;

        while (current.next != head) {
            current = current.next;
        }
        head = current;
    }
    public void display() // for displaying list
    {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        Node<T> current = head.next;
        do {
            System.out.print(current.value + " ");
            current = current.next;
        } while (current != head.next);
        System.out.println();
    }
    
    public T getData(int index) {
        return get(index);
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
}