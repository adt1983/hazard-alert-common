import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.publicalerts.cap.Circle;
import com.google.publicalerts.cap.Point;
import com.hazardalert.common.CommonUtil;
import com.vividsolutions.jts.geom.Polygon;

public class CircleToPolygon {
	@Test
	@SuppressWarnings("unused")
	public void test() {
		com.google.publicalerts.cap.Circle c = Circle.newBuilder()
														.setPoint(Point.newBuilder().setLatitude(39.95).setLongitude(-75.157).build())
														.setRadius(1.0)
														.build();
		com.google.publicalerts.cap.Polygon p = CommonUtil.toPolygon(c);
		Polygon jts = CommonUtil.toPolygonJts(p);
		String s = jts.toText();
		double area = jts.getArea();
		String ch = jts.convexHull().toText();
		assertTrue(jts.isSimple());
		assertEquals(0, CommonUtil.toPolygonJts(p).getNumInteriorRing());
	}

	@Test
	@SuppressWarnings("unused")
	public void testTaiwan() {
		com.google.publicalerts.cap.Circle c = Circle.newBuilder()
														.setPoint(Point.newBuilder().setLatitude(24.49971).setLongitude(121.8358).build())
														.setRadius(10.0)
														.build();
		com.google.publicalerts.cap.Polygon p = CommonUtil.toPolygon(c);
		Polygon jts = CommonUtil.toPolygonJts(p);
		String s = jts.toText();
	}
}
