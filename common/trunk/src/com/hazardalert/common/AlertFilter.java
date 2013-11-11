package com.hazardalert.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.publicalerts.cap.Alert;
import com.google.publicalerts.cap.Info;
import com.google.publicalerts.cap.Info.Certainty;
import com.google.publicalerts.cap.Info.Severity;
import com.google.publicalerts.cap.Info.Urgency;

public class AlertFilter implements Serializable {
	private Bounds include;

	private Bounds exclude;

	private Long minExpires;

	private Long minEffective;

	private List<Long> severity;

	private List<Long> certainty;

	private List<Long> urgency;

	private List<Long> status;

	private List<Long> senders;

	private Long limit;

	public AlertFilter() {
		setMinExpires(new Date().getTime());
		addStatus(Alert.Status.ACTUAL); // Hide Exercise/System/Test/Draft by default
	}

	public static AlertFilter defaultClientFilter() {
		AlertFilter af = new AlertFilter();
		af.addUrgency(Urgency.FUTURE);
		af.addUrgency(Urgency.EXPECTED);
		af.addUrgency(Urgency.IMMEDIATE);
		//
		af.addSeverity(Severity.MINOR);
		af.addSeverity(Severity.MODERATE);
		af.addSeverity(Severity.SEVERE);
		af.addSeverity(Severity.EXTREME);
		//
		af.addCertainty(Certainty.UNLIKELY);
		af.addCertainty(Certainty.POSSIBLE);
		af.addCertainty(Certainty.LIKELY);
		af.addCertainty(Certainty.VERY_LIKELY);
		af.addCertainty(Certainty.OBSERVED);
		//
		af.setMinEffective(new Date().getTime() - (3 * CommonUtil.ONE_DAY_MS)); // effective in the last 3 days
		return af;
	}

	public List<Long> getCertainty() {
		return certainty;
	}

	public AlertFilter setCertainty(List<Long> certainty) {
		this.certainty = certainty;
		return this;
	}

	public AlertFilter addCertainty(int value) {
		return addCertainty(Info.Certainty.valueOf(value));
	}

	public AlertFilter addCertainty(Info.Certainty c) {
		if (null == certainty) {
			certainty = new ArrayList<Long>();
		}
		addTo(certainty, c.getNumber());
		return this;
	}

	private void addTo(List<Long> list, int value) {
		addTo(list, Long.valueOf(value));
	}

	private void addTo(List<Long> list, Long value) {
		if (!list.contains(value)) {
			list.add(value);
		}
	}

	/*
	 * public boolean contains(Long a) {
		for (Long b : this) {
			if (0 == a.compareTo(b)) {
				return true;
			}
		}
		return false;
	}
	 */
	public List<Long> getSenders() {
		return senders;
	}

	public AlertFilter setSenders(List<Long> senders) {
		this.senders = senders;
		return this;
	}

	public AlertFilter addSender(Long sender) {
		if (null == senders) {
			senders = new ArrayList<Long>();
		}
		addTo(senders, sender);
		return this;
	}

	public List<Long> getUrgency() {
		return urgency;
	}

	public boolean hasSender(Long id) {
		if (null == this.senders) {
			return false;
		}
		return this.senders.contains(id);
	}

	public boolean hasStatus(Alert.Status status) {
		if (null == this.status) {
			return false;
		}
		return this.status.contains(Long.valueOf(status.ordinal()));
	}

	public AlertFilter setUrgency(List<Long> urgency) {
		this.urgency = urgency;
		return this;
	}

	public AlertFilter addUrgency(int value) {
		return addUrgency(Info.Urgency.valueOf(value));
	}

	public AlertFilter addUrgency(Info.Urgency u) {
		if (null == urgency) {
			urgency = new ArrayList<Long>();
		}
		addTo(urgency, u.getNumber());
		return this;
	}

	public List<Long> getStatus() {
		return status;
	}

	public AlertFilter setStatus(List<Long> status) {
		this.status = status;
		return this;
	}

	public AlertFilter addStatus(Alert.Status s) {
		if (null == status) {
			status = new ArrayList<Long>();
		}
		addTo(status, s.getNumber());
		return this;
	}

	public List<Long> getSeverity() {
		return severity;
	}

	public AlertFilter setSeverity(List<Long> severity) {
		this.severity = severity;
		return this;
	}

	public AlertFilter addSeverity(int value) {
		return addSeverity(Info.Severity.valueOf(value));
	}

	public AlertFilter addSeverity(Info.Severity s) {
		if (null == severity) {
			severity = new ArrayList<Long>();
		}
		addTo(severity, s.getNumber());
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

	@Override
	public boolean equals(Object obj) {
		AlertFilter rhs = (AlertFilter) obj;
		boolean equals = true;
		equals = fieldEquals(certainty, rhs.certainty) ? equals : false;
		equals = fieldEquals(exclude, rhs.exclude) ? equals : false;
		equals = fieldEquals(include, rhs.include) ? equals : false;
		equals = fieldEquals(limit, rhs.limit) ? equals : false;
		equals = fieldEquals(minEffective, rhs.minEffective) ? equals : false;
		equals = fieldEquals(minExpires, rhs.minExpires) ? equals : false;
		equals = fieldEquals(senders, rhs.senders) ? equals : false;
		equals = fieldEquals(severity, rhs.severity) ? equals : false;
		equals = fieldEquals(status, rhs.status) ? equals : false;
		equals = fieldEquals(urgency, rhs.urgency) ? equals : false;
		return equals;
	}

	private <T> boolean fieldEquals(T lhs, T rhs) {
		if (null == lhs) {
			return null == rhs;
		}
		return null == rhs ? false : lhs.equals(rhs);
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(certainty);
		out.writeObject(exclude);
		out.writeObject(include);
		out.writeObject(limit);
		out.writeObject(minEffective);
		out.writeObject(minExpires);
		out.writeObject(senders);
		out.writeObject(severity);
		out.writeObject(status);
		out.writeObject(urgency);
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		certainty = (List<Long>) in.readObject();
		exclude = (Bounds) in.readObject();
		include = (Bounds) in.readObject();
		limit = (Long) in.readObject();
		minEffective = (Long) in.readObject();
		minExpires = (Long) in.readObject();
		senders = (List<Long>) in.readObject();
		severity = (List<Long>) in.readObject();
		status = (List<Long>) in.readObject();
		urgency = (List<Long>) in.readObject();
	}
}
