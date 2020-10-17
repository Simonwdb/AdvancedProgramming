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
		result = result.copy();
		out.println("Print statement");
		while(!result.isEmpty()) {
			BigInteger temp = result.get();
			out.print(temp.toString() + " ");
			result.remove(temp);
		}
	}

	private void start() {
		InterpreterInterface<Set<BigInteger>> interpreter = new Interpreter<Set<BigInteger>>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			SetInterface<BigInteger> result = interpreter.eval(in.nextLine());
			if (result != null) {
				print(result);
			}
			;
		}


	}

	public static void main(String[] args) {
		new Main().start();
	}
}
