import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        FileProcessor fileProcessor = new FileProcessor(new FileContentExtractor());
        ExecutorService executorService = Executors.newCachedThreadPool();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Please enter file path");
            String filePathString = scanner.nextLine();

            executorService.submit(() -> {
                try {
                    fileProcessor.process(filePathString);
                } catch (Exception e) {
                    System.out.println("ERROR: Failed to process file: " + filePathString);
                    e.printStackTrace();
                }
            });
        }
    }
}
