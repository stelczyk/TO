import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

public class ConsoleInputReader implements InputReader {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public UserRequest readUserInput(Set<String> availableCodes) {
        System.out.println("Dostępne waluty: " + String.join(", ", availableCodes));
        String source = readCode("Waluta źródłowa: ", availableCodes);
        BigDecimal amount = readAmount();
        String target = readCode("Waluta docelowa: ", availableCodes);
        return new UserRequest(source, amount, target);
    }

    // pyta, dopóki kod nie będzie na liście
    private String readCode(String prompt, Set<String> availableCodes) {
        while (true) {
            System.out.print(prompt);
            String code = scanner.nextLine().trim().toUpperCase();
            if (availableCodes.contains(code)) return code;
            System.out.println("Nie ma takiej waluty, spróbuj ponownie.");
        }
    }

    // pyta, dopóki nie dostanie liczby większej od 0
    private BigDecimal readAmount() {
        while (true) {
            System.out.print("Kwota: ");
            try {
                BigDecimal amount = new BigDecimal(scanner.nextLine().trim().replace(',', '.'));
                if (amount.signum() > 0) return amount;
            } catch (NumberFormatException ignored) { }
            System.out.println("Kwota musi być liczbą większą od 0.");
        }
    }
}
