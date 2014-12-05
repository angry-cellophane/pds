package com.pds.list;

import com.pds.annotations.Immutable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class MatrixList<E> implements List<E> {

    @Immutable
    private static class Chunk<E> {
        private static final Chunk<?> EMPTY = new Chunk<>(null, null, 0, 0);

        private final Chunk<E> next;
        private final E[] values;
        private final int firstValueIndex;
        private final int totalCount;

        private Chunk(Chunk<E> next, E[] values, int firstValueIndex, int totalCount) {
            this.next = next;
            this.values = values;
            this.totalCount = totalCount;
            this.firstValueIndex = firstValueIndex;
        }

        private static <E> Chunk<E> empty(){
            @SuppressWarnings("unchecked") Chunk<E> empty = (Chunk<E>) EMPTY;
            return empty;
        }
    }

    private static final int CHUNK_SIZE = 16;
    private static final MatrixList<?> EMPTY = new MatrixList<Object>(Chunk.empty());

    private final int chunkSize;
    private final Chunk<E> headChunk;

    MatrixList(Chunk<E> chunk, int chunkSize){
        this.chunkSize = chunkSize;
        this.headChunk = chunk;
    }

    MatrixList(Chunk<E> chunk) {
        this(chunk, CHUNK_SIZE);
    }

    @Override
    public int size() {
        return headChunk.totalCount;
    }

    @Override
    public boolean isEmpty() {
        return headChunk == Chunk.EMPTY;
    }

    @Override
    public E head() {
        if (headChunk == Chunk.EMPTY) throw new IllegalStateException();
        return headChunk.values[headChunk.firstValueIndex];
    }

    @Override
    public List<E> tail() {
        if (headChunk == Chunk.EMPTY) throw new IllegalStateException();
        if (headChunk.totalCount == 1) return empty();
        if (headChunk.firstValueIndex == headChunk.values.length - 1) return new MatrixList<>(headChunk.next);

        Chunk<E> tailChunk = new Chunk<>(headChunk.next,
                headChunk.values,
                headChunk.firstValueIndex + 1,
                headChunk.totalCount - 1);
        return new MatrixList<>(tailChunk);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Chunk<E> chunk;
            private int index;

            {
                chunk = headChunk;
                index = headChunk.firstValueIndex - 1;
            }

            @Override
            public boolean hasNext() {
                return index != chunk.values.length - 1
                        || chunk.next != Chunk.EMPTY;
            }

            @Override
            public E next() {
                if (index != chunk.values.length - 1) {
                    index ++;
                } else {
                    chunk = chunk.next;
                    if (chunk == Chunk.EMPTY) throw new IllegalStateException("Can not get a next element of the empty list");

                    index = chunk.firstValueIndex;
                }
                return chunk.values[index];
            }
        };
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
        Chunk<E> newChunk;
        if (headChunk.firstValueIndex == 0) {
            E[] newValues = (E[]) new Object[chunkSize];
            newValues[chunkSize - 1] = element;
            newChunk = new Chunk<>(headChunk, newValues, chunkSize - 1, headChunk.totalCount + 1);
        } else {
            E[] valueCopy = Arrays.copyOf(headChunk.values, chunkSize);
            int newFirstValueIndex = headChunk.firstValueIndex - 1;
            valueCopy[newFirstValueIndex] = element;
            newChunk = new Chunk<>(headChunk.next, valueCopy, newFirstValueIndex, headChunk.totalCount + 1);
        }
        return new MatrixList<>(newChunk);
    }

    @Override
    public List<E> addAll(List<E> elements) {
        return null;
    }

    @Override
    public List<E> addAll(E... elements) {
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
        for (E e : this) {
            if (predicate.test(e)) return true;
        }
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super E> predicate) {
        for (E e : this) {
            if (!predicate.test(e)) return false;
        }
        return true;
    }

    @Override
    public List<E> filter(Predicate<? super E> predicate) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<E> find(Predicate<? super E> predicate) {
        for (E e : this) {
            if (predicate.test(e)) return Optional.of(e);
        }
        return Optional.empty();
    }

    public static <E> List<E> empty(){
        @SuppressWarnings("unchecked") List<E> empty = (List<E>) EMPTY;
        return empty;
    }

    public static <E> List<E> of(E ... values) {
        Chunk<E> head = Chunk.empty();
        for (int i = values.length; i >= 0; i-= CHUNK_SIZE) {
            int size = Math.min(CHUNK_SIZE, values.length);
            @SuppressWarnings("unchecked") E[] newValues = (E[]) new Object[size];
            System.arraycopy(values, i - size, newValues, i - size, size);
            head = new Chunk<>(head, newValues, 0, size);
        }
        return new MatrixList<>(head);
    }
}
