package lol.foxxy.idfkwasbored.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import lol.foxxy.idfkwasbored.ansitools.AnsiUtils;

public class KeyUtil {
	public static void getProductKey() throws Exception {
		AnsiUtils colors = new AnsiUtils();

		String digitalProductId = (String) Advapi32Util.registryGetValue(WinReg.HKEY_LOCAL_MACHINE,
				"SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\SoftwareProtectionPlatform",
				"BackupProductKeyDefault");

		Process p = new ProcessBuilder("cmd", "/c", "powershell",
				"\"(Get-WmiObject -query 'select * from SoftwareLicensingService').OA3xOriginalProductKey\"").start();
		InputStream in = p.getInputStream();
		p.waitFor();
		byte[] bytes = in.readAllBytes();
		String bytescombined = new String(bytes);

		byte[] encodedKey = Advapi32Util.registryGetBinaryValue(WinReg.HKEY_LOCAL_MACHINE,
				"SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "DigitalProductId");
		String decodedKey = DecodeProductKeyWin8AndUp(encodedKey);
		System.out.println(colors.getRed() + "[+] " + colors.reset() + "Current key: " + decodedKey);
		System.out.println(colors.getRed() + "[+] " + colors.reset() + "Current key (backup, usually invalid): "
				+ digitalProductId);
		System.out.println(colors.getRed() + "[+] " + colors.reset() + "OEM Key: " + bytescombined);

		Scanner sc = new Scanner(System.in);
		System.out.print(colors.getRed() + "[/] " + colors.reset() + "Press enter to continue... ");
		while (sc.hasNextLine()) {
			return;
		}
	}

	public static String DecodeProductKeyWin8AndUp(byte[] digitalProductId) {
		String key = "";
		int keyOffset = 52;
		byte keyBytes = (byte) ((digitalProductId[66] / 6) & 1);
		digitalProductId[66] = (byte) ((digitalProductId[66] & 0xf7) | (keyBytes & 2) * 4);

		String digits = "BCDFGHJKMPQRTVWXY2346789";
		int last = 0;

		for (int i = 24; i >= 0; i--) {
			int current = 0;
			for (int i2 = 14; i2 >= 0; i2--) {
				current *= 256;
				current = (digitalProductId[i2 + keyOffset] & 0xFF) + current;
				digitalProductId[i2 + keyOffset] = (byte) (current / 24);
				current %= 24;
				last = current;
			}
			key = digits.charAt(current) + key;
		}

		String keypart1 = key.substring(1, last + 1);
		String keypart2 = key.substring(last + 1);
		key = keypart1 + "N" + keypart2;

		for (int i3 = 5; i3 < key.length(); i3 += 6) {
			key = FXStringUtil.insertString(key, "-", i3);
		}

		return key;
	}
}
