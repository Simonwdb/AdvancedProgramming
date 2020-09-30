package nl.vu.labs.phoenix.ap;


public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {
	
	Node list;
	
	public LinkedList() {
		list = null;
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
	
    @Override
    public boolean isEmpty() {
        return list == null;
    }

    @Override
    public ListInterface<E> init() {
    	list = null;
        return this;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ListInterface<E> insert(E d) {
    	// insert d to the empty list
    	if (isEmpty()) {
    		list = new Node(d);
    	}
    	
    	// insert d after the first element
    	
//    	if (list.next == null) {
//    		list.next = new Node(d, list, null);
//    	}
    	
    	if (goToNext()) {
    		list.next = new Node(d, list, null);
    	}
    	
    	// insert d at the last element
    	
    	/*while(list.next != null) {
    		list = list.next;
    	}
    	list.next = new Node(d, list, null);
    	*/
    	
    	if (goToLast()) {
    		list.next = new Node(d, list, null);
    	}
    	
    	// insert d "in the middle"
    	
        return this;
    }

    @Override
    public E retrieve() {
    	if (isEmpty()) {
    		return null;
    	}
        return list.data;
    }

    @Override
    public ListInterface<E> remove() {
        return this;
    }

    @Override
    public boolean find(E d) {
    	if (isEmpty()) {
    		return false;
    	}
        return list.data == d || find(list.next.data);
    }

    @Override
    public boolean goToFirst() {
    	if (isEmpty()) {
    		return false;
    	}
    	while (list.prior != null) {
    		list.prior = list;
    	}
        return true;
    }

    @Override
    public boolean goToLast() {
    	if (isEmpty()) {
    		return false;
    	}
    	while (list.next != null) {
    		list.next = list;
    	}
        return true;
    }

    @Override
    public boolean goToNext() {
    	if (isEmpty() || list.next == null) {
    		return false;
    	}
    	list = list.next;
        return true;
    }

    @Override
    public boolean goToPrevious() {
    	if (isEmpty() || list.prior == null) {
    		return false;
    	}
    	list = list.prior;
        return true;
    }

    @Override
    public ListInterface<E> copy() {
        return this;
    }
}