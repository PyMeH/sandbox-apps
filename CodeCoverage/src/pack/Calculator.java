package pack;

public class Calculator {

	@FunctionalInterface
	public static interface BinaryOperation {
		int o(int a, int b);
	}

	@FunctionalInterface
	public static interface TernaryOperation {
		int o(int a, int b, int c);
	}

	public int executeOperation(int a, int b, BinaryOperation op) {
		return op.o(a, b);
	}

	public int executeOperation(int a, int b, int c, TernaryOperation op) {
		return op.o(a, b, c);
	}

	public static void main(String[] args) {

		Calculator myApp = new Calculator();
		BinaryOperation subtraction = (a, b) -> a - b;
		System.out.println("40 - 2 = " + myApp.executeOperation(40, 2, subtraction));
		System.out.println("10 + 20 + 30 = " + myApp.executeOperation(10, 20, 30, (a, b, c) -> a + b + c));
	}

}