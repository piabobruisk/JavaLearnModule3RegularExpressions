package by.epam.pia.learning.string.regularexpressions.task1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paragraph {

    private ArrayList<String> sentence;

    public Paragraph(String str) {

        int start = 0;
        sentence = new ArrayList<>();

        str = str.replace("\n", " ");
        Pattern pattern = Pattern.compile("[.!?]+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String findSentence = str.substring(start, matcher.end()).trim();
            sentence.add(findSentence);
            start = matcher.end() + 1;
        }
    }


    public int getSentenceCount() {
        return sentence.size();
    }

    public String getSentence(int index) {
        return sentence.get(index);
    }

    public ArrayList<String> getSentenceWord(int index) {
        ArrayList<String> result = new ArrayList<>();
        String tmp = sentence.get(index);

        Pattern pattern = Pattern.compile("[а-яёА-ЯЁ]+");
        Matcher matcher = pattern.matcher(tmp);

        while (matcher.find()) {
            String findWord = tmp.substring(matcher.start(), matcher.end());
            result.add(findWord);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("   ");
        for (String string : sentence) {
            result.append(string);
            result.append(" ");
        }
        return result.toString();
    }
}
