# Lab 3 - Stan oraz pamiątka

Symulacja rozprzestrzeniania się wirusa w populacji poruszającej się po obszarze 40 × 40 m, z wizualizacją w oknie Swing oraz zapisem i wczytywaniem stanu w dowolnym momencie.

## Uruchomienie

Wymagana Java 21. Z folderu `lab3`:

```
javac -d out src/*.java
java -cp out Main            # przypadek 1: nikt nie ma odporności
java -cp out Main immune     # przypadek 2: 30% osobników odpornych
java -ea -cp out Main check  # test pamiątki bez okna (-ea włącza asercje)
```

Sterowanie: `S` - zapis migawki, `1`-`9` - przywrócenie migawki o danym numerze, spacja - pauza.

Kolory: zielony - zdrowy, czerwony - zakażony z objawami, pomarańczowy - zakażony bez objawów, niebieski - odporny.

## Działanie

Każda sekunda symulacji to 25 kroków. W każdym kroku:
1. **Zdrowie** - każdy osobnik wykonuje krok swojego stanu. Zdrowy liczy, jak długo zakażony jest w odległości ≤ 2 m; po 3 s nieprzerwanego kontaktu zaraża się z prawdopodobieństwem 100% (od objawowego) lub 50% (od bezobjawowego). Zakażony po 20-30 s zdrowieje i staje się odporny.
2. **Ruch** - pozycja = pozycja + prędkość · 0.04 s. Średnio raz na sekundę kierunek i szybkość losowo się zmieniają (szybkość ≤ 2.5 m/s). Na granicy obszaru osobnik z prawdopodobieństwem 50% zawraca, a w 50% opuszcza obszar.
3. **Dopływ** - gdy osobników jest mniej niż 50, nowi wchodzą z losowych punktów granicy (10% z nich jest zakażonych).

Ruch jest modelowany wektorami z Laboratorium 2 (`IVector`, `Vector2D`, `Polar2DAdapter` do odczytu kierunku), rozszerzonymi o `add`, `scale` i `sub`.

## Wzorce

**Stan** (`HealthState.java`, `Person.java`)
- `Person` (Context) trzyma jeden obiekt stanu i wywołuje `state = state.step(...)`.
- Stany: `Healthy`, `Symptomatic`, `Asymptomatic` (wspólna klasa bazowa `Infected`) oraz `Immune`. Każdy sam decyduje o swoim zachowaniu i o przejściu do następnego stanu:
  `Healthy → Symptomatic / Asymptomatic → Immune`.
- Osobnik zawsze ma dokładnie jeden stan, więc nie da się być np. jednocześnie odpornym i zakażonym.

**Pamiątka** (`Simulation.java`, `Memento.java`, `Caretaker.java`)
- `Simulation` (Originator) tworzy migawkę (`save()`) i odtwarza z niej swój stan (`restore()`).
- `Memento` to niezmienna, głęboka kopia stanu: czas, licznik id oraz kopie osobników wraz ze stanami zdrowia.
- `Caretaker` przechowuje listę migawek i nie zagląda do ich zawartości.

## Założenia

- Początkowa populacja jest zdrowa, a wirus pojawia się wraz z wchodzącymi osobnikami.
- Zakażony ma objawy z prawdopodobieństwem 50%.
- Jeden nieprzerwany kontakt trwający 3 s daje jedno losowanie zakażenia.
- Migawki są przechowywane w pamięci programu. Generator liczb losowych nie jest częścią migawki, więc po przywróceniu stan świata jest identyczny, ale dalszy przebieg może się różnić.
