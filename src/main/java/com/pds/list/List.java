package com.pds.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface List<E> {

    int size();
    
    boolean isEmpty();

    E head();

    List<E> tail();

    Iterator<E> iterator();

    Object[] toArray();

    E[] toArray(E[] a);

    List<E> remove(E element);

    List<E> addAll(List<? extends E> c);

    List<E> removeAll(List<? super E> c);

    List<E> retainAll(List<? super E> c);
    
    List<E> add(E element);

    ListIterator<E> listIterator();

    <R> List<R> map(Function<? super E,? extends R> mapper);

    <R> List<R> flatMap(Function<? super E, ? extends List<? extends R>> mapper);

    <R> R foldLeft(R initValue, BiFunction<? extends R, ? super E, ? extends R> foldFunction);

    <R> R foldRight(R initValue, BiFunction<? extends R, ? super E, ? extends R> foldFunction);

    boolean anyMatch(Predicate<? super E> predicate);

    boolean allMatch(Predicate<? super E> predicate);

    List<E> filter(Predicate<? super E> predicate);

    E find(Predicate<? super E> predicate);

    boolean equals(Object that);

    int hashcode();

    public static <E> List<E> of(E ... values ) {
        return null;
    }

    public static <E> List<E> of(java.util.List<E> list) {
        return null;
    }

    public static <E> List<E> empty() {
        return null;
    }

}
