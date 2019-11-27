package com.benchmarks.LinkedList;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import org.openjdk.jmh.annotations.*;

public class LinkedListLong {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"100","1000","10000","100000","1000000"})
        public int N;
        public long target;
        public LinkedList<Long> data;
        public LinkedList<Long> contains;
        public LinkedList<Long> filter;

        @Setup(Level.Trial)
        public void setupData() {
            var rnd = new Random();
            data = new LinkedList<Long>();

            for (int i = 1; i <= N; i++) {
                data.add((long) i * N);
            }
            
            contains = new LinkedList<Long>();
            int max = 101;
            int min = 1;
    
            target = (long) rnd.nextInt(max - min) - min;
    
            for (int i = 1; i <= N; i++) {
                contains.add(N * (long) rnd.nextInt(max - min) - min);
            }
            
            filter = new LinkedList<Long>();
            int maxf = N;
            int minf = -N;
    
            for (int i = 1; i <= N; i++) {
                data.add((long) rnd.nextInt(maxf - minf) - minf);
            }
        }
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long lambdaReduce(Bench b) {
        return b.data.stream().reduce(0L, Long::sum);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long loopReduce(Bench b) {
        long total = 0;
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            total += iter.next();
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long iteratorReduce(Bench b) {
        long total = 0;
        for (var value : b.data) {
            total += value;
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> lambdaPopulate(Bench b) {
        var rnd = new Random();
        return Stream.iterate(rnd.nextLong(), i -> i + rnd.nextLong()).limit(b.data.size())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> loopPopulate(Bench b) {
        var result = new LinkedList<Long>();
        var rnd = new Random();

        for (int i = 0; i < b.data.size(); i++) {
            result.add(i + rnd.nextLong());
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> iteratorPopulate(Bench b) {
        var result = new LinkedList<Long>();
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
        long count = 0;
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            if (iter.next() > 0)
                count++;
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public long iteratorIterate(Bench b) {
        long count = 0;
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
            var curr = iter.next();
            if (curr == b.target) {
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
    public LinkedList<Long> lambdaFilter(Bench b) {
        return b.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> loopFilter(Bench b) {
        var result = new LinkedList<Long>();
        var iter = b.filter.iterator();

        while (iter.hasNext()) {
            var curr = iter.next();
            if (curr >= 0)
                result.add(curr);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> iteratorFilter(Bench b) {
        var result = new LinkedList<Long>();
        for (var value : b.filter) {
            if (value >= 0)
                result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> lambdaCopy(Bench b) {
        return b.data.stream().map(n -> n).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> loopCopy(Bench b) {
        var result = new LinkedList<Long>();
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> iteratorCopy(Bench b) {
        var result = new LinkedList<Long>();
        for (var value : b.data) {
            result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> lambdaMap(Bench b) {
        return b.data.stream().map(n -> n * 5).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> loopMap(Bench b) {
        var result = new LinkedList<Long>();
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            var curr = iter.next();
            result.add(curr * 5);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Long> iteratorMap(Bench b) {
        var result = new LinkedList<Long>();
        for (var value : b.data) {
            result.add(value * 5);
        }

        return result;
    }
}
