package com.benchmarks.HashSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import com.benchmarks.Student;

import org.openjdk.jmh.annotations.*;
public class HashSetClass {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"1000"})
        public int N;
        public int target;

        public HashSet<Student> students;
        public ArrayList<Integer> range;

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

        @Setup(Level.Trial)
        public void setupData() {
            var rnd = new Random();
            int maxF = firstNames.size();
            int maxL = lastNames.size();

            target = rnd.nextInt(N - (-N)) - N;
            students = new HashSet<Student>(N);

            for (int i = 1; i <= N; i++) {
                var s = new Student() {
                    {
                        average = rnd.nextInt(100 - 50) - 50;
                        ID = rnd.nextLong();
                        firstName = firstNames.get(rnd.nextInt(maxF));
                        lastName = lastNames.get(rnd.nextInt(maxL));
                    }
                };

                students.add(s);
            }

            range = new ArrayList<Integer>(N);
            for (int i = 1; i <= N; i++) {
                range.add(i);
            }
        }
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String lambdaReduce(Bench b) {
        return b.students.stream()
                .map(s -> String.format("%s, %s, %s", s.lastName, s.firstName,
                        (s.average > 60) ? Integer.toString(s.average) : "Failed"))
                .collect(StringBuilder::new, (sb, s) -> sb.append(s), (sb1, sb2) -> sb1.append(sb2.toString()))
                .toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String loopReduce(Bench b) {
        var sb = new StringBuilder();
        var iter = b.students.iterator();

        while (iter.hasNext()) {
            var s = iter.next();
            var average = s.average;
            var passed = average > 60 ? Integer.toString(average) : "Failed";
            sb.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
        }

        return sb.toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String iteratorReduce(Bench b) {
        var sb = new StringBuilder();

        for (var s : b.students) {
            var average = s.average;
            var passed = average > 60 ? Integer.toString(average) : "Failed";
            sb.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
        }

        return sb.toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> lambdaPopulate(Bench b) {
        var rnd = new Random();
        int maxF = b.firstNames.size();
        int maxL = b.lastNames.size();

        return b.range.stream().map(i -> new Student() {
            {
                firstName = b.firstNames.get(rnd.nextInt(maxF));
                lastName = b.lastNames.get(rnd.nextInt(maxL));
                average = rnd.nextInt(100 - 50) - 50;
                ID = i + rnd.nextLong();
            }
        }).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> loopPopulate(Bench b) {
        var rnd = new Random();
        var result = new HashSet<Student>(b.range.size());

        int maxF = b.firstNames.size();
        int maxL = b.lastNames.size();

        for (int i = 0; i < b.range.size(); i++) {
            var s = i;
            result.add(new Student() {
                {
                    firstName = b.firstNames.get(rnd.nextInt(maxF));
                    lastName = b.lastNames.get(rnd.nextInt(maxL));
                    average = rnd.nextInt(100 - 50) - 50;
                    ID = s + rnd.nextLong();
                }
            });
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> iteratorPopulate(Bench b) {
        var rnd = new Random();
        var result = new HashSet<Student>(b.range.size());

        int maxF = b.firstNames.size();
        int maxL = b.lastNames.size();

        for (var s : b.range) {
            result.add(new Student() {
                {
                    firstName = b.firstNames.get(rnd.nextInt(maxF));
                    lastName = b.lastNames.get(rnd.nextInt(maxL));
                    average = rnd.nextInt(100 - 50) - 50;
                    ID = s + rnd.nextLong();
                }
            });
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int lambdaIterate(Bench b) {
        return (int) b.students.stream()
                .filter(s -> s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE).count();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int loopIterate(Bench b) {
        int count = 0;
        var iter = b.students.iterator();

        while (iter.hasNext()) {
            var s = iter.next();
            if (s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int iteratorIterate(Bench b) {
        int count = 0;
        for (var s : b.students) {
            if (s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean lambdaContains(Bench b) {
        return b.students.stream().anyMatch(
                s -> s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es"));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean loopContains(Bench b) {
        var iter = b.students.iterator();
        while (iter.hasNext()) {
            var s = iter.next();
            if (s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                return true;
            }
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public boolean iteratorContains(Bench b) {
        for (var s : b.students) {
            if (s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                return true;
            }
        }

        return false;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> lambdaFilter(Bench b) {
        return b.students.stream()
                .filter(s -> s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > b.target)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> loopFilter(Bench b) {
        var result = new HashSet<Student>(b.students.size());
        var iter = b.students.iterator();

        while (iter.hasNext()) {
            var s = iter.next();
            if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > b.target) {
                result.add(s);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> iteratorFilter(Bench b) {
        var result = new HashSet<Student>(b.students.size());
        for (var s : b.students) {
            if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > b.target) {
                result.add(s);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> lambdaCopy(Bench b) {
        return b.students.stream().map(s -> new Student() {
            {
                average = s.average;
                ID = s.ID;
                firstName = s.firstName;
                lastName = s.lastName;
            }
        }).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> loopCopy(Bench b) {
        var result = new HashSet<Student>(b.students.size());
        var iter = b.students.iterator();

        while (iter.hasNext()) {
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

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashSet<Student> iteratorCopy(Bench b) {
        var result = new HashSet<Student>(b.students.size());
        for (var s : b.students) {
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

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<Long, String> lambdaMap(Bench b) {
        return new HashMap<Long, String>(b.students.stream()
                .collect(Collectors.toMap(s -> s.ID, s -> String.format("%s, %s", s.lastName, s.firstName))));

    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<Long, String> loopMap(Bench b) {
        var result = new HashMap<Long, String>(b.students.size());
        var iter = b.students.iterator();

        while (iter.hasNext()) {
            var s = iter.next();
            var value = String.format("%s, %s", s.lastName, s.firstName);

            result.put(s.ID, value);
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<Long, String> iteratorMap(Bench b) {
        var result = new HashMap<Long, String>(b.students.size());
        for (var s : b.students) {
            var value = String.format("%s, %s", s.lastName, s.firstName);
            result.put(s.ID, value);
        }

        return result;
    }
}
