package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A set interpreter for sets of elements of type T
 */
public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	// just for testing
	PrintStream out;
	HashMap<Identifier, T> list;
	
	public Interpreter() {
		out = new PrintStream(System.out);
		list = new HashMap<Identifier, T>();
	}
	
	private char nextChar(Scanner input) {
		return input.next().charAt(0);
	}

	private boolean nextCharIs(Scanner in, char c) {
		return in.hasNext(Pattern.quote(c + ""));
	}

	private boolean nextCharIsDigit(Scanner in) {
		return in.hasNext("[0-9]");
	}
	
	private boolean nextCharIsNotZero(Scanner in) {
		return in.hasNext("[1-9]");
	}

	private boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}
	
	private boolean nextCharIsWhiteSpace(Scanner in) {
		return in.hasNext(" ");
	}

	private boolean nextCharIsLetterOrNumber(Scanner in) {
		return nextCharIsDigit(in) && nextCharIsLetter(in);
	}
	
	@Override
	public T getMemory(String v) {
		// TODO Implement me
		return null;
	}

	@Override
	public T eval(String s) {
		// TODO Implement me
		return null;
	}
	
	private void printSet() {
		// Idea is to implement the printSet() to the Set class. 
		// so that the Set-object can call the printSet().
	}
	
	private void statement (Scanner input) throws APException {
		
		if (nextCharIs(input, '=')) {
			assignment(input);
		} else if (nextCharIs(input, '?')) {
			print_statement(input);
		} else if (nextCharIs(input, '/')) {
			comment(input);
		} else {
			throw new APException("...");
		}
	}
	
	T assignment (Scanner input) throws APException {
		T result;
		identifier (input);
		character (input, '=');
		expression (input);
		eoln (input);
		return result;
	}
	
	private void print_statement (Scanner input) throws APException {
		character(input, '?');
		expression(input);
		eoln(input);
//		printSet();	Needs to be created.
	}
	
	private void comment (Scanner input) throws APException {
		character(input, '/');
		eoln(input);
	}
	
	
	Identifier identifier(Scanner input) throws APException {
		if (! nextCharIsLetter(input)) {
			throw new APException ("Identifier does not start with a letter.");
		}
		Identifier result = new Identifier();
		while (nextCharIsLetterOrNumber(input)) {
			result.add(nextChar(input));
		}
		return result;
	}
	
	// expression = term { additive_operator term}
	T expression (Scanner input) throws APException {
		T result;
		return result;
	}
	
//	term = factor {multiplicative_operator factor}	still needs to be created
	
	T factor (Scanner input) throws APException {
		// still need to write this method
		return null;
	}
	
	private T getIdentifier (Scanner input) {
		// still need to write this method
		return null;
	}
	
	SetInterface<T> factor (Scanner input) throws APException {
		SetInterface<T> result = new Set<T>;
		if (nextCharIsLetter(input)){
//			result = getIdentifier(input);
		} else if (nextCharIs(input, '{')) {
			result = new Set();
			result.add(nextChar(input));
		} else if (nextCharIs(input, '(')) {
			result = complex_factor(input);
		} else {
			throw new APException ("Input is not a factor.");
		}
		return result;
	}
	
	
	SetInterface<T> complex_factor(Scanner input) throws APException {
		SetInterface<T> result = new Set<T>;
		character(input, '(');
//		expression(input);
		character(input, ')');
		return result;
	}
	
	SetInterface<T> set (Scanner input) throws APException {
		SetInterface<T> result = new Set<T>;
		character(input, '{');
//		result.add(row_natural_numbers(input));
		character(input, '}');
		return result;
	}
	
	SetInterface<T> row_natural_numbers (Scanner input) throws APException {
		SetInterface<T> result = new Set<T>;
		result.add(natural_number(input));
		while (nextCharIs(input, ',')) {
			character(input, ',');
			result.add(natural_number(input));
		}
		return result;
	}
	
	
	SetInterface<T> additive_operator (Scanner input) throws APException {
		SetInterface<T> result = new Set<T>;
		if (nextCharIs(input, '+')) {
//			result.union();
		} else if (nextCharIs(input, '|')) {
//			result.symmetricDifference();
		} else if (nextCharIs(input, '-')) {
//			result.difference();
		} else {
			throw new APException("Input does not contain an additive operator");
		}
		return result;
	}
	
	SetInterface<T> multiplicative_operator (Scanner input) throws APException {
		character(input, '*');
		SetInterface<T> set = new Set<T>;
		// how can we perform the operation?
//		result.intersection();
		return set;
	}

	BigInteger natural_number (Scanner input) throws APException {
		// if input is i.e. 010, it will return 10. Is this outcome allowed?
		String s = new String();
		while(input.hasNext()) {
			if (nextCharIsDigit(input)) {
				s = s + input.next();
			} else {
				throw new APException("Input does not contain only numbers.");
			}
		}
		return new BigInteger(s);
	}
	
	BigInteger positive_number (Scanner input) throws APException {
		if (! nextCharIsNotZero(input)) {
			throw new APException("Character is not a positive number.");
		}
		String s = new String(input.next());
		
		while (! nextCharIsWhiteSpace(input) && input.hasNext()) {
			s  = s + input.next();
		}
		
		return new BigInteger(s);
		
	}
	
	BigInteger number (Scanner input) {
		if (nextCharIs(input, '0')) {
			return zero();
		} else {
			return not_zero(input.next());
		}
	}
	
	BigInteger zero () {
		return BigInteger.ZERO;
	}
	
	BigInteger not_zero (String s) {
		return new BigInteger(s);
	}
	
	char Letter (char c) {
		// seems a bit unnecessary if performed this way
		return c;
	}
	
	void eoln (Scanner input) throws APException {
		if (input.hasNext()) {
			throw new APException("Row still has items left, where eoln is expected.");
		}
	}
	
	void character (Scanner input, char c) throws APException {
		if (! nextCharIs(input, c)) {
			String s = new String("Character does not contain ");
			throw new APException(s + c);
		}
		nextChar(input);
	}
	
	void test () {
		try {
		out.printf("Give some input : ");
		Scanner in = new Scanner(System.in);
		Scanner row = new Scanner(in.nextLine());
		row.useDelimiter("");
		
		out.println(set(row));
		} catch (APException e) {
			out.println(e);
		}
	}
	
	public static void main(String[] args) {
		new Interpreter().test();
	}

}
