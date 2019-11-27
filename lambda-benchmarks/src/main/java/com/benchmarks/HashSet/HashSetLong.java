package com.benchmarks.HashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import org.openjdk.jmh.annotations.*;

public class HashSetLong {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"100","1000","10000","100000","1000000"})
        public int N;
        public long target;
        public HashSet<Long> data;
        public HashSet<Long> contains;
        public HashSet<Long> filter;
        public ArrayList<Long> range;

        @Setup(Level.Trial)
        public void setupData() {
            data = new HashSet<Long>(N);
            for (int i = 1; i <= N; i++) {
                data.add((long) (i * N));
            }

            range = new ArrayList<Long>(N);
            for (int i = 1; i <= N; i++) {
                data.add((long) i);
            }

            var rnd = new Random();
            target = (long) rnd.nextInt(101) * N;
            contains = new HashSet<Long>(N);
    
            for (int i = 1; i <= N; i++) {
                contains.add((long) rnd.nextInt(101) * N);
            }

            int max = N;
            int min = -N;
    
            filter = new HashSet<Long>(N);
            for (int i = 1; i <= N; i++) {
                long e = (long) rnd.nextInt(max - min) - min;
                data.add(e);
            }
        }
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long lambdaReduce(Bench b) {
        return b.data.stream().reduce(0L, Long::sum);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long loopReduce(Bench b) {
        long total = 0L;
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            total += iter.next();
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long iteratorReduce(Bench b) {
        long total = 0L;

        for (var value : b.data) {
            total += value;
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> lambdaPopulate(Bench b) {
        return b.range.stream().map(i -> i + i).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> loopPopulate(Bench b) {
        var result = new HashSet<Long>(b.range.size());

        for (int i = 0; i < b.range.size(); i++) {
            var value = b.range.get(i);
            result.add(value + value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> iteratorPopulate(Bench b) {
        var result = new HashSet<Long>(b.range.size());

        for (var value : b.range) {
            result.add(value + value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long lambdaIterate(Bench b) {
        return b.data.stream().filter(n -> n > 0).count();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long loopIterate(Bench b) {
        long count = 0L;
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            if (iter.next() > 0)
                count++;
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long iteratorIterate(Bench b) {
        long count = 0L;

        for (var value : b.data) {
            if (value > 0)
                count++;
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean lambdaContains(Bench b) {
        return b.contains.stream().anyMatch(n -> n == b.target);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean loopContains(Bench b) {
        var iter = b.contains.iterator();

        while (iter.hasNext()) {
            var value = iter.next();
            if (value == b.target) {
                return true;
            }
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean iteratorContains(Bench b) {
        for (var value : b.contains) {
            if (value == b.target)
                return true;
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> lambdaFilter(Bench b) {
        return b.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> loopFilter(Bench b) {
        var result = new HashSet<Long>();
        var iter = b.filter.iterator();

        while (iter.hasNext()) {
            var value = iter.next();
            if (value >= 0) {
                result.add(value);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> iteratorFilter(Bench b) {
        var result = new HashSet<Long>();

        for (var value : b.filter) {
            if (value >= 0)
                result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> lambdaCopy(Bench b) {
        return b.data.stream().map(n -> n).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> loopCopy(Bench b) {
        var copy = new HashSet<Long>(b.data.size());
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            copy.add(iter.next());
        }

        return copy;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> iteratorCopy(Bench b) {
        var copy = new HashSet<Long>(b.data.size());

        for (var value : b.data) {
            copy.add(value);
        }

        return copy;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> lambdaMap(Bench b) {
        return b.data.stream().map(n -> n * 10).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> loopMap(Bench b) {
        var result = new HashSet<Long>(b.data.size());
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            var value = iter.next();
            result.add(value * 10);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Long> iteratorMap(Bench b) {
        var result = new HashSet<Long>(b.data.size());

        for (var value : b.data) {
            result.add(value * 10);
        }

        return result;
    }
}
