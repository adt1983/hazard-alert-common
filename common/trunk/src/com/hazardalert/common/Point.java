package com.hazardalert.common;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

/*
 * CAP stores points as [lat, lng], WKT stores points as [lng, lat] - Helper class to sort this mess out.
 */
@SuppressWarnings("serial")
public class Point extends com.vividsolutions.jts.geom.Coordinate {
	public static final Point BB_NE = new Point(-90.0, -180.0);

	public static final Point BB_SW = new Point(90.0, 180.0);

	public Point() {}

	public Point(double _lat, double _lng) {
		setLat(_lat);
		setLng(_lng);
	}

	public Point(com.vividsolutions.jts.geom.Coordinate jts) {
		this(jts.y, jts.x);
	}

	public Point(com.vividsolutions.jts.geom.Point jts) {
		this(jts.getY(), jts.getX());
	}

	public Point(com.google.publicalerts.cap.Point p) {
		this(p.getLatitude(), p.getLongitude());
	}

	public Point(Point p) {
		this(p.getLat(), p.getLng());
	}

	public void setLat(double _lat) {
		if (-90.0 > _lat || _lat > 90.0) {
			throw new IllegalArgumentException("Invalid Latitude: " + _lat);
		}
		y = _lat;
	}

	public void setLng(double _lng) {
		if (-180.0 > _lng || _lng > 180.0) {
			throw new IllegalArgumentException("Invalid Longitude: " + _lng);
		}
		x = _lng;
	}

	public double getLat() {
		return y;
	}

	public double getLng() {
		return x;
	}

	public com.vividsolutions.jts.geom.Coordinate toCoordinate() {
		return new Coordinate(this);
	}

	public com.vividsolutions.jts.geom.Point toPointJTS() {
		return new GeometryFactory().createPoint(this);
	}

	public com.google.publicalerts.cap.Point toPointCAP() {
		com.google.publicalerts.cap.Point.Builder point = com.google.publicalerts.cap.Point.newBuilder();
		point.setLatitude(getLat());
		point.setLongitude(getLng());
		return point.build();
	}

	//bearing (radians)
	//distance (km)
	//http://www.movable-type.co.uk/scripts/latlong.html
	public Point travel(double bearing, double distance) {
		final double R = 6371.0; // radius of earth in km
		final double dOverR = distance / R;
		final double lat1 = Math.toRadians(getLat());
		final double lng1 = Math.toRadians(getLng());
		final double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dOverR) + Math.cos(lat1) * Math.sin(dOverR) * Math.cos(bearing));
		final double lng2 = lng1
				+ Math.atan2(Math.sin(bearing) * Math.sin(dOverR) * Math.cos(lat1), Math.cos(dOverR) - Math.sin(lat1) * Math.sin(lat2));
		return new Point(Math.toDegrees(lat2), Math.toDegrees(lng2));
	}

	// returns distance in km between two lat/lng pairs
	//http://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-latitude-longitude-points
	public double distanceBetween(Point p) {
		final double R = 6371.0; // radius of earth in km
		double dLat = Math.toRadians(getLat() - p.getLat());
		double dLng = Math.toRadians(getLng() - p.getLng());
		double d = Math.sin(dLat / 2) * Math.sin(dLat / 2) + //
				Math.cos(Math.toRadians(p.getLat())) * Math.cos(Math.toRadians(getLat())) * //
				Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(d), Math.sqrt(1 - d));
		return R * c;
	}
}
