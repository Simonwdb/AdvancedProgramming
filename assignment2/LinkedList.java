package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

	Node current;
	Node head;
	private int numberOfElements;
	PrintStream out;

	public LinkedList() {
		this.current = null;
		this.head = null;
		this.numberOfElements = 0;
		out = new PrintStream(System.out);
	}

	private class Node {

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

	private void increaseSize() {
		numberOfElements++;
	}

	private void decreaseSize() {
		numberOfElements--;
	}

	@Override
	public boolean isEmpty() {
		return current == null;
	}

	@Override
	public ListInterface<E> init() {
		current = null;
		numberOfElements = 0;
		return this;
	}

	@Override
	public int size() {
		return numberOfElements;
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
		increaseSize();
		current = n;
		return this;
	}

	@Override
	public E retrieve() {
		return isEmpty() ? null : current.data;
	}

	@Override
	public ListInterface<E> remove() {
		if (isEmpty()) {
			return null;
		} else if (current.prior == null && current.next == null) {
			// remove if list-POST is empty
			current = null;
		} else if (current.next == null) {
			// remove if current-PRE is last element of list-PRE
			current.prior.next = null;
			goToPrevious();
		} else if (current.prior == null) {
			// remove if current-PRE is first element of list-PRE
			current.next.prior = null;
			goToNext();
		} else {
			// remove if current-PRE is "in the middle" of list-PRE
			current.prior.next = current.next;
			current.next.prior = current.prior;
			goToNext();
		}
		decreaseSize();
		return this;
	}

	@Override
	public boolean find(E d) {
		if (isEmpty()) {
			return false;
		}
		goToFirst();
		if (current.data.compareTo(d) == 0) {
			return true;
		} else if (current.data.compareTo(d) > 0) {
			return false;
		}
		while (current.next != null && current.next.data.compareTo(d) <= 0) {
			current = current.next;
		}
		if (current.data.compareTo(d) == 0) {
			return true;
		}
		return false;
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
		// new empty list
		ListInterface<E> copyList = new LinkedList<E>();
		goToFirst();
		
		
		while (current.next != null) {
			copyList.insert(retrieve());
			goToNext();
		}
		copyList.insert(retrieve());
		
		return copyList;
	}
}