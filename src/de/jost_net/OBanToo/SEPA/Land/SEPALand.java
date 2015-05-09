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

import java.util.ArrayList;
import java.util.StringTokenizer;

import de.jost_net.OBanToo.SEPA.SEPAException;

public class SEPALand
{
  private String kennzeichen;

  private ArrayList<Element> elements;

  private int gesamtlaenge = 4;

  private String bezeichnung;

  private Integer bankidentfierlength;

  private Integer accountlength;

  private String ibansample;

  public SEPALand(String kennzeichen, String bezeichnung, String ibanclass,
      String ibansample)
  {
    this.kennzeichen = kennzeichen;
    this.bezeichnung = bezeichnung;
    this.ibansample = ibansample;
    elements = new ArrayList<Element>();
    StringTokenizer tok = new StringTokenizer(ibanclass, "!nac", true);
    while (tok.hasMoreTokens())
    {
      int lae = Integer.parseInt(tok.nextToken());
      gesamtlaenge += lae;
      tok.nextToken(); // Dieser Token wird nicht benötigt
      elements.add(new Element(lae, tok.nextToken()));
    }
    bankidentfierlength = elements.get(0).lae;
    accountlength = elements.get(1).lae;
  }

  public String getKennzeichen()
  {
    return kennzeichen;
  }

  public String getBezeichnung()
  {
    return bezeichnung;
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

  public void setIBANSample(String ibansample)
  {
    this.ibansample = ibansample;
  }

  public String getIBANSample()
  {
    return this.ibansample;
  }

  public boolean check(String iban) throws SEPAException
  {
    if (iban.length() != gesamtlaenge)
    {
      throw new SEPAException("Ungültige Länge der IBAN");
    }
    int pos = 3;
    for (Element element : elements)
    {
      for (int i = pos; i <= pos + element.getLaenge(); i++)
      {
        char c = iban.charAt(i);
        if (element.isNumeric())
        {
          if (c < '0' || c > '9')
          {
            throw new SEPAException("Ungültiges Zeichen an Position " + i + 1);
          }
        }
        if (element.isAlpha())
        {
          if (c < 'A' || c > 'Z')
          {
            throw new SEPAException("Ungültiges Zeichen an Position " + i + 1);
          }
        }
        if (element.isCharacter())
        {
          if (!(c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))
          {
            throw new SEPAException("Ungültiges Zeichen an Position " + i + 1);
          }
        }
      }
      pos += element.getLaenge();
    }
    return true;
  }

  private class Element
  {
    private int lae;

    private String typ;

    public Element(int lae, String typ)
    {
      this.lae = lae;
      this.typ = typ;
    }

    public int getLaenge()
    {
      return lae;
    }

    public boolean isAlpha()
    {
      return typ.equals("a");
    }

    public boolean isNumeric()
    {
      return typ.equals("n");
    }

    public boolean isCharacter()
    {
      return typ.equals("c");
    }

  }

}
