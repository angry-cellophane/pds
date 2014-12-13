package com.pds.list;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by alexander on 13.12.14.
 */
public class CommonListTest {

    @DataProvider
    public Object[][] int_range_1_to_5() {
        Integer[] range = {1, 2, 3, 4, 5};
        return new Object[][] {
                new Object[] {List.of(range)},
                new Object[] {List.matrixList(range)},
                new Object[] {List.cached(List.of(range))},
                new Object[] {List.cached(List.matrixList(range))}
        };
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testCreation(List<Integer> list){
        Iterator<Integer> it = list.iterator();
        for (int i = 1; i < 5; i++) {
            assertTrue(it.hasNext());
            Integer value = it.next();
            assertEquals((int)value, i);
        }
    }

    @DataProvider(name = "empty")
    public Object[][] emptyProvider() {
        return new Object[][] {
                new Object[] {List.empty()},
                new Object[] {List.emptyMatrixList()},
                new Object[] {List.cached(List.empty())},
                new Object[] {List.cached(List.emptyMatrixList())}
        };
    }

    @Test(dataProvider = "empty")
    public void testSizeEmptyList(List<Integer> list) throws Exception {
        assertTrue(list.isEmpty());
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testIsEmpty(List<Integer> list) throws Exception {
        assertFalse(list.isEmpty());
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testHead(List<Integer> list) throws Exception {
        assertEquals(list.head(), Integer.valueOf(1));
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testIterator(List<Integer> list) throws Exception {
        int count = 0;
        for (Iterator<Integer> it = list.iterator(); it.hasNext();) {
            Integer i = it.next();
            assertEquals(i, Integer.valueOf(count + 1));
            count ++;
        }
        assertEquals(count, list.size());
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testToArray(List<Integer> list) throws Exception {
        Integer[] ints = new Integer[] {1,2,3,4,5};
        assertEquals(list.toArray(), ints);
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testToArray1(List<Integer> list) throws Exception {
        Integer[] ints = new Integer[] {1,2,3,4,5};
        assertEquals(list.toArray(new Integer[list.size()]), ints);
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testAdd(List<Integer> list) throws Exception {
        assertEquals(list.size(),5);

        List<Integer> newList = list.add(0);
        assertEquals(newList.size(),6);
        assertEquals(newList.head(), Integer.valueOf(0));
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testMap(List<Integer> list) throws Exception {
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

    @Test(dataProvider = "int_range_1_to_5")
    public void testFlatMap(List<Integer> list) throws Exception {
        List<Integer> newList = list.flatMap(x -> List.matrixList(1, 2, 3));
        int i = 0;
        for (Integer i1 : newList) {
            assertEquals(i1, Integer.valueOf(++i % 4));
        }
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testFoldLeft(List<Integer> list) throws Exception {
        Integer totalSum = list.foldLeft(0, (sum, cur) -> sum + cur);
        assertEquals(totalSum, Integer.valueOf(15));
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testFoldRight(List<Integer> list) throws Exception {
        String total = list.foldLeft("", (sum, cur) -> sum + cur);
        assertEquals(total, "54321");
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testAnyMatch(List<Integer> list) throws Exception {
        assertTrue(list.anyMatch(x -> x % 2 == 0));
    }

    @DataProvider
    public Object[][] int_odd_random_range() {
        Integer[] range = new Integer[] {2,6,8,10,12,16,156};
        return new Object[][] {
                new Object[] {List.of(range)},
                new Object[] {List.matrixList(range)},
                new Object[] {List.cached(List.of(range))},
                new Object[] {List.cached(List.matrixList(range))}
        };
    }

    @Test(dataProvider = "int_odd_random_range")
    public void testAllMatch(List<Integer> list) throws Exception {
        assertTrue(list.allMatch(x -> x % 2 == 0));
    }

    @Test(dataProvider = "int_range_1_to_5")
    public void testFilter(List<Integer> originList) throws Exception {
        List<Integer> filteredList = originList.filter(x -> x % 2 != 0);
        assertEquals(filteredList, List.matrixList(1,3,5));
    }

    @Test
    public void testFind() throws Exception {
        List<Integer> list = List.matrixList(1, 2, 3, 4, 5, 13);
        Optional<Integer> result = list.find(x -> x == 5);
        assertTrue(result.isPresent());
        assertEquals(result.get(), Integer.valueOf(5));
    }

}
