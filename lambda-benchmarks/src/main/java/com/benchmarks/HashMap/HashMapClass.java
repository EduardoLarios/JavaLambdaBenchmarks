package com.benchmarks.HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import com.benchmarks.Student;

import org.openjdk.jmh.annotations.*;

public class HashMapClass {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000"})
        public int N;
        public List<String> firstNames = new ArrayList<String>(List.of(
                // Simple Male
                "Juan", "Carlos", "Manuel", "Francisco", "Mauricio", "Eduardo",
                // Simple Female
                "Fernanda", "María", "Sofía", "Ana", "Carla", "Marlene",
                // Composite Male
                "Juan Manuel", "Luis Carlos", "Manuel Alejandro", "Javier Francisco", "Luis Eduardo", "José Luis",
                // Composite Female
                "María Fernanda", "María Jose", "Sofía Paulina", "Ana Belén", "Daniela Alejandra", "Luz Angélica"));

        public List<String> lastNames = new ArrayList<String>(List.of("García", "Rodríguez", "Hernández",
                "López", "Martínez", "González", "Pérez", "Sánchez", "Ramírez", "Torres", "Flores", "Rivera", "Gómez",
                "Díaz", "Cruz", "Morales", "Reyes", "Gutiérrez", "Ortiz"));

        public HashMap<String, Student> students;
        public ArrayList<Integer> range;

        @Setup(Level.Trial)
        public void setupData() {
            var rnd = new Random();
            var names = firstNames;
            var surnames = lastNames;

            int mnames = names.size();
            int msurnames = surnames.size();
            int max = 101;
            int min = 50;

            range = new ArrayList<Integer>(N);
            students = new HashMap<String, Student>(N);

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
            
            for (int i = 1; i <= N; i++) {
                range.add(i);
            }
        }
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String lambdaReduce(Bench b) {
        var sb = new StringBuilder();
        b.students.keySet().forEach(k -> {
            var student = b.students.get(k);
            var item = String.format("%s : %s,%s - %s", k, student.firstName, student.lastName,
                    (student.average > 60) ? Integer.toString(student.average) : "Failed");
            sb.append(item);
        });

        return sb.toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String loopReduce(Bench b) {
        var builder = new StringBuilder();
        var iter = b.students.keySet().iterator();

        while (iter.hasNext()) {
            var key = iter.next();
            var student = b.students.get(key);
            var item = String.format("%s : %s,%s - %s", key, student.firstName, student.lastName,
                    (student.average > 60) ? Integer.toString(student.average) : "Failed");

            builder.append(item);
        }

        return builder.toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String iteratorReduce(Bench b) {
        var builder = new StringBuilder();
        for (var key : b.students.keySet()) {
            var student = b.students.get(key);
            var item = String.format("%s : %s,%s - %s", key, student.firstName, student.lastName,
                    (student.average > 60) ? Integer.toString(student.average) : "Failed");

            builder.append(item);
        }

        return builder.toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> lambdaPopulate(Bench b) {
        var rnd = new Random();
        var names = b.firstNames;
        var surnames = b.lastNames;

        int ncount = names.size();
        int scount = surnames.size();
        int max = 101;
        int min = 50;

        var result = new HashMap<String, Student>(b.N);
        b.range.forEach(i -> {
            int n = i;
            Student student = new Student() {
                {
                    average = rnd.nextInt(max - min) - min;
                    ID = n * b.N;
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

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> loopPopulate(Bench b) {
        var result = new HashMap<String, Student>(b.N);
        var rnd = new Random();
        var names = b.firstNames;
        var surnames = b.lastNames;

        int ncount = names.size();
        int scount = surnames.size();
        int max = 101;
        int min = 50;

        for (int i = 0; i < b.range.size(); i++) {
            int n = i;
            var student = new Student() {
                {
                    average = rnd.nextInt(max - min) - min;
                    ID = n * b.N;
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

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> iteratorPopulate(Bench b) {
        var result = new HashMap<String, Student>(b.N);
        var rnd = new Random();
        var names = b.firstNames;
        var surnames = b.lastNames;

        int ncount = names.size();
        int scount = surnames.size();
        int max = 101;
        int min = 50;

        for (var i : b.range) {
            var student = new Student() {
                {
                    average = rnd.nextInt(max - min) - min;
                    ID = i * b.N;
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

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int lambdaIterate(Bench b) {
        var map = b.students;
        return (int) b.students.keySet().stream().filter(k -> k.length() > 0 && k.contains("-")
                && map.get(k).average >= 50 && map.get(k).ID < Long.MAX_VALUE).count();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int loopIterate(Bench b) {
        int count = 0;
        var iter = b.students.keySet().iterator();

        while (iter.hasNext()) {
            var key = iter.next();
            var val = b.students.get(key);

            if (key.length() > 0 && key.contains("-") && val.average >= 50 && val.ID < Long.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int iteratorIterate(Bench b) {
        int count = 0;
        for (var key : b.students.keySet()) {
            var val = b.students.get(key);

            if (key.length() > 0 && key.contains("-") && val.average >= 50 && val.ID < Long.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean lambdaContains(Bench b) {
        return b.students.values().stream().anyMatch(
                s -> s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("ez"));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean loopContains(Bench b) {
        var iter = b.students.keySet().iterator();
        while (iter.hasNext()) {
            var key = iter.next();
            var val = b.students.get(key);

            if (val.average >= 70 && val.average <= 85 && val.firstName.contains(" ")
                    && val.lastName.contains("ez")) {
                return true;
            }
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean iteratorContains(Bench b) {
        for (var key : b.students.keySet()) {
            var val = b.students.get(key);
            if (val.average >= 70 && val.average <= 85 && val.firstName.contains(" ")
                    && val.lastName.contains("ez")) {
                return true;
            }
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> lambdaFilter(Bench b) {
        return new HashMap<String, Student>(b.students.entrySet().stream().filter(kvp -> 
            kvp.getValue().average >= 70 &&
            kvp.getValue().firstName.contains(" ") && 
            kvp.getValue().lastName.contains(" ") &&
            kvp.getValue().ID >= 0).collect(Collectors.toMap(kvp -> kvp.getKey(), kvp -> kvp.getValue())));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> loopFilter(Bench b) {
        var iter = b.students.keySet().iterator();
        var result = new HashMap<String, Student>();

        while (iter.hasNext()) {
            var k = iter.next();
            var s = b.students.get(k);
            if (s.average >= 70 && s.firstName.contains(" ") && s.lastName.contains(" ") && s.ID >= 0) {
                result.put(k, s);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> iteratorFilter(Bench b) {
        var result = new HashMap<String, Student>();
        for (var key : b.students.keySet()) {
            var s = b.students.get(key);

            if (s.average >= 70 && s.firstName.contains(" ") && s.lastName.contains(" ") && s.ID >= 0) {
                result.put(key, s);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> lambdaCopy(Bench b) {
        return new HashMap<String, Student>(b.students.entrySet().stream().collect(Collectors.toMap(kvp -> kvp.getKey(), kvp -> kvp.getValue())));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> loopCopy(Bench b) {
        var iter = b.students.keySet().iterator();
        var result = new HashMap<String, Student>(b.students.size());

        while (iter.hasNext()) {
            var key = iter.next();
            var og = b.students.get(key);
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

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<String, Student> iteratorCopy(Bench b) {
        var result = new HashMap<String, Student>(b.students.size());
        for (var key : b.students.keySet()) {
            var og = b.students.get(key);
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

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<Integer, String> lambdaMap(Bench b) {
        var num = new Object() { int i = 0; };
        return new HashMap<Integer, String>(b.students.entrySet()
            .stream()
            .collect(Collectors.toMap(
                kvp -> num.i++, 
                kvp -> String.format("%s,%s - %d", kvp.getValue().lastName, kvp.getValue().firstName, kvp.getValue().average))));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<Integer, String> loopMap(Bench b) {
        var result = new HashMap<Integer, String>(b.students.size());
        var iter = b.students.keySet().iterator();

        for (int i = 0; iter.hasNext(); i++) {
            var k = iter.next();
            var s = b.students.get(k);

            var item = String.format("%s,%s - %d", s.lastName, s.firstName, s.average);
            result.put(i, item);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<Integer, String> iteratorMap(Bench b) {
        var result = new HashMap<Integer, String>(b.students.size());
        int i = 0;

        for (var key : b.students.keySet()) {
            var s = b.students.get(key);

            var item = String.format("%s,%s - %d", s.lastName, s.firstName, s.average);
            result.put(i++, item);
        }

        return result;
    }
}
