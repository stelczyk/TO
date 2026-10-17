import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class NbpXmlRateParser implements RateParser {

    @Override
    public Map<String, Currency> parse(String xml) throws Exception {
        NodeList positions = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)))
                .getElementsByTagName("pozycja");

        Map<String, Currency> rates = new TreeMap<>(); // kody posortowane alfabetycznie
        rates.put("PLN", new Currency("PLN", "złoty polski", 1, BigDecimal.ONE)); // nie ma go w tabeli NBP

        for (int i = 0; i < positions.getLength(); i++) {
            Element p = (Element) positions.item(i);
            String code = get(p, "kod_waluty");
            rates.put(code, new Currency(code, get(p, "nazwa_waluty"),
                    Integer.parseInt(get(p, "przelicznik")),
                    new BigDecimal(get(p, "kurs_sredni").replace(',', '.'))));
        }
        return rates;
    }

    private String get(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent().trim();
    }
}
