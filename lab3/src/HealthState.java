import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Wzorzec Stan: kazdy stan sam decyduje o swoim zachowaniu i o przejsciu do nastepnego
public interface HealthState {
    HealthState step(Person me, Simulation sim); // zwraca nastepny stan (albo this)
    Color color();
    HealthState copy();                           // dla pamiatki
}

// wrazliwy + zdrowy
class Healthy implements HealthState {
    static final double RANGE = 2.0;                             // [m]
    static final int CONTACT_STEPS = 3 * Simulation.STEPS_PER_SEC; // 3 s

    // id zakazonego -> ile krokow z rzedu jest w zasiegu
    private final Map<Integer, Integer> contact = new HashMap<>();

    public HealthState step(Person me, Simulation sim) {
        Map<Integer, Integer> now = new HashMap<>();
        for (Person o : sim.persons()) {
            if (!(o.state instanceof Infected inf) || me.distance(o) > RANGE) continue;
            int steps = contact.getOrDefault(o.id, 0) + 1;
            // ponytail: jedno losowanie na nieprzerwany kontakt, dokladnie w chwili osiagniecia 3 s
            if (steps == CONTACT_STEPS && sim.rnd.nextDouble() < inf.infectionChance())
                return Infected.random(sim.rnd);
            now.put(o.id, steps);
        }
        contact.clear();        // kontakt przerwany => licznik od zera
        contact.putAll(now);
        return this;
    }

    public Color color() { return new Color(40, 170, 60); }

    public HealthState copy() {
        Healthy h = new Healthy();
        h.contact.putAll(contact);
        return h;
    }
}

// wrazliwy + zakazony; podtrzymuje zakazenie 20-30 s, potem odpornosc
abstract class Infected implements HealthState {
    protected int stepsLeft;

    Infected(int stepsLeft) { this.stepsLeft = stepsLeft; }

    static Infected random(Random rnd) {
        int steps = rnd.nextInt(20 * Simulation.STEPS_PER_SEC, 30 * Simulation.STEPS_PER_SEC + 1);
        return rnd.nextBoolean() ? new Symptomatic(steps) : new Asymptomatic(steps);
    }

    abstract double infectionChance();

    public HealthState step(Person me, Simulation sim) {
        return --stepsLeft <= 0 ? new Immune() : this;
    }
}

class Symptomatic extends Infected {
    Symptomatic(int stepsLeft) { super(stepsLeft); }
    double infectionChance() { return 1.0; }
    public Color color() { return new Color(220, 30, 30); }
    public HealthState copy() { return new Symptomatic(stepsLeft); }
}

class Asymptomatic extends Infected {
    Asymptomatic(int stepsLeft) { super(stepsLeft); }
    double infectionChance() { return 0.5; }
    public Color color() { return new Color(245, 150, 20); }
    public HealthState copy() { return new Asymptomatic(stepsLeft); }
}

// odporny - stan koncowy
class Immune implements HealthState {
    public HealthState step(Person me, Simulation sim) { return this; }
    public Color color() { return new Color(50, 110, 220); }
    public HealthState copy() { return this; } // bezstanowy, mozna wspoldzielic
}
