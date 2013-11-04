import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hazardalert.common.Point;
import com.vividsolutions.jts.geom.Coordinate;

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
}
