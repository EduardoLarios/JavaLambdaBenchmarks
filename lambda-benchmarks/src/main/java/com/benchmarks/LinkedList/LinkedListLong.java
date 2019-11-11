package com.benchmarks.LinkedList;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class LinkedListLong {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static long target;
        public static LinkedList<Long> data;
        public static LinkedList<Long> contains;
        public static LinkedList<Long> filter;

        @Setup(Level.Trial)
        public void setupData() {
            data = new LinkedList<Long>();

            for (int i = 1; i <= N; i++) {
                data.add((long) i * N);
            }
        }

        @Setup(Level.Trial)
        public void setupContains() {
            contains = new LinkedList<Long>();

            int max = 101;
            int min = 1;
            var rnd = new Random();

            target = (long) rnd.nextInt(max - min) - min;

            for (int i = 1; i <= N; i++) {
                contains.add(N * (long) rnd.nextInt(max - min) - min);
            }
        }

        @Setup(Level.Trial)
        public void setupFilter() {
            filter = new LinkedList<Long>();

            int max = N;
            int min = -N;
            var rnd = new Random();

            for (int i = 1; i <= N; i++) {
                data.add((long) rnd.nextInt(max - min) - min);
            }
        }

    }

    @Benchmark
    public long lambdaReduce() {
        return Bench.data.stream().reduce(0L, Long::sum);
    }

    @Benchmark
    public long loopReduce() {
        long total = 0;
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            total += iter.next();
        }

        return total;
    }

    @Benchmark
    public long iteratorReduce() {
        long total = 0;
        for (var value : Bench.data) {
            total += value;
        }

        return total;
    }

    @Benchmark
    public LinkedList<Long> lambdaPopulate() {
        var rnd = new Random();
        return Stream.iterate(rnd.nextLong(), i -> i + rnd.nextLong()).limit(Bench.data.size())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark
    public LinkedList<Long> loopPopulate() {
        var result = new LinkedList<Long>();
        var rnd = new Random();

        for (int i = 0; i < Bench.data.size(); i++) {
            result.add(i + rnd.nextLong());
        }

        return result;
    }

    @Benchmark
    public LinkedList<Long> iteratorPopulate() {
        var result = new LinkedList<Long>();
        var rnd = new Random();

        for (var value : Bench.data) {
            result.add(value + rnd.nextLong());
        }

        return result;
    }

    @Benchmark
    public long lambdaIterate() {
        return Bench.data.stream().filter(n -> n > 0).count();
    }

    @Benchmark
    public long loopIterate() {
        long count = 0;
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            if (iter.next() > 0)
                count++;
        }

        return count;
    }

    @Benchmark
    public long iteratorIterate() {
        long count = 0;
        for (var value : Bench.data) {
            if (value > 0)
                count++;
        }

        return count;
    }

    @Benchmark
    public boolean lambdaContains() {
        return Bench.contains.stream().anyMatch(n -> n == Bench.target);
    }

    @Benchmark
    public boolean loopContains() {
        var iter = Bench.contains.iterator();

        while (iter.hasNext()) {
            var curr = iter.next();
            if (curr == Bench.target) {
                return true;
            }
        }

        return false;
    }

    @Benchmark
    public boolean iteratorContains() {
        for (var value : Bench.contains) {
            if (value == Bench.target)
                return true;
        }

        return false;
    }

    @Benchmark
    public LinkedList<Long> lambdaFilter() {
        return Bench.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark
    public LinkedList<Long> loopFilter() {
        var result = new LinkedList<Long>();
        var iter = Bench.filter.iterator();

        while (iter.hasNext()) {
            var curr = iter.next();
            if (curr >= 0)
                result.add(curr);
        }

        return result;
    }

    @Benchmark
    public LinkedList<Long> iteratorFilter() {
        var result = new LinkedList<Long>();
        for (var value : Bench.filter) {
            if (value >= 0)
                result.add(value);
        }

        return result;
    }

    @Benchmark
    public LinkedList<Long> lambdaCopy() {
        return Bench.data.stream().map(n -> n).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark
    public LinkedList<Long> loopCopy() {
        var result = new LinkedList<Long>();
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    @Benchmark
    public LinkedList<Long> iteratorCopy() {
        var result = new LinkedList<Long>();
        for (var value : Bench.data) {
            result.add(value);
        }

        return result;
    }

    @Benchmark
    public LinkedList<Long> lambdaMap() {
        return Bench.data.stream().map(n -> n * 5).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark
    public LinkedList<Long> loopMap() {
        var result = new LinkedList<Long>();
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            var curr = iter.next();
            result.add(curr * 5);
        }

        return result;
    }

    @Benchmark
    public LinkedList<Long> iteratorMap() {
        var result = new LinkedList<Long>();
        for (var value : Bench.data) {
            result.add(value * 5);
        }

        return result;
    }
}
