package com.pds.list;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ListTest {

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
    public void testContains() throws Exception {

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
    public void testRemove() throws Exception {

    }

    @Test
    public void testContainsAll() throws Exception {

    }

    @Test
    public void testAddAll() throws Exception {

    }

    @Test
    public void testAddAll1() throws Exception {

    }

    @Test
    public void testRemoveAll() throws Exception {

    }

    @Test
    public void testRetainAll() throws Exception {

    }

    @Test
    public void testClear() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testAdd1() throws Exception {

    }

    @Test
    public void testRemove1() throws Exception {

    }

    @Test
    public void testIndexOf() throws Exception {

    }

    @Test
    public void testLastIndexOf() throws Exception {

    }

    @Test
    public void testListIterator() throws Exception {

    }

    @Test
    public void testListIterator1() throws Exception {

    }

    @Test
    public void testSubList() throws Exception {

    }
}