package by.epam.pia.learning.string.regularexpressions.task2;

//2.  Дана строка, содержащая следующий текст (xml-документ):
//
//<notes>
//   <note id = "1">
//       <to>Вася</to>
//       <from>Света</from>
//       <heading>Напоминание</heading>
//       <body>Позвони мне завтра!</body>
//   </note>
//   <note id = "2">
//       <to>Петя</to>
//       <from>Маша</from>
//       <heading>Важное напоминание</heading>
//       <body/>
//   </note>
//</notes>
//
//Напишите анализатор, позволяющий последовательно возвращать содержимое узлов xml-документа
//и его тип (открывающий тег, закрывающий тег, содержимое тега, тег без тела).
//Пользоваться готовыми парсерами XML для решения данной задачи нельзя.

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static int level = 0;
    private static final String string =
            "<notes>\n" +
                    "   <note id = \"1\">\n" +
                    "       <to>Вася</to>\n" +
                    "       <from>Света</from>\n" +
                    "       <heading>Напоминание</heading>\n" +
                    "       <body>Позвони мне завтра!</body>\n" +
                    "   </note>\n" +
                    "   <note id = \"2\">\n" +
                    "       <to>Петя</to>\n" +
                    "       <from>Маша</from>\n" +
                    "       <heading>Важное напоминание</heading>\n" +
                    "       <body/>\n" +
                    "   </note>\n" +
                    "</notes>";

    public static void main(String[] args) {

        String[] lines = string.split("\n");
        for (String line : lines) {
            System.out.println(line);
            analyseNode(line);
        }
    }

    private static void analyseNode(String str) {

        String openTAG = "";
        String attribyte = "";
        Pattern patternOpen = Pattern.compile("<.+?>");
        Matcher matcherOpen = patternOpen.matcher(str);

        if (matcherOpen.find()) {

            String rawOpenTAG = str.substring(matcherOpen.start() + 1, matcherOpen.end() - 1);

            if (rawOpenTAG.charAt(0) == '/') {

                // если тег содержит / в начале то это конечный тег
                level--;
                System.out.println("Тег " + rawOpenTAG.substring(1, rawOpenTAG.length()) + " закрывающий, уровень: " + level + ".  ");
            } else if (rawOpenTAG.charAt(rawOpenTAG.length() - 1) == '/') {

                // если тег содержит / в конце то это пустой тег
                System.out.println("Тег " + rawOpenTAG.substring(0, rawOpenTAG.length() - 1) + " пустой, уровень: " + level + ".  ");
            } else {

                String[] tmp = rawOpenTAG.split(" ", 2);
                openTAG = tmp[0];

                System.out.print("Тег " + openTAG + " открывающий, уровень: " + level + ".  ");

                if (tmp.length > 1) attribyte = tmp[1];
                if (attribyte.length() > 0) System.out.print("Атрибут тега: " + attribyte);

                level++;

                Pattern patternClose = Pattern.compile("</" + openTAG + ">");
                Matcher matcherClose = patternClose.matcher(str);
                if (matcherClose.find()) {

                    System.out.print("Содержимое: " + str.substring(matcherOpen.end(), matcherClose.start()) + ".  ");
                    System.out.print("Тег " + str.substring(matcherClose.start() + 2, matcherClose.end() - 1) + " закрывающий.  ");
                    level--;
                }
                System.out.println();
            }
        }
    }
}
