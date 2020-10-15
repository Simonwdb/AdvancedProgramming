package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {
	
	private LinkedList<T> list;
	
	public Set () {
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
		if (isEmpty()) {
			return new Set<T>();
		} else {
			SetInterface<T> copy = new Set<T>();
			list.goToFirst();
			copy.add(get());	// in case if the list has only one element.
			while (list.goToNext()) {
				copy.add(get());
			}
			// do we need to reset the current to the first element in the original list?
			list.goToFirst();
			return copy;
		}
	}

	@Override
	public SetInterface<T> difference(SetInterface<T> set1) {
		if (set1.isEmpty()) {
			return new Set<T>();
		} else if (isEmpty()) {
			return set1;
		} else {
			SetInterface<T> result = set1.copy();
			while (! set1.isEmpty()) {
				T temp = set1.get();
				if (containsT(temp)) {
					result.remove(temp);
				}
				set1.remove(temp);
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
			while (! set1.isEmpty()) {
				T temp = set1.get();
				if (! containsT(temp)) {
					result.remove(temp);
				}
				set1.remove(temp);
			}
			return result;
		}
	}

	@Override
	public SetInterface<T> union(SetInterface<T> set1) {
		SetInterface<T> result = difference(set1);
		list.goToFirst();
		while (list.goToNext()) {
			result.add(list.retrieve());
		}
		return result;
	}

	@Override
	public SetInterface<T> symmetricDifference(SetInterface<T> set1) {
		SetInterface<T> temp = new Set<T>();
		list.goToFirst();
		while (list.goToNext()) {
			temp.add(list.retrieve());
		}
		if (set1.isEmpty()) {
			return temp;
		} else if (isEmpty()) {
			return set1;
		} else {
			SetInterface<T> diff1 = temp.difference(set1);
			SetInterface<T> diff2 = set1.difference(temp);
			SetInterface<T> symDiff = diff1.union(diff2);
			return symDiff;
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
	

}
