public class Planet {

    /** Instance Variables */
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        double r = Math.pow(dx * dx + dy * dy, 0.5);
        return r;
    }

    public double calcForceExertedBy(Planet p) {
        double g = 6.67e-11;
        double force = (g * this.mass * p.mass) / Math.pow(calcDistance(p), 2);
        return force;
    }

    public double calcForceExertedByX(Planet p) {
        double force = this.calcForceExertedBy(p);
        double dx = p.xxPos - this.xxPos;
        double r = this.calcDistance(p);
        double forceX = force * dx / r;
        return forceX;
    }

    public double calcForceExertedByY(Planet p) {
        double force = this.calcForceExertedBy(p);
        double dy = p.yyPos - this.yyPos;
        double r = this.calcDistance(p);
        double forceY = force * dy / r;
        return forceY;
    }

    public double calcNetForceExertedByX(Planet[] allP) {
        int index = 0;
        double net = 0;
        while (index < allP.length) {
            if (this.equals(allP[index])) {
                index += 1;
            } else {
                net += calcForceExertedByX(allP[index]);
                index += 1;
            }
        }
        return net;
    }

    public double calcNetForceExertedByY(Planet[] allP) {
        int index = 0;
        double net = 0;
        while (index < allP.length) {
            if (this.equals(allP[index])) {
                index += 1;
            } else {
                net += calcForceExertedByY(allP[index]);
                index += 1;
            }
        }
        return net;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel += dt * aX;
        this.yyVel += dt * aY;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" +this.imgFileName);
    }

}
