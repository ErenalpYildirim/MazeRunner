public class Queue<Q> {
    private Node<Q> front;
    private Node<Q> rear;
    private int size;
    
    
    private static class Node<Q> {

        Q value;
        private Node<Q> next;
      
        public Node(Q value) {
          this.value = value;
          this.next = null;
        }
      
        public void setNext(Node<Q> node) {
          this.next = node;
        }
      
        public Node<Q> getNext() {
          
           return this.next;
        }
  
        public Q getValue() {
          
          
           return this.value;
        }
      
      }
    
    public Queue() {
    
    front = null;
    rear = null;
    size = 0;
    }
    
    public void enqueue(Q x) {

        Node<Q> newNode = new Node<>(x);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.setNext(newNode);
        }
        rear = newNode;
        size++; 
    
    }
    
    public Q dequeue() {
    if (isEmpty()) {
      System.out.println("Queue is empty");
      return null;
    }
    Q data = front.getValue();
    front = front.getNext();
    size--;
    if(front == null) {
        rear = null;
    }
    return data;
    }
    
    
    public Q peek() {
    if (isEmpty()) {
    System.out.println("Queue is empty");
    return null;
    }
    return front.getValue();
    }
    
    public boolean isEmpty() {
    return front == null;
    }
    public String toString(){
      StringBuilder sb = new StringBuilder();
      Node<Q> current = front;
      while(current != null){
        sb.append(current.getValue()).append(" ");
        current = current.getNext();
      }
      return sb.toString();
    }
    public int size() {
        return size;
    }
}