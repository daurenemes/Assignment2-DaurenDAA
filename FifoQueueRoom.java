/**
 * TASK 1 -- the warm-up. A singly linked FIFO queue.
 * Severity is ignored: first patient in is the first patient out.
 */
public class FifoQueueRoom implements WaitingRoom {

    private static class Node {
        final Patient p;
        Node next;

        Node(Patient p) {
            this.p = p;
        }
    }

    private Node head, tail;
    private int size;

    public void add(Patient p) {
        Node node = new Node(p);

        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }

        size++;
    }

    public Patient next() {
        if (head == null) {
            throw new IllegalStateException("empty room");
        }

        Patient result = head.p;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        size--;
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}