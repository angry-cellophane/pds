package com.pds.list;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedList<E> implements List<E> {

    private static interface Node<E> {
        boolean isNill();
        Node<E> next();
        E value();

        static <E> Node<E> create(Node<E> next, E value){
            return new Some<>(next, value);
        }

        static <E> Node<E> nill(){
            @SuppressWarnings("unchecked") Node<E> node = (Node<E>) Nill.INSTANCE;
            return node;
        }
    }

    private static final class Some<E> implements Node<E> {

        private final E value;
        private final Node<E> next;

        private Some(Node<E> next,E value) {
            this.value = value;
            this.next = next;
        }

        @Override
        public boolean isNill() {
            return false;
        }

        @Override
        public Node<E> next() {
            return this.next;
        }

        @Override
        public E value() {
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Some some = (Some) o;

            if (!next.equals(some.next)) return false;
            if (!value.equals(some.value)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = value.hashCode();
            result = 31 * result + next.hashCode();
            return result;
        }
    }

    private static enum Nill implements Node {
        INSTANCE;

        @Override
        public boolean isNill() {
            return true;
        }

        @Override
        public Node next() {
            return null;
        }

        @Override
        public Object value() {
            throw new IllegalStateException("Nill node has no value");
        }
    }

    private static class LinkedListIterator<E> implements Iterator<E>{
        private Node<E> currentNode;

        private LinkedListIterator(Node<E> head) {
            this.currentNode = head;
        }

        @Override
        public boolean hasNext() {
            return !currentNode.isNill();
        }

        @Override
        public E next() {
            E value = currentNode.value();
            currentNode = currentNode.next();
            return value;
        }
    }

    private final int size;
    private final Node<E> head;

    private LinkedList(Node<E> head, int size) {
        this.head = head;
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E head() {
        return head.value();
    }

    @Override
    public List<E> tail() {
        return new LinkedList<>(head.next(), size - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator<>(head);
    }

    @Override
    public Object[] toArray() {
        Object[] values = new Object[this.size];

        Node<E> node = null;
        for (int i = 0; i < this.size; i++) {
            node = i == 0 ? this.head : node.next();
            values[i] = node.value();
        }
        return values;
    }

    @Override
    public E[] toArray(E[] values) {
        Node<E> node = null;
        for (int i = 0; i < this.size; i++) {
            node = i == 0 ? this.head : node.next();
            values[i] = node.value();
        }
        return values;
    }


    @Override
    public List<E> add(E element) {
        return new LinkedList<>(Node.create(this.head, element), size + 1);
    }

    @Override
    public <R> List<R> map(Function<? super E, ? extends R> mapper) {
        @SuppressWarnings("unchecked")
        R[] newValues = (R[]) new Object[this.size];

        Node<? super E> node = null;
        for (int i = 0; i < this.size; i++) {
            node = i == 0 ? this.head : node.next();
            @SuppressWarnings("unchecked") E oldValue = (E) node.value();
            newValues[i] = mapper.apply(oldValue);
        }

        Node<R> newListHead = Node.nill();
        for (int i = this.size - 1; i >= 0 ; i--) {
            newListHead = Node.create(newListHead, newValues[i]);
        }
        return new LinkedList<>(newListHead, this.size);
    }

    @Override
    public <R> List<R> flatMap(Function<? super E, ? extends List<? extends R>> mapper) {
        @SuppressWarnings("unchecked")
        R[][] newValues = (R[][]) new Object[this.size][];

        Node<? super E> node = null;
        for (int i = 0; i < this.size; i++) {
            node = i == 0 ? this.head : node.next();
            @SuppressWarnings("unchecked") E oldValue = (E) node.value();
            @SuppressWarnings("unchecked") R[] values = (R[]) mapper.apply(oldValue).toArray();
            newValues[i] = (R[]) new Object[values.length];
            System.arraycopy(values, 0, newValues[i], 0, values.length);
        }

        int size = 0;
        Node<R> newListHead = Node.nill();
        for (int i = this.size - 1; i >= 0 ; i--) {
            R[] values = newValues[i];
            size += values.length;
            for (int j = values.length - 1; j >=0 ; j--) {
                newListHead = Node.create(newListHead, values[j]);
            }
        }
        return new LinkedList<>(newListHead, size);
    }

    @Override
    public <R> R foldLeft(R identity, BiFunction<R, ? super E, R> foldFunction) {
        R base = identity;
        for (E value : this) {
            base = foldFunction.apply(base, value);
        }
        return base;
    }

    @Override
    public <R> R foldRight(R identity, BiFunction<R, ? super E, R> foldFunction) {
        R base = identity;
        @SuppressWarnings("unchecked") E[] values = toArray((E[]) new Object[this.size]);
        for (int i = values.length - 1; i >=0 ; i--) {
            base = foldFunction.apply(base, values[i]);
        }
        return base;
    }

    @Override
    public boolean anyMatch(Predicate<? super E> predicate) {
        return !allMatch(predicate.negate());
    }

    @Override
    public boolean allMatch(Predicate<? super E> predicate) {
        for (E value : this) {
            if (!predicate.test(value)) return false;
        }
        return true;
    }

    @Override
    public List<E> filter(Predicate<? super E> predicate) {
        @SuppressWarnings("unchecked") E[] values = toArray((E[])new Object[this.size]);

        Node<E> head = Node.nill();
        int size = 0;
        for (int i = values.length - 1; i >= 0; i--) {
            if (predicate.test(values[i])) {
                head = Node.create(head, values[i]);
                size ++;
            }
        }
        return new LinkedList<>(head, size);
    }

    @Override
    public Optional<E> find(Predicate<? super E> predicate) {
        for (E value : this) {
            if (predicate.test(value)) return Optional.of(value);
        }
        return Optional.empty();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedList that = (LinkedList) o;

        if (size != that.size) return false;
        if (!head.equals(that.head)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + head.hashCode();
        return result;
    }

    static <E> List<E> nill(){
        return new LinkedList<>(Node.nill(), 0);
    }
}
