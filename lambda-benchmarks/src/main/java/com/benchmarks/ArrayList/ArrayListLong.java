package com.benchmarks.ArrayList;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;
import org.openjdk.jmh.annotations.*;

public class ArrayListLong {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000"})
        public int N;
        public long target;
        public ArrayList<Long> data;
        public ArrayList<Long> contains;
        public ArrayList<Long> filtering;

        @Setup(Level.Trial)
        public void setupData() {
            var rnd = new Random();
            int min = -N;

            data = new ArrayList<Long>(N);
            contains = new ArrayList<Long>(N);
            filtering = new ArrayList<Long>(N);
            target = (long) rnd.nextInt(101) * N;

            for (int i = 1; i <= N; i++) {
                data.add((long) (i * N));
            
            }
    
            for (int i = 1; i <= N; i++) {
                contains.add((long) rnd.nextInt(101) * N);
            }

            for (int i = 1; i <= N; i++) {
                long e = (long) rnd.nextInt(N - min) - min;
                filtering.add(e);
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

        for (int i = 0; i < b.data.size(); i++) {
            total += b.data.get(i);
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
    public ArrayList<Long> lambdaPopulate(Bench b) {
        var rnd = new Random();
        return Stream.iterate(rnd.nextLong(), i -> i + rnd.nextLong()).limit(b.data.size())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> loopPopulate(Bench b) {
        var result = new ArrayList<Long>(b.data.size());
        var rnd = new Random();

        for (int i = 0; i < b.data.size(); i++) {
            result.add(b.data.get(i) + rnd.nextLong());
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> iteratorPopulate(Bench b) {
        var result = new ArrayList<Long>(b.data.size());
        var rnd = new Random();

        for (var value : b.data) {
            result.add(value + rnd.nextLong());
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

        for (int i = 0; i < b.data.size(); i++) {
            if (b.data.get(i) > 0) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long iteratorIterate(Bench b) {
        long count = 0L;

        for (var value : b.data) {
            if (value > 0) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean lambdaContains(Bench b) {
        return b.contains.stream().anyMatch(n -> n == b.target);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean loopContains(Bench b) {
        for (int i = 0; i < b.contains.size(); i++) {
            if (b.contains.get(i) == b.target)
                return true;
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
    public ArrayList<Long> lambdaFilter(Bench b) {
        return b.filtering.stream().filter(n -> n >= 0).collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> loopFilter(Bench b) {
        var result = new ArrayList<Long>();

        for (int i = 0; i < b.filtering.size(); i++) {
            if (b.filtering.get(i) >= 0) {
                result.add(b.filtering.get(i));
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> iteratorFilter(Bench b) {
        var result = new ArrayList<Long>();

        for (var value : b.filtering) {
            if (value >= 0) {
                result.add(value);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> lambdaCopy(Bench b) {
        return b.data.stream().map(n -> n).collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> loopCopy(Bench b) {
        var copy = new ArrayList<Long>(b.data.size());

        for (int i = 0; i < b.data.size(); i++) {
            copy.add(b.data.get(i));
        }

        return copy;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> iteratorCopy(Bench b) {
        var copy = new ArrayList<Long>(b.data.size());

        for (var value : b.data) {
            copy.add(value);
        }

        return copy;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> lambdaMap(Bench b) {
        return b.data.stream().map(n -> n * b.N).collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> loopMap(Bench b) {
        var result = new ArrayList<Long>(b.data.size());

        for (int i = 0; i < b.data.size(); i++) {
            result.add(b.data.get(i) * b.N);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Long> iteratorMap(Bench b) {
        var result = new ArrayList<Long>(b.data.size());

        for (var value : b.data) {
            result.add(value * b.N);
        }

        return result;
    }
}