package com.pds.list;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by alexander on 10.05.14.
 */
@State(Scope.Thread)
public class ListBenchmarks {

    private static final String ARRAY_LIST = "ArrayList";
    private static final String LINKED_LIST = "LinkedList";
    private static final String PDS_LINKED_LIST = "PdsLinkedList";

    private static final int LIST_SIZE = 5_000_000;

    private Iterable<Integer> intList;
    private final Random random = new Random();

    @Param({ARRAY_LIST, LINKED_LIST, PDS_LINKED_LIST})
    private String listType;

    @Setup
    public void setup(){
        intList = create(listType);
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int traverseTest(){
        int sum = 0;
        for (Integer i : intList) {
            sum += i;
        }
        return sum;
    }

    private Iterable<Integer> create(String listType){
        switch (listType) {
            case ARRAY_LIST : return createArrayList();
            case LINKED_LIST : return createLinkedList();
            case PDS_LINKED_LIST : return createPdsLinkedList();
            default:
                throw new IllegalArgumentException();
        }
    }

    private Iterable<Integer> createPdsLinkedList() {
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

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*"+ListBenchmarks.class.getSimpleName() + ".*")
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
