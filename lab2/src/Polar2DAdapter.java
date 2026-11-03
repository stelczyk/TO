public class Polar2DAdapter implements IPolar2D, IVector {
    private final IVector srcVector;

    public Polar2DAdapter(IVector srcVector) {
        this.srcVector = srcVector;
    }

    public double abs() { return srcVector.abs(); }
    public double cdot(IVector param) { return srcVector.cdot(param); }
    public double[] getComponents() { return srcVector.getComponents(); }

    // kat miedzy osia OX a wektorem (dla 3D: rzut na XY)
    public double getAngle() {
        double[] c = IVector.xyz(srcVector);
        return Math.atan2(c[1], c[0]);
    }
}
