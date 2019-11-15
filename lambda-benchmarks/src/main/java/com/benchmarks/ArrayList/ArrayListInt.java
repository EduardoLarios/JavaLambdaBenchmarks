package com.benchmarks.ArrayList;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import org.openjdk.jmh.annotations.*;

public class ArrayListInt {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000"})
        public int N;
        public int target;
        public ArrayList<Integer> data;
        public ArrayList<Integer> contains;
        public ArrayList<Integer> filter;
        
        @Setup(Level.Trial)
        public void setupData() {
            data = new ArrayList<Integer>(N);
            for (int i = 1; i <= N; i++) {
                data.add(i);
            }

            contains = new ArrayList<Integer>(N);
            var max = 101;
            var min = 1;
            var rnd = new Random();
    
            target = rnd.nextInt(max - min) - min;
    
            for (int i = 1; i <= N; i++) {
                contains.add(rnd.nextInt(max - min) - min);
            }

            filter = new ArrayList<Integer>(N);
            int minf = -N;
    
            for (int i = 1; i <= N; i++) {
                filter.add(rnd.nextInt(N - minf) - minf);
            }
        }
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int lambdaReduce(Bench b) {
        return b.data.stream().reduce(0, Integer::sum);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int loopReduce(Bench b) {
        int total = 0;
        for (int i = 0; i < b.data.size(); i++) {
            total += b.data.get(i);
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int iteratorReduce(Bench b) {
        int total = 0;
        for (var value : b.data) {
            total += value;
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> lambdaPopulate(Bench b) {
        var rnd = new Random();
        return Stream.iterate(rnd.nextInt(101), i -> i + rnd.nextInt(101)).limit(b.data.size())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> loopPopulate(Bench b) {
        var result = new ArrayList<Integer>(b.data.size());
        var rnd = new Random();

        for (int i = 0; i < b.data.size(); i++) {
            result.add(b.data.get(i) + rnd.nextInt(101));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> iteratorPopulate(Bench b) {
        var result = new ArrayList<Integer>(b.data.size());
        var rnd = new Random();

        for (var value : b.data) {
            result.add(value + rnd.nextInt(101));
        }

        return result;
    }
   
    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int lambdaIterate(Bench b) {
        return (int) b.data.stream().filter(n -> n > 0).count();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int loopIterate(Bench b) {
        int count = 0;
        for (int i = 0; i < b.data.size(); i++) {
            if (b.data.get(i) > 0)
                count++;
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int iteratorIterate(Bench b) {
        int count = 0;
        for (var value : b.data) {
            if (value > 0)
                ++count;
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
    public ArrayList<Integer> lambdaFilter(Bench b) {
        return b.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> loopFilter(Bench b) {
        var result = new ArrayList<Integer>();
        for (int i = 0; i < b.filter.size(); i++) {
            if (b.filter.get(i) >= 0)
                result.add(b.filter.get(i));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> iteratorFilter(Bench b) {
        var result = new ArrayList<Integer>();
        for (var value : b.data) {
            if (value >= 0)
                result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> lambdaCopy(Bench b) {
        return b.data.stream().map(n -> n).collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> loopCopy(Bench b) {
        var result = new ArrayList<Integer>(b.data.size());
        for (int i = 0; i < b.data.size(); i++) {
            result.add(b.data.get(i));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> iteratorCopy(Bench b) {
        var result = new ArrayList<Integer>(b.data.size());
        for (var value : b.data) {
            result.add(value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> lambdaMap(Bench b) {
        return b.data.stream().map(n -> n * n).collect(Collectors.toCollection(ArrayList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> loopMap(Bench b) {
        var result = new ArrayList<Integer>(b.data.size());
        for (int i = 0; i < b.data.size(); i++) {
            result.add(b.data.get(i) * b.data.get(i));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public ArrayList<Integer> iteratorMap(Bench b) {
        var result = new ArrayList<Integer>(b.data.size());
        for (var value : b.data) {
            result.add(value * value);
        }

        return result;
    }
}