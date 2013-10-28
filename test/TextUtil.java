import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hazardalert.common.CommonUtil;

public class TextUtil {
	@Test
	public void test() {
		String[][] shouldEqual = {
				{ "1", "1" }, //
				{ " www", " http://www" }, { " HTTP://WWW", " http://www" }, { " http://www", " http://www" },
				{ "blah www.xyz.gov/page blah", "blah http://www.xyz.gov/page blah" } };
		String[][] shouldNotEqual = { { "1", "2" }, { "http://www", "http://http://www" } };
		for (String[] s : shouldEqual) {
			String xform = CommonUtil.lowercaseLinks(s[0]);
			assertTrue(xform + " != " + s[1], xform.equals(s[1]));
		}
		for (String[] s : shouldNotEqual) {
			String xform = CommonUtil.lowercaseLinks(s[0]);
			assertFalse(xform + " == " + s[1], xform.equals(s[1]));
		}
	}
}
