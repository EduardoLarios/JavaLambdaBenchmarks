package com.benchmarks.HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.*;

public class HashMapInt {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000000"})
        public int N;
        public int target;
        public HashMap<Integer, Integer> data;
        public HashMap<Integer, Integer> contains;
        public ArrayList<Integer> range;

        
        @Setup(Level.Trial)
        public void setupData() {
            var rnd = new Random();
            int max = N;
            int min = -N;
            
            data = new HashMap<Integer, Integer>(N);
            range = new ArrayList<Integer>(N);
            contains = new HashMap<Integer, Integer>(N);
            target = rnd.nextInt(max - min) - min;
            
            for (int i = 1; i <= N; i++) {
                data.put(i, i * 10);
            }

            for (int i = 1; i <= N; i++) {
                range.add(i);
            }
            
    
            for (int i = 1; i <= N; i++) {
                contains.put(i, rnd.nextInt(max - min) - min);
            }
        }
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int lambdaReduce(Bench b) {
        return b.data.values().stream().reduce(0, Integer::sum);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int loopReduce(Bench b) {
        int total = 0;
        var iter = b.data.values().iterator();

        while(iter.hasNext()) {
            total += iter.next();
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int iteratorReduce(Bench b) {
        int total = 0;
        for (var value : b.data.values()) {
            total += value;
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> lambdaPopulate(Bench b) {
        return new HashMap<Integer, Integer>(b.range.stream().collect(Collectors.toMap(k -> k, v -> v * 5)));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> loopPopulate(Bench b) {
        var map = new HashMap<Integer, Integer>(b.N);

        for (int i = 1; i <= b.N; i++) {
            map.put(i, i * 5);
        }

        return map;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> iteratorPopulate(Bench b) {
        var map = new HashMap<Integer, Integer>(b.N);

        for (var value : b.range) {
            map.put(value, value * 5);
        }

        return map;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int lambdaIterate(Bench b) {
        return (int) b.data.values().stream().filter(n -> n < Integer.MAX_VALUE).count();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int loopIterate(Bench b) {
        int count = 0;
        var iter = b.data.values().iterator();

        while(iter.hasNext()) {
            var value = iter.next();
            if(value < Integer.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int iteratorIterate(Bench b) {
        int count = 0;

        for (var value : b.data.values()) {
            if(value < Integer.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean lambdaContains(Bench b) {
        return b.contains.values().stream().anyMatch(n -> n == b.target);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean loopContains(Bench b) {
        var iter = b.contains.values().iterator();

        while(iter.hasNext()) {
            if(iter.next() == b.target) {
                return true;
            }
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean iteratorContains(Bench b) {
        for (var value : b.contains.values()) {
            if(value == b.target) {
                return true;
            }
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> lambdaFilter(Bench b) {
        return new HashMap<Integer, Integer>(b.data.keySet().stream().filter(k -> k % 2 == 0).collect(Collectors.toMap(k -> k, v -> v * 10)));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> loopFilter(Bench b) {
        var iter = b.data.keySet().iterator();
        var result = new HashMap<Integer, Integer>();

        while(iter.hasNext()) {
            var key = iter.next();
            if(key % 2 == 0) {
                result.put(key, key * 10);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> iteratorFilter(Bench b) {
        var result = new HashMap<Integer, Integer>();
        for (var key : b.data.keySet()) {
            if(key % 2 == 0) {
                result.put(key, key * 10);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> lambdaCopy(Bench b) {
        var map = b.data;
        return new HashMap<Integer, Integer>(map.keySet().stream().collect(Collectors.toMap(k -> k, k -> map.get(k))));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> loopCopy(Bench b) {
        var result = new HashMap<Integer, Integer>(b.data.size());
        var iter = b.data.keySet().iterator();

        while(iter.hasNext()) {
            var key = iter.next();
            result.put(key, b.data.get(key));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> iteratorCopy(Bench b) {
        var result = new HashMap<Integer, Integer>(b.data.size());
        for (var key : b.data.keySet()) {
            result.put(key, b.data.get(key));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> lambdaMap(Bench b) {
        var map = b.data;
        return new HashMap<Integer, Integer>(map.keySet().stream().collect(Collectors.toMap(k -> k * 10, k -> map.get(k) * 10)));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> loopMap(Bench b) {
        var result = new HashMap<Integer, Integer>(b.data.size());
        var iter = b.data.keySet().iterator();

        while(iter.hasNext()) {
            var key = iter.next();
            var value = b.data.get(key);

            result.put(key * 10, value * 10);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Integer, Integer> iteratorMap(Bench b) {
        var result = new HashMap<Integer, Integer>(b.data.size());
        for (var key : b.data.keySet()) {
            var value = b.data.get(key);
            result.put(key * 10, value * 10);
        }

        return result;
    }
}
