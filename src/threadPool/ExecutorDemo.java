package threadPool;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExecutorDemo {

    /**
     * 计算文件中给定单词的出现频率
     * @param word
     * @param path
     * @return 返回给定单词的出现次数
     */
    public static long occurrences(String word, Path path){
        try (Scanner in = new Scanner(path))
        {
            int count = 0;
            while (in.hasNext())
                if (in.next().equals(word)) count++;
            return count;
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * 仅返回所有文件的路径
     * @param rootDir 给定的根目录
     * @return
     * @throws IOException
     */
    public static Set<Path> descendants(Path rootDir) throws IOException{
        try (Stream<Path> entries = Files.walk(rootDir))
        {
            return entries.filter(Files::isRegularFile).collect(Collectors.toSet());
        }
    }

    /**
     *
     * @param word 需要搜索的单词
     * @param path 在哪个文件中进行搜索
     * @return
     */
    public static Callable<Path> searchForTask(String word, Path path){
        return ()->{
            try (Scanner in = new Scanner(path))
            {
                while (in.hasNext()){
                    if (in.next().equals(word)) return path;
                    if (Thread.currentThread().isInterrupted()){
                        System.out.println("Search in "+path+" canceled.");
                        return null;
                    }
                }
            }
            throw new NoSuchElementException();
        };
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("输入需要扫描的根目录: ");
            String start = in.nextLine();
            System.out.println("输出关键词 (如class): ");
            String word = in.nextLine();

            Set<Path> files = descendants(Paths.get(start));
            ArrayList<Callable<Long>> tasks = new ArrayList<>();

            for (Path file : files) {
                //为每一个文件创建一个callable对象
                Callable<Long> task = () -> occurrences(word, file);
                tasks.add(task);
            }

            //这里可以替换为单个线程池，看看多线程是否可以加速处理过程;new出现56696次 花费时间: 2435 ms
            ExecutorService executor = Executors.newCachedThreadPool();
            //new出现56696次  花费时间: 8099 ms
//            ExecutorService executor = Executors.newSingleThreadExecutor();

            Instant startTime = Instant.now();
            List<Future<Long>> results = executor.invokeAll(tasks);
            long total = 0;
            for (Future<Long> result : results)
                total += result.get();
            Instant endTime = Instant.now();
            System.out.println(word + "出现" + total + "次");
            System.out.println("花费时间: " + Duration.between(startTime, endTime).toMillis()+" ms");



            ArrayList<Callable<Path>> searchTasks = new ArrayList<>();
            for (Path file : files){
                searchTasks.add(searchForTask(word, file));
            }
            Path found = executor.invokeAny(searchTasks);
            System.out.printf(word+"出现在: "+found);

            if (executor instanceof ThreadPoolExecutor){
                System.out.printf("最大线程数量: " + ((ThreadPoolExecutor) executor).getLargestPoolSize());
            }

            executor.shutdown();
        }
    }
}
