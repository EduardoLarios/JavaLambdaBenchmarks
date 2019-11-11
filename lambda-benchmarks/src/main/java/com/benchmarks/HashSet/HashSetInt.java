package com.benchmarks.HashSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class HashSetInt {
    
    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static int target;
        public static HashSet<Integer> data;
        public static HashSet<Integer> contains;
        public static HashSet<Integer> filter;
        public static ArrayList<Integer> range; 

        @Setup(Level.Trial)
        public void setupData() {
            data = new HashSet<Integer>(N);
            for (int i = 1; i <= N; i++) {
                data.add(i);
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
            var max = N;
            var min = -N;
            var rnd = new Random();
            
            contains = new HashSet<Integer>(N);
            target = rnd.nextInt(max - min) - min;

            for (int i = 1; i <= N; i++) {
                contains.add(rnd.nextInt(max - min) - min);
            }
        }

        @Setup(Level.Trial)
        public void setupFilter() {
            var rnd = new Random();
            int max = N;
            int min = -N;

            filter = new HashSet<Integer>(N);
            for (int i = 1; i <= N; i++) {
                data.add(rnd.nextInt(max - min) - min);
            }
        }
    }

    public static class Reduce {

        @Benchmark
        public int lambdaReduce() {
            return Bench.data.stream().reduce(0, Integer::sum);
        }

        @Benchmark
        public int loopReduce() {
            int total = 0;
            var iter = Bench.data.iterator();

            while(iter.hasNext())
            {
                total += iter.next();
            }

            return total;
        }

        @Benchmark
        public int iteratorReduce() {
            int total = 0;
            for (var value : Bench.data) {
                total += value;
            }

            return total;
        }
    }

    public static class Populate {

        @Benchmark
        public HashSet<Integer> lambdaPopulate() {
            return Bench.range.stream().map(i -> i + i).collect(Collectors.toCollection(HashSet::new));
        }

        @Benchmark
        public HashSet<Integer> loopPopulate() {
            var result = new HashSet<Integer>(Bench.range.size());

            for (int i = 1; i <= Bench.range.size(); i++) {
                var value = Bench.range.get(i);
                result.add(value + value);
            }

            return result;
        }

        @Benchmark
        public HashSet<Integer> iteratorPopulate() {
            var result = new HashSet<Integer>(Bench.range.size());

            for (var value : Bench.range) {
                result.add(value + value);
            }

            return result;
        }
    }

    public static class Iterate {

        @Benchmark
        public int lambdaIterate() {
            return (int) Bench.data.stream().filter(n -> n > 0).count();
        }

        @Benchmark
        public int loopIterate() {
            int count = 0;
            var iter = Bench.data.iterator();

            while(iter.hasNext()) {
                if (iter.next() > 0)
                    count++;
            }

            return count;
        }

        @Benchmark
        public int iteratorIterate() {
            int count = 0;
            for (var value : Bench.data) {
                if (value > 0)
                    count++;
            }

            return count;
        }
    }

    public static class Contains {

        @Benchmark
        public boolean lambdaContains() {
            return Bench.contains.stream().anyMatch(n -> n == Bench.target);
        }        

        @Benchmark
        public boolean loopContains() {
            var iter = Bench.contains.iterator();

            while(iter.hasNext()) {
                var value = iter.next();
                if(value == Bench.target) return true;
            }

            return false;
        }

        @Benchmark
        public boolean iteratorContains() {
            for (var value : Bench.contains) {
                if(value == Bench.target) return true;
            }

            return false;
        }
    }

    public static class Filter {

        @Benchmark
        public HashSet<Integer> lambdaFilter() {
            return Bench.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(HashSet::new));
        }

        @Benchmark
        public HashSet<Integer> loopFilter() {
            var result = new HashSet<Integer>();
            var iter = Bench.filter.iterator();

            while(iter.hasNext()) {
                var value = iter.next();
                if(value >= 0) result.add(value); 
            }

            return result;
        }

        @Benchmark
        public HashSet<Integer> iteratorFilter() {
            var result = new HashSet<Integer>();

            for (var value : Bench.filter) {
                if(value >= 0) result.add(value);
            }

            return result;
        }
    }

    public static class Copy {

        @Benchmark
        public HashSet<Integer> lambdaCopy() {
            return Bench.data.stream().map(n -> n).collect(Collectors.toCollection(HashSet::new));
        }

        @Benchmark
        public HashSet<Integer> loopCopy() {
            var result = new HashSet<Integer>(Bench.data.size());
            var iter = Bench.data.iterator();

            while(iter.hasNext()) {
                result.add(iter.next());
            }

            return result;
        }

        @Benchmark
        public HashSet<Integer> iteratorCopy() {
            var result = new HashSet<Integer>(Bench.data.size());
            for (var value : Bench.data) {
                result.add(value);
            }

            return result;
        }
    }

    public static class Map {

        @Benchmark
        public HashSet<Integer> lambdaMap() {
            return Bench.data.stream().map(n -> n * n).collect(Collectors.toCollection(HashSet::new));
        }

        @Benchmark
        public HashSet<Integer> loopMap() {
            var result = new HashSet<Integer>(Bench.data.size());
            var iter = Bench.data.iterator();

            while(iter.hasNext()) {
                var value = iter.next();
                result.add(value * value);
            }

            return result;
        }

        @Benchmark 
        public HashSet<Integer> iteratorMap() {
            var result = new HashSet<Integer>(Bench.data.size());
            for (var value : Bench.data) {
                result.add(value * value);
            }

            return result;
        }
    }
}
