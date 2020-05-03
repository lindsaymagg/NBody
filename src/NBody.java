	

/**
 * @author Lindsay Maggioncalda
 * lnm22 / lindsaymagg
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));
		s.nextInt();
		double rad = s.nextDouble();		
		s.close();
		return rad;	
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static Body[] readBodies(String fname) throws FileNotFoundException {
		
			Scanner s = new Scanner(new File(fname));
			// find number of bodies
			int nb = s.nextInt();
			s.nextDouble();
			Body[] bodies = new Body[nb];
			// read data for each body
			// construct new body object and add to array
			for(int k=0; k < nb; k++) {
				double x1 = s.nextDouble();
				double y1 = s.nextDouble();
				double xv1 = s.nextDouble();
				double yv1 = s.nextDouble();
				double mass = s.nextDouble();
				String imagename = s.next();
				bodies[k] = new Body(x1, y1, xv1, yv1, mass, imagename);
				
			}
			
			s.close();
			
			//return array of body objects read
			return bodies;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 157788000.0;
		double dt = 25000.0;
		
		String fname= "./data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		Body[] bodies = readBodies(fname);
		double radius = readRadius(fname);
		
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
	
		
		for(double t = 0.0; t < totalTime; t += dt) {		
			// create double arrays xforces and yforces
			// to hold forces on each body
			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];
			
			// loop over all bodies, calculate
			// net forces and store in xforces and yforces
			for (int b = 0; b < bodies.length; ++b) {
				Body currentbody = bodies[b];
				double xfor = currentbody.calcNetForceExertedByX(bodies);
				xforces[b] = xfor;
				double yfor = currentbody.calcNetForceExertedByY(bodies);
				yforces[b] = yfor;
			}
			
			// loop over all bodies and call update
			// with dt and corresponding xforces, yforces values
			for (int b = 0; b < bodies.length; ++b) {
				Body currentbody = bodies[b];
				currentbody.update(dt, xforces[b], yforces[b]);
			}		
			StdDraw.picture(0,0,"images/starfield.jpg");
			// loop over all bodies and call draw on each one
			for (Body b : bodies) {
				b.draw();
			}
			
			StdDraw.show(10);
		}
		
		// prints final values after simulation		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
