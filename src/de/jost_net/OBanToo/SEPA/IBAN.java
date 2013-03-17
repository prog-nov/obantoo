/**
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.MessageFormat;

import de.jost_net.OBanToo.SEPA.Land.SEPALaender;
import de.jost_net.OBanToo.SEPA.Land.SEPALand;

public class IBAN
{
  private String iban;

  private SEPALand land;

  private static final String[] ALPHABET = new String[] { "A", "B", "C", "D",
      "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
      "S", "T", "U", "V", "W", "X", "Y", "Z" };

  /**
   * Konstruktor mit Übergabe der IBAN als String
   * 
   * @param iban
   *          IBAN
   * @throws SEPAException
   *           wenn die IBAN nicht den Konventionen entspricht.
   */
  public IBAN(String iban) throws SEPAException
  {
    this.iban = iban;
    if (iban == null)
    {
      throw new SEPAException("IBAN ist leer");
    }
    if (iban.length() < 4)
    {
      throw new SEPAException(
          "Ungültige IBAN. Landeskennung und/oder Prüfziffer fehlen");
    }
    land = SEPALaender.getLand(iban.substring(0, 2));
    int laebankid = land.getBankIdentifierLength();
    int laeaccount = land.getAccountLength();
    int laeiban = 4 + laebankid + laeaccount;
    if (iban.length() != laeiban)
    {
      throw new SEPAException("Ungültige IBAN-Länge. Vorgeschrieben sind "
          + laeiban + " Stellen für " + land.getBezeichnung());
    }
  }

  /**
   * 
   * Vor der Erstellung der persönlichen internationalen Bankkontonummer für
   * jeden Kontoinhaber wird von der Bank die Prüfziffer elektronisch berechnet.
   * Dazu werden in Deutschland die achtstellige Bankleitzahl, die zehnstellige
   * Kontonummer und die zweistellige, alphanumerische Länderkennung benötigt.
   * Kontonummern mit weniger als zehn Stellen werden mit führenden Nullen
   * aufgefüllt.
   * 
   * Die Berechnung erfolgt in mehreren Schritten. Zuerst wird die Länderkennung
   * um zwei Nullen ergänzt. Danach wird aus Kontonummer und Bankleitzahl die
   * BBAN kreiert. Also beispielsweise Bankleitzahl 70090100 und Kontonummer
   * 1234567890 ergeben die BBAN 700901001234567890.
   * 
   * Anschließend werden die beiden Alpha-Zeichen der Länderkennung sowie
   * weitere eventuell in der Kontonummer enthaltene Buchstaben in rein
   * numerische Ausdrücke umgewandelt. Die Grundlage für die Zahlen, die aus den
   * Buchstaben gebildet werden sollen, bildet ihre Position der jeweiligen
   * Alpha-Zeichen im lateinischen Alphabet. Zu diesem Zahlenwert wird 9
   * addiert. Die Summe ergibt die Zahl, die den jeweiligen Buchstaben ersetzen
   * soll. Dementsprechend steht für A (Position 1+9) die Zahl 10, für D
   * (Position 4+9) die 13 und für E (Position 5+9) die 14. Der Länderkennung DE
   * entspricht also die Ziffernfolge 1314.
   * 
   * Im nächsten Schritt wird diese Ziffernfolge, ergänzt um die beiden Nullen,
   * an die BBAN gehängt. Hieraus ergibt sich 700901001234567890131400. Diese
   * bei deutschen Konten immer 24-stellige Zahl wird anschließend Modulo 97
   * genommen. Das heißt, es wird der Rest berechnet, der sich bei der Teilung
   * der 24-stelligen Zahl durch 97 ergibt. Das ist für dieses Beispiel 90.
   * Dieses Ergebnis wird von der nach ISO-Standard festgelegten Zahl 98
   * subtrahiert. Ist das Resultat, wie in diesem Beispiel, kleiner als Zehn, so
   * wird der Zahl eine Null vorangestellt, sodass sich wieder ein zweistelliger
   * Wert ergibt. Somit ist die errechnete Prüfziffer 08. Aus der Länderkennung,
   * der zweistelligen Prüfsumme und der BBAN wird nun die IBAN generiert. Die
   * ermittelte IBAN lautet in unserem Beispiel: DE08700901001234567890.
   * 
   * D = 4.stelle im Alphabet + 9 = 13 E = 5.stelle im Alphabet + 9 = 14
   * 
   * -> DE = 1314
   * 
   * Länderkennung um 2 Nullen ergänzen
   * 
   * -> 131400
   * 
   * 1. Konstante zur Berechnung; Modulo 97
   * 
   * 700901001234567890131400 % 97
   * 
   * -> 90
   * 
   * 2. Konstante zur Berechnung: 98 - 90 = 8 -> ergänzt um führende 0 -> 08
   * 
   * DE08700901001234567890131400
   * 
   * @throws RemoteException
   * 
   */

  public IBAN(String kontoNr, String blz, String landkennzeichen)
      throws SEPAException
  {
    if (kontoNr == null || kontoNr.trim().length() == 0 || blz == null
        || blz.trim().length() == 0)
    {
      return;
    }

    SEPALand land = SEPALaender.getLand(landkennzeichen);
    if (blz.length() != land.getBankIdentifierLength().intValue())
    {
      throw new SEPAException(
          MessageFormat
              .format(
                  "Bankleitzahl hat falsche Länge für {0}. Maximal {1,number, integer} Stellen.",
                  new Object[] { land.getBezeichnung(),
                      new Integer(land.getBankIdentifierLength()) }));
    }

    if (kontoNr.length() > land.getAccountLength().intValue())
    {
      throw new SEPAException("Kontonummer zu lang für "
          + land.getBezeichnung());
    }
    StringBuilder accountString = new StringBuilder();
    for (int i = 0; i < land.getAccountLength().intValue() - kontoNr.length(); i++)
    {
      accountString.append("0");
    }
    accountString.append(kontoNr);
    this.iban = landkennzeichen
        + getPruefziffer(blz, accountString.toString(),
            getLandKennung(land.getKennzeichen())) + blz
        + accountString.toString();
  }

  public String getIBAN()
  {
    return iban;
  }

  public SEPALand getLand()
  {
    return land;
  }

  public String getBLZ()
  {
    if (iban.length() == 0)
    {
      return "";
    }
    return iban.substring(4, land.getBankIdentifierLength() + 4);
  }

  public String getKonto() throws SEPAException
  {
    if (iban.length() == 0)
    {
      return "";
    }
    return iban.substring(4 + land.getBankIdentifierLength(),
        land.getAccountLength() + land.getBankIdentifierLength() + 4);
  }

  private static String getPruefziffer(String blz, String konto,
      String laenderkennung) throws SEPAException
  {
    BigInteger bi = null;
    try
    {
      bi = new BigInteger(blz + konto + laenderkennung);
    }
    catch (NumberFormatException e)
    {
      String error = MessageFormat.format(
          "Ungültige Bankverbindung: {0} {1} {2}", blz, konto, laenderkennung);
      throw new SEPAException(error);
    }
    BigInteger modulo = bi.mod(BigInteger.valueOf(97));
    String pruefZiffer = String.valueOf(98 - modulo.longValue());

    if (pruefZiffer.length() < 2)
    {
      pruefZiffer = "0" + pruefZiffer;
    }
    return pruefZiffer;
  }

  private static final String getLandKennung(String landkennung)
  {
    int[] landKnzAsNumber = new int[2];

    for (int i = 0; i < 2; i++)
    {
      for (int j = 0; j < ALPHABET.length; j++)
      {
        if (ALPHABET[j].toUpperCase().equals(
            String.valueOf(landkennung.charAt(i)).toUpperCase()))
        {
          landKnzAsNumber[i] = j + 10;
          break;
        }
      }
    }
    return String.valueOf(landKnzAsNumber[0])
        + String.valueOf(landKnzAsNumber[1]) + "00";
  }

}
