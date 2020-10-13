package nl.vu.labs.phoenix.ap;


/* [1] Set Specification
 * 	   -- Complete the specification for a set interface.
 * 		  See the List interface for inspiration
 */
/** @elements
 *    objects of type T
 *  @structure
 *    None
 *  @domain
 *    The elements are unique and are of the same type.
 *  @constructor
 *    There is a default constructor that creates an empty set.
 *  @precondition
 *    --
 *  @postcondition
 *    The new Set-object is the empty set
 *
 **/
public interface SetInterface<T extends Comparable<T>> {
	
	/* 
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */
	
    /** @precondition
     *      --
     *  @postcondition
     *    FALSE: element was already present
     *     TRUE: element was inserted.
     **/
	boolean add(T t);
	
    /** @precondition
     *    The set is not empty.
     *  @postcondition
     *    The value of the current element has been returned.
     **/
	T get();
	
    /** @precondition
     *      --
     *  @postcondition
     *    FALSE: element can't be removed, element is not in set.
     *     TRUE: element is removed.
     **/
	boolean remove(T t);
	
    /** @precondition
     *    --
     *  @postcondition
     *    The number of elements in the set has been returned.
     **/
	int size();
	
    /** @precondition
     *    --
     *  @postcondition
     *    A copy of the set has been returned.
     */
	SetInterface<T> copy();
	
	/*
	 * [3] Methods for set operations 
	 * 	   -- Add methods to perform the 4 basic set operations 
	 * 		  (union, intersection, difference, symmetric difference)
	 */
	
    /** @precondition
     *    --
     *  @postcondition
     *    Returned a set containing the elements in first set but not in second set.
     **/
	SetInterface<T> difference (SetInterface<T> set1);

    /** @precondition
     *    --
     *  @postcondition
     *    Returns a set containing the elements that contains in both sets.
     **/
	SetInterface<T> intersection (SetInterface<T> set1);

    /** @precondition
     *    --
     *  @postcondition
     *    Returns a set containing all elements of both sets.
     **/
	SetInterface<T> union (SetInterface<T> set1);

    /** @precondition
     *    --
     *  @postcondition
     *    Returns a set containing all elements of both sets that are not contains in the intersection.
     **/
	SetInterface<T> symmetricDifference (SetInterface<T> set1);

	/* 
	 * [4] Add anything else you think belongs to this interface 
	 */
    
	/** @precondition
     *      --
     *  @postcondition
     *    FALSE: element is not in the set.
     *     TRUE: element is in the set.
     **/
	boolean containsT (T t);
	
    /** @precondition
     *      --
     *  @postcondition
     *    FALSE: set is not empty.
     *     TRUE: set is empty.
     **/
	boolean isEmpty ();
	
    /** @precondition
     *      --
     *  @postcondition
     *    set is empty.
     **/
	void init();
	
}
