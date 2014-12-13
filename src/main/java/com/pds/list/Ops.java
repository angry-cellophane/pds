package com.pds.list;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by alexander on 13.12.14.
 */
public class Ops {

    private static final Op TAIL = new Op(OpType.TAIL, OpFiller.NON, null);

    public static Op tail() {
        return TAIL;
    }

    public static <E> Op add(E element) {
        return new Op(OpType.ADD, OpFiller.SINGLE, element);
    }

    public static <E> Op addAll(List<E> elements) {
        return new Op(OpType.ADD, OpFiller.SINGLE, elements);
    }

    public static <E> Op addAll(E ... elements) {
        return new Op(OpType.ADD, OpFiller.ARRAY, elements);
    }

    public static Op map(Function<?,?> mapper) {
        return new Op(OpType.MAP, OpFiller.SINGLE, mapper);
    }

    public static Op flatMap(Function<?,?> mapper) {
        return new Op(OpType.FLAT_MAP, OpFiller.SINGLE, mapper);
    }

    public static <R> Op foldLeft(R identity, BiFunction<R, ?, R> foldFunction) {
        return new Op(OpType.FOLD_LEFT, OpFiller.ARRAY, new Object[]{identity, foldFunction});
    }

    public static <R> Op foldRight(R identity, BiFunction<R, ?, R> foldFunction) {
        return new Op(OpType.FOLD_RIGHT, OpFiller.SINGLE, new Object[]{identity, foldFunction});
    }

    public static Op anyMatch(Predicate<?> predicate) {
        return new Op(OpType.ANY_MATCH, OpFiller.SINGLE, predicate);
    }

    public static Op allMatch(Predicate<?> predicate) {
        return new Op(OpType.ALL_MATCH, OpFiller.SINGLE, predicate);
    }

    public static Op filter(Predicate<?> predicate) {
        return new Op(OpType.FILTER, OpFiller.SINGLE, predicate);
    }

    public static Op find(Predicate<?> predicate) {
        return new Op(OpType.FIND, OpFiller.SINGLE, predicate);
    }

    private Ops(){}
}
