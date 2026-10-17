import java.net.URI;
import java.net.URLConnection;

public class NbpDataSource implements DataSource {
    private final String url;

    public NbpDataSource(String url) {
        this.url = url;
    }

    @Override
    public String getData() throws Exception {
        URLConnection connection = URI.create(url).toURL().openConnection();
        return new String(connection.getInputStream().readAllBytes(), "ISO-8859-2");
    }
}
