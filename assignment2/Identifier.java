package nl.vu.labs.phoenix.ap;


public class Identifier implements IdentifierInterface {
	
	private StringBuffer sb;
	
	public Identifier() {
		init('a');
	}
	
	public Identifier(char c) {
		init(c);
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
		if (id == null || id.getClass() != getClass()) {
			return false;
		}
		Identifier i = (Identifier) id;
		return sb.toString().equals(i.sb.toString());
	}
	
	public int hashCode() {
		return sb.toString().hashCode();
	}

}
