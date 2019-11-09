package com.benchmarks;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class ArrayListInt {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static int target;
        public static ArrayList<Integer> data = new ArrayList<Integer>(N);
        public static ArrayList<Integer> contains = new ArrayList<Integer>(N);
        public static ArrayList<Integer> filtering = new ArrayList<Integer>(N);
        
        @Setup(Level.Trial)
        public void setupData() {
            for (int i = 1; i <= N; i++) {
                data.add(i);
            }
        }

        @Setup(Level.Trial)
        public void setupContains() {
            var max = 101;
            var min = 1;
            var rnd = new Random();

            target = rnd.nextInt(max - min) - min;

            for (int i = 1; i <= N; i++) {
                Bench.contains.add(rnd.nextInt(max - min) - min);
            }
        }

        @Setup(Level.Trial)
        public void setupFilter() {
            var rnd = new Random();
            int min = -N;

            for (int i = 1; i <= N; i++) {
                Bench.filtering.add(rnd.nextInt(N - min) - min);
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
            for (int i = 0; i < Bench.data.size(); i++) {
                total += Bench.data.get(i);
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
        public ArrayList<Integer> lambdaPopulate() {
            var rnd = new Random();
            return Stream.iterate(rnd.nextInt(101), i -> i + rnd.nextInt(101)).limit(Bench.data.size())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Integer> loopPopulate() {
            var result = new ArrayList<Integer>(Bench.data.size());
            var rnd = new Random();

            for (int i = 0; i < Bench.data.size(); i++) {
                result.add(Bench.data.get(i) + rnd.nextInt(101));
            }

            return result;
        }

        @Benchmark
        public ArrayList<Integer> iteratorPopulate() {
            var result = new ArrayList<Integer>(Bench.data.size());
            var rnd = new Random();

            for (var value : Bench.data) {
                result.add(value + rnd.nextInt(101));
            }

            return result;
        }
    }

    public static class Iterate {

        @Benchmark
        public int lambdaCount() {
            return (int) Bench.data.stream().filter(n -> n > 0).count();
        }

        @Benchmark
        public int loopIterate() {
            int count = 0;
            for (int i = 0; i < Bench.data.size(); i++) {
                if (Bench.data.get(i) > 0)
                    count++;
            }

            return count;
        }

        @Benchmark
        public int iteratorIterate() {
            int count = 0;
            for (var value : Bench.data) {
                if (value > 0)
                    ++count;
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
            for (int i = 0; i < Bench.contains.size(); i++) {
                if (Bench.contains.get(i) == Bench.target)
                    return true;
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
        public ArrayList<Integer> lambdaFilter() {
            return Bench.filtering.stream().filter(n -> n >= 0).collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Integer> loopFilter() {
            var result = new ArrayList<Integer>();
            for (int i = 0; i < Bench.filtering.size(); i++) {
                if (Bench.filtering.get(i) >= 0)
                    result.add(Bench.filtering.get(i));
            }

            return result;
        }

        @Benchmark
        public ArrayList<Integer> iteratorFilter() {
            var result = new ArrayList<Integer>();
            for (var value : Bench.data) {
                if (value >= 0)
                    result.add(value);
            }

            return result;
        }
    }

    public static class Copy {

        @Benchmark
        public ArrayList<Integer> lambdaCopy() {
            return Bench.data.stream().map(n -> n).collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Integer> loopCopy() {
            var result = new ArrayList<Integer>(Bench.data.size());
            for (int i = 0; i < Bench.data.size(); i++) {
                result.add(Bench.data.get(i));
            }

            return result;
        }

        @Benchmark
        public ArrayList<Integer> iteratorCopy() {
            var result = new ArrayList<Integer>(Bench.data.size());
            for (var value : Bench.data) {
                result.add(value);
            }

            return result;
        }
    }

    public static class Map {

        @Benchmark
        public ArrayList<Integer> lambdaMap() {
            return Bench.data.stream().map(n -> n * n).collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Integer> loopMap() {
            var result = new ArrayList<Integer>(Bench.data.size());
            for (int i = 0; i < Bench.data.size(); i++) {
                result.add(Bench.data.get(i) * Bench.data.get(i));
            }

            return result;
        }

        @Benchmark
        public ArrayList<Integer> iteratorMap() {
            var result = new ArrayList<Integer>(Bench.data.size());
            for (var value : Bench.data) {
                result.add(value * value);
            }

            return result;
        }
    }
}