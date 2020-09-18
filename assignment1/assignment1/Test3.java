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
			out.println("Set doesn't start with '{'");
			return false;
//            throw new Exception("Set doesn't start with '{'");
		} else {
			in.next();
		}

		if (!nextCharIsLetter(in)) {
			out.println("First char is not a letter");
			return false;
//            throw new Exception("First char is not a letter");
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
				if (in.hasNext() && nextChar(in) == ' ') {
					in.next();
					return true;
				} if (in.hasNext() && nextChar(in) != ' ') {
					out.println("Invalid input after '}'");
					return false;
				}
			}
		}
		if (!endFound) {
			out.println("Set does not end with '}'");
			return false;
		}
		return true;
	}

	void printSet(Set set) {
		out.print('{');
		for (int i = 0; i < set.index - 1; i++) {
			out.printf("%s ", set.s[i].sb.toString());
		}
		out.print(set.s[set.index - 1].sb.toString() + '}');
		out.println();
	}

	void printId(Identifier i) {
		out.println(i.sb.toString());
	}

	boolean askSet(Scanner input, String question, Set set) throws Exception {
		do {
			out.printf("%s", question);
			if (!input.hasNextLine()) {
				out.printf("\n"); // otherwise line with ^D will be overwritten
				return false;
			}
		} while (!isCorrect(input, set));
		return true;
	}

	boolean askBothSets(Scanner input, Set set1, Set set2) throws Exception {
		return askSet(input, "Give first set : ", set1) && askSet(input, "Give second set : ", set2);
	}

	void calculateAndGiveOutput(Set set1, Set set2) throws Exception {
		Set difference = set2.difference(set1);
		Set intersection = set2.intersection(set1);
		Set union = set2.union(set1);
		Set symDiff = set1.symmetricDifference(set2);
		out.print("difference = ");
		printSet(difference);
		out.print("intersection = ");
		printSet(intersection);
		out.print("union = ");
		printSet(union);
		out.print("sym. diff. = ");
		printSet(symDiff);
	}

	void start() {

		try {
			Set set1 = new Set(), set2 = new Set();
			while (askBothSets(in, set1, set2)) {
				calculateAndGiveOutput(set1, set2);
			}
		} catch (Exception e) {
			out.println(e);
		}

	}

	public static void main(String[] args) {
		new Test3().start();
	}

}
