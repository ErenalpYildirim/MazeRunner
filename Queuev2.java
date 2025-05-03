public class Queuev2 {
    private Node front;
    private Node rear;
    private int size;
    
    
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
    
    public Queuev2(int capacity) {
    
    front = null;
    rear = null;
    size = 0;
    }
    
    public void enqueue(Object x) {

        Node newNode = new Node(x);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.setNext(newNode);
        }
        rear = newNode;
        size++; 
    
    }
    
    public Object dequeue() {
    if (isEmpty()) {
    System.out.println("Queue is empty");
    return -1;
  }
  Node data = front;
    front = front.getNext();
    size--;
    if(front == null) {
        rear = null;}

      return data.getValue();
    
    }
    
    
    public Object peek() {
    if (isEmpty()) {
    System.out.println("Queue is empty");
    return -1;
    }
    return front.getValue();
    }
    
    public boolean isEmpty() {
    return front == null;
    }
    public String toString(){
      StringBuilder sb = new StringBuilder();
      Node current = front;
      while(current != null){
        sb.append(current.getValue()).append(" ");
        current = current.getNext();
      }
      return sb.toString();
    }
}