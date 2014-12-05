package com.pds.list;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class MatrixList<E> implements List<E> {

    private static class Chunk<E> {
        private static final Chunk<?> EMPTY = new Chunk<>(null, null, -1, 0);

        private final Chunk<E> next;
        private final E[] values;
        private final int firstValueIndex;
        private final int valuesCount;

        private Chunk(Chunk<E> next, E[] values, int firstValueIndex, int valuesCount) {
            this.next = next;
            this.values = values;
            this.valuesCount = valuesCount;
            this.firstValueIndex = firstValueIndex;
        }

        private static <E> Chunk<E> empty(){
            @SuppressWarnings("unchecked") Chunk<E> empty = (Chunk<E>) EMPTY;
            return empty;
        }
    }

    private static final int CHUNK_SIZE = 16;

    private final int chunkSize;
    private final Chunk<E> headChunk;
    private final int size;

    public MatrixList(Chunk<E> chunk, int chunkSize){
        this.chunkSize = chunkSize;
        this.headChunk = chunk;
        size = calculateSize();
    }

    public MatrixList(Chunk<E> chunk) {
        this(chunk, CHUNK_SIZE);
    }

    private int calculateSize() {
        int size = 0;
        Chunk<E> current = headChunk;
        while (current != Chunk.EMPTY) {
            size += current.valuesCount - current.firstValueIndex;
            current = current.next;
        }
        return size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return headChunk == Chunk.EMPTY;
    }

    @Override
    public E head() {
        if (headChunk == Chunk.EMPTY) throw new IllegalStateException();

        return headChunk.values[headChunk.valuesCount - 1];
    }

    @Override
    public List<E> tail() {
        if (headChunk == Chunk.EMPTY) throw new IllegalStateException();

        if (headChunk.valuesCount == 1) return new MatrixList<>(headChunk.next, headChunk.next.valuesCount);

        Chunk<E> tailChunk = new Chunk<>(headChunk.next,
                headChunk.values,
                headChunk.firstValueIndex + 1,
                headChunk.valuesCount);
        return new MatrixList<>(tailChunk);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public E[] toArray(E[] a) {
        return (E[]) new Object[0];
    }

    @Override
    public List<E> add(E element) {
        return null;
    }

    @Override
    public <R> List<R> map(Function<? super E, ? extends R> mapper) {
        return null;
    }

    @Override
    public <R> List<R> flatMap(Function<? super E, ? extends List<? extends R>> mapper) {
        return null;
    }

    @Override
    public <R> R foldLeft(R identity, BiFunction<R, ? super E, R> foldFunction) {
        return null;
    }

    @Override
    public <R> R foldRight(R identity, BiFunction<R, ? super E, R> foldFunction) {
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
    public Optional<E> find(Predicate<? super E> predicate) {
        return null;
    }

    public static <E> List<E> empty(){
        return new MatrixList<>(Chunk.empty());
    }

    public static <E> List<E> of(E ... values) {
        Chunk<E> head = Chunk.empty();
        for (int i = 0; i < values.length; i+= CHUNK_SIZE) {
            int size = Math.min(CHUNK_SIZE, values.length);
            @SuppressWarnings("unchecked") E[] newValues = (E[]) new Object[size];
            System.arraycopy(values,i,newValues,0, size);
            head = new Chunk<>(head, newValues, 0, size);
        }
        return new MatrixList<>(head);
    }
}
