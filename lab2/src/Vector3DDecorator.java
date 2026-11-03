public class Vector3DDecorator implements IVector {
    private final IVector srcVector;
    private final double z;

    public Vector3DDecorator(IVector srcVector, double z) {
        this.srcVector = srcVector;
        this.z = z;
    }

    public double[] getComponents() {
        double[] s = srcVector.getComponents();
        return new double[]{s[0], s[1], z};
    }

    public double abs() { return Math.sqrt(cdot(this)); }
    public double cdot(IVector param) { return IVector.dot(this, param); }
    public IVector cross(IVector param) { return IVector.cross(this, param); }
    public IVector getSrcV() { return srcVector; }
}
