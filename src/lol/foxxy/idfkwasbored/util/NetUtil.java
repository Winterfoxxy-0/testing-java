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
	      InetAddress inetAddress = InetAddress.getLocalHost();
	      NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
	      byte[] macArray = network.getHardwareAddress();
	      StringBuilder str = new StringBuilder();
	      // Convert the macArray to String
	      for (int i = 0; i < macArray.length; i++) {
	        str.append(String.format("%02X%s", macArray[i], (i < macArray.length - 1) ? " ": ""));
	      }
	       return str.toString();

	}
	public static void getWifiPasswords() throws Exception{
		AnsiUtils colors = new AnsiUtils();

        Path temp = Files.createTempFile("netshOut", ".file");
		new ProcessBuilder("cmd", "/c", "netsh wlan show profile > " + temp.toString().replace("\\\\", "\\")).inheritIO().start().waitFor();
	    ArrayList<String> lines = new ArrayList<>(Files.readAllLines(temp));
	    ArrayList<String> ssids = new ArrayList<String>();
	    HashMap<String, String> end = new HashMap<String, String>();

	    for(String s : lines) {
	    	if(s.contains(": ")) {
	    		ssids.add(s.split(": ")[1].strip());
			}
	    }
	    for(String s : ssids) {
	        temp = Files.createTempFile("netshOut", ".file");
			new ProcessBuilder("cmd", "/c", "netsh wlan show profile name="+ s +" key=clear > " + temp.toString().replace("\\\\", "\\")).inheritIO().start().waitFor();
			lines = new ArrayList<>(Files.readAllLines(temp));
			for(String t : lines) {
				if(t.contains("Key Content")) {
		    		end.put(s, t.split(": ")[1].strip());
				}
			}
	    }
	    Path currentRelativePath = Paths.get("");
	    String currPath = currentRelativePath.toAbsolutePath().toString();
        Files.writeString(Paths.get(currPath + "\\wifi.json"), new JSONObject(end).toString(2));
	    System.out.println(new JSONObject(end).toString(2)
				.replace("]", colors.getRed() + "]" + colors.reset())
				.replace("\"", colors.getRed() + "\"" + colors.reset())
				.replace(",", colors.getWhite() + "," + colors.reset())
		);
		System.out.println(colors.getRed() + "[+] " + colors.reset() + "Saved to " + colors.getRed() + "wifi.json" + colors.reset());
		Scanner sc = new Scanner(System.in);
		System.out.print(colors.getRed() + "[/] " + colors.reset() + "Press enter to continue... ");
		while (sc.hasNextLine()) {
			return;

		}
	}
}	
