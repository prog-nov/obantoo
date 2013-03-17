/**
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA.Land;

public class SEPALand
{
  private String kennzeichen;

  private String bezeichnung;

  private Integer bankidentfierlength;

  private Integer accountlength;

  private String bankidentifiersample;

  private String accountsample;

  private String ibansample;

  public SEPALand()
  {
  }

  public String getKennzeichen()
  {
    return kennzeichen;
  }

  public void setKennzeichen(String kennzeichen)
  {
    this.kennzeichen = kennzeichen;
  }

  public String getBezeichnung()
  {
    return bezeichnung;
  }

  public void setBezeichnung(String bezeichnung)
  {
    this.bezeichnung = bezeichnung;
  }

  public void setBankIdentifierLength(Integer bankidentifierlength)
  {
    this.bankidentfierlength = bankidentifierlength;
  }

  public Integer getBankIdentifierLength()
  {
    return this.bankidentfierlength;
  }

  public void setAccountLength(Integer accountlength)
  {
    this.accountlength = accountlength;
  }

  public Integer getAccountLength()
  {
    return this.accountlength;
  }

  public void setBankIdentifierSample(String bankidentifiersample)
  {
    this.bankidentifiersample = bankidentifiersample;
  }

  public String getBankIdentifierSample()
  {
    return this.bankidentifiersample;
  }

  public void setAccountSample(String accountsample)
  {
    this.accountsample = accountsample;
  }

  public String getAccountSample()
  {
    return this.accountsample;
  }

  public void setIBANSample(String ibansample)
  {
    this.ibansample = ibansample;
  }

  public String getIBANSample()
  {
    return this.ibansample;
  }

}
