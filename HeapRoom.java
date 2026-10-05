/**
 * TASK 3 -- binary max-heap stored in a plain array.
 * Patient.before(a, b) decides which patient should be called first.
 */
public class HeapRoom implements WaitingRoom {

    private Patient[] a = new Patient[4];
    private int size;

    public void add(Patient p) {
        if (size == a.length) {
            Patient[] bigger = new Patient[a.length * 2];

            System.arraycopy(a, 0, bigger, 0, size);

            a = bigger;
        }

        a[size] = p;
        size++;

        siftUp(size - 1);
    }

    public Patient next() {
        if (size == 0) {
            throw new IllegalStateException("empty room");
        }

        Patient result = a[0];

        size--;

        if (size > 0) {
            a[0] = a[size];
            a[size] = null;

            siftDown(0);
        } else {
            a[0] = null;
        }

        return result;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;

            if (!Patient.before(a[i], a[parent])) {
                break;
            }

            swap(i, parent);

            i = parent;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;

            if (left >= size) {
                break;
            }

            int right = left + 1;
            int best = left;

            // Choose the better of the two children.
            if (right < size && Patient.before(a[right], a[left])) {
                best = right;
            }

            // Current node is already in the correct position.
            if (!Patient.before(a[best], a[i])) {
                break;
            }

            swap(i, best);

            i = best;
        }
    }

    private void swap(int i, int j) {
        Patient temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** GIVEN -- the heap array from index 0, used by the trace. Do not change. */
    public String layout() {
        StringBuilder s = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                s.append(' ');
            }

            s.append('#')
                    .append(a[i].id())
                    .append('s')
                    .append(a[i].severity());
        }

        return s.append(']').toString();
    }
}