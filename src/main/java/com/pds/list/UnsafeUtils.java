package com.pds.list;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by alexander on 13.12.14.
 */
public class UnsafeUtils {

    private static final Unsafe U;
    private static final long NEXT_FIELD_OFFSET;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            U = (Unsafe) field.get(null);
            Field nextField = LinkedList.Some.class.getDeclaredField("next");
            NEXT_FIELD_OFFSET = U.objectFieldOffset(nextField);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static <E> void writeNextNode(LinkedList.Some<E> node, LinkedList.Node<E> nextNode) {
        U.putObject(node, NEXT_FIELD_OFFSET, nextNode);
    }

    private UnsafeUtils() {}
}
