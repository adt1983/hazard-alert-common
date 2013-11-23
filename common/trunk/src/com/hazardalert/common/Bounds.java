package com.hazardalert.common;

import java.io.IOException;
import java.io.Serializable;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;

// TODO convert storage to Point
@SuppressWarnings("serial")
public class Bounds implements Serializable {
	private Double ne_lat;

	private Double ne_lng;

	private Double sw_lat;

	private Double sw_lng;

	public Bounds() {}

	public double getNe_lat() {
		return ne_lat;
	}

	public void setNe_lat(double ne_lat) {
		new Assert(isLat(ne_lat));
		this.ne_lat = ne_lat;
	}

	public double getNe_lng() {
		return ne_lng;
	}

	public void setNe_lng(double ne_lng) {
		new Assert(isLng(ne_lng));
		this.ne_lng = ne_lng;
	}

	public double getSw_lat() {
		return sw_lat;
	}

	public void setSw_lat(double sw_lat) {
		new Assert(isLat(sw_lat));
		this.sw_lat = sw_lat;
	}

	public double getSw_lng() {
		return sw_lng;
	}

	public void setSw_lng(double sw_lng) {
		new Assert(isLng(sw_lng));
		this.sw_lng = sw_lng;
	}

	/*
	 * Helper Methods
	 */
	public Bounds(double ne_lat, double ne_lng, double sw_lat, double sw_lng) {
		this.setNe_lat(ne_lat);
		this.setNe_lng(ne_lng);
		this.setSw_lat(sw_lat);
		this.setSw_lng(sw_lng);
	}

	public Bounds(Envelope env) {
		this(env.getMaxY(), env.getMaxX(), env.getMinY(), env.getMinX());
	}

	public Bounds(Point center, double distanceKM) {
		Point ne = center.travel(Math.toRadians(45.0), distanceKM);
		Point sw = center.travel(Math.toRadians(225.0), distanceKM);
		init(ne, sw);
		new Assert(contains(center));
	}

	public Bounds(Coordinate northeast, Coordinate southwest) {
		init(new Point(northeast), new Point(southwest));
	}

	public Bounds(Point northeast, Point southwest) {
		init(northeast, southwest);
	}

	private void init(Point ne, Point sw) {
		this.setNe_lng(ne.getLng());
		this.setNe_lat(ne.getLat());
		this.setSw_lng(sw.getLng());
		this.setSw_lat(sw.getLat());
		new Assert(ne.getLat() > sw.getLat());
	}

	public com.vividsolutions.jts.geom.Envelope toEnvelope() {
		Envelope env = new Envelope();
		env.expandToInclude(getNe_lng(), getNe_lat());
		env.expandToInclude(getSw_lng(), getSw_lat());
		return env;
	}

	public com.vividsolutions.jts.geom.Polygon toPolygon() {
		Coordinate mbr[] = new Coordinate[5];
		mbr[0] = new Coordinate(ne_lng, ne_lat);
		mbr[1] = new Coordinate(sw_lng, ne_lat);
		mbr[2] = new Coordinate(sw_lng, sw_lat);
		mbr[3] = new Coordinate(ne_lng, sw_lat);
		mbr[4] = new Coordinate(ne_lng, ne_lat);
		GeometryFactory factory = new GeometryFactory();
		LinearRing shell = factory.createLinearRing(mbr);
		return new com.vividsolutions.jts.geom.Polygon(shell, null, factory);
	}

	@Override
	public String toString() {
		return toPolygon().toText();
	}

	public double _getMinX() {
		return 0.0;
	}

	private boolean isLat(double lat) {
		return -90.0 <= lat && lat <= 90.0;
	}

	private boolean isLng(double lng) {
		return -180.0 <= lng && lng <= 180.0;
	}

	@Override
	public boolean equals(Object obj) {
		Bounds rhs = (Bounds) obj;
		boolean equals = true;
		equals = ne_lng.equals(rhs.ne_lng) ? equals : false;
		equals = ne_lat.equals(rhs.ne_lat) ? equals : false;
		equals = sw_lng.equals(rhs.sw_lng) ? equals : false;
		equals = sw_lat.equals(rhs.sw_lat) ? equals : false;
		return equals;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(ne_lng);
		out.writeObject(ne_lat);
		out.writeObject(sw_lng);
		out.writeObject(sw_lat);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		ne_lng = (Double) in.readObject();
		ne_lat = (Double) in.readObject();
		sw_lng = (Double) in.readObject();
		sw_lat = (Double) in.readObject();
	}

	public boolean spansAntiMeridian() {
		return sw_lng > ne_lng;
	}

	public Bounds[] splitOnAntiMeridian() {
		new Assert(spansAntiMeridian());
		Bounds split[] = new Bounds[2];
		split[0] = new Bounds(ne_lat, ne_lng, sw_lat, -180.0); // East of 180 Meridian
		split[1] = new Bounds(ne_lat, 180.0, sw_lat, sw_lng); // West of 180 Meridian
		new Assert(!split[0].spansAntiMeridian());
		new Assert(!split[1].spansAntiMeridian());
		return split;
	}

	public boolean contains(Point p) {
		if (spansAntiMeridian()) {
			Bounds[] split = splitOnAntiMeridian();
			return split[0].contains(p) || split[1].contains(p);
		}
		return (sw_lat < p.getLat() && p.getLat() < ne_lat) && (sw_lng < p.getLng() && p.getLng() < ne_lng);
	}
}
