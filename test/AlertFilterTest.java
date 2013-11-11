import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import com.hazardalert.common.AlertFilter;
import com.hazardalert.common.Bounds;

public class AlertFilterTest {
	@Test
	public void test() {
		Long limits[] = new Long[] { Long.valueOf(0), Long.valueOf(10), null };
		Bounds bounds[] = new Bounds[] { new Bounds(1.0, 2.0, -3.0, -4.0), null, new Bounds(20.0, 22.0, -30.0, -50.0) };
		List<List<Long>> longs = new ArrayList<List<Long>>();
		for (int i = 0; i < 3; i++) {
			List<Long> x = new ArrayList<Long>();
			x.add(Long.valueOf(i * 3));
			x.add(Long.valueOf(i * 4));
			longs.add(x);
		}
		AlertFilter original = new AlertFilter();
		for (int limit = 0; limit < limits.length; limit++) {
			original.setLimit(limits[limit]);
			for (int include = 0; include < bounds.length; include++) {
				original.setInclude(bounds[include]);
				for (int exclude = 0; exclude < bounds.length; exclude++) {
					original.setExclude(bounds[exclude]);
					for (int certainty = 0; certainty < longs.size(); certainty++) {
						original.setCertainty(longs.get(certainty));
						doSerializationTest(original);
					}
				}
			}
		}
	}

	public void doSerializationTest(AlertFilter original) {
		try {
			AlertFilter clone = SerializationUtils.clone(original);
			assertTrue(original.equals(clone));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
