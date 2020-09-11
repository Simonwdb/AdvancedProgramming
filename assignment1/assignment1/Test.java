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
	
	boolean isSetWithBracket (StringBuffer s) {
		if (s.charAt(0) != '{') {
			out.printf("'{' is missing at the begin\n");
			return false;
		}
		if (s.charAt(s.length() - 1) != '}') {
			out.printf("'}' is missing at the end\n");
			return false;
		}
		return true;
	}
	
	boolean inputContainsCorrectSet(Scanner input, Set set) {
		Scanner test = new Scanner(input.nextLine());	// lees de gehele input als 1 lijn wordt '{a b c}
		StringBuffer s = new StringBuffer(test.nextLine());
		
		if (! isSetWithBracket(s)) {
			return false;
		}

		s.deleteCharAt(0);
		s.deleteCharAt(s.length() - 1);	// zorgt voor opschoning zodat StringBuffer 'a b c' wordt
	
		set.testAdd(s);
		
		return true;
	}
	
	boolean nextCharIs (Scanner in, char c) {
		return in.hasNext(Pattern.quote(c+""));
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
    	out.println(set1);
    	out.println(set2);
    }
	
	void start() {
		Set set1 = new Set(),
			set2 = new Set();
	

        while (askBothSets(in, set1, set2)) {
            calculateAndGiveOutput(set1, set2);
        }
		

		
	}

	public static void main(String[] args) {
		new Test().start();
	}

}
