package com.hazardalert.common;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

public class Point {
	public static final Point BB_NE = new Point(-90.0, -180.0);

	public static final Point BB_SW = new Point(90.0, 180.0);

	private double lat;

	private double lng;

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
		this(p.lat, p.lng);
	}

	public void setLat(double _lat) {
		if (-90.0 > _lat || _lat > 90.0) {
			throw new RuntimeException("lat: " + _lat);
		}
		lat = _lat;
	}

	public void setLng(double _lng) {
		if (-180.0 > _lng || _lng > 180.0) {
			throw new RuntimeException("lng: " + _lng);
		}
		lng = _lng;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public com.vividsolutions.jts.geom.Coordinate toCoordinate() {
		return new Coordinate(lng, lat);
	}

	public com.vividsolutions.jts.geom.Point toPointJTS() {
		return new GeometryFactory().createPoint(toCoordinate());
	}

	public com.google.publicalerts.cap.Point toPointCAP() {
		return CommonUtil.jts_to_cap(toCoordinate());
	}
}
