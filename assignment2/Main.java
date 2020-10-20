package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

public class Main<T> {

	private PrintStream out;

	Main() {
		out = new PrintStream(System.out);
	}

	private void print(SetInterface<BigInteger> result) {
		SetInterface<BigInteger> copyResult = result.copy();
		System.out.println("size copy " + copyResult.size());
		System.out.println("size result " + result.size());
		while(!copyResult.isEmpty()) {
			out.printf("%d ", copyResult.get());
			copyResult.remove(copyResult.get());

		}
		out.println(); // to create a new line in the interpreter
	}

	private void start() {
		InterpreterInterface<Set<BigInteger>> interpreter = new Interpreter<Set<BigInteger>>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			SetInterface<BigInteger> result = interpreter.eval(in.nextLine());
			if (result != null) {
				print(result);
			}
		}

	}

	public static void main(String[] args) {
		new Main().start();
	}
}
