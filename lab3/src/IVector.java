import java.util.Arrays;

public interface IVector {
    double abs();
    double cdot(IVector param);
    double[] getComponents();

    // ponytail: wektor 2D => z = 0, copyOf dopelnia zerami
    static double[] xyz(IVector v) {
        return Arrays.copyOf(v.getComponents(), 3);
    }

    static double dot(IVector a, IVector b) {
        double[] p = xyz(a), q = xyz(b);
        return p[0] * q[0] + p[1] * q[1] + p[2] * q[2];
    }

    // metoda macierzowa: det | i j k ; a ; b | rozwiniety wzgledem pierwszego wiersza
    static IVector cross(IVector a, IVector b) {
        double[] p = xyz(a), q = xyz(b);
        double x = det2(p[1], p[2], q[1], q[2]);
        double y = -det2(p[0], p[2], q[0], q[2]);
        double z = det2(p[0], p[1], q[0], q[1]);
        return new Vector3DDecorator(new Vector2D(x, y), z);
    }

    // lab3: operacje potrzebne do ruchu (wynik 2D - symulacja jest plaska)
    static IVector add(IVector a, IVector b) {
        double[] p = xyz(a), q = xyz(b);
        return new Vector2D(p[0] + q[0], p[1] + q[1]);
    }

    static IVector scale(IVector a, double k) {
        double[] p = xyz(a);
        return new Vector2D(p[0] * k, p[1] * k);
    }

    static IVector sub(IVector a, IVector b) {
        return add(a, scale(b, -1));
    }

    private static double det2(double a, double b, double c, double d) {
        return a * d - b * c;
    }
}
