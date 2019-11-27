package com.benchmarks.LinkedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

import com.benchmarks.Student;

import org.openjdk.jmh.annotations.*;

public class LinkedListClass {

    @State(Scope.Thread)
    public static class Bench {
        @Param({"100","1000","10000","100000","1000000"})
        public int N;
        public int target;
        public LinkedList<Student> students;
        public LinkedList<Integer> data;

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

            data = new LinkedList<Integer>();
            for (int i = 1; i <= N; i++) {
                data.add(i);
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
        var builder = new StringBuilder(b.students.size());
        var iter = b.students.iterator();

        while (iter.hasNext()) {
            var s = iter.next();
            var average = s.average;
            var passed = average > 60 ? Integer.toString(average) : "Failed";
            builder.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
        }

        return builder.toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String iteratorReduce(Bench b) {
        var builder = new StringBuilder(b.students.size());

        for (var s : b.students) {
            var average = s.average;
            var passed = average > 60 ? Integer.toString(average) : "Failed";
            builder.append(String.format("%s, %s, %s", s.lastName, s.firstName, passed));
        }

        return builder.toString();
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Student> lambdaPopulate(Bench b) {
        var rnd = new Random();
        int maxF = b.firstNames.size();
        int maxL = b.lastNames.size();

        return b.data.stream().map(i -> new Student() {
            {
                firstName = b.firstNames.get(rnd.nextInt(maxF));
                lastName = b.lastNames.get(rnd.nextInt(maxL));
                average = rnd.nextInt(100 - 50) - 50;
                ID = i + rnd.nextLong();
            }
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Student> loopPopulate(Bench b) {
        var rnd = new Random();
        var result = new LinkedList<Student>();

        int max = 101;
        int min = 50;
        int fname = b.firstNames.size();
        int lname = b.lastNames.size();

        for (int i = 0; i < b.data.size(); i++) {
            int n = i;
            result.add(new Student() {
                {
                    average = rnd.nextInt(max - min) - min;
                    ID = n * b.N;
                    firstName = b.firstNames.get(rnd.nextInt(fname));
                    lastName = b.lastNames.get(rnd.nextInt(lname));
                }
            });
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Student> iteratorPopulate(Bench b) {
        var rnd = new Random();
        var result = new LinkedList<Student>();

        int max = 101;
        int min = 50;
        int fname = b.firstNames.size();
        int lname = b.lastNames.size();

        for (var i : b.data) {
            result.add(new Student() {
                {
                    average = rnd.nextInt(max - min) - min;
                    ID = i * b.N;
                    firstName = b.firstNames.get(rnd.nextInt(fname));
                    lastName = b.lastNames.get(rnd.nextInt(lname));
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
        while(iter.hasNext()) {
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
    public LinkedList<Student> lambdaFilter(Bench b) {
        return b.students.stream()
                .filter(s -> s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > b.target)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Student> loopFilter(Bench b) {
        var result = new LinkedList<Student>();
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
    public LinkedList<Student> iteratorFilter(Bench b) {
        var result = new LinkedList<Student>();
        for (var s : b.students) {
            if (s.average > 50 && s.average < 70 && s.firstName.contains("i") && s.ID > b.target) {
                result.add(s);
            }
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Student> lambdaCopy(Bench b) {
        return b.students.stream().map(s -> new Student() {
            {
                average = s.average;
                ID = s.ID;
                firstName = s.firstName;
                lastName = s.lastName;
            }
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public LinkedList<Student> loopCopy(Bench b) {
        var result = new LinkedList<Student>();
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
    public LinkedList<Student> iteratorCopy(Bench b) {
        var result = new LinkedList<Student>();

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
            result.put(s.ID, String.format("%s, %s", s.lastName, s.firstName));
        }

        return result;
    }

    @Benchmark @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public HashMap<Long, String> iteratorMap(Bench b) {
        var result = new HashMap<Long, String>(b.students.size());
        for (var s : b.students) {
            result.put(s.ID, String.format("%s, %s", s.lastName, s.firstName));
        }

        return result;
    }
}
