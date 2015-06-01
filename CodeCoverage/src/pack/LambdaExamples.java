package pack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LambdaExamples {

	public static void main(String[] args) {

		Predicate<Integer> isOdd = (x) -> 0 == x % 2;
		executePredicate(25, isOdd);
		executePredicate(-24, isOdd);

		List<Integer> l = Arrays.asList(-1, 0, 1, 2, 3, 14, 25, 36, 111, 222, 1023, 2049);

		System.out.println("all numbers (1):");
		l.forEach(System.out::println);

		System.out.println("all numbers (2):");
		l.stream().filter((_x) -> true).forEach(System.out::println);

		System.out.println("odd numbers:");
		l.stream().filter(isOdd).forEach(System.out::println);

		System.out.println("3-digit numbers:");
		l.stream().filter((s) -> 100 <= s & s < 1000).forEach(System.out::println);

		System.out.println("positive numbers:");
		l.stream().filter((s) -> 0 < s).forEach(System.out::println);
	}

	private void myPrint(Object o) {
		System.out.println(o);
	}

	private static <T> void executePredicate(T v, Predicate<T> p) {
		System.out.println("" + v + " : " + p.test(v));
	}
}
