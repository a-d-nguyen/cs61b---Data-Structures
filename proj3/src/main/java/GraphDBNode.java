import java.util.HashSet;

/**
 * Created by Anthony Nguyen on 4/19/2017.
 */
public class GraphDBNode {
    private long id;
    private double longitude;
    private double latitude;
    private HashSet<ConnectFour> adjacent;
    private HashSet<Long> allDestinations = new HashSet<>();
    String name;

    public GraphDBNode(long id, double lon, double lat) {
        this.id = id;
        this.longitude = lon;
        this.latitude = lat;
        adjacent = new HashSet<ConnectFour>();
    }

    public void reName(String newName) {
        name = newName;
    }

    public long getId() {
        return id;
    }

    public void addFour(GraphDBNode x) {
        if (this.adjacent.contains(x)) {
            return;
        }
        Double distance = whatisDistance(x.longitude, x.latitude);
        this.adjacent.add(new ConnectFour(this.id, x.id, distance));
        this.allDestinations.add(x.getId());
    }

    public double whatisDistance(double lon, double lat) {
        return Math.sqrt(Math.pow((this.longitude - lon), 2)
                + Math.pow((this.latitude - lat), 2));
    }

    public HashSet<ConnectFour> getConnections() {
        return adjacent;
    }

    public HashSet<Long> getAllDestinations() {
        return allDestinations;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public class ConnectFour {
        long location;
        long destination;
        double distance;

        public ConnectFour(long x, long y, double dist) {
            this.location = x;
            this.destination = y;
            this.distance = dist;
        }
    }
}
