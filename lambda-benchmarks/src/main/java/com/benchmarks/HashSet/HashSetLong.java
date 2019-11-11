package com.benchmarks.HashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class HashSetLong {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static long target;
        public static HashSet<Long> data;
        public static HashSet<Long> contains;
        public static HashSet<Long> filter;
        public static ArrayList<Long> range;

        @Setup(Level.Trial)
        public void setupData() {
            data = new HashSet<Long>(N);
            for (int i = 1; i <= N; i++) {
                data.add((long) (i * N));
            }
        }

        @Setup(Level.Trial)
        public void setupRange() {
            range = new ArrayList<Long>(N);
            for (int i = 1; i <= N; i++) {
                data.add((long) i);
            }
        }

        @Setup(Level.Trial)
        public void setupContains() {
            var rnd = new Random();
            target = (long) rnd.nextInt(101) * N;
            contains = new HashSet<Long>(N);

            for (int i = 1; i <= N; i++) {
                contains.add((long) rnd.nextInt(101) * N);
            }
        }

        @Setup(Level.Trial)
        public void setupFilter() {
            var rnd = new Random();
            int max = N;
            int min = -N;

            filter = new HashSet<Long>(N);
            for (int i = 1; i <= N; i++) {
                long e = (long) rnd.nextInt(max - min) - min;
                data.add(e);
            }
        }
    }

    @Benchmark
    public long lambdaReduce() {
        return Bench.data.stream().reduce(0L, Long::sum);
    }

    @Benchmark
    public long loopReduce() {
        long total = 0L;
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            total += iter.next();
        }

        return total;
    }

    @Benchmark
    public long iteratorReduce() {
        long total = 0L;

        for (var value : Bench.data) {
            total += value;
        }

        return total;
    }

    @Benchmark
    public HashSet<Long> lambdaPopulate() {
        return Bench.range.stream().map(i -> i + i).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark
    public HashSet<Long> loopPopulate() {
        var result = new HashSet<Long>(Bench.range.size());

        for (int i = 0; i < Bench.range.size(); i++) {
            var value = Bench.range.get(i);
            result.add(value + value);
        }

        return result;
    }

    @Benchmark
    public HashSet<Long> iteratorPopulate() {
        var result = new HashSet<Long>(Bench.range.size());

        for (var value : Bench.range) {
            result.add(value + value);
        }

        return result;
    }

    @Benchmark
    public long lambdaIterate() {
        return Bench.data.stream().filter(n -> n > 0).count();
    }

    @Benchmark
    public long loopIterate() {
        long count = 0L;
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            if (iter.next() > 0)
                count++;
        }

        return count;
    }

    @Benchmark
    public long iteratorIterate() {
        long count = 0L;

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
            var value = iter.next();
            if (value == Bench.target) {
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
    public HashSet<Long> lambdaFilter() {
        return Bench.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark
    public HashSet<Long> loopFilter() {
        var result = new HashSet<Long>();
        var iter = Bench.filter.iterator();

        while (iter.hasNext()) {
            var value = iter.next();
            if (value >= 0) {
                result.add(value);
            }
        }

        return result;
    }

    @Benchmark
    public HashSet<Long> iteratorFilter() {
        var result = new HashSet<Long>();

        for (var value : Bench.filter) {
            if (value >= 0)
                result.add(value);
        }

        return result;
    }

    @Benchmark
    public HashSet<Long> lambdaCopy() {
        return Bench.data.stream().map(n -> n).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark
    public HashSet<Long> loopCopy() {
        var copy = new HashSet<Long>(Bench.data.size());
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            copy.add(iter.next());
        }

        return copy;
    }

    @Benchmark
    public HashSet<Long> iteratorCopy() {
        var copy = new HashSet<Long>(Bench.data.size());

        for (var value : Bench.data) {
            copy.add(value);
        }

        return copy;
    }

    @Benchmark
    public HashSet<Long> lambdaMap() {
        return Bench.data.stream().map(n -> n * 10).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark
    public HashSet<Long> loopMap() {
        var result = new HashSet<Long>(Bench.data.size());
        var iter = Bench.data.iterator();

        while (iter.hasNext()) {
            var value = iter.next();
            result.add(value * 10);
        }

        return result;
    }

    @Benchmark
    public HashSet<Long> iteratorMap() {
        var result = new HashSet<Long>(Bench.data.size());

        for (var value : Bench.data) {
            result.add(value * 10);
        }

        return result;
    }
}
