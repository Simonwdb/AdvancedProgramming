package assignment1;

interface SetInterface {
	/*
	 *
	 * Elements: identifiers of the type Identifier
	 * Structure: None
	 * Domain: 0 to maximum MAX_ELEMENTS identifiers
	 *
	 * constructors
	 *
	 * Set();
	 *   PRE  - 
	 *   POST -A new Set-object has been made and contains the empty set.
	 *
	 * Set (Set src);
	 *   PRE  - 
	 *   POST - A new Set-object has been made and contains a copy of src.
	 *
	 */
	
	int MAX_ELEMENTS = 20;

	void init ();
	/* PRE  - 
	   POST - The set is empty.
	*/

	void delete (Identifier element);
	/* PRE	- 
	   POST	- The element is not in the set.
	*/
	
	void add (Identifier element) throws Exception;
	/* PRE	-
	 * POST	- true: Set contains a copy of the element.
	 * 		- false: Set contains MAX_ELEMENTS and element is not part of the set.
	 * 
	 */
	
	Identifier getElement ();
	/* PRE	- Set is not empty.
	 * POST	- Returns a copy of a random element from the set.
	 * 
	 */

	Set difference (Set element1);
	/* PRE	- 
	   POST	- Returned a set containing the elements in element1 but not in element2.
	*/

	Set intersection (Set element1);
	/* PRE	-
	   POST	- Returns a set containing the elements that contains in both sets.
	*/

	Set union (Set element1) throws Exception;
	/* PRE	-
	   POST	- true: returns a set containing all elements of both sets. (N.B. sets do no contain duplicate elements)
	   		  false: result contains more elements than MAX_ELEMENTS.
	*/

	Set symmetricDifference (Set element1) throws Exception;
	/* PRE -
   	   POST	- true: returns a set containing all elements of both sets that are not contains in the intersection.
	   		  false: result contains more elements than MAX_ELEMENTS.
	*/

	boolean containsIdentifier (Identifier element);
	/* PRE	- 
	   POST	- true: when the element is in the set.
	    	  false: when the element is not in the set.
	*/

	boolean isEmpty ();
	/* PRE  - 
	   POST - true:  The amount of elements of the set equals 0.
	          false: the amount of elements of the set is greater than 0.
	*/

	int size ();
	/* PRE  - 
	   POST - The amount of elements of the set has been returned.
	*/

}