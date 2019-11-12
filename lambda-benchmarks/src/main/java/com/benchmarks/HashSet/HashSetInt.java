package com.benchmarks.HashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import org.openjdk.jmh.annotations.*;

public class HashSetInt {
    
    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000000"})
        public int N;
        public int target;
        public HashSet<Integer> data;
        public HashSet<Integer> contains;
        public HashSet<Integer> filter;
        public ArrayList<Integer> range; 

        @Setup(Level.Trial)
        public void setupData() {
            data = new HashSet<Integer>(N);
            for (int i = 1; i <= N; i++) {
                data.add(i);
            }

            range = new ArrayList<Integer>(N);
            for (int i = 1; i <= N; i++) {
                range.add(i);
            }

            var max = N;
            var min = -N;
            var rnd = new Random();
            
            contains = new HashSet<Integer>(N);
            target = rnd.nextInt(max - min) - min;
    
            for (int i = 1; i <= N; i++) {
                contains.add(rnd.nextInt(max - min) - min);
            }

            int maxf = N;
            int minf = -N;
    
            filter = new HashSet<Integer>(N);
            for (int i = 1; i <= N; i++) {
                data.add(rnd.nextInt(maxf - minf) - minf);
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

        while(iter.hasNext())
        {
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
    public HashSet<Integer> lambdaPopulate(Bench b) {
        return b.range.stream().map(i -> i + i).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> loopPopulate(Bench b) {
        var result = new HashSet<Integer>(b.range.size());

        for (int i = 0; i < b.range.size(); i++) {
            var value = b.range.get(i);
            result.add(value + value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> iteratorPopulate(Bench b) {
        var result = new HashSet<Integer>(b.range.size());

        for (var value : b.range) {
            result.add(value + value);
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

        while(iter.hasNext()) {
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

        while(iter.hasNext()) {
            var value = iter.next();
            if(value == b.target) return true;
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean iteratorContains(Bench b) {
        for (var value : b.contains) {
            if(value == b.target) return true;
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> lambdaFilter(Bench b) {
        return b.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> loopFilter(Bench b) {
        var result = new HashSet<Integer>();
        var iter = b.filter.iterator();

        while(iter.hasNext()) {
            var value = iter.next();
            if(value >= 0) result.add(value); 
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> iteratorFilter(Bench b) {
        var result = new HashSet<Integer>();

        for (var value : b.filter) {
            if(value >= 0) result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> lambdaCopy(Bench b) {
        return b.data.stream().map(n -> n).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> loopCopy(Bench b) {
        var result = new HashSet<Integer>(b.data.size());
        var iter = b.data.iterator();

        while(iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> iteratorCopy(Bench b) {
        var result = new HashSet<Integer>(b.data.size());
        for (var value : b.data) {
            result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> lambdaMap(Bench b) {
        return b.data.stream().map(n -> n * n).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashSet<Integer> loopMap(Bench b) {
        var result = new HashSet<Integer>(b.data.size());
        var iter = b.data.iterator();

        while(iter.hasNext()) {
            var value = iter.next();
            result.add(value * value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS) 
    public HashSet<Integer> iteratorMap(Bench b) {
        var result = new HashSet<Integer>(b.data.size());
        for (var value : b.data) {
            result.add(value * value);
        }

        return result;
    }
}
