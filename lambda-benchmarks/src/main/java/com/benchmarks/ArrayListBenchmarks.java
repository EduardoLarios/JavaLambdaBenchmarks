package com.benchmarks;

import java.util.ArrayList;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

public class ArrayListBenchmarks {
    public class IntegerBenchmarks {
    }

    public class LongBenchmarks {
        public class Reduce {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i * N));
                }
            }

            @Benchmark
            public Long loopReduce() {
                Long total = 0L;

                for (int i = 0; i < data.size(); i++) {
                    total += data.get(i);
                }

                return total;
            }

            @Benchmark
            public Long iteratorReduce() {
                long total = 0L;

                for (long value : data) {
                    total += value;
                }

                return total;
            }
        }

        public class Populate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i * N));
                }
            }

            @Benchmark
            public ArrayList<Long> loopPopulate() {
                ArrayList<Long> result = new ArrayList<Long>(data.size());
                Random rnd = new Random();

                for (int i = 0; i < data.size(); i++) {
                    result.add(rnd.nextLong());
                }

                return result;
            }

            @Benchmark
            public ArrayList<Long> iteratorPopulate() {
                ArrayList<Long> result = new ArrayList<Long>(data.size());
                Random rnd = new Random();

                for (long value : data) {
                    result.add(rnd.nextLong());
                }

                return result;
            }
        }

        public class Iterate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i);
                }
            }

            @Benchmark
            public long loopIterate() {
                long count = 0L;

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) > 0)
                        count++;
                }

                return count;
            }

            @Benchmark
            public long iteratorIterate() {
                long count = 0L;

                for (long value : data) {
                    if (value > 0)
                        ++count;
                }

                return count;
            }

        }

        public class Contains {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public long target;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                Random rnd = new Random();
                target = (long) rnd.nextInt(101) * N;

                for (int i = 1; i <= N; i++) {
                    data.add((long) rnd.nextInt(101) * N);
                }
            }

            @Benchmark
            public long loopContains() {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) == target)
                        return data.get(i);
                }

                return -1L;
            }

            @Benchmark
            public long iteratorContains() {
                for (long value : data) {
                    if (value == target)
                        return value;
                }

                return -1L;
            }
        }

        public class Filter {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                Random rnd = new Random();
                int max = N;
                int min = -N;

                for (int i = 1; i <= N; i++) {
                    long e = (long) rnd.nextInt(max - min) - min;
                    data.add(e);
                }
            }

            @Benchmark
            public ArrayList<Long> loopFilter() {
                ArrayList<Long> result = new ArrayList<Long>();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) >= 0)
                        result.add(data.get(i));
                }

                return result;
            }

            @Benchmark
            public ArrayList<Long> iteratorFilter() {
                ArrayList<Long> result = new ArrayList<Long>();

                for (Long value : data) {
                    if (value >= 0)
                        result.add(value);
                }

                return result;
            }

        }

        public class Copy {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i));
                }
            }

            @Benchmark
            public ArrayList<Long> loopCopy() {
                ArrayList<Long> copy = new ArrayList<Long>(data.size());

                for (int i = 0; i < data.size(); i++) {
                    copy.add(data.get(i));
                }

                return copy;
            }

            @Benchmark
            public ArrayList<Long> iteratorCopy() {
                ArrayList<Long> copy = new ArrayList<Long>(data.size());

                for (Long value : data) {
                    copy.add(value);
                }

                return copy;
            }
        }

        public class Map {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i));
                }
            }

            @Benchmark
            public ArrayList<Long> loopMap() {
                ArrayList<Long> result = new ArrayList<Long>(data.size());

                for (int i = 0; i < data.size(); i++) {
                    result.add(data.get(i) * N);
                }

                return result;
            }

            @Benchmark
            public ArrayList<Long> iteratorMap() {
                ArrayList<Long> result = new ArrayList<Long>(data.size());

                for (Long value : data) {
                    result.add(value * N);
                }

                return result;
            }

        }
    }

    public class ClassBenchmarks {
        
    }
}
