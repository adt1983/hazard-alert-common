package com.hazardalert.common;

// replacement for 'assert' since we want assertions to be thrown in production
public class Assert {
	public Assert(boolean assertion) {
		if (!assertion) {
			throw new IllegalArgumentException(); // Exception not Error so we can catch
		}
	}

	public Assert(boolean assertion, String msg) {
		if (!assertion) {
			throw new IllegalArgumentException(msg);
		}
	}
}
