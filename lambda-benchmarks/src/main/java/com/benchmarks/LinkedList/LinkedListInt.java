package com.benchmarks.LinkedList;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import org.openjdk.jmh.annotations.*;

public class LinkedListInt {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000000"})
        public int N;
        public int target;
        public LinkedList<Integer> data;
        public LinkedList<Integer> contains;
        public LinkedList<Integer> filter;

        @Setup(Level.Trial)
        public void setupData() {
            data = new LinkedList<Integer>();
            for (int i = 1; i <= N; i++) {
                data.add(i);
            }
            
            contains = new LinkedList<Integer>();
            var max = 101;
            var min = 1;
            var rnd = new Random();
    
            target = rnd.nextInt(max - min) - min;
    
            for (int i = 1; i <= N; i++) {
                contains.add(rnd.nextInt(max - min) - min);
            }
            
            filter = new LinkedList<Integer>();
            int maxf = N;
            int minf = -N;
    
            for (int i = 1; i <= N; i++) {
                filter.add(rnd.nextInt(maxf - minf) - minf);
            }
        }
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int lambdaReduce(Bench b) {
        return b.data.stream().reduce(0, Integer::sum);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int loopReduce(Bench b) {
        int total = 0;
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            total += iter.next();
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int iteratorReduce(Bench b) {
        int total = 0;
        for (var value : b.data) {
            total += value;
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> lambdaPopulate(Bench b) {
        var rnd = new Random();
        
        return Stream.iterate(rnd.nextInt(101), i -> i + rnd.nextInt(101)).limit(b.data.size())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> loopPopulate(Bench b) {
        var result = new LinkedList<Integer>();
        var rnd = new Random();

        for (int i = 0; i < b.data.size(); i++) {
            result.add(i + rnd.nextInt(101));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> iteratorPopulate(Bench b) {
        var result = new LinkedList<Integer>();
        var rnd = new Random();

        for (var value : b.data) {
            result.add(value + rnd.nextInt(101));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int lambdaIterate(Bench b) {
        return (int) b.data.stream().filter(n -> n > 0).count();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int loopIterate(Bench b) {
        int count = 0;
        var iter = b.data.iterator();

        while (iter.hasNext()) {
            if (iter.next() > 0)
                count++;
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int iteratorIterate(Bench b) {
        int count = 0;
        for (var value : b.data) {
            if (value > 0)
                count++;
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean lambdaContains(Bench b) {
        return b.contains.stream().anyMatch(n -> n == b.target);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
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

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean iteratorContains(Bench b) {
        for (var value : b.contains) {
            if (value == b.target)
                return true;
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> lambdaFilter(Bench b) {
        return b.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> loopFilter(Bench b) {
        var result = new LinkedList<Integer>();
        var iter = b.filter.iterator();

        while (iter.hasNext()) {
            var curr = iter.next();
            if (curr >= 0)
                result.add(curr);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> iteratorFilter(Bench b) {
        var result = new LinkedList<Integer>();
        for (var value : b.filter) {
            if (value >= 0)
                result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> lambdaCopy(Bench b) {
        return b.data.stream().map(n -> n).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> loopCopy(Bench b) {
        var result = new LinkedList<Integer>();
        var iter = b.filter.iterator();

        while (iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> iteratorCopy(Bench b) {
        var result = new LinkedList<Integer>();
        for (var value : b.filter) {
            result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> lambdaMap(Bench b) {
        return b.data.stream().map(n -> n * n).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> loopMap(Bench b) {
        var result = new LinkedList<Integer>();
        var iter = b.filter.iterator();

        while (iter.hasNext()) {
            var curr = iter.next();
            result.add(curr * curr);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public LinkedList<Integer> iteratorMap(Bench b) {
        var result = new LinkedList<Integer>();
        for (var value : b.filter) {
            result.add(value * value);
        }

        return result;
    }
}
