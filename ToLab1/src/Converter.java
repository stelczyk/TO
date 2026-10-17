import java.math.BigDecimal;
import java.util.Map;

public interface Converter {
    BigDecimal convert(Map<String, Currency> rates, UserRequest request);
}
