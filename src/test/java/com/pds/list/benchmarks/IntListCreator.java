package com.pds.list.benchmarks;

import com.pds.list.List;

import java.util.ArrayList;
import java.util.Random;

import static com.pds.list.benchmarks.ListNames.ARRAY_LIST;
import static com.pds.list.benchmarks.ListNames.LINKED_LIST;
import static com.pds.list.benchmarks.ListNames.PDS_LINKED_LIST;

/**
 * Created by alexander on 10.05.14.
 */
public class IntListCreator {

    private final int LIST_SIZE;
    private final Random random;

    public IntListCreator(int list_size) {
        LIST_SIZE = list_size;
        random = new Random();
    }

    public Iterable<Integer> create(String listType){
        switch (listType) {
            case ARRAY_LIST : return createArrayList();
            case LINKED_LIST : return createLinkedList();
            case PDS_LINKED_LIST : return createPdsLinkedList();
            default:
                throw new IllegalArgumentException();
        }
    }

    private List<Integer> createPdsLinkedList() {
        List<Integer> ints = List.empty();
        for (int i = 0; i < LIST_SIZE; i++) {
            ints = ints.add(random.nextInt());
        }
        return ints;
    }

    private java.util.LinkedList<Integer> createLinkedList() {
        java.util.LinkedList<Integer> ints = new java.util.LinkedList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            ints.add(random.nextInt());
        }
        return ints;
    }

    private ArrayList<Integer> createArrayList() {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            ints.add(random.nextInt());
        }
        return ints;
    }
}
