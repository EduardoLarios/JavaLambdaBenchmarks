package com.benchmarks.HashSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

import com.benchmarks.Student;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class HashSetClass {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        private static int target;

        public static HashSet<Student> students;
        public static ArrayList<Integer> range;

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
        }

        @Setup(Level.Trial)
        public void setupRange() {
            range = new ArrayList<Integer>(N);
            for (int i = 1; i <= N; i++) {
                range.add(i);
            }
        }

    }

    @Benchmark
    public String lambdaReduce() {
        return Bench.students.stream()
                .map(s -> String.format("%s, %s, %s", s.lastName, s.firstName,
                        (s.average > 60) ? Integer.toString(s.average) : "Failed"))
                .collect(StringBuilder::new, (sb, s) -> sb.append(s), (sb1, sb2) -> sb1.append(sb2.toString()))
                .toString();
    }

    @Benchmark
    public String loopReduce() {
        var sb = new StringBuilder();
        var iter = Bench.students.iterator();

        while (iter.hasNext()) {
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

        for (var s : Bench.students) {
            var average = s.average;
            var passed = average > 60 ? Integer.toString(average) : "Failed";
            sb.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
        }

        return sb.toString();
    }

    @Benchmark
    public HashSet<Student> lambdaPopulate() {
        var rnd = new Random();
        int maxF = Bench.firstNames.size();
        int maxL = Bench.lastNames.size();

        return Bench.range.stream().map(i -> new Student() {
            {
                firstName = Bench.firstNames.get(rnd.nextInt(maxF));
                lastName = Bench.lastNames.get(rnd.nextInt(maxL));
                average = rnd.nextInt(100 - 50) - 50;
                ID = i + rnd.nextLong();
            }
        }).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark
    public HashSet<Student> loopPopulate() {
        var rnd = new Random();
        var result = new HashSet<Student>(Bench.range.size());

        int maxF = Bench.firstNames.size();
        int maxL = Bench.lastNames.size();

        for (int i = 0; i < Bench.range.size(); i++) {
            var s = i;
            result.add(new Student() {
                {
                    firstName = Bench.firstNames.get(rnd.nextInt(maxF));
                    lastName = Bench.lastNames.get(rnd.nextInt(maxL));
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
        var result = new HashSet<Student>(Bench.range.size());

        int maxF = Bench.firstNames.size();
        int maxL = Bench.lastNames.size();

        for (var s : Bench.range) {
            result.add(new Student() {
                {
                    firstName = Bench.firstNames.get(rnd.nextInt(maxF));
                    lastName = Bench.lastNames.get(rnd.nextInt(maxL));
                    average = rnd.nextInt(100 - 50) - 50;
                    ID = s + rnd.nextLong();
                }
            });
        }

        return result;
    }

    @Benchmark
    public int lambdaIterate() {
        return (int) Bench.students.stream()
                .filter(s -> s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE).count();
    }

    @Benchmark
    public int loopIterate() {
        int count = 0;
        var iter = Bench.students.iterator();

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
        for (var s : Bench.students) {
            if (s.firstName.length() > 0 && s.average >= 50 && s.ID < Long.MAX_VALUE) {
                count++;
            }
        }

        return count;
    }

    @Benchmark
    public boolean lambdaContains() {
        return Bench.students.stream().anyMatch(
                s -> s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es"));
    }

    @Benchmark
    public boolean loopContains() {
        var iter = Bench.students.iterator();
        while (iter.hasNext()) {
            var s = iter.next();
            if (s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                return true;
            }
        }

        return false;
    }

    @Benchmark
    public boolean iteratorContains() {
        for (var s : Bench.students) {
            if (s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es")) {
                return true;
            }
        }

        return false;
    }

    @Benchmark
    public HashSet<Student> lambdaFilter() {
        return Bench.students.stream()
                .filter(s -> s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > Bench.target)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark
    public HashSet<Student> loopFilter() {
        var result = new HashSet<Student>(Bench.students.size());
        var iter = Bench.students.iterator();

        while (iter.hasNext()) {
            var s = iter.next();
            if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > Bench.target) {
                result.add(s);
            }
        }

        return result;
    }

    @Benchmark
    public HashSet<Student> iteratorFilter() {
        var result = new HashSet<Student>(Bench.students.size());
        for (var s : Bench.students) {
            if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > Bench.target) {
                result.add(s);
            }
        }

        return result;
    }

    @Benchmark
    public HashSet<Student> lambdaCopy() {
        return Bench.students.stream().map(s -> new Student() {
            {
                average = s.average;
                ID = s.ID;
                firstName = s.firstName;
                lastName = s.lastName;
            }
        }).collect(Collectors.toCollection(HashSet::new));
    }

    @Benchmark
    public HashSet<Student> loopCopy() {
        var result = new HashSet<Student>(Bench.students.size());
        var iter = Bench.students.iterator();

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

    @Benchmark
    public HashSet<Student> iteratorCopy() {
        var result = new HashSet<Student>(Bench.students.size());
        for (var s : Bench.students) {
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
    public HashMap<Long, String> lambdaMap() {
        return new HashMap<Long, String>(Bench.students.stream()
                .collect(Collectors.toMap(s -> s.ID, s -> String.format("%s, %s", s.lastName, s.firstName))));

    }

    @Benchmark
    public HashMap<Long, String> loopMap() {
        var result = new HashMap<Long, String>(Bench.students.size());
        var iter = Bench.students.iterator();

        while (iter.hasNext()) {
            var s = iter.next();
            var value = String.format("%s, %s", s.lastName, s.firstName);

            result.put(s.ID, value);
        }

        return result;
    }

    @Benchmark
    public HashMap<Long, String> iteratorMap() {
        var result = new HashMap<Long, String>(Bench.students.size());
        for (var s : Bench.students) {
            var value = String.format("%s, %s", s.lastName, s.firstName);
            result.put(s.ID, value);
        }

        return result;
    }
}
