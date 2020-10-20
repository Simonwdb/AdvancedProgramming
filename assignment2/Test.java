package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;


public class Test {
	
	LinkedList<Integer> list;
	PrintStream out;
	
	Test () {
		list = new LinkedList<Integer>();
		out = new PrintStream(System.out);
	}
	
	void start() {
		
//		out.println(list.size());
//		
//		out.println("insert one item");
//		list.insert(10);
//		list.insert(20);
		
//		out.println(list.size());
//		
//		out.println("insert 200 items");
//		for (int i = 0; i < 200; i++) {
//			list.insert(i);
//		}
//		out.println(list.size());
//		
//		out.println("removing one item");
//		list.remove();
//		out.println(list.size());
//		
//		out.println("init list");
//		list.init();
//		out.println(list.size());
		
		
		// Manual testing remove();
		list.insert(4);
		list.insert(4);
//		list.insert(4);
//		list.insert(6);
//		list.insert(8);
//		list.insert(10);		
		//ListInterface<Integer> testList = list.copy();
//		System.out.println(list.find(4));
//		System.out.println(list.retrieve());
//		list.goToNext();
//		System.out.println(list.retrieve());
//		list.goToNext();
//		System.out.println(list.retrieve());
//		testList.goToPrevious();
//		testList.goToPrevious();
//		testList.goToNext();
		//out.println(testList.retrieve());

	}

	public static void main(String[] args) {
		new Test().start();
	}

}


