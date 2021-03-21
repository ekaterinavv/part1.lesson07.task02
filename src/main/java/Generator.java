
import java.io.*;
import java.util.*;

public class Generator {
    static Random random = new Random();
    final int n1 = 15;
    final int n2 = 15;
    final int n3 = 20;
    final int n4 = 1000;
    final int n5 = 15;
    float probability;
    final float commaProbability = 0.2f;
    List<String> wordsList = new ArrayList<>();
    Character[] lastSymbolAtSentence = new Character[]{'.', '!', '?'};

    //Слово состоит из 1<=n2<=15 латинских букв
    public String genegateWord() {
        int len = random.nextInt(n1) + 1;
        int dif = 'z' - 'a';
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) (random.nextInt(dif) + 'a');
        }
        return new String(str);
    }

    //Есть массив слов 1<=n4<=1000.
    public List<String> genegateWordsArray() {
        List<String> wordsListNew = new ArrayList<>();
        int len = random.nextInt(n4) + 1;
        for (int i = 0; i < len; i++) {
            wordsListNew.add(genegateWord());
        }
        return wordsListNew;
    }

    //Предложение начинается с заглавной буквы
    //Предложение заканчивается (.|!|?)+" "
    //Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
    public String generateSentence() {
        String sentence = "";
        int len = random.nextInt(n2) + 1;
        for (int i = 0; i < len; i++) {
            // Есть вероятность probability вхождения одного из слов этого массива в следующее
            //предложение (1/probability).
            float probabilityCurWord = random.nextFloat();
            sentence += (probabilityCurWord < probability) ? wordsList.get(random.nextInt(wordsList.size())) : genegateWord();
            sentence += (commaProbability > random.nextFloat()) ? "," : " ";
        }
        sentence = firstUpperCase(sentence);
        sentence = replaceLastChar(sentence, lastSymbolAtSentence[random.nextInt(lastSymbolAtSentence.length)]);
        return sentence;
    }

    //В одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
    public String genegateParagraph() {
        String paragraph = "";
        int len = random.nextInt(n3) + 1;
        for (int i = 0; i < len; i++) {
            paragraph += generateSentence();
            paragraph += "\r\n";
        }
        return paragraph;
    }

    //Текст состоит из абзацев
    public String genegateText() {
        String text = "";
        int len = random.nextInt(n3) + 1;
        for (int i = 0; i < len; i++) {
            text += genegateParagraph();
        }
        return text;
    }

    private static String firstUpperCase(String word) {
        if (word == null || word.isEmpty()) {
            return ""; //или return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private static String replaceLastChar(String word, char symbol) {
        if (word == null || word.isEmpty()) {
            return ""; //или return word;
        }
        return word.substring(0, word.length() - 1) + symbol;
    }

    /*Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability),
    который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.*/
    public void getFiles(String path, int n, int size, String[] words, int probability) {
        if (words != null) {
            this.wordsList = Arrays.asList(words);
            for (int i = 0; i < wordsList.size(); i++)
                wordsList.set(i, wordsList.get(i).toLowerCase());
        } else {
            this.wordsList = genegateWordsArray();
        }
        this.probability = probability / 100f;
        try {
            new File(path).mkdir();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < n; i++) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/text" + (i + 1) + ".txt"))) {
                int fileSize = 0;
                while (true) {
                    String text = genegateText();
                    fileSize += text.getBytes().length;
                    if (fileSize > size) {
                        break;
                    }
                    bw.write(genegateText() + "\n");
                    bw.flush();
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
        }
        System.out.println("Success!");
    }
}
