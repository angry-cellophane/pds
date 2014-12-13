package com.pds.list;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by alexander on 13.12.14.
 */
public class CachedWrapper<E> implements List<E> {

    private final List<E> realList;
    private final Map<Op, Object> cache;

    CachedWrapper(List<E> realList) {
        this.realList = realList;
        this.cache = new WeakHashMap<>();
    }

    @Override
    public int size() {
        return realList.size();
    }

    @Override
    public boolean isEmpty() {
        return realList.isEmpty();
    }

    @Override
    public E head() {
        return realList.head();
    }

    @Override
    public List<E> tail() {
        Op op = Ops.tail();
        @SuppressWarnings("unchecked") List<E> result = (List<E>) cache.get(op);
        if (result != null) return result;
        result = realList.tail();
        cache.put(op, result);
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return realList.iterator();
    }

    @Override
    public Object[] toArray() {
        return realList.toArray();
    }

    @Override
    public E[] toArray(E[] a) {
        return realList.toArray(a);
    }

    @Override
    public List<E> add(E element) {
        Op op = Ops.add(element);
        @SuppressWarnings("unchecked") List<E> result = (List<E>) cache.get(op);
        if (result != null) return result;
        result = realList.add(element);
        cache.put(op, result);
        return result;
    }

    @Override
    public List<E> addAll(List<E> elements) {
        Op op = Ops.addAll(elements);
        @SuppressWarnings("unchecked") List<E> result = (List<E>) cache.get(op);
        if (result != null) return result;

        result = realList.addAll(elements);
        cache.put(op, result);
        return result;
    }

    @Override
    public List<E> addAll(E... elements) {
        Op op = Ops.addAll(elements);
        @SuppressWarnings("unchecked") List<E> result = (List<E>) cache.get(op);
        if (result != null) return result;
        result = realList.addAll(elements);
        cache.put(op, result);
        return result;
    }

    @Override
    public <R> List<R> map(Function<? super E, ? extends R> mapper) {
        Op op = Ops.map(mapper);
        @SuppressWarnings("unchecked") List<R> result = (List<R>) cache.get(op);
        if (result != null) return result;
        result = realList.map(mapper);
        cache.put(op,result);
        return result;
    }

    @Override
    public <R> List<R> flatMap(Function<? super E, ? extends List<? extends R>> mapper) {
        Op op = Ops.flatMap(mapper);
        @SuppressWarnings("unchecked") List<R> result = (List<R>) cache.get(op);
        if (result != null) return result;
        result = realList.flatMap(mapper);
        cache.put(op, result);
        return result;

    }

    @Override
    public <R> R foldLeft(R identity, BiFunction<R, ? super E, R> foldFunction) {
        Op op = Ops.foldLeft(identity, foldFunction);
        @SuppressWarnings("unchecked") R result = (R) cache.get(op);
        if (result != null) return result;
        result = realList.foldLeft(identity, foldFunction);
        cache.put(op, result);
        return result;
    }

    @Override
    public <R> R foldRight(R identity, BiFunction<R, ? super E, R> foldFunction) {
        Op op = Ops.foldRight(identity, foldFunction);
        @SuppressWarnings("unchecked") R result = (R) cache.get(op);
        if (result != null) return result;
        result = realList.foldRight(identity, foldFunction);
        cache.put(op, result);
        return result;
    }

    @Override
    public boolean anyMatch(Predicate<? super E> predicate) {
        Op op = Ops.anyMatch(predicate);
        @SuppressWarnings("unchecked") Boolean result = (Boolean) cache.get(op);
        if (result != null) return result;
        result = realList.anyMatch(predicate);
        cache.put(op, result);
        return result;
    }

    @Override
    public boolean allMatch(Predicate<? super E> predicate) {
        Op op = Ops.allMatch(predicate);
        @SuppressWarnings("unchecked") Boolean result = (Boolean) cache.get(op);
        if (result != null) return result;
        result = realList.allMatch(predicate);
        cache.put(op, result);
        return result;
    }

    @Override
    public List<E> filter(Predicate<? super E> predicate) {
        Op op = Ops.filter(predicate);
        @SuppressWarnings("unchecked") List<E> result = (List<E>) cache.get(op);
        if (result != null) return result;
        result = realList.filter(predicate);
        cache.put(op, result);
        return result;
    }

    @Override
    public Optional<E> find(Predicate<? super E> predicate) {
        Op op = Ops.find(predicate);
        @SuppressWarnings("unchecked") Optional<E> result = (Optional<E>) cache.get(op);
        if (result != null) return result;
        result = realList.find(predicate);
        cache.put(op, result);
        return result;
    }
}
