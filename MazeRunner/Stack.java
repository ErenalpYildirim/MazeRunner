public class Stack<T> {
    private Node top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }
    
    public class Node {
        public T value;
        private Node next;
    
        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    
        public void setNext(Node node) {
            this.next = node;
        }
    
        public Node getNext() {
            return this.next;
        }

        public T getValue() {
            return this.value;
        }
    }
 
    public void push(T value) {
        Node newNode = new Node(value);
        
        newNode.setNext(top);
        
        top = newNode;
        size++;
    }
    
    public T pop() {
        if (top == null) {
            return null;
        } else {
            T value = top.getValue();
            top = top.getNext();
            size--;
            return value;
        }
    }
    
    public T peek() {
        if (top == null) {
            return null;
        } else {
            return top.getValue();
        }
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int size() {
        return size;
    }
    
    public void clear() {
        top = null;
        size = 0;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int moveNum = 5; // last 5 moves
        sb.append("[");
        
        Node curr = top;
        
        while (curr != null&& moveNum > 0) {
            sb.append(curr.getValue());
            if (curr.getNext() != null) {
                sb.append(", ");
            }
            curr = curr.getNext();
            moveNum--;
        }
        
        sb.append("]");
        
        return sb.toString();
    }
}