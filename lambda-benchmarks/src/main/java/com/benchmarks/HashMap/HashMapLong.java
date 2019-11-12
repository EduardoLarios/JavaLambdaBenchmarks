package com.benchmarks.HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.*;

public class HashMapLong {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000000"})
        public int N;
        public long target;
        public HashMap<Long, Long> data;
        public HashMap<Long, Long> contains;
        public ArrayList<Long> range;

        @Setup(Level.Trial)
        public void setupData() {
            data = new HashMap<Long, Long>(N);
            for (long i = 1; i <= N; i++) {
                data.put(i * 10, i * N);
            }

            range = new ArrayList<Long>(N);
            for (long i = 1; i <= N; i++) {
                range.add(i);
            }

            var rnd = new Random();
            int max = N;
            int min = -N;
    
            contains = new HashMap<Long, Long>();
            target = rnd.nextInt(max - min) - min;
    
            for (long i = 1; i <= N; i++) {
                contains.put(i, (long)rnd.nextInt(max - min) - min);
            }
        }
    }
    
    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public long lambdaReduce(Bench b) {
        return b.data.values().stream().reduce(0L, Long::sum);
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public long loopReduce(Bench b) {
        long total = 0;
        var iter = b.data.values().iterator();

        while(iter.hasNext()) {
            total += iter.next();
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public long iteratorReduce(Bench b) {
        long total = 0;
        for (var value : b.data.values()) {
            total += value;
        }

        return total;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> lambdaPopulate(Bench b) {
        return new HashMap<Long, Long>(b.range.stream().collect(Collectors.toMap(k -> k, v -> v * 5)));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> loopPopulate(Bench b) {
        var map = new HashMap<Long, Long>(b.N);

        for (long i = 0; i < b.range.size(); i++) {
            map.put(i, i * 5);
        }

        return map;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> iteratorPopulate(Bench b) {
        var map = new HashMap<Long, Long>(b.N);

        for (var value : b.range) {
            map.put(value, value * 5);
        }

        return map;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public long lambdaIterate(Bench b) {
        return b.data.values().stream().filter(n -> n < Long.MAX_VALUE).count();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public long loopIterate(Bench b) {
        long count = 0;
        var iter = b.data.values().iterator();

        while(iter.hasNext()) {
            if(iter.next() < Long.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public long iteratorIterate(Bench b) {
        long count = 0;
        for (var value : b.data.values()) {
            if(value < Long.MAX_VALUE) {
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
    public HashMap<Long, Long> lambdaFilter(Bench b) {
        return new HashMap<Long, Long>(b.data.keySet().stream().filter(k -> k % 2 == 0).collect(Collectors.toMap(k -> k, v -> v * 10)));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> loopFilter(Bench b) {
        var iter = b.data.keySet().iterator();
        var result = new HashMap<Long, Long>();

        while(iter.hasNext()) {
            var key = iter.next();
            if(key % 2 == 0) {
                result.put(key, key * 10);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> iteratorFilter(Bench b) {
        var result = new HashMap<Long, Long>();
        for (var key : b.data.keySet()) {
            if(key % 2 == 0) {
                result.put(key, key * 10);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> lambdaCopy(Bench b) {
        var map = b.data;
        return new HashMap<Long, Long>(map.keySet().stream().collect(Collectors.toMap(k -> k, k -> map.get(k))));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> loopCopy(Bench b) {
        var result = new HashMap<Long, Long>(b.data.size());
        var iter = b.data.keySet().iterator();

        while(iter.hasNext()) {
            var key = iter.next();
            result.put(key, b.data.get(key));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> iteratorCopy(Bench b) {
        var result = new HashMap<Long, Long>(b.data.size());
        for (var key : b.data.keySet()) {
            result.put(key, b.data.get(key));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> lambdaMap(Bench b) {
        var map = b.data;
        return new HashMap<Long, Long>(map.keySet().stream().collect(Collectors.toMap(k -> k * 10, k -> map.get(k) * 10)));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> loopMap(Bench b) {
        var result = new HashMap<Long, Long>(b.data.size());
        var iter = b.data.keySet().iterator();

        while(iter.hasNext()) {
            var key = iter.next();
            var value = b.data.get(key);

            result.put(key * 10, value * 10);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public HashMap<Long, Long> iteratorMap(Bench b) {
        var result = new HashMap<Long, Long>(b.data.size());
        for (var key : b.data.keySet()) {
            var value = b.data.get(key);
            result.put(key * 10, value * 10);
        }

        return result;
    }
}
