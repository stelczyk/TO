import java.util.Map;

public interface RateParser {
    Map<String, Currency> parse(String xml) throws Exception;
}
