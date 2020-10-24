package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	private PrintStream out;
	private HashMap<Identifier, T> map;

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
		Identifier id = new Identifier();
		for (int i = 0; i < v.length(); i++) {
			id.add(v.charAt(i));
		}
		return map.get(id);
	}

	@Override
	public T eval(String s) {
		Scanner input = new Scanner(s);
		input.useDelimiter("");
		T result = null;
		try {
			result = statement(input);
		} catch (APException e) {
			out.println(e);
		}
		return result;
	}

	private T statement(Scanner input) throws APException {
		skipWhiteSpace(input);
		T result = null;
		if (nextCharIsLetterOrNumber(input)) {
			assignment(input);
		} else if (nextCharIs(input, '?')) {
			result = print_statement(input);
		} else if (nextCharIs(input, '/')) {
			comment(input);
		} else {
			throw new APException("Statement does not start with an assignment, print or comment");
		}
		return result;
	}

	private void assignment(Scanner input) throws APException {
		skipWhiteSpace(input);
		Identifier id = new Identifier();
		if (nextCharIsLetterOrNumber(input)) {
			id = identifier(input);
		}
		skipWhiteSpace(input);
		checkCharacter(input, '=');
		skipWhiteSpace(input);
		T value = expression(input);
		checkEoln(input);
		map.put(id, value);
	}

	T print_statement(Scanner input) throws APException {
		T result;
		checkCharacter(input, '?');
		skipWhiteSpace(input);
		result = expression(input);
		checkEoln(input);
		return result;
	}

	private void comment(Scanner input) throws APException {
		checkCharacter(input, '/');
	}

	private Identifier identifier(Scanner input) throws APException {
		if (!nextCharIsLetter(input)) {
			throw new APException("Identifier does not start with a letter.");
		}
		Identifier result = new Identifier();

		while (nextCharIsLetterOrNumber(input)) {
			result.add(nextChar(input));
		}
		if (nextCharIsWhiteSpace(input)) {
			skipWhiteSpace(input);
			if (nextCharIsLetterOrNumber(input)) {
				throw new APException("Identifier may not contain spaces");
			}
		}
		return result;
	}

	private T calculate(SetInterface<BigInteger> set1, char c, SetInterface<BigInteger> set2) throws APException {
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
			throw new APException("Wrong operater is given: " + c);
		}
	}

	private T expression(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = term(input);
		skipWhiteSpace(input);
		while (nextCharIsAdditive(input)) {
			result = calculate(result, nextChar(input), term(input));
			skipWhiteSpace(input);
		}
		return (T) result;
	}

	private T term(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = factor(input);
		skipWhiteSpace(input);
		while (nextCharIs(input, '*')) {
			result = calculate(result, nextChar(input), factor(input));
			skipWhiteSpace(input);
		}
		return (T) result;
	}

	private T factor(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIsLetter(input)) {
			result = map.get(identifier(input));
		} else if (nextCharIs(input, '{')) {
			result = set(input);
		} else if (nextCharIs(input, '(')) {
			result = complex_factor(input);
		} else {
			throw new APException("Input is not a factor.");
		}
		return (T) result;
	}

	private T complex_factor(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		checkCharacter(input, '(');
		result = (expression(input));
		checkCharacter(input, ')');
		return (T) result;
	}

	private T set(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		checkCharacter(input, '{');
		skipWhiteSpace(input);
		if (nextCharIs(input, '}')) { // catch empty sets
			checkCharacter(input, '}');
			return (T) result;
		} else {
			result = row_natural_numbers(input);
			checkCharacter(input, '}');
			return (T) result;
		}
	}

	private T row_natural_numbers(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (!nextCharIsDigit(input)) {
			handleNonDigits(input);
		}
		result.add(natural_number(input));
		skipWhiteSpace(input);
		while (nextCharIs(input, ',')) {
			checkCharacter(input, ',');
			skipWhiteSpace(input);
			if (nextCharIsDigit(input)) {
				result.add(natural_number(input));
			}
		}

		return (T) result;
	}

	private BigInteger natural_number(Scanner input) throws APException {
		skipWhiteSpace(input);
		return number(input);
	}

	private BigInteger positive_number(Scanner input) throws APException {
		StringBuffer sb = new StringBuffer();
		while (nextCharIsDigit(input)) {
			sb.append(nextChar(input));
		}
		if (nextCharIsWhiteSpace(input)) {
			skipWhiteSpace(input);
			if (nextCharIsDigit(input)) {
				throw new APException("No spaces allowed between digits of natural numbers");
			}
		}
		return new BigInteger(sb.toString());
	}

	private BigInteger number(Scanner input) throws APException {
		if (nextCharIs(input, '0')) {
			return zero(input);
		} else if (nextCharIsDigit(input)) {
			return positive_number(input);
		} else {
			throw new APException("Input does not contain only numbers");
		}
	}

	private BigInteger zero(Scanner input) throws APException {
		input.next();
		skipWhiteSpace(input);
		if (nextCharIsDigit(input)) {
			throw new APException("Natural number can not start with 0");
		} else {
			return BigInteger.ZERO;
		}
	}

	private void handleNonDigits(Scanner input) throws APException {
		if (nextCharIs(input, '-')) {
			throw new APException("Natural number can not be negative");
		}
		if (nextCharIs(input, '+')) {
			throw new APException("Natural number can not be signed");
		} else {
			throw new APException("Natural number does not start with a number");
		}
	}

	private void checkEoln(Scanner input) throws APException {
		skipWhiteSpace(input);
		if (input.hasNext()) {
			throw new APException("Row still has items left, where eoln is expected.");
		}
	}

	private void checkCharacter(Scanner input, char c) throws APException {
		if (!nextCharIs(input, c)) {
			if (c == '{') {
				throw new APException("Set does not start with '{'");
			} else if (c == '}') {
				throw new APException("Set does not end with '}'");
			} else {
				String s = new String("Character does not contain ");
				throw new APException(s + c);
			}
		}
		nextChar(input);
	}
}
