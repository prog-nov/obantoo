//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.04 at 09:57:14 AM CET 
//

package de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ServiceLevelSEPA complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceLevelSEPA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.002.02}ServiceLevelSEPACode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLevelSEPA", propOrder = { "cd" })
public class ServiceLevelSEPA
{

  @XmlElement(name = "Cd", required = true)
  protected ServiceLevelSEPACode cd;

  /**
   * Gets the value of the cd property.
   * 
   * @return possible object is {@link ServiceLevelSEPACode }
   * 
   */
  public ServiceLevelSEPACode getCd()
  {
    return cd;
  }

  /**
   * Sets the value of the cd property.
   * 
   * @param value
   *          allowed object is {@link ServiceLevelSEPACode }
   * 
   */
  public void setCd(ServiceLevelSEPACode value)
  {
    this.cd = value;
  }

}