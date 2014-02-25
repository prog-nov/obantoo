package de.jost_net.OBanToo.SEPA.Ueberweisung;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

import de.jost_net.OBanToo.SEPA.BIC;
import de.jost_net.OBanToo.SEPA.IBAN;
import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.StringLatin.Zeichen;

public class Empfaenger
{

  private String bic;

  private String name;

  private String nameorig;

  private String iban;

  private String verwendungszweck;

  private String verwendungszweckorig;

  private BigDecimal betrag;

  private String referenz;

  private static final BigDecimal nu = new BigDecimal("0.00");

  /**
   * BIC zurückgeben
   */
  public String getBic() throws SEPAException
  {
    checkBic(bic);
    return bic;
  }

  /**
   * BIC setzen. Länge 8 oder 11 Stellen
   */
  public void setBic(String bic) throws SEPAException
  {
    checkBic(bic);
    this.bic = bic;
  }

  private void checkBic(String bic) throws SEPAException
  {
    new BIC(bic);
  }

  /**
   * Name des Zahlungspflichtigen zurückgeben
   * 
   * @return Name des Zahlungspflichtigen
   */
  public String getName() throws SEPAException
  {
    checkName(name);
    return name;
  }

  public String getNameOrig() throws SEPAException
  {
    checkName(nameorig);
    return nameorig;
  }

  /**
   * Name des Zahlungspflichtigen setzen. Länge max. 70 Stellen.
   */
  public void setName(String name) throws SEPAException
  {
    String tmpName = Zeichen.convert(name);
    checkName(tmpName);
    this.name = tmpName;
    this.nameorig = name;
  }

  private void checkName(String name) throws SEPAException
  {
    if (name == null || name.length() == 0 || name.length() > 70)
    {
      throw new SEPAException("Ungültiger Name: " + name);
    }
  }

  /**
   * BIC zurückgeben.
   */
  public String getIban() throws SEPAException
  {
    new IBAN(iban);
    return iban;
  }

  /**
   * BIC setzen. Länge abhängig vom Land.
   */
  public void setIban(String iban) throws SEPAException
  {
    new IBAN(iban);
    this.iban = iban;
  }

  /**
   * Unstrukturierten Verwendungszweck zurückgeben.
   */
  public String getVerwendungszweck() throws SEPAException
  {
    checkVerwendungszweck(verwendungszweck);
    return verwendungszweck;
  }

  public String getVerwendungszweckOrig() throws SEPAException
  {
    checkVerwendungszweck(verwendungszweckorig);
    return verwendungszweckorig;
  }

  /**
   * Unstrukturierten Verwendungszweck setzen. Länge max. 70 Stellen.
   */
  public void setVerwendungszweck(String verwendungszweck) throws SEPAException
  {
    String tmpVerwendungszweck = Zeichen.convert(verwendungszweck);
    checkVerwendungszweck(tmpVerwendungszweck);
    this.verwendungszweck = tmpVerwendungszweck;
    this.verwendungszweckorig = verwendungszweck;
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

  /**
   * Betrag zurückgeben.
   */
  public BigDecimal getBetrag() throws SEPAException
  {
    checkBetrag(betrag);
    return betrag;
  }

  /**
   * Betrag setzen. Wert muss > 0
   */
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

  public void checkFaelligkeit(Date faelligkeit) throws SEPAException
  {
    if (faelligkeit == null)
    {
      throw new SEPAException("Fälligkeit ist null");
    }
  }

  public String getReferenz()
  {
    if (referenz == null)
    {
      return "NOTPROVIDED";
    }
    return referenz;
  }

  public void setReferenz(String referenz)
  {
    this.referenz = referenz;
  }

  @Override
  public String toString()
  {
    String message = "";
    try
    {
      message = MessageFormat
          .format(
              "Empfänger: Name={0}, IBAN={1}, BIC={2}, Verwendungszweck={3}, Betrag={4}, ",
              getName(), getIban(), getBic(), getVerwendungszweck(),
              getBetrag());
    }
    catch (SEPAException e)
    {
      message = e.getMessage();
    }
    return message;
  }
}
