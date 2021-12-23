package lol.foxxy.idfkwasbored;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;

import lol.foxxy.idfkwasbored.ansitools.AnsiUtils;
import lol.foxxy.idfkwasbored.util.NetUtil;
import lol.foxxy.idfkwasbored.util.StorageUtil;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

public class Main {
	public static int i = 0;
	public static HashMap<String, String> iToKey = new HashMap<String, String>();
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
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			System.out.println(colors.getRed() + title + colors.reset());
			System.out.println();
			System.out.println();
			SystemInfo systemInfo = new SystemInfo();
	        InetAddress localhost = InetAddress.getLocalHost();
			String hostname = systemInfo.getOperatingSystem().getNetworkParams().getHostName();
			String localIP = localhost.getLocalHost().toString().trim();
			HardwareAbstractionLayer hardware = systemInfo.getHardware();
			CentralProcessor processor = hardware.getProcessor();
			List<GraphicsCard> graphicscard = hardware.getGraphicsCards();
			GlobalMemory memory = hardware.getMemory();

			System.out.println(colors.getRed() + "[+] " + colors.reset() + "CPU: " + processor.toString());
			System.out.println();
			if (graphicscard.size() > 1) {
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
				System.out.println(colors.getRed() + "[+] " + colors.reset() + "GPU: " + graphicscard.get(0).getName()
						+ "\n Device ID: " + graphicscard.get(0).getDeviceId() + "\n Vendor: "
						+ graphicscard.get(0).getVendor() + "\n VRAM: " + graphicscard.get(0).getVRam()
						+ "\n Driver Version: " + graphicscard.get(0).getVersionInfo() + "\n");

			}
			System.out.println(colors.getRed() + "[+] " + colors.reset() + "RAM: "
					+ StorageUtil.humanReadableByteCountBin(memory.getTotal()) + " (" + memory.getTotal() + " bytes)");
			System.out.println();
			System.out.println(colors.getRed() + "[+] " + colors.reset() + "Network: " + localIP + "\n MAC Address: " + NetUtil.getMacAddr());
			System.out.println(
					colors.getRed() + "1" + colors.reset() + ") " + colors.reset() + "Enviroment Variable Getter"
			);
			System.out.println(
					colors.getRed() + "2" + colors.reset() + ") " + colors.reset() + "Get WiFi Passwords"
			);
			System.out.println(
					colors.getRed() + "3" + colors.reset() + ") " + colors.reset() + "Exit"
			);
			System.out.println();
			Scanner sc = new Scanner(System.in);
			System.out.print(colors.getRed() + "[/] " + colors.reset() + "Select an option: ");
			String str = sc.nextLine();
			if (str.contains("1")) {
				doEnvVarGetter();

			} else if (str.contains("3")) {
				System.exit(0);
			}  else if (str.contains("2")) {
				NetUtil.getWifiPasswords();
			} else {
				System.out.println(colors.getRed() + "[!] " + colors.reset() + "Invalid Selection! Please try again.");
				Thread.sleep(3000);
			}
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}

	}

	public static void doEnvVarGetter() throws InterruptedException, IOException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		Main.i = 1;
		System.out.println(colors.getRed() + "[/] " + colors.reset() + "Enviroment Variable Getter");
		System.getenv().forEach((K, V) -> {
			System.out.println(colors.getRed() + String.valueOf(i) + colors.reset() + ") " + colors.reset() + K);
			Main.iToKey.put(String.valueOf(i), K);
			++Main.i;
		});
		Scanner sc = new Scanner(System.in);
		System.out.print(colors.getRed() + "[/] " + colors.reset() + "What would you like to read the value of? ");
		while (sc.hasNextLine()) {
			String str = sc.nextLine();
			if (!iToKey.get(str).contains("Path")) {
				System.out.println(System.getenv().get(iToKey.get(str)));
			} else {
				System.out.println(colors.getRed() + "[+] " + colors.reset()
						+ "Attempt to retrieve PATH detected, prettyprinting...");
				JSONArray obj = new JSONArray();
				Arrays.asList(System.getenv("PATH").split(";")).forEach(K -> {
					obj.put(K);
				});
				System.out.println(obj.toString(2).replace("[", colors.getRed() + "[" + colors.reset())
						.replace("]", colors.getRed() + "]" + colors.reset())
						.replace("\"", colors.getRed() + "\"" + colors.reset())
						.replace(",", colors.getWhite() + "," + colors.reset()));
			}
			break;

		}
		sc = new Scanner(System.in);
		System.out.print(colors.getRed() + "[/] " + colors.reset() + "Press enter to continue... ");
		while (sc.hasNextLine()) {
			return;

		}

	}
}