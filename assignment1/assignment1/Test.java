package assignment1;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {
	
	PrintStream out;
	Scanner in;
	StringBuffer sb1;
	StringBuffer sb2;
	
	Test () {
		out = new PrintStream(System.out);
		in = new Scanner(System.in);
		in.useDelimiter("");
		sb1 = new StringBuffer();
		sb2 = new StringBuffer();
		
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
	
	boolean isCharLetterOrNumber (Scanner in) {
		return nextCharIsLetter(in) || nextCharIsDigit(in);
	}
	
	boolean inputContainsCorrectSet (Scanner input, Set set) throws Exception {
		Scanner s = new Scanner(input.nextLine());
		s.useDelimiter("");
		
		if (! nextCharIs(s, '{')) {
			throw new Exception ("'{' is missing at the beginning.");
		} else {
			s.next();
		}
		
		if (! nextCharIsLetter (s)) {
			throw new Exception ("First identifier doesn't start with a letter.");
		}
		
		Identifier i = new Identifier();
		
		while (s.hasNext()) {
			if (isCharLetterOrNumber (s) ) {
				i.add(nextChar(s));
			} else {
				i.sb.deleteCharAt(0);
				set.add(i);
				i = new Identifier();
				s.next();
			}
			
			// (nextCharIs(s, '}') && s.hasNext() doesn't work, don't know why
			
			if (nextCharIs(s, '}') && s.hasNext()) {
				throw new Exception ("'}' is unexpected.");
			}
			
		}
//		printSet(set);
	
		return true;
	}
	
	public void printSet(Set set) {
		for (int i = 0; i < set.index; i++) {
			out.printf("%s \n", set.s[i].sb.toString());
		}
	}
	
	public void printId (Identifier id) {
		out.println(id.sb.toString());
	}
	
	
    boolean askSet (Scanner input, String question, Set set) {
        do {
            out.printf("%s", question);
            if (! input.hasNextLine()) {
                out.printf("\n"); // otherwise line with ^D will be overwritten
                return false;
            }
        } while (! inputContainsCorrectSet(input, set));
        return true;
    }
	
    boolean askBothSets (Scanner input, Set set1, Set set2) {
        return askSet(input, "Give first set : ", set1) &&
               askSet(input, "Give second set : ", set2);
    }
    
    void calculateAndGiveOutput (Set set1, Set set2) {
//    	out.println(set1);
//    	out.println(set2);
    }
    

	
	void start() {
		Set set1 = new Set(),
			set2 = new Set();
	
		try {
			inputContainsCorrectSet(in, set1);
		} catch (Exception e) {
			out.println(e);
		}

//
//        while (askBothSets(in, set1, set2)) {
//            calculateAndGiveOutput(set1, set2);
//        }
		
		
	}

	public static void main(String[] args) {
		new Test().start();
	}

}
