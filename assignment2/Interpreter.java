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
	HashMap<Identifier, T> map;

	public Interpreter() {
		out = new PrintStream(System.out);
		map = new HashMap<Identifier, T>();
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
		return nextCharIsDigit(in) || nextCharIsLetter(in);
	}

	private void skipWhiteSpace(Scanner in) {
		while (nextCharIsWhiteSpace(in)) {
			nextChar(in);
		}
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

		if (nextCharIsLetter(input)) {
			assignment(input);
		} else if (nextCharIs(input, '?')) {
			print_statement(input);
		} else if (nextCharIs(input, '/')) {
			comment(input);
		} else {
			throw new APException("Statement does not start with an assignment, print or comment");
		}
	}

	private void assignment(Scanner input) throws APException {
		skipWhiteSpace(input);
		Identifier id = new Identifier();
		if (nextCharIsLetter(input)) {
			id = identifier(input);
		}
		skipWhiteSpace(input);
		character(input, '=');
		skipWhiteSpace(input);
		T value = expression(input);
		eoln(input);
		map.put(id, value);
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
	}

	Identifier identifier(Scanner input) throws APException {
		if (!nextCharIsLetter(input)) {
			throw new APException("Identifier does not start with a letter.");
		}
		Identifier result = new Identifier(nextChar(input));

		while (nextCharIsLetterOrNumber(input)) {
			result.add(nextChar(input));
		}
		return result;
	}
	
	T calculate(SetInterface<BigInteger> set1, char c, SetInterface<BigInteger> set2) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (c == '*') {
			return (T) set1.intersection(set2);
		} else if (c == '+') {
			return (T) set1.union(set2);
		} else if (c == '-') {
			return (T) set1.difference(set2);
		} else if (c == '|') {
			return (T) set1.symmetricDifference(set2);
		} else {
			throw new APException("wrong operater is given: " + c);
		}
	}

// expression = term { additive_operator term}
	T expression(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = term(input);
		skipWhiteSpace(input);
		if (nextCharIsAdditive(input)) {
			result = calculate(result, nextChar(input), term(input));
		}
		return (T) result;
	}

//	term = factor {multiplicative_operator factor}	still needs to be created

	T term(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = factor(input);
		skipWhiteSpace(input);
		if (nextCharIs(input, '*')) {
			result = calculate(result, nextChar(input), factor(input));
		}
		return (T) result;
	}

	T getIdentifier(Scanner input) {
		Identifier id = new Identifier();
		while (nextCharIsLetterOrNumber(input)) {
			id.add(nextChar(input));
		}
		return map.get(id);
	}

	T factor(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIsLetter(input)) {
			result = getIdentifier(input);
		} else if (nextCharIs(input, '{')) {
			result = set(input);
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
		result = (expression(input));
		character(input, ')');
		return (T) result;
	}

	T set(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		character(input, '{');
		result = row_natural_numbers(input);
		character(input, '}');
		return (T) result;
	}

	T row_natural_numbers(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result.add(natural_number(input));
		skipWhiteSpace(input);
		while (nextCharIs(input, ',')) {
			character(input, ',');
			skipWhiteSpace(input);
			result.add(natural_number(input));
		}
		return (T) result;
	}

	BigInteger natural_number(Scanner input) throws APException {
		StringBuffer sb = new StringBuffer();
		skipWhiteSpace(input);
		if (!nextCharIsNotZero(input)) {
			throw new APException("natural_number can not start with 0");
		}

		while (input.hasNext() && !nextCharIs(input, ',')) {
			skipWhiteSpace(input);
			if (nextCharIsDigit(input)) {
				sb.append(input.next());
			} else if (nextCharIs(input, '}')) {
				break;
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
			statement(row);
			out.println(" ");
		} catch (APException e) {
			out.println(e);
		}
	}

	public static void main(String[] args) {
		new Interpreter().test();
	}

}
