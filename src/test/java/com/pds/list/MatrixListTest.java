package com.pds.list;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.*;

public class MatrixListTest {

    @Test
    public void testCreation(){
        List<Integer> list = List.matrixList(1,2,3,4,5);
        Iterator<Integer> it = list.iterator();
        for (int i = 1; i < 5; i++) {
            assertTrue(it.hasNext());
            Integer value = it.next();
            assertEquals((int)value, i);
        }
    }

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
        List<Integer> list = List.matrixList(IntStream.range(1,17).mapToObj(Integer::new).collect(Collectors.toList()));
        assertFalse(list.isEmpty());
    }

    @Test
    public void testSizeManyChunks() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testHead() throws Exception {
        Integer[] ints = new Integer[] {1,2,3,4};
        List<Integer> list = List.matrixList(ints);
        assertEquals(list.head(), ints[0]);
    }

    @Test
    public void testTail() throws Exception {
        Integer[] ints = new Integer[] {4,3,2,1};
        List<Integer> list = List.matrixList(ints);
        assertEquals(list.tail(), Arrays.asList(3,2,1));
    }

    @Test
    public void testIterator() throws Exception {
        List<Integer> list = List.matrixList(0,1,2,3,4);
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
        Integer[] ints = new Integer[] {4,3,2,1};
        List<Integer> list = List.matrixList(ints);
        assertEquals(list.toArray(), ints);
    }

    @Test
    public void testToArray1() throws Exception {
        Integer[] ints = new Integer[] {4,3,2,1};
        List<Integer> list = List.matrixList(ints);
        assertEquals(list.toArray(new Integer[list.size()]), ints);
    }

    @Test
    public void testAdd() throws Exception {
        List<Integer> list = List.matrixList(2, 3, 4);
        assertEquals(list.size(),3);

        List<Integer> newList = list.add(1);
        assertEquals(newList.size(),4);
        assertEquals(newList.head(), Integer.valueOf(1));
    }

    @Test
    public void testMap() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4);
        List<Integer> newList = list.map(x -> x * 2);
        assertEquals(list.size(), newList.size());

        Iterator<Integer> it1 = list.iterator(), it2 = newList.iterator();
        for (; it1.hasNext() && it2.hasNext(); ) {
            Integer i1 = it1.next();
            Integer i2 = it2.next();

            assertEquals(Integer.valueOf(i1 * 2), i2);
        }
        assertFalse(it1.hasNext());
        assertFalse(it2.hasNext());
    }

    @Test
    public void testFlatMap() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4);
        List<Integer> newList = list.flatMap(x -> List.matrixList(1, 2, 3));
        int i = 1;
        for (Integer i1 : newList) {
            assertEquals(i1, Integer.valueOf(++i % 3));
        }
    }

    @Test
    public void testFoldLeft() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4, 5);
        Integer totalSum = list.foldLeft(0, (sum, cur) -> sum + cur);
        assertEquals(totalSum, Integer.valueOf(15));
    }

    @Test
    public void testFoldRight() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4, 5);
        String total = list.foldLeft("", (sum, cur) -> sum + cur);
        assertEquals(total, "54321");
    }

    @Test
    public void testAnyMatch() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4);
        assertTrue(list.anyMatch(x -> x % 2 == 0));
    }

    @Test
    public void testAllMatch() throws Exception {
        List<Integer> list = List.matrixList(2,6,8,10,12,16,156);
        assertTrue(list.allMatch(x -> x % 2 == 0));
    }

    @Test
    public void testFilter() throws Exception {
        List<Integer> originList = List.matrixList(1, 2, 3, 4, 5, 6);
        List<Integer> filteredList = originList.filter(x -> x % 2 == 0);
        assertEquals(filteredList, List.matrixList(2,4,6));
    }

    @Test
    public void testFind() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4, 5, 13);
        Optional<Integer> result = list.find(x -> x == 5);
        assertTrue(result.isPresent());
        assertEquals(result.get(), Integer.valueOf(5));
    }
}