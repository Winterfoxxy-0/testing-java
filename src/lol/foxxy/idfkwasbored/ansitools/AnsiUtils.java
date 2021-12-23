package lol.foxxy.idfkwasbored.ansitools;

public class AnsiUtils {
	private final String ANSI_RESET = "\u001B[0m";
	private final String ANSI_BLACK = "\u001B[30m";
	private final String ANSI_RED = "\u001B[31m";
	private final String ANSI_GREEN = "\u001B[32m";
	private final String ANSI_YELLOW = "\u001B[33m";
	private final String ANSI_BLUE = "\u001B[34m";
	private final String ANSI_PURPLE = "\u001B[35m";
	private final String ANSI_CYAN = "\u001B[36m";
	private final String ANSI_WHITE = "\u001B[37m";
	
	public String reset() {
		return ANSI_RESET;
	}
	public String getBlack() {
		return ANSI_BLACK;
	}
	public String getRed() {
		return ANSI_RED;
	}
	public String getGreen() {
		return ANSI_GREEN;
	}
	public String getYellow() {
		return ANSI_YELLOW;
	}
	public String getBlue() {
		return ANSI_BLUE;
	}
	public String getPurple() {
		return ANSI_PURPLE;
	}
	public String getCyan() {
		return ANSI_CYAN;
	}
	public String getWhite() {
		return ANSI_WHITE;
	}
}
