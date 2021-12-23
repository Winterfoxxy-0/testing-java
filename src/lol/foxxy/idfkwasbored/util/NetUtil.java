package lol.foxxy.idfkwasbored.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import lol.foxxy.idfkwasbored.ansitools.AnsiUtils;

public class NetUtil {
	public static String getMacAddr() throws Exception {
		// grab the localhost addr
		InetAddress inetAddress = InetAddress.getLocalHost();
		// get the network interface based on the localhost addr
		NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
		// get the array of bytes that is the MAC address
		byte[] macArray = network.getHardwareAddress();
		// create a string builder
		StringBuilder str = new StringBuilder();
		// Convert the macArray to String
		for (int i = 0; i < macArray.length; i++) {
			str.append(String.format("%02X%s", macArray[i], (i < macArray.length - 1) ? " " : ""));
		}
		return str.toString();

	}

	public static void getWifiPasswords() throws Exception {
		// create an instance of the console-color class
		AnsiUtils colors = new AnsiUtils();
		// make a temp file
		Path temp = Files.createTempFile("netshOut", ".file");
		// output all networks on the computer to the temp file
		new ProcessBuilder("cmd", "/c", "netsh wlan show profile > " + temp.toString().replace("\\\\", "\\"))
				.inheritIO().start().waitFor();
		// make a list of all the lines that the above command outputted
		ArrayList<String> lines = new ArrayList<>(Files.readAllLines(temp));
		// make a list of the ssids
		ArrayList<String> ssids = new ArrayList<String>();
		// the hashmap that will contain the final result
		HashMap<String /* ssid */, String /* password */> end = new HashMap<String, String>();
		
		// loop over the lines in the text file
		for (String s : lines) {
			// check if the line contains the splitter that netsh uses to differentiate a key from a value 
			if (s.contains(": ")) {
				// split it and add the end to the ssids list
				ssids.add(s.split(": ")[1].strip());
			}
		}
		// loop over the ssids
		for (String s : ssids) {
			// create a new temp file
			temp = Files.createTempFile("netshOut", ".file");
			// create a new netsh process, this time to get the password
			new ProcessBuilder("cmd", "/c",
					"netsh wlan show profile name=" + s + " key=clear > " + temp.toString().replace("\\\\", "\\"))
							.inheritIO().start().waitFor();
			// read that file to an arraylist
			lines = new ArrayList<>(Files.readAllLines(temp));
			// loop over the lines
			for (String t : lines) {
				// check if the file contains the prefix to the line that contains the password
				if (t.contains("Key Content")) {
					// split it using the same differentiator and adds ssid and password to list
					end.put(s, t.split(": ")[1].strip());
				}
			}
		}
		// get the current app path
		Path currentRelativePath = Paths.get("");
		String currPath = currentRelativePath.toAbsolutePath().toString();
		// write the end list that contains the ssids and passwords to a file by converting it to json
		Files.writeString(Paths.get(currPath + "\\wifi.json"), new JSONObject(end).toString(2));
		// pretty print it
		System.out.println(new JSONObject(end).toString(2).replace("]", colors.getRed() + "]" + colors.reset())
				.replace("\"", colors.getRed() + "\"" + colors.reset())
				.replace(",", colors.getWhite() + "," + colors.reset()));
		// let the user know that the ssids and passwords have been saved to a file
		System.out.println(colors.getRed() + "[+] " + colors.reset() + "Saved to " + colors.getRed() + "wifi.json"
				+ colors.reset());
		// wait for the user to press enter to continue
		Scanner sc = new Scanner(System.in);
		System.out.print(colors.getRed() + "[/] " + colors.reset() + "Press enter to continue... ");
		while (sc.hasNextLine()) {
			return;

		}
	}
}
