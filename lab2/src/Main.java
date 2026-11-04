import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        IVector[] v = {
            new Polar2DAdapter(new Vector2D(3, 4)),
            new Vector3DDecorator(new Vector2D(1, 2), 3),
            new Vector3DInheritance(2, -1, 4)
        };
        String[] names = {"A", "B", "C"};

        for (int i = 0; i < v.length; i++) {
            // wektor, ktory sam nie jest IPolar2D, opakowujemy adapterem
            IPolar2D p = v[i] instanceof IPolar2D ? (IPolar2D) v[i] : new Polar2DAdapter(v[i]);
            System.out.printf("%s: kartezjanskie %s, biegunowe r=%.4f, kat=%.4f rad%n",
                names[i], Arrays.toString(v[i].getComponents()), p.abs(), p.getAngle());
        }
        //xd
        for (int i = 0; i < v.length; i++)
            for (int j = 0; j < v.length; j++)
                System.out.printf("%s . %s = %.2f    %s x %s = %s%n", names[i], names[j], v[i].cdot(v[j]),
                    names[i], names[j], Arrays.toString(IVector.cross(v[i], v[j]).getComponents()));
    }
}
