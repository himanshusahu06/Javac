import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Java8 {
    public static void main(String[] args) {
        NoArgLambda lambda = () -> System.out.println("Hello World");
        ThreeNumberLambda sumNumber = (x, y, z) -> x + y + z;
        GenericLambda<Integer, Double> genericLambda = (x, y) -> x * y.intValue();
        SingleArgumentLambda singleArgumentLambda = x -> x * x;

        lambda.apply();
        apply(() -> System.out.println("Hello world from type interference!!"));
        System.out.println(sumNumber.apply(1, 2, 3));
        System.out.println(genericLambda.apply(5, 6.7));
        System.out.println(singleArgumentLambda.apply(5));

        playWithPerson();
    }

    private static void playWithPerson() {
        List<Person> personList = Arrays.asList(
                new Person("Foo", "Bar", 45),
                new Person("Mark", "Steven", 38),
                new Person("Himanshu", "Sahu", 34),
                new Person("Walter", "White", 24),
                new Person("Jessy", "Pinkmen", 24)
        );

        System.out.println("Person whose last name is starting with S");
        // 1. print all person whose last name is starting with S
        printWithCondition(personList, person -> person.lastName.startsWith("S"));
        System.out.println("Person whose last name ends with n");
        // 2. print all person whose last name ends with n
        printWithPredicate(personList, person -> person.lastName.endsWith("n"));
        System.out.println("Person whose with age more than 24");
        // 3. print all person with age more than 24
        performWithConsumer(personList, person -> person.age > 24, p -> System.out.println(p.firstName));
    }

    /**
     * prints person from list which passes a condition test
     */
    private static void performWithConsumer(List<Person> personList, Condition<Person> condition, Consumer<Person> consumer) {
        personList.stream().filter(condition::test).forEach(consumer);
    }

    /**
     * prints person from list which passes a condition test
     */
    private static void printWithCondition(List<Person> personList, Condition<Person> condition) {
        personList.stream().filter(condition::test).forEach(System.out::println);
    }

    /**
     * prints person from list which passes a predicate test
     */
    private static void printWithPredicate(List<Person> personList, Predicate<Person> predicate) {
        personList.stream().filter(predicate).forEach(System.out::println);
    }

    private static class Person {
        String firstName;
        String lastName;
        int age;

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void apply(NoArgLambda lambda) {
        lambda.apply();
    }
}

/**
 * lambda interface will have exactly one abstract method
 */
interface NoArgLambda {
    void apply();
}

/**
 * marking a lambda with @FunctionalInterface gives a clue
 * to consumers that this interface is lambda so it prevent
 * consumers to add more methods in the interface.
 */
@FunctionalInterface
interface SingleArgumentLambda {
    int apply(int x);
}

interface ThreeNumberLambda {
    int apply(int x, int y, int z);
}

interface GenericLambda<X extends Number, Y extends Number> {
    Number apply(X x, Y y);
}

/**
 *  Represents a predicate (boolean-valued function) of one argument.
 */
@FunctionalInterface
interface Condition<T> {
    boolean test(T t);
}
