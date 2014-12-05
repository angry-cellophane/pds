package com.pds.list;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface List<E> extends Iterable<E> {

    int size();
    
    boolean isEmpty();

    E head();

    List<E> tail();

    Iterator<E> iterator();

    Object[] toArray();

    E[] toArray(E[] a);
    
    List<E> add(E element);

    List<E> addAll(List<E> elements);

    List<E> addAll(E... elements);

    <R> List<R> map(Function<? super E,? extends R> mapper);

    <R> List<R> flatMap(Function<? super E, ? extends List<? extends R>> mapper);

    <R> R foldLeft(R identity, BiFunction<R, ? super E, R> foldFunction);

    <R> R foldRight(R identity, BiFunction<R, ? super E, R> foldFunction);

    boolean anyMatch(Predicate<? super E> predicate);

    boolean allMatch(Predicate<? super E> predicate);

    List<E> filter(Predicate<? super E> predicate);

    Optional<E> find(Predicate<? super E> predicate);

    public static <E> List<E> of(E ... values ) {
        List<E> list = LinkedList.nill();
        for (int i = values.length -1; i >= 0 ; i--) {
            list = list.add(values[i]);
        }
        return list;
    }

    public static <E> List<E> of(java.util.List<E> originalList) {
        List<E> list = LinkedList.nill();
        for (ListIterator<E> it = originalList.listIterator(originalList.size()); it.hasPrevious();){
            E value = it.previous();
            list = list.add(value);
        }
        return list;
    }


    public static <E> List<E> matrixList(E ... values ) {
        return MatrixList.of(values);
    }

    public static <E> List<E> matrixList(java.util.List<E> originalList) {
        List<E> list = MatrixList.empty();
        for (ListIterator<E> it = originalList.listIterator(originalList.size()); it.hasPrevious();){
            E value = it.previous();
            list = list.add(value);
        }
        return list;
    }

    public static <E> List<E> empty() {
        return LinkedList.nill();
    }

    public static <E> List<E> emptyMatrixList() {
        return MatrixList.empty();
    }
}
