package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;
import java.math.BigInteger;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

	private LinkedList<T> list;

	// for testing
	PrintStream out = new PrintStream(System.out);

	public Set() {
		list = new LinkedList<T>();
		list.init();
	}

	public Set(SetInterface<T> set1) {
		list.init();
		list = (LinkedList<T>) set1.copy();
	}

	public void init() {
		list.init();
	}

	@Override
	public boolean add(T t) {
		if (list.find(t)) {
			return false;
		}
		list.insert(t);
		return true;
	}

	@Override
	public T get() {
		return list.retrieve();
	}

	@Override
	public boolean remove(T t) {
		if (containsT(t)) {
			list.remove();
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public SetInterface<T> copy() {
		SetInterface<T> copy = new Set<T>();
		if (isEmpty()) {
			return copy;
		} else {
			list.goToFirst();
			copy.add(get());
			while (list.goToNext()) {
				copy.add(get());
			}
			list.goToFirst();
			return copy;
		}
	}

	@Override
	public SetInterface<T> difference(SetInterface<T> set1) {
		SetInterface<T> result = copy();
		SetInterface<T> copySet = set1.copy();
		if (set1.isEmpty()) {
			return result;
		} else if (isEmpty()) {
			return new Set<T>();
		} else {

			while (!copySet.isEmpty()) {
				if (result.containsT(copySet.get())) {
					result.remove(copySet.get());
				}
				copySet.remove(copySet.get());
			}
			return result;
		}
	}

	@Override
	public SetInterface<T> intersection(SetInterface<T> set1) {
		if (set1.isEmpty() || isEmpty()) {
			return new Set<T>();
		} else {
			SetInterface<T> result = set1.copy();
			SetInterface<T> set1Copy = set1.copy();
			while (!set1Copy.isEmpty()) {
				T temp = set1Copy.get();
				if (!containsT(temp)) {
					result.remove(temp);
				}
				set1Copy.remove(temp);
			}
			return result;
		}
	}

	@Override
	public SetInterface<T> union(SetInterface<T> set1) {
		SetInterface<T> result = set1.copy();
		list.goToFirst();
		if (list.retrieve() != null) {
			result.add(list.retrieve());
		}
		while (list.goToNext()) {
			result.add(list.retrieve());
		}
		return result;
	}

	@Override
	public SetInterface<T> symmetricDifference(SetInterface<T> set1) {

		if (set1.isEmpty()) {
			return copy();
		} else if (isEmpty()) {
			return set1;
		} else {
			SetInterface<T> result = copy();
			SetInterface<T> copySet = set1.copy();

			while (!copySet.isEmpty()) {
				if (containsT(copySet.get())) {
					result.remove(copySet.get());
				} else {
					result.add(copySet.get());
				}
				copySet.remove(copySet.get());
			}
			return result;
		}
	}

	@Override
	public boolean containsT(T t) {
		if (list.find(t)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void setToFirst() {
		list.goToFirst();
	}

}
