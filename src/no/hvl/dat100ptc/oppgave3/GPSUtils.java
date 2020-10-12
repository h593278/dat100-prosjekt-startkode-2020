package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		
		double[] latitude = new double[gpspoints.length];
		
			for(int i = 0; i < gpspoints.length; i++) {
				
				latitude[i] = gpspoints[i].getLatitude();
			}
		
		return latitude;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitudes = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			
			longitudes[i] = gpspoints[i].getLongitude();
		}
	
	return longitudes;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double phi1 = toRadians(gpspoint1.getLatitude());
		double phi2 = toRadians(gpspoint2.getLatitude());
		double deltaPhi = phi2-phi1;
		double deltaLamda = toRadians(gpspoint2.getLongitude()-gpspoint1.getLongitude());
	
		double sinDeltaPhi = Math.pow(Math.sin(deltaPhi/2),2);
		double sinDeltaLamda = Math.pow(Math.sin(deltaLamda/2),2);
		double cosPhi1 = Math.cos(phi1);
		double cosPhi2 = Math.cos(phi2);

		double a = sinDeltaPhi+cosPhi1*cosPhi2*sinDeltaLamda;
		
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R*c; 
		
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		double meter = distance(gpspoint1, gpspoint2);
		
		secs = gpspoint2.getTime()-gpspoint1.getTime();
		
		speed = meter/secs;
		speed = speed*3.6;
		
		return speed;
	}

	public static String formatTime(int secs) {

		String timestr = "";
		String TIMESEP = ":";

		int timer = secs/3600;
		
		int rest = secs%(60*60);
		
		int min = rest/60;
		
		rest = rest%60;
		
		int sekunder = rest;
		
		if (timer<10) {
			timestr+="0";
			timestr+=timer;
		} else {
			timestr+=timer;
		}
		
		timestr += TIMESEP;
		
		if (min<10) {
			timestr+="0";
			timestr+=min;
		} else {
			timestr+=min;
		}
		
		timestr += TIMESEP;
		
		if (sekunder<10) {
			timestr+="0";
			timestr+=sekunder;
		} else {
			timestr+=sekunder;
		}
				
		timestr=String.format("%10s", timestr);
		return timestr;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str = "";
		
		d=Math.floor(d*100+0.5)/100;
		
		String beholder=""+d;
		
		if (d%1>=0 && d%1<0.11) {
			beholder+="0";
		}
		
		str=String.format("%s", beholder);

		return str;
	}
}
