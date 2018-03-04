public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] allP = readPlanets(filename);

        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for (int i = 0; i < allP.length; i++) {
            allP[i].draw();
        }

        double time = 0;
        StdAudio.loop("audio/2001.mid");
        while (time <= T) {
            double[] xForces = new double[allP.length];
            double[] yForces = new double[allP.length];
            for (int i = 0; i < allP.length; i++) {
                xForces[i] = allP[i].calcNetForceExertedByX(allP);
                yForces[i] = allP[i].calcNetForceExertedByY(allP);
            }
            for (int i = 0; i < allP.length; i++) {
                allP[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (int i = 0; i < allP.length; i++) {
                allP[i].draw();
            }
            StdDraw.show(10);
            time += dt;
        }
        StdOut.printf("%d\n", allP.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < allP.length; i++) {
	          StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   		         allP[i].xxPos, allP[i].yyPos, allP[i].xxVel, allP[i].yyVel, allP[i].mass, allP[i].imgFileName);
        }
    }
    public static double readRadius(String n) {
        In input = new In(n);
        input.readDouble();
        return input.readDouble();
    }

    public static Planet[] readPlanets(String n) {
        In input = new In(n);
        Planet[] allP = new Planet[input.readInt()];
        int count = 0;
        input.readDouble();
        while (count < allP.length) {
            allP[count] = new Planet(input.readDouble(), input.readDouble(), input.readDouble(), input.readDouble(), input.readDouble(), input.readString());
            count ++;
        }
        return allP;
    }

}
