package assignment1;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Test3 {

	PrintStream out;
	Scanner in;

	Test3() {
		out = new PrintStream(System.out);
		in = new Scanner(System.in);
	}

	char nextChar(Scanner input) {
		return input.next().charAt(0);
	}

	boolean nextCharIs(Scanner in, char c) {
		return in.hasNext(Pattern.quote(c + ""));
	}

	boolean nextCharIsDigit(Scanner in) {
		return in.hasNext("[0-9]");
	}

	boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}

	boolean isLetterOrNumber(Scanner in) {
		return nextCharIsDigit(in) && nextCharIsLetter(in);
	}

	void addToSet(Identifier i, Set set) throws Exception {
		i.sb.deleteCharAt(0);
		if (!set.containsIdentifier(i)) {
			set.add(i);
		} else {
			System.out.println("duplicate id");
		}
	}

	boolean isCorrect(Scanner input, Set set) throws Exception {
		Scanner in = new Scanner(input.nextLine());
		in.useDelimiter("");
		Identifier i = new Identifier();
		boolean endFound = false;

		if (!nextCharIs(in, '{')) {
			throw new Exception("Set doesn't start with '{'");
		} else {
			in.next();
		}

		if (!nextCharIsLetter(in)) {
			throw new Exception("First char is not a letter");
		}

		while (in.hasNext()) {
			if (nextCharIsLetter(in) || nextCharIsDigit(in)) {
				i.add(nextChar(in));
			} else if (nextCharIs(in, ' ')) {
				addToSet(i, set);
				i = new Identifier();
				in.next();
			} else if (nextChar(in) == '}') {
				endFound = true;
				addToSet(i, set);
				if (in.hasNext()) {
					throw new Exception("Invalid input after '}'");
				}
			}
		}
		if (!endFound) {
			throw new Exception("Set does not end with '}'");
		}
		System.out.println("final set:");
		printSet(set);
		return true;
	}

	void printSet(Set set) {
		for (int i = 0; i < set.index; i++) {
			out.printf("%s ", set.s[i].sb.toString());
		}
	}

	void printId(Identifier i) {
		out.println(i.sb.toString());
	}

	void start() {
		out.printf("Give input : ");

		Set set = new Set();

		try {
			isCorrect(in, set);
		} catch (Exception e) {
			out.println();
			out.println(e);
		}

	}

	public static void main(String[] args) {
		new Test3().start();
	}

}
