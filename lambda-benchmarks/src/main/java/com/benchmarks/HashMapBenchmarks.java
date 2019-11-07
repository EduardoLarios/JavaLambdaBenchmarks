package com.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

public class HashMapBenchmarks {
    public class IntegerBenchmarks {
        public class Reduce {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Integer, Integer> data = new HashMap<Integer, Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public int loopReduce() {
                int total = 0;
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    total += data.get(key);
                }

                return total;
            }

            @Benchmark
            public int iteratorReduce() {
                int total = 0;
                for (var key : data.keySet()) {
                    total += data.get(key);
                }

                return total;
            }
        }

        public class Populate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Integer> data = new ArrayList<Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public HashMap<Integer, Integer> loopPopulate() {
                var map = new HashMap<Integer, Integer>(N);

                for (int i = 0; i < data.size(); i++) {
                    map.put(i, i * 5);
                }

                return map;
            }

            @Benchmark
            public HashMap<Integer, Integer> iteratorPopulate() {
                var map = new HashMap<Integer, Integer>(N);

                for (var value : data) {
                    map.put(value, value * 5);
                }

                return map;
            }
        }

        public class Iterate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Integer, Integer> data = new HashMap<Integer, Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public int loopIterate() {
                int count = 0;
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    if(data.get(key) < Integer.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }

            @Benchmark
            public int iteratorIterate() {
                int count = 0;
                for (var value : data.keySet()) {
                    if(data.get(value) < Integer.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }
        }

        public class Contains {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public int target;
            public HashMap<Integer, Integer> data = new HashMap<Integer, Integer>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int max = N;
                int min = -N;

                target = rnd.nextInt(max - min) - min;

                for (int i = 1; i <= N; i++) {
                    data.put(i, rnd.nextInt(max - min) - min);
                }
            }

            @Benchmark
            public boolean loopContains() {
                var iter = data.keySet().iterator();
                while(iter.hasNext()) {
                    if(data.get(iter.next()) == target) {
                        return true;
                    }
                }

                return false;
            }

            @Benchmark
            public boolean iteratorContains() {
                for (var key : data.keySet()) {
                    if(data.get(key) == target) {
                        return true;
                    }
                }

                return false;
            }
        }

        public class Filter {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Integer, Integer> data = new HashMap<Integer, Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public HashMap<Integer, Integer> loopFilter() {
                var iter = data.keySet().iterator();
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
                for (var key : data.keySet()) {
                    if(key % 2 == 0) {
                        result.put(key, key * 10);
                    }
                }

                return result;
            }
        }

        public class Copy {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Integer, Integer> data = new HashMap<Integer, Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public HashMap<Integer, Integer> loopCopy() {
                var result = new HashMap<Integer, Integer>(data.size());
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    result.put(key, data.get(key));
                }

                return result;
            }

            @Benchmark
            public HashMap<Integer, Integer> iteratorCopy() {
                var result = new HashMap<Integer, Integer>(data.size());
                for (var key : data.keySet()) {
                    result.put(key, data.get(key));
                }

                return result;
            }
        }

        public class Map {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Integer, Integer> data = new HashMap<Integer, Integer>(N);

            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public HashMap<Integer, Integer> loopMap() {
                var result = new HashMap<Integer, Integer>(data.size());
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    var value = data.get(key);

                    result.put(key * 10, value * 10);
                }

                return result;
            }

            @Benchmark
            public HashMap<Integer, Integer> iteratorMap() {
                var result = new HashMap<Integer, Integer>(data.size());
                for (var key : data.keySet()) {
                    var value = data.get(key);
                    result.put(key * 10, value * 10);
                }

                return result;
            }
        }
    }

    public class LongBenchmarks {
        public class Reduce {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Long, Long> data = new HashMap<Long, Long>(N);

            @Setup
            public void setupData() {
                for (long i = 1; i <= N; i++) {
                    data.put(i * 10, i * N);
                }
            }

            @Benchmark
            public long loopReduce() {
                long total = 0;
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    total += data.get(key);
                }

                return total;
            }

            @Benchmark
            public long iteratorReduce() {
                long total = 0;
                for (var key : data.keySet()) {
                    total += data.get(key);
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
                for (long i = 1; i <= N; i++) {
                    data.add(i);
                }
            }

            @Benchmark
            public HashMap<Long, Long> loopPopulate() {
                var map = new HashMap<Long, Long>(N);

                for (long i = 0; i < data.size(); i++) {
                    map.put(i, i * 5);
                }

                return map;
            }

            @Benchmark
            public HashMap<Long, Long> iteratorPopulate() {
                var map = new HashMap<Long, Long>(N);

                for (var value : data) {
                    map.put(value, value * 5);
                }

                return map;
            }
        }

        public class Iterate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Long, Long> data = new HashMap<Long, Long>(N);

            @Setup
            public void setupData() {
                for (long i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public long loopIterate() {
                long count = 0;
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    if(data.get(key) < Long.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }

            @Benchmark
            public long iteratorIterate() {
                long count = 0;
                for (var key : data.keySet()) {
                    if(data.get(key) < Long.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }
        }

        public class Contains {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public long target;
            public HashMap<Long, Long> data = new HashMap<Long, Long>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                int max = N;
                int min = -N;

                target = rnd.nextInt(max - min) - min;

                for (long i = 1; i <= N; i++) {
                    data.put(i, (long)rnd.nextInt(max - min) - min);
                }
            }

            @Benchmark
            public boolean loopContains() {
                var iter = data.keySet().iterator();
                while(iter.hasNext()) {
                    if(data.get(iter.next()) == target) {
                        return true;
                    }
                }

                return false;
            }

            @Benchmark
            public boolean iteratorContains() {
                for (var key : data.keySet()) {
                    if(data.get(key) == target) {
                        return true;
                    }
                }

                return false;
            }
        }

        public class Filter {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Long, Long> data = new HashMap<Long, Long>(N);

            @Setup
            public void setupData() {
                for (long i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public HashMap<Long, Long> loopFilter() {
                var iter = data.keySet().iterator();
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
                for (var key : data.keySet()) {
                    if(key % 2 == 0) {
                        result.put(key, key * 10);
                    }
                }

                return result;
            }
        }

        public class Copy {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Long, Long> data = new HashMap<Long, Long>(N);

            @Setup
            public void setupData() {
                for (long i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public HashMap<Long, Long> loopCopy() {
                var result = new HashMap<Long, Long>(data.size());
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    result.put(key, data.get(key));
                }

                return result;
            }

            @Benchmark
            public HashMap<Long, Long> iteratorCopy() {
                var result = new HashMap<Long, Long>(data.size());
                for (var key : data.keySet()) {
                    result.put(key, data.get(key));
                }

                return result;
            }
        }

        public class Map {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<Long, Long> data = new HashMap<Long, Long>(N);

            @Setup
            public void setupData() {
                for (long i = 1; i <= N; i++) {
                    data.put(i, i * 10);
                }
            }

            @Benchmark
            public HashMap<Long, Long> loopMap() {
                var result = new HashMap<Long, Long>(data.size());
                var iter = data.keySet().iterator();

                while(iter.hasNext()) {
                    var key = iter.next();
                    var value = data.get(key);

                    result.put(key * 10, value * 10);
                }

                return result;
            }

            @Benchmark
            public HashMap<Long, Long> iteratorMap() {
                var result = new HashMap<Long, Long>(data.size());
                for (var key : data.keySet()) {
                    var value = data.get(key);
                    result.put(key * 10, value * 10);
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
            public HashMap<String, Student> data = new HashMap<String, Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;
                

                for (int i = 1; i <= N; i++) {
                    int n = i;
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", i, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    data.put(key, student);
                }
            }

            @Benchmark
            public String loopReduce() {
                var builder = new StringBuilder();
                var iter = data.keySet().iterator();

                while (iter.hasNext()) {
                    var key = iter.next();
                    var student = data.get(key);
                    var item = String.format("%s : %s,%s - %s",
                        key, 
                        student.firstName,
                        student.lastName,
                        (student.average > 60) ? Integer.toString(student.average) : "Failed");

                    builder.append(item);
                }

                return builder.toString();
            }

            @Benchmark
            public String iteratorReduce() {
                var builder = new StringBuilder();
                for (var key : data.keySet()) {
                    var student = data.get(key);
                    var item = String.format("%s : %s,%s - %s",
                        key, 
                        student.firstName,
                        student.lastName,
                        (student.average > 60) ? Integer.toString(student.average) : "Failed");

                    builder.append(item);
                }

                return builder.toString();
            }
        }

        public class Populate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public ArrayList<Integer> data = new ArrayList<Integer>(N);
            
            @Setup
            public void setupData() {
                for (int i = 1; i <= N; i++) {
                    data.add(i);
                }
            }
            
            @Benchmark
            public HashMap<String, Student> loopPopulate() {
                var result = new HashMap<String, Student>(N);
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;
                
                for (int i = 0; i < data.size(); i++) {
                    int n = i;
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", i, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    result.put(key, student);
                }

                return result;
            }

            @Benchmark
            public HashMap<String, Student> iteratorPopulate() {
                var result = new HashMap<String, Student>(N);
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;

                for (var n : data) {
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", n, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    result.put(key, student);
                }

                return result;
            }
        }

        public class Iterate {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<String, Student> data = new HashMap<String, Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;
                

                for (int i = 1; i <= N; i++) {
                    int n = i;
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", i, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    data.put(key, student);
                }
            }

            @Benchmark
            public int loopIterate() {
                int count = 0;
                var iter = data.keySet().iterator();

                while (iter.hasNext()) {
                    var key = iter.next();
                    var val = data.get(key);

                    if(key.length() > 0 && key.contains("-") && val.average >= 50 && val.ID < Long.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }

            @Benchmark
            public int iteratorIterate() {
                int count = 0;
                for (var key : data.keySet()) {
                    var val = data.get(key);

                    if(key.length() > 0 && key.contains("-") && val.average >= 50 && val.ID < Long.MAX_VALUE) {
                        count++;
                    }
                }

                return count;
            }
        }

        public class Contains {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<String, Student> data = new HashMap<String, Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;
                

                for (int i = 1; i <= N; i++) {
                    int n = i;
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", i, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    data.put(key, student);
                }
            }

            @Benchmark
            public boolean loopContains() {
                var iter = data.keySet().iterator();
                while (iter.hasNext()) {
                    var key = iter.next();
                    var val = data.get(key);
    
                    if(val.average >= 70 && val.average <= 85 && val.firstName.contains(" ") && val.lastName.contains("ez")) {
                        return true;
                    }
                }

                return false;
            }

            @Benchmark
            public boolean iteratorContains() {
                for (var key : data.keySet()) {
                    var val = data.get(key);
                    if(val.average >= 70 && val.average <= 85 && val.firstName.contains(" ") && val.lastName.contains("ez")) {
                        return true;
                    }
                }

                return false;
            }
        }

        public class Filter {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<String, Student> data = new HashMap<String, Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;
                

                for (int i = 1; i <= N; i++) {
                    int n = i;
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", i, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    data.put(key, student);
                }
            }

            @Benchmark
            public HashMap<String, Student> loopFilter() {
                var iter = data.keySet().iterator();
                var result = new HashMap<String, Student>();

                while (iter.hasNext()) {
                    var k = iter.next();
                    var s = data.get(k);
                    if(s.average >= 70 && s.firstName.contains(" ") && s.lastName.contains(" ") && s.ID >= 0) {
                        result.put(k, s);
                    }
                }

                return result;
            }

            @Benchmark
            public HashMap<String, Student> iteratorFilter() {
                var result = new HashMap<String, Student>();
                for (var key : data.keySet()) {
                    var s = data.get(key);

                    if(s.average >= 70 && s.firstName.contains(" ") && s.lastName.contains(" ") && s.ID >= 0) {
                        result.put(key, s);
                    }
                }

                return result;
            }
        }

        public class Copy {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<String, Student> data = new HashMap<String, Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;
                

                for (int i = 1; i <= N; i++) {
                    int n = i;
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", i, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    data.put(key, student);
                }
            }

            @Benchmark
            public HashMap<String, Student> loopCopy() {
                var iter = data.keySet().iterator();
                var result = new HashMap<String, Student>(data.size());

                while (iter.hasNext()) {
                    var key = iter.next();
                    var og = data.get(key);
                    var student = new Student() {
                        {
                            average = og.average;
                            ID = og.ID;
                            firstName = og.firstName;
                            lastName = og.lastName;
                        }
                    };

                    result.put(key, student);
                }

                return result;
            }

            @Benchmark
            public HashMap<String, Student> iteratorCopy() {
                var result = new HashMap<String, Student>(data.size());
                for (var key : data.keySet()) {
                    var og = data.get(key);
                    var student = new Student() {
                        {
                            average = og.average;
                            ID = og.ID;
                            firstName = og.firstName;
                            lastName = og.lastName;
                        }
                    };

                    result.put(key, student);
                }

                return result;
            }
        }

        public class Map {
            @Param({ "10", "100", "1000", "10000" })
            public int N;
            public HashMap<String, Student> data = new HashMap<String, Student>(N);

            @Setup
            public void setupData() {
                var rnd = new Random();
                var names = ClassBenchmarks.firstNames;
                var surnames = ClassBenchmarks.lastNames;

                int ncount = names.size();
                int scount = surnames.size();
                int max = 101;
                int min = 50;
                

                for (int i = 1; i <= N; i++) {
                    int n = i;
                    var student = new Student() {
                        {
                            average = rnd.nextInt(max - min) - min;
                            ID = n * N;
                            firstName = names.get(rnd.nextInt(ncount));
                            lastName = surnames.get(rnd.nextInt(scount));
                        }
                    };

                    var key = String.format("%d - %c%c%d", i, 
                        student.firstName.charAt(0), 
                        student.lastName.charAt(0),
                        student.ID);

                    data.put(key, student);
                }
            }

            @Benchmark 
            public HashMap<Integer, String> loopMap() {
                var result = new HashMap<Integer, String>(data.size());
                var iter = data.keySet().iterator();

                for (int i = 0; iter.hasNext(); i++) {
                    var k = iter.next();
                    var s = data.get(k);

                    var item = String.format("%s,%s - %d", s.lastName, s.firstName, s.average);
                    result.put(i, item);
                }

                return result;
            }

            @Benchmark
            public HashMap<Integer, String> iteratorMap() {
                var result = new HashMap<Integer, String>(data.size());
                int i = 0;

                for (var key : data.keySet()) {
                    var s = data.get(key);

                    var item = String.format("%s,%s - %d", s.lastName, s.firstName, s.average);
                    result.put(i++, item);
                }

                return result;
            }
        }
    }
}