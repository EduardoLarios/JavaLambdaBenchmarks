package com.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

public class HashSetBenchmarks {
    public class IntegerBenchmarks {
        @Param({ "10", "100", "1000", "10000" })
        public int N;

        public class Reduce {
            public HashSet<Integer> data = new HashSet<Integer>(N);

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

                while(iter.hasNext())
                {
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
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public HashSet<Integer> loopPopulate() {
                var result = new HashSet<Integer>(data.size());
                int count = 0;

                for (int i = 0; i < data.size(); i++) {
                    result.add(data.get(i) + count++);
                }

                return result;
            }

            @Benchmark
            public HashSet<Integer> iteratorPopulate() {
                var result = new HashSet<Integer>(data.size());
                int count = 0;

                for (var value : data) {
                    result.add(value + count++);
                }

                return result;
            }
        }

        public class Iterate {
            public HashSet<Integer> data = new HashSet<Integer>(N);

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

                while(iter.hasNext()) {
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
                        count++;
                }

                return count;
            }
        }
    
        public class Contains {
            public int target;
            public HashSet<Integer> data = new HashSet<Integer>(N);

            @Setup
            public void setupData() {
                var max = N;
                var min = -N;
                var rnd = new Random();

                target = rnd.nextInt(max - min) - min;

                for (int i = 1; i <= N; i++) {
                    data.add(rnd.nextInt(max - min) - min);
                }
            }

            @Benchmark
            public boolean loopContains() {
                var iter = data.iterator();

                while(iter.hasNext()) {
                    var value = iter.next();
                    if(value == target) return true;
                }

                return false;
            }

            @Benchmark
            public boolean iteratorContains() {
                for (var value : data) {
                    if(value == target) return true;
                }

                return false;
            }
        }
    
        public class Filter {
            public HashSet<Integer> data = new HashSet<Integer>(N);

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
            public HashSet<Integer> loopFilter() {
                var result = new HashSet<Integer>();
                var iter = data.iterator();

                while(iter.hasNext()) {
                    var value = iter.next();
                    if(value >= 0) result.add(value); 
                }

                return result;
            }

            @Benchmark
            public HashSet<Integer> iteratorFilter() {
                var result = new HashSet<Integer>();

                for (var value : data) {
                    if(value >= 0) result.add(value);
                }

                return result;
            }
        }
    
        public class Copy {
            public HashSet<Integer> data = new HashSet<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public HashSet<Integer> loopCopy() {
                var result = new HashSet<Integer>(data.size());
                var iter = data.iterator();

                while(iter.hasNext()) {
                    result.add(iter.next());
                }

                return result;
            }

            @Benchmark
            public HashSet<Integer> iteratorCopy() {
                var result = new HashSet<Integer>(data.size());
                for (var value : data) {
                    result.add(value);
                }

                return result;
            }
        }
    
        public class Map {
            public HashSet<Integer> data = new HashSet<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public HashSet<Integer> loopMap() {
                var result = new HashSet<Integer>(data.size());
                var iter = data.iterator();

                while(iter.hasNext()) {
                    var value = iter.next();
                    result.add(value * value);
                }

                return result;
            }

            @Benchmark 
            public HashSet<Integer> iteratorMap() {
                var result = new HashSet<Integer>(data.size());
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
            public HashSet<Long> data = new HashSet<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i * N));
                }
            }

            @Benchmark
            public long loopReduce() {
                long total = 0L;
                var iter = data.iterator();

                while(iter.hasNext()) {
                    total += iter.next();
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
            public HashSet<Long> data = new HashSet<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i * N));
                }
            }

            @Benchmark
            public HashSet<Long> loopPopulate() {
                var result = new HashSet<Long>(data.size());
                var rnd = new Random();

                for (int i = 0; i < data.size(); i++) {
                    result.add(rnd.nextLong());
                }

                return result;
            }

            @Benchmark
            public HashSet<Long> iteratorPopulate() {
                var result = new HashSet<Long>(data.size());
                var rnd = new Random();

                for (var value : data) {
                    result.add(value + rnd.nextLong());
                }

                return result;
            }
        }

        public class Iterate {
            public HashSet<Long> data = new HashSet<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) i);
                }
            }

            @Benchmark
            public long loopIterate() {
                long count = 0L;
                var iter = data.iterator();

                while(iter.hasNext()) {
                    if(iter.next() > 0) count++;
                }

                return count;
            }

            @Benchmark
            public long iteratorIterate() {
                long count = 0L;

                for (var value : data) {
                    if (value > 0)
                        count++;
                }

                return count;
            }

        }

        public class Contains {
            public long target;
            public HashSet<Long> data = new HashSet<Long>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                target = (long) rnd.nextInt(101) * N;

                for (int i = 1; i <= N; i++) {
                    data.add((long) rnd.nextInt(101) * N);
                }
            }

            @Benchmark
            public boolean loopContains() {
                var iter =  data.iterator();

                while(iter.hasNext()) {
                    var value = iter.next();
                    if(value == target) {
                        return true;
                    }
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
            public HashSet<Long> data = new HashSet<Long>(N);

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
            public HashSet<Long> loopFilter() {
                var result = new HashSet<Long>();
                var iter = data.iterator();

                while(iter.hasNext()) {
                    var value = iter.next();
                    if(value >= 0) {
                        result.add(value);
                    }
                }

                return result;
            }

            @Benchmark
            public HashSet<Long> iteratorFilter() {
                var result = new HashSet<Long>();

                for (var value : data) {
                    if (value >= 0)
                        result.add(value);
                }

                return result;
            }

        }

        public class Copy {
            public HashSet<Long> data = new HashSet<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i));
                }
            }

            @Benchmark
            public HashSet<Long> loopCopy() {
                var copy = new HashSet<Long>(data.size());
                var iter = data.iterator();

                while(iter.hasNext()) {
                    copy.add(iter.next());
                }

                return copy;
            }

            @Benchmark
            public HashSet<Long> iteratorCopy() {
                var copy = new HashSet<Long>(data.size());

                for (var value : data) {
                    copy.add(value);
                }

                return copy;
            }
        }

        public class Map {
            public HashSet<Long> data = new HashSet<Long>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add((long) (i));
                }
            }

            @Benchmark
            public HashSet<Long> loopMap() {
                var result = new HashSet<Long>(data.size());
                var iter = data.iterator();

                while(iter.hasNext()) {
                    var value = iter.next();
                    result.add(value * N);
                }

                return result;
            }

            @Benchmark
            public HashSet<Long> iteratorMap() {
                var result = new HashSet<Long>(data.size());

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
            public HashSet<Student> students = new HashSet<Student>(N);

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
                var sb = new StringBuilder();
                var iter = students.iterator();

                while(iter.hasNext()) {
                    var s = iter.next();
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
            public HashSet<Student> loopPopulate() {
                var rnd = new Random();
                var result = new HashSet<Student>(students.size());

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
            public HashSet<Student> iteratorPopulate() {
                var rnd = new Random();
                var result = new HashSet<Student>(students.size());

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
            public HashSet<Student> students = new HashSet<Student>(N);

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

                while(iter.hasNext()) {
                    var s = iter.next();
                    if (s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE) {
                        ++count;
                    }
                }

                return count;
            }

            @Benchmark
            public int iteratorIterate() {
                int count = 0;
                for (var s : students) {
                    if (s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE) {
                        ++count;
                    }
                }

                return count;
            }
        }

        public class Contains {
            public int target;
            public HashSet<Student> students = new HashSet<Student>(N);

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
            public boolean loopContains() {
                var iter = students.iterator();
                while(iter.hasNext()) {
                    var s = iter.next();
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
            public HashSet<Student> students = new HashSet<Student>(N);

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
            public HashSet<Student> loopFilter() {
                var result = new HashSet<Student>(students.size());
                var iter = students.iterator();

                while(iter.hasNext()) {
                    var s = iter.next();
                    if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > target) {
                        result.add(s);
                    }
                }

                return result;
            }

            @Benchmark
            public HashSet<Student> iteratorFilter() {
                var result = new HashSet<Student>(students.size());
                for (var s : students) {
                    if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > target) {
                        result.add(s);
                    }
                }

                return result;
            }
        }

        public class Copy {
            public HashSet<Student> students = new HashSet<Student>(N);

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
            public HashSet<Student> loopCopy() {
                var result = new HashSet<Student>(students.size());
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
            public HashSet<Student> iteratorCopy() {
                var result = new HashSet<Student>(students.size());
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
            public HashSet<Student> students = new HashSet<Student>(N);

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
