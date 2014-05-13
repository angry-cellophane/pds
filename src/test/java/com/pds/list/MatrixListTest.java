package com.pds.list;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MatrixListTest {

    @Test
    public void testSizeEmptyList() throws Exception {
        List<Integer> list = List.emptyMatrixList();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testSizeOneChunk() throws Exception {
        List<Integer> list = List.matrixList(1,2,3,4,5);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testSizeFullChunk() throws Exception {

    }

    @Test
    public void testSizeManyChunks() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {

    }

    @Test
    public void testHead() throws Exception {

    }

    @Test
    public void testTail() throws Exception {

    }

    @Test
    public void testIterator() throws Exception {

    }

    @Test
    public void testToArray() throws Exception {

    }

    @Test
    public void testToArray1() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testMap() throws Exception {

    }

    @Test
    public void testFlatMap() throws Exception {

    }

    @Test
    public void testFoldLeft() throws Exception {

    }

    @Test
    public void testFoldRight() throws Exception {

    }

    @Test
    public void testAnyMatch() throws Exception {

    }

    @Test
    public void testAllMatch() throws Exception {

    }

    @Test
    public void testFilter() throws Exception {

    }

    @Test
    public void testFind() throws Exception {

    }
}