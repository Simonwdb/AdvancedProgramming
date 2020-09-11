package assignment1;

interface IdentifierInterface {
	
	/*
	 *
	 * Elements: alphanumeric characters of the type Character
	 * Structure: lineair
	 * Domain: all rows of alphanumeric characters
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
	
	void init (char c);
	/* PRE  - char c must be a letter.
	   POST - Identifier contains char c with length of one.
	*/
	
	void add (char element);
	/* PRE  - char element must be a alphanumeric character.
	   POST - An element is now added to the index size() of the identifier and the length increase by one.
	*/
	
	boolean compareIdentifier (Identifier element);
	/* PRE	- 
	 * POST	- true: contents of the identifier is equal to the contents of the element.
	 *        false: contents of the identifier is not equal to the contents of the element.
	 */
	
	char getChar (int index);
	/* PRE 	- 0 <= index <= size() - 1.
	   POST	- Returns the character in the identifier at index element.
	*/
	
	int size ();
	/* PRE  - 
	   POST - The amount of elements of the identifier has been returned.
	*/
	
}
