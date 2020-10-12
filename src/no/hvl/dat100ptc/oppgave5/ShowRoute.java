package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
        double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

        ystep = MAPXSIZE / (Math.abs(maxlat - minlat)); 

        return ystep;
		
	}

	public void showRouteMap(int ybase) {

		int r=3;
		int x,y;
		
		setColor(0,255,0);
		double[] tabLat = GPSUtils.getLatitudes(gpspoints);
		double[] tabLon = GPSUtils.getLongitudes(gpspoints);
		
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		for(int i=0; i<tabLat.length; i++) {
			
			x=MARGIN+(int)((tabLon[i]-minlon)*xstep());
			y=(int)(ybase-(tabLat[i]-minlat)*ystep());
			
			
			fillCircle(x,y,r);
			if(i>0) {
				int a = MARGIN+(int)((tabLon[i-1]-minlon)*xstep());
				int b = (int)(ybase-(tabLat[i-1]-minlat)*ystep());
				
				drawLine(a,b, x, y);
			}
			
		}
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		
		drawString("Total time      :" + GPSUtils.formatTime(gpscomputer.totalTime()), MARGIN, 20);
		drawString("Total distance  :" + GPSUtils.formatDouble(gpscomputer.totalDistance()), MARGIN, 40);
		drawString("Total elevation :" + GPSUtils.formatDouble(gpscomputer.totalElevation()), MARGIN, 60);
		drawString("Max speed       :" + GPSUtils.formatDouble(gpscomputer.maxSpeed()), MARGIN, 80);
		drawString("Average speed   :" + GPSUtils.formatDouble(gpscomputer.averageSpeed()), MARGIN, 100);
		drawString("Energy          :" + GPSUtils.formatDouble(gpscomputer.totalKcal(80)), MARGIN, 120);

		
//		System.out.println("Total time     : " + totalTime()); 
//		System.out.println("Total distance : " + totalDistance());
//		System.out.println("Total elevation: " + totalElevation());
//		System.out.println("Max speed      : " + maxSpeed());
//		System.out.println("Average speed  : " + averageSpeed());
//		System.out.println("Energy         : " + totalKcal(WEIGHT));
	}

}
