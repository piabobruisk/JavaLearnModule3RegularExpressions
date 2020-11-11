package by.epam.pia.learning.string.regularexpressions.task1;

//1. Cоздать приложение, разбирающее текст (текст хранится в строке)
//и позволяющее выполнять с текстом три различных операции:
// - отсортировать абзацы по количеству предложений;
// - в каждом предложении отсортировать слова по длине;
// - отсортировать лексемы в предложении по убыванию количества вхождений заданного символа, а в случае равенства – по алфавиту.

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String charFind = "о";

    private static final String delimiter = "----------------------------------------------------------------------------------------------------------";
    private static final String string = "   Вначале Анге почувствовала боль в горле. Потом заболело всё и сразу. Голова кружилась," +
            " неслась куда-то\nкак отпущенный по ветру воздушный шарик, но одновременно была тяжёлой как свинец. Тело ныло," +
            " словно её\nдолго и небрежно волочили по земле. А ещё у неё было гадкое ощущение, что она обмочилась – " +
            "комбинезон\nв паху был влажным и липким.\n" +
            "\tЧто с ней?\n" +
            "   Она открыла глаза – с трудом. Полутьма, низкий металлический потолок, рядом жёсткие ложементы кресел…\n" +
            "она полулежала-полусидела в таком же, пристёгнутая ремнями безопасности. Анге пошевелила пальцами на руке.\n" +
            "Бережно повернула голову.\n" +
            "   Ну да. Она в космическом корабле. В первом звездолёте Невара – «Дружбе». Точнее, она в одном из его\nпосадочных катеров…" +
            " и катер в свободном полёте… она в невесомости.\n" +
            "   А впереди, в кресле пилота, развёрнутом в положение «для кис» — Криди. Лучший инженер, которого она\nзнала –" +
            " что среди «детей Солнца», что среди кис. Её лучший, да и вообще единственный друг на корабле.\n" +
            "Её муж, пусть и по символическому обряду.\n" +
            "   Диверсант!\n" +
            "   Заложивший взрывчатку в один из катеров!\n" +
            "   Пытавшийся её задушить!\n" +
            "   Анге осторожно дотянулась до кнопки блокировки ремня. Нажала.\n" +
            "   Ремни не ослабли.\n" +
            "   — Не отстёгивайся, — резко сказал Криди не поворачиваясь. Его спина напряглась, хвост дёрнулся.\n" +
            "В пилотажном кресле он лежал на животе, вытянув к пульту передние лапы.\n" +
            "   — Зачем, Криди? – хрипло спросила Анге.\n" +
            "   — Через минуту мы входим в атмосферу. Тебя будет швырять по кабине, глупая обезьяна!\n" +
            "   Эти слова – «обезьяна», «обезьянья дочь» никогда раньше не казались Анге обидными. Ну да, они произошли\n" +
            "от обезьян. А кисы от кошачьих. На соседних планетах эволюция выбрала разные виды для того, чтобы одарить\nих" +
            " непрошенным разумом.\n" +
            "   Анге порой звала Криди «котом». Он порой звал её «обезьяной». Это было нормально, это были грубоватые\nшутки," +
            " на которые они имели право.\n" +
            "   До тех пор, пока лапы кота не сжались на обезьяньем горле.\n";

    public static void main(String[] args) {

        List<Paragraph> paragraphs = getParagraph();

        System.out.println("Исходный текст");
        System.out.println("Всего абзацев:" + paragraphs.size());
        System.out.println(delimiter);
        System.out.println(string);


        paragraphs.sort(new Comparator<Paragraph>() {
            @Override
            public int compare(Paragraph o1, Paragraph o2) {
                return o1.getSentenceCount() < o2.getSentenceCount() ? -1 : (o1.getSentenceCount() > o2.getSentenceCount()) ? 1 : 0;
            }
        });

        System.out.println("Сортировка абзацев");
        System.out.println(delimiter);

        for (int j = 0, paragraphsSize = paragraphs.size(); j < paragraphsSize; j++) {
            Paragraph paragraph = paragraphs.get(j);
            int sentenceCount = paragraph.getSentenceCount();
            System.out.println("\nПредложений в " + (j + 1) + " абзаце: " + sentenceCount);
            System.out.println(paragraph);

            // - в каждом предложении отсортировать слова по длине;
            Comparator<String> wordLength = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.length() < o2.length() ? -1 : (o1.length() > o2.length()) ? 1 : 0;
                }
            };

            System.out.println("\nСортировка слов в предложении по длине");

            for (int i = 0; i < sentenceCount; i++) {
                List<String> words = paragraph.getSentenceWord(i);
                words.sort(wordLength);
                System.out.println(Arrays.toString(words.toArray(new String[0])));
            }

            // - отсортировать лексемы в предложении по убыванию количества вхождений заданного символа, а в случае равенства – по алфавиту.

            Comparator<String> charCount = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    //получить количество вхождения символа
                    int o1Count = getCharCount(o1);
                    int o2Count = getCharCount(o2);

                    if (o1Count<o2Count) return -1;
                    if (o1Count>o2Count) return 1;

                    char o1Char = o1.toLowerCase().charAt(0);
                    char o2Char = o2.toLowerCase().charAt(0);
                    // тут тогда сравнение по алфавиту
                    if (o1Char<o2Char) return -1;
                    if (o1Char>o2Char) return 1;
                    return  0;
                }
            };

            System.out.println("Сортировка слов в предложении по количеству вхождения заданного символа");
            for (int i = 0; i < sentenceCount; i++) {

                Set<String> set = new HashSet<>(paragraph.getSentenceWord(i));
                List<String> words = new ArrayList<>(set);

                words.sort(charCount);
                System.out.println(Arrays.toString(words.toArray(new String[0])));
            }

        }
    }

    private static int getCharCount(String str) {
        int count=0;
        Pattern pattern = Pattern.compile(charFind);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static ArrayList<Paragraph> getParagraph() {
        int start = 0;
        ArrayList<Paragraph> result = new ArrayList<>();

        Pattern pattern = Pattern.compile("[.!?]+[\n]{1,2}[ ]{2,}|\t+");
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            String findMatches = string.substring(matcher.start(), matcher.end());
            String[] tmp = findMatches.split("\n");
            result.add(new Paragraph(string.substring(start, matcher.start()+tmp[0].length() )));
            start = matcher.end();
            if (tmp.length>1)start-=tmp[1].length();
        }
        result.add(new Paragraph(string.substring(start, string.length() - 1)));

        return result;
    }
}
