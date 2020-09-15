package assignment1;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Test2 {
	
	PrintStream out;
	Scanner in;
	
	Test2() {
		out = new PrintStream(System.out);
		in = new Scanner(System.in);
	}
	
	char nextChar (Scanner input) {
		return input.next().charAt(0);
	}
	
	boolean nextCharIs (Scanner in, char c) {
		return in.hasNext(Pattern.quote(c+""));
	}
	
	boolean nextCharIsDigit (Scanner in) {
		return in.hasNext("[0-9]");
	}
	
	boolean nextCharIsLetter (Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}
	
	boolean isLetterOrNumber (Scanner in) {
		return nextCharIsDigit(in) && nextCharIsLetter(in);
	}
	
	boolean isCorrect (Scanner input, Set set) throws Exception {
		Scanner in = new Scanner(input.nextLine());
		in.useDelimiter("");
		
		if (! nextCharIs(in, '{')) {
			throw new Exception ("Serie doesn't start with '{'");
		} else {
			in.next();
		}
		
		if (! nextCharIsLetter(in)) {
			throw new Exception ("First char is not a letter");
		}

		
		while(in.hasNext()) {
			if (nextCharIsLetter(in) || nextCharIsDigit(in)) {
				out.printf("%s", nextChar(in));
			} else if (nextCharIs(in, ' ')) {
				out.printf("\nStarting with new Identifier\n", in.next());
			} else if (nextChar(in) == '}' /*nextCharIs(in, '}')*/ && in.hasNext()) {
			
				
				char c = nextChar(in);
				
				if (c == ' ' && in.hasNext() || c != ' ') {
					throw new Exception ("Invalid input after '}'");
				}
				
				// Need to work that when there is no '}' there will be also a new Exception thrown
				
			}
		}
		
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
	
	void start () {
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
		new Test2().start();
	}

}
