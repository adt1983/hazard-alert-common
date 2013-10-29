package com.hazardalert.common;

import java.util.ArrayList;

public class Longs extends ArrayList<Long> {
	public boolean contains(int a) {
		return contains(Long.valueOf(a));
	}

	public boolean contains(Long a) {
		for (Long b : this) {
			if (0 == a.compareTo(b)) {
				return true;
			}
		}
		return false;
	}

	public boolean add(int a) {
		return add(Long.valueOf(a));
	}

	@Override
	public boolean add(Long a) {
		if (contains(a)) {
			return false;
		}
		return super.add(a);
	}

	public boolean removeLong(int a) {
		return removeLong(Long.valueOf(a));
	}

	public boolean removeLong(Long a) {
		if (!contains(a)) {
			return false;
		}
		return super.remove(a);
	}
}
