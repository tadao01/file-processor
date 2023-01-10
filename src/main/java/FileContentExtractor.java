import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileContentExtractor {
    public long getNrOfClients(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith("001"))
                .count();
    }

    public long getNrOfSellers(List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith("002"))
                .count();
    }

    public int getBiggestSaleId(List<String> lines) {
        String biggestSale =  lines.stream()
                .filter(line -> line.startsWith("003"))
                .max(Comparator.comparing(line -> getTotalSalePrice(line)))
                .get();
        return Integer.parseInt(biggestSale.split(",")[1]);
    }

    public String getSellerWithLeastMoney(List<String> lines) {
        String sellerWithLeastMoney = lines.stream()
                .filter(line -> line.startsWith("001"))
                .min(Comparator.comparing(line -> getSellerMoney(line.split(",")[1], lines)))
                .get();
        return sellerWithLeastMoney.split(",")[1];
    }

    private double getSellerMoney(String name, List<String> lines) {
        return lines.stream()
                .filter(line -> line.startsWith("003") && line.endsWith(name))
                .mapToDouble(line -> getTotalSalePrice(line))
                .sum();
    }

    private double getTotalSalePrice(String line) {
        String[] sales = line.replace("[", "")
                .replace("]", "")
                .split(",")[2]
                .split(";");

        return Arrays.stream(sales)
                .mapToDouble(sale -> {
                    double amount = Double.parseDouble(sale.split("-")[1]);
                    double price = Double.parseDouble(sale.split("-")[2]);
                    return amount * price;
                })
                .sum();
    }
}
