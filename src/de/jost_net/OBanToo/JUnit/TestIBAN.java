/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import de.jost_net.OBanToo.SEPA.IBAN;
import de.jost_net.OBanToo.SEPA.IBANCode;
import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.SEPA.SEPAException.Fehler;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIBAN
{

  /**
   * Standardmethode
   */
  @Test
  public void regel000000()
  {
    try
    {
      IBAN iban = new IBAN("1861889", "47853520", "DE");
      assertEquals("DE61478535200001861889", iban.getIBAN());
      assertEquals(IBANCode.PRUEFZIFFERNMETHODEFEHLT, iban.getCode());
    }
    catch (SEPAException e)
    {
      fail();
    }
  }

  /**
   * Standardmethode - Ungültiges Land
   */
  @Test
  public void regel000001()
  {
    try
    {
      new IBAN("EF61478535200001861889");
      fail(); // IBAN muss SEPAException werfen.
    }
    catch (SEPAException e)
    {
      assertEquals(Fehler.UNGUELTIGES_LAND, e.getFehler());
    }
  }

  /**
   * Standardmethode - Ungültige Prüfziffer
   */
  @Test
  public void regel000002()
  {
    try
    {
      new IBAN("DE11478535200001861889");
      fail(); // IBAN muss SEPAException werfen.
    }
    catch (SEPAException e)
    {
      assertEquals("Ungültige IBAN. Prüfziffer falsch. DE11478535200001861889",
          e.getMessage());
    }
  }

  /**
   * keine IBAN-Berechnung
   */
  // @Test
  // public void regel000100()
  // {
  // try
  // {
  // IBAN iban = new IBAN("1", "48050000", "DE");
  // assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
  // }
  // catch (SEPAException e)
  // {
  // fail();
  // }
  // }

  @Test
  public void regel000200()
  {
    try
    {
      IBAN iban = new IBAN("1234560864", "72020700", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      iban = new IBAN("1234560678", "72020700", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      iban = new IBAN("1234560890", "72020700", "DE");
      assertEquals("DE76720207001234560890", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel000300()
  {
    try
    {
      IBAN iban = new IBAN("6161604670", "51010800", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel000400()
  {
    try
    {
      IBAN iban = new IBAN("135", "10050000", "DE");
      assertEquals("DE86100500000990021440", iban.getIBAN());
      iban = new IBAN("1111", "10050000", "DE");
      assertEquals("DE19100500006600012020", iban.getIBAN());
      iban = new IBAN("1900", "10050000", "DE");
      assertEquals("DE73100500000920019005", iban.getIBAN());
      iban = new IBAN("7878", "10050000", "DE");
      assertEquals("DE48100500000780008006", iban.getIBAN());
      iban = new IBAN("8888", "10050000", "DE");
      assertEquals("DE43100500000250030942", iban.getIBAN());
      iban = new IBAN("9595", "10050000", "DE");
      assertEquals("DE60100500001653524703", iban.getIBAN());
      iban = new IBAN("97097", "10050000", "DE");
      assertEquals("DE15100500000013044150", iban.getIBAN());
      iban = new IBAN("112233", "10050000", "DE");
      assertEquals("DE54100500000630025819", iban.getIBAN());
      iban = new IBAN("336666", "10050000", "DE");
      assertEquals("DE22100500006604058903", iban.getIBAN());
      iban = new IBAN("484848", "10050000", "DE");
      assertEquals("DE43100500000920018963", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel000501()
  {
    try
    {
      IBAN iban = new IBAN("732502200", "26580070", "DE");
      assertEquals("DE32265800700732502200", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("7325022", "26580070", "DE");
      assertEquals("DE32265800700732502200", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("8732502200", "26580070", "DE");
      assertEquals("DE60265800708732502200", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("4820379900", "26580070", "DE");
      assertEquals("DE37265800704820379900", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("1814706100", "50080000", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      iban = new IBAN("2814706100", "50080000", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      iban = new IBAN("3814706100", "50080000", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      iban = new IBAN("4814706100", "50080000", "DE");
      assertEquals("DE70500800004814706100", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("5814706100", "50080000", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      iban = new IBAN("6814706100", "50080000", "DE");
      assertEquals("DE77500800006814706100", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("7814706100", "50080000", "DE");
      assertEquals("DE32500800007814706100", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("8814706100", "50080000", "DE");
      assertEquals("DE84500800008814706100", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("9814706100", "50080000", "DE");
      assertEquals("DE39500800009814706100", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("23165400", "50080000", "DE");
      assertEquals("DE42500800000023165400", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("231654", "50080000", "DE");
      assertEquals("DE42500800000023165400", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("4350300", "50080000", "DE");
      assertEquals("DE21500800000004350300", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("43503", "50080000", "DE");
      assertEquals("DE21500800000004350300", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("526400", "50080000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
      iban = new IBAN("526400", "50089400", "DE");
      assertEquals("DE49500894000000526400", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("998761700", "10080000", "DE");
      assertEquals("DE73100800000998761700", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("998761700", "12080000", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      iban = new IBAN("43434280", "26580070", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
      iban = new IBAN("4343428000", "26580070", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
      iban = new IBAN("99021000", "26580070", "DE");
      assertEquals("DE10265800709902100000", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("4217386", "50540028", "DE");
      assertEquals("DE24505400280421738600", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("4217387", "50540028", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
      iban = new IBAN("111198800", "72040046", "DE");
      assertEquals("DE10720400460111198800", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("111198700", "72040046", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
      iban = new IBAN("420086100", "50540028", "DE");
      assertEquals("DE46505400280420086100", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("421573704", "50540028", "DE");
      assertEquals("DE13505400280421573704", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("421679200", "50540028", "DE");
      assertEquals("DE26505400280421679200", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("130023500", "65440087", "DE");
      assertEquals("DE63654400870130023500", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("104414", "23040022", "DE");
      assertEquals("DE29230400220010441400", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("104417", "23040022", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
      iban = new IBAN("40050700", "12040000", "DE");
      assertEquals("DE27120400000040050700", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("101337", "23040022", "DE");
      assertEquals("DE73230400220010133700", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("10503101", "23040022", "DE");
      assertEquals("DE77230400220010503101", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("52065002", "12040000", "DE");
      assertEquals("DE12120400000052065002", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("930125001", "50040000", "DE");
      assertEquals("DE97500400000930125001", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("930125000", "70040041", "DE");
      assertEquals("DE89700400410930125000", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("930125006", "50040000", "DE");
      assertEquals("DE59500400000930125006", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("930125001", "50040033", "DE");
      assertEquals(IBANCode.IBANBERECHNUNGNICHTMOEGLICH, iban.getCode());
      // iban = new IBAN("930125007", "70045050", "DE"); // der Test steht noch
      // in der Liste. In der Änderungshistorik wird allerdings ausgeführt, dass
      // die genannte BLZ geöffnet wurde (1.5)
      // assertEquals("50", iban.getRetCode());
      iban = new IBAN("130023500", "20041111", "DE");
      assertEquals("DE81200411110130023500", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("111", "37080040", "DE");
      assertEquals("DE69370800400215022000", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());
      iban = new IBAN("101010", "50040000", "DE");
      assertEquals("DE46500400000311011100", iban.getIBAN());
      assertEquals("COBADEFFXXX", iban.getBIC());

    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }

  }

  @Test
  public void regel000700()
  {
    try
    {
      IBAN iban = new IBAN("1115", "37050198", "DE");
      assertEquals("DE15370501980000001115", iban.getIBAN());
      iban = new IBAN("23002157", "37050198", "DE");
      assertEquals("DE25370501980023002157", iban.getIBAN());
      iban = new IBAN("18882068", "37050198", "DE");
      assertEquals("DE15370501980018882068", iban.getIBAN());
      iban = new IBAN("1900668508", "37050198", "DE");
      assertEquals("DE57370501981900668508", iban.getIBAN());
      iban = new IBAN("1900730100", "37050198", "DE");
      assertEquals("DE41370501981900730100", iban.getIBAN());
      iban = new IBAN("1900637016", "37050198", "DE");
      assertEquals("DE39370501981900637016", iban.getIBAN());
      iban = new IBAN("23002447", "37050198", "DE");
      assertEquals("DE52370501980023002447", iban.getIBAN());
      iban = new IBAN("17368", "37050198", "DE");
      assertEquals("DE12370501980000017368", iban.getIBAN());
      iban = new IBAN("73999", "37050198", "DE");
      assertEquals("DE83370501980000073999", iban.getIBAN());
      iban = new IBAN("1901335750", "37050198", "DE");
      assertEquals("DE42370501981901335750", iban.getIBAN());
      iban = new IBAN("9992959", "37050198", "DE");
      assertEquals("DE22370501980009992959", iban.getIBAN());
      iban = new IBAN("1901693331", "37050198", "DE");
      assertEquals("DE56370501981901693331", iban.getIBAN());
      iban = new IBAN("1900399856", "37050198", "DE");
      assertEquals("DE98370501981900399856", iban.getIBAN());
      iban = new IBAN("34407379", "37050198", "DE");
      assertEquals("DE81370501980034407379", iban.getIBAN());
      iban = new IBAN("1900480466", "37050198", "DE");
      assertEquals("DE17370501981900480466", iban.getIBAN());
      iban = new IBAN("57762957", "37050198", "DE");
      assertEquals("DE64370501980057762957", iban.getIBAN());
      iban = new IBAN("2222222", "37050198", "DE");
      assertEquals("DE85370501980002222222", iban.getIBAN());
      iban = new IBAN("9992959", "37050198", "DE");
      assertEquals("DE22370501980009992959", iban.getIBAN());
      iban = new IBAN("33217", "37050198", "DE");
      assertEquals("DE53370501980000033217", iban.getIBAN());
      iban = new IBAN("92817", "37050198", "DE");
      assertEquals("DE83370501980000092817", iban.getIBAN());
      iban = new IBAN("91025", "37050198", "DE");
      assertEquals("DE64370501980000091025", iban.getIBAN());
      iban = new IBAN("90944", "37050198", "DE");
      assertEquals("DE20370501980000090944", iban.getIBAN());
      iban = new IBAN("5602024", "37050198", "DE");
      assertEquals("DE24370501980005602024", iban.getIBAN());
      iban = new IBAN("9992959", "37050198", "DE");
      assertEquals("DE22370501980009992959", iban.getIBAN());
      iban = new IBAN("2222222", "37050198", "DE");
      assertEquals("DE85370501980002222222", iban.getIBAN());
      iban = new IBAN("38901", "37050198", "DE");
      assertEquals("DE39370501980000038901", iban.getIBAN());
      iban = new IBAN("43597665", "37050198", "DE");
      assertEquals("DE96370501980043597665", iban.getIBAN());
      iban = new IBAN("15002223", "37050198", "DE");
      assertEquals("DE98370501980015002223", iban.getIBAN());
      iban = new IBAN("57762957", "37050198", "DE");
      assertEquals("DE64370501980057762957", iban.getIBAN());
      iban = new IBAN("2222222", "37050198", "DE");
      assertEquals("DE85370501980002222222", iban.getIBAN());
      iban = new IBAN("1901783868", "37050198", "DE");
      assertEquals("DE54370501981901783868", iban.getIBAN());
      iban = new IBAN("2222222", "37050198", "DE");
      assertEquals("DE85370501980002222222", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }

  }

  @Test
  public void regel000800()
  {
    try
    {
      IBAN iban = new IBAN("38000", "50020200", "DE");
      assertEquals("DE80500202000000038000", iban.getIBAN());
      assertEquals("BHFBDEFF500", iban.getBIC());
      iban = new IBAN("30009963", "51020000", "DE");
      assertEquals("DE46500202000030009963", iban.getIBAN());
      assertEquals("BHFBDEFF500", iban.getBIC());
      iban = new IBAN("40033086", "30020500", "DE");
      assertEquals("DE02500202000040033086", iban.getIBAN());
      assertEquals("BHFBDEFF500", iban.getBIC());
      iban = new IBAN("50017409", "20120200", "DE");
      assertEquals("DE55500202000050017409", iban.getIBAN());
      assertEquals("BHFBDEFF500", iban.getBIC());
      iban = new IBAN("55036107", "70220200", "DE");
      assertEquals("DE38500202000055036107", iban.getIBAN());
      assertEquals("BHFBDEFF500", iban.getBIC());
      iban = new IBAN("70049754", "10020200", "DE");
      assertEquals("DE98500202000070049754", iban.getIBAN());
      assertEquals("BHFBDEFF500", iban.getBIC());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }

  }

  @Test
  public void regel000900()
  {
    try
    {
      IBAN iban = new IBAN("1116232594", "68351976", "DE");
      assertEquals("DE03683515573047232594", iban.getIBAN());
      assertEquals("SOLADES1SFH", iban.getBIC());
      iban = new IBAN("0016005845", "68351976", "DE");
      assertEquals("DE10683515570016005845", iban.getIBAN());
      assertEquals("SOLADES1SFH", iban.getBIC());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel001000()
  {
    try
    {
      IBAN iban = new IBAN("2000", "50050201", "DE");
      assertEquals("DE42500502010000222000", iban.getIBAN());
      iban = new IBAN("800000", "50050201", "DE");
      assertEquals("DE89500502010000180802", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel001100()
  {
    try
    {
      IBAN iban = new IBAN("1000", "32050000", "DE");
      assertEquals("DE44320500000008010001", iban.getIBAN());
      iban = new IBAN("47800", "32050000", "DE");
      assertEquals("DE36320500000000047803", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel001201()
  {
    try
    {
      IBAN iban = new IBAN("5000002096", "50850049", "DE");
      assertEquals("DE95500500005000002096", iban.getIBAN());
      assertEquals("HELADEFFXXX", iban.getBIC());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel001301()
  {
    try
    {
      IBAN iban = new IBAN("60624", "40050000", "DE");
      assertEquals("DE15300500000000060624", iban.getIBAN());
      assertEquals("WELADEDDXXX", iban.getBIC());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel001900()
  {
    try
    {
      IBAN iban = new IBAN("20475000", "50130100", "DE");
      assertEquals("DE82501203830020475000", iban.getIBAN());
      assertEquals("DELBDE33XXX", iban.getBIC());
    }
    catch (SEPAException e)
    {
      System.out.println(e.getFehler());
      fail();
    }
  }

  @Test
  public void regel002001_01()
  {
    try
    {
      IBAN iban = new IBAN("38150900", "10070000", "DE");
      assertEquals("DE82100700000038150900", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_02()
  {
    try
    {
      IBAN iban = new IBAN("600103660", "10070000", "DE");
      assertEquals("DE42100700000600103660", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_03()
  {
    try
    {
      IBAN iban = new IBAN("39101181", "10070000", "DE");
      assertEquals("DE62100700000039101181", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel0020_04()
  {
    try
    {
      IBAN iban = new IBAN("117", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_05()
  {
    try
    {
      IBAN iban = new IBAN("500", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_06()
  {
    try
    {
      IBAN iban = new IBAN("1800", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_07()
  {
    try
    {
      IBAN iban = new IBAN("56002", "10070000", "DE");
      assertEquals("DE94100700000005600200", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_08()
  {
    try
    {
      IBAN iban = new IBAN("752691", "10070000", "DE");
      assertEquals("DE72100700000075269100", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_09()
  {
    try
    {
      IBAN iban = new IBAN("3700246", "10070000", "DE");
      assertEquals("DE14100700000003700246", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_10()
  {
    try
    {
      IBAN iban = new IBAN("6723143", "10070000", "DE");
      assertEquals("DE20100700000006723143", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_11()
  {
    try
    {
      IBAN iban = new IBAN("5719083", "10070000", "DE");
      assertEquals("DE41100700000571908300", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_12()
  {
    try
    {
      IBAN iban = new IBAN("8007759", "10070000", "DE");
      assertEquals("DE96100700000800775900", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_13()
  {
    try
    {
      IBAN iban = new IBAN("3500022", "10070000", "DE");
      assertEquals("DE72100700000350002200", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_14()
  {
    try
    {
      IBAN iban = new IBAN("9000043", "10070000", "DE");
      assertEquals("DE68100700000900004300", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_15()
  {
    try
    {
      IBAN iban = new IBAN("94012341", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_16()
  {
    try
    {
      IBAN iban = new IBAN("123456700", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_17()
  {
    try
    {
      IBAN iban = new IBAN("5073321010", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_18()
  {
    try
    {
      IBAN iban = new IBAN("1415900000", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_19()
  {
    try
    {
      IBAN iban = new IBAN("1000300004", "10070000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_20()
  {
    try
    {
      IBAN iban = new IBAN("94012341", "76026000", "DE");
      assertEquals(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_21()
  {
    try
    {
      IBAN iban = new IBAN("5073321010", "76026000", "DE");
      assertEquals(IBANCode.KOMBINATIONBLZKONTOUNZULAESSIG, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_22()
  {
    try
    {
      IBAN iban = new IBAN("1234517892", "76026000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002001_23()
  {
    try
    {
      IBAN iban = new IBAN("987614325", "76026000", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002101_01()
  {
    try
    {
      IBAN iban = new IBAN("305200", "35020030", "DE");
      assertEquals("DE81360200300000305200", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002101_02()
  {
    try
    {
      IBAN iban = new IBAN("900826", "36220030", "DE");
      assertEquals("DE03360200300000900826", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002101_03()
  {
    try
    {
      IBAN iban = new IBAN("705020", "36520030", "DE");
      assertEquals("DE71360200300000705020", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002101_04()
  {
    try
    {
      IBAN iban = new IBAN("9197354", "36020030", "DE");
      assertEquals("DE18360200300009197354", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002500_01()
  {
    try
    {
      IBAN iban = new IBAN("2777939", "64150182", "DE");
      assertEquals("DE81600501010002777939", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002500_02()
  {
    try
    {
      IBAN iban = new IBAN("7893500686", "64450288", "DE");
      assertEquals("DE80600501017893500686", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002600_01()
  {
    try
    {
      IBAN iban = new IBAN("55111", "35060190", "DE");
      assertEquals("DE21350601900000055111", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002700_01()
  {
    try
    {
      IBAN iban = new IBAN("3333", "32060362", "DE");
      assertEquals("DE47320603620000003333", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002900_01()
  {
    try
    {
      IBAN iban = new IBAN("2600123456", "51210800", "DE");
      assertEquals("DE02512108000260123456", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel002900_02()
  {
    try
    {
      IBAN iban = new IBAN("1410123456", "51210800", "DE");
      assertEquals("DE35512108000141123456", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // @Test
  // BLZ existiert nicht mehr in der BLZ
  // public void regel003101_1()
  // {
  // try
  // {
  // IBAN iban = new IBAN("6790149813", "54520071", "DE");
  // assertEquals("DE77545201946790149813", iban.getIBAN());
  // assertEquals(IBANCode.PRUEFZIFFERNMETHODEFEHLT, iban.getCode());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  // @Test
  // BLZ existiert nicht mehr in der BLZ
  // public void regel003101_2()
  // {
  // try
  // {
  // IBAN iban = new IBAN("6791000000", "54520071", "DE");
  // assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  // @Test
  // BLZ existiert nicht mehr in der BLZ
  // public void regel003101_3()
  // {
  // try
  // {
  // IBAN iban = new IBAN("897", "10120760", "DE");
  // assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  // @Test
  // BLZ existiert nicht mehr in der BLZ
  // public void regel003101_A()
  // {
  // try
  // {
  // IBAN iban = new IBAN("1210100047", "79020325", "DE");
  // assertEquals("DE70762200731210100047", iban.getIBAN());
  // assertEquals(IBANCode.PRUEFZIFFERNMETHODEFEHLT, iban.getCode());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  // @Test
  // BLZ existiert nicht mehr in der BLZ
  // public void regel003101_B()
  // {
  // try
  // {
  // IBAN iban = new IBAN("1210100047", "70020001", "DE");
  // assertEquals("DE70762200731210100047", iban.getIBAN());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  // @Test
  // BLZ existiert nicht mehr in der BLZ
  // public void regel003101_C()
  // {
  // try
  // {
  // IBAN iban = new IBAN("1210100047", "76020214", "DE");
  // assertEquals("DE70762200731210100047", iban.getIBAN());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  @Test
  public void regel003101_D()
  {
    try
    {
      IBAN iban = new IBAN("1210100047", "76220073", "DE");
      assertEquals("DE70762200731210100047", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // Testfall J
  @Test
  public void regel003200_01()
  {
    try
    {
      IBAN iban = new IBAN("1210100047", "76220073", "DE");
      assertEquals("DE70762200731210100047", iban.getIBAN());
      assertEquals(IBANCode.PRUEFZIFFERNMETHODEFEHLT, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // Testfall K
  @Test
  public void regel003200_02()
  {
    try
    {
      IBAN iban = new IBAN("1457032621", "66020286", "DE");
      assertEquals("DE92660202861457032621", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // Testfall L
  @Test
  public void regel003200_03()
  {
    try
    {
      IBAN iban = new IBAN("3200000012", "76220073", "DE");
      assertEquals("DE06710221823200000012", iban.getIBAN());
      assertEquals(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // Testfall M
  @Test
  public void regel003200_04()
  {
    try
    {
      IBAN iban = new IBAN("5951950", "10020890", "DE");
      assertEquals("DE07100208900005951950", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // Testfall N
  @Test
  public void regel003200_05()
  {
    try
    {
      IBAN iban = new IBAN("897", "10020890", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // Testfall O
  @Test
  public void regel003200_06()
  {
    try
    {
      IBAN iban = new IBAN("847321750", "85020086", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003301_P1()
  {
    try
    {
      IBAN iban = new IBAN("2950219435", "70020270", "DE");
      assertEquals("DE76700202702950219435", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003301_P2()
  {
    try
    {
      IBAN iban = new IBAN("1457032621", "70020270", "DE");
      assertEquals("DE92660202861457032621", iban.getIBAN());
      assertEquals(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003301_P3()
  {
    try
    {
      IBAN iban = new IBAN("897", "70020270", "DE");
      assertEquals("DE55700202700000000897", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003301_P4()
  {
    try
    {
      IBAN iban = new IBAN("847321750", "70020270", "DE");
      assertEquals("DE36700202700847321750", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003301_P5()
  {
    try
    {
      IBAN iban = new IBAN("847321750", "72020070", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003301_P6()
  {
    try
    {
      IBAN iban = new IBAN("22222", "70020270", "DE");
      assertEquals("DE11700202705803435253", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003400_Q1()
  {
    try
    {
      IBAN iban = new IBAN("1320815432", "60020290", "DE");
      assertEquals("DE69600202901320815432", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003400_Q2()
  {
    try
    {
      IBAN iban = new IBAN("1457032621", "60020290", "DE");
      assertEquals("DE92660202861457032621", iban.getIBAN());
      assertEquals(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003400_Q3()
  {
    try
    {
      IBAN iban = new IBAN("5951950", "60020290", "DE");
      assertEquals("DE67600202900005951950", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003400_Q4()
  {
    try
    {
      IBAN iban = new IBAN("847321750", "60020290", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003501_R1()
  {
    try
    {
      IBAN iban = new IBAN("1050958422", "79020076", "DE");
      assertEquals("DE42790200761050958422", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003501_R2()
  {
    try
    {
      IBAN iban = new IBAN("1320815432", "79020076", "DE");
      assertEquals("DE69600202901320815432", iban.getIBAN());
      assertEquals(IBANCode.GEMELDETEBLZZURLOESCHUNGVORGEMERKT, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003501_R3()
  {
    try
    {
      IBAN iban = new IBAN("5951950", "79020076", "DE");
      assertEquals("DE56790200760005951950", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003501_R4()
  {
    try
    {
      IBAN iban = new IBAN("847321750", "79020076", "DE");
      assertEquals(IBANCode.AUFBAUKONTONUMMERFALSCH, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_01()
  {
    try
    {
      IBAN iban = new IBAN("0000101105", "21050000", "DE");
      assertEquals("DE32210500000101105000", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_02()
  {
    try
    {
      IBAN iban = new IBAN("0000840132", "21050000", "DE");
      assertEquals("DE91210500000840132000", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @Ignore
  public void regel003600_03()
  {
    try
    {
      IBAN iban = new IBAN("0000631879", "21050000", "DE");
      assertEquals("DE81210500000631879000", iban.getIBAN());

    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_04()
  {
    try
    {
      IBAN iban = new IBAN("30000025", "21050000", "DE");
      assertEquals("DE75210500000030000025", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_05()
  {
    try
    {
      IBAN iban = new IBAN("0051300528", "21050000", "DE");
      assertEquals("DE76210500000051300528", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_06()
  {
    try
    {
      IBAN iban = new IBAN("0100271010", "21050000", "DE");
      assertEquals("DE85210500000100271010", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_07()
  {
    try
    {
      IBAN iban = new IBAN("0319574000", "21050000", "DE");
      assertEquals("DE55210500000319574000", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_08()
  {
    try
    {
      IBAN iban = new IBAN("3060000035", "21050000", "DE");
      assertEquals("DE13210500003060000035", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_09()
  {
    try
    {
      IBAN iban = new IBAN("3070010313", "21050000", "DE");
      assertEquals("DE09210500003070010313", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_10()
  {
    try
    {
      IBAN iban = new IBAN("1100001928", "21050000", "DE");
      assertEquals("DE51210500001100001928", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_11()
  {
    try
    {
      IBAN iban = new IBAN("7052000037", "21050000", "DE");
      assertEquals("DE82210500007052000037", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003600_12()
  {
    try
    {
      IBAN iban = new IBAN("8553002045", "21050000", "DE");
      assertEquals("DE20210500008553002045", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003700_01()
  {
    try
    {
      IBAN iban = new IBAN("0000123456", "20110700", "DE");
      assertEquals("DE41300107000000123456", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel003700_02()
  {
    try
    {
      IBAN iban = new IBAN("0000654321", "30010700", "DE");
      assertEquals("DE85300107000000654321", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // @Test
  // public void regel004001_01()
  // {
  // try
  // {
  // IBAN iban = new IBAN("6015002", "68051310", "DE");
  // assertEquals("DE17680523280006015002", iban.getIBAN());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }
  @Test
  public void regel004100_01()
  {
    try
    {
      IBAN iban = new IBAN("0062220000", "62220000", "DE");
      assertEquals("DE96500604000000011404", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel004100_02()
  {
    try
    {
      IBAN iban = new IBAN("1234567890", "62220000", "DE");
      assertEquals("DE96500604000000011404", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel004300_01()
  {
    try
    {
      IBAN iban = new IBAN("868", "60651070", "DE");
      assertEquals("DE49666500850000000868", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel004300_02()
  {
    try
    {
      IBAN iban = new IBAN("12602", "60651070", "DE");
      assertEquals("DE33666500850000012602", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel004400_01()
  {
    try
    {
      IBAN iban = new IBAN("202", "68050101", "DE");
      assertEquals("DE51680501010002282022", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // @Test
  // Die Bankleitzahl hat jetzt die Regel 000000
  // public void regel004600_01()
  // {
  // try
  // {
  // IBAN iban = new IBAN("1234567890", "10120600", "DE");
  // assertEquals("DE62310108331234567890", iban.getIBAN());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  @Test
  public void regel004800_01()
  {
    try
    {
      IBAN iban = new IBAN("1231234567", "10120800", "DE");
      assertEquals("DE12360102001231234567", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void regel004900_01()
  {
    try
    {
      IBAN iban = new IBAN("0001991182", "30060010", "DE");
      assertEquals("DE26300600109911820001", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @Ignore
  // Mail an die Sparkasse WittmundLeer geschickt. Ist es richtig, dass die
  // Regel 0000 in der BLZ-Datei eingetragen ist? 14.09.2013 Jo
  public void regel005000_01()
  {
    try
    {
      IBAN iban = new IBAN("130084981", "28252760", "DE");
      assertEquals("DE24285500000130084981", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  // Die Bank existiert in der BLZ-Datei vom 8.6.2015 nicht mehr
  // @Test
  // public void regel005100_01()
  // {
  // try
  // {
  // IBAN iban = new IBAN("5308810004", "67220020", "DE");
  // assertEquals("DE38600501010002662604", iban.getIBAN());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // fail();
  // }
  // }

  @Test
  public void regel005300_01()
  {
    try
    {
      IBAN iban = new IBAN("35000", "55050000", "DE");
      assertEquals("DE94600501017401555913", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  @Ignore
  public void regel005500_01()
  {
    try
    {
      IBAN iban = new IBAN("7456123400", "25410300", "DE");
      assertEquals("DE47254102007456123400", iban.getIBAN());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void A4()
  {
    try
    {
      IBAN iban = new IBAN("11111", "57020500", "DE");
      assertEquals("DE81570205000000011111", iban.getIBAN());
      assertEquals(IBANCode.PRUEFZIFFERNMETHODEFEHLT, iban.getCode());
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      fail();
    }
  }

}