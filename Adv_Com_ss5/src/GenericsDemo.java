//5631319321 Ramkhana Cheursawathee

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class GenericsDemo {
	private static ArrayList<Integer> value = new ArrayList<Integer>();
	final static int money[] = { 5, 100, 1, 25 };
	final static String coinName[] = { "Penny", "Nickel", "Quarter", "Dollar" };

	public static void main(String[] args) {
		// store elements from nameArray in names ArrayList
		for (int i = 0; i < money.length; i++) {
			value.add(money[i]);
		}
		System.out.println("Unsorted coin's value");
		for (int i = 0; i < money.length; i++) {
			// Print unsorted array
			// Invoking "positional access" methods.
			if (i == (money.length) - 1) {
				System.out.println(value.get(i));
			} else {
				System.out.print(value.get(i) + ", ");
			}
		}

		// sorting elements in values' array
		Collections.sort(value);
		System.out.println("--------------");
		System.out.println("Sorted coin's value");
		for (int i = 0; i < money.length; i++) {
			if (i == (money.length) - 1) {
				System.out.println(value.get(i));
			} else {
				System.out.print(value.get(i) + ", ");
			}
		}

		ListIterator listIter = value.listIterator();
		System.out.println("--------------");
		System.out.println("Iterator Forward Reading");
		while (listIter.hasNext()) {
			System.out.print(listIter.next() + "\t");
		}
		System.out.println("\n--------------");
		System.out.println("Iterator Backward Reading");
		// Using ListIterator to traverse the value array.
		while (listIter.hasPrevious()) {
			System.out.print(listIter.previous() + "\t");
		}

		// Using some polymorphic method from the Collections class to
		// manipulate the list.
		// Rotates the elements in the collection by the 3.
		// rotate by the 3 means we bring the 3th element from the right side of
		// array to be 1st element
		Collections.rotate(value, 3);
		System.out.println("\n--------------");
		System.out.println("Polymorphic Manipulation by rotating elements in collection");
		for (int i = 0; i < money.length; i++) {
			System.out.print(value.get(i) + "\t");
		}

		// value need to be sorted again since its elements was rotated
		Collections.sort(value);

		// Using common map implementation HashMap
		// <Interger,String> Integer is the key of the map. String is the map's
		// value type
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < coinName.length; i++) {
			// put is to add an association to map
			map.put(value.get(i), coinName[i]);
		}

		// Showing basic operations of the map.
		System.out.println("\n--------------");
		System.out.println("Map's operation");
		System.out.println("   VALUE\tCOIN");
		for (int i = 0; i < map.size(); i++) {
			System.out.print((i + 1) + ". " + value.get(i) + "\t = \t");
			System.out.println(map.get(value.get(i)));
		}
		System.out.println("\n----THE END----");

	}

}