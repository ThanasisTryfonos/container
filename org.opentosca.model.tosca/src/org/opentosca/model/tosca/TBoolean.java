//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2013.07.10 at 12:45:26 PM CEST
//
// TOSCA version: TOSCA-v1.0-cs02.xsd
//

package org.opentosca.model.tosca;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for tBoolean.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="tBoolean">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="yes"/>
 *     &lt;enumeration value="no"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tBoolean")
@XmlEnum
public enum TBoolean {
	
	@XmlEnumValue("yes")
	YES("yes"), @XmlEnumValue("no")
	NO("no");
	
	private final String value;
	
	
	TBoolean(String v) {
		this.value = v;
	}
	
	public String value() {
		return this.value;
	}
	
	public static TBoolean fromValue(String v) {
		for (TBoolean c : TBoolean.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
	
}
