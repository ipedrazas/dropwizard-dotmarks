package net.dotmarks.api.om;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

public class DotmarkTest {

	@Test
	public void testDotmarkConstructor() {
		String sUUID = UUID.randomUUID().toString();
		String[] tags = {"test", "junit"};
		Dotmark d = new Dotmark(
					"http://localhost",
					"title",
					new Date(),
					new Date(),
					sUUID,
					Arrays.asList(tags),
					false,
					"juint"
				);
	
		assertNotNull(UUID.fromString(d.getUser().toString()));
	}

}
