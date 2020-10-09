package nl.vu.labs.phoenix.ap;

import java.util.Objects;

public class Identifier implements IdentifierInterface {
	
	StringBuffer sb;
	
	public Identifier() {
		init('a');
	}
	
	@Override
	public String value() {
		return sb.toString();
	}

	@Override
	public void init(char c) {
		sb = new StringBuffer();
		add(c);
	}

	@Override
	public void add(char c) {
		sb.append(c);
	}

	@Override
	public int size() {
		return sb.length();
	}
	
	public boolean equals(Object id) {
		if (id == null || Objects.equals(id, sb)) {
			return false;
		}
		return sb.toString().equals(id.toString());
	}
	
	public int hashCode() {
		return sb.hashCode();
	}

}
