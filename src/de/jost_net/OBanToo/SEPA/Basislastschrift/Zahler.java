package de.jost_net.OBanToo.SEPA.Basislastschrift;

import java.math.BigDecimal;
import java.util.Date;

import de.jost_net.OBanToo.SEPA.BIC;
import de.jost_net.OBanToo.SEPA.IBAN;
import de.jost_net.OBanToo.SEPA.SEPAException;

public class Zahler
{
  private String mandatid;

  private Date mandatdatum;

  private String bic;

  private String name;

  private String iban;

  private String verwendungszweck;

  private BigDecimal betrag;

  private static final BigDecimal nu = new BigDecimal("0.00");

  public String getMandatid() throws SEPAException
  {
    checkMandatID(mandatid);
    return mandatid;
  }

  public void setMandatid(String mandatid) throws SEPAException
  {
    checkMandatID(mandatid);
    this.mandatid = mandatid;
  }

  private void checkMandatID(String mandatid) throws SEPAException
  {
    if (mandatid == null || mandatid.length() == 0 || mandatid.length() > 35)
    {
      throw new SEPAException("Ungültige Mandat-ID: " + mandatid);
    }
  }

  public Date getMandatdatum() throws SEPAException
  {
    checkMandatdatum(mandatdatum);
    return mandatdatum;
  }

  public void setMandatdatum(Date mandatdatum) throws SEPAException
  {
    checkMandatdatum(mandatdatum);
    this.mandatdatum = mandatdatum;
  }

  private void checkMandatdatum(Date mandatdatum) throws SEPAException
  {
    if (mandatdatum == null || mandatdatum.after(new Date()))
    {
      throw new SEPAException("Ungültiges Mandatdatum: " + mandatdatum);
    }
  }

  public String getBic() throws SEPAException
  {
    checkBic(bic);
    return bic;
  }

  public void setBic(String bic) throws SEPAException
  {
    checkBic(bic);
    this.bic = bic;
  }

  private void checkBic(String bic) throws SEPAException
  {
    new BIC(bic);
  }

  public String getName() throws SEPAException
  {
    checkName(name);
    return name;
  }

  public void setName(String name) throws SEPAException
  {
    checkName(name);
    this.name = name;
  }

  private void checkName(String name) throws SEPAException
  {
    if (name == null || name.length() == 0 || name.length() > 70)
    {
      throw new SEPAException("Ungültiger Name: " + name);
    }
  }

  public String getIban() throws SEPAException
  {
    new IBAN(iban);
    return iban;
  }

  public void setIban(String iban) throws SEPAException
  {
    new IBAN(iban);
    this.iban = iban;
  }

  public String getVerwendungszweck() throws SEPAException
  {
    checkVerwendungszweck(verwendungszweck);
    return verwendungszweck;
  }

  public void setVerwendungszweck(String verwendungszweck) throws SEPAException
  {
    checkVerwendungszweck(verwendungszweck);
    this.verwendungszweck = verwendungszweck;
  }

  private void checkVerwendungszweck(String verwendungszweck)
      throws SEPAException
  {
    if (verwendungszweck == null || verwendungszweck.length() == 0
        || verwendungszweck.length() > 140)
    {
      throw new SEPAException("Ungültiger Verwendungszweck: "
          + verwendungszweck);
    }

  }

  public BigDecimal getBetrag() throws SEPAException
  {
    checkBetrag(betrag);
    return betrag;
  }

  public void setBetrag(BigDecimal betrag) throws SEPAException
  {
    checkBetrag(betrag);
    this.betrag = betrag;
  }

  public void checkBetrag(BigDecimal betrag) throws SEPAException
  {
    if (betrag == null || betrag.compareTo(nu) == -1
        || betrag.compareTo(nu) == 0)
    {
      throw new SEPAException("Ungültiger Betrag: " + betrag);
    }

  }

  public void add(Zahler zahler) throws SEPAException
  {
    if (verwendungszweck == null)
    {
      verwendungszweck = "";
    }
    String tmpverwendungszweck = verwendungszweck + " " + betrag.toString()
        + ", " + zahler.getVerwendungszweck() + " " + zahler.getBetrag();
    if (tmpverwendungszweck.length() > 140)
    {
      throw new SEPAException(
          "Zahlung kann nicht zusammengefügt werden. Verwendungszweck ist zu lang");
    }
    verwendungszweck = tmpverwendungszweck;
    betrag = betrag.add(zahler.getBetrag());
  }
}
