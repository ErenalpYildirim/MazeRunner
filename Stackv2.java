public class Stackv2 {
    private Node top;
    private int size;
    
    public Stackv2() {
       top = null;
       size = 0;
    }
    public class Node {

      public Object value;
      private Node next;
    
      public Node(Object value) {
        this.value = value;
        this.next = null;
      }
    
      public void setNext(Node node) {
        this.next = node;
      }
    
      public Node getNext() {
        
         return this.next;
      }

      public Object getValue() {
        
        
         return this.value;
      }
    
    }
 
      public void push(Object value) {
       Node newNode = new Node(value);
       
       newNode.setNext(top);
       
       top = newNode;
       size++;
    }
    
      public Object pop() {
       if (top == null) {
          return null;
       } else {
          Object value = top.getValue();
          top = top.getNext();
          size--;
          return value;
       }
    }
    
    public Object peek() {
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
       
       sb.append("[");
       
       Node curr = top;
       
       
       while (curr != null) {
          sb.append(curr.getValue());
          if (curr.getNext() != null) {
             sb.append(", ");
          }
          curr = curr.getNext();
       }
       
       sb.append("]");
       
       
       
       
       return sb.toString();
    
    
    
      }

 }