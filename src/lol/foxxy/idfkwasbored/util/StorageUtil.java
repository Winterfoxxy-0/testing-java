package lol.foxxy.idfkwasbored.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StorageUtil {
	public static String humanReadableByteCountBin(long bytes) {
		// not going to bother with this one, all it does is conver bytes into a format that isn't hell to read
		long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
		if (absB < 1024) {
			return bytes + " B";
		}
		long value = absB;
		CharacterIterator ci = new StringCharacterIterator("KMGTPE");
		for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
			value >>= 10;
			ci.next();
		}
		value *= Long.signum(bytes);
		return String.format("%.1f %ciB", value / 1024.0, ci.current());
	}
}
