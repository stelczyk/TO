public class Polar2DInheritance extends Vector2D implements IPolar2D {
    public Polar2DInheritance(double x, double y) { super(x, y); }

    public double getAngle() {
        double[] c = getComponents();
        return Math.atan2(c[1], c[0]);
    }
}
