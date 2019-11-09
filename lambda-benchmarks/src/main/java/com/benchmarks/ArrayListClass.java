package com.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class ArrayListClass {

    @State(Scope.Benchmark)
    public static class Bench {

        @Param({ "10", "100", "1000", "10000" })
        public static int N;
        public static int target;
        public static ArrayList<Student> students = new ArrayList<Student>(N);
        public static ArrayList<Integer> data = new ArrayList<Integer>(N);

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
        public void setupStudents() {
            var rnd = new Random();
            int maxF = firstNames.size();
            int maxL = lastNames.size();
            target = rnd.nextInt(Bench.N - (-Bench.N)) - Bench.N;

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
            var sb = new StringBuilder();
            var size = Bench.students.size();

            for (int i = 0; i < size; i++) {
                var s = Bench.students.get(i);
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
    }

    public static class Populate {

        @Benchmark
        public ArrayList<Student> lambdaPopulate() {
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
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Student> loopPopulate() {
            int size = Bench.data.size();
            int maxF = Bench.firstNames.size();
            int maxL = Bench.lastNames.size();
            
            var rnd = new Random();
            var result = new ArrayList<Student>(size);

            for (int i = 0; i < size; i++) {
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
        public ArrayList<Student> iteratorPopulate() {
            int maxF = Bench.firstNames.size();
            int maxL = Bench.lastNames.size();
            int size = Bench.students.size();

            var rnd = new Random();
            var result = new ArrayList<Student>(size);


            for (var i : Bench.data) {
                result.add(new Student() {
                    {
                        firstName = Bench.firstNames.get(rnd.nextInt(maxF));
                        lastName = Bench.lastNames.get(rnd.nextInt(maxL));
                        average = rnd.nextInt(100 - 50) - 50;
                        ID = i + rnd.nextLong();
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
            int size = Bench.students.size();

            for (int i = 0; i < size; i++) {
                var s = Bench.students.get(i);
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
            int size = Bench.students.size();

            for (int i = 0; i < size; i++) {
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
        public ArrayList<Student> lambdaFilter() {
            return Bench.students.stream()
                    .filter(s -> s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > Bench.target)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        @Benchmark
        public ArrayList<Student> loopFilter() {
            int size = Bench.students.size();
            var result = new ArrayList<Student>(size);

            for (int i = 0; i < size; i++) {
                var s = Bench.students.get(i);
                if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > Bench.target) {
                    result.add(s);
                }
            }

            return result;
        }

        @Benchmark
        public ArrayList<Student> iteratorFilter() {
            int size = Bench.students.size();
            var result = new ArrayList<Student>(size);
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
        public ArrayList<Student> lambdaCopy() {
            return Bench.students.stream().map(s -> new Student() {
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
            int size = Bench.students.size();
            var result = new ArrayList<Student>(size);

            for (int i = 0; i < size; i++) {
                var s = Bench.students.get(i);

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
            int size = Bench.students.size();
            var result = new ArrayList<Student>(size);

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
            int size = Bench.students.size();
            var result = new HashMap<Long, String>(size);

            for (int i = 0; i < size; i++) {
                var s = Bench.students.get(i);
                var value = String.format("%s, %s", s.lastName, s.firstName);

                result.put(s.ID, value);
            }

            return result;
        }

        @Benchmark
        public HashMap<Long, String> iteratorMap() {
            int size = Bench.students.size();
            var result = new HashMap<Long, String>(size);

            for (var s : Bench.students) {
                var value = String.format("%s, %s", s.lastName, s.firstName);
                result.put(s.ID, value);
            }

            return result;
        }
    }
}