package com.pds.list;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedList<E> implements List<E> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public E head() {
        return null;
    }

    @Override
    public List<E> tail() {
        return null;
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
        return null;
    }

    @Override
    public List<E> remove(E element) {
        return null;
    }

    @Override
    public List<E> addAll(List<? extends E> c) {
        return null;
    }

    @Override
    public List<E> removeAll(List<? super E> c) {
        return null;
    }

    @Override
    public List<E> retainAll(List<? super E> c) {
        return null;
    }

    @Override
    public List<E> add(E element) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
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

    @Override
    public int hashcode() {
        return 0;
    }
}
