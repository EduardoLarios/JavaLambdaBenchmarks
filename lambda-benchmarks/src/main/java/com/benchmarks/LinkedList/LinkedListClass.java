package com.benchmarks.LinkedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

public class LinkedListClass {

    @State(Scope.Benchmark)
    public static class Bench {
        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static int target;

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

        public static LinkedList<Student> students;
        public static LinkedList<Student> contains;
        public static LinkedList<Student> filter;
        public static LinkedList<Integer> data;

        @Setup(Level.Trial)
        public void setupStudents() {
            var rnd = new Random();
            int maxF = firstNames.size();
            int maxL = lastNames.size();

            students = new LinkedList<Student>();
            target = rnd.nextInt((N / 2)) - 1;

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
        public void setupData() {
            data = new LinkedList<Integer>();
            for (int i = 1; i <= N; i++) {
                data.add(i);
            }
        }
    }

    public static class Reduce {

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
            var builder = new StringBuilder(Bench.students.size());
            var iter = Bench.students.iterator();

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
            var builder = new StringBuilder(Bench.students.size());

            for (var s : Bench.students) {
                var average = s.average;
                var passed = average > 60 ? Integer.toString(average) : "Failed";
                builder.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
            }

            return builder.toString();
        }
    }

    public static class Populate {

        @Benchmark
        public LinkedList<Student> lambdaPopulate() {
            var rnd = new Random();
            int maxF = Bench.firstNames.size();
            int maxL = Bench.lastNames.size();

            return Bench.data.stream().map(i -> new Student() {
                {
                    firstName = Bench.firstNames.get(rnd.nextInt(maxF));
                    lastName = Bench.lastNames.get(rnd.nextInt(maxL));
                    average = rnd.nextInt(100 - 50) - 50;
                    ID = i + rnd.nextLong();
                }
            }).collect(Collectors.toCollection(LinkedList::new));
        }

        @Benchmark
        public LinkedList<Student> loopPopulate() {
            var rnd = new Random();
            var result = new LinkedList<Student>();

            int max = 101;
            int min = 50;
            int fname = Bench.firstNames.size();
            int lname = Bench.lastNames.size();

            for (int i = 0; i < Bench.data.size(); i++) {
                int n = i;
                result.add(new Student() {
                    {
                        average = rnd.nextInt(max - min) - min;
                        ID = n * Bench.N;
                        firstName = Bench.firstNames.get(rnd.nextInt(fname));
                        lastName = Bench.lastNames.get(rnd.nextInt(lname));
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
            int fname = Bench.firstNames.size();
            int lname = Bench.lastNames.size();

            for (var i : Bench.data) {
                result.add(new Student() {
                    {
                        average = rnd.nextInt(max - min) - min;
                        ID = i * Bench.N;
                        firstName = Bench.firstNames.get(rnd.nextInt(fname));
                        lastName = Bench.lastNames.get(rnd.nextInt(lname));
                    }
                });
            }

            return result;
        }
    }

    public static class Iterate {

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
    }

    public static class Contains {

        @Benchmark
        public boolean lambdaContains() {
            return Bench.students.stream().anyMatch(
                    s -> s.average >= 70 && s.average <= 85 && s.firstName.contains(" ") && s.lastName.contains("es"));
        }

        @Benchmark
        public boolean loopContains() {
            for (int i = 0; i < Bench.students.size(); i++) {
                var s = Bench.students.get(i);
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
    }

    public static class Filter {

        @Benchmark
        public LinkedList<Student> lambdaFilter() {
            return Bench.students.stream()
                    .filter(s -> s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > Bench.target)
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        @Benchmark
        public LinkedList<Student> loopFilter() {
            var result = new LinkedList<Student>();
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
        public LinkedList<Student> iteratorFilter() {
            var result = new LinkedList<Student>();
            for (var s : Bench.students) {
                if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > Bench.target) {
                    result.add(s);
                }
            }

            return result;
        }
    }

    public static class Copy {

        @Benchmark
        public LinkedList<Student> lambdaCopy() {
            return Bench.students.stream().map(s -> new Student() {
                {
                    average = s.average;
                    ID = s.ID;
                    firstName = s.firstName;
                    lastName = s.lastName;
                }
            }).collect(Collectors.toCollection(LinkedList::new));
        }

        @Benchmark
        public LinkedList<Student> loopCopy() {
            var result = new LinkedList<Student>();
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
        public LinkedList<Student> iteratorCopy() {
            var result = new LinkedList<Student>();

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
    }

    public static class Map {

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
                result.put(s.ID, String.format("%s, %s", s.lastName, s.firstName));
            }

            return result;
        }

        @Benchmark
        public HashMap<Long, String> iteratorMap() {
            var result = new HashMap<Long, String>(Bench.students.size());
            for (var s : Bench.students) {
                result.put(s.ID, String.format("%s, %s", s.lastName, s.firstName));
            }

            return result;
        }
    }
}
