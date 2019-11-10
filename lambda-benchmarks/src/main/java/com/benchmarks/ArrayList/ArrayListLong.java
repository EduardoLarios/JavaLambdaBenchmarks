package com.benchmarks.ArrayList;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class ArrayListLong {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static long target;
        public static ArrayList<Long> data = new ArrayList<Long>(N);
        public static ArrayList<Long> contains = new ArrayList<Long>(N);
        public static ArrayList<Long> filtering = new ArrayList<Long>(N);

        @Setup(Level.Trial)
        public void setupData() {
            for (int i = 1; i <= N; i++) {
                data.add((long) (i * N));
            }
        }

        @Setup(Level.Trial)
        public void setupContains() {
            var rnd = new Random();
            target = (long) rnd.nextInt(101) * N;

            for (int i = 1; i <= N; i++) {
                filtering.add((long) rnd.nextInt(101) * N);
            }
        }

        @Setup(Level.Trial)
        public void setupFilter() {
            var rnd = new Random();
            int min = -N;

            for (int i = 1; i <= N; i++) {
                long e = (long) rnd.nextInt(N - min) - min;
                filtering.add(e);
            }
        }
    }

    public static class Reduce {
        
        @Benchmark
        public long lambdaReduce() {
            return Bench.data.stream().reduce(0L, Long::sum);
        }

        @Benchmark
        public long loopReduce() {
            long total = 0L;

            for (int i = 0; i < Bench.data.size(); i++) {
                total += Bench.data.get(i);
            }

            return total;
        }

        @Benchmark
        public long iteratorReduce() {
            long total = 0L;

            for (var value : Bench.data) {
                total += value;
            }

            return total;
        }
    }

    public static class Populate {

        @Benchmark
        public ArrayList<Long> lambdaPopulate() {
            var rnd = new Random();
            return Stream.iterate(rnd.nextLong(), i -> i + rnd.nextLong()).limit(Bench.data.size())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Long> loopPopulate() {
            var result = new ArrayList<Long>(Bench.data.size());
            var rnd = new Random();

            for (int i = 0; i < Bench.data.size(); i++) {
                result.add(Bench.data.get(i) + rnd.nextLong());
            }

            return result;
        }

        @Benchmark
        public ArrayList<Long> iteratorPopulate() {
            var result = new ArrayList<Long>(Bench.data.size());
            var rnd = new Random();

            for (var value : Bench.data) {
                result.add(value + rnd.nextLong());
            }

            return result;
        }
    }

    public static class Iterate {

        @Benchmark
        public long lambdaIterate() {
            return Bench.data.stream().filter(n -> n > 0).count();
        }

        @Benchmark
        public long loopIterate() {
            long count = 0L;

            for (int i = 0; i < Bench.data.size(); i++) {
                if (Bench.data.get(i) > 0) {
                    count++;
                }
            }

            return count;
        }

        @Benchmark
        public long iteratorIterate() {
            long count = 0L;

            for (var value : Bench.data) {
                if (value > 0) {
                    count++;
                }
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
        public ArrayList<Long> lambdaFilter() {
            return Bench.filtering.stream().filter(n -> n >= 0).collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Long> loopFilter() {
            var result = new ArrayList<Long>();

            for (int i = 0; i < Bench.filtering.size(); i++) {
                if (Bench.filtering.get(i) >= 0) {
                    result.add(Bench.filtering.get(i));
                }
            }

            return result;
        }

        @Benchmark
        public ArrayList<Long> iteratorFilter() {
            var result = new ArrayList<Long>();

            for (var value : Bench.filtering) {
                if (value >= 0) {
                    result.add(value);
                }
            }

            return result;
        }

    }

    public static class Copy {

        @Benchmark
        public ArrayList<Long> lambdaCopy() {
            return Bench.data.stream().map(n -> n).collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Long> loopCopy() {
            var copy = new ArrayList<Long>(Bench.data.size());

            for (int i = 0; i < Bench.data.size(); i++) {
                copy.add(Bench.data.get(i));
            }

            return copy;
        }

        @Benchmark
        public ArrayList<Long> iteratorCopy() {
            var copy = new ArrayList<Long>(Bench.data.size());

            for (var value : Bench.data) {
                copy.add(value);
            }

            return copy;
        }
    }

    public static class Map {

        @Benchmark
        public ArrayList<Long> lambdaMap() {
            return Bench.data.stream().map(n -> n * Bench.N).collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Long> loopMap() {
            var result = new ArrayList<Long>(Bench.data.size());

            for (int i = 0; i < Bench.data.size(); i++) {
                result.add(Bench.data.get(i) * Bench.N);
            }

            return result;
        }

        @Benchmark
        public ArrayList<Long> iteratorMap() {
            var result = new ArrayList<Long>(Bench.data.size());

            for (var value : Bench.data) {
                result.add(value * Bench.N);
            }

            return result;
        }
    }
}