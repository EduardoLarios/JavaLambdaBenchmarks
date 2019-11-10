package com.benchmarks.LinkedList;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class LinkedListInt {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static int target;
        public static LinkedList<Integer> data;
        public static LinkedList<Integer> contains;
        public static LinkedList<Integer> filter;

        @Setup(Level.Trial)
        public void setupData() {
            data = new LinkedList<Integer>();
            for (int i = 1; i <= N; i++) {
                data.add(i);
            }
        }

        @Setup(Level.Trial)
        public void setupContains() {
            contains = new LinkedList<Integer>();
            var max = 101;
            var min = 1;
            var rnd = new Random();

            target = rnd.nextInt(max - min) - min;

            for (int i = 1; i <= N; i++) {
                contains.add(rnd.nextInt(max - min) - min);
            }
        }

        @Setup(Level.Trial)
        public void setupFilter() {
            filter = new LinkedList<Integer>();
            var rnd = new Random();
            int max = N;
            int min = -N;

            for (int i = 1; i <= N; i++) {
                filter.add(rnd.nextInt(max - min) - min);
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

            while (iter.hasNext()) {
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
        public LinkedList<Integer> lambdaPopulate() {
            var rnd = new Random();
            
            return Stream.iterate(rnd.nextInt(101), i -> i + rnd.nextInt(101)).limit(Bench.data.size())
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        @Benchmark
        public LinkedList<Integer> loopPopulate() {
            var result = new LinkedList<Integer>();
            var rnd = new Random();

            for (int i = 0; i < Bench.data.size(); i++) {
                result.add(i + rnd.nextInt(101));
            }

            return result;
        }

        @Benchmark
        public LinkedList<Integer> iteratorPopulate() {
            var result = new LinkedList<Integer>();
            var rnd = new Random();

            for (var value : Bench.data) {
                result.add(value + rnd.nextInt(101));
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

            while (iter.hasNext()) {
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

            while (iter.hasNext()) {
                var curr = iter.next();
                if (curr == Bench.target) {
                    return true;
                }
            }

            return false;
        }

        @Benchmark
        public boolean iteratorContains() {
            for (var value : Bench.contains) {
                if (value == Bench.target)
                    return true;
            }

            return false;
        }
    }

    public static class Filter {

        @Benchmark
        public LinkedList<Integer> lambdaFilter() {
            return Bench.filter.stream().filter(n -> n >= 0).collect(Collectors.toCollection(LinkedList::new));
        }

        @Benchmark
        public LinkedList<Integer> loopFilter() {
            var result = new LinkedList<Integer>();
            var iter = Bench.filter.iterator();

            while (iter.hasNext()) {
                var curr = iter.next();
                if (curr >= 0)
                    result.add(curr);
            }

            return result;
        }

        @Benchmark
        public LinkedList<Integer> iteratorFilter() {
            var result = new LinkedList<Integer>();
            for (var value : Bench.filter) {
                if (value >= 0)
                    result.add(value);
            }

            return result;
        }
    }

    public static class Copy {

        @Benchmark
        public LinkedList<Integer> lambdaCopy() {
            return Bench.data.stream().map(n -> n).collect(Collectors.toCollection(LinkedList::new));
        }

        @Benchmark
        public LinkedList<Integer> loopCopy() {
            var result = new LinkedList<Integer>();
            var iter = Bench.filter.iterator();

            while (iter.hasNext()) {
                result.add(iter.next());
            }

            return result;
        }

        @Benchmark
        public LinkedList<Integer> iteratorCopy() {
            var result = new LinkedList<Integer>();
            for (var value : Bench.filter) {
                result.add(value);
            }

            return result;
        }
    }

    public static class Map {

        @Benchmark
        public LinkedList<Integer> lambdaMap() {
            return Bench.data.stream().map(n -> n * n).collect(Collectors.toCollection(LinkedList::new));
        }

        @Benchmark
        public LinkedList<Integer> loopMap() {
            var result = new LinkedList<Integer>();
            var iter = Bench.filter.iterator();

            while (iter.hasNext()) {
                var curr = iter.next();
                result.add(curr * curr);
            }

            return result;
        }

        @Benchmark
        public LinkedList<Integer> iteratorMap() {
            var result = new LinkedList<Integer>();
            for (var value : Bench.filter) {
                result.add(value * value);
            }

            return result;
        }
    }
}
