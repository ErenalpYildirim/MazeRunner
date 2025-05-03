public class CircularLinkedListv2 {
    private Node tail;
    
    public CircularLinkedListv2() {
    tail = null;
    }
    public void add(int value) {
    Node newNode = new Node(value);
    if (tail == null) {
    tail = newNode;
    tail.setNext(tail);
    } else {
    newNode.setNext(tail.getNext());
    tail.setNext(newNode);
    tail = newNode;
    }
    }
    public void remove(int value) {
    if (tail != null) {
    Node prev = tail;
    Node curr = tail.getNext();
    while (curr != tail && curr.getValue() != value) {
    prev = curr;
    curr = curr.getNext();
    }
    if (curr != tail) {
    4
    CENG 202 – Data Structures
    if (curr == tail.getNext()) {
    tail.setNext(curr.getNext());
    }
    if (curr == tail) {
    tail = prev;
    }
    prev.setNext(curr.getNext());
    }
    }
    }
    public Node search(int value) {
    if (tail != null) {
    Node curr = tail.getNext();
    while (curr != tail && curr.getValue() != value) {
    curr = curr.getNext();
    }
    if (curr.getValue() == value) {
    return curr;
    }
    }
    return null;
    }
    public void rotateClockwise() {
    if (tail != null) {
    tail = tail.getNext();
    }
    }
    public void rotateCounterclockwise() {
    if (tail != null) {
    tail = tail.getPrev();
    }
    }
    }