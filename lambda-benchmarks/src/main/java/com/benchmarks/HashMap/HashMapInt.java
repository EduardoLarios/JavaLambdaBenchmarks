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

public class HashMapInt {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static int target;
        public static HashMap<Integer, Integer> data;
        public static HashMap<Integer, Integer> contains;
        public static ArrayList<Integer> range;

        
        @Setup(Level.Trial)
        public void setupData() {
            data = new HashMap<Integer, Integer>(N);
            for (int i = 1; i <= N; i++) {
                data.put(i, i * 10);
            }
        }

        @Setup(Level.Trial)
        public void setupRange() {
            range = new ArrayList<Integer>(N);
            for (int i = 1; i <= N; i++) {
                range.add(i);
            }
        }

        @Setup(Level.Trial)
        public void setupContains() {
            var rnd = new Random();
            int max = N;
            int min = -N;

            contains = new HashMap<Integer, Integer>(N);
            target = rnd.nextInt(max - min) - min;

            for (int i = 1; i <= N; i++) {
                contains.put(i, rnd.nextInt(max - min) - min);
            }
        }

    }

    public static class Reduce {

        @Benchmark
        public int lambdaReduce() {
            return Bench.data.values().stream().reduce(0, Integer::sum);
        }

        @Benchmark
        public int loopReduce() {
            int total = 0;
            var iter = Bench.data.values().iterator();

            while(iter.hasNext()) {
                total += iter.next();
            }

            return total;
        }

        @Benchmark
        public int iteratorReduce() {
            int total = 0;
            for (var value : Bench.data.values()) {
                total += value;
            }

            return total;
        }
    }

    public static class Populate {
        
        @Benchmark
        public HashMap<Integer, Integer> lambdaPopulate() {
            return new HashMap<Integer, Integer>(Bench.range.stream().collect(Collectors.toMap(k -> k, v -> v * 5)));
        }

        @Benchmark
        public HashMap<Integer, Integer> loopPopulate() {
            var map = new HashMap<Integer, Integer>(Bench.N);

            for (int i = 1; i <= Bench.N; i++) {
                map.put(i, i * 5);
            }

            return map;
        }

        @Benchmark
        public HashMap<Integer, Integer> iteratorPopulate() {
            var map = new HashMap<Integer, Integer>(Bench.N);

            for (var value : Bench.range) {
                map.put(value, value * 5);
            }

            return map;
        }
    }

    public static class Iterate {
        
        @Benchmark
        public int lambdaIterate() {
            return (int) Bench.data.values().stream().filter(n -> n < Integer.MAX_VALUE).count();
        }

        @Benchmark
        public int loopIterate() {
            int count = 0;
            var iter = Bench.data.values().iterator();

            while(iter.hasNext()) {
                var value = iter.next();
                if(value < Integer.MAX_VALUE) {
                    count++;
                }
            }

            return count;
        }

        @Benchmark
        public int iteratorIterate() {
            int count = 0;

            for (var value : Bench.data.values()) {
                if(value < Integer.MAX_VALUE) {
                    count++;
                }
            }

            return count;
        }
    }

    public static class Contains {

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
        public HashMap<Integer, Integer> lambdaFilter() {
            return new HashMap<Integer, Integer>(Bench.data.keySet().stream().filter(k -> k % 2 == 0).collect(Collectors.toMap(k -> k, v -> v * 10)));
        }

        @Benchmark
        public HashMap<Integer, Integer> loopFilter() {
            var iter = Bench.data.keySet().iterator();
            var result = new HashMap<Integer, Integer>();

            while(iter.hasNext()) {
                var key = iter.next();
                if(key % 2 == 0) {
                    result.put(key, key * 10);
                }
            }

            return result;
        }

        @Benchmark
        public HashMap<Integer, Integer> iteratorFilter() {
            var result = new HashMap<Integer, Integer>();
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
        public HashMap<Integer, Integer> lambdaCopy() {
            var map = Bench.data;
            return new HashMap<Integer, Integer>(map.keySet().stream().collect(Collectors.toMap(k -> k, k -> map.get(k))));
        }

        @Benchmark
        public HashMap<Integer, Integer> loopCopy() {
            var result = new HashMap<Integer, Integer>(Bench.data.size());
            var iter = Bench.data.keySet().iterator();

            while(iter.hasNext()) {
                var key = iter.next();
                result.put(key, Bench.data.get(key));
            }

            return result;
        }

        @Benchmark
        public HashMap<Integer, Integer> iteratorCopy() {
            var result = new HashMap<Integer, Integer>(Bench.data.size());
            for (var key : Bench.data.keySet()) {
                result.put(key, Bench.data.get(key));
            }

            return result;
        }
    }

    public static class Map {

        @Benchmark
        public HashMap<Integer, Integer> lambdaMap() {
            var map = Bench.data;
            return new HashMap<Integer, Integer>(map.keySet().stream().collect(Collectors.toMap(k -> k * 10, k -> map.get(k) * 10)));
        }

        @Benchmark
        public HashMap<Integer, Integer> loopMap() {
            var result = new HashMap<Integer, Integer>(Bench.data.size());
            var iter = Bench.data.keySet().iterator();

            while(iter.hasNext()) {
                var key = iter.next();
                var value = Bench.data.get(key);

                result.put(key * 10, value * 10);
            }

            return result;
        }

        @Benchmark
        public HashMap<Integer, Integer> iteratorMap() {
            var result = new HashMap<Integer, Integer>(Bench.data.size());
            for (var key : Bench.data.keySet()) {
                var value = Bench.data.get(key);
                result.put(key * 10, value * 10);
            }

            return result;
        }
    }
}
