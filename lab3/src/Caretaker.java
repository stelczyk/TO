import java.util.ArrayList;
import java.util.List;

// Opiekun: przechowuje migawki, nie zaglada do ich wnetrza
public class Caretaker {
    private final Simulation originator;
    private final List<Memento> mementos = new ArrayList<>();

    public Caretaker(Simulation originator) { this.originator = originator; }

    public void create() { mementos.add(originator.save()); }

    public boolean restore(int i) {
        if (i < 0 || i >= mementos.size()) return false;
        originator.restore(mementos.get(i));
        return true;
    }

    public List<Memento> list() { return List.copyOf(mementos); }
}
