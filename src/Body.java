
public class Body {
	//instance variables
	private double myXVel;
	private double myYVel;
	private double myXPos;
	private double myYPos;
	private double myMass;
	private String myFileName;
	
	//accessor methods: get desired value and return it
	public double getXVel() {
		return myXVel;
	}

	public double getYVel() {
		return myYVel;
	}

	public double getX() {
		return myXPos;
	}

	public double getY() {
		return myYPos;
	}

	public double getMass() {
		return myMass;
	}

	public String getName() {
		return myFileName;
	}

	//Create Body from parameters
	public Body(double x, double y, double xv, double yc, double mass, String filename) {
		myXVel = xv;
		myYVel = yc;
		myXPos = x;
		myYPos = y;
		myMass = mass;
		myFileName = filename;
	}
	
	//Copy Body b
	public Body(Body b) {
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myXPos = b.getX();
		myYPos = b.getY();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	
	//Calculate distance between Body and Body b
	public double calcDistance(Body b) {
		double dx = (this.getX()-b.getX());
		double dy = (this.getY()-b.getY());
		double radsq = ((dx*dx) + (dy*dy));
		double dist = Math.sqrt(radsq);
		return dist;
	}
	
	//Calculate force exerted on Body by Body p
	public double calcForceExertedBy(Body p) {
		double gravconst = 6.67*1e-11;
		double masses = (this.getMass()*p.getMass());
		double radsq = (this.calcDistance(p))*(this.calcDistance(p));
		double force = (gravconst * masses)/radsq;
		return force;
	}
	
	//Calculate force exerted in the x-direction on Body by Body p
	public double calcForceExertedByX(Body p) {
		double force = (this.calcForceExertedBy(p));
		double dx = (this.getX()-p.getX());
		double dist = this.calcDistance(p);
		double fx = (force*dx)/dist;
		return fx*-1;		
	}
	
	//Calculate force exerted in the y-direction on Body by Body p
	public double calcForceExertedByY(Body p) {
		double force = (this.calcForceExertedBy(p));
		double dy = (this.getY()-p.getY());
		double dist = this.calcDistance(p);
		double fy = (force*dy)/dist;
		return fy*-1;		
	}
	
	//Calculate net force exerted in the x-direction on Body by all bodies
	public double calcNetForceExertedByX(Body[] bodies) {
		double netforcex = 0.0;
		for (Body b : bodies) {
			if (! b.equals(this)) {
				netforcex += this.calcForceExertedByX(b);
			}			
		}
		return netforcex;
		}	
	
	//Calculate net force exerted in the y-direction on Body by all bodies
	public double calcNetForceExertedByY(Body[] bodies) {
		double netforcey = 0.0;
		for (Body b : bodies) {
			if (! b.equals(this)) {
				netforcey += this.calcForceExertedByY(b);
			}			
		}
		return netforcey;
		}
	
	//Update the Body's position and velocities
	public void update(double deltaT, double xforce, double yforce) {
		double ax = xforce/(this.getMass());
		double ay = yforce/(this.getMass());
		double nvx = myXVel + deltaT*ax;
		double nvy = myYVel + deltaT*ay;
		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;		
	}
	
	//Draw Body
	public void draw() {
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}
}
