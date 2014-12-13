package com.pds.list;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by alexander on 13.12.14.
 */
public class UnsafeUtils {

    private static final Unsafe U;
    private static final long NEXT_NODE_OFFSET;
    private static final long NEXT_CHUNK_OFFSET;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            U = (Unsafe) field.get(null);

            Field nextNodeField = LinkedList.Some.class.getDeclaredField("next");
            NEXT_NODE_OFFSET = U.objectFieldOffset(nextNodeField);

            Field nextChunkField = MatrixList.Chunk.class.getDeclaredField("next");
            NEXT_CHUNK_OFFSET = U.objectFieldOffset(nextChunkField);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static <E> void putNextNode(LinkedList.Some<E> node, LinkedList.Node<E> nextNode) {
        U.putObject(node, NEXT_NODE_OFFSET, nextNode);
    }

    static <E> void putNextChunk(MatrixList.Chunk<E> currentChunk, MatrixList.Chunk<E> nextChunk) {
        U.putObject(currentChunk, NEXT_CHUNK_OFFSET, nextChunk);
    }

    private UnsafeUtils() {}
}
