import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashSet;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    // Recommended: QuadTree instance variable. You'll need to make
    //              your own QuadTree since there is no built-in quadtree in Java.
    private QuadTree pop;

    /**
     * imgRoot is the name of the directory containing the images.
     * You may not actually need this for your class.
     */
    public Rasterer(String imgRoot) {
        // YOUR CODE HERE
        pop = new QuadTree();
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
<<<<<<< HEAD
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     * <li>Has dimensions of at least w by h, where w and h are the user viewport width
     * and height.</li>
     * <li>The tiles collected must cover the most longitudinal distance per pixel
     * (LonDPP) possible, while still covering less than or equal to the amount of
     * longitudinal distance per pixel in the query box for the user viewport size. </li>
     * <li>Contains all tiles that intersect the query bounding box that fulfill the
     * above condition.</li>
     * <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
=======
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
>>>>>>> 1c0df392fa29a10a01640a01900abcf4fb511fb7
     * </p>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     * the user viewport width and height.
     * @return A map of results for the front end as specified:
     * "render_grid"   -> String[][], the files to display
     * "raster_ul_lon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     * Can also be interpreted as the length of the numbers in the image
     * string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     * forget to set this to true! <br>
     * //     * @see #REQUIRED_RASTER_REQUEST_PARAMS
     */
    private LinkedList<QuadTree.QTNode> theChosenOnes;

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
//        System.out.println(params);
        theChosenOnes = new LinkedList<>();
        double resolution = (params.get("lrlon") - params.get("ullon")) / params.get("w");
        Map<String, Object> results = new HashMap<>();
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        searchAndDestroy(pop.getRoot(), params.get("ullon"), params.get("ullat"),
                params.get("lrlon"), params.get("lrlat"), resolution);
        Collections.sort(theChosenOnes);

        results.put("raster_ul_lon", theChosenOnes.peekFirst().getuLcoords()[0]);
        results.put("raster_ul_lat", theChosenOnes.peekFirst().getuLcoords()[1]);
        results.put("raster_lr_lon", theChosenOnes.peekLast().getlRcoords()[0]);
        results.put("raster_lr_lat", theChosenOnes.peekLast().getlRcoords()[1]);
        results.put("depth", theChosenOnes.get(1).depth());

        Integer[] dimen = getDimensions(theChosenOnes);
//        String[][] renderGrid = new String[dimen[1]][dimen[0]];
/*        for (int i = 0; i < dimen[1]; i++) {
            for (int k = 0; k < dimen[0]; k++) {
                renderGrid[i][k] = "img/" + theChosenOnes.removeFirst().fileName() + ".png";
            }
        }*/
        String[][] renderGrid = formatChosen(dimen, theChosenOnes);
        results.put("render_grid", renderGrid);
        results.put("query_success", true);
//        System.out.println(results);
        System.currentTimeMillis();
        return results;
    }

    //Searches for the nodes that intercept the query
    public void searchAndDestroy(QuadTree.QTNode current, double uLlong, double uLlat,
                                 double lRlong, double lRlat, double res) {
        if (current.getLonDPP() <= res || current.fileName().length() == 7) {
            if (intercept(current, uLlong, uLlat, lRlong, lRlat, res)) {
                theChosenOnes.add(current);
            }
        } else {
            for (QuadTree.QTNode node : current.getChildren()) {
                if (intercept(current, uLlong, uLlat, lRlong, lRlat, res)) {
                    searchAndDestroy(node, uLlong, uLlat, lRlong, lRlat, res);
                }
            }
        }
    }

    public Integer[] getDimensions(LinkedList<QuadTree.QTNode> list1) {
        HashSet<Double> longitudes = new HashSet<>();
        HashSet<Double> latitudes = new HashSet<>();
        for (QuadTree.QTNode x : list1) {
            longitudes.add(x.getuLcoords()[0]);
            latitudes.add(x.getuLcoords()[1]);
        }
        Integer[] dimen = {longitudes.size(), latitudes.size()};
        return dimen;
    }

    public boolean intercept(QuadTree.QTNode current, double uLlong, double uLlat,
                             double lRlong, double lRlat, double res) {
        return (current.getuLcoords()[0] < lRlong && current.getlRcoords()[0] > uLlong)
                && (current.getuLcoords()[1] > lRlat && current.getlRcoords()[1] < uLlat);
    }

    public String[][] formatChosen(Integer[] dim, LinkedList x) {
        String[][] renderGrid = new String[dim[1]][dim[0]];
        for (int i = 0; i < dim[1]; i++) {
            for (int k = 0; k < dim[0]; k++) {
                renderGrid[i][k] = "img/" + theChosenOnes.removeFirst().fileName() + ".png";
            }
        }
        return renderGrid;
    }
}
