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
import java.util.HashMap;
import java.util.Iterator;

public class SEPALaender
{
  private static ArrayList<SEPALand> laender = new ArrayList<SEPALand>();

  private static HashMap<String, SEPALand> laendermap = new HashMap<String, SEPALand>();
  static
  {
    laender.add(getDeutschland());
    laender.add(getAndorra());
    laender.add(getBelgien());
    laender.add(getBosnienHerzegowina());
    laender.add(getBulgrien());
    laender.add(getDaenemark());
    laender.add(getEstland());
    laender.add(getFinnland());
    laender.add(getFrankreich());
    laender.add(getGibraltar());
    laender.add(getGriechenland());
    laender.add(getGrossbritannien());
    laender.add(getItalien());
    laender.add(getIrland());
    laender.add(getIsland());
    laender.add(getIsrael());
    laender.add(getKroatien());
    laender.add(getLettland());
    laender.add(getLiechtenstein());
    laender.add(getLitauen());
    laender.add(getLuxemburg());
    laender.add(getMalta());
    laender.add(getMauritius());
    laender.add(getMazedonien());
    laender.add(getMonaco());
    laender.add(getMontenegro());
    laender.add(getNiederlande());
    laender.add(getNorwegen());
    laender.add(getOesterreich());
    laender.add(getPolen());
    laender.add(getPortugal());
    laender.add(getRumaenien());
    laender.add(getSanMarino());
    laender.add(getSchweden());
    laender.add(getSchweiz());
    laender.add(getSerbien());
    laender.add(getSlowakei());
    laender.add(getSlowenien());
    laender.add(getSpanien());
    laender.add(getTschechien());
    laender.add(getTuerkei());
    laender.add(getUngarn());

    laender.add(getZypern());

    for (SEPALand land : laender)
    {
      laendermap.put(land.getKennzeichen(), land);
    }
  }

  public static SEPALand getLand(String kennzeichen)
  {
    return laendermap.get(kennzeichen);
  }

  public static ArrayList<SEPALand> getLaender()
  {
    return laender;
  }

  public Iterator<SEPALand> getIterator()
  {
    return laender.iterator();
  }

  private static SEPALand getDeutschland()
  {
    SEPALand deutschland = new SEPALand("DE", "Deutschland", "8!n10!n",
        "DE89370400440532013000");
    deutschland.setBankIdentifierLength(8);
    deutschland.setAccountLength(10);
    return deutschland;
  }

  private static SEPALand getAndorra()
  {
    return new SEPALand("AD", "Andorra", "4!n4!n12!c",
        "AD1200012030200359100100");
  }

  private static SEPALand getBelgien()
  {
    return new SEPALand("BE", "Belgien", "3!n7!n2!n", "BE68539007547034");
  }

  private static SEPALand getBosnienHerzegowina()
  {
    return new SEPALand("BA", "Bosnien-Herzegowina", "3!n3!n8!n2!n",
        "BA391290079401028494");
  }

  private static SEPALand getBulgrien()
  {
    return new SEPALand("BG", "Bulgarien", "4!a4!n2!n8!c",
        "BG80BNBG96611020345678");
  }

  private static SEPALand getDaenemark()
  {
    return new SEPALand("DK", "Dänemark", "4!n9!n1!n", "DK5000400440116243");
  }

  private static SEPALand getEstland()
  {
    return new SEPALand("EE", "Estland", "2!n2!n11!n1!n",
        "EE382200221020145685");
  }

  private static SEPALand getFinnland()
  {
    return new SEPALand("FI", "Finnland", "6!n7!n1!n", "FI2112345600000785");
  }

  private static SEPALand getFrankreich()
  {
    return new SEPALand("FR", "Frankreich", "5!n5!n11!c2!n",
        "FR1420041010050500013M02606");
  }

  private static SEPALand getGibraltar()
  {
    return new SEPALand("GI", "Gibraltar", "4!a15!c", "GI75NWBK000000007099453");
  }

  private static SEPALand getGriechenland()
  {
    return new SEPALand("GR", "Griechenland", "3!n4!n16!c",
        "GR1601101250000000012300695");
  }

  private static SEPALand getGrossbritannien()
  {
    return new SEPALand("GB", "Großbritannien", "4!a6!n8!n",
        "GB29NWBK60161331926819");
  }

  private static SEPALand getIrland()
  {
    return new SEPALand("IE", "Irland", "4!a6!n8!n", "IE29AIBK93115212345678");
  }

  private static SEPALand getIsland()
  {
    return new SEPALand("IS", "Island", "4!n2!n6!n10!n",
        "IS140159260076545510730339");
  }

  private static SEPALand getIsrael()
  {
    return new SEPALand("IL", "Israel", "3!n3!n13!n", "IL620108000000099999999");
  }

  private static SEPALand getItalien()
  {
    return new SEPALand("IT", "Italien", "1!a5!n5!n12!c",
        "IT68D0300203280000400162854");
  }

  private static SEPALand getKroatien()
  {
    return new SEPALand("HR", "Kroatien", "7!n10!n", "HR1210010051863000160");
  }

  private static SEPALand getLettland()
  {
    return new SEPALand("LV", "Lettland", "4!a13!c", "LV80BANK0000435195001");
  }

  private static SEPALand getLiechtenstein()
  {
    return new SEPALand("LI", "Liechtenstein", "5!n12!c",
        "LI21088100002324013AA");
  }

  private static SEPALand getLitauen()
  {
    return new SEPALand("LT", "Litauen", "5!n11!n", "LT121000011101001000");
  }

  private static SEPALand getLuxemburg()
  {
    return new SEPALand("LU", "Luxemburg", "3!n13!c", "LU280019400644750000");
  }

  private static SEPALand getMalta()
  {
    return new SEPALand("MT", "Malta", "4!a5!n18!c",
        "MT84MALT011000012345MTLCAST001S");
  }

  private static SEPALand getMauritius()
  {
    return new SEPALand("MU", "Mauritius", "4!a2!n2!n12!n3!n3!a",
        "MU17BOMM0101101030300200000MUR");
  }

  private static SEPALand getMazedonien()
  {
    return new SEPALand("MK", "Mazedonien", "3!n10!c2!n", "MK07250120000058984");
  }

  private static SEPALand getMonaco()
  {
    return new SEPALand("MC", "Monaco", "5!n5!n11!c2!n",
        "MC1112739000700011111000H79");
  }

  private static SEPALand getMontenegro()
  {
    return new SEPALand("ME", "Montenegro", "3!n13!n2!n",
        "ME25505000012345678951");
  }

  private static SEPALand getNiederlande()
  {
    return new SEPALand("NL", "Niederlande", "4!a10!n", "NL91ABNA0417164300");
  }

  private static SEPALand getNorwegen()
  {
    return new SEPALand("NO", "Norwegen", "4!n6!n1!n", "NO9386011117947");
  }

  private static SEPALand getOesterreich()
  {
    SEPALand oesterreich = new SEPALand("AT", "Österreich", "5!n11!n",
        "AT611904300234573201");
    oesterreich.setBankIdentifierLength(5);
    oesterreich.setAccountLength(11);
    return oesterreich;
  }

  private static SEPALand getPolen()
  {
    return new SEPALand("PL", "Polen", "2!n8!n16!n",
        "PL61109010140000071219812874");
  }

  private static SEPALand getPortugal()
  {
    return new SEPALand("PT", "Portugal", "4!n4!n11!n2!n",
        "PT50000201231234567890154");
  }

  private static SEPALand getRumaenien()
  {
    return new SEPALand("RO", "Rumänien", "4!a16!c", "RO49AAAA1B31007593840000");
  }

  private static SEPALand getSanMarino()
  {
    return new SEPALand("SM", "San Marino", "1!a5!n5!n12!c",
        "SM86U0322509800000000270100");
  }

  private static SEPALand getSchweden()
  {
    return new SEPALand("SE", "Schweden", "3!n16!n1!n",
        "SE4550000000058398257466");
  }

  private static SEPALand getSchweiz()
  {
    return new SEPALand("CH", "Schweiz", "5!n12!c", "CH9300762011623852957");
  }

  private static SEPALand getSerbien()
  {
    return new SEPALand("RS", "Serbien", "3!n13!n2!n", "RS35260005601001611379");
  }

  private static SEPALand getSlowakei()
  {
    return new SEPALand("SK", "Slowakei", "4!n6!n10!n",
        "SK3112000000198742637541");
  }

  private static SEPALand getSlowenien()
  {
    return new SEPALand("SI", "Slowenien", "5!n8!n2!n", "SI56191000000123438");
  }

  private static SEPALand getSpanien()
  {
    return new SEPALand("ES", "Spanien", "4!n4!n1!n1!n10!n",
        "ES9121000418450200051332");
  }

  private static SEPALand getTschechien()
  {
    return new SEPALand("CZ", "Tschechien", "4!n6!n10!n",
        "CZ6508000000192000145399");
  }

  private static SEPALand getTuerkei()
  {
    return new SEPALand("TR", "Türkei", "5!n1!c16!c",
        "TR330006100519786457841326");
  }

  private static SEPALand getUngarn()
  {
    return new SEPALand("HU", "Ungarn", "3!n4!n1!n15!n1!n",
        "HU42117730161111101800000000");
  }

  private static SEPALand getZypern()
  {
    return new SEPALand("CY", "Zypern", "3!n5!n16!c",
        "CY17002001280000001200527600");
  }
}
