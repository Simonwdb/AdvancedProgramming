
/*
// program = { statement } <eof>
Interpreter program (Scanner input) throws APException {
	Interpreter result = statement(input)
	return result
}

// statement = assignment | print_statement | comment ;
T statement (Scanner input) throws APException {
	
	if (nextCharIs(input, '=')) {
		return assignment(input)
	} else if (nextCharIs(input, '?')) {
		return print_statement(input)
	} else if (nextCharIs(input, '/')) {
		return comment(input)
	} else {
		throw new APException("...")
	}
	return null
}

// assignment = identifier '=' expression <eoln>
Type assignment (Scanner input) throws APEXception {
	identifier (input)
	character (input, '=')
	expression (input)
	eoln (input)
	return result
}

// print_statement = '?' expression <eoln>
Type print_statement (Scanner input) throws APEXception {
	character(input, '?')
	expression(input)
	eoln (input)
	return result
}

// comment = '/' <a line of text> <eoln>
Type comment (Scanner input) throws APEXception {
	character (input, '/')
	storeLine (input)
	eoln (input)
	return result
}

// identifier = letter {letter | number}
Identifier identifier (Scanner input) {
	nextCharIsLetter(input)
	Identifier result = new Identifier()
	while (nextCharIsLetterOrNumber(input)) {
		result.add(nextChar(input));
	}
	return result
}

// expression = term { additive_operator term}
Type expression (Scanner input) throws APException {
	
	return result
}

// term = factor {multiplicative_operator factor}

// factor = identifier | complex_factor | set ;
Set factor (Scanner input) throws APException {
	Set result;
	if (nextCharIsLetter(input)){
		result = getIdentifier(input)
	} else if (nextCharIs(input, '{') {
		result = new Set()
		result.add(nextChar(input))
	} else if (nextCharIs(input, '(') {
		result = complex_factor()
	} else {
		throw new APException ("...")
	}
	return result
}

// complex_factor = '(' expression ')'
Set complex_factor (Scanner input) throws APException {
	character(input, '(')
	expression(input)
	character(input, ')')
	return result
}

// set = '{' row_natural_numbers '}' ;
Set set (Scanner input) throws APException {
	Set result = new Set()
	character(input, '{')
	result.row_natural_nummbers(input)
	character(input, '}')
	return result
}

// row_natural_numbers = [ natural_number { ',' natural_number} ] ;
Set row_natural_numbers (Scanner input) {
	Set result = new Set() 
	result.add(natural_number(input))
	while (nextCharIs(input, ',') {
		character(input, ',')
		result.add(natural_nummber(input))
	}
	return result
}

// additive_operator = '+' | '|' | '-' ;
Set additive_operator (Scanner input) throws APException {
	Set result = new Set()
	if (nextCharIs(input, '+')) {
		result.union()
	} else if (nextCharIs(input, '|') {
		result.symmDifference()
	} else if (nextCharIs(input, '-') {
		result.complement()
	} else {
		throw new APException ("...")
	}
	return result
}

// multiplicative_operator = '*' ;
Set multiplicative_operator (Scanner input) throws APException {
	character(input, '*')
	Set result = new Set()
	result.intersection()
	return result
}

// natural_number = positive_number | zero ;
StringBuffer natural_number (Scanner input) throws APException {
	StringBuffer result = new StringBuffer()
	while(input.hasNext()) {
		char c = input.next()
		if (c == positive_nummber || c == zero) {
			result.add(c)
		} else {
			throw new APException ("...")
		}
	}
	return result
}

// positive_number = not_zero { number } ;
StringBuffer positive_number (Scanner input) throws APException {
	if (! not_zero(input)) {
		throw new APException ("...")
	}
	StringBuffer result = new StringBuffer()
	result.append(nextChar(input))
	while(input.hasNext()) {
		if (nextCharIsDigit(input)) {
			result.append(input.next())
		}
	}
	return result
}

// number = zero | not_zero ;
Stringbuffer number (Scanner input) {
	StringBuffer result = new StringBuffer()
	if (nextCharIs(input, zero())) {
		result.append(zero())
	} else {
		result.append(not_zero(input))
	}
	return result
}

// zero = '0' ;
StringBuffer zero() {
	return '0'
}

// not_zero = [1-9] ;
StringBuffer not_zero (Scanner input) {
	StringBuffer result = new StringBuffer()
	while (input.hasNext()) {
		result.append(input.next())
	}
	return result
}

// letter = [A-Za-z] ;
StringBuffer letter (Scanner input) {
	StringBuffer result = new StringBuffer()
	while (nextCharIsLetter(input) {
		result.append(input.next())
	}
	return result
}

// eoln
void eoln (Scanner input) throws APException {
	if (input.hasNext()) {
		throw new APException ("...")
	}
}

// character
void character (Scanner input, char c) throws APException {
	if (! nextCharIs(input, c)) {
		throw new APException ("...")
	}
	nextChar(input)
}
 */

