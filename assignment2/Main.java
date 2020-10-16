package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

public class Main<T> {
	
	private PrintStream out;
	
	Main() {
		out = new PrintStream(System.out);
	}
	
	private void print(T obj) {
		out.println("Print statement");
	}
	
	private void start() {
		InterpreterInterface<Set<BigInteger>> interpreter = new Interpreter<Set<BigInteger>>();
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			T temp = (T) interpreter.eval(in.nextLine());
			if ( temp != null) {
				// need to be printing
				print(temp);
			};
		}
		
		
		//1. Create a scanner on System.in
		//2. call interpreter.eval() on each line
		
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}
