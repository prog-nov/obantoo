/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA;

public class IBANRet
{
  private IBANCode code = null;

  private String iban = null;

  private String bic = null;

  public IBANRet(IBANCode code, String iban, String bic)
  {
    this.code = code;
    this.iban = iban;
    this.bic = bic;
  }

  public IBANRet(IBANCode code)
  {
    this.code = code;
  }

  public IBANCode getCode()
  {
    return code;
  }

  public void setCode(IBANCode code)
  {
    this.code = code;
  }

  public String getIban()
  {
    return iban;
  }

  public String getBic()
  {
    return bic;
  }
}
