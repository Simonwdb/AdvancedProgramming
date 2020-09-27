package nl.vu.labs.phoenix.ap;

/* [1] Identifier Specification
 * 	   -- Complete the specification for an Identifier interface.
 * 		  See the List interface for inspiration
 * 	/*
	 *
	 * Elements: alphanumeric characters of the type Character
	 * Structure: linear
	 * Domain: all rows of alphanumeric characters starting with a letter, length at least 1
	 *
	 * constructors
	 *
	 * Identifier();
	 *   PRE  - 
	 *   POST -A new Identifier-object has been made and contains a stack of at least one identifier.
	 *
	 * Identifier (Identifier src);
	 *   PRE  - 
	 *   POST - A new Identifier-object has been made and contains a copy of src.
	 *
	 */

public interface IdentifierInterface {
	/*
	 * [2] Mandatory methods. Make sure you do not modify these! -- Complete the
	 * specifications of these methods
	 */

	String value();

	/*
	 * [3] Add anything else you think belongs to this interface
	 */

	// your code here

	void init(char c);
	/*
	 * PRE - char c must be a letter. POST - Identifier contains char c with length
	 * of one.
	 */

	void add(char element);
	/*
	 * PRE - char element must be a alphanumeric character. POST - An element is now
	 * added to the index size() of the identifier and the length increase by one.
	 */

	boolean compareIdentifier(Identifier element);
	/*
	 * PRE - POST - true: contents of the identifier is equal to the contents of the
	 * element. false: contents of the identifier is not equal to the contents of
	 * the element.
	 */

	char getChar(int index);
	/*
	 * PRE - 0 <= index <= size() - 1. POST - Returns the character in the
	 * identifier at index element.
	 */

	int size();
	/*
	 * PRE - POST - The amount of elements of the identifier has been returned.
	 */
}
