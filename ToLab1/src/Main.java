import java.math.BigDecimal;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        DataSource source = new NbpDataSource("https://static.nbp.pl/dane/kursy/xml/lasta.xml");
        RateParser parser = new NbpXmlRateParser();
        InputReader reader = new ConsoleInputReader();
        Converter converter = new CurrencyConverter();
        ResultPresenter printer = new ConsoleResultPrinter();

        Map<String, Currency> rates;
        try {
            rates = parser.parse(source.getData());
        } catch (Exception e) {
            System.out.println("Nie udało się pobrać kursów NBP: " + e.getMessage());
            return;
        }

        UserRequest request = reader.readUserInput(rates.keySet());
        BigDecimal result = converter.convert(rates, request);
        printer.showResult(result, request);
    }
}