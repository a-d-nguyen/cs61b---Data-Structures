import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    HashMap<Long, GraphDBNode> nodes = new HashMap<>();
    static HashMap<String, GraphDBNode> locationNames = new HashMap<>();
    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // Your code here.
        Long[] temp = (Long[]) nodes.keySet().toArray(new Long[nodes.size()]);
//        Iterable<Long> temp1 = vertices();
        for (Long x: temp) {
            if (nodes.get(x).getConnections().isEmpty()) {
                nodes.remove(x);
            }
        }
    }

    /** Returns an iterable of all vertex IDs in the graph. */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        Iterable<Long> iteratable = nodes.keySet();
        return iteratable;
//        return new ArrayList<Long>();
    }

    /** Returns ids of all vertices adjacent to v. */
    Iterable<Long> adjacent(long v) {
        return nodes.get(v).getAllDestinations();
//        return null;
    }

    /** Returns the Euclidean distance between vertices v and w, where Euclidean distance
     *  is defined as sqrt( (lonV - lonV)^2 + (latV - latV)^2 ). */
    double distance(long v, long w) {
        GraphDBNode first = nodes.get(v);
        GraphDBNode second = nodes.get(w);
        return Math.sqrt(Math.pow(first.getLongitude() - second.getLongitude(), 2)
                + Math.pow(first.getLatitude() - second.getLatitude(), 2));
//        return Math.sqrt(Math.pow(v, 2) - Math.pow(w, 2));
    }

    /** Returns the vertex id closest to the given longitude and latitude. */
    long closest(double lon, double lat) {
        Iterable<Long> iteratable = vertices();
        long closest = 0;
        double distance = 1000000;
        for (Long x: iteratable) {
            GraphDBNode temp = nodes.get(x);
            double dist = temp.whatisDistance(lon, lat);
            if (dist < distance) {
                distance = dist;
                closest = x;
            }

        }
        return closest;
//        return 0;
    }

    /** Longitude of vertex v. */
    double lon(long v) {
        return nodes.get(v).getLongitude();
//        return 0;
    }

    /** Latitude of vertex v. */
    double lat(long v) {
        return nodes.get(v).getLatitude();
//        return 0;
    }
}
