package Lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class Employee {
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class Main {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("Some NameX", 25));
        list.add(new Employee("Some NameY", 35));
        list.add(new Employee("Some NameZ", 42));
        list.add(new Employee("Some NameK", 19));

        String x = "someString1";
        String y = "someOtherString2";
        String z = makeSomeString((p1, p2) -> p1.concat(p2), x, y);
        System.out.println(z);

        printWithPredicateCondition(list, employee -> employee.getAge() > 20);
    }

    private static void printWithPredicateCondition(List<Employee> list, Predicate<Employee> predicate) {
        list.forEach((employee -> {
            if (predicate.test(employee)) {
                String name = employee.getName();
                int age = employee.getAge();
                System.out.println("Name: " + name + ", Age: " + age);
            }
        }));
    }

    private static String makeSomeString(SomeInterface someInterface, String p1, String p2) {
        return someInterface.doStuff(p1, p2);
    }
}

interface SomeInterface {
    public String doStuff(String s1, String s2);
}
