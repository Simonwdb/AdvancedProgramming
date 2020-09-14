package assignment1;

public class Identifier implements IdentifierInterface {
	
	StringBuffer sb;
	
	public Identifier () {
		init('a');
	}
	
	public Identifier (Identifier src) {
		init('a');
		copyIdentifier(sb, src.sb);
	}

	private void copyIdentifier (StringBuffer dest, StringBuffer src) {
		for (int i = 0; i < src.length(); i ++) {
			dest.setCharAt(i, src.charAt(i));
		}
	}
	
	public void init(char c) {
		sb = new StringBuffer();
		add(c);

	}

	public void add(char element) {
		sb.append(element);
	}

	public boolean compareIdentifier(Identifier element) {
		return sb.toString().equals(element.sb.toString());	
	}

	public char getChar(int index) {
		return sb.charAt(index);
	}

	public int size() {
		return sb.length();
	}
	
}
