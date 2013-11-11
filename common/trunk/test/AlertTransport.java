import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

public class AlertTransport {
	@Test
	public void test() throws UnsupportedEncodingException {
		String input = "http://www.weather.gov/";
		String enc = URLEncoder.encode(input, "UTF-8");
		String dec = URLDecoder.decode(enc, "UTF-8");
	}
}
