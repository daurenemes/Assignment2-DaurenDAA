import java.util.List;

/**
 * TASK 4 -- an array-backed stack.
 * The stack itself is implemented manually with a growing Entry[] array.
 */
public class CallLog {

    public static final class Entry {
        public final Patient patient;
        public final int minute;

        public Entry(Patient patient, int minute) {
            this.patient = patient;
            this.minute = minute;
        }
    }

    private Entry[] a = new Entry[4];
    private int size;

    public void push(Entry e) {
        if (size == a.length) {
            Entry[] bigger = new Entry[a.length * 2];

            System.arraycopy(a, 0, bigger, 0, size);

            a = bigger;
        }

        a[size] = e;
        size++;
    }

    public Entry pop() {
        if (size == 0) {
            return null;
        }

        size--;

        Entry result = a[size];
        a[size] = null;

        return result;
    }

    public Entry peek() {
        if (size == 0) {
            return null;
        }

        return a[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** The n most recently called patients, most recent first. Does not change size(). */
    public List<Entry> recent(int n) {
        if (n < 0) {
            n = 0;
        }

        if (n > size) {
            n = size;
        }

        Entry[] result = new Entry[n];

        for (int i = 0; i < n; i++) {
            result[i] = a[size - 1 - i];
        }

        return List.of(result);
    }
}