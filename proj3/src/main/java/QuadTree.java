/**
 * Created by Anthony Nguyen on 4/16/2017.
 */
public class QuadTree {
    private QTNode root;

    public QuadTree() {
        double[] uL = {MapServer.ROOT_ULLON, MapServer.ROOT_ULLAT};
        double[] lR = {MapServer.ROOT_LRLON, MapServer.ROOT_LRLAT};
        root = new QTNode("root", uL, lR);
        treeGrowth(this.root);
    }

    private void treeGrowth(QTNode node) {
        if (node.depth() == 7) {
            return;
        }
        double centerLon = (node.uLcoords[0] + node.lRcoords[0]) / 2;
        double centerLat = (node.uLcoords[1] + node.lRcoords[1]) / 2;

        String name = node.imgFile;
        if (name.equals("root")) {
            name = "";
        }

        for (int p = 0; p < 4; p++) {
            if (p == 0) {
                double[] newUl = node.uLcoords;
                double[] newLr = {centerLon, centerLat};
                node.reNameChild(p, new QTNode(name + "1", newUl, newLr));
            } else if (p == 1) {
                double[] newUl = {centerLon, node.uLcoords[1]};
                double[] newLr = {node.lRcoords[0], centerLat};
                node.reNameChild(p, new QTNode(name + "2", newUl, newLr));
            } else if (p == 2) {
                double[] newUl = {node.uLcoords[0], centerLat};
                double[] newLr = {centerLon, node.lRcoords[1]};
                node.reNameChild(p, new QTNode(name + "3", newUl, newLr));
            } else {
                double[] newUl = {centerLon, centerLat};
                double[] newLr = node.lRcoords;
                node.reNameChild(p, new QTNode(name + "4", newUl, newLr));
            }
            treeGrowth(node.getChild(p));
        }

/*        double[] newUl = node.uLcoords;
        double[] newLr = {centerLon, centerLat};
        node.reNameChild(0, new QTNode(name + "1", newUl, newLr));
        treeGrowth(node.getChild(0));

        double[] newUl2 = {centerLon, node.uLcoords[1]};
        double[] newLr2 = {node.lRcoords[0], centerLat};
        node.reNameChild(1, new QTNode(name + "2", newUl2, newLr2));
        treeGrowth(node.getChild(1));

        double[] newUl3 = {node.uLcoords[0], centerLat};
        double[] newLr3 = {centerLon, node.lRcoords[1]};
        node.reNameChild(2, new QTNode(name + "3", newUl3, newLr3));
        treeGrowth(node.getChild(2));

        double[] newUl4 = {centerLon, centerLat};
        double[] newLr4 = node.lRcoords;
        node.reNameChild(3, new QTNode(name + "4", newUl4, newLr4));
        treeGrowth(node.getChild(3));*/

    }

    public QTNode getRoot() {
        return root;
    }

    public class QTNode implements Comparable<QTNode> {
        private String imgFile;
        private double[] uLcoords;
        private double[] lRcoords;
        private QTNode[] children = new QTNode[4];

        public QTNode(String imgFile, double[] uLcoords, double[] lRcoords) {
            this.imgFile = imgFile;
            this.uLcoords = uLcoords;
            this.lRcoords = lRcoords;
        }

        @Override
        public int compareTo(QTNode x) {
            if (this.uLcoords[1] > x.uLcoords[1]) {
                return -1;
            }
            return 1;
        }

        public QTNode getChild(int which) {
            return children[which];
        }

        public QTNode[] getChildren() {
            return children;
        }

        public double getLonDPP() {
            return (lRcoords[0] - uLcoords[0]) / 256;
        }

        public void reNameChild(int x, QTNode node) {
            children[x] = node;
        }

        public String fileName() {
            return imgFile;
        }

        public double[] getuLcoords() {
            return uLcoords;
        }

        public double[] getlRcoords() {
            return lRcoords;
        }

        public int depth() {
            return imgFile.length();
        }

    }
}
