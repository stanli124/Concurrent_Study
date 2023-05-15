import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class CountLongWords {
    public static void main(String[] args) {
        try {
            String contents = new String(Files.readAllBytes(Paths.get("D:\\BaiduNetdiskDownload\\toutiao_word_corpus.txt")), StandardCharsets.UTF_8);
            List<String> words = Arrays.asList(contents.split(" "));

            long count = 0;
            long startTime = System.currentTimeMillis();
            for (String word : words){
                if (word.length() > 3) count++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("count: " + count + " ,花费时间: " +  (endTime-startTime));

            startTime = System.currentTimeMillis();
            count = words.stream().filter(word -> word.length() > 3).count();
            endTime = System.currentTimeMillis();
            System.out.println("count: " + count + " ,花费时间: " +  (endTime-startTime));

            Stream.of("","",""); //创建一个流

        }catch (IOException e){
            System.out.println(e);
        }
    }
}
