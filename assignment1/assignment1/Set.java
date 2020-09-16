package assignment1;

import java.io.PrintStream;
import java.util.Scanner;

public class Set implements SetInterface {

	PrintStream out;
	Identifier[] s;
	int index;

	Set() {
		s = new Identifier[MAX_ELEMENTS];
		index = 0;
	}

	Set(Set src) {
		s = new Identifier[src.s.length];
		index = src.index;
		copySet(s, src.s, index);
	}

	public void copySet(Identifier[] dest, Identifier[] src, int amount) {
		// Need to be tested!

		for (int i = 0; i < amount; i++) {
			dest[i] = src[i];
		}
	}

	public void init() {

	}

	public void delete(Identifier element) {

	}

	public void testAdd(StringBuffer s) throws Exception {
		// Idee is dat hier een 'schone' StringBuffer bijvoorbeeld " a b c" inkomt, deze
		// vervolgens individueel toe te wijzen aan een Identifier en die toe te voegen
		// aan een set.
		Scanner test = new Scanner(s.toString());
		test.useDelimiter(" ");
		while (test.hasNext()) {
			Identifier i = new Identifier();
			i.sb.append(test.next()); // deze manier werkt niet, hierdoor komen alle losse identifiers in 1
										// identifier.

		}

	}

	public void add(Identifier element) throws Exception {
		if (index < MAX_ELEMENTS) {
			s[index] = element;
			index++;
		} else {
			throw new Exception("Set can't add element");
		}

	}

	public Identifier getElement() {
		return s[0];
	}

	public Set difference(Set element1, Set element2) {
		for (int i = 0; i < element1.index; i++) {
			for (int j = 0; j < element2.index; j++) {
				
			}
		}
		return null;
	}

	public Set intersection(Set element1) {
		return null;
	}

	public Set union(Set element1) throws Exception {
		return null;
	}

	public Set symmetricDifference(Set element1) throws Exception {
		return null;
	}

	public boolean containsIdentifier(Identifier element) {
		for (int i = 0; i < index; i++) {
			if (s[i].compareIdentifier(element)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return index == 0;
	}

	public int size() {
		return index;
	}

}
