import org.bukkit.ChatColor;

public class TextUtil {
	private static final int[] characterWidths = { 1, 9, 9, 8, 8, 8, 8, 7, 9,
			8, 9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9,
			9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6, 3,
			6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6,
			6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6,
			3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 3, 6,
			6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9, 9, 6, 6, 6, 8, 8, 6,
			8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
			9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9, 8, 7, 7, 8, 7,
			8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6,
			9, 7, 6, 7, 1 };

	private static final String allowedChars = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~¦ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»";
	private static final char COLOR_CHAR = '§';
	private static final int CHAT_WINDOW_WIDTH = 320;
	private static final int CHAT_STRING_LENGTH = 119;

	public static String[] wrapText(String text) {
		StringBuilder out = new StringBuilder();
		StringBuilder line = new StringBuilder();

		int lineWidth = 0;

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);

			if (ch == '\n') {
				out.append(line.toString());
				out.append('\n');
				line = new StringBuilder();
				line.append(ChatColor.getLastColors(out.toString()));
			} else if (ch == COLOR_CHAR && (i < text.length() - 1)) {
				char colorChar = text.charAt(++i);
				if (Character.toString(colorChar)
						.matches("[0-9a-fA-Fk-oK-ORr]")) {
					line.append(COLOR_CHAR).append(colorChar);
				} else {
					line.append("&").append(colorChar);
				}
			} else {
				int index = allowedChars.indexOf(ch);
				if (index != -1) {
					index += 32;
					int width = characterWidths[index];
					line.append(ch);
					lineWidth += width;

					if (line.length() > CHAT_STRING_LENGTH
							|| lineWidth > CHAT_WINDOW_WIDTH) {
						// Try to split
						int lastSpace = line.lastIndexOf(" ");
						if (lastSpace >= 0) {
							if (lastSpace - 1 > 0) {
								line.delete(lastSpace, line.length());
							}
							out.append(line);
							i = text.lastIndexOf(' ', i);
						} else {
							out.append(line.delete(0, line.length() - 1)
									.toString());
							i--;
						}
						out.append('\n');
						line = new StringBuilder();
						line.append(ChatColor.getLastColors(out.toString()));
						lineWidth = 0;
					}
				}
			}
		}
		if (ChatColor.stripColor(line.toString()).length() > 0) {
			out.append(line);
		}
		return out.toString().split("\n");
	}

	public static String capitalizeFirst(String s) {
		if (s.length() <= 1) {
			return s.toUpperCase();
		} else {
			return s.substring(0, 1).toUpperCase().concat(s.substring(1));
		}
	}
}