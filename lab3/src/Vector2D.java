public class Vector2D implements IVector {
    private final double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getComponents() { return new double[]{x, y}; }
    public double abs() { return Math.hypot(x, y); }
    public double cdot(IVector param) { return IVector.dot(this, param); }
}
