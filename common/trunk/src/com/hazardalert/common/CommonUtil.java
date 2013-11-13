package com.hazardalert.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

//import com.google.publicalerts.cap.Point;
public abstract class CommonUtil {
	/**
	 * Common time values
	 */
	public static final long ONE_SECOND_MS = 1000;

	public static final long ONE_MINUTE_MS = 60 * ONE_SECOND_MS;

	public static final long FIFTEEN_MINUTES_MS = 15 * ONE_MINUTE_MS;

	public static final long ONE_HOUR_MS = 60 * ONE_MINUTE_MS;

	public static final long ONE_DAY_MS = 24 * ONE_HOUR_MS;

	public static final long ONE_WEEK_MS = 7 * ONE_DAY_MS;

	public final static com.google.publicalerts.cap.Polygon toPolygon(com.google.publicalerts.cap.Circle circle) {
		Point center = new Point(circle.getPoint());
		Point perimeter[] = new Point[17];
		for (int i = 0; i < perimeter.length - 1; i++) {
			double bearing = Math.toRadians(i * 360.0 / (perimeter.length - 1));
			perimeter[i] = center.travel(bearing, circle.getRadius());
		}
		perimeter[perimeter.length - 1] = perimeter[0];
		GeometryFactory factory = new GeometryFactory();
		LinearRing shell = factory.createLinearRing(perimeter);
		return toPolygonCap(factory.createPolygon(shell));
	}

	public final static com.vividsolutions.jts.geom.Polygon toPolygonJts(com.google.publicalerts.cap.Polygon capPolygon) {
		final List<com.google.publicalerts.cap.Point> points = capPolygon.getPointList();
		Coordinate coordinates[] = new Coordinate[points.size()];
		for (int i = 0; i < coordinates.length; i++) {
			coordinates[i] = new Point(points.get(i));
		}
		GeometryFactory factory = new GeometryFactory();
		LinearRing shell = factory.createLinearRing(coordinates);
		//return factory.createPolygon(shell);
		return new com.vividsolutions.jts.geom.Polygon(shell, null, factory);
	}

	public static com.google.publicalerts.cap.Polygon toPolygonCap(com.vividsolutions.jts.geom.LineString ls) {
		return toPolygonCap(new GeometryFactory().createLinearRing(ls.getCoordinates()));
	}

	public static com.google.publicalerts.cap.Polygon toPolygonCap(com.vividsolutions.jts.geom.LinearRing lr) {
		com.google.publicalerts.cap.Polygon.Builder builder = com.google.publicalerts.cap.Polygon.newBuilder();
		final Coordinate coordinates[] = lr.getCoordinates();
		for (int i = 0; i < coordinates.length; i++) {
			builder.addPoint(new Point(coordinates[i]).toPointCAP());
		}
		return builder.build();
	}

	public final static com.google.publicalerts.cap.Polygon toPolygonCap(com.vividsolutions.jts.geom.Polygon jtsPolygon) {
		if (0 != jtsPolygon.getNumInteriorRing()) {
			throw new RuntimeException(jtsPolygon.toText());
		}
		return toPolygonCap(jtsPolygon.getExteriorRing());
	}

	public final static com.vividsolutions.jts.geom.Polygon[] cap_to_jts(List<com.google.publicalerts.cap.Polygon> input) {
		Polygon polygons[] = new Polygon[input.size()];
		for (int i = 0; i < polygons.length; i++) {
			polygons[i] = toPolygonJts(input.get(i));
		}
		return polygons;
	}

	public final static com.vividsolutions.jts.geom.GeometryCollection cap_to_jts(com.google.publicalerts.cap.Alert cap) {
		GeometryFactory factory = new GeometryFactory();
		//List<com.vividsolutions.jts.geom.Geometry> circles = new ArrayList<com.vividsolutions.jts.geom.Geometry>();
		List<com.google.publicalerts.cap.Polygon> capPolygons = new LinkedList<com.google.publicalerts.cap.Polygon>();
		for (com.google.publicalerts.cap.Info info : cap.getInfoList()) {
			for (com.google.publicalerts.cap.Area area : info.getAreaList()) {
				for (com.google.publicalerts.cap.Polygon polygon : area.getPolygonList()) {
					capPolygons.add(polygon);
				}
			}
		}
		Polygon jtsPolygons[] = cap_to_jts(capPolygons);
		//return factory.createMultiPolygon(jtsPolygons);
		return factory.createGeometryCollection(jtsPolygons);
	}

	public final static com.vividsolutions.jts.geom.Envelope jts_getEnvelope(com.google.publicalerts.cap.Alert cap) {
		return cap_to_jts(cap).getEnvelopeInternal();
	}

	public final static com.vividsolutions.jts.geom.Point jts_getCentroid(com.google.publicalerts.cap.Alert cap) {
		GeometryCollection gc = cap_to_jts(cap);
		com.vividsolutions.jts.geom.Point centroid = gc.getCentroid();
		return centroid;
	}

	// distance (km)
	public final static Envelope getBoundingBox(Coordinate center, double distance) {
		Point start = new Point(center);
		Coordinate ne = start.travel(Math.toRadians(45.0), distance);
		Coordinate sw = start.travel(Math.toRadians(225.0), distance);
		return new Envelope(ne, sw);
	}

	public final static Geometry createBoundingBox(Coordinate northeast, Coordinate southwest) {
		Coordinate mbr[] = new Coordinate[5];
		mbr[0] = new Coordinate(northeast.x, northeast.y);
		mbr[1] = new Coordinate(northeast.x, southwest.y);
		mbr[2] = new Coordinate(southwest.x, southwest.y);
		mbr[3] = new Coordinate(southwest.x, northeast.y);
		mbr[4] = new Coordinate(northeast.x, northeast.y);
		GeometryFactory factory = new GeometryFactory();
		LinearRing shell = factory.createLinearRing(mbr);
		return new Polygon(shell, null, factory);
	}

	public static List<String> toString(List<Long> longs) {
		List<String> strings = new ArrayList<String>();
		for (Long l : longs) {
			strings.add(l.toString());
		}
		return strings;
	}

	public static final String lowercaseLinks(String s) {
		final String prefix = " http://www";
		s = s.replaceAll("[\\s]HTTP://WWW|[\\s](?i)www", prefix);
		char[] rawString = s.toCharArray();
		for (int i = 0; -1 != (i = s.indexOf(prefix, i));) {
			i++; // skip initial space
			for (; i < s.length() && !Character.isWhitespace(rawString[i]); i++) {
				rawString[i] = Character.toLowerCase(rawString[i]);
			}
		}
		return String.copyValueOf(rawString);
	}

	public static <T> List<T> toNonNull(List<T> results) {
		if (null == results) {
			results = new LinkedList<T>();
		}
		return results;
	}
}
