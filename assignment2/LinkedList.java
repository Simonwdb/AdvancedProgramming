package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

	private Node current;
	private Node head;
	private Node tail;
	private int numberOfElements;

	// just for testing
	PrintStream out = new PrintStream(System.out);

	public LinkedList() {
		this.current = null;
		this.head = null;
		this.tail = null;
		this.numberOfElements = 0;
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
		head = null;
		tail = null;
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
			head = tail = n;
		} else if (head.data.compareTo(n.data) >= 0) { // insert at the start, n <= current
			insertAtFront(n);
		} else if (current.next == null && tail.data.compareTo(n.data) <= 0) { // insert at the end
			insertAtBack(n);
		} else if (current.data.compareTo(n.data) >= 0) {
			insertAtLeft(n);
		} else if (current.next != null && current.data.compareTo(n.data) < 0) {
			insertAtRight(n);
		}
		increaseSize();
		current = n;
		return this;
	}

	private void insertAtFront(Node n) {
		n.next = head;
		head = n.next.prior = n;
	}

	private void insertAtBack(Node n) {
		tail = current.next = n;
		n.prior = current;
	}

	private void insertAtLeft(Node n) {
		while (current.prior.data.compareTo(n.data) > 0) {
			current = current.prior;
		}
		n.prior = current.prior;
		current.prior = current.prior.next = n;
		n.next = current;
	}

	private void insertAtRight(Node n) {
		while (current.next != null && n.data.compareTo(current.next.data) > 0) {
			current = current.next;
		}
		if (current.next == null) {
			insertAtBack(n);
		} else {
			n.next = current.next;
			current.next = current.next.prior = n;
			n.prior = current;
		}
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

	private boolean findLeft(E d) {
		while (current.prior != null && current.data.compareTo(d) >= 0) {
			current = current.prior;
			while (current.prior != null && current.prior.data.compareTo(d) == 0) {
				current = current.prior;
			}
			if (current.data.compareTo(d) == 0) {
				return true;
			}
		}

		return false;
	}

	private boolean findRight(E d) {
		while (current.next != null && current.data.compareTo(d) < 0) {
			current = current.next;
			if (current.data.compareTo(d) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean find(E d) {
		if (isEmpty()) {
			return false;
		}
		if (current.data.compareTo(d) == 0) {
			return true;
		} else if (current.data.compareTo(d) > 0) {
			return findLeft(d);
		} else {
			return findRight(d);
		}
	}

	@Override
	public boolean goToFirst() {
		if (isEmpty()) {
			return false;
		}
		current = head;
		return true;
	}

	@Override
	public boolean goToLast() {
		if (isEmpty()) {
			return false;
		}
		current = tail;
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