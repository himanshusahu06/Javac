import java.util.Iterator;

public class  Stack<T> implements Iterable<T> {

    private Node<T> first;

    public void push(T item) {
        Node<T> oldFirst = first;
        first = new Node<>(item);
        first.next = oldFirst;
    }

    public T pop() {
        Node<T> poppedItem = first;
        first = first.next;
        poppedItem.next = null;
        return poppedItem.item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements  Iterator<T> {

        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            Node<T> curr = current;
            current = current.next;
            return curr.item;
        }
    }

    private static class Node<T> {
        T item;
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }
}
