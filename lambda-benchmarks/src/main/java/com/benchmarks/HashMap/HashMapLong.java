package com.benchmarks.HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class HashMapLong {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static long target;
        public static HashMap<Long, Long> data;
        public static HashMap<Long, Long> contains;
        public static ArrayList<Long> range;

        @Setup(Level.Trial)
        public void setupData() {
            data = new HashMap<Long, Long>(N);
            for (long i = 1; i <= N; i++) {
                data.put(i * 10, i * N);
            }
        }


        @Setup
        public void setupRange() {
            range = new ArrayList<Long>(N);
            for (long i = 1; i <= N; i++) {
                range.add(i);
            }
        }

        @Setup
        public void setupContains() {
            var rnd = new Random();
            int max = N;
            int min = -N;

            target = rnd.nextInt(max - min) - min;

            for (long i = 1; i <= N; i++) {
                contains.put(i, (long)rnd.nextInt(max - min) - min);
            }
        }
    }
    
    public static class Reduce {

        @Benchmark
        public long lambdaReduce() {
            return Bench.data.values().stream().reduce(0L, Long::sum);
        }

        @Benchmark
        public long loopReduce() {
            long total = 0;
            var iter = Bench.data.values().iterator();

            while(iter.hasNext()) {
                total += iter.next();
            }

            return total;
        }

        @Benchmark
        public long iteratorReduce() {
            long total = 0;
            for (var value : Bench.data.values()) {
                total += value;
            }

            return total;
        }
    }

    public static class Populate {

        @Benchmark
        public HashMap<Long, Long> lambdaPopulate() {
            return new HashMap<Long, Long>(Bench.range.stream().collect(Collectors.toMap(k -> k, v -> v * 5)));
        }

        @Benchmark
        public HashMap<Long, Long> loopPopulate() {
            var map = new HashMap<Long, Long>(Bench.N);

            for (long i = 0; i < Bench.range.size(); i++) {
                map.put(i, i * 5);
            }

            return map;
        }

        @Benchmark
        public HashMap<Long, Long> iteratorPopulate() {
            var map = new HashMap<Long, Long>(Bench.N);

            for (var value : Bench.range) {
                map.put(value, value * 5);
            }

            return map;
        }
    }

    public static class Iterate {

        @Benchmark
        public long lambdaIterate() {
            return Bench.data.values().stream().filter(n -> n < Long.MAX_VALUE).count();
        }

        @Benchmark
        public long loopIterate() {
            long count = 0;
            var iter = Bench.data.values().iterator();

            while(iter.hasNext()) {
                if(iter.next() < Long.MAX_VALUE) {
                    count++;
                }
            }

            return count;
        }

        @Benchmark
        public long iteratorIterate() {
            long count = 0;
            for (var value : Bench.data.values()) {
                if(value < Long.MAX_VALUE) {
                    count++;
                }
            }

            return count;
        }
    }

    public static class Contains {
        
        @Benchmark
        public boolean lambdaContains() {
            return Bench.contains.values().stream().anyMatch(n -> n == Bench.target);
        }

        @Benchmark
        public boolean loopContains() {
            var iter = Bench.contains.values().iterator();
            while(iter.hasNext()) {
                if(iter.next() == Bench.target) {
                    return true;
                }
            }

            return false;
        }

        @Benchmark
        public boolean iteratorContains() {
            for (var value : Bench.contains.values()) {
                if(value == Bench.target) {
                    return true;
                }
            }

            return false;
        }
    }

    public static class Filter {

        @Benchmark
        public HashMap<Long, Long> lambdaFilter() {
            return new HashMap<Long, Long>(Bench.data.keySet().stream().filter(k -> k % 2 == 0).collect(Collectors.toMap(k -> k, v -> v * 10)));
        }

        @Benchmark
        public HashMap<Long, Long> loopFilter() {
            var iter = Bench.data.keySet().iterator();
            var result = new HashMap<Long, Long>();

            while(iter.hasNext()) {
                var key = iter.next();
                if(key % 2 == 0) {
                    result.put(key, key * 10);
                }
            }

            return result;
        }

        @Benchmark
        public HashMap<Long, Long> iteratorFilter() {
            var result = new HashMap<Long, Long>();
            for (var key : Bench.data.keySet()) {
                if(key % 2 == 0) {
                    result.put(key, key * 10);
                }
            }

            return result;
        }
    }

    public static class Copy {

        @Benchmark
        public HashMap<Long, Long> lambdaCopy() {
            var map = Bench.data;
            return new HashMap<Long, Long>(map.keySet().stream().collect(Collectors.toMap(k -> k, k -> map.get(k))));
        }

        @Benchmark
        public HashMap<Long, Long> loopCopy() {
            var result = new HashMap<Long, Long>(Bench.data.size());
            var iter = Bench.data.keySet().iterator();

            while(iter.hasNext()) {
                var key = iter.next();
                result.put(key, Bench.data.get(key));
            }

            return result;
        }

        @Benchmark
        public HashMap<Long, Long> iteratorCopy() {
            var result = new HashMap<Long, Long>(Bench.data.size());
            for (var key : Bench.data.keySet()) {
                result.put(key, Bench.data.get(key));
            }

            return result;
        }
    }

    public static class Map {

        @Benchmark
        public HashMap<Long, Long> lambdaMap() {
            var map = Bench.data;
            return new HashMap<Long, Long>(map.keySet().stream().collect(Collectors.toMap(k -> k * 10, k -> map.get(k) * 10)));
        }

        @Benchmark
        public HashMap<Long, Long> loopMap() {
            var result = new HashMap<Long, Long>(Bench.data.size());
            var iter = Bench.data.keySet().iterator();

            while(iter.hasNext()) {
                var key = iter.next();
                var value = Bench.data.get(key);

                result.put(key * 10, value * 10);
            }

            return result;
        }

        @Benchmark
        public HashMap<Long, Long> iteratorMap() {
            var result = new HashMap<Long, Long>(Bench.data.size());
            for (var key : Bench.data.keySet()) {
                var value = Bench.data.get(key);
                result.put(key * 10, value * 10);
            }

            return result;
        }
    }
}
