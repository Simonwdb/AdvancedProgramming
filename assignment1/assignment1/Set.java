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

	// set1 = ['a1', 'b', 'c']
	// set2 = ['d', 'e', 'b']

	// set3 = ['a1', 'c'] (difference) elements that contain in set1 but not in set2
	// set3 = ['b'] (intersection) elements that contain in both sets
	// set3 = ['a1', 'b', 'c', 'd', 'e'] (union) all elements, but not the
	// duplicates
	// set3 = ['a1', 'c', 'd', 'e'] (symDiff) all elements in both sets, except the
	// intersection

	public Set difference(Set element1) throws Exception {
		Set set3 = new Set();
		for (int i = 0; i < element1.index; i++) {
			if (!containsIdentifier(element1.s[i])) {
				set3.add(element1.s[i]);
			}
		}
		return set3;
	}

	public Set intersection(Set element1) throws Exception {
		Set set3 = new Set();
		for (int i = 0; i < element1.index; i++) {
			if (containsIdentifier(element1.s[i])) {
				set3.add(element1.s[i]);
			}
		}
		return set3;
	}

	public Set union(Set element1) throws Exception {
		Set set3 = new Set();
		for (int i = 0; i < element1.index; i++) {
			if (!containsIdentifier(element1.s[i])) {
				set3.add(element1.s[i]);
			}
		}
		for (int i = 0; i < index; i++) {
			set3.add(s[i]);
		}

		return set3;
	}

	public Set symmetricDifference(Set element1) throws Exception {
		Set set3 = new Set(element1);
		Set set4 = new Set();
		for (int i = 0; i < index; i++) {
			set4.add(s[i]);
		}
		Set diff1 = set4.difference(set3);
		Set diff2 = set3.difference(set4);
		Set symDiff = diff1.union(diff2);

		return symDiff;
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
