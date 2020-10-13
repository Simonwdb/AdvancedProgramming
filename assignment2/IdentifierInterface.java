package nl.vu.labs.phoenix.ap;


/* [1] Identifier Specification
 * 	   -- Complete the specification for an Identifier interface.
 * 		  See the List interface for inspiration
 */
/** @elements
 *    Characters of the type Character.
 *  @structure
 *    Linear.
 *  @domain
 *    All rows of alphanumeric characters starting with a letter, length at least 1.
 *  @constructor
 *    There is a default constructor that creates an Identifier-object and contains one character.
 *  @precondition
 *    --
 *  @postcondition
 *    The new Identifier-object with at least one character c.
 *
 **/
public interface IdentifierInterface {
	/* 
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */
	
    /** @precondition
     *    -- 
     *  @postcondition
     *    Returned a string object with all the characters.
     **/
	String value();
	
	/* 
	 * [3] Add anything else you think belongs to this interface 
	 */
	
	// your code here
	
    /** @precondition
     *    Char c must be a letter.
     *  @postcondition
     *    Identifier contains char c with length of one.
     **/
	void init(char c);
	
    /** @precondition
     *    Char c must be a alphanumeric character.
     *  @postcondition
     *    An element is now added to the index size() of the identifier 
	 *    and the length increase by one.
     **/
	void add(char c);

    /** @precondition
     *    --
     *  @postcondition
     *    FALSE: if id == null or id is not an Identifier-object or contents of the identifier 
	 *           is not equal to the contents of the element.
     *     TRUE: contents of the identifier is equal to the contents of the
	 *           element.
     **/
	boolean equals(Object id);

    /** @precondition
     *    --
     *  @postcondition
     *    The amount of elements of the identifier has been returned.
     **/
	int size();
	
    /** @precondition
     *    --
     *  @postcondition
     *    HashCode for the Identifier is returned.
     **/
	int hashCode();
}

