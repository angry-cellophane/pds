package com.pds.list;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

import static org.testng.Assert.*;

public class LinkedListTest {

    @Test
    public void testSizeEmptyList() throws Exception {
        List<Integer> list = List.empty();
        assertEquals(list.size(),0L);
    }

    @Test
    public void testSize5() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertEquals(list.size(),5L);
    }

    @Test
    public void testIsEmptyEmptyList() throws Exception {
        List<Integer> list = List.empty();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testIsEmptyNonEmptyList() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testHead() throws Exception {
        List<Integer> list = List.of(1);
        assertEquals(list.head(), Integer.valueOf(1));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testHeadEmptyList() throws Exception {
        List<Integer> list = List.empty();
        int head = list.head();
    }

    @Test
    public void testTail() throws Exception {
        List<Integer> list = List.of(2,3);
        assertEquals(list.add(1).tail(), List.of(2,3));
    }

    @Test
    public void testIterator() throws Exception {
        List<Integer> list = List.of(0,1,2,3,4);
        int count = 0;
        for (Iterator<Integer> it = list.iterator(); it.hasNext();) {
            Integer i = it.next();
            assertEquals(i, Integer.valueOf(count));
            count ++;
        }
        assertEquals(count, list.size());
    }

    @Test
    public void testToArray() throws Exception {
        List<Integer> list = List.of(0,1,2,3,4);
        assertEquals(list.toArray(), new Object[]{0,1,2,3,4});
    }

    @Test
    public void testToArray1() throws Exception {
        List<Integer> list = List.of(0,1,2,3,4);
        assertEquals(list.toArray(new Integer[5]), new Integer[]{0,1,2,3,4});
    }

    @Test
    public void testAdd() throws Exception {
        List<Integer> list = List.of(0,1,2,3,4);
        list = list.add(25);
        assertEquals(list.head(), Integer.valueOf(25));
    }

    @Test
    public void testMap() throws Exception {
        List<Integer> list = List.of(0,1,2,3,4);
        List<Integer> newList = list.map( x -> x * 2);
        assertEquals(list.size(), newList.size());

        for (Iterator<Integer> it1 = list.iterator(), it2 = newList.iterator();
             it1.hasNext() && it2.hasNext();) {
            Integer v1 = it1.next();
            Integer v2 = it2.next();
            assertEquals(v1 * 2, (int) v2);
        }
    }

    @Test
    public void testFlatMap() throws Exception {
        List<Integer> list = List.of(0,1,2,3,4);
        List<Integer> newList = list.flatMap(i -> List.of(i, i * 2, i * 3));
        assertEquals(newList.size(), list.size() * 3);

        for (Iterator<Integer> it1 = list.iterator(), it2 = newList.iterator();
             it1.hasNext();) {
            Integer v1 = it1.next();
            for (int i = 1; i <= 3; i++) {
                assertTrue(it2.hasNext());
                Integer v2 = it2.next();
                assertEquals(v1 * i, (int) v2);
            }
        }
    }

    @Test
    public void testFoldLeftCommutativeOperation() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertEquals(list.foldLeft(0, (x, y) -> x + y), Integer.valueOf(15));
    }

    @Test
    public void testFoldLeftNonCommutativeOperation() throws Exception {
        List<String> list = List.of("a", "b", "c", "d");
        assertEquals(list.foldLeft("", (x, y) -> x + y), "abcd");
    }

    @Test
    public void testFoldRightCommutativeOperation() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertEquals(list.foldRight(0, (x, y) -> x + y), Integer.valueOf(15));
    }

    @Test
    public void testFoldRightNonCommutativeOperation() throws Exception {
        List<String> list = List.of("a", "b", "c", "d");
        assertEquals(list.foldRight("", (x, y) -> x + y), "dcba");
    }

    @Test
    public void testAnyMatchHasValue() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertTrue(list.anyMatch(x -> x > 4));
    }

    @Test
    public void testAnyMatchHasNoValue() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertFalse(list.anyMatch(x -> x < 0));
    }

    @Test
    public void testAllMatchTrue() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertTrue(list.allMatch(x -> x > 0));
    }

    @Test
    public void testAllMatchFalse() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertFalse(list.allMatch(x -> x % 2 != 0));
    }

    @Test
    public void testFilter() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        assertEquals(list.filter(x -> x % 2 != 0).toArray(), new Object[]{1,3,5});
    }

    @Test
    public void testFindTrue() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        Optional<Integer> actual = list.find(x -> x % 2 != 0 && x > 2);
        assertTrue(actual.isPresent());
        assertEquals((int)actual.get(), 3);
    }

    @Test
    public void testFindFalse() throws Exception {
        List<Integer> list = List.of(1,2,3,4,5);
        Optional<Integer> actual = list.find(x -> x % 2 != 0 && x < 0);
        assertFalse(actual.isPresent());
    }
}