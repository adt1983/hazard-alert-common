import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.hazardalert.common.Bounds;
import com.hazardalert.common.CommonUtil;
import com.hazardalert.common.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

// CAP stores coordinates as [lat, lng]. WKT stores coordinates as [lng, lat]
public class PointTest {
	@Test
	public void test() {
		final double EPSILON = 0.000001;
		Point p = new Point();
		p.setLat(1.0);
		p.setLng(2.0);
		Coordinate jtsC = p.toCoordinate();
		assertEquals(p.getLat(), jtsC.y, EPSILON);
		assertEquals(p.getLng(), jtsC.x, EPSILON);
		com.vividsolutions.jts.geom.Point jtsP = p.toPointJTS();
		com.google.publicalerts.cap.Point capP = p.toPointCAP();
		Point points[] = new Point[] { p, new Point(jtsC), new Point(jtsP), new Point(capP) };
		for (int i = 0; i < points.length; i++) {
			assertEquals(p.getLat(), points[i].getLat(), EPSILON);
			assertEquals(p.getLng(), points[i].getLng(), EPSILON);
		}
	}

	@Test
	public void testCreateBB() {
		Point center = new Point(39.945, -75.149);
		Envelope e = CommonUtil.getBoundingBox(center, 25.0);
		Bounds b = new Bounds(e);
		Point ne = new Point(b.getNe_lat(), b.getNe_lng());
		Point sw = new Point(b.getSw_lat(), b.getSw_lng());
		assertEquals(50.0, ne.distanceBetween(sw), 1.0);
		int x = 0;
	}

	@Test
	public void testFailCreateBB() {
		Point ne = new Point(40.558951424103554, -74.98918476299168);
		Point sw = new Point(39.31831855405722, -75.30712514960216);
		double d = ne.distanceBetween(sw);
		assertFalse(49.0 < d && d < 51.0);
	}
}
