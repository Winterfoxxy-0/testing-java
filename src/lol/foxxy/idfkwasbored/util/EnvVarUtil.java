package lol.foxxy.idfkwasbored.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;

import lol.foxxy.idfkwasbored.Main;
import lol.foxxy.idfkwasbored.ansitools.AnsiUtils;

public class EnvVarUtil {
	public static int i = 0;
	public static HashMap<String, String> iToKey = new HashMap<String, String>();

	public static void doEnvVarGetter() throws InterruptedException, IOException {
		// initialize the console-color class
		AnsiUtils colors = new AnsiUtils();
		// clear the screen
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		// set the counter to one
		EnvVarUtil.i = 1;
		// print title
		System.out.println(colors.getRed() + "[/] " + colors.reset() + "Enviroment Variable Getter");
		// loop over all enviroment variables
		System.getenv().forEach((K, V) -> {
			// print them in a numbered selection list
			System.out.println(colors.getRed() + String.valueOf(i) + colors.reset() + ") " + colors.reset() + K);
			// adding them to a number-to-key list
			EnvVarUtil.iToKey.put(String.valueOf(i), K);
			++EnvVarUtil.i;
		});
		// creating a scanner to read lines
		Scanner sc = new Scanner(System.in);
		// prompting the user
		System.out.print(colors.getRed() + "[/] " + colors.reset() + "What would you like to read the value of? ");
		// create a while loop that runs code when the scanner has a line to read
		while (sc.hasNextLine()) {
			// get the line
			String str = sc.nextLine();
			// check if the user didn't select path
			if (!iToKey.get(str).contains("Path")) {
				// print out the enviroment variable
				System.out.println(System.getenv().get(iToKey.get(str)));
			} else {
				// alert the user that the program is going to pretty print the path
				System.out.println(colors.getRed() + "[+] " + colors.reset()
						+ "Attempt to retrieve PATH detected, prettyprinting...");
				// create a json array that will hold the values of the path variable
				JSONArray obj = new JSONArray();
				// perform a for loop on the path variable
				Arrays.asList(System.getenv("PATH").split(";")).forEach(K -> {
					// add the currently selected path variable to a list
					obj.put(K);
				});
				// pretty print the path, with colors
				System.out.println(obj.toString(2).replace("[", colors.getRed() + "[" + colors.reset())
						.replace("]", colors.getRed() + "]" + colors.reset())
						.replace("\"", colors.getRed() + "\"" + colors.reset())
						.replace(",", colors.getWhite() + "," + colors.reset()));
			}
			break;

		}
		// wait for the user to press enter, to return back to the homescreen
		sc = new Scanner(System.in);
		System.out.print(colors.getRed() + "[/] " + colors.reset() + "Press enter to continue... ");
		while (sc.hasNextLine()) {
			return;

		}

	}
}
