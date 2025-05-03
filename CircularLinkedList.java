// /**
//  * Implementation of a Circular Linked List for rotating maze corridors
//  * @param <T> Type of elements stored in the list
//  */
// public class CircularLinkedList<T> {
//     private Node<T> head;
//     private int size;
    
//     /**
//      * Node class for circular linked lis   t
//      * @param <T> Type of data stored in the node
//      */
//     private static class Node<T> {
//         T data;
//         Node<T> next;
        
//         public Node(T data) {
//             this.data = data;
//             this.next = null;
//         }
//     }
    
//     /**
//      * Constructor for an empty circular linked list
//      */
//     public CircularLinkedList() {
//         head = null;
//         size = 0;
//     }
    
//     /**
//      * Adds a new element to the list
//      * @param data Element to be added
//      */
//     public void add(T data) {
//         Node<T> newNode = new Node<>(data);
        
//         if (isEmpty()) {
//             head = newNode;
//             newNode.next = head; // Points back to itself to form a circle
//         } else {
//             Node<T> current = head;
//             while (current.next != head) {
//                 current = current.next;
//             }
//             current.next = newNode;
//             newNode.next = head;
//         }
        
//         size++;
//     }
    
//     /**
//      * Rotates the circular linked list by one position
//      * Shifts all elements one position to the right
//      */
//     public void rotate() {
//         if (!isEmpty() && size > 1) {
//             // Find the last node
//             Node<T> last = head;
//             while (last.next != head) {
//                 last = last.next;
//             }
            
//             // Move head to the end and update head
//             last.next = head.next;
//             head.next = head;
//             head = last.next;
//         }
//     }
    
//     /**
//      * Gets the element at the specified index
//      * @param index Position of the element
//      * @return Element at the specified index
//      * @throws IndexOutOfBoundsException if index is out of range
//      */
//     public T get(int index) {
//         if (isEmpty()) {
//             throw new IllegalStateException("Cannot get from an empty list");
//         }
        
//         if (index < 0 || index >= size) {
//             throw new IndexOutOfBoundsException("Index " +  + " is out of bounds");
//         }
        
//         Node<T> current = head;
//         for (int i = 0; i < index; i++) {
//             current = current.next;
//         }
        
//         return current.data;
//     }
    
//     /**
//      * Sets the element at the specified index
//      * @param index Position of the element
//      * @param data New element value
//      * @throws IndexOutOfBoundsException if index is out of range
//      */
//     public void set(int index, T data) {
//         if (isEmpty()) {
//             throw new IllegalStateException("Cannot set in an empty list");
//         }
        
//         if (index < 0 || index >= size) {
//             throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
//         }
        
//         Node<T> current = head;
//         for (int i = 0; i < index; i++) {
//             current = current.next;
//         }
        
//         current.data = data;
//     }
    
//     /**
//      * Checks if the list is empty
//      * @return true if list is empty, false otherwise
//      */
//     public boolean isEmpty() {
//         return head == null;
//     }
    
//     /**
//      * Returns the number of elements in the list
//      * @return Size of the list
//      */
//     public int size() {
//         return size;
//     }
    
//     /**
//      * Returns a string representation of the list
//      * @return String representation of all elements
//      */
//     @Override
//     public String toString() {
//         if (isEmpty()) {
//             return "[]";
//         }
        
//         StringBuilder sb = new StringBuilder("[");
//         Node<T> current = head;
        
//         do {
//             sb.append(current.data);
//             current = current.next;
//             if (current != head) {
//                 sb.append(", ");
//             }
//         } while (current != head);
        
//         sb.append("]");
//         return sb.toString();
//     }
    
//     /**
//      * Returns the list as an array
//      * @return Array containing all elements
//      */
//     @SuppressWarnings("unchecked")
//     public T[] toArray() {
//         Object[] array = new Object[size];
        
//         if (!isEmpty()) {
//             Node<T> current = head;
//             for (int i = 0; i < size; i++) {
//                 array[i] = current.data;
//                 current = current.next;
//             }
//         }
        
//         return (T[]) array;
//     }
// }