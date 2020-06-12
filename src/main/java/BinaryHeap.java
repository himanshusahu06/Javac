import java.util.Arrays;

/**
* Binary max heap (priority with priority as value itself)
**/
public class BinaryHeap {
    private int[] arr;
    private int size;

    public BinaryHeap(int maxSize) {
        this.arr = new int[maxSize];
        this.size = 0;
    }

    public void insert(int value) {
        arr[size++] = value;
        swim(size - 1);
    }

    public int getMax() {
        return arr[0];
    }

    public int deleteMax() {
        if (size < 0) {
            throw new IllegalArgumentException("No elements to delete from heap.");
        }
        int maxElement = arr[0];
        swap(0, --size);
        sink(0);
        return maxElement;
    }

    private void swim(int index) {
        System.out.printf("swimming %d\n", arr[index]);
        while (index > 0 && less((index - 1) / 2, index)) {
            swap((index - 1) / 2, index);
            index = (index - 1) / 2;
        }
    }

    private void sink(int index) {
        while ((2 * index + 1) < size) {
            int left = 2 * index + 1;
            if (left + 1 < size && less(left, left+1)) {
                left++;
            }
            if (less(index, left)) {
                swap(index, left);
                index = left;
            } else {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "BinaryHeap{" +
                "arr=" + Arrays.toString(arr) +
                '}';
    }

    private boolean less(int x, int y) {
        return arr[x] < arr[y];
    }

    private void swap(int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}
