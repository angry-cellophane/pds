package com.pds.list;

import com.pds.annotations.Immutable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class MatrixList<E> implements List<E> {

    @Immutable
    protected static class Chunk<E> {
        protected static final Chunk<?> EMPTY = new Chunk<>(null, null, 0);

        protected final Chunk<E> next;
        protected final E[] values;
        protected final int firstValueIndex;
        protected int hashCode;

        private Chunk(Chunk<E> next, E[] values, int firstValueIndex) {
            this.next = next;
            this.values = values;
            this.firstValueIndex = firstValueIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Chunk chunk = (Chunk) o;

            if (firstValueIndex != chunk.firstValueIndex) return false;
            if (!next.equals(chunk.next)) return false;
            if (!Arrays.equals(values, chunk.values)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            if (hashCode > 0 || this == EMPTY) return hashCode;

            if (this == EMPTY) return 0;

            hashCode = next.hashCode();
            hashCode = 31 * hashCode + Arrays.hashCode(values);
            hashCode = 31 * hashCode + firstValueIndex;
            return hashCode;
        }

        private static <E> Chunk<E> empty(){
            @SuppressWarnings("unchecked") Chunk<E> empty = (Chunk<E>) EMPTY;
            return empty;
        }
    }

    protected class MatrixListIterator implements Iterator<E> {

        protected Chunk<E> chunk;
        protected int index;

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

    private static final int CHUNK_SIZE = 16;
    private static final MatrixList<?> EMPTY = new MatrixList<>(Chunk.empty(), 0);

    private final int chunkSize;
    private final Chunk<E> headChunk;
    private final int size;
    private int hash;

    MatrixList(Chunk<E> chunk, int chunkSize, int size){
        this.chunkSize = chunkSize;
        this.headChunk = chunk;
        this.size = size;
    }

    MatrixList(Chunk<E> chunk, int size) {
        this(chunk, CHUNK_SIZE, size);
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
        return headChunk.values[headChunk.firstValueIndex];
    }

    @Override
    public List<E> tail() {
        if (headChunk == Chunk.EMPTY) throw new IllegalStateException();
        if (size == 1) return empty();
        if (headChunk.firstValueIndex == headChunk.values.length - 1) return new MatrixList<>(headChunk.next, size - 1);

        Chunk<E> tailChunk = new Chunk<>(headChunk.next,
                headChunk.values,
                headChunk.firstValueIndex + 1);
        return new MatrixList<>(tailChunk, size - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return new MatrixListIterator();
    }

    @Override
    public Object[] toArray() {
        @SuppressWarnings("unchecked") E[] newArray = (E[])new Object[size];
        return toArray(newArray);
    }

    @Override
    public E[] toArray(E[] a) {
        int index = 0;
        Chunk<?> c = headChunk;
        while (c != Chunk.EMPTY) {
            int count = c.values.length - c.firstValueIndex;
            System.arraycopy(c.values, c.firstValueIndex, a, index, count);
            index += count;
            c = c.next;
        }
        return a;
    }

    @Override
    public List<E> add(E element) {
        Chunk<E> newChunk;
        if (headChunk.firstValueIndex == 0) {
            E[] newValues = (E[]) new Object[chunkSize];
            newValues[chunkSize - 1] = element;
            newChunk = new Chunk<>(headChunk, newValues, chunkSize - 1);
        } else {
            E[] valueCopy = Arrays.copyOf(headChunk.values, chunkSize);
            int newFirstValueIndex = headChunk.firstValueIndex - 1;
            valueCopy[newFirstValueIndex] = element;
            newChunk = new Chunk<>(headChunk.next, valueCopy, newFirstValueIndex);
        }
        return new MatrixList<>(newChunk, size + 1);
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
        if (this == EMPTY) return empty();

        int chunkCount = size / chunkSize;
        if (size % chunkSize != 0) chunkCount++;
        @SuppressWarnings("unchecked") R[][] newValues = (R[][]) new Object[chunkCount][];
        Chunk<E> c = headChunk;
        int chunkNumber = 0;
        while (c != Chunk.EMPTY) {
            @SuppressWarnings("unchecked") R[] newChunkValues = (R[]) new Object[c.values.length];
            for (int i = 0; i < c.values.length; i++) {
                newChunkValues[i] = mapper.apply(c.values[i]);
            }
            newValues[chunkNumber] = newChunkValues;
            c = c.next;
        }
        Chunk<R> p = Chunk.empty();
        for (int i = newValues.length - 1; i >= 1; i--) {
            p = new Chunk<>(p, newValues[i], 0);
        }
        p = new Chunk<>(p, newValues[0], headChunk.firstValueIndex);
        return new MatrixList<R>(p, chunkSize, size);
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
        return new LazyFilteredMatrixList<E>(headChunk, chunkSize, size, predicate);
    }

    @Override
    public Optional<E> find(Predicate<? super E> predicate) {
        for (E e : this) {
            if (predicate.test(e)) return Optional.of(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof MatrixList)) return false;

        @SuppressWarnings("unchecked") MatrixList<E> that = (MatrixList<E>) o;

        if (size != that.size()) return false;
        for (Iterator<E> it1 = this.iterator(), it2 = that.iterator(); it1.hasNext() && it2.hasNext(); ) {
            E el1 = it1.next();
            E el2 = it2.next();
            if ((el1 == null && el2 != null) || !el1.equals(el2)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if (hash != 0 || headChunk == Chunk.empty()) return hash;

        hash = headChunk.hashCode();
        hash = 31 * hash + size;
        return hash;
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
            head = new Chunk<>(head, newValues, 0);
        }
        return new MatrixList<>(head, values.length);
    }
}
