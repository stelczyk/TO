import java.math.BigDecimal;

public class UserRequest {
    private final String sourceCode;
    private final BigDecimal amount;
    private final String targetCode;

    public UserRequest(String sourceCode, BigDecimal amount, String targetCode) {
        this.sourceCode = sourceCode;
        this.amount = amount;
        this.targetCode = targetCode;
    }

    public String getSourceCode() { return sourceCode; }
    public BigDecimal getAmount() { return amount; }
    public String getTargetCode() { return targetCode; }
}
