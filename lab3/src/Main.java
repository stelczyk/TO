import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

// java Main          - nikt nie ma odpornosci
// java Main immune   - czesc populacji odporna
// java Main check    - test pamiatki bez okna
public class Main extends JPanel {
    private final Simulation sim;
    private final Caretaker caretaker;
    private final Timer timer;
    private String message = "S - migawka, 1-9 - przywroc, spacja - pauza";

    Main(Simulation sim) {
        this.sim = sim;
        this.caretaker = new Caretaker(sim);
        setPreferredSize(new Dimension(700, 760));
        setBackground(Color.WHITE);
        setFocusable(true);
        timer = new Timer(1000 / Simulation.STEPS_PER_SEC, e -> { sim.step(); repaint(); });

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == ' ') {
                    if (timer.isRunning()) timer.stop(); else timer.start();
                } else if (c == 's' || c == 'S') {
                    caretaker.create();
                    message = String.format("Zapisano migawke %d (t = %.2f s)", caretaker.list().size(), sim.time());
                } else if (c >= '1' && c <= '9') {
                    int i = c - '1';
                    message = caretaker.restore(i)
                        ? String.format("Przywrocono migawke %d (t = %.2f s)", i + 1, sim.time())
                        : "Brak migawki " + (i + 1);
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int top = 60;
        double scale = Math.min(getWidth(), getHeight() - top) / Math.max(Simulation.W, Simulation.H);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, top, (int) (Simulation.W * scale), (int) (Simulation.H * scale));

        int healthy = 0, sym = 0, asym = 0, immune = 0;
        for (Person p : sim.persons()) {
            switch (p.state) {
                case Healthy h -> healthy++;
                case Symptomatic s -> sym++;
                case Asymptomatic a -> asym++;
                default -> immune++;
            }
            int x = (int) (p.x() * scale), y = top + (int) (p.y() * scale);
            g.setColor(p.state.color());
            g.fillOval(x - 5, y - 5, 10, 10);
        }

        g.setColor(Color.BLACK);
        g.drawString(String.format("t = %.2f s   osobnikow: %d   zdrowi: %d   objawowi: %d   bezobjawowi: %d   odporni: %d",
            sim.time(), sim.persons().size(), healthy, sym, asym, immune), 10, 20);
        g.drawString(message + (timer.isRunning() ? "" : "   [PAUZA]"), 10, 40);
    }

    // zapis w t1, symulacja dalej, przywrocenie => ten sam stan co w t1
    static void check() {
        Simulation sim = new Simulation(false, 1);
        for (int i = 0; i < 2000; i++) sim.step();
        Memento m = sim.save();
        String before = snapshot(sim.persons());
        for (int i = 0; i < 500; i++) sim.step();
        assert !snapshot(sim.persons()).equals(before);
        sim.restore(m);
        assert snapshot(sim.persons()).equals(before) : "restore nie odtworzyl stanu";
        assert sim.time() == m.time();
        sim.step();                  // zmiana po przywroceniu nie psuje migawki
        sim.restore(m);
        assert snapshot(sim.persons()).equals(before) : "migawka zmieniona po restore";
        System.out.println("check OK, t = " + sim.time() + " s, osobnikow: " + sim.persons().size());
    }

    private static String snapshot(List<Person> ps) {
        StringBuilder sb = new StringBuilder();
        for (Person p : ps) sb.append(p.id).append(p.x()).append(p.y()).append(p.state.getClass().getSimpleName());
        return sb.toString();
    }

    public static void main(String[] args) {
        String mode = args.length > 0 ? args[0] : "";
        if (mode.equals("check")) { check(); return; }

        Simulation sim = new Simulation(mode.equals("immune"), System.nanoTime());
        SwingUtilities.invokeLater(() -> {
            Main panel = new Main(sim);
            JFrame f = new JFrame("Lab 3 - symulacja zakazen" + (mode.equals("immune") ? " (z odpornoscia)" : ""));
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(panel);
            f.pack();
            f.setVisible(true);
            panel.requestFocusInWindow();
            panel.timer.start();
        });
    }
}
