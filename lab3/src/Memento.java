import java.util.List;

// Pamiatka: niemutowalna migawka stanu symulacji. Odczyt tylko przez Simulation.restore().
public final class Memento {
    private final int stepCount;
    private final int nextId;
    private final List<Person> persons; // glebokie kopie

    Memento(int stepCount, int nextId, List<Person> persons) {
        this.stepCount = stepCount;
        this.nextId = nextId;
        this.persons = persons.stream().map(Person::copy).toList();
    }

    int stepCount() { return stepCount; }
    int nextId() { return nextId; }
    List<Person> persons() { return persons; }

    public double time() { return (double) stepCount / Simulation.STEPS_PER_SEC; }
}
