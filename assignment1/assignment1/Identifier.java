package assignment1;

public class Identifier implements IdentifierInterface {
	
	StringBuffer sb;
	
	Identifier () {
		sb = new StringBuffer();
	}

	public void init(char c) {
//		if (Character.isLetter(c)) {
//			sb.append(c);
//		}
	}

	public void add(char element) {
		
	}

	public boolean compareIdentifier(Identifier element) {
		return sb.equals(element);	// deze vergelijking werkt niet. Moet nog zoeken naar een goede oplossing
	}

	public char getChar(int index) {
		return sb.charAt(index);
	}

	public int size() {
		return sb.length();
	}

}
