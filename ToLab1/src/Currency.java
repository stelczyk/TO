import java.math.BigDecimal;

public class Currency {
    private final String code;
    private final String name;
    private final int unit;
    private final BigDecimal averagePrice;

    public Currency(String code, String name, int unit, BigDecimal averagePrice) {
        this.code = code;
        this.name = name;
        this.unit = unit;
        this.averagePrice = averagePrice;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getUnit() { return unit; }
    public BigDecimal getAveragePrice() { return averagePrice; }
}
