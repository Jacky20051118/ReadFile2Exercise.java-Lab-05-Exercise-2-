import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFile2Exercise extends Thread {

    private static int notFinished;
    private static StringBuffer result = new StringBuffer();

    private String fileName;

    synchronized static void finished(String words) {
        notFinished--;
        result.append(words);

        if (notFinished == 0) {
            System.out.println("Extracted words: " + result.toString());
        }
    }

    public ReadFile2Exercise(String f) {
        fileName = f;
    }

    @Override
    public void run() {
        StringBuffer extracted = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            while (true) {
                String st = reader.readLine();
                if (st == null) {
                    break;
                }
                String word[] = st.trim().split(" +");
                for (int i = 0; i < word.length; i++) {
                    if (word[i].length() > 7) {
                        extracted.append(word[i]).append(" ");
                    }
                }
            }
            finished(extracted.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void main(String[] st) {
        ReadFile2Exercise.notFinished = st.length;
        for (String fileName : st) {
            ReadFile2Exercise readFile2Exercise = new ReadFile2Exercise(fileName);
            readFile2Exercise.start();
        }
    }
}
