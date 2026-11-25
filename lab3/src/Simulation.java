import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

// Originator we wzorcu Pamiatka
public class Simulation {
    static final int STEPS_PER_SEC = 25;
    static final double DT = 1.0 / STEPS_PER_SEC;
    static final double W = 40, H = 40;        // obszar [m]
    static final double MAX_SPEED = 2.5;       // [m/s]
    static final int POPULATION = 50;          // docelowa liczebnosc
    static final double SPAWN_CHANCE = 0.1;    // na krok, gdy populacja < POPULATION
    static final double INFECTED_ON_ENTRY = 0.1;
    static final double IMMUNE_FRACTION = 0.3; // tylko w przypadku z odpornoscia

    final Random rnd;
    private final boolean immuneCase;
    private List<Person> persons = new ArrayList<>();
    private int stepCount = 0;
    private int nextId = 0;

    public Simulation(boolean immuneCase, long seed) {
        this.immuneCase = immuneCase;
        this.rnd = new Random(seed);
        for (int i = 0; i < POPULATION; i++)
            persons.add(newPerson(new Vector2D(rnd.nextDouble() * W, rnd.nextDouble() * H), randomVelocity(), false));
    }

    public List<Person> persons() { return persons; }
    public double time() { return (double) stepCount / STEPS_PER_SEC; }

    public void step() {
        for (Person p : persons) p.updateHealth(this);

        for (Iterator<Person> it = persons.iterator(); it.hasNext(); ) {
            Person p = it.next();
            if (rnd.nextDouble() < 1.0 / STEPS_PER_SEC) p.vel = perturb(p.vel); // srednio raz na sekunde

            double[] next = IVector.add(p.pos, IVector.scale(p.vel, DT)).getComponents();
            boolean outX = next[0] < 0 || next[0] > W, outY = next[1] < 0 || next[1] > H;
            if (!outX && !outY) { p.pos = new Vector2D(next[0], next[1]); continue; }

            if (rnd.nextBoolean()) { it.remove(); continue; } // opuszcza obszar
            double[] v = p.vel.getComponents();               // zawraca: odbicie od sciany
            p.vel = new Vector2D(outX ? -v[0] : v[0], outY ? -v[1] : v[1]);
        }

        if (persons.size() < POPULATION && rnd.nextDouble() < SPAWN_CHANCE) spawnOnBorder();
        stepCount++;
    }

    // --- Pamiatka ---
    public Memento save() { return new Memento(stepCount, nextId, persons); }

    public void restore(Memento m) {
        stepCount = m.stepCount();
        nextId = m.nextId();
        // kopia, zeby te sama migawke dalo sie przywrocic wiele razy
        persons = new ArrayList<>(m.persons().stream().map(Person::copy).toList());
    }

    // --- pomocnicze ---
    private Person newPerson(IVector pos, IVector vel, boolean entering) {
        HealthState s;
        if (entering && rnd.nextDouble() < INFECTED_ON_ENTRY) s = Infected.random(rnd);
        else if (immuneCase && rnd.nextDouble() < IMMUNE_FRACTION) s = new Immune();
        else s = new Healthy();
        return new Person(nextId++, pos, vel, s);
    }

    private IVector polar(double speed, double angle) {
        return new Vector2D(speed * Math.cos(angle), speed * Math.sin(angle));
    }

    private IVector randomVelocity() {
        return polar(rnd.nextDouble() * MAX_SPEED, rnd.nextDouble() * 2 * Math.PI);
    }

    // losowa zmiana kierunku i szybkosci, szybkosc przycieta do [0, MAX_SPEED]
    private IVector perturb(IVector vel) {
        double angle = new Polar2DAdapter(vel).getAngle() + rnd.nextGaussian() * 0.5;
        double speed = Math.clamp(vel.abs() + rnd.nextGaussian() * 0.5, 0, MAX_SPEED);
        return polar(speed, angle);
    }

    private void spawnOnBorder() {
        double t = rnd.nextDouble();
        IVector pos = switch (rnd.nextInt(4)) {
            case 0 -> new Vector2D(t * W, 0);
            case 1 -> new Vector2D(t * W, H);
            case 2 -> new Vector2D(0, t * H);
            default -> new Vector2D(W, t * H);
        };
        // kierunek na losowy punkt wewnatrz obszaru
        IVector dir = IVector.sub(new Vector2D(rnd.nextDouble() * W, rnd.nextDouble() * H), pos);
        double speed = 0.5 + rnd.nextDouble() * (MAX_SPEED - 0.5);
        persons.add(newPerson(pos, IVector.scale(dir, speed / dir.abs()), true));
    }
}
