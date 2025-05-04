public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }
    
    public class Node<T> {
        public T value;
        private Node<T> next;
    
        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    
        public void setNext(Node<T> node) {
            this.next = node;
        }
    
        public Node<T> getNext() {
            return this.next;
        }

        public T getValue() {
            return this.value;
        }
    }
 
    public void push(T value) {
        Node<T> newNode = new Node<T>(value);
        if (top == null) {
            top = newNode;
            top.setNext(top); // Make it circular
        } else {
            newNode.setNext(top.getNext());
            top.setNext(newNode);
            top = newNode;
        }
        size++;
    }
    
    public T pop() {
        if (top == null) {
            return null;
        }
        T value = top.getValue();
        if (top.getNext() == top) {
            top = null; // Only one element
        } else {
            Node<T> temp = top;
            while (temp.getNext() != top) {
                temp = temp.getNext();
            }
            temp.setNext(top.getNext());
            top = temp;
        }
        size--;
        return value;
    }
    
    public T peek() {
        if (top == null) {
            return null;
        }
        return top.getValue();
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
        if (top == null) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        Node<T> curr = top;
        do {
            sb.append(curr.getValue());
            curr = curr.getNext();
            if (curr != top) {
                sb.append(", ");
            }
        } while (curr != top);
        
        sb.append("]");
        return sb.toString();
    }
}