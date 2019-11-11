package com.benchmarks.HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.benchmarks.Student;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class HashMapClass {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
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

        public static HashMap<String, Student> students;
        public static ArrayList<Integer> range;

        @Setup(Level.Trial)
        public void setupData() {
            var rnd = new Random();
            var names = firstNames;
            var surnames = lastNames;

            int mnames = names.size();
            int msurnames = surnames.size();
            int max = 101;
            int min = 50;

            for (int i = 1; i <= N; i++) {
                int n = i;
                var student = new Student() {
                    {
                        average = rnd.nextInt(max - min) - min;
                        ID = n * N;
                        firstName = names.get(rnd.nextInt(mnames));
                        lastName = surnames.get(rnd.nextInt(msurnames));
                    }
                };

                var key = String.format("%d - %c%c%d", i, student.firstName.charAt(0), student.lastName.charAt(0),
                        student.ID);

                students.put(key, student);
            }
        }

        @Setup(Level.Trial)
        public void setupRange() {
            range = new ArrayList<Integer>(N);
            for (int i = 1; i <= N; i++) {
                range.add(i);
            }
        }
    }

    public static class Reduce {

        @Benchmark
        public String lambdaReduce() {
            var sb = new StringBuilder();
            Bench.students.keySet().forEach(k -> {
                var student = Bench.students.get(k);
                var item = String.format("%s : %s,%s - %s", k, student.firstName, student.lastName,
                        (student.average > 60) ? Integer.toString(student.average) : "Failed");
                sb.append(item);
            });

            return sb.toString();
        }

        @Benchmark
        public String loopReduce() {
            var builder = new StringBuilder();
            var iter = Bench.students.keySet().iterator();

            while (iter.hasNext()) {
                var key = iter.next();
                var student = Bench.students.get(key);
                var item = String.format("%s : %s,%s - %s", key, student.firstName, student.lastName,
                        (student.average > 60) ? Integer.toString(student.average) : "Failed");

                builder.append(item);
            }

            return builder.toString();
        }

        @Benchmark
        public String iteratorReduce() {
            var builder = new StringBuilder();
            for (var key : Bench.students.keySet()) {
                var student = Bench.students.get(key);
                var item = String.format("%s : %s,%s - %s", key, student.firstName, student.lastName,
                        (student.average > 60) ? Integer.toString(student.average) : "Failed");

                builder.append(item);
            }

            return builder.toString();
        }
    }

    public static class Populate {

        @Benchmark
        public HashMap<String, Student> lambdaPopulate() {
            var rnd = new Random();
            var names = Bench.firstNames;
            var surnames = Bench.lastNames;

            int ncount = names.size();
            int scount = surnames.size();
            int max = 101;
            int min = 50;

            var result = new HashMap<String, Student>(Bench.N);
            Bench.range.forEach(i -> {
                int n = i;
                Student student = new Student() {
                    {
                        average = rnd.nextInt(max - min) - min;
                        ID = n * Bench.N;
                        firstName = names.get(rnd.nextInt(ncount));
                        lastName = surnames.get(rnd.nextInt(scount));
                    }
                };

                var key = String.format("%d - %c%c%d", i, student.firstName.charAt(0), student.lastName.charAt(0),
                        student.ID);

                result.put(key, student);
            });

            return result;
        }

        @Benchmark
        public HashMap<String, Student> loopPopulate() {
            var result = new HashMap<String, Student>(Bench.N);
            var rnd = new Random();
            var names = Bench.firstNames;
            var surnames = Bench.lastNames;

            int ncount = names.size();
            int scount = surnames.size();
            int max = 101;
            int min = 50;

            for (int i = 0; i < Bench.range.size(); i++) {
                int n = i;
                var student = new Student() {
                    {
                        average = rnd.nextInt(max - min) - min;
                        ID = n * Bench.N;
                        firstName = names.get(rnd.nextInt(ncount));
                        lastName = surnames.get(rnd.nextInt(scount));
                    }
                };

                var key = String.format("%d - %c%c%d", i, student.firstName.charAt(0), student.lastName.charAt(0),
                        student.ID);

                result.put(key, student);
            }

            return result;
        }

        @Benchmark
        public HashMap<String, Student> iteratorPopulate() {
            var result = new HashMap<String, Student>(Bench.N);
            var rnd = new Random();
            var names = Bench.firstNames;
            var surnames = Bench.lastNames;

            int ncount = names.size();
            int scount = surnames.size();
            int max = 101;
            int min = 50;

            for (var i : Bench.range) {
                var student = new Student() {
                    {
                        average = rnd.nextInt(max - min) - min;
                        ID = i * Bench.N;
                        firstName = names.get(rnd.nextInt(ncount));
                        lastName = surnames.get(rnd.nextInt(scount));
                    }
                };

                var key = String.format("%d - %c%c%d", i, student.firstName.charAt(0), student.lastName.charAt(0),
                        student.ID);

                result.put(key, student);
            }

            return result;
        }
    }

    public static class Iterate {

        @Benchmark
        public int lambdaIterate() {
            var map = Bench.students;
            return (int) Bench.students.keySet().stream().filter(k -> k.length() > 0 && k.contains("-")
                    && map.get(k).average >= 50 && map.get(k).ID < Long.MAX_VALUE).count();
        }

        @Benchmark
        public int loopIterate() {
            int count = 0;
            var iter = Bench.students.keySet().iterator();

            while (iter.hasNext()) {
                var key = iter.next();
                var val = Bench.students.get(key);

                if (key.length() > 0 && key.contains("-") && val.average >= 50 && val.ID < Long.MAX_VALUE) {
                    count++;
                }
            }

            return count;
        }

        @Benchmark
        public int iteratorIterate() {
            int count = 0;
            for (var key : Bench.students.keySet()) {
                var val = Bench.students.get(key);

                if (key.length() > 0 && key.contains("-") && val.average >= 50 && val.ID < Long.MAX_VALUE) {
                    count++;
                }
            }

            return count;
        }
    }

    public static class Contains {

        @Benchmark
        public boolean lambdaContains() {
            return Bench.students.values().stream().anyMatch(
                    s -> s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("ez"));
        }

        @Benchmark
        public boolean loopContains() {
            var iter = Bench.students.keySet().iterator();
            while (iter.hasNext()) {
                var key = iter.next();
                var val = Bench.students.get(key);

                if (val.average >= 70 && val.average <= 85 && val.firstName.contains(" ")
                        && val.lastName.contains("ez")) {
                    return true;
                }
            }

            return false;
        }

        @Benchmark
        public boolean iteratorContains() {
            for (var key : Bench.students.keySet()) {
                var val = Bench.students.get(key);
                if (val.average >= 70 && val.average <= 85 && val.firstName.contains(" ")
                        && val.lastName.contains("ez")) {
                    return true;
                }
            }

            return false;
        }
    }

    public static class Filter {

        @Benchmark
        public HashMap<String, Student> lambdaFilter() {
            return new HashMap<String, Student>(Bench.students.entrySet().stream().filter(kvp -> 
                kvp.getValue().average >= 70 &&
                kvp.getValue().firstName.contains(" ") && 
                kvp.getValue().lastName.contains(" ") &&
                kvp.getValue().ID >= 0).collect(Collectors.toMap(kvp -> kvp.getKey(), kvp -> kvp.getValue())));
        }

        @Benchmark
        public HashMap<String, Student> loopFilter() {
            var iter = Bench.students.keySet().iterator();
            var result = new HashMap<String, Student>();

            while (iter.hasNext()) {
                var k = iter.next();
                var s = Bench.students.get(k);
                if (s.average >= 70 && s.firstName.contains(" ") && s.lastName.contains(" ") && s.ID >= 0) {
                    result.put(k, s);
                }
            }

            return result;
        }

        @Benchmark
        public HashMap<String, Student> iteratorFilter() {
            var result = new HashMap<String, Student>();
            for (var key : Bench.students.keySet()) {
                var s = Bench.students.get(key);

                if (s.average >= 70 && s.firstName.contains(" ") && s.lastName.contains(" ") && s.ID >= 0) {
                    result.put(key, s);
                }
            }

            return result;
        }
    }

    public static class Copy {

        @Benchmark
        public HashMap<String, Student> lambdaCopy() {
            return new HashMap<String, Student>(Bench.students.entrySet().stream().collect(Collectors.toMap(kvp -> kvp.getKey(), kvp -> kvp.getValue())));
        }

        @Benchmark
        public HashMap<String, Student> loopCopy() {
            var iter = Bench.students.keySet().iterator();
            var result = new HashMap<String, Student>(Bench.students.size());

            while (iter.hasNext()) {
                var key = iter.next();
                var og = Bench.students.get(key);
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
            var result = new HashMap<String, Student>(Bench.students.size());
            for (var key : Bench.students.keySet()) {
                var og = Bench.students.get(key);
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

    public static class Map {

        @Benchmark
        public HashMap<Integer, String> lambdaMap() {
            var num = new Object() { int i = 0; };
            return new HashMap<Integer, String>(Bench.students.entrySet()
                .stream()
                .collect(Collectors.toMap(
                    kvp -> num.i++, 
                    kvp -> String.format("%s,%s - %d", kvp.getValue().lastName, kvp.getValue().firstName, kvp.getValue().average))));
        }

        @Benchmark
        public HashMap<Integer, String> loopMap() {
            var result = new HashMap<Integer, String>(Bench.students.size());
            var iter = Bench.students.keySet().iterator();

            for (int i = 0; iter.hasNext(); i++) {
                var k = iter.next();
                var s = Bench.students.get(k);

                var item = String.format("%s,%s - %d", s.lastName, s.firstName, s.average);
                result.put(i, item);
            }

            return result;
        }

        @Benchmark
        public HashMap<Integer, String> iteratorMap() {
            var result = new HashMap<Integer, String>(Bench.students.size());
            int i = 0;

            for (var key : Bench.students.keySet()) {
                var s = Bench.students.get(key);

                var item = String.format("%s,%s - %d", s.lastName, s.firstName, s.average);
                result.put(i++, item);
            }

            return result;
        }
    }
}
