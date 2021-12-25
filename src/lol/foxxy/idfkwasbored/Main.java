package lol.foxxy.idfkwasbored;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;

import lol.foxxy.idfkwasbored.ansitools.AnsiUtils;
import lol.foxxy.idfkwasbored.util.EnvVarUtil;
import lol.foxxy.idfkwasbored.util.KeyUtil;
import lol.foxxy.idfkwasbored.util.NetUtil;
import lol.foxxy.idfkwasbored.util.StorageUtil;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

public class Main {
	public static int i = 0;
	public static String title = "  ██████▓██   ██▓  ██████  █    ██ ▄▄▄█████▓ ██▓ ██▓    \r\n"
								+ "▒██    ▒ ▒██  ██▒▒██    ▒  ██  ▓██▒▓  ██▒ ▓▒▓██▒▓██▒    \r\n"
								+ "░ ▓██▄    ▒██ ██░░ ▓██▄   ▓██  ▒██░▒ ▓██░ ▒░▒██▒▒██░    \r\n"
								+ "  ▒   ██▒ ░ ▐██▓░  ▒   ██▒▓▓█  ░██░░ ▓██▓ ░ ░██░▒██░    \r\n"
								+ "▒██████▒▒ ░ ██▒▓░▒██████▒▒▒▒█████▓   ▒██▒ ░ ░██░░██████▒\r\n"
								+ "▒ ▒▓▒ ▒ ░  ██▒▒▒ ▒ ▒▓▒ ▒ ░░▒▓▒ ▒ ▒   ▒ ░░   ░▓  ░ ▒░▓  ░\r\n"
								+ "░ ░▒  ░ ░▓██ ░▒░ ░ ░▒  ░ ░░░▒░ ░ ░     ░     ▒ ░░ ░ ▒  ░\r\n"
								+ "░  ░  ░  ▒ ▒ ░░  ░  ░  ░   ░░░ ░ ░   ░       ▒ ░  ░ ░   \r\n"
								+ "      ░  ░ ░           ░     ░               ░      ░  ░\r\n"
								+ "         ░ ░                                            ";
	public static AnsiUtils colors = new AnsiUtils();

	public static void main(String[] args) throws Exception {
		while (true) {
			i = 0;
			// clear the screen
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			// print the title
			System.out.println(colors.getRed() + title + colors.reset());
			System.out.println();
			System.out.println();
			// get the oshi systeminfo object
			SystemInfo systemInfo = new SystemInfo();
			// grab the localhost addr
			InetAddress localhost = InetAddress.getLocalHost();
			// get the computer's hostname & ip
			String localIP = localhost.getLocalHost().toString().trim();
			// get the HAL object
			HardwareAbstractionLayer hardware = systemInfo.getHardware();
			// grab processor info
			CentralProcessor processor = hardware.getProcessor();
			// grab gpu info for all gpus
			List<GraphicsCard> graphicscard = hardware.getGraphicsCards();
			// grab the memory info
			GlobalMemory memory = hardware.getMemory();
			String username = System.getProperty("user.name");
			// print the cpu info
			System.out.println(colors.getRed() + "[+] " + colors.reset() + "CPU: " + processor.toString());
			System.out.println();
			// check the gpu amount
			if (graphicscard.size() > 1) {
				// if larger than one, print info for all of them.
				graphicscard.forEach((K) -> {
					System.out.println(
							colors.getRed() + "[+] " + colors.reset() + "GPU: " + graphicscard.get(Main.i).getName()
									+ "\n Device ID: " + graphicscard.get(Main.i).getDeviceId() + "\n Vendor: "
									+ graphicscard.get(Main.i).getVendor() + "\n VRAM: "
									+ graphicscard.get(Main.i).getVRam() + "\n Driver Version: "
									+ graphicscard.get(Main.i).getVersionInfo().replace("DriverVersion=", "") + "\n");
					++Main.i;
				});
			} else if (graphicscard.size() == 1) {
				// if its equal to one, then print info for the single gpu
				System.out.println(colors.getRed() + "[+] " + colors.reset() + "GPU: " + graphicscard.get(0).getName()
						+ "\n Device ID: " + graphicscard.get(0).getDeviceId() + "\n Vendor: "
						+ graphicscard.get(0).getVendor() + "\n VRAM: " + graphicscard.get(0).getVRam()
						+ "\n Driver Version: " + graphicscard.get(0).getVersionInfo() + "\n");

			}
			// get the ram in bytes, then convert it to a human-readable format, then print it
			System.out.println(colors.getRed() + "[+] " + colors.reset() + "RAM: "
					+ StorageUtil.humanReadableByteCountBin(memory.getTotal()) + " (" + memory.getTotal() + " bytes)");
			System.out.println();
			// print out the hostname, ip, and mac address
			System.out.println(colors.getRed() + "[+] " + colors.reset() + "Network: " + localIP + "\n MAC Address: "
					+ NetUtil.getMacAddr() + "\n Public IP: " + NetUtil.getIPV4());
			// print the selectable options
			System.out.println(
					colors.getRed() + "1" + colors.reset() + ") " + colors.reset() + "Enviroment Variable Getter");
			System.out.println(
					colors.getRed() + "2" + colors.reset() + ") " + colors.reset() + "Get WiFi Passwords"
			);
			System.out.println(
					colors.getRed() + "3" + colors.reset() + ") " + colors.reset() + "Get Windows License Key"
			);
			System.out.println(
					colors.getRed() + "4" + colors.reset() + ") " + colors.reset() + "Retrieve Geolocation Data via IP address"
			);
			System.out.println(
					colors.getRed() + "5" + colors.reset() + ") " + colors.reset() + "Exit"
			);
			System.out.println();
			// create a scanner to read an input line
			Scanner sc = new Scanner(System.in);
			// give the user an option selection prompt
			System.out.print(colors.getRed() + "[/] " + colors.reset() + username + colors.getRed() + "@" + colors.reset()+ "sysutil> ");
			// read the next line
			String str = sc.nextLine();
			// check what option the user selected
			if (str.contains("1")) {
				// enviroment variable getter
				EnvVarUtil.doEnvVarGetter();

			} else if (str.contains("5")) {
				// exit
				System.exit(0);
			} else if (str.contains("4")) {
				NetUtil.getGeoLocationDataIPV4();
			} else if (str.contains("2")) {
				// grab the wifi passwords
				NetUtil.getWifiPasswords();
			} else if (str.contains("3")) {
				// grab the wifi passwords
				KeyUtil.getProductKey();
			} else {
				// alert the user that they made an invalid selection
				System.out.println(colors.getRed() + "[!] " + colors.reset() + "Invalid Selection! Please try again.");
				Thread.sleep(3000);
			}
			// clear the screen after the selection was made
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}

	}


}