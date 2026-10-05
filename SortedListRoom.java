/**
 * TASK 2 -- a singly linked list kept in call order at all times.
 * Patient.before(a, b) decides the complete ordering rule.
 */
public class SortedListRoom implements WaitingRoom {

    private static class Node {
        final Patient p;
        Node next;

        Node(Patient p) {
            this.p = p;
        }
    }

    private Node head;
    private int size;

    public void add(Patient p) {
        Node node = new Node(p);

        // Empty list, or p belongs before the current head.
        if (head == null || Patient.before(p, head.p)) {
            node.next = head;
            head = node;
            size++;
            return;
        }

        // Find the position where p belongs.
        Node current = head;

        while (current.next != null
                && !Patient.before(p, current.next.p)) {
            current = current.next;
        }

        node.next = current.next;
        current.next = node;

        size++;
    }

    public Patient next() {
        if (head == null) {
            throw new IllegalStateException("empty room");
        }

        Patient result = head.p;
        head = head.next;

        size--;

        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** GIVEN -- the list from head to tail, used by the trace. Do not change. */
    public String layout() {
        StringBuilder s = new StringBuilder("[");

        for (Node c = head; c != null; c = c.next) {
            if (c != head) {
                s.append(' ');
            }

            s.append('#')
                    .append(c.p.id())
                    .append('s')
                    .append(c.p.severity());
        }

        return s.append(']').toString();
    }
}