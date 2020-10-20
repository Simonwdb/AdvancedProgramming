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
			// need to change this, because in this class there can't be a PrintStream
//			out.println(e);
			// don't know if this will work
			System.out.println(e);
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

	T expression(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = term(input);
		skipWhiteSpace(input);
		while (nextCharIsAdditive(input)) {
			result = calculate(result, nextChar(input), term(input));
			skipWhiteSpace(input);
		}
		return (T) result;
	}

	T term(Scanner input) throws APException {
		skipWhiteSpace(input);
		SetInterface<BigInteger> result = factor(input);
		skipWhiteSpace(input);
		while (nextCharIs(input, '*')) {
			result = calculate(result, nextChar(input), factor(input));
			skipWhiteSpace(input);
		}
		return (T) result;
	}

	T factor(Scanner input) throws APException {
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
		skipWhiteSpace(input);
		if (nextCharIs(input, '}')) { // catch empty sets
			character(input, '}');
			return (T) result;
		} else {
			result = row_natural_numbers(input);
			character(input, '}');
			return (T) result;
		}
	}

	T row_natural_numbers(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (!nextCharIsDigit(input)) {
			throw new APException("Row does not start with a number");
		}
		result.add(natural_number(input));
		skipWhiteSpace(input);
		while (nextCharIs(input, ',')) {
			character(input, ',');
			skipWhiteSpace(input);
			if (nextCharIsDigit(input)) {
				result.add(natural_number(input));
			}
		}

		return (T) result;
	}
	
	private boolean startNaturalNumWithZero(StringBuffer sb) {
		System.out.println("Checking in boolean for nat.number starting with 0");
		Scanner in = new Scanner(sb.toString());
		in.useDelimiter("");
		if (! nextCharIsNotZero(in)) {
			in.next();
			if (nextCharIsDigit(in)) {
				return true;
			}
		}
		System.out.println("Boolean leaving with false");
		return false;
	}
	
	private StringBuffer testZero(Scanner input) throws APException {
		StringBuffer sb = new StringBuffer();
		if (! nextCharIsNotZero(input)) {
			input.next();
			if (nextCharIsDigit(input)) {
				throw new APException("Natural number can not start with zero");
			}
			sb.append(nextChar(input));
		}
		return sb;
	}
	
	private void checkNaturalNumber(StringBuffer sb) throws APException {
		Scanner in = new Scanner(sb.toString());
		in.useDelimiter("");
		if (! nextCharIsNotZero(in)) {
			in.next();
			if (nextCharIsDigit(in)) {
				throw new APException("Natural Number can not start with zero");
			}
		}
	}
	
	BigInteger natural_number(Scanner input) throws APException {
		StringBuffer sb = new StringBuffer();
		skipWhiteSpace(input);
		
		while(input.hasNext()) {
			if (nextCharIsDigit(input)) {
				sb.append(nextChar(input));
			} else if (nextCharIs(input, ',') || nextCharIs(input, '}')) {
				break;
			} else if (nextCharIsWhiteSpace(input)) {
				skipWhiteSpace(input);
				if (nextCharIsDigit(input)) {
					throw new APException("no spaces allowed between natural numbers");
				}
			} else if (! nextCharIsDigit(input)) {
				throw new APException("only numbers are allowed");
			}
		}
		
		// check if Natural Number start with zero and has more numbers
		checkNaturalNumber(sb);
		
		return new BigInteger(sb.toString());
	}

// from this

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
	
// to this are the methods we are not currently using, shall we delete them?

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
}
