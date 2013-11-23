import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import com.hazardalert.common.Bounds;
import com.hazardalert.common.Point;

public class BoundsTest {
	@Test
	public void test() {
		Bounds tests[] = new Bounds[] { new Bounds(1.0, 2.0, -1.0, -2.0) };
		for (int i = 0; i < tests.length; i++) {
			try {
				Bounds clone = SerializationUtils.clone(tests[i]);
				assertTrue(tests[i].equals(clone));
			}
			catch (Exception e) {
				fail();
			}
		}
	}

	@Test
	public void testAustin() {
		Bounds b = new Bounds(new Point(30.340778, -97.691460), 0.25);
	}
}
