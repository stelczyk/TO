# Lab 1 - Kalkulator walut NBP

Konsolowy program w Javie, który przelicza dowolną kwotę z jednej waluty na drugą
według aktualnych średnich kursów NBP

## Działanie

1. Pobiera tabelę kursów ze strony NBP.
2. Zamienia XML na listę walut (z dodanym PLN, względem którego NBP podaje kursy).
3. Pyta o walutę źródłową, kwotę i walutę docelową. Błędne dane są odrzucane,
   a program pyta ponownie.
4. Przelicza kwotę przez PLN i wypisuje wynik, np. `100 USD = 93.12 EUR`.
   Wzór: `wynik = kwota × (kursA / przelicznikA) / (kursB / przelicznikB)`


Program zaprojektowano zgodnie z zasadami **SOLID**.
Każdy krok (pobieranie, parsowanie, wczytywanie, przeliczanie, wyświetlanie)
ma osobny interfejs i jedną klasę, która go implementuje. Konkretne klasy
tworzy tylko `Main`; reszta programu zależy wyłącznie od interfejsów.