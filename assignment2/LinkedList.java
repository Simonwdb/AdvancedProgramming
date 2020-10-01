package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

	Node current;
	Node head;
	PrintStream out;

	public LinkedList() {
		current = null;
		head = null;
		out = new PrintStream(System.out);
	}

	// change back to private!!
	public class Node {

		E data;
		Node prior, next;

		public Node(E data) {
			this(data, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}
	}

	@Override
	public boolean isEmpty() {
		return current == null;
	}

	@Override
	public ListInterface<E> init() {
		current = null;
		return this;
	}

	@Override
	public int size() {
		// if tested manually there are no problems, but with the JUnit test problems
		// occur.
		if (isEmpty()) {
			return 0;}
//		} else if (current.next == null) {
//			return 1;
//		}
//		goToNext();
//		return 1 + size();
		return 0;
	}
	

	@Override
	public ListInterface<E> insert(E d) {
		Node n = new Node(d);
		if (isEmpty()) {
			head = n;
		} else if (n.data.compareTo(head.data) < 0) { // insert at the start, n < current
			n.next = head;
			n.next.prior = n;
			head = n;
		} else {
			current = head;
			while (current.next != null && n.data.compareTo(current.next.data) > 0) {
				current = current.next;
			}
			n.prior = current;
			if (current.next != null) { // insert in between, 
				n.next = current.next;
				current.next = current.next.prior = n;
				n.prior = current;
			} else { // insert at the end
				current.next = n;
				n.prior = current;
			}
		}
		current = n;
		return this;
	}

	@Override
	public E retrieve() {
		if (isEmpty()) {
			return null;
		}
		return current.data;
	}

	@Override
	public ListInterface<E> remove() {
		if (isEmpty()) {
			return null;
		} else if (current.prior == null && current.next == null) {
			current = null;
		} else if (current.next == null) {
			current.prior.next = current.next; // do we need to say this or can be said = null?
			goToPrevious();
		} else if (current.prior == null) {
			current.next.prior = current.prior; // same as above
			goToNext();
		} else {
			current.prior.next = current.next;
			current.next.prior = current.prior;
			goToNext();
		}
		return this;
	}

	@Override
	public boolean find(E d) {
		if (isEmpty()) {
			return false;
		}
		return current.data == d || find(current.next.data);
	}

	@Override
	public boolean goToFirst() {
		if (isEmpty()) {
			return false;
		}
		while (current.prior != null) {
			current = current.prior;
		}
		return true;
	}

	@Override
	public boolean goToLast() {
		if (isEmpty()) {
			return false;
		}
		while (current.next != null) {
			current = current.next;
		}
		return true;
	}

	@Override
	public boolean goToNext() {
		if (isEmpty() || current.next == null) {
			return false;
		}
		current = current.next;
		return true;
	}

	@Override
	public boolean goToPrevious() {
		if (isEmpty() || current.prior == null) {
			return false;
		}
		current = current.prior;
		return true;
	}

	@Override
	public ListInterface<E> copy() {
		ListInterface<E> listCopy = new LinkedList<E>();

		return this;
	}
}