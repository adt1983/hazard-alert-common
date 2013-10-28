package com.hazardalert.common;

public class AlertTransport {
	public String payload; // Base 64 encoded XML from javax.xml.bind.DatatypeConverter.printBase64Binary

	public String getPayload() {
		return payload;
	}

	public AlertTransport setPayload(String payload) {
		this.payload = payload;
		return this;
	}
}
