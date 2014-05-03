package com.pds.list;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LinkedListTest {

    @Test
    public void testSizeEmptyList() throws Exception {
        List<Integer> list = List.empty();
        assertEquals(list.size(),0);
    }

    @Test
    public void testSize5() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertEquals(list.size(),5);
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
    public void testRemove() throws Exception {

    }

    @Test
    public void testAddAll() throws Exception {

    }

    @Test
    public void testRemoveAll() throws Exception {

    }

    @Test
    public void testRetainAll() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testListIterator() throws Exception {

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

    @Test
    public void testHashcode() throws Exception {

    }
}