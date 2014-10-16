/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import de.jost_net.OBanToo.PruefziffernCheck.KontoPruefziffernrechnung;
import de.jost_net.OBanToo.PruefziffernCheck.PZRet;
import de.jost_net.OBanToo.SEPA.SEPAException.Fehler;
import de.jost_net.OBanToo.SEPA.Ausnahmen.ExHypoBehandlung;
import de.jost_net.OBanToo.SEPA.BankenDaten.Bank;
import de.jost_net.OBanToo.SEPA.BankenDaten.Banken;
import de.jost_net.OBanToo.SEPA.Land.SEPALaender;
import de.jost_net.OBanToo.SEPA.Land.SEPALand;

/*
 Sehr geehrte Damen und Herren,

 wir haben die ab 9. Dezember 2013 gültige Version der Übersicht der IBAN
 Regeln im ExtraNet der Deutschen Bundesbank zur Verfügung gestellt.

 Gegenüber der seit 9. September 2013 gültigen Version wurden zwei neue
 IBAN-Regeln (0055 00 und 0056 00) in die Übersicht der IBAN-Regeln
 aufgenommen. Bei sechs IBAN-Regeln (0005 01, 0010 00, 0015 00, 0020 01,
 0045 00  und 0043 00) haben sich  inhaltliche  Änderungen ergeben; diese
 IBAN-Regeln sind mit einer neuen Versionsnummer gekennzeichnet.  Zu zwei
 IBAN-Regeln (0046 00 und 0047 00) hat sich lediglich die E-Mail-Adresse des
 Ansprechpartners geändert; bei diesen beiden Regeln bleibt die
 Versionsnummer unverändert. Bei der IBAN-Regel 0021 01 erfolgte in den
 Beispielen lediglich die Korrektur eines falschen (zehnstelligen) BIC; die
 Versionsnummer bleibt unverändert.

 */
public class IBAN
{

  private IBANCode code;

  private String iban;

  private String bic;

  private SEPALand land;

  private static final HashMap<String, String> transformation = new HashMap<String, String>();

  static
  {
    transformation.put("0", "0");
    transformation.put("1", "1");
    transformation.put("2", "2");
    transformation.put("3", "3");
    transformation.put("4", "4");
    transformation.put("5", "5");
    transformation.put("6", "6");
    transformation.put("7", "7");
    transformation.put("8", "8");
    transformation.put("9", "9");
    transformation.put("A", "10");
    transformation.put("B", "11");
    transformation.put("C", "12");
    transformation.put("D", "13");
    transformation.put("E", "14");
    transformation.put("F", "15");
    transformation.put("G", "16");
    transformation.put("H", "17");
    transformation.put("I", "18");
    transformation.put("J", "19");
    transformation.put("K", "20");
    transformation.put("L", "21");
    transformation.put("M", "22");
    transformation.put("N", "23");
    transformation.put("O", "24");
    transformation.put("P", "25");
    transformation.put("Q", "26");
    transformation.put("R", "27");
    transformation.put("S", "28");
    transformation.put("T", "29");
    transformation.put("U", "30");
    transformation.put("V", "31");
    transformation.put("W", "32");
    transformation.put("X", "33");
    transformation.put("Y", "34");
    transformation.put("Z", "35");
  }

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
    if (land == null)
    {
      throw new SEPAException(Fehler.UNGUELTIGES_LAND);
    }
    int laebankid = land.getBankIdentifierLength();
    int laeaccount = land.getAccountLength();
    int laeiban = 4 + laebankid + laeaccount;
    if (iban.length() != laeiban)
    {
      throw new SEPAException("Ungültige IBAN-Länge. Vorgeschrieben sind "
          + laeiban + " Stellen für " + land.getBezeichnung());
    }
    String pz = iban.substring(2, 4);
    String blz = iban.substring(4, 4 + laebankid);
    String konto = iban.substring(4 + laebankid, 4 + laebankid + laeaccount);
    if (land.getKennzeichen().equals("DE"))
    {
      Bank b = Banken.getBankByBLZ(blz);
      if (b == null)
      {
        throw new SEPAException("BLZ in der IBAN existiert nicht");
      }
      if (!pz.equals(getPruefziffer(blz, konto, land.getKennzeichen())))
      {
        throw new SEPAException("Ungültige IBAN. Prüfziffer falsch. " + iban);
      }
      bic = b.getBIC();
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
   * 
   */

  public IBAN(String kontoNr, String blz, String landkennzeichen)
      throws SEPAException
  {
    if (blz == null || blz.trim().length() == 0)
    {
      throw new SEPAException(Fehler.BLZ_LEER);
    }

    // Führende Nullen aus der Kontonummer entfernen
    while (kontoNr.length() > 0 && kontoNr.startsWith("0"))
    {
      kontoNr = kontoNr.substring(1);
    }

    if (kontoNr == null || kontoNr.trim().length() == 0)
    {
      throw new SEPAException(Fehler.KONTO_LEER);
    }

    SEPALand land = SEPALaender.getLand(landkennzeichen);
    if (land == null)
    {
      throw new SEPAException(Fehler.UNGUELTIGES_LAND);
    }
    if (blz.length() != land.getBankIdentifierLength().intValue())
    {
      throw new SEPAException(
          Fehler.BLZ_UNGUELTIGE_LAENGE,
          MessageFormat
              .format(
                  "Bankleitzahl hat falsche Länge für {0}. Maximal {1,number, integer} Stellen.",
                  new Object[] { land.getBezeichnung(),
                      new Integer(land.getBankIdentifierLength()) }));
    }

    if (kontoNr.length() > land.getAccountLength().intValue())
    {
      throw new SEPAException(Fehler.KONTO_UNGUELTIGE_LAENGE,
          "Kontonummer zu lang für " + land.getBezeichnung());
    }
    Bank b = Banken.getBankByBLZ(blz);
    if (b == null)
    {
      throw new SEPAException(Fehler.BLZ_UNGUELTIG, blz);
    }

    if (land.getKennzeichen().equals("DE"))
    {
      Class<?> cl = null;
      Method method = null;

      try
      {
        cl = IBAN.class;
        method = cl.getMethod("ibanRegel_" + b.getIBANRegel(), new Class[] {
            String.class, String.class, SEPALand.class });
      }
      catch (Exception e)
      {
        code = IBANCode.IBANBERECHNUNGNICHTMOEGLICH;
        throw new SEPAException(Fehler.IBANREGEL_NICHT_IMPLEMENTIERT,
            b.getIBANRegel());
      }

      try
      {
        Object[] args = new Object[] { blz, kontoNr, land };
        Object ret = method.invoke(null, args);
        IBANRet retval = (IBANRet) ret;
        code = retval.getCode();
        iban = retval.getIban();
        bic = retval.getBic();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
      catch (IllegalArgumentException e)
      {
        e.printStackTrace();
      }
      catch (InvocationTargetException e)
      {
        Throwable th = e.getCause();
        th.printStackTrace();
        throw new SEPAException(
            Fehler.KONTO_PRUEFZIFFERNREGEL_NICHT_IMPLEMENTIERT);
      }

    }
  }

  public String getIBAN()
  {
    return iban;
  }

  public String getBIC()
  {
    return bic;
  }

  public IBANCode getCode()
  {
    return code;
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

  public String getKonto()
  {
    if (iban.length() == 0)
    {
      return "";
    }
    String k = iban.substring(4 + land.getBankIdentifierLength(),
        land.getAccountLength() + land.getBankIdentifierLength() + 4);
    return truncateLeadingZeros(k);
  }

  private static String getPruefziffer(String blz, String konto,
      String laenderkennung) throws SEPAException
  {
    String tmpIban1 = blz + konto + laenderkennung + "00";
    String tmpIban2 = "";
    for (int i = 0; i < tmpIban1.length(); i++)
    {
      tmpIban2 += transformation.get(tmpIban1.substring(i, i + 1));
    }
    BigInteger bi = null;
    try
    {
      bi = new BigInteger(tmpIban2);
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

  public static IBANRet ibanRegel_000000(String blz, String konto, SEPALand land)
      throws Exception
  {
    return ibanRegel_000000(blz, konto, land, null, false);
  }

  /**
   * Standardregel
   */
  public static IBANRet ibanRegel_000000(String blz, String konto,
      SEPALand land, String bic, boolean ungueltigePruefzifferZugelassen)
      throws Exception
  {
    Bank b = Banken.getBankByBLZ(blz);

    if (b == null)
    {
      return new IBANRet(IBANCode.BLZUNGUELTIG);
    }
    boolean pruefziffernmethodefehlt = false;
    try
    {
      if (!KontoPruefziffernrechnung.checkAccountCRCByAlg(
          b.getPruefziffernmethode(), blz, konto).isValid())
      {
        if (!ungueltigePruefzifferZugelassen)
        {
          return new IBANRet(IBANCode.KONTONUMMERUNGUELTIG);
        }
      }
    }
    catch (Exception e)
    {
      if (e.getMessage().startsWith("CRC algorithm"))
      {
        pruefziffernmethodefehlt = true;
      }
      else
      {
        return new IBANRet(IBANCode.PRUEFZIFFERNMETHODEFEHLT);
      }
    }
    StringBuilder accountString = new StringBuilder();
    for (int i = 0; i < land.getAccountLength().intValue() - konto.length(); i++)
    {
      accountString.append("0");
    }
    accountString.append(konto);
    String iban = land.getKennzeichen()
        + getPruefziffer(blz, accountString.toString(), land.getKennzeichen())
        + blz + accountString.toString();
    if (bic == null)
    {
      BIC bi = new BIC(konto, blz, land.getKennzeichen());
      bic = bi.getBIC();
    }
    if (pruefziffernmethodefehlt)
    {
      return new IBANRet(IBANCode.PRUEFZIFFERNMETHODEFEHLT, iban, bic);
    }
    else
    {
      return new IBANRet(IBANCode.PRUEFZIFFERNMETHODEFEHLT, iban, bic);
    }
  }

  /**
   * keine IBAN-Ermittlung (diese Bankleitzahl findet im Zahlungsverkehr keine
   * Verwendung)
   */
  public static IBANRet ibanRegel_000100(String blz, String konto, SEPALand land)
  {
    return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
  }

  /**
   * Augsburger Aktienbank
   */
  public static IBANRet ibanRegel_000200(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (konto.substring(7, 9).equals("86") || konto.substring(7, 8).equals("6"))
    {
      return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Aareal Bank AG ZWL Wiesbaden
   */
  public static IBANRet ibanRegel_000300(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (konto.equals("6161604670"))
    {
      return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Landesbank Berlin / Berliner Sparkasse
   */
  public static IBANRet ibanRegel_000400(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("135", "0990021440");
    spendenkonten.put("1111", "6600012020");
    spendenkonten.put("1900", "0920019005");
    spendenkonten.put("7878", "0780008006");
    spendenkonten.put("8888", "0250030942");
    spendenkonten.put("9595", "1653524703");
    spendenkonten.put("97097", "0013044150");
    spendenkonten.put("112233", "0630025819");
    spendenkonten.put("336666", "6604058903");
    spendenkonten.put("484848", "0920018963");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Commerzbank AG
   */
  public static IBANRet ibanRegel_000501(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("30040000" + "36", "0261103600");
    spendenkonten.put("47880031" + "50", "519899900");
    spendenkonten.put("47840065" + "50", "150103000");
    spendenkonten.put("47840065" + "55", "150103000");
    spendenkonten.put("70080000" + "94", "928553201");
    spendenkonten.put("70040041" + "94", "212808000");
    spendenkonten.put("47840065" + "99", "150103000");
    spendenkonten.put("37080040" + "100", "269100000");
    spendenkonten.put("38040007" + "100", "119160000");
    spendenkonten.put("37080040" + "111", "215022000");
    spendenkonten.put("51080060" + "123", "12299300");
    spendenkonten.put("36040039" + "150", "161620000");
    spendenkonten.put("68080030" + "202", "416520200");
    spendenkonten.put("30040000" + "222", "348010002");
    spendenkonten.put("38040007" + "240", "109024000");
    spendenkonten.put("69240075" + "444", "445520000");
    spendenkonten.put("60080000" + "502", "901581400");
    spendenkonten.put("60040071" + "502", "525950200");
    spendenkonten.put("55040022" + "555", "2110500");
    spendenkonten.put("39080005" + "556", "204655600");
    spendenkonten.put("39040013" + "556", "106555600");
    spendenkonten.put("57080070" + "661", "604101200");
    spendenkonten.put("26580070" + "700", "710000000");
    spendenkonten.put("50640015" + "777", "222222200");
    spendenkonten.put("30040000" + "999", "123799900");
    spendenkonten.put("86080000" + "1212", "480375900");
    spendenkonten.put("37040044" + "1888", "212129101");
    spendenkonten.put("25040066" + "1919", "141919100");
    spendenkonten.put("10080000" + "1987", "928127700");
    spendenkonten.put("50040000" + "2000", "728400300");
    spendenkonten.put("20080000" + "2222", "903927200");
    spendenkonten.put("38040007" + "3366", "385333000");
    spendenkonten.put("37080040" + "4004", "233533500");
    spendenkonten.put("37080040" + "4444", "233000300");
    spendenkonten.put("43080083" + "4630", "825110100");
    spendenkonten.put("50080000" + "6060", "96736100");
    spendenkonten.put("10040000" + "7878", "267878700");
    spendenkonten.put("10080000" + "8888", "928126501");
    spendenkonten.put("50080000" + "9000", "26492100");
    spendenkonten.put("79080052" + "9696", "300021700");
    spendenkonten.put("79040047" + "9696", "680210200");
    spendenkonten.put("39080005" + "9800", "208457000");
    spendenkonten.put("50080000" + "42195", "900333200");
    spendenkonten.put("32040024" + "47800", "155515000");
    spendenkonten.put("37080040" + "55555", "263602501");
    spendenkonten.put("38040007" + "55555", "305555500");
    spendenkonten.put("50080000" + "101010", "90003500");
    spendenkonten.put("50040000" + "101010", "311011100");
    spendenkonten.put("37040044" + "102030", "222344400");
    spendenkonten.put("86080000" + "121200", "480375900");
    spendenkonten.put("66280053" + "121212", "625242400");
    spendenkonten.put("16080000" + "123456", "12345600");
    spendenkonten.put("29080010" + "124124", "107502000");
    spendenkonten.put("37080040" + "182002", "216603302");
    spendenkonten.put("12080000" + "212121", "4050462200");
    spendenkonten.put("37080040" + "300000", "983307900");
    spendenkonten.put("37040044" + "300000", "300000700");
    spendenkonten.put("37080040" + "333333", "270330000");
    spendenkonten.put("38040007" + "336666", "105232300");
    spendenkonten.put("55040022" + "343434", "2179000");
    spendenkonten.put("85080000" + "400000", "459488501");
    spendenkonten.put("37080040" + "414141", "41414100");
    spendenkonten.put("38040007" + "414141", "108000100");
    spendenkonten.put("20080000" + "505050", "500100600");
    spendenkonten.put("37080040" + "555666", "55566600");
    spendenkonten.put("20080000" + "666666", "900732500");
    spendenkonten.put("30080000" + "700000", "800005000");
    spendenkonten.put("70080000" + "700000", "750055500");
    spendenkonten.put("70080000" + "900000", "319966601");
    spendenkonten.put("37080040" + "909090", "269100000");
    spendenkonten.put("38040007" + "909090", "1191600");
    spendenkonten.put("70080000" + "949494", "575757500");
    spendenkonten.put("70080000" + "1111111", "448060000");
    spendenkonten.put("70040041" + "1111111", "152140000");
    spendenkonten.put("10080000" + "1234567", "920192001");
    spendenkonten.put("38040007" + "1555555", "258266600");
    spendenkonten.put("76040061" + "2500000", "482146800");
    spendenkonten.put("16080000" + "3030400", "4205227110");
    spendenkonten.put("37080040" + "5555500", "263602501");
    spendenkonten.put("75040062" + "6008833", "600883300");
    spendenkonten.put("12080000" + "7654321", "144000700");
    spendenkonten.put("70080000" + "7777777", "443540000");
    spendenkonten.put("70040041" + "7777777", "213600000");
    spendenkonten.put("64140036" + "8907339", "890733900");
    spendenkonten.put("70080000" + "9000000", "319966601");
    spendenkonten.put("61080006" + "9999999", "202427500");
    spendenkonten.put("12080000" + "12121212", "4101725100");
    spendenkonten.put("29080010" + "12412400", "107502000");
    spendenkonten.put("34280032" + "014111935", "645753800");
    spendenkonten.put("38040007" + "43434343", "118163500");
    spendenkonten.put("30080000" + "70000000", "800005000");
    spendenkonten.put("70080000" + "70000000", "750055500");
    spendenkonten.put("44040037" + "111111111", "320565500");
    spendenkonten.put("70040041" + "400500500", "400500500");
    spendenkonten.put("60080000" + "500500500", "901581400");
    spendenkonten.put("60040071" + "500500500", "512700600");
    String _konto = spendenkonten.get(blz + konto);
    if (_konto != null)
    {
      konto = _konto;
    }

    Bank b = Banken.getBankByBLZ(blz);
    PZRet ok = KontoPruefziffernrechnung.checkAccountCRCByAlg(
        b.getPruefziffernmethode(), blz, konto);
    if (!ok.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }
    if (b.getPruefziffernmethode().equals("13"))
    {
      if (konto.length() == 6 || konto.length() == 7)
      {
        konto = konto + "00";
      }
    }
    if (b.getPruefziffernmethode().equals("76"))
    {
      if (ok.getPos() == 10)
      {
        konto = konto + "00";
      }
      konto = fillup(konto);
      String kontoart = konto.substring(0, 1);
      if (kontoart.equals("1") || kontoart.equals("2") || kontoart.equals("3")
          || kontoart.equals("5"))
      {
        return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
      }
    }
    String[] blzgesperrtekontenkreise = new String[] { "10080900", "25780022",
        "42080082", "54280023", "65180005", "79580099", "12080000", "25980027",
        "42680081", "54580020", "65380003", "80080000", "13080000", "26080024",
        "43080083", "54680022", "66280053", "81080000", "14080000", "26281420",
        "44080055", "55080065", "66680013", "82080000", "15080000", "26580070",
        "44080057", "57080070", "67280051", "83080000", "16080000", "26880063",
        "44580070", "58580074", "69280035", "84080000", "17080000", "26981062",
        "45080060", "59080090", "70080056", "85080200", "18080000", "28280012",
        "46080010", "60080055", "70080057", "86080055", "20080055", "29280011",
        "47880031", "60080057", "70380006", "86080057", "20080057", "30080055",
        "49080025", "60380002", "71180005", "87080000", "21080050", "30080057",
        "50080055", "60480008", "72180002", "21280002", "31080015", "50080057",
        "61080006", "73180011", "21480003", "32080010", "50080082", "61281007",
        "73380004", "21580000", "33080030", "50680002", "61480001", "73480013",
        "22180000", "34080031", "50780006", "62080012", "74180009", "22181400",
        "34280032", "50880050", "62280012", "74380007", "22280000", "36280071",
        "51380040", "63080015", "75080003", "24080000", "36580072", "52080080",
        "64080014", "76080053", "24180001", "40080040", "53080030", "64380011",
        "79080052", "25480021", "41280043", "54080021", "65080009", "79380051" };
    for (String s : blzgesperrtekontenkreise)
    {
      if (blz.equals(s))
      {
        BigInteger k = new BigInteger(konto);
        BigInteger kv = new BigInteger("0998000000");
        BigInteger kb = new BigInteger("0999499999");
        if (k.compareTo(kv) >= 0 && k.compareTo(kb) <= 0)
        {
          return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
        }
      }
    }
    if (blz.equals("50040033")) // Generell gesperrt
    {
      return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
    }
    return ibanRegel_000000(blz, konto, land, "COBADEFFXXX", false);
  }

  /**
   * Commerzbank AG 02: Anpassung der Buchungskontonummern der Spendenkonten 100
   * (Kap. 5.4), 909090, 555, 343434
   */
  public static IBANRet ibanRegel_000502(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("30040000" + "36", "0261103600");
    spendenkonten.put("47880031" + "50", "519899900");
    spendenkonten.put("47840065" + "50", "150103000");
    spendenkonten.put("47840065" + "55", "150103000");
    spendenkonten.put("70080000" + "94", "928553201");
    spendenkonten.put("70040041" + "94", "212808000");
    spendenkonten.put("47840065" + "99", "150103000");
    spendenkonten.put("37080040" + "100", "269100000");
    spendenkonten.put("38040007" + "100", "119160000");
    spendenkonten.put("37080040" + "111", "215022000");
    spendenkonten.put("51080060" + "123", "12299300");
    spendenkonten.put("36040039" + "150", "161620000");
    spendenkonten.put("68080030" + "202", "416520200");
    spendenkonten.put("30040000" + "222", "348010002");
    spendenkonten.put("38040007" + "240", "109024000");
    spendenkonten.put("69240075" + "444", "445520000");
    spendenkonten.put("60080000" + "502", "901581400");
    spendenkonten.put("60040071" + "502", "525950200");
    spendenkonten.put("55040022" + "555", "211050000");
    spendenkonten.put("39080005" + "556", "204655600");
    spendenkonten.put("39040013" + "556", "106555600");
    spendenkonten.put("57080070" + "661", "604101200");
    spendenkonten.put("26580070" + "700", "710000000");
    spendenkonten.put("50640015" + "777", "222222200");
    spendenkonten.put("30040000" + "999", "123799900");
    spendenkonten.put("86080000" + "1212", "480375900");
    spendenkonten.put("37040044" + "1888", "212129101");
    spendenkonten.put("25040066" + "1919", "141919100");
    spendenkonten.put("10080000" + "1987", "928127700");
    spendenkonten.put("50040000" + "2000", "728400300");
    spendenkonten.put("20080000" + "2222", "903927200");
    spendenkonten.put("38040007" + "3366", "385333000");
    spendenkonten.put("37080040" + "4004", "233533500");
    spendenkonten.put("37080040" + "4444", "233000300");
    spendenkonten.put("43080083" + "4630", "825110100");
    spendenkonten.put("50080000" + "6060", "96736100");
    spendenkonten.put("10040000" + "7878", "267878700");
    spendenkonten.put("10080000" + "8888", "928126501");
    spendenkonten.put("50080000" + "9000", "26492100");
    spendenkonten.put("79080052" + "9696", "300021700");
    spendenkonten.put("79040047" + "9696", "680210200");
    spendenkonten.put("39080005" + "9800", "208457000");
    spendenkonten.put("50080000" + "42195", "900333200");
    spendenkonten.put("32040024" + "47800", "155515000");
    spendenkonten.put("37080040" + "55555", "263602501");
    spendenkonten.put("38040007" + "55555", "305555500");
    spendenkonten.put("50080000" + "101010", "90003500");
    spendenkonten.put("50040000" + "101010", "311011100");
    spendenkonten.put("37040044" + "102030", "222344400");
    spendenkonten.put("86080000" + "121200", "480375900");
    spendenkonten.put("66280053" + "121212", "625242400");
    spendenkonten.put("16080000" + "123456", "12345600");
    spendenkonten.put("29080010" + "124124", "107502000");
    spendenkonten.put("37080040" + "182002", "216603302");
    spendenkonten.put("12080000" + "212121", "4050462200");
    spendenkonten.put("37080040" + "300000", "983307900");
    spendenkonten.put("37040044" + "300000", "300000700");
    spendenkonten.put("37080040" + "333333", "270330000");
    spendenkonten.put("38040007" + "336666", "105232300");
    spendenkonten.put("55040022" + "343434", "217900000");
    spendenkonten.put("85080000" + "400000", "459488501");
    spendenkonten.put("37080040" + "414141", "41414100");
    spendenkonten.put("38040007" + "414141", "108000100");
    spendenkonten.put("20080000" + "505050", "500100600");
    spendenkonten.put("37080040" + "555666", "55566600");
    spendenkonten.put("20080000" + "666666", "900732500");
    spendenkonten.put("30080000" + "700000", "800005000");
    spendenkonten.put("70080000" + "700000", "750055500");
    spendenkonten.put("70080000" + "900000", "319966601");
    spendenkonten.put("37080040" + "909090", "269100000");
    spendenkonten.put("38040007" + "909090", "119160000");
    spendenkonten.put("70080000" + "949494", "575757500");
    spendenkonten.put("70080000" + "1111111", "448060000");
    spendenkonten.put("70040041" + "1111111", "152140000");
    spendenkonten.put("10080000" + "1234567", "920192001");
    spendenkonten.put("38040007" + "1555555", "258266600");
    spendenkonten.put("76040061" + "2500000", "482146800");
    spendenkonten.put("16080000" + "3030400", "4205227110");
    spendenkonten.put("37080040" + "5555500", "263602501");
    spendenkonten.put("75040062" + "6008833", "600883300");
    spendenkonten.put("12080000" + "7654321", "144000700");
    spendenkonten.put("70080000" + "7777777", "443540000");
    spendenkonten.put("70040041" + "7777777", "213600000");
    spendenkonten.put("64140036" + "8907339", "890733900");
    spendenkonten.put("70080000" + "9000000", "319966601");
    spendenkonten.put("61080006" + "9999999", "202427500");
    spendenkonten.put("12080000" + "12121212", "4101725100");
    spendenkonten.put("29080010" + "12412400", "107502000");
    spendenkonten.put("34280032" + "014111935", "645753800");
    spendenkonten.put("38040007" + "43434343", "118163500");
    spendenkonten.put("30080000" + "70000000", "800005000");
    spendenkonten.put("70080000" + "70000000", "750055500");
    spendenkonten.put("44040037" + "111111111", "320565500");
    spendenkonten.put("70040041" + "400500500", "400500500");
    spendenkonten.put("60080000" + "500500500", "901581400");
    spendenkonten.put("60040071" + "500500500", "512700600");
    String _konto = spendenkonten.get(blz + konto);
    if (_konto != null)
    {
      konto = _konto;
    }

    Bank b = Banken.getBankByBLZ(blz);
    PZRet ok = KontoPruefziffernrechnung.checkAccountCRCByAlg(
        b.getPruefziffernmethode(), blz, konto);
    if (!ok.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }
    if (b.getPruefziffernmethode().equals("13"))
    {
      if (konto.length() == 6 || konto.length() == 7)
      {
        konto = konto + "00";
      }
    }
    if (b.getPruefziffernmethode().equals("76"))
    {
      if (ok.getPos() == 10)
      {
        konto = konto + "00";
      }
      konto = fillup(konto);
      String kontoart = konto.substring(0, 1);
      if (kontoart.equals("1") || kontoart.equals("2") || kontoart.equals("3")
          || kontoart.equals("5"))
      {
        return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
      }
    }
    String[] blzgesperrtekontenkreise = new String[] { "10080900", "25780022",
        "42080082", "54280023", "65180005", "79580099", "12080000", "25980027",
        "42680081", "54580020", "65380003", "80080000", "13080000", "26080024",
        "43080083", "54680022", "66280053", "81080000", "14080000", "26281420",
        "44080055", "55080065", "66680013", "82080000", "15080000", "26580070",
        "44080057", "57080070", "67280051", "83080000", "16080000", "26880063",
        "44580070", "58580074", "69280035", "84080000", "17080000", "26981062",
        "45080060", "59080090", "70080056", "85080200", "18080000", "28280012",
        "46080010", "60080055", "70080057", "86080055", "20080055", "29280011",
        "47880031", "60080057", "70380006", "86080057", "20080057", "30080055",
        "49080025", "60380002", "71180005", "87080000", "21080050", "30080057",
        "50080055", "60480008", "72180002", "21280002", "31080015", "50080057",
        "61080006", "73180011", "21480003", "32080010", "50080082", "61281007",
        "73380004", "21580000", "33080030", "50680002", "61480001", "73480013",
        "22180000", "34080031", "50780006", "62080012", "74180009", "22181400",
        "34280032", "50880050", "62280012", "74380007", "22280000", "36280071",
        "51380040", "63080015", "75080003", "24080000", "36580072", "52080080",
        "64080014", "76080053", "24180001", "40080040", "53080030", "64380011",
        "79080052", "25480021", "41280043", "54080021", "65080009", "79380051" };
    for (String s : blzgesperrtekontenkreise)
    {
      if (blz.equals(s))
      {
        BigInteger k = new BigInteger(konto);
        BigInteger kv = new BigInteger("0998000000");
        BigInteger kb = new BigInteger("0999499999");
        if (k.compareTo(kv) >= 0 && k.compareTo(kb) <= 0)
        {
          return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
        }
      }
    }
    if (blz.equals("50040033")) // Generell gesperrt
    {
      return new IBANRet(IBANCode.IBANBERECHNUNGNICHTMOEGLICH);
    }
    return ibanRegel_000000(blz, konto, land, "COBADEFFXXX", false);
  }

  /**
   * Commerzbank AG 03: Anpassung des Testfalls "Gesperrte BLZ"<br>
   * Fkeine Änderungen in der Logik erforderlich
   */
  public static IBANRet ibanRegel_000503(String blz, String konto, SEPALand land)
      throws Exception
  {
    return ibanRegel_000502(blz, konto, land);
  }

  /**
   * Stadtsparkasse München
   */
  public static IBANRet ibanRegel_000600(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("1111111", "20228888");
    spendenkonten.put("7777777", "903286003");
    spendenkonten.put("34343434", "1000506517");
    spendenkonten.put("70000", "18180018");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Sparkasse KölnBonn
   */
  public static IBANRet ibanRegel_000700(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("111", "1115");
    spendenkonten.put("221", "23002157");
    spendenkonten.put("1888", "18882068");
    spendenkonten.put("2006", "1900668508");
    spendenkonten.put("2626", "1900730100");
    spendenkonten.put("3004", "1900637016");
    spendenkonten.put("3636", "23002447");
    spendenkonten.put("4000", "4028");
    spendenkonten.put("4444", "17368");
    spendenkonten.put("5050", "73999");
    spendenkonten.put("8888", "1901335750");
    spendenkonten.put("30000", "9992959");
    spendenkonten.put("43430", "1901693331");
    spendenkonten.put("46664", "1900399856");
    spendenkonten.put("55555", "34407379");
    spendenkonten.put("102030", "1900480466");
    spendenkonten.put("151515", "57762957");
    spendenkonten.put("222222", "2222222");
    spendenkonten.put("300000", "9992959");
    spendenkonten.put("333333", "33217");
    spendenkonten.put("414141", "92817");
    spendenkonten.put("606060", "91025");
    spendenkonten.put("909090", "90944");
    spendenkonten.put("2602024", "5602024");
    spendenkonten.put("3000000", "9992959");
    spendenkonten.put("7777777", "2222222");
    spendenkonten.put("8090100", "38901");
    spendenkonten.put("14141414", "43597665");
    spendenkonten.put("15000023", "15002223");
    spendenkonten.put("15151515", "57762957");
    spendenkonten.put("22222222", "2222222");
    spendenkonten.put("200820082", "1901783868");
    spendenkonten.put("222220022", "2222222");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * BHF-Bank AG
   */
  public static IBANRet ibanRegel_000800(String blz, String konto, SEPALand land)
      throws Exception
  {
    String[] blzs = new String[] { "10020200", "20120200", "25020200",
        "30020500", "51020000", "55020000", "60120200", "70220200", "86020200" };
    String _blz = blz;
    for (String s : blzs)
    {
      if (blz.equals(s))
      {
        _blz = "50020200";
      }
    }
    return ibanRegel_000000(_blz, konto, land, "BHFBDEFF500", false);
  }

  /**
   * Sparkasse Schopfheim-Zell
   */
  public static IBANRet ibanRegel_000900(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("68351976") && konto.length() == 10
        && konto.startsWith("1116"))
    {
      konto = "3047" + konto.substring(4);
    }
    blz = "68351557";

    return ibanRegel_000000(blz, konto, land, "SOLADES1SFH", false);
  }

  /**
   * Frankfurter Sparkasse
   */
  public static IBANRet ibanRegel_001000(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("50050201") & konto.equals("2000"))
    {
      konto = "222000";
    }
    if (blz.equals("50050201") & konto.equals("800000"))
    {
      konto = "180802";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Frankfurter Sparkasse
   */
  public static IBANRet ibanRegel_001001(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("50050201") & konto.equals("2000"))
    {
      konto = "222000";
    }
    if (blz.equals("50050201") & konto.equals("800000"))
    {
      konto = "180802";
    }
    // Neu in 01
    if (blz.equals("50050222"))
    {
      blz = "50050201";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Sparkasse Krefeld
   */
  public static IBANRet ibanRegel_001100(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("1000", "8010001");
    spendenkonten.put("47800", "47803");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Landesbank Hessen-Thüringen Girozentrale
   */
  public static IBANRet ibanRegel_001201(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "50050000";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Landesbank Hessen-Thüringen Girozentrale
   */
  public static IBANRet ibanRegel_001301(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "30050000";
    return ibanRegel_000000(blz, konto, land, "WELADEDDXXX", false);
  }

  /**
   * Deutsche Apotheker- und Ärztebank eG
   */
  public static IBANRet ibanRegel_001400(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "30060601";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Pax-Bank eG
   */
  public static IBANRet ibanRegel_001500(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("556", "0000101010");
    spendenkonten.put("888", "0031870011");
    spendenkonten.put("4040", "4003600101");
    spendenkonten.put("5826", "1015826017");
    spendenkonten.put("25000", "0025000110");
    spendenkonten.put("393393", "0033013019");
    spendenkonten.put("444555", "0032230016");
    spendenkonten.put("603060", "6002919018");
    spendenkonten.put("2120041", "0002130041");
    spendenkonten.put("80868086", "4007375013");
    spendenkonten.put("400569017", "4000569017");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Pax-Bank eG
   */
  public static IBANRet ibanRegel_001501(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("94", "3008888018"); // Neu in 01
    spendenkonten.put("556", "0000101010");
    spendenkonten.put("888", "0031870011");
    spendenkonten.put("4040", "4003600101");
    spendenkonten.put("5826", "1015826017");
    spendenkonten.put("25000", "0025000110");
    spendenkonten.put("393393", "0033013019");
    spendenkonten.put("444555", "0032230016");
    spendenkonten.put("603060", "6002919018");
    spendenkonten.put("2120041", "0002130041");
    spendenkonten.put("80868086", "4007375013");
    spendenkonten.put("400569017", "4000569017");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Kölner Bank eG
   */
  public static IBANRet ibanRegel_001600(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("300000", "18128012");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Volksbank Bonn Rhein-Sieg
   */
  public static IBANRet ibanRegel_001700(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("100", "2009090013");
    spendenkonten.put("111", "2111111017");
    spendenkonten.put("240", "2100240010");
    spendenkonten.put("4004", "2204004016");
    spendenkonten.put("4444", "2044444014");
    spendenkonten.put("6060", "2016060014");
    spendenkonten.put("102030", "1102030016");
    spendenkonten.put("333333", "2033333016");
    spendenkonten.put("909090", "2009090013");
    spendenkonten.put("50005000", "5000500013");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Aachener Bank eG
   */
  public static IBANRet ibanRegel_001800(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("556", "120440110");
    spendenkonten.put("5435435430", "543543543");
    spendenkonten.put("2157", "121787016");
    spendenkonten.put("9800", "120800019");
    spendenkonten.put("202050", "1221864014");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Bethmann Bank
   */
  public static IBANRet ibanRegel_001900(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("50130100") || blz.equals("50220200")
        || blz.equals("70030800"))
    {
      blz = "50120383";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Deutsche Bank AG
   */
  public static IBANRet ibanRegel_002001(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("10020000"))
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
    // Spendenkonten
    if (blz.equals("50070010") && konto.equals("9999"))
    {
      konto = "92777202";
    }

    Bank b = Banken.getBankByBLZ(blz);
    if (b.getPruefziffernmethode().equals("63"))
    {
      konto = truncateLeadingZeros(konto);
      if (konto.length() <= 4)
      {
        return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
      }
      else if (konto.length() >= 5 && konto.length() <= 6)
      {
        String _konto = konto + "00";
        PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
            b.getPruefziffernmethode(), blz, _konto);
        if (ret.isValid())
        {
          if (ret.getAlg().equals("63") && ret.getPos() == 8)
          {
            return ibanRegel_000000(blz, _konto, land);
          }
        }
      }
      else if (konto.length() == 7)
      {
        PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
            b.getPruefziffernmethode(), blz, konto);
        if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
        {
          String _konto = konto + "00";
          ret = KontoPruefziffernrechnung.checkAccountCRC(
              b.getPruefziffernmethode(), blz, _konto);
          if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
          {
            return ibanRegel_000000(blz, _konto, land);
          }
          else
          {
            return ibanRegel_000000(blz, konto, land);
          }
        }
        else
        {
          String _konto = konto + "00";
          ret = KontoPruefziffernrechnung.checkAccountCRC(
              b.getPruefziffernmethode(), blz, _konto);
          if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
          {
            return ibanRegel_000000(blz, _konto, land);
          }
          else
          {
            return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
          }
        }
      }
      else if (konto.length() >= 8 && konto.length() <= 9)
      {
        PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
            b.getPruefziffernmethode(), blz, konto);
        if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
        {
          return ibanRegel_000000(blz, konto, land);
        }
      }
    }
    else if (b.getPruefziffernmethode().equals("C7"))
    {
      PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
          b.getPruefziffernmethode(), blz, konto);
      if (ret.isValid() && ret.getAlg().equals("63"))
      {
        return ibanRegel_000000(blz, konto, land);
      }
      else if (ret.isValid() && ret.getAlg().equals("06"))
      {
        return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
      }
      else
      {
        return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
      }
    }
    return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
  }

  /**
   * Deutsche Bank AG
   */
  public static IBANRet ibanRegel_002002(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("10020000"))
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
    // Spendenkonten
    if (blz.equals("50070010") && konto.equals("9999"))
    {
      konto = "92777202";
    }

    Bank b = Banken.getBankByBLZ(blz);
    if (b.getPruefziffernmethode().equals("63"))
    {
      konto = truncateLeadingZeros(konto);
      if (konto.length() <= 4)
      {
        return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
      }
      else if (konto.length() >= 5 && konto.length() <= 6)
      {
        String _konto = konto + "00";
        PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
            b.getPruefziffernmethode(), blz, _konto);
        if (ret.isValid())
        {
          if (ret.getAlg().equals("63") && ret.getPos() == 8)
          {
            return ibanRegel_000000(blz, _konto, land);
          }
        }
      }
      else if (konto.length() == 7)
      {
        PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
            b.getPruefziffernmethode(), blz, konto);
        if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
        {
          String _konto = konto + "00";
          ret = KontoPruefziffernrechnung.checkAccountCRC(
              b.getPruefziffernmethode(), blz, _konto);
          if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
          {
            return ibanRegel_000000(blz, _konto, land);
          }
          else
          {
            return ibanRegel_000000(blz, konto, land);
          }
        }
        else
        {
          String _konto = konto + "00";
          ret = KontoPruefziffernrechnung.checkAccountCRC(
              b.getPruefziffernmethode(), blz, _konto);
          if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
          {
            return ibanRegel_000000(blz, _konto, land);
          }
          else
          {
            return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
          }
        }
      }
      else if (konto.length() >= 8 && konto.length() <= 9)
      {
        PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
            b.getPruefziffernmethode(), blz, konto);
        if (ret.isValid() && ret.getAlg().equals("63") && ret.getPos() == 8)
        {
          return ibanRegel_000000(blz, konto, land);
        }
      }
    }
    else if (b.getPruefziffernmethode().equals("C7"))
    {
      PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
          b.getPruefziffernmethode(), blz, konto);
      if (ret.isValid() && ret.getAlg().equals("63"))
      {
        return ibanRegel_000000(blz, konto, land);
      }
      else if (ret.isValid() && ret.getAlg().equals("06"))
      {
        return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
      }
      else
      {
        return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
      }
    }
    return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
  }

  /**
   * National-Bank AG
   */
  public static IBANRet ibanRegel_002101(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "36020030";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * GLS Gemeinschaftsbank eG
   */
  public static IBANRet ibanRegel_002200(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("1111111", "2222200000");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Volksbank Osnabrück eG
   */
  public static IBANRet ibanRegel_002300(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("700", "1000700800");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Bank im Bistum Essen eG
   */
  public static IBANRet ibanRegel_002400(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("94", "1694");
    spendenkonten.put("248", "17248");
    spendenkonten.put("345", "17345");
    spendenkonten.put("400", "14400");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Landesbank Baden-Württemberg / Baden-Württembergische Bank
   */
  public static IBANRet ibanRegel_002500(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "60050101";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Bank für Kirche und Diakonie eG, KD Bank
   */
  public static IBANRet ibanRegel_002600(String blz, String konto, SEPALand land)
      throws Exception
  {
    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (ret.isValid())
    {
      return ibanRegel_000000(blz, konto, land);
    }
    else if (konto.equals("55111") || konto.equals("80901002"))
    {
      return ibanRegel_000000(blz, konto, land, null, true);
    }
    else
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
  }

  /**
   * Volksbank Krefeld eG
   */
  public static IBANRet ibanRegel_002700(String blz, String konto, SEPALand land)
      throws Exception
  {
    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (ret.isValid())
    {
      return ibanRegel_000000(blz, konto, land);
    }
    else if (konto.equals("3333") || konto.equals("4444"))
    {
      return ibanRegel_000000(blz, konto, land, null, true);
    }
    else
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
  }

  /**
   * Sparkasse Hannover
   */
  public static IBANRet ibanRegel_002800(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("25050299"))
    {
      blz = "25050180";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Société Générale
   */
  public static IBANRet ibanRegel_002900(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (konto.length() == 10 && !konto.substring(0, 1).equals("0")
        && konto.substring(3, 4).equals("0"))
    {
      konto = konto.substring(0, 3) + konto.substring(4, 10);
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Volksbank Krefeld eG
   */
  public static IBANRet ibanRegel_003000(String blz, String konto, SEPALand land)
      throws Exception
  {
    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (ret.isValid())
    {
      return ibanRegel_000000(blz, konto, land);
    }
    ArrayList<String> konten = new ArrayList<String>();
    konten.add("1718190");
    konten.add("22000225");
    konten.add("49902271");
    konten.add("49902280");
    konten.add("101680029");
    konten.add("104200028");
    konten.add("106200025");
    konten.add("108000171");
    konten.add("108000279");
    konten.add("108001364");
    konten.add("108001801");
    konten.add("108002514");
    konten.add("300008542");
    konten.add("9130099995");
    konten.add("9130500002");
    konten.add("9131100008");
    konten.add("9131600000");
    konten.add("9131610006");
    konten.add("9132200006");
    konten.add("9132400005");
    konten.add("9132600004 ");
    konten.add("9132700017");
    konten.add("9132700025");
    konten.add("9132700033");
    konten.add("9132700041");
    konten.add("9133200700");
    konten.add("9133200735");
    konten.add("9133200743");
    konten.add("9133200751");
    konten.add("9133200786");
    konten.add("9133200808");
    konten.add("9133200816");
    konten.add("9133200824");
    konten.add("9133200832");
    konten.add("9136700003");
    konten.add("9177300010");
    konten.add("9177300060");
    konten.add("9198100002");
    konten.add("9198200007");
    konten.add("9198200104");
    konten.add("9198300001");
    konten.add("9331300141");
    konten.add("9331300150");
    konten.add("9331401010");
    konten.add("9331401061");
    konten.add("9349010000");
    konten.add("9349100000");
    konten.add("9360500001");
    konten.add("9364902007");
    konten.add("9366101001");
    konten.add("9366104000");
    konten.add("9370620030");
    konten.add("9370620080");
    konten.add("9371900010");
    konten.add("9373600005");
    konten.add("9402900021");
    konten.add("9605110000");
    konten.add("9614001000");
    konten.add("9615000016");
    konten.add("9615010003");
    konten.add("9618500036");
    konten.add("9631020000");
    konten.add("9632600051");
    konten.add("9632600060");
    konten.add("9635000012");
    konten.add("9635000020");
    konten.add("9635701002");
    konten.add("9636010003");
    konten.add("9636013002");
    konten.add("9636016001");
    konten.add("9636018004");
    konten.add("9636019000");
    konten.add("9636022001");
    konten.add("9636024004");
    konten.add("9636025000");
    konten.add("9636027003");
    konten.add("9636028000");
    konten.add("9636045001");
    konten.add("9636048000");
    konten.add("9636051001");
    konten.add("9636053004");
    konten.add("9636120003");
    konten.add("9636140004");
    konten.add("9636150000");
    konten.add("9636320002");
    konten.add("9636700000");
    konten.add("9638120000");
    konten.add("9639401100");
    konten.add("9639801001");
    konten.add("9670010004");
    konten.add("9680610000");
    konten.add("9705010002");
    konten.add("9705403004");
    konten.add("9705404000");
    konten.add("9705509996");
    konten.add("9707901001");
    konten.add("9736010000");
    konten.add("9780100050");
    konten.add("9791000030");
    konten.add("9990001003");
    konten.add("9990001100");
    konten.add("9990002000");
    konten.add("9990004002");
    konten.add("9991020001");
    konten.add("9991040002");
    konten.add("9991060003");
    konten.add("9999999993");
    konten.add("9999999994");
    konten.add("9999999995");
    konten.add("9999999996");
    konten.add("9999999997");
    konten.add("9999999998");
    konten.add("9999999999");
    if (konten.contains(konto))
    {
      return ibanRegel_000000(blz, konto, land, null, true);
    }
    return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
  }

  /**
   * UniCredit Bank AG
   */
  public static IBANRet ibanRegel_003101(String blz, String konto, SEPALand land)
      throws Exception
  {
    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (!ret.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }

    if (b.getHinweisloeschung().equals("1"))
    {
      if (konto.length() != 10)
      {
        return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
      }
      String ersatzblz = ExHypoBehandlung.getErsatzBLZ(blz, konto, land);
      if (ersatzblz != null)
      {
        IBANRet ir = ibanRegel_000000(ersatzblz, konto, land);
        if (ir.getCode().equals(IBANCode.GUELTIG))
        {
          ir.setCode(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT);
        }
        return ir;
      }
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }
    else
    {
      return ibanRegel_003200(blz, konto, land);
    }
  }

  /**
   * UniCredit Bank AG
   */
  public static IBANRet ibanRegel_003200(String blz, String konto, SEPALand land)
      throws Exception
  {
    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (!ret.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }

    if (b.getHinweisloeschung().equals("0"))
    {
      if (konto.length() == 10)
      {
        String ersatzblz = ExHypoBehandlung.getErsatzBLZ(blz, konto, land);
        if (ersatzblz != null)
        {
          IBANRet ir = ibanRegel_000000(ersatzblz, konto, land);
          if (!blz.equals(ersatzblz))
          {
            ir.setCode(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT);
          }
          return ir;
        }
      }
      else
      {
        BigInteger blzbi = new BigInteger(konto);
        if (blzbi.compareTo(new BigInteger("800000000")) >= 0
            && blzbi.compareTo(new BigInteger("899999999")) <= 0)
        {
          return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
        }
        else
        {
          return ibanRegel_000000(blz, konto, land);
        }
      }

      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }
    else
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);// TODO 0032
    }
  }

  /**
   * UniCredit Bank AG
   */
  public static IBANRet ibanRegel_003301(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (!blz.equals("70020270"))
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("22222", "5803435253");
    spendenkonten.put("1111111", "39908140");
    spendenkonten.put("94", "2711931");
    spendenkonten.put("7777777", "5800522694");
    spendenkonten.put("55555", "5801800000");

    String _konto = spendenkonten.get(konto);
    if (_konto != null)
    {
      konto = _konto;
    }

    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (!ret.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }

    if (b.getHinweisloeschung().equals("0"))
    {
      if (konto.length() == 10)
      {
        String ersatzblz = ExHypoBehandlung.getErsatzBLZ(blz, konto, land);
        if (ersatzblz != null)
        {
          IBANRet ir = ibanRegel_000000(ersatzblz, konto, land);
          if (!blz.equals(ersatzblz))
          {
            ir.setCode(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT);
          }
          return ir;
        }
      }
      else
      {
        return ibanRegel_000000(blz, konto, land);
      }
    }
    return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
  }

  /**
   * UniCredit Bank AG
   */
  public static IBANRet ibanRegel_003400(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (!blz.equals("60020290"))
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("500500500", "4340111112");
    spendenkonten.put("502", "4340118001");

    String _konto = spendenkonten.get(konto);
    if (_konto != null)
    {
      konto = _konto;
    }

    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (!ret.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }

    if (b.getHinweisloeschung().equals("0"))
    {
      if (konto.length() == 10)
      {
        String ersatzblz = ExHypoBehandlung.getErsatzBLZ(blz, konto, land);
        if (ersatzblz != null)
        {
          IBANRet ir = ibanRegel_000000(ersatzblz, konto, land);
          if (!blz.equals(ersatzblz))
          {
            ir.setCode(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT);
          }
          return ir;
        }
      }
      else
      {
        BigInteger blzbi = new BigInteger(konto);
        if (blzbi.compareTo(new BigInteger("800000000")) >= 0
            && blzbi.compareTo(new BigInteger("899999999")) <= 0)
        {
          return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
        }
        else
        {
          return ibanRegel_000000(blz, konto, land);
        }
      }
    }
    return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
  }

  /**
   * UniCredit Bank AG
   */
  public static IBANRet ibanRegel_003501(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (!blz.equals("79020076"))
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("9696", "1490196966");

    String _konto = spendenkonten.get(konto);
    if (_konto != null)
    {
      konto = _konto;
    }

    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (!ret.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }

    if (b.getHinweisloeschung().equals("0"))
    {
      if (konto.length() == 10)
      {
        String ersatzblz = ExHypoBehandlung.getErsatzBLZ(blz, konto, land);
        if (ersatzblz != null)
        {
          IBANRet ir = ibanRegel_000000(ersatzblz, konto, land);
          if (!blz.equals(ersatzblz))
          {
            ir.setCode(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT);
          }
          return ir;
        }
      }
      else
      {
        BigInteger blzbi = new BigInteger(konto);
        if (blzbi.compareTo(new BigInteger("800000000")) >= 0
            && blzbi.compareTo(new BigInteger("899999999")) <= 0)
        {
          return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
        }
        else
        {
          return ibanRegel_000000(blz, konto, land);
        }
      }
    }
    return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
  }

  /**
   * HSH Nordbank AG, Hamburg und Kiel
   */
  public static IBANRet ibanRegel_003600(String blz, String konto, SEPALand land)
      throws Exception
  {
    BigInteger kontobi = new BigInteger(konto);
    if ((kontobi.compareTo(new BigInteger("0000000000")) >= 0 && kontobi
        .compareTo(new BigInteger("0000099999")) <= 0)
        || (kontobi.compareTo(new BigInteger("0000900000")) >= 0 && kontobi
            .compareTo(new BigInteger("0029999999")) <= 0)
        || (kontobi.compareTo(new BigInteger("0060000000")) >= 0 && kontobi
            .compareTo(new BigInteger("0099999999")) <= 0)
        || (kontobi.compareTo(new BigInteger("0900000000")) >= 0 && kontobi
            .compareTo(new BigInteger("0999999999")) <= 0)
        || (kontobi.compareTo(new BigInteger("2000000000")) >= 0 && kontobi
            .compareTo(new BigInteger("2999999999")) <= 0)
        || (kontobi.compareTo(new BigInteger("7100000000")) >= 0 && kontobi
            .compareTo(new BigInteger("8499999999")) <= 0)
        || (kontobi.compareTo(new BigInteger("8600000000")) >= 0 && kontobi
            .compareTo(new BigInteger("8999999999")) <= 0))
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }

    Bank b = Banken.getBankByBLZ(blz);
    PZRet ret = KontoPruefziffernrechnung.checkAccountCRC(
        b.getPruefziffernmethode(), blz, konto);
    if (!ret.isValid())
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }
    blz = "21050000";
    konto = truncateLeadingZeros(konto);
    if (konto.length() == 6 && !konto.startsWith("0") && !konto.startsWith("9"))
    {
      return ibanRegel_000000(blz, konto + "000", land);
    }
    else if (konto.length() == 8
        && (konto.startsWith("3") || konto.startsWith("4") || konto
            .startsWith("5")))
    {
      return ibanRegel_000000(blz, konto, land);
    }
    else if (konto.length() == 9 || konto.length() == 10)
    {
      return ibanRegel_000000(blz, konto, land);
    }
    return null;
  }

  /**
   * The Bank of Tokyo-Mitsubishi UFJ, Ltd.
   */
  public static IBANRet ibanRegel_003700(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "30010700";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Ostfriesische Volksbank eG
   */
  public static IBANRet ibanRegel_003800(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("26691213"))
    {
      blz = "28590075";
    }
    else if (blz.equals("28591579"))
    {
      blz = "28590075";
    }
    else if (blz.equals("25090300"))
    {
      blz = "28590075";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Oldenburgische Landesbank AG
   */
  public static IBANRet ibanRegel_003900(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashSet<String> ersatzblz = new HashSet<String>();
    ersatzblz.add("25621327");
    ersatzblz.add("26520017");
    ersatzblz.add("26521703");
    ersatzblz.add("26522319");
    ersatzblz.add("26620010");
    ersatzblz.add("26621413");
    ersatzblz.add("26720028");
    ersatzblz.add("28021002");
    ersatzblz.add("28021301");
    ersatzblz.add("28021504");
    ersatzblz.add("28021623");
    ersatzblz.add("28021705");
    ersatzblz.add("28021906");
    ersatzblz.add("28022015");
    ersatzblz.add("28022412");
    ersatzblz.add("28022511");
    ersatzblz.add("28022620");
    ersatzblz.add("28022822");
    ersatzblz.add("28023224");
    ersatzblz.add("28023325");
    ersatzblz.add("28220026");
    ersatzblz.add("28222208");
    ersatzblz.add("28222621");
    ersatzblz.add("28320014");
    ersatzblz.add("28321816");
    ersatzblz.add("28420007");
    ersatzblz.add("28421030");
    ersatzblz.add("28520009");
    ersatzblz.add("28521518");
    ersatzblz.add("29121731");
    if (ersatzblz.contains(blz))
    {
      blz = "28020050";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Sparkasse Staufen-Breisach
   */
  public static IBANRet ibanRegel_004001(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "68052328";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Bausparkasse Schwäbisch Hall AG
   */
  public static IBANRet ibanRegel_004100(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "50060400";
    konto = "11404";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Deutsche Bundesbank
   */
  public static IBANRet ibanRegel_004200(String blz, String konto, SEPALand land)
      throws Exception
  {
    konto = truncateLeadingZeros(konto);

    BigInteger endnummer = new BigInteger(konto.substring(3));
    if (endnummer.compareTo(new BigInteger("0")) >= 0
        && endnummer.compareTo(new BigInteger("999")) <= 0)
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }

    if (konto.startsWith("50462") || konto.startsWith("50463")
        || konto.startsWith("50469"))
    {
      return ibanRegel_000000(blz, konto, land);
    }
    if (konto.substring(3, 4).equals("0"))
    {
      return ibanRegel_000000(blz, konto, land);
    }
    return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
  }

  /**
   * Sparkasse Pforzheim Calw
   */
  public static IBANRet ibanRegel_004300(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("60651070"))
    {
      blz = "66650085";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Sparkasse Pforzheim Calw
   */
  public static IBANRet ibanRegel_004301(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("60651070"))
    {
      blz = "66650085";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Sparkasse Freiburg
   */
  public static IBANRet ibanRegel_004400(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (konto.equals("202"))
    {
      konto = "2282022";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * SEB AG
   */
  public static IBANRet ibanRegel_004500(String blz, String konto, SEPALand land)
      throws Exception
  {
    return ibanRegel_000000(blz, konto, land, "ESSEDE5FXXX", false);
  }

  /**
   * SEB AG
   */
  public static IBANRet ibanRegel_004501(String blz, String konto, SEPALand land)
      throws Exception
  {
    return ibanRegel_000000(blz, konto, land, "ESSEDE5FXXX", false);
  }

  /**
   * Santander Consumer Bank
   */
  public static IBANRet ibanRegel_004600(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "31010833";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Santander Consumer Bank
   */
  public static IBANRet ibanRegel_004700(String blz, String konto, SEPALand land)
      throws Exception
  {
    konto = truncateLeadingZeros(konto);
    if (konto.length() == 8)
    {
      konto = konto + "00";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Santander Consumer Bank
   */
  public static IBANRet ibanRegel_004800(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("10120800") || blz.equals("27010200")
        || blz.equals("60020300"))
    {
      blz = "36010200";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * WGZ Bank
   */
  public static IBANRet ibanRegel_004900(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (!blz.equals("30060010") && !blz.equals("40060000")
        && !blz.equals("57060000"))
    {
      return new IBANRet(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG);
    }
    konto = truncateLeadingZeros(konto);
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("36", "0002310113");
    spendenkonten.put("936", "0002310113");
    spendenkonten.put("999", "0001310113");
    spendenkonten.put("6060", "0000160602");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }

    _konto = fillup(_konto);
    if (!_konto.substring(4, 5).equals("9"))
    {
      return ibanRegel_000000(blz, _konto, land);
    }
    else
    {
      _konto = _konto.substring(4) + _konto.substring(0, 4);
      return ibanRegel_000000(blz, _konto, land, null, true);
    }
  }

  /**
   * Sparkasse LeerWittmund
   */
  public static IBANRet ibanRegel_005000(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("28252760"))
    {
      blz = "28550000";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Landesbank Baden-Württemberg / Baden-Württembergische Bank
   */
  public static IBANRet ibanRegel_005100(String blz, String konto, SEPALand land)
      throws Exception
  {
    konto = truncateLeadingZeros(konto);
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("0000000333", "7832500881");
    spendenkonten.put("0000000502", "0001108884");
    spendenkonten.put("0500500500", "0005005000");
    spendenkonten.put("0502502502", "0001108884");

    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Landesbank Baden-Württemberg / Baden-Württembergische Bank
   */
  public static IBANRet ibanRegel_005200(String blz, String konto, SEPALand land)
      throws Exception
  {
    HashMap<String, String> ers = new HashMap<String, String>();
    ers.put("672200205308810004", "600501010002662604");
    ers.put("672200205308810000", "600501010002659600");
    ers.put("670200205203145700", "600501017496510994");
    ers.put("694210206208908100", "600501017481501341");
    ers.put("666200204840404000", "600501017498502663");
    ers.put("641200301201200100", "600501017477501214");
    ers.put("640200301408050100", "600501017469534505");
    ers.put("630201301112156300", "600501010004475655");
    ers.put("620300507002703200", "600501017406501175");
    ers.put("692200206402145400", "600501017485500252");
    String x = ers.get(blz + konto);
    if (x != null)
    {
      blz = x.substring(0, 8);
      konto = x.substring(8);
      return ibanRegel_000000(blz, konto, land);
    }
    else
    {
      return ibanRegel_000100(blz, konto, land);
    }
  }

  /**
   * Landesbank Baden-Württemberg / Baden-Württembergische Bank
   */
  public static IBANRet ibanRegel_005300(String blz, String konto, SEPALand land)
      throws Exception
  {
    konto = truncateLeadingZeros(konto);
    HashMap<String, String> ers = new HashMap<String, String>();
    ers.put("5505000035000", "600501017401555913");
    ers.put("55050000119345106", "600501017401555906");
    ers.put("55050000908", "600501017401507480");
    ers.put("55050000901", "600501017401507497");
    ers.put("55050000910", "600501017401507466");
    ers.put("5505000035100", "600501017401555913");
    ers.put("55050000902", "600501017401507473");
    ers.put("5505000044000", "600501017401555872");
    ers.put("55050000110132511", "600501017401550530");
    ers.put("55050000110024270", "600501017401501266");
    ers.put("550500003500", "600501017401555913");
    ers.put("55050000110050002", "600501017401502234");
    ers.put("5505000055020100", "600501017401555872");
    ers.put("55050000110149226", "600501017401512248");
    ers.put("600200301047444300", "600501017871538395");
    ers.put("600200301040748400", "600501010001366705");
    ers.put("600200301000617900", "600501010002009906");
    ers.put("600200301003340500", "600501010002001155");
    ers.put("600200301002999900", "600501010002588991");
    ers.put("600200301004184600", "600501017871513509");
    ers.put("600200301000919900", "600501017871531505");
    ers.put("600200301054290000", "600501017871521216");
    ers.put("600500001523", "600501010001364934");
    ers.put("600500002811", "600501010001367450");
    ers.put("600500002502", "600501010001366705");
    ers.put("60050000250412", "600501017402051588");
    ers.put("600500003009", "600501010001367924");
    ers.put("600500004596", "600501010001372809");
    ers.put("600500003080", "600501010002009906");
    ers.put("600500001029204", "600501010002782254");
    ers.put("600500003002", "600501010001367924");
    ers.put("60050000123456", "600501010001362826");
    ers.put("600500002535", "600501010001119897");
    ers.put("600500005500", "600501010001375703");
    ers.put("660200204002401000", "600501017495500967");
    ers.put("660200204000604100", "600501010002810030");
    ers.put("660200204002015800", "600501017495530102");
    ers.put("660200204003746700", "600501017495501485");
    ers.put("6605000086567", "600501010001364934");
    ers.put("6605000086345", "600501017402046641");
    ers.put("6605000085304", "600501017402045439");
    ers.put("6605000085990", "600501017402051588");
    ers.put("860500001016", "600501017461500128");
    ers.put("860500003535", "600501017461505611");
    ers.put("860500002020", "600501017461500018");
    ers.put("860500004394", "60050107461505714");
    String x = ers.get(blz + konto);
    if (x != null)
    {
      blz = x.substring(0, 8);
      konto = x.substring(8);
    }
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * Landesbank Baden-Württemberg / Baden-Württembergische Bank
   */
  public static IBANRet ibanRegel_005400(String blz, String konto, SEPALand land)
      throws Exception
  {
    konto = truncateLeadingZeros(konto);
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("500", "500500");
    spendenkonten.put("502", "502502");
    spendenkonten.put("18067", "180670");
    spendenkonten.put("484848", "484849");
    spendenkonten.put("636306", "63606");
    spendenkonten.put("760440", "160440");
    spendenkonten.put("1018413", "10108413");
    spendenkonten.put("2601577", "26015776");
    spendenkonten.put("5005000", "500500");
    spendenkonten.put("10796740", "10796743");
    spendenkonten.put("11796740", "11796743");
    spendenkonten.put("12796740", "12796743");
    spendenkonten.put("13796740", "13796743");
    spendenkonten.put("14796740", "14796743");
    spendenkonten.put("15796740", "15796743");
    spendenkonten.put("16307000", "163107000");
    spendenkonten.put("16610700", "166107000");
    spendenkonten.put("16796740", "16796743");
    spendenkonten.put("17796740", "17796743");
    spendenkonten.put("18796740", "18796743");
    spendenkonten.put("19796740", "19796743");
    spendenkonten.put("20796740", "20796743");
    spendenkonten.put("21796740", "21796743");
    spendenkonten.put("22796740", "22796743");
    spendenkonten.put("23796740", "23796743");
    spendenkonten.put("24796740", "24796743");
    spendenkonten.put("25796740", "25796743");
    spendenkonten.put("26610700", "266107000");
    spendenkonten.put("26796740", "26796743");
    spendenkonten.put("27796740", "27796743");
    spendenkonten.put("28796740", "28796743");
    spendenkonten.put("29796740", "29796743");
    spendenkonten.put("45796740", "45796743");
    spendenkonten.put("50796740", "50796743");
    spendenkonten.put("51796740", "51796743");
    spendenkonten.put("52796740", "52796743");
    spendenkonten.put("53796740", "53796743");
    spendenkonten.put("54796740", "54796743");
    spendenkonten.put("55796740", "55796743");
    spendenkonten.put("56796740", "56796743");
    spendenkonten.put("57796740", "57796743");
    spendenkonten.put("58796740", "58796743");
    spendenkonten.put("59796740", "59796743");
    spendenkonten.put("60796740", "60796743");
    spendenkonten.put("61796740", "61796743");
    spendenkonten.put("62796740", "62796743");
    spendenkonten.put("63796740", "63796743");
    spendenkonten.put("64796740", "64796743");
    spendenkonten.put("65796740", "65796743");
    spendenkonten.put("66796740", "66796743");
    spendenkonten.put("67796740", "67796743");
    spendenkonten.put("68796740", "68796743");
    spendenkonten.put("69796740", "69796743");
    spendenkonten.put("1761070000", "176107000");
    spendenkonten.put("2210531180", "201053180");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    if (_konto.length() != 10)
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Evangelische Darlehnsgenossenschaft eG
   */
  public static IBANRet ibanRegel_005401(String blz, String konto, SEPALand land)
      throws Exception
  {
    konto = truncateLeadingZeros(konto);
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("500", "500500");
    spendenkonten.put("502", "502502");
    spendenkonten.put("18067", "180670");
    spendenkonten.put("484848", "484849");
    spendenkonten.put("636306", "63606");
    spendenkonten.put("760440", "160440");
    spendenkonten.put("1018413", "10108413");
    spendenkonten.put("2601577", "26015776");
    spendenkonten.put("5005000", "500500");
    spendenkonten.put("10796740", "10796743");
    spendenkonten.put("11796740", "11796743");
    spendenkonten.put("12796740", "12796743");
    spendenkonten.put("13796740", "13796743");
    spendenkonten.put("14796740", "14796743");
    spendenkonten.put("15796740", "15796743");
    spendenkonten.put("16307000", "163107000");
    spendenkonten.put("16610700", "166107000");
    spendenkonten.put("16796740", "16796743");
    spendenkonten.put("17796740", "17796743");
    spendenkonten.put("18796740", "18796743");
    spendenkonten.put("19796740", "19796743");
    spendenkonten.put("20796740", "20796743");
    spendenkonten.put("21796740", "21796743");
    spendenkonten.put("22796740", "22796743");
    spendenkonten.put("23796740", "23796743");
    spendenkonten.put("24796740", "24796743");
    spendenkonten.put("25796740", "25796743");
    spendenkonten.put("26610700", "266107000");
    spendenkonten.put("26796740", "26796743");
    spendenkonten.put("27796740", "27796743");
    spendenkonten.put("28796740", "28796743");
    spendenkonten.put("29796740", "29796743");
    spendenkonten.put("45796740", "45796743");
    spendenkonten.put("50796740", "50796743");
    spendenkonten.put("51796740", "51796743");
    spendenkonten.put("52796740", "52796743");
    spendenkonten.put("53796740", "53796743");
    spendenkonten.put("54796740", "54796743");
    spendenkonten.put("55796740", "55796743");
    spendenkonten.put("56796740", "56796743");
    spendenkonten.put("57796740", "57796743");
    spendenkonten.put("58796740", "58796743");
    spendenkonten.put("59796740", "59796743");
    spendenkonten.put("60796740", "60796743");
    spendenkonten.put("61796740", "61796743");
    spendenkonten.put("62796740", "62796743");
    spendenkonten.put("63796740", "63796743");
    spendenkonten.put("64796740", "64796743");
    spendenkonten.put("65796740", "65796743");
    spendenkonten.put("66796740", "66796743");
    spendenkonten.put("67796740", "67796743");
    spendenkonten.put("68796740", "68796743");
    spendenkonten.put("69796740", "69796743");
    spendenkonten.put("1761070000", "176107000");
    spendenkonten.put("2210531180", "201053180");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    if (_konto.length() != 10)
    {
      return new IBANRet(IBANCode.AUFBAUKONTONUMMERFALSCH);
    }
    IBANRet ret = ibanRegel_000000(blz, _konto, land);
    if (ret.getCode() == IBANCode.AUFBAUKONTONUMMERFALSCH)
    {
      ArrayList<String> konten = new ArrayList<String>();
      konten.add("624044");
      konten.add("4063060");
      konten.add("20111908");
      konten.add("20211908");
      konten.add("20311908");
      konten.add("20411908");
      konten.add("20511908");
      konten.add("20611908");
      konten.add("20711908");
      konten.add("20811908");
      konten.add("20911908");
      konten.add("21111908");
      konten.add("21211908");
      konten.add("21311908");
      konten.add("21411908");
      konten.add("21511908");
      konten.add("21611908");
      konten.add("21711908");
      konten.add("21811908");
      konten.add("21911908");
      konten.add("22111908");
      konten.add("22211908");
      konten.add("22311908");
      konten.add("22411908");
      konten.add("22511908");
      konten.add("22611908");
      konten.add("46211991");
      konten.add("50111908");
      konten.add("50211908");
      konten.add("50311908");
      konten.add("50411908");
      konten.add("50511908");
      konten.add("50611908");
      konten.add("50711908");
      konten.add("50811908");
      konten.add("50911908");
      konten.add("51111908");
      konten.add("51111991");
      konten.add("51211908");
      konten.add("51211991");
      konten.add("51311908");
      konten.add("51411908");
      konten.add("51511908");
      konten.add("51611908");
      konten.add("51711908");
      konten.add("51811908");
      konten.add("51911908");
      konten.add("52111908");
      konten.add("52111991");
      konten.add("52211908");
      konten.add("52211991");
      konten.add("52311908");
      konten.add("52411908");
      konten.add("52511908");
      konten.add("52611908");
      konten.add("52711908");
      konten.add("52811908");
      konten.add("52911908");
      konten.add("53111908");
      konten.add("53211908");
      konten.add("53311908");
      konten.add("57111908");
      konten.add("58111908");
      konten.add("58211908");
      konten.add("58311908");
      konten.add("58411908");
      konten.add("58511908");
      konten.add("80111908");
      konten.add("80211908");
      konten.add("80311908");
      konten.add("80411908");
      konten.add("80511908");
      konten.add("80611908");
      konten.add("80711908");
      konten.add("80811908");
      konten.add("80911908");
      konten.add("81111908");
      konten.add("81211908");
      konten.add("81311908");
      konten.add("81411908");
      konten.add("81511908");
      konten.add("81611908");
      konten.add("81711908");
      konten.add("81811908");
      konten.add("81911908");
      konten.add("82111908");
      konten.add("82211908");
      konten.add("82311908");
      konten.add("82411908");
      konten.add("82511908");
      konten.add("82611908");
      konten.add("82711908");
      konten.add("82811908");
      konten.add("82911908");
      konten.add("99624044");
      konten.add("300143869");
      if (konten.contains(konto))
      {
        return ibanRegel_000000(blz, _konto, land, "GENODEF1EDG", true);
      }
    }
    return ret;
  }

  /**
   * BHW Kreditservice GmbH
   */
  public static IBANRet ibanRegel_005500(String blz, String konto, SEPALand land)
      throws Exception
  {
    blz = "25410200";
    return ibanRegel_000000(blz, konto, land);
  }

  /**
   * SEB AG
   */
  public static IBANRet ibanRegel_005600(String blz, String konto, SEPALand land)
      throws Exception
  {
    konto = truncateLeadingZeros(konto);
    HashMap<String, String> spendenkonten = new HashMap<String, String>();
    spendenkonten.put("36", "1010240003");
    spendenkonten.put("50", "1328506100");
    spendenkonten.put("99", "1826063000");
    spendenkonten.put("110", "1015597802");
    spendenkonten.put("240", "1010240000");
    spendenkonten.put("333", "1011296100");
    spendenkonten.put("555", "1600220800");
    spendenkonten.put("556", "1000556100");
    spendenkonten.put("606", "1967153801");
    spendenkonten.put("700", "1070088000");
    spendenkonten.put("777", "1006015200");
    spendenkonten.put("999", "1010240001");
    spendenkonten.put("1234", "1369152400");
    spendenkonten.put("1313", "1017500000");
    spendenkonten.put("1888", "1241113000");
    spendenkonten.put("1953", "1026500901");
    spendenkonten.put("1998", "1547620500");
    spendenkonten.put("2007", "1026500907");
    spendenkonten.put("4004", "1635100100");
    spendenkonten.put("4444", "1304610900");
    spendenkonten.put("5000", "1395676000");
    String _konto = spendenkonten.get(konto);
    if (_konto == null)
    {
      _konto = konto;
    }
    return ibanRegel_000000(blz, _konto, land);
  }

  /**
   * Badenia Bausparkasse
   */
  public static IBANRet ibanRegel_005700(String blz, String konto, SEPALand land)
      throws Exception
  {
    if (blz.equals("50810900"))
    {
      blz = "66010200";
    }
    return ibanRegel_000000(blz, konto, land);
  }

  private static String truncateLeadingZeros(String konto)
  {
    String _konto = konto;
    while (_konto.startsWith("0"))
    {
      _konto = _konto.substring(1);
    }
    return _konto;
  }

  private static String fillup(String konto)
  {
    String _konto = konto;
    while (_konto.length() < 10)
    {
      _konto = "0" + _konto;
    }
    return _konto;
  }
}