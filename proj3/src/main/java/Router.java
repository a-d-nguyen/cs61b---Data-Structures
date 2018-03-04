import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Collections;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a LinkedList of <code>Long</code>s representing the shortest path from st to dest,
     * where the longs are node IDs.
     */
    public static LinkedList<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                                double destlon, double destlat) {
        LinkedList<Long> route;

        Long source = g.closest(stlon, stlat);
        Long destinationID = g.closest(destlon, destlat);
        GraphDBNode sourceNode = g.nodes.get(source);
        GraphDBNode destinationNode = g.nodes.get(destinationID);

        HashMap<GraphDBNode, Double> originToNode = new HashMap<GraphDBNode, Double>();
        HashSet<GraphDBNode> visited = new HashSet<GraphDBNode>();
        HashMap<GraphDBNode, GraphDBNode> parents = new HashMap<GraphDBNode, GraphDBNode>();
        Comparator<GraphDBNode> newPriority = new PriorityCompare(originToNode, destinationNode);
        PriorityQueue<GraphDBNode> fringe = new PriorityQueue<GraphDBNode>(g.nodes.size(),
                newPriority);

        originToNode.put(sourceNode, (double) 0);
        parents.put(sourceNode, null);
        fringe.add(sourceNode);

        while (!fringe.isEmpty()) {
            GraphDBNode node = fringe.poll();
            if (node.equals(destinationNode)) {
                break;
            } else if (visited.contains(node)) {
                continue;
            } else {
                visited.add(node);
            }
            Double fValue = originToNode.get(node);
            for (Long x : node.getAllDestinations()) {
                GraphDBNode temporary = g.nodes.get(x);
                if (visited.contains(temporary)) {
                    continue;
                }
                Double newFValue = distanceUsingCoords(node.getLongitude(), node.getLatitude(),
                        temporary.getLongitude(), temporary.getLatitude()) + fValue;
                if (bestRoute(temporary, newFValue, originToNode)) {
                    originToNode.put(temporary, newFValue);
                    parents.put(temporary, node);
                    fringe.add(temporary);
//                    System.out.println(newFValue);
                }
            }
        }

        route = createRoute(destinationNode, parents, sourceNode);

//        System.out.println(route);
        return route;
    }

    public static double distanceUsingCoords(double xlon, double xlat, double ylon, double ylat) {
        return Math.sqrt(Math.pow((xlon - ylon), 2)
                + Math.pow((xlat - ylat), 2));
    }

    public static LinkedList<Long> createRoute(GraphDBNode destination, HashMap<GraphDBNode,
            GraphDBNode> parentals, GraphDBNode source) {
        LinkedList<Long> createRoute = new LinkedList<>();
        GraphDBNode findTheWay = destination;
        while (parentals.get(findTheWay) != null) {
            createRoute.add(findTheWay.getId());
            findTheWay = parentals.get(findTheWay);
        }
        createRoute.add(source.getId());
        Collections.reverse(createRoute);

/*        createRoute.add(source.getId());
        for (GraphDBNode x: parentals.keySet()) {
            createRoute.add(x.getId());
        }*/

        return createRoute;
    }

    public static boolean bestRoute(GraphDBNode temporary, double fValue,
                                    HashMap<GraphDBNode, Double> originToNode) {
        if (!originToNode.containsKey(temporary)) {
            return true;
        } else if (fValue < originToNode.get(temporary)) {
            return true;
        }
        return false;
    }

    public static class PriorityCompare implements Comparator<GraphDBNode> {
        HashMap<GraphDBNode, Double> originToNode;
        GraphDBNode dest;

        public PriorityCompare(HashMap<GraphDBNode, Double> originToNode, GraphDBNode dest) {
            this.originToNode = originToNode;
            this.dest = dest;
        }

        @Override
        public int compare(GraphDBNode x, GraphDBNode y) {
            if (originToNode.get(x) + distanceUsingCoords(x.getLongitude(), x.getLatitude(),
                    dest.getLongitude(), dest.getLatitude())
                    > originToNode.get(y) + distanceUsingCoords(y.getLongitude(),
                    y.getLatitude(), dest.getLongitude(), dest.getLatitude())) {
                return 1;
            }
            return -1;
        }
    }
}

/*        Long source = g.closest(stlon, stlat);
        Long destinationID = g.closest(destlon, destlat);
        route.add(source);

        int size = 0;

        Long nextAdd = null;
        while (!route.contains(destinationID)) {
            HashSet<Long> temp = g.nodes.get(route.get(size)).getAllDestinations();
            double distance = 10000000;
            if (temp.contains(destinationID)) {
                route.add(destinationID);
            } else {
                for (Long x : temp) {
                    double dist = g.distance(x, destinationID) + g.distance(route.get(size), x);
                    if (dist < distance) {
                        distance = dist;
                        nextAdd = x;
                    }
                }
                route.add(nextAdd);
            }
            size += 1;

        }

       Comparator<GraphDBNode> priority = (x, y) -> Double.compare(originToNode.get(x)
                        + distanceUsingCoords(x.getLongitude(), x.getLatitude(),
                destinationNode.getLongitude(), destinationNode.getLatitude()),
                originToNode.get(y) + distanceUsingCoords(y.getLongitude(),
                        y.getLatitude(), destinationNode.getLongitude(),
                        destinationNode.getLatitude()));
        */
