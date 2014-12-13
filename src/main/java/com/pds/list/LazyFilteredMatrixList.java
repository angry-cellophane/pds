package com.pds.list;

import java.util.Iterator;
import java.util.function.Predicate;

import static com.pds.list.MatrixList.Chunk;

public class LazyFilteredMatrixList<E> extends MatrixList<E> {

    private final Predicate<? super E> predicate;
    private int size = -1;

    private class LazyFilteringMatrixListIterator extends MatrixListIterator {

        @Override
        public boolean hasNext() {
            while (super.hasNext()) {
                E e = textNext();
                if (predicate.test(e)) {
                    return true;
                } else {
                    stepOver();
                }
            }
            return false;
        }

        private E textNext() {
            int testedIndex;
            Chunk<E> c;
            if (index != chunk.values.length - 1) {
                testedIndex = index + 1;
                c = chunk;
            } else {
                c = chunk.next;
                if (c == Chunk.EMPTY)
                    throw new IllegalStateException("Can not get a next element of the empty list");

                testedIndex = c.firstValueIndex;
            }
            return c.values[testedIndex];
        }

        private void stepOver() {
            if (index != chunk.values.length - 1) {
                index ++;
            } else {
                chunk = chunk.next;
                if (chunk == Chunk.EMPTY) throw new IllegalStateException("Can not get a next element of the empty list");

                index = chunk.firstValueIndex;
            }
        }
    }

    LazyFilteredMatrixList(Chunk<E> chunk, int chunkSize, int size, Predicate<? super E> predicate) {
        super(chunk, chunkSize, size);
        this.predicate = predicate;
    }

    @Override
    public Iterator<E> iterator() {
        return new LazyFilteringMatrixListIterator();
    }

    @Override
    public int size() {
        if (size != -1) return size;

        int count = 0;
        for (E e : this) {
            count ++;
        }
        return size = count;
    }
}
