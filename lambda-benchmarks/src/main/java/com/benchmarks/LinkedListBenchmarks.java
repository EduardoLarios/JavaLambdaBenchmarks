package com.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

public class LinkedListBenchmarks {
    public class IntegerBenchmarks {
        public class Reduce {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Integer> data = new LinkedList<Integer>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public int loopReduce() {
                int total = 0;
                var iter = data.iterator();

                while (iter.hasNext()) {
                    total += iter.next();
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
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Integer> data = new LinkedList<Integer>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public LinkedList<Integer> loopPopulate() {
                var result = new LinkedList<Integer>();
                var rnd = new Random();

                for (int i = 0; i < data.size(); i++) {
                    result.add(rnd.nextInt(101));
                }

                return result;
            }

            @Benchmark
            public LinkedList<Integer> iteratorPopulate() {
                var result = new LinkedList<Integer>();
                var rnd = new Random();

                for (var value : data) {
                    result.add(rnd.nextInt(101));
                }

                return result;
            }
        }

        public class Iterate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Integer> data = new LinkedList<Integer>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public int loopIterate() {
                int count = 0;
                var iter = data.iterator();

                while (iter.hasNext()) {
                    if (iter.next() > 0)
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
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public int target;
            public LinkedList<Integer> data = new LinkedList<Integer>();

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
            public int loopContains() {
                var iter = data.iterator();

                while (iter.hasNext()) {
                    var curr = iter.next();
                    if (curr == target) {
                        return curr;
                    }
                }

                return -1;
            }

            @Benchmark
            public int iteratorContains() {
                for (var value : data) {
                    if (value == target)
                        return value;
                }

                return -1;
            }
        }

        public class Filter {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Integer> data = new LinkedList<Integer>();

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
            public LinkedList<Integer> loopFilter() {
                var result = new LinkedList<Integer>();
                var iter = data.iterator();

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
                for (var value : data) {
                    if (value >= 0)
                        result.add(value);
                }

                return result;
            }
        }

        public class Copy {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Integer> data = new LinkedList<Integer>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public LinkedList<Integer> loopCopy() {
                var result = new LinkedList<Integer>();
                var iter = data.iterator();

                while (iter.hasNext()) {
                    result.add(iter.next());
                }

                return result;
            }

            @Benchmark
            public LinkedList<Integer> iteratorCopy() {
                var result = new LinkedList<Integer>();
                for (var value : data) {
                    result.add(value);
                }

                return result;
            }
        }

        public class Map {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Integer> data = new LinkedList<Integer>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public LinkedList<Integer> loopMap() {
                var result = new LinkedList<Integer>();
                var iter = data.iterator();

                while (iter.hasNext()) {
                    var curr = iter.next();
                    result.add(curr * curr);
                }

                return result;
            }

            @Benchmark
            public LinkedList<Integer> iteratorMap() {
                var result = new LinkedList<Integer>();
                for (var value : data) {
                    result.add(value * value);
                }

                return result;
            }
        }
    }

    public class LongBenchmarks {
        public class Reduce {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Long> data = new LinkedList<Long>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i * N);
                }
            }

            @Benchmark
            public long loopReduce() {
                long total = 0;
                var iter = data.iterator();

                while (iter.hasNext()) {
                    total += iter.next();
                }

                return total;
            }

            @Benchmark
            public long iteratorReduce() {
                long total = 0;
                for (var value : data) {
                    total += value;
                }

                return total;
            }
        }

        public class Populate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Long> data = new LinkedList<Long>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i);
                }
            }

            @Benchmark
            public LinkedList<Long> loopPopulate() {
                var result = new LinkedList<Long>();
                var rnd = new Random();

                for (int i = 0; i < data.size(); i++) {
                    result.add(rnd.nextLong());
                }

                return result;
            }

            @Benchmark
            public LinkedList<Long> iteratorPopulate() {
                var result = new LinkedList<Long>();
                var rnd = new Random();

                for (var value : data) {
                    result.add(rnd.nextLong());
                }

                return result;
            }
        }

        public class Iterate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Long> data = new LinkedList<Long>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i);
                }
            }

            @Benchmark
            public long loopIterate() {
                long count = 0;
                var iter = data.iterator();

                while (iter.hasNext()) {
                    if (iter.next() > 0)
                        count++;
                }

                return count;
            }

            @Benchmark
            public long iteratorIterate() {
                long count = 0;
                for (var value : data) {
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
            public LinkedList<Long> data = new LinkedList<Long>();

            @Setup
            public void setupData() {
                int max = 101;
                int min = 1;
                var rnd = new Random();

                target = (long) rnd.nextInt(max - min) - min;

                for (int i = 1; i <= N; i++) {
                    data.add(N * (long) rnd.nextInt(max - min) - min);
                }
            }

            @Benchmark
            public long loopContains() {
                var iter = data.iterator();

                while (iter.hasNext()) {
                    var curr = iter.next();
                    if (curr == target) {
                        return curr;
                    }
                }

                return -1;
            }

            @Benchmark
            public long iteratorContains() {
                for (var value : data) {
                    if (value == target)
                        return value;
                }

                return -1;
            }
        }

        public class Filter {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Long> data = new LinkedList<Long>();

            @Setup
            public void setupData() {
                int max = N;
                int min = -N;
                var rnd = new Random();

                for (int i = 1; i <= N; i++) {
                    data.add((long) rnd.nextInt(max - min) - min);
                }
            }

            @Benchmark
            public LinkedList<Long> loopFilter() {
                var result = new LinkedList<Long>();
                var iter = data.iterator();

                while (iter.hasNext()) {
                    var curr = iter.next();
                    if (curr >= 0)
                        result.add(curr);
                }

                return result;
            }

            @Benchmark
            public LinkedList<Long> iteratorFilter() {
                var result = new LinkedList<Long>();
                for (var value : data) {
                    if (value >= 0)
                        result.add(value);
                }

                return result;
            }
        }

        public class Copy {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Long> data = new LinkedList<Long>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i * N);
                }
            }

            @Benchmark
            public LinkedList<Long> loopCopy() {
                var result = new LinkedList<Long>();
                var iter = data.iterator();

                while (iter.hasNext()) {
                    result.add(iter.next());
                }

                return result;
            }

            @Benchmark
            public LinkedList<Long> iteratorCopy() {
                var result = new LinkedList<Long>();
                for (var value : data) {
                    result.add(value);
                }

                return result;
            }
        }

        public class Map {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Long> data = new LinkedList<Long>();

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i * N);
                }
            }

            @Benchmark
            public LinkedList<Long> loopMap() {
                var result = new LinkedList<Long>();
                var iter = data.iterator();

                while (iter.hasNext()) {
                    var curr = iter.next();
                    result.add(curr * 5);
                }

                return result;
            }

            @Benchmark
            public LinkedList<Long> iteratorMap() {
                var result = new LinkedList<Long>();
                for (var value : data) {
                    result.add((long) value * 5);
                }

                return result;
            }
        }
    }

    public static class ClassBenchmarks {
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
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Student> students = new LinkedList<Student>();

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
            public String loopReduce() {
                var builder = new StringBuilder(students.size());
                var iter = students.iterator();

                while (iter.hasNext()) {
                    var s = iter.next();
                    var average = s.average;
                    var passed = average > 60 ? Integer.toString(average) : "Failed";
                    builder.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
                }

                return builder.toString();
            }

            @Benchmark
            public String iteratorReduce() {
                var builder = new StringBuilder(students.size());

                for (var s : students) {
                    var average = s.average;
                    var passed = average > 60 ? Integer.toString(average) : "Failed";
                    builder.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
                }

                return builder.toString();
            }
        }

        public class Populate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Integer> students = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    students.add(i);
                }
            }

            @Benchmark
            public LinkedList<Student> loopPopulate() {
                var rnd = new Random();
                var result = new LinkedList<Student>();

                int max = 101;
                int min = 50;
                int fname = ClassBenchmarks.firstNames.size();
                int lname = ClassBenchmarks.lastNames.size();

                for (int i = 0; i < students.size(); i++) {
                    int n = i;
                    result.add(new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(fname));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(lname));
                        }
                    });
                }

                return result;
            }

            @Benchmark
            public LinkedList<Student> iteratorPopulate() {
                var rnd = new Random();
                var result = new LinkedList<Student>();

                int max = 101;
                int min = 50;
                int fname = ClassBenchmarks.firstNames.size();
                int lname = ClassBenchmarks.lastNames.size();

                for (var s : students) {
                    result.add(new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = s * N;
                            firstName = ClassBenchmarks.firstNames.get(rnd.nextInt(fname));
                            lastName = ClassBenchmarks.lastNames.get(rnd.nextInt(lname));
                        }
                    });
                }

                return result;
            }
        }

        public class Iterate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Student> students = new LinkedList<Student>();

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
            public int loopIterate() {
                int count = 0;
                var iter = students.iterator();

                while (iter.hasNext()) {
                    var s = iter.next();
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
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Student> students = new LinkedList<Student>();

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
            public Student loopContains() {
                for (int i = 0; i < students.size(); i++) {
                    var s = students.get(i);
                    if(s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                        return s;
                    }
                }

                return null;
            }

            @Benchmark
            public Student iteratorContains() {
                for (var s : students) {
                    if(s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                        return s;
                    }
                }

                return null;
            }
        }

        public class Filter {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public int target;
            public LinkedList<Student> students = new LinkedList<Student>();

            @Setup
            public void setupData() {
                var rnd = new Random();
                int maxF = ClassBenchmarks.firstNames.size();
                int maxL = ClassBenchmarks.lastNames.size();

                target = rnd.nextInt((N / 2)) - 1;

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
            public LinkedList<Student> loopFilter() {
                var result = new LinkedList<Student>();
                var iter = students.iterator();

                while (iter.hasNext()) {
                    var s = iter.next();
                    if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > target) {
                        result.add(s);
                    }
                }

                return result;
            }

            @Benchmark
            public LinkedList<Student> iteratorFilter() {
                var result = new LinkedList<Student>();
                for (var s : result) {
                    if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > target) {
                        result.add(s);
                    }
                }

                return result;
            }
        }

        public class Copy {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Student> students = new LinkedList<Student>();

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
            public LinkedList<Student> loopCopy() {
                var result = new LinkedList<Student>();
                var iter = students.iterator();

                while(iter.hasNext()) {
                    var s = iter.next();
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
            public LinkedList<Student> iteratorCopy() {
                var result = new LinkedList<Student>();

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
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public LinkedList<Student> students = new LinkedList<Student>();

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
            public HashMap<Long, String> loopMap() {
                var result = new HashMap<Long, String>(students.size());
                var iter = students.iterator();

                while(iter.hasNext()) {
                    var s = iter.next();
                    result.put(s.ID, String.format("%s, %s", s.lastName, s.firstName));
                }

                return result;
            }

            @Benchmark
            public HashMap<Long, String> iteratorMap() {
                var result = new HashMap<Long, String>(students.size());
                for (var s : students) {
                    result.put(s.ID, String.format("%s, %s", s.lastName, s.firstName));
                }

                return result;
            }
        }
    }
}