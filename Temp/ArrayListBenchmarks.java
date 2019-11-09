package com.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

public class ArrayListBenchmarks {
    public class IntegerBenchmarks {
        @Param({ "10", "100", "1000", "10000" })
        public int N;

        public class Reduce {
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public int lambdaReduce() {
                return data.stream().reduce(0, Integer::sum);
            }

            @Benchmark
            public int loopReduce() {
                int total = 0;
                for (int i = 0; i < data.size(); i++) {
                    total += data.get(i);
                }

                return total;
            }

            @Benchmark
            public int iteratorReduce() {
                int total = 0;
                for (var value : data) {
                    total += value;
                }

                return total;
            }
        }

        public class Populate {
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public ArrayList<Integer> lambdaPopulate() {
                var rnd = new Random();
                return Stream.iterate(rnd.nextInt(101), i -> i + rnd.nextInt(101)).limit(data.size())
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Integer> loopPopulate() {
                var result = new ArrayList<Integer>(data.size());
                var rnd = new Random();

                for (int i = 0; i < data.size(); i++) {
                    result.add(data.get(i) + rnd.nextInt(101));
                }

                return result;
            }

            @Benchmark
            public ArrayList<Integer> iteratorPopulate() {
                var result = new ArrayList<Integer>(data.size());
                var rnd = new Random();

                for (var value : data) {
                    result.add(value + rnd.nextInt(101));
                }

                return result;
            }
        }

        public class Iterate {
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public int lambdaCount() {
                return (int) data.stream().filter(n -> n > 0).count();
            }

            @Benchmark
            public int loopIterate() {
                int count = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) > 0)
                        count++;
                }

                return count;
            }

            @Benchmark
            public int iteratorIterate() {
                int count = 0;
                for (var value : data) {
                    if (value > 0)
                        ++count;
                }

                return count;
            }
        }

        public class Contains {
            public int target;
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                var max = 101;
                var min = 1;
                var rnd = new Random();

                target = rnd.nextInt(max - min) - min;

                for (int i = 1; i <= N; i++) {
                    data.add(rnd.nextInt(max - min) - min);
                }
            }

            @Benchmark
            public boolean lambdaContains() {
                return data.stream().anyMatch(n -> n == target);
            }

            @Benchmark
            public boolean loopContains() {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) == target)
                        return true;
                }

                return false;
            }

            @Benchmark
            public boolean iteratorContains() {
                for (var value : data) {
                    if (value == target)
                        return true;
                }

                return false;
            }
        }

        public class Filter {
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int max = N;
                int min = -N;

                for (int i = 1; i <= N; i++) {
                    data.add(rnd.nextInt(max - min) - min);
                }
            }

            @Benchmark
            public ArrayList<Integer> lambdaFilter() {
                return data.stream().filter(n -> n >= 0).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Integer> loopFilter() {
                var result = new ArrayList<Integer>();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) >= 0)
                        result.add(data.get(i));
                }

                return result;
            }

            @Benchmark
            public ArrayList<Integer> iteratorFilter() {
                var result = new ArrayList<Integer>();
                for (var value : data) {
                    if (value >= 0)
                        result.add(value);
                }

                return result;
            }
        }

        public class Copy {
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public ArrayList<Integer> lambdaCopy() {
                return data.stream().map(n -> n).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Integer> loopCopy() {
                var result = new ArrayList<Integer>(data.size());
                for (int i = 0; i < data.size(); i++) {
                    result.add(data.get(i));
                }

                return result;
            }

            @Benchmark
            public ArrayList<Integer> iteratorCopy() {
                var result = new ArrayList<Integer>(data.size());
                for (var value : data) {
                    result.add(value);
                }

                return result;
            }
        }

        public class Map {
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public ArrayList<Integer> lambdaMap() {
                return data.stream().map(n -> n * n).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Integer> loopMap() {
                var result = new ArrayList<Integer>(data.size());
                for (int i = 0; i < data.size(); i++) {
                    result.add(data.get(i) * data.get(i));
                }

                return result;
            }

            @Benchmark
            public ArrayList<Integer> iteratorMap() {
                var result = new ArrayList<Integer>(data.size());
                for (var value : data) {
                    result.add(value * value);
                }

                return result;
            }
        }
    }

    public class LongBenchmarks {
        @Param({ "10", "100", "1000", "10000" })
        public int N;

        public class Reduce {
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i * N));
                }
            }

            @Benchmark
            public long lambdaReduce() {
                return data.stream().reduce(0L, Long::sum);
            }

            @Benchmark
            public long loopReduce() {
                long total = 0L;

                for (int i = 0; i < data.size(); i++) {
                    total += data.get(i);
                }

                return total;
            }

            @Benchmark
            public long iteratorReduce() {
                long total = 0L;

                for (var value : data) {
                    total += value;
                }

                return total;
            }
        }

        public class Populate {
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i * N));
                }
            }

            @Benchmark
            public ArrayList<Long> lambdaPopulate() {
                var rnd = new Random();
                return Stream.iterate(rnd.nextLong(), i -> i + rnd.nextLong())
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Long> loopPopulate() {
                var result = new ArrayList<Long>(data.size());
                var rnd = new Random();

                for (int i = 0; i < data.size(); i++) {
                    result.add(data.get(i) + rnd.nextLong());
                }

                return result;
            }

            @Benchmark
            public ArrayList<Long> iteratorPopulate() {
                var result = new ArrayList<Long>(data.size());
                var rnd = new Random();

                for (var value : data) {
                    result.add(value + rnd.nextLong());
                }

                return result;
            }
        }

        public class Iterate {
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i);
                }
            }

            @Benchmark
            public long lambdaIterate() {
                return data.stream().filter(n -> n > 0).count();
            }

            @Benchmark
            public long loopIterate() {
                long count = 0L;

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) > 0) {
                        count++;
                    }
                }

                return count;
            }

            @Benchmark
            public long iteratorIterate() {
                long count = 0L;

                for (var value : data) {
                    if (value > 0) {
                        count++;
                    }
                }

                return count;
            }

        }

        public class Contains {
            public long target;
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                target = (long) rnd.nextInt(101) * N;

                for (int i = 1; i <= N; i++) {
                    data.add((long) rnd.nextInt(101) * N);
                }
            }

            @Benchmark
            public boolean lambdaContains() {
                return data.stream().anyMatch(n -> n == target);
            }

            @Benchmark
            public boolean loopContains() {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) == target)
                        return true;
                }

                return false;
            }

            @Benchmark
            public boolean iteratorContains() {
                for (var value : data) {
                    if (value == target)
                        return true;
                }

                return false;
            }
        }

        public class Filter {
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int max = N;
                int min = -N;

                for (int i = 1; i <= N; i++) {
                    long e = (long) rnd.nextInt(max - min) - min;
                    data.add(e);
                }
            }

            @Benchmark
            public ArrayList<Long> lambdaFilter() {
                return data.stream().filter(n -> n >= 0).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Long> loopFilter() {
                var result = new ArrayList<Long>();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) >= 0) {
                        result.add(data.get(i));
                    }
                }

                return result;
            }

            @Benchmark
            public ArrayList<Long> iteratorFilter() {
                var result = new ArrayList<Long>();

                for (var value : data) {
                    if (value >= 0) {
                        result.add(value);
                    }
                }

                return result;
            }

        }

        public class Copy {
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i));
                }
            }

            @Benchmark
            public ArrayList<Long> lambdaCopy() {
                return data.stream().map(n -> n).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Long> loopCopy() {
                var copy = new ArrayList<Long>(data.size());

                for (int i = 0; i < data.size(); i++) {
                    copy.add(data.get(i));
                }

                return copy;
            }

            @Benchmark
            public ArrayList<Long> iteratorCopy() {
                var copy = new ArrayList<Long>(data.size());

                for (var value : data) {
                    copy.add(value);
                }

                return copy;
            }
        }

        public class Map {
            public ArrayList<Long> data = new ArrayList<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i));
                }
            }

            @Benchmark
            public ArrayList<Long> lambdaMap() {
                return data.stream().map(n -> n * N).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Long> loopMap() {
                var result = new ArrayList<Long>(data.size());

                for (int i = 0; i < data.size(); i++) {
                    result.add(data.get(i) * N);
                }

                return result;
            }

            @Benchmark
            public ArrayList<Long> iteratorMap() {
                var result = new ArrayList<Long>(data.size());

                for (var value : data) {
                    result.add(value * N);
                }

                return result;
            }

        }
    }

    public static class ClassBenchmarks {
        @Param({ "10", "100", "1000", "10000" })
        public int N;
        public static List<String> firstNames = new ArrayList<String>(List.of(
                // Simple Male
                "Juan", "Carlos", "Manuel", "Francisco", "Mauricio", "Eduardo",
                // Simple Female
                "Fernanda", "María", "Sofía", "Ana", "Carla", "Marlene",
                // Composite Male
                "Juan Manuel", "Luis Carlos", "Manuel Alejandro", "Javier Francisco", "Luis Eduardo", "José Luis",
                // Composite Female
                "María Fernanda", "María Jose", "Sofía Paulina", "Ana Belén", "Daniela Alejandra", "Luz Angélica"));

        public static List<String> lastNames = new ArrayList<String>(List.of("García", "Rodríguez", "Hernández",
                "López", "Martínez", "González", "Pérez", "Sánchez", "Ramírez", "Torres", "Flores", "Rivera", "Gómez",
                "Díaz", "Cruz", "Morales", "Reyes", "Gutiérrez", "Ortiz"));

        public class Student {
            public int average;
            public long ID;
            public String firstName;
            public String lastName;
        }

        public class Reduce {
            public ArrayList<Student> students = new ArrayList<Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (int i = 1; i <= N; i++) {
                    var s = new Student() {
                        {
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = rnd.nextLong();
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                        }
                    };

                    students.add(s);
                }
            }

            @Benchmark
            public String lambdaReduce() {
                return students.stream()
                        .map(s -> String.format("%s, %s, %s", s.lastName, s.firstName,
                                (s.average > 60) ? Integer.toString(s.average) : "Failed"))
                        .collect(StringBuilder::new, (sb, s) -> sb.append(s), (sb1, sb2) -> sb1.append(sb2.toString()))
                        .toString();
            }

            @Benchmark
            public String loopReduce() {
                var sb = new StringBuilder();
                for (int i = 0; i < students.size(); i++) {
                    var s = students.get(i);
                    var average = s.average;
                    var passed = average > 60 ? Integer.toString(average) : "Failed";
                    sb.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
                }

                return sb.toString();
            }

            @Benchmark
            public String iteratorReduce() {
                var sb = new StringBuilder();

                for (var s : students) {
                    var average = s.average;
                    var passed = average > 60 ? Integer.toString(average) : "Failed";
                    sb.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
                }

                return sb.toString();
            }
        }

        public class Populate {
            public ArrayList<Integer> students = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    students.add(i);
                }
            }

            @Benchmark
            public ArrayList<Student> lambdaPopulate() {
                var rnd = new Random();
                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                return students.stream().map(i -> new Student() {
                    {
                        firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                        lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                        average = rnd.nextInt(100 - 50) - 50;
                        ID = i + rnd.nextLong();
                    }
                }).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Student> loopPopulate() {
                var rnd = new Random();
                var result = new ArrayList<Student>(students.size());

                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (int i = 0; i < students.size(); i++) {
                    var s = i;
                    result.add(new Student() {
                        {
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = s + rnd.nextLong();
                        }
                    });
                }

                return result;
            }

            @Benchmark
            public ArrayList<Student> iteratorPopulate() {
                var rnd = new Random();
                var result = new ArrayList<Student>(students.size());

                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (var s : students) {
                    result.add(new Student() {
                        {
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = s + rnd.nextLong();
                        }
                    });
                }

                return result;
            }
        }

        public class Iterate {
            public ArrayList<Student> students = new ArrayList<Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (int i = 1; i <= N; i++) {
                    var s = new Student() {
                        {
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = rnd.nextLong();
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                        }
                    };

                    students.add(s);
                }
            }

            @Benchmark
            public int lambdaIterate() {
                return (int) students.stream()
                        .filter(s -> s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE).count();
            }

            @Benchmark
            public int loopIterate() {
                int count = 0;
                for (int i = 0; i < students.size(); i++) {
                    var s = students.get(i);
                    if (s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }

            @Benchmark
            public int iteratorIterate() {
                int count = 0;
                for (var s : students) {
                    if (s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }
        }

        public class Contains {
            public int target;
            public ArrayList<Student> students = new ArrayList<Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                target = rnd.nextInt(N - (-N)) - N;

                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (int i = 1; i <= N; i++) {
                    var s = new Student() {
                        {
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = rnd.nextLong();
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                        }
                    };

                    students.add(s);
                }
            }

            @Benchmark
            public boolean lambdaContains() {
                return students.stream().anyMatch(s -> s.average >= 70 && s.average <= 85 && s.firstName.contains(" ")
                        && s.lastName.contains("es"));
            }

            @Benchmark
            public boolean loopContains() {
                for (int i = 0; i < students.size(); i++) {
                    var s = students.get(i);
                    if (s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                        return true;
                    }
                }

                return false;
            }

            @Benchmark
            public boolean iteratorContains() {
                for (var s : students) {
                    if (s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                        return true;
                    }
                }

                return false;
            }
        }

        public class Filter {
            public int target;
            public ArrayList<Student> students = new ArrayList<Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                target = rnd.nextInt(N - (-N)) - N;

                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (int i = 1; i <= N; i++) {
                    var s = new Student() {
                        {
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = rnd.nextLong();
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                        }
                    };

                    students.add(s);
                }
            }

            @Benchmark
            public ArrayList<Student> lambdaFilter() {
                return students.stream()
                        .filter(s -> s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > target)
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Student> loopFilter() {
                var result = new ArrayList<Student>(students.size());
                for (int i = 0; i < students.size(); i++) {
                    var s = students.get(i);
                    if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > target) {
                        result.add(s);
                    }
                }

                return result;
            }

            @Benchmark
            public ArrayList<Student> iteratorFilter() {
                var result = new ArrayList<Student>(students.size());
                for (var s : students) {
                    if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > target) {
                        result.add(s);
                    }
                }

                return result;
            }
        }

        public class Copy {
            public ArrayList<Student> students = new ArrayList<Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (int i = 1; i <= N; i++) {
                    var s = new Student() {
                        {
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = rnd.nextLong();
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                        }
                    };

                    students.add(s);
                }
            }

            @Benchmark
            public ArrayList<Student> lambdaCopy() {
                return students.stream().map(s -> new Student() {
                    {
                        average = s.average;
                        ID = s.ID;
                        firstName = s.firstName;
                        lastName = s.lastName;
                    }
                }).collect(Collectors.toCollection(ArrayList::new));
            }

            @Benchmark
            public ArrayList<Student> loopCopy() {
                var result = new ArrayList<Student>(students.size());
                for (int i = 0; i < students.size(); i++) {
                    var s = students.get(i);
                    result.add(new Student() {
                        {
                            average = s.average;
                            ID = s.ID;
                            firstName = s.firstName;
                            lastName = s.lastName;
                        }
                    });
                }

                return result;
            }

            @Benchmark
            public ArrayList<Student> iteratorCopy() {
                var result = new ArrayList<Student>(students.size());
                for (var s : students) {
                    result.add(new Student() {
                        {
                            average = s.average;
                            ID = s.ID;
                            firstName = s.firstName;
                            lastName = s.lastName;
                        }
                    });
                }

                return result;
            }
        }

        public class Map {
            public ArrayList<Student> students = new ArrayList<Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                for (int i = 1; i <= N; i++) {
                    var s = new Student() {
                        {
                            average = rnd.nextInt(100 - 50) - 50;
                            ID = rnd.nextLong();
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(maxF));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(maxL));
                        }
                    };

                    students.add(s);
                }
            }

            @Benchmark
            public HashMap<Long, String> lambdaMap() {
                return new HashMap<Long, String>(students.stream()
                        .collect(Collectors.toMap(s -> s.ID, s -> String.format("%s, %s", s.lastName, s.firstName))));

            }

            @Benchmark
            public HashMap<Long, String> loopMap() {
                var result = new HashMap<Long, String>(students.size());
                for (int i = 0; i < students.size(); i++) {
                    var s = students.get(i);
                    var value = String.format("%s, %s", s.lastName, s.firstName);

                    result.put(s.ID, value);
                }

                return result;
            }

            @Benchmark
            public HashMap<Long, String> iteratorMap() {
                var result = new HashMap<Long, String>(students.size());
                for (var s : students) {
                    var value = String.format("%s, %s", s.lastName, s.firstName);
                    result.put(s.ID, value);
                }

                return result;
            }
        }
    }
}