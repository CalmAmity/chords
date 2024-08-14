import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Chords {
    public static void main(String[] args) {
        final String[] input = readFile(args[0]);

        int index = 0;
        while (index < input.length) {
            System.out.print(zipStrings(input[index], input[index + 1]));
            index += 2;

            if (index >= input.length || input[index].isBlank()) {
                System.out.print("</p>\n\n<p>");
                index++;
            } else {
                System.out.println("<br/>");
            }
        }
    }

    private static String[] readFile(String filename) {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            while (line != null) {
                result.add(line);
                line = br.readLine();
            }

            return result.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String zipStrings(final String chords, final String words) {
        int index = 0;
        int endOfCurrentChord = -1;
        final StringBuilder result = new StringBuilder();
        while (index < Math.min(chords.length(), words.length())) {
            if (index >= endOfCurrentChord && chords.charAt(index) != ' ') {
                endOfCurrentChord = indexOfDisregardFirst(chords, ' ', index);
                if (endOfCurrentChord == -1) {
                    endOfCurrentChord = chords.length();
                }
                final String chord = chords.substring(index, endOfCurrentChord);

                trimEndingWhitespace(result);

                result.append("<c>");
                result.append(chord);
                result.append("</c>");

                if (words.charAt(index) == ' ') {
                    index++;
                }
            }

            result.append(words.charAt(index));
            index++;
        }

        if (index < chords.length()) {
            result.append("<c>");
            result.append(chords.substring(index).trim());
            result.append("</c>");
        }

        if (index < words.length()) {
            result.append(words.substring(index));
        }

        return result.toString();
    }

    private static int indexOfDisregardFirst(final String str, final char c, final int firstIndex) {
        final int substringIndex = str.substring(firstIndex).indexOf(c);
        if (substringIndex == -1) {
            return -1;
        }
        return substringIndex + firstIndex;
    }

    private static StringBuilder trimEndingWhitespace(final StringBuilder stringBuilder) {
        while (!stringBuilder.isEmpty() && Character.isWhitespace(stringBuilder.charAt(stringBuilder.length() - 1))) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder;
    }
}
