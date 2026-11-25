// Context we wzorcu Stan
public class Person {
    final int id;
    IVector pos, vel;   // [m], [m/s] - wektory z lab2 (niemutowalne)
    HealthState state;

    Person(int id, IVector pos, IVector vel, HealthState state) {
        this.id = id;
        this.pos = pos;
        this.vel = vel;
        this.state = state;
    }

    void updateHealth(Simulation sim) { state = state.step(this, sim); }

    double distance(Person o) { return IVector.sub(pos, o.pos).abs(); }

    Person copy() { return new Person(id, pos, vel, state.copy()); } // wektory niemutowalne - wspoldzielimy

    double x() { return pos.getComponents()[0]; }
    double y() { return pos.getComponents()[1]; }
}
