package com.pds.list;

import java.util.Iterator;
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
            @SuppressWarnings("unchecked")
            Node<E> node = (Node<E>) Nill.INSTANCE;
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

    private final int size;
    private final Node<E> head;
    private final Node<E> tail;

    private LinkedList(Node<E> head, Node<E> tail, int size) {
        this.head = head;
        this.tail = tail;
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
        return new LinkedList<>(head.next(), tail, size - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
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
        return new LinkedList<>(Node.create(this.head, element), this.tail, size + 1);
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
        Node<R> newListTail = newListHead;
        for (int i = this.size - 1; i >= 0 ; i--) {
            newListHead = Node.create(newListHead, newValues[i]);
        }
        return new LinkedList<>(newListHead, newListTail, this.size);
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
            newValues[i] = values;
        }

        Node<R> newListHead = Node.nill();
        Node<R> newListTail = newListHead;
        for (int i = this.size - 1; i >= 0 ; i--) {
            R[] values = newValues[i];
            for (int j = values.length; j >=0 ; j--) {
                newListHead = Node.create(newListHead, values[j]);
            }
        }
        return new LinkedList<>(newListHead, newListTail, this.size);
    }

    @Override
    public <R> R foldLeft(R initValue, BiFunction<? extends R, ? super E, ? extends R> foldFunction) {
        return null;
    }

    @Override
    public <R> R foldRight(R initValue, BiFunction<? extends R, ? super E, ? extends R> foldFunction) {
        return null;
    }

    @Override
    public boolean anyMatch(Predicate<? super E> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super E> predicate) {
        return false;
    }

    @Override
    public List<E> filter(Predicate<? super E> predicate) {
        return null;
    }

    @Override
    public E find(Predicate<? super E> predicate) {
        return null;
    }
}
