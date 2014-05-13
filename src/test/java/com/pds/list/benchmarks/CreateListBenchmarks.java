package com.pds.list.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static com.pds.list.benchmarks.ListNames.*;

@State(Scope.Thread)
public class CreateListBenchmarks {

    private static final int LIST_SIZE = 5_000_000;
    private final IntListCreator listCreator = new IntListCreator(LIST_SIZE);

    private Iterable<Integer> intList;

    @Param({ARRAY_LIST, LINKED_LIST, PDS_LINKED_LIST})
    private String listType;

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Iterable<Integer> createListTest(){
        return listCreator.create(listType);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*"+CreateListBenchmarks.class.getSimpleName() + ".*")
                .warmupIterations(30)
                .measurementIterations(30)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
