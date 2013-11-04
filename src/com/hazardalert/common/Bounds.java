package com.hazardalert.common;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;

public class Bounds {
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
		return -90.0 < lat && lat < 90.0;
	}

	private boolean isLng(double lng) {
		return -180.0 < lng && lng < 180.0;
	}
}
