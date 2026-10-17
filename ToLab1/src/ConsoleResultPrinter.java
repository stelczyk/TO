import java.math.BigDecimal;

public class ConsoleResultPrinter implements ResultPresenter {

    @Override
    public void showResult(BigDecimal result, UserRequest request) {
        System.out.println(request.getAmount() + " " + request.getSourceCode()
                + " = " + result + " " + request.getTargetCode());
    }
}
