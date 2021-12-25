package lol.foxxy.idfkwasbored.util;

public class FXStringUtil {
	// Java program to insert a string into another string
	// without using any pre-defined method



	// Function to insert string
	public static String insertString(String originalString, String stringToBeInserted, int index) {

		String string2 = new StringBuilder(originalString).insert(index, stringToBeInserted).toString();
		return string2;
	}
}
