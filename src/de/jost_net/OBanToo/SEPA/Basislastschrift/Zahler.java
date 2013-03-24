package de.jost_net.OBanToo.SEPA.Basislastschrift;

import java.math.BigDecimal;
import java.util.Date;

import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.SequenceType1Code;

public class Zahler
{
  private String mandatid;

  private Date mandatdatum;

  private String bic;

  private String name;

  private String iban;

  private String verwendungszweck;

  private BigDecimal betrag;

  private SequenceType1Code sequencecode;

  // TODO plausis einbauen
  public String getMandatid()
  {
    return mandatid;
  }

  public void setMandatid(String mandatid)
  {
    this.mandatid = mandatid;
  }

  public Date getMandatdatum()
  {
    return mandatdatum;
  }

  public void setMandatdatum(Date mandatdatum)
  {
    this.mandatdatum = mandatdatum;
  }

  public String getBic()
  {
    return bic;
  }

  public void setBic(String bic)
  {
    this.bic = bic;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getIban()
  {
    return iban;
  }

  public void setIban(String iban)
  {
    this.iban = iban;
  }

  public String getVerwendungszweck()
  {
    return verwendungszweck;
  }

  public void setVerwendungszweck(String verwendungszweck)
  {
    this.verwendungszweck = verwendungszweck;
  }

  public BigDecimal getBetrag()
  {
    return betrag;
  }

  public void setBetrag(BigDecimal betrag)
  {
    this.betrag = betrag;
  }

  public SequenceType1Code getSequenceType1Code() throws SEPAException
  {
    if (sequencecode == null)
    {
      throw new SEPAException("SequenceType1Code ist nicht gefüllt");
    }
    return sequencecode;
  }

  /**
   * Kann die Werte SequendeType1Code.FRST (erste Abbuchung), .RCUR
   * (Wiederholung), .OOFF (einmalig) und .FNL (letzte) annehmen.
   */
  public void setSequenceType1Code(SequenceType1Code sequencecode)
  {
    this.sequencecode = sequencecode;
  }
}
