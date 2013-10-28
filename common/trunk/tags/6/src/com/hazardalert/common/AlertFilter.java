package com.hazardalert.common;

import java.util.Date;

public class AlertFilter {
	private Bounds include;

	private Bounds exclude;

	private Long minExpires;

	private Long minEffective;

	private Long limit;

	public Long getMinEffective() {
		return minEffective;
	}

	public void setMinEffective(Long minEffective) {
		this.minEffective = minEffective;
	}

	public AlertFilter() {
		setMinExpires(new Date().getTime());
	}

	public Long getMinExpires() {
		return minExpires;
	}

	public AlertFilter setMinExpires(Long minExpires) {
		this.minExpires = minExpires;
		return this;
	}

	public Long getLimit() {
		return limit;
	}

	public AlertFilter setLimit(Long limit) {
		this.limit = limit;
		return this;
	}

	public Bounds getInclude() {
		return include;
	}

	public AlertFilter setInclude(Bounds include) {
		this.include = include;
		return this;
	}

	public Bounds getExclude() {
		return exclude;
	}

	public AlertFilter setExclude(Bounds exclude) {
		this.exclude = exclude;
		return this;
	}

	/*
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (null != include) {
			sb.append(include.toString());
		}
		if (null != exclude) {
			sb.append(exclude.toString());
		}
		if (null != minEffective) {
			sb.append(minEffective);
		}
		if (null != minExpires) {
			sb.append(minExpires);
		}
		if (null != limit) {
			sb.append(limit.toString());
		}
		return sb.toString();
	}
}
