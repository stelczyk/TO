public class Vector3DInheritance extends Vector2D {
    private final double z;

    public Vector3DInheritance(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    @Override public double[] getComponents() {
        double[] s = super.getComponents();
        return new double[]{s[0], s[1], z};
    }

    @Override public double abs() { return Math.sqrt(cdot(this)); }
    public IVector cross(IVector param) { return IVector.cross(this, param); }
}
