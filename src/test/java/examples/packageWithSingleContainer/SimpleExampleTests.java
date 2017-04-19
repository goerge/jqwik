package examples.packageWithSingleContainer;

import net.jqwik.api.properties.*;

import static org.assertj.core.api.Assertions.*;

public class SimpleExampleTests {

	@Property
	void succeeding() {
	}

	@Property
	static void staticExample() {
	}

	@Property
	void failing() {
		fail("failing");
	}
}
