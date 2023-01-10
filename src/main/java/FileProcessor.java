import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileProcessor {
    private FileContentExtractor fileContentExtractor;

    public FileProcessor(FileContentExtractor fileContentExtractor) {
        this.fileContentExtractor = fileContentExtractor;
    }

    public void process(String filePathString) throws IOException {
        boolean isTextFile = filePathString.endsWith(".txt");
        if(!isTextFile) {
            throw new IllegalArgumentException("ERROR: has to be text file");
        }

        Path filePath = Paths.get(filePathString);
        List<String> lines = Files.readAllLines(filePath);

        long nrOfClients = fileContentExtractor.getNrOfClients(lines);
        long nrOfSellers = fileContentExtractor.getNrOfSellers(lines);
        int biggestSaleId = fileContentExtractor.getBiggestSaleId(lines);
        String sellerWithLeastMoney = fileContentExtractor.getSellerWithLeastMoney(lines);

        String resultsFilePathString = filePathString.replace(".txt", ".done.txt");
        Path resultsFilePath = Files.createFile(Paths.get(resultsFilePathString));

        Files.writeString(resultsFilePath, "Number of Clients found in the file: " + nrOfClients + "\n", StandardOpenOption.APPEND);
        Files.writeString(resultsFilePath, "Number of Sellers found in the file: " + nrOfSellers + "\n", StandardOpenOption.APPEND);
        Files.writeString(resultsFilePath, "Sales id of the biggest sale: " + biggestSaleId + "\n", StandardOpenOption.APPEND);
        Files.writeString(resultsFilePath, "Name of the Seller that earned less money: " + sellerWithLeastMoney + "\n", StandardOpenOption.APPEND);

        Files.delete(filePath);
    }
}

