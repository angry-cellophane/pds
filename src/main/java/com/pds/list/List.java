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
        for (E value : values) {
            list = list.add(value);
        }
        return list;
    }

    public static <E> List<E> of(java.util.List<E> originalList) {
        List<E> list = LinkedList.nill();
        for (E value : originalList) {
            list = list.add(value);
        }
        return list;
    }

    public static <E> List<E> empty() {
        return LinkedList.nill();
    }

}
