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

	private boolean skipWhiteSpace(Scanner in) {
		while (nextCharIsWhiteSpace(in)) {
			nextChar(in);
		}
		return true;
	}
	
	private boolean nextCharIsAdditive(Scanner in) {
		if (nextCharIs(in, '+')) {
			return true;
		} else if (nextCharIs(in, '|')) {
			return true;
		} else if (nextCharIs(in, '-')) {
			return true;
		}
		return false;
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

	private void statement(Scanner input) throws APException {
		skipWhiteSpace(input);
		Identifier id = new Identifier();
		if (nextCharIsLetter(input)) {
			id = identifier(input);
		}
		skipWhiteSpace(input);
		
		if (nextCharIs(input, '=')) {
			assignment(input, id);
		} else if (nextCharIs(input, '?')) {
			print_statement(input);
		} else if (nextCharIs(input, '/')) {
			comment(input);
		} else {
			throw new APException("...");
		}
	}

	private void assignment(Scanner input, Identifier id) throws APException {
		skipWhiteSpace(input);
		character(input, '=');
		skipWhiteSpace(input);
		T value = expression(input);
		eoln(input);
		list.put(id, value);
	}

	T print_statement(Scanner input) throws APException {
		T result;
		character(input, '?');
		skipWhiteSpace(input);
		result = expression(input);
		eoln(input);
		return result;
	}

	private void comment(Scanner input) throws APException {
		character(input, '/');
		eoln(input);
	}

	Identifier identifier(Scanner input) throws APException {
		if (!nextCharIsLetter(input)) {
			throw new APException("Identifier does not start with a letter.");
		}
		Identifier result = new Identifier();
		while (nextCharIsLetterOrNumber(input)) {
			result.add(nextChar(input));
		}
		return result;
	}

// expression = term { additive_operator term}
	T expression(Scanner input) throws APException {
		// input = B + {4, 5, 6}
		SetInterface<BigInteger> result = factor(input);
		// if nextCharIs 'additive'
		if (input.hasNext() && nextCharIsAdditive(input)) {
			// Do we need to give the operator also to the result?
			 result.add(term(input));
		}
		return (T) result;
	}

//	term = factor {multiplicative_operator factor}	still needs to be created

	T term(Scanner input) throws APException {
		// input = A * {4, 5, 6}
		SetInterface<BigInteger> result = factor(input);
		// if nextCharIs '*'
		if (input.hasNext() && nextCharIs(input, '*')) {
			// Do we need to give the operator also to the result?
			character(input, '*');
			 result.add(factor(input));
		}
		return (T) result;
	}
	
	T getIdentifier (Scanner input) {
		Identifier id = new Identifier();
		while (input.hasNext()) {
			id.add(nextChar(input));
		}
		return list.get(id);
	}

	T factor(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIsLetter(input)) {
			result = getIdentifier(input);
		} else if (nextCharIs(input, '{')) {
			result = new Set();
			result.add(nextChar(input));
		} else if (nextCharIs(input, '(')) {
			result = complex_factor(input);
		} else {
			throw new APException("Input is not a factor.");
		}
		return (T) result;
	}

	T complex_factor(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		character(input, '(');
		result.add(expression(input));
		character(input, ')');
		return (T) result;
	}

	T set(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		character(input, '{');
		result.add(row_natural_numbers(input));
		character(input, '}');
		return (T) result;
	}

	T row_natural_numbers(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result.add(natural_number(input));
		while (nextCharIs(input, ',')) {
			character(input, ',');
			result.add(natural_number(input));
		}
		

		return (T) result;

	}

	T additive_operator(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIs(input, '+')) {
			result = calculate('+');
		} else if (nextCharIs(input, '|')) {
			result = calculate('|');
		} else if (nextCharIs(input, '-')) {
			result = calculate('-');
		} else {
			throw new APException("Input does not contain an additive operator");
		}
		return (T) result;
	}

	T multiplicative_operator(Scanner input) throws APException {
		character(input, '*');
		SetInterface<BigInteger> set = new Set<BigInteger>();
		// how can we give the right sets in the calculate method?
		set = calculate(set, set, '*');
		return (T) set;
	}

	T calculate(SetInterface<BigInteger> set1, SetInterface<BigInteger> set2, char c) {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (c == '+') {
			result = set1.union(set2);
		} else if (c == '|') {
			result = set1.symmetricDifference(set2);
		} else if (c == '-') {
			result = set1.difference(set2);
		} else if (c == '*') {
			result = set1.intersection(set2);
		}

		return (T) result;
	}

	BigInteger natural_number(Scanner input) throws APException {
		StringBuffer sb = new StringBuffer();
		if (!nextCharIsNotZero(input)) {
			throw new APException("natural_number can not start with 0");
		}

		while (input.hasNext() && !nextCharIs(input, ',')) {
			if (nextCharIsWhiteSpace(input)) {
				throw new APException("Spaces are not allowed in natural numbers.");
			}
			if (nextCharIsDigit(input)) {
				sb.append(input.next());
			} else {
				throw new APException("Input does not contain only numbers.");
			}
		}
		return new BigInteger(sb.toString());
	}

	BigInteger positive_number(Scanner input) throws APException {
		if (!nextCharIsNotZero(input)) {
			throw new APException("Character is not a positive number.");
		}
		StringBuffer sb = new StringBuffer(nextChar(input));

		while (!nextCharIsWhiteSpace(input) && input.hasNext()) {
			sb.append(nextChar(input));
		}

		return new BigInteger(sb.toString());

	}

	BigInteger number(Scanner input) {
		if (nextCharIs(input, '0')) {
			return zero();
		} else {
			return not_zero(input);
		}
	}

	BigInteger zero() {
		return BigInteger.ZERO;
	}

	BigInteger not_zero(Scanner input) {
		StringBuffer sb = new StringBuffer(nextChar(input));
		return new BigInteger(sb.toString());
	}

	void eoln(Scanner input) throws APException {
		skipWhiteSpace(input);
		if (input.hasNext()) {
			throw new APException("Row still has items left, where eoln is expected.");
		}
	}

	void character(Scanner input, char c) throws APException {
		if (!nextCharIs(input, c)) {
			String s = new String("Character does not contain ");
			throw new APException(s + c);
		}
		nextChar(input);
	}

	void test() {
		try {
			out.printf("Give some input : ");
			Scanner in = new Scanner(System.in);
			Scanner row = new Scanner(in.nextLine());
			row.useDelimiter("");

			out.println(row_natural_numbers(row));
		} catch (APException e) {
			out.println(e);
		}
	}

	public static void main(String[] args) {
		new Interpreter().test();
	}

}
