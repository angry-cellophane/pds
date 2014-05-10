package com.pds.list;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static com.pds.list.ListNames.*;

@State(Scope.Thread)
public class TraverseListBenchmarks {

    private static final int LIST_SIZE = 5_000_000;
    private final IntListCreator listCreator = new IntListCreator(LIST_SIZE);

    private Iterable<Integer> intList;

    @Param({ARRAY_LIST, LINKED_LIST, PDS_LINKED_LIST})
    private String listType;

    @Setup(Level.Trial)
    public void setup() {
        intList = listCreator.create(listType);
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

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*"+TraverseListBenchmarks.class.getSimpleName() + ".*")
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
