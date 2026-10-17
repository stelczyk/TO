import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CurrencyConverter implements Converter {

    // wynik = kwota * (kursA / unitA) / (kursB / unitB)
    @Override
    public BigDecimal convert(Map<String, Currency> rates, UserRequest request) {
        BigDecimal from = perOneUnit(rates.get(request.getSourceCode()));
        BigDecimal to = perOneUnit(rates.get(request.getTargetCode()));
        return request.getAmount().multiply(from).divide(to, 2, RoundingMode.HALF_UP);
    }

    // kurs za 1 jednostkę (np. JPY jest podawany za 100)
    private BigDecimal perOneUnit(Currency c) {
        return c.getAveragePrice().divide(BigDecimal.valueOf(c.getUnit()), 10, RoundingMode.HALF_UP);
    }
}
