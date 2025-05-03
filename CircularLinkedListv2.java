public class CircularLinkedListv2 {
    private  class Node {
        Object data;
        Node next;


        Node(Object data) {
        this.data = data;
        this.next = null;
        }
    
    }
    private Node tail;
    private int size;
    
    
    public CircularLinkedListv2() {
        tail = null;
        size = 0;
        }
   
   
    public void add(Object value) {
        Node newNode = new Node(value);
            if (tail == null) {
                tail = newNode;
                tail.next= tail;
    } 
    else {
        newNode.next= tail.next;
        tail.next= newNode;  
        tail = newNode;
    }
    }

    public Node search(Object value) {
        if (tail != null) {
        Node curr = tail.next;
        while (curr != tail && curr.data != value) {
            curr = curr.next;
    }
        if (curr.data == value) {
        return curr;
        }
    }
        return null;
}
    public void rotateClockwise() {
      if (tail == null) {
        
            return;
        }
        Node current =  tail.next;

        while (current.next != tail) {
            current = current.next;
        }
        tail = current;
    }
    public void display() {
        if (tail == null) {
            System.out.println("List is empty.");
            return;
        }
        Node current = tail.next;
        do {
            System.out.print(current.data + " ");
            current = current.next;
        } while (current != tail.next);
        System.out.println();
    }
    
   
}