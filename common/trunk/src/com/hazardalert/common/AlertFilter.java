package com.hazardalert.common;

import java.util.Date;

import com.google.publicalerts.cap.Alert;
import com.google.publicalerts.cap.Info;

public class AlertFilter {
	private Bounds include;

	private Bounds exclude;

	private Long minExpires;

	private Long minEffective;

	private Longs severity;

	private Longs certainty;

	private Longs urgency;

	private Longs status;

	private Long limit;

	public AlertFilter() {
		setMinExpires(new Date().getTime());
		addStatus(Alert.Status.ACTUAL); // Hide Exercise/System/Test/Draft by default
	}

	public Longs getCertainty() {
		return certainty;
	}

	public AlertFilter setCertainty(Longs certainty) {
		this.certainty = certainty;
		return this;
	}

	public AlertFilter addCertainty(Info.Certainty c) {
		if (null == certainty) {
			certainty = new Longs();
		}
		certainty.add(c.getNumber());
		return this;
	}

	public Longs getUrgency() {
		return urgency;
	}

	public AlertFilter setUrgency(Longs urgency) {
		this.urgency = urgency;
		return this;
	}

	public AlertFilter addUrgency(Info.Urgency u) {
		if (null == urgency) {
			urgency = new Longs();
		}
		urgency.add(u.getNumber());
		return this;
	}

	public Longs getStatus() {
		return status;
	}

	public AlertFilter setStatus(Longs status) {
		this.status = status;
		return this;
	}

	public AlertFilter addStatus(Alert.Status s) {
		if (null == status) {
			status = new Longs();
		}
		status.add(s.getNumber());
		return this;
	}

	public Longs getSeverity() {
		return severity;
	}

	public AlertFilter setSeverity(Longs severity) {
		this.severity = severity;
		return this;
	}

	public AlertFilter addSeverity(Info.Severity s) {
		if (null == severity) {
			severity = new Longs();
		}
		severity.add(s.getNumber());
		return this;
	}

	public Long getMinEffective() {
		return minEffective;
	}

	public AlertFilter setMinEffective(Long minEffective) {
		this.minEffective = minEffective;
		return this;
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
