/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */

package de.jost_net.OBanToo.JUnit;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.jost_net.OBanToo.PruefziffernCheck.KontoPruefziffernrechnung;
import de.jost_net.OBanToo.SEPA.BankenDaten.Bank;
import de.jost_net.OBanToo.SEPA.BankenDaten.Banken;

@RunWith(JUnit4.class)
public class TestPruefziffer
{
  @Test
  public void testAlgorithmen()
  {
    HashSet<String> przalg = new HashSet<String>();
    for (Bank b : Banken.getBanken())
    {
      if (b.getHinweisloeschung().equals("0"))
      {
        przalg.add(b.getPruefziffernmethode());
      }
    }
    int i = 0;
    Iterator<String> iter = przalg.iterator();
    while (iter.hasNext())
    {
      String alg = iter.next();
      try
      {
        KontoPruefziffernrechnung.checkAccountCRCByAlg(alg, "1000000", "1");
      }
      catch (Exception e)
      {
        i++;
        System.out.println("Nicht implementiert: " + alg);
      }
    }
    System.out.println("Anzahl: " + i);
  }

  @Test
  public void test00_01() throws Exception
  {
    assertTrue(check("00", "9290701"));
  }

  @Test
  public void test00_02() throws Exception
  {
    assertTrue(check("00", "539290858"));
  }

  @Test
  public void test00_03() throws Exception
  {
    assertTrue(check("00", "1501824"));
  }

  @Test
  public void test00_04() throws Exception
  {
    assertTrue(check("00", "1501832"));
  }

  @Test
  public void test06_01() throws Exception
  {
    assertTrue(check("06", "94012341"));
  }

  @Test
  public void test06_02() throws Exception
  {
    assertTrue(check("06", "5073321010"));
  }

  @Test
  public void test10_01() throws Exception
  {
    assertTrue(check("10", "12345008"));
  }

  @Test
  public void test10_02() throws Exception
  {
    assertTrue(check("10", "87654008"));
  }

  @Test
  public void test17_01() throws Exception
  {
    assertTrue(check("17", "0446786040"));
  }

  @Test
  public void test19_01() throws Exception
  {
    assertTrue(check("19", "0240334000"));
  }

  @Test
  public void test19_02() throws Exception
  {
    assertTrue(check("19", "0200520016"));
  }

  @Test
  public void test26_01() throws Exception
  {
    assertTrue(check("26", "0520309001"));
  }

  @Test
  public void test26_02() throws Exception
  {
    assertTrue(check("26", "1111118111"));
  }

  @Test
  public void test26() throws Exception
  {
    assertTrue(check("26", "0005501024"));
  }

  @Test
  public void test28_01() throws Exception
  {
    assertTrue(check("28", "19999000"));
  }

  @Test
  public void test28_02() throws Exception
  {
    assertTrue(check("28", "9130000201"));
  }

  @Test
  public void test31_01() throws Exception
  {
    assertTrue(check("31", "1000000524"));
  }

  @Test
  public void test31_02() throws Exception
  {
    assertTrue(check("31", "1000000583"));
  }

  @Test
  public void test32_01() throws Exception
  {
    assertTrue(check("32", "9141405"));
  }

  @Test
  public void test32_02() throws Exception
  {
    assertTrue(check("32", "1709107983"));
  }

  @Test
  public void test32_03() throws Exception
  {
    assertTrue(check("32", "0122116979"));
  }

  @Test
  public void test32_04() throws Exception
  {
    assertTrue(check("32", "0121114867"));
  }

  @Test
  public void test32_05() throws Exception
  {
    assertTrue(check("32", "9030101192"));
  }

  @Test
  public void test32_06() throws Exception
  {
    assertTrue(check("32", "9245500460"));
  }

  @Test
  public void test33_01() throws Exception
  {
    assertTrue(check("33", "84956"));
  }

  @Test
  public void test33_02() throws Exception
  {
    assertTrue(check("33", "84956"));
  }

  @Test
  public void test34_01() throws Exception
  {
    assertTrue(check("34", "9913000700"));
  }

  @Test
  public void test34_02() throws Exception
  {
    assertTrue(check("34", "9914001000"));
  }

  @Test
  @Ignore
  public void test35() throws Exception
  {
    String a = "35";
    assertTrue(check(a, "0000108443"));
    assertTrue(check(a, "0000107451"));
    assertTrue(check(a, "0000102921"));
    assertTrue(check(a, "0000102349"));
    assertTrue(check(a, "0000101709"));
    assertTrue(check(a, "0000101599"));
  }

  @Test
  public void test36_01() throws Exception
  {
    assertTrue(check("36", "113178"));
  }

  @Test
  public void test36_02() throws Exception
  {
    assertTrue(check("36", "146666"));
  }

  @Test
  @Ignore
  public void test37() throws Exception
  {
    String a = "37";
    assertTrue(check(a, "624315"));
    assertTrue(check(a, "632500"));
  }

  @Test
  @Ignore
  public void test38() throws Exception
  {
    String a = "38";
    assertTrue(check(a, "191919"));
    assertTrue(check(a, "1100660"));
    assertTrue(check(a, "200205"));
    assertTrue(check(a, "10019400"));
  }

  @Test
  public void test40_01() throws Exception
  {
    assertTrue(check("40", "1258345"));
  }

  @Test
  public void test40_02() throws Exception
  {
    assertTrue(check("40", "3231963"));
  }

  @Test
  public void test41() throws Exception
  {
    String a = "41";
    assertTrue(check(a, "4013410024"));
    assertTrue(check(a, "4016660195"));
    assertTrue(check(a, "0166805317"));
    assertTrue(check(a, "4019310079"));
    assertTrue(check(a, "4019340829"));
    assertTrue(check(a, "4019151002"));
  }

  @Test
  public void test42() throws Exception
  {
    String a = "42";
    assertTrue(check(a, "59498"));
    assertTrue(check(a, "59510"));
  }

  @Test
  public void test43() throws Exception
  {
    String a = "43";
    assertTrue(check(a, "6135244"));
    assertTrue(check(a, "9516893476"));
  }

  @Test
  public void test44() throws Exception
  {
    String a = "44";
    assertTrue(check(a, "889006"));
    assertTrue(check(a, "2618040504"));
  }

  @Test
  public void test45_01() throws Exception
  {
    assertTrue(check("45", "3545343232"));
  }

  @Test
  public void test45_02() throws Exception
  {
    assertTrue(check("45", "4013410024"));
  }

  @Test
  public void test45_03() throws Exception
  {
    assertTrue(check("45", "0994681254"));
  }

  @Test
  public void test45_04() throws Exception
  {
    assertTrue(check("45", "1000199999"));
  }

  @Test
  public void test46_01() throws Exception
  {
    assertTrue(check("46", "0235468612"));
  }

  @Test
  public void test46_02() throws Exception
  {
    assertTrue(check("46", "0837890901"));
  }

  @Test
  public void test46_03() throws Exception
  {
    assertTrue(check("46", "1041447600"));
  }

  @Test
  @Ignore
  public void test47() throws Exception
  {
    String a = "47";
    assertTrue(check(a, "1018000"));
    assertTrue(check(a, "1003554450"));
  }

  @Test
  public void test50_01() throws Exception
  {
    assertTrue(check("50", "4000005001"));
  }

  @Test
  public void test50_02() throws Exception
  {
    assertTrue(check("50", "4444442001"));
  }

  @Test
  public void test51() throws Exception
  {
    String a = "51";
    assertTrue(check(a, "0001156071"));
    assertTrue(check(a, "0001156136"));
    assertTrue(check(a, "0001156078"));
    assertTrue(check(a, "0001234567"));
    assertTrue(check(a, "340968"));
    assertTrue(check(a, "201178"));
    assertTrue(check(a, "1009588"));
    assertTrue(check(a, "0000156071"));
    assertTrue(check(a, "101356073"));
    assertTrue(check(a, "199100002"));
    assertTrue(check(a, "0099100010"));
    assertTrue(check(a, "2599100002"));
    assertTrue(check(a, "0199100004"));
    assertTrue(check(a, "2599100003"));
    assertTrue(check(a, "3199204090"));
  }

  @Test
  @Ignore
  public void test54() throws Exception
  {
    String a = "54";
    assertTrue(check(a, "4964137395"));
    assertTrue(check(a, "4900010987"));
  }

  @Test
  @Ignore
  public void test57() throws Exception
  {
    String a = "57";
    assertTrue(check(a, "7500021766"));
    assertTrue(check(a, "9400001734"));
    assertTrue(check(a, "7800028282"));
    assertTrue(check(a, "8100244186"));
    assertTrue(check(a, "3251080371"));
    assertTrue(check(a, "3891234567"));
    assertTrue(check(a, "7777778800"));
    assertTrue(check(a, "5001050352"));
    assertTrue(check(a, "5045090090"));
    assertTrue(check(a, "1909700805"));
    assertTrue(check(a, "9322111030"));
    assertTrue(check(a, "7400060823"));
  }

  @Test
  public void test58_01() throws Exception
  {
    assertTrue(check("58", "1800881120"));
  }

  @Test
  public void test58_02() throws Exception
  {
    assertTrue(check("58", "9200654108"));
  }

  @Test
  public void test58_03() throws Exception
  {
    assertTrue(check("58", "1015222224"));
  }

  @Test
  public void test58_04() throws Exception
  {
    assertTrue(check("58", "3703169668"));
  }

  @Test
  @Ignore
  public void test64() throws Exception
  {
    String a = "64";
    assertTrue(check(a, "1206473010"));
    assertTrue(check(a, "5016511020"));
  }

  @Test
  public void test66_01() throws Exception
  {
    assertTrue(check("66", "100154508"));
  }

  @Test
  public void test66_02() throws Exception
  {
    assertTrue(check("66", "101154508"));
  }

  @Test
  public void test66_03() throws Exception
  {
    assertTrue(check("66", "100154516"));
  }

  @Test
  public void test66_04() throws Exception
  {
    assertTrue(check("66", "101154516"));
  }

  @Test
  public void test69() throws Exception
  {
    String a = "69";
    assertTrue(check(a, "1234567900"));
    assertTrue(check(a, "1234567006"));
  }

  @Test
  public void test71() throws Exception
  {
    assertTrue(check("71", "7101234007"));
  }

  @Test
  public void test73_01() throws Exception
  {
    assertTrue(check("73", "0003503398"));
  }

  @Test
  public void test73_02() throws Exception
  {
    assertTrue(check("73", "0001340967"));
  }

  @Test
  public void test73_03() throws Exception
  {
    assertTrue(check("73", "0003503391"));
  }

  @Test
  public void test73_04() throws Exception
  {
    assertTrue(check("73", "0001340968"));
  }

  @Test
  public void test73_05() throws Exception
  {
    assertTrue(check("73", "0003503392"));
  }

  @Test
  public void test73_06() throws Exception
  {
    assertTrue(check("73", "0001340966"));
  }

  @Test
  public void test73_07() throws Exception
  {
    assertTrue(check("73", "123456"));
  }

  @Test
  public void test74() throws Exception
  {
    String a = "74";
    assertTrue(check(a, "1016"));
    assertTrue(check(a, "26260"));
    assertTrue(check(a, "242243"));
    assertTrue(check(a, "242248"));
    assertTrue(check(a, "18002113"));
    assertTrue(check(a, "1821200043"));
  }

  @Test
  public void test76() throws Exception
  {
    String a = "76";
    assertTrue(check(a, "0006543200"));
    assertTrue(check(a, "9012345600"));
    assertTrue(check(a, "7876543100"));
  }

  @Test
  @Ignore
  public void test77() throws Exception
  {
    String a = "77";
    assertTrue(check(a, "10338"));
    assertTrue(check(a, "13844"));
    assertTrue(check(a, "65354"));
    assertTrue(check(a, "69258"));
  }

  @Test
  public void test78() throws Exception
  {
    String a = "78";
    assertTrue(check(a, "7581499"));
    assertTrue(check(a, "9999999981"));
  }

  @Test
  @Ignore
  public void test79() throws Exception
  {
    String a = "79";
    assertTrue(check(a, "3230012688"));
    assertTrue(check(a, "4230028872"));
    assertTrue(check(a, "5440001898"));
    assertTrue(check(a, "6330001063"));
    assertTrue(check(a, "7000149349"));
    assertTrue(check(a, "8000003577"));
    assertTrue(check(a, "1550167850"));
    assertTrue(check(a, "9011200140"));
  }

  @Test
  @Ignore
  public void test80() throws Exception
  {
    String a = "80";
    assertTrue(check(a, "340968"));
    assertTrue(check(a, "340966"));
  }

  @Test
  public void test81() throws Exception
  {
    String a = "81";
    assertTrue(check(a, "0646440"));
    assertTrue(check(a, "1359100"));
  }

  @Test
  public void test82() throws Exception
  {
    String a = "82";
    assertTrue(check(a, "123897"));
    assertTrue(check(a, "3199500501"));
  }

  @Test
  @Ignore
  public void test83() throws Exception
  {
    String a = "83";
    assertTrue(check(a, "0001156071"));
    assertTrue(check(a, "0001156136"));
    assertTrue(check(a, "0000156078"));
    assertTrue(check(a, "0000156071"));
    assertTrue(check(a, "0099100002"));
  }

  @Test
  public void test84_01() throws Exception
  {
    assertTrue(check("84", "240699"));
  }

  @Test
  public void test84_02() throws Exception
  {
    assertTrue(check("84", "350982"));
  }

  @Test
  public void test84_03() throws Exception
  {
    assertTrue(check("84", "461059"));
  }

  @Test
  public void test84_04() throws Exception
  {
    assertTrue(check("84", "240692"));
  }

  @Test
  public void test84_05() throws Exception
  {
    assertTrue(check("84", "350985"));
  }

  @Test
  public void test84_06() throws Exception
  {
    assertTrue(check("84", "461052"));
  }

  @Test
  public void test84_07() throws Exception
  {
    assertTrue(check("84", "240961"));
  }

  @Test
  public void test84_08() throws Exception
  {
    assertTrue(check("84", "350984"));
  }

  @Test
  public void test84_09() throws Exception
  {
    assertTrue(check("84", "461054"));
  }

  @Test
  public void test84_10() throws Exception
  {
    assertTrue(check("84", "0199100002"));
  }

  @Test
  public void test84_11() throws Exception
  {
    assertTrue(check("84", "0099100010"));
  }

  @Test
  public void test84_12() throws Exception
  {
    assertTrue(check("84", "2599100002"));
  }

  @Test
  public void test84_13() throws Exception
  {
    assertTrue(check("84", "2599100002"));
  }

  @Test
  public void test84_14() throws Exception
  {
    assertTrue(check("84", "0199100004"));
  }

  @Test
  public void test84_15() throws Exception
  {
    assertTrue(check("84", "2599100003"));
  }

  @Test
  public void test84_16() throws Exception
  {
    assertTrue(check("84", "3199204090"));
  }

  @Test
  public void test85() throws Exception
  {
    String a = "85";
    assertTrue(check(a, "0001156071"));
    assertTrue(check(a, "0001156136"));
    assertTrue(check(a, "0000156078"));
    assertTrue(check(a, "0000156071"));
    assertTrue(check(a, "3199100002"));
  }

  @Test
  public void test86() throws Exception
  {
    String a = "86";
    assertTrue(check(a, "340968"));
    assertTrue(check(a, "1001171"));
    assertTrue(check(a, "1009588"));
    assertTrue(check(a, "123897"));
    assertTrue(check(a, "340960"));
  }

  @Test
  public void test87() throws Exception
  {
    String a = "87";
    assertTrue(check(a, "0000000406"));
    assertTrue(check(a, "0000051768"));
    assertTrue(check(a, "0010701590"));
    assertTrue(check(a, "0010720185"));
    assertTrue(check(a, "0000100005"));
    assertTrue(check(a, "0000393814"));
    assertTrue(check(a, "0000950360"));
    assertTrue(check(a, "3199500501"));
  }

  @Test
  public void test88() throws Exception
  {
    String a = "88";
    assertTrue(check(a, "2525259"));
    assertTrue(check(a, "1000500"));
    assertTrue(check(a, "90013000"));
    assertTrue(check(a, "92525253"));
    assertTrue(check(a, "99913003"));
  }

  @Test
  @Ignore
  public void test89() throws Exception
  {
    String a = "89";
    assertTrue(check(a, "1098506"));
    assertTrue(check(a, "32028008"));
    assertTrue(check(a, "218433000"));
    assertTrue(check(a, "92525253"));
    assertTrue(check(a, "99913003"));
  }

  @Test
  public void test90() throws Exception
  {
    String a = "90";
    assertTrue(check(a, "0001975641"));
    assertTrue(check(a, "0001988654"));
    assertTrue(check(a, "0000863530"));
    assertTrue(check(a, "0000784451"));
    assertTrue(check(a, "0000654321"));
    assertTrue(check(a, "0000824491"));
    assertTrue(check(a, "0000677747"));
    assertTrue(check(a, "0000840507"));
    assertTrue(check(a, "0000996663"));
    assertTrue(check(a, "0000666034"));
    assertTrue(check(a, "0099100002"));
  }

  @Test
  public void test91() throws Exception
  {
    String a = "91";
    assertTrue(check(a, "2974118000"));
    assertTrue(check(a, "5281741000"));
    assertTrue(check(a, "9952810000"));
    assertTrue(check(a, "2974117000"));
    assertTrue(check(a, "5281770000"));
    assertTrue(check(a, "9952812000"));
    assertTrue(check(a, "8840019000"));
    assertTrue(check(a, "8840050000"));
    assertTrue(check(a, "8840087000"));
    assertTrue(check(a, "8840045000"));
    assertTrue(check(a, "8840012000"));
    assertTrue(check(a, "8840055000"));
    assertTrue(check(a, "8840080000"));
  }

  @Test
  @Ignore
  public void test93() throws Exception
  {
    String a = "93";
    assertTrue(check(a, "6714790000"));
    assertTrue(check(a, "0000671479"));
    assertTrue(check(a, "1277830000"));
    assertTrue(check(a, "0000127783"));
    assertTrue(check(a, "1277910000"));
    assertTrue(check(a, "0000127791"));
    assertTrue(check(a, "3067540000"));
    assertTrue(check(a, "0000306754"));
  }

  @Test
  public void test94_01() throws Exception
  {
    assertTrue(check("94", "6782533003"));
  }

  @Test
  public void test95() throws Exception
  {
    String a = "95";
    assertTrue(check(a, "0068007003"));
    assertTrue(check(a, "0847321750"));
    assertTrue(check(a, "6450060494"));
    assertTrue(check(a, "6454000003"));
  }

  @Test
  public void test96() throws Exception
  {
    String a = "96";
    assertTrue(check(a, "0000254100"));
    assertTrue(check(a, "9421000009"));
    assertTrue(check(a, "0000000208"));
    assertTrue(check(a, "0101115152"));
    assertTrue(check(a, "0301204301"));
  }

  @Test
  public void test98_01() throws Exception
  {
    assertTrue(check("98", "9619439213"));
  }

  @Test
  public void test98_02() throws Exception
  {
    assertTrue(check("98", "3009800016"));
  }

  @Test
  public void test98_03() throws Exception
  {
    assertTrue(check("98", "9619509976"));
  }

  @Test
  public void test98_04() throws Exception
  {
    assertTrue(check("98", "5989800173"));
  }

  @Test
  public void test98_05() throws Exception
  {
    assertTrue(check("98", "9619319999"));
  }

  @Test
  public void test98_06() throws Exception
  {
    assertTrue(check("98", "6719430018"));
  }

  @Test
  public void test99() throws Exception
  {
    String a = "99";
    assertTrue(check(a, "0068007003"));
    assertTrue(check(a, "0847321750"));
  }

  @Test
  public void testA0() throws Exception
  {
    String a = "A0";
    assertTrue(check(a, "521003287"));
    assertTrue(check(a, "54500"));
    assertTrue(check(a, "3287"));
    assertTrue(check(a, "18761"));
    assertTrue(check(a, "28290"));
  }

  @Test
  public void testA1() throws Exception
  {
    String a = "A1";
    assertTrue(check(a, "0010030005"));
    assertTrue(check(a, "0010030997"));
    assertTrue(check(a, "1010030054"));
  }

  @Test
  public void testA2() throws Exception
  {
    String a = "A2";
    assertTrue(check(a, "3456789019"));
    assertTrue(check(a, "5678901231"));
    assertTrue(check(a, "6789012348"));
    assertTrue(check(a, "3456789012"));
  }

  @Test
  public void testA3() throws Exception
  {
    String a = "A3";
    assertTrue(check(a, "1234567897"));
    assertTrue(check(a, "0123456782"));
    assertTrue(check(a, "9876543210"));
    assertTrue(check(a, "1234567890"));
    assertTrue(check(a, "0123456789"));
  }

  @Test
  @Ignore
  public void testA4() throws Exception
  {
    String a = "A4";
    assertTrue(check(a, "0004711173"));
    assertTrue(check(a, "0007093330"));
    assertTrue(check(a, "0004711172"));
    assertTrue(check(a, "0007093335"));
    assertTrue(check(a, "1199503010"));
    assertTrue(check(a, "8499421235"));
    assertTrue(check(a, "0000862342"));
    assertTrue(check(a, "8997710000"));
    assertTrue(check(a, "0664040000"));
    assertTrue(check(a, "0000905844"));
    assertTrue(check(a, "5030101099"));
    assertTrue(check(a, "0001123458"));
    assertTrue(check(a, "1299503117"));
  }

  @Test
  public void testA5() throws Exception
  {
    String a = "A5";
    assertTrue(check(a, "9941510001"));
    assertTrue(check(a, "9961230019"));
    assertTrue(check(a, "9380027210"));
    assertTrue(check(a, "9932290910"));
    assertTrue(check(a, "0000251437"));
    assertTrue(check(a, "0007948344"));
    assertTrue(check(a, "0000159590"));
    assertTrue(check(a, "0000051640"));
  }

  @Test
  public void testA6() throws Exception
  {
    String a = "A6";
    assertTrue(check(a, "800048548"));
    assertTrue(check(a, "0855000014"));
    assertTrue(check(a, "17"));
    assertTrue(check(a, "55300030"));
    assertTrue(check(a, "150178033"));
    assertTrue(check(a, "600003555"));
    assertTrue(check(a, "900291823"));
  }

  @Test
  public void testA7() throws Exception
  {
    String a = "A7";
    assertTrue(check(a, "19010008"));
    assertTrue(check(a, "19010438"));
    assertTrue(check(a, "19010660"));
    assertTrue(check(a, "19010876"));
    assertTrue(check(a, "209010892"));
  }

  @Test
  public void testA8() throws Exception
  {
    String a = "A8";
    assertTrue(check(a, "7436661"));
    assertTrue(check(a, "7436670"));
    assertTrue(check(a, "1359100"));
    assertTrue(check(a, "7436660"));
    assertTrue(check(a, "7436678"));
    assertTrue(check(a, "0003503398"));
    assertTrue(check(a, "0001340967"));
  }

  @Test
  public void testA9() throws Exception
  {
    String a = "A9";
    assertTrue(check(a, "5043608"));
    assertTrue(check(a, "86725"));
    assertTrue(check(a, "504360"));
    assertTrue(check(a, "822035"));
    assertTrue(check(a, "32577083"));
  }

  @Test
  @Ignore
  public void testB0() throws Exception
  {
    String a = "B0";
    assertTrue(check(a, "1197423162"));
    assertTrue(check(a, "1000000606"));
    assertTrue(check(a, "1000000406"));
    assertTrue(check(a, "1035791538"));
    assertTrue(check(a, "1126939724"));
    assertTrue(check(a, "1197423460"));
  }

  @Test
  public void testB1() throws Exception
  {
    String a = "B1";
    assertTrue(check(a, "1434253150"));
    assertTrue(check(a, "2746315471"));
    assertTrue(check(a, "7414398260"));
    assertTrue(check(a, "8347251693"));
  }

  @Test
  public void testB2_01() throws Exception
  {
    assertTrue(check("B2", "0020012357"));
  }

  @Test
  public void testB2_02() throws Exception
  {
    assertTrue(check("B2", "0080012345"));
  }

  @Test
  public void testB2_03() throws Exception
  {
    assertTrue(check("B2", "0926801910"));
  }

  @Test
  public void testB2_04() throws Exception
  {
    assertTrue(check("B2", "1002345674"));
  }

  @Test
  public void testB2_05() throws Exception
  {
    assertTrue(check("B2", "8000990054"));
  }

  @Test
  public void testB2_06() throws Exception
  {
    assertTrue(check("B2", "9000481805"));
  }

  @Test
  public void testB3() throws Exception
  {
    String a = "B3";
    assertTrue(check(a, "1000000060"));
    assertTrue(check(a, "0000000140"));
    assertTrue(check(a, "0000000019"));
    assertTrue(check(a, "1002798417"));
    assertTrue(check(a, "8409915001"));
    assertTrue(check(a, "9635000101"));
    assertTrue(check(a, "9730200100"));
  }

  @Test
  @Ignore
  public void testB4() throws Exception
  {
    String a = "B4";
    assertTrue(check(a, "9941510001"));
    assertTrue(check(a, "9961230019"));
    assertTrue(check(a, "9380027210"));
    assertTrue(check(a, "9932290910"));
    assertTrue(check(a, "0000251437"));
    assertTrue(check(a, "0007948344"));
    assertTrue(check(a, "0000051640"));
  }

  @Test
  public void testB5() throws Exception
  {
    String a = "B5";
    assertTrue(check(a, "0159006955"));
    assertTrue(check(a, "2000123451"));
    assertTrue(check(a, "1151043216"));
    assertTrue(check(a, "9000939033"));
    assertTrue(check(a, "0123456782"));
    assertTrue(check(a, "0130098767"));
    assertTrue(check(a, "1045000252"));
  }

  @Test
  public void testB6() throws Exception
  {
    String a = "B6";
    assertTrue(check(a, "9110000000"));
    assertTrue(check(a, "0269876545"));
    assertTrue(check(a, "80053782", "487310018"));
  }

  @Test
  public void testB7_01() throws Exception
  {
    assertTrue(check("B7", "0700001529"));
  }

  @Test
  public void testB7_02() throws Exception
  {
    assertTrue(check("B7", "0730000019"));
  }

  @Test
  public void testB7_03() throws Exception
  {
    assertTrue(check("B7", "0001001008"));
  }

  @Test
  public void testB7_04() throws Exception
  {
    assertTrue(check("B7", "0001057887"));
  }

  @Test
  public void testB7_05() throws Exception
  {
    assertTrue(check("B7", "0001007222"));
  }

  @Test
  public void testB7_06() throws Exception
  {
    assertTrue(check("B7", "0810011825"));
  }

  @Test
  public void testB7_07() throws Exception
  {
    assertTrue(check("B7", "0800107653"));
  }

  @Test
  public void testB7_08() throws Exception
  {
    assertTrue(check("B7", "0005922372"));
  }

  @Test
  public void testB8() throws Exception
  {
    String a = "B8";
    assertTrue(check(a, "0734192657"));
    assertTrue(check(a, "6932875274"));
    assertTrue(check(a, "3145863029"));
    assertTrue(check(a, "2938692523"));
  }

  @Test
  public void testB9_01() throws Exception
  {
    assertTrue(check("B9", "87920187"));
  }

  @Test
  public void testB9_02() throws Exception
  {
    assertTrue(check("B9", "41203755"));
  }

  @Test
  public void testB9_03() throws Exception
  {
    assertTrue(check("B9", "81069577"));
  }

  @Test
  public void testB9_04() throws Exception
  {
    assertTrue(check("B9", "61287958"));
  }

  @Test
  public void testB9_05() throws Exception
  {
    assertTrue(check("B9", "58467232"));
  }

  @Test
  public void testB9_06() throws Exception
  {
    assertTrue(check("B9", "7125633"));
  }

  @Test
  public void testB9_07() throws Exception
  {
    assertTrue(check("B9", "1253657"));
  }

  @Test
  public void testB9_08() throws Exception
  {
    assertTrue(check("B9", "4353631"));
  }

  @Test
  public void testC0() throws Exception
  {
    String a = "C0";
    assertTrue(check(a, "13051172", "43001500"));
    assertTrue(check(a, "13051172", "48726458"));
    assertTrue(check(a, "0082335729"));
    assertTrue(check(a, "0734192657"));
    assertTrue(check(a, "6932875274"));
  }

  @Test
  public void testC1() throws Exception
  {
    String a = "C1";
    assertTrue(check(a, "0446786040"));
    assertTrue(check(a, "0478046940"));
    assertTrue(check(a, "0701625830"));
    assertTrue(check(a, "0701625840"));
    assertTrue(check(a, "0882095630"));
    assertTrue(check(a, "5432112349"));
    assertTrue(check(a, "5543223456"));
    assertTrue(check(a, "5654334563"));
    assertTrue(check(a, "5765445670"));
    assertTrue(check(a, "5876556788"));
  }

  @Test
  public void testC2_01() throws Exception
  {
    assertTrue(check("C2", "2394871426"));
  }

  @Test
  public void testC2_02() throws Exception
  {
    assertTrue(check("C2", "4218461950"));
  }

  @Test
  public void testC2_03() throws Exception
  {
    assertTrue(check("C2", "7352569148"));
  }

  @Test
  public void testC2_04() throws Exception
  {
    assertTrue(check("C2", "5127485166"));
  }

  @Test
  public void testC2_05() throws Exception
  {
    assertTrue(check("C2", "8738142564"));
  }

  @Test
  public void testC3_01() throws Exception
  {
    assertTrue(check("C3", "9294182"));
  }

  @Test
  public void testC3_02() throws Exception
  {
    assertTrue(check("C3", "4431276"));
  }

  @Test
  public void testC3_03() throws Exception
  {
    assertTrue(check("C3", "19919"));
  }

  @Test
  public void testC3_04() throws Exception
  {
    assertTrue(check("C3", "9000420530"));
  }

  @Test
  public void testC3_05() throws Exception
  {
    assertTrue(check("C3", "9000010006"));
  }

  @Test
  public void testC3_06() throws Exception
  {
    assertTrue(check("C3", "9000577650"));
  }

  @Test
  public void testC4_01() throws Exception
  {
    assertTrue(check("C4", "0000000019"));
  }

  @Test
  public void testC4_02() throws Exception
  {
    assertTrue(check("C4", "0000292932"));
  }

  @Test
  public void testC4_03() throws Exception
  {
    assertTrue(check("C4", "0000094455"));
  }

  @Test
  public void testC4_04() throws Exception
  {
    assertTrue(check("C4", "9000420530"));
  }

  @Test
  public void testC4_05() throws Exception
  {
    assertTrue(check("C4", "9000010006"));
  }

  @Test
  public void testC4() throws Exception
  {
    assertTrue(check("C4", "9000577650"));
  }

  @Test
  public void testC5() throws Exception
  {
    String a = "C5";
    assertTrue(check(a, "0000301168"));
    assertTrue(check(a, "0000302554"));
    assertTrue(check(a, "0300020050"));
    assertTrue(check(a, "0300566000"));
    assertTrue(check(a, "1000061378"));
    assertTrue(check(a, "1000061412"));
    assertTrue(check(a, "4450164064"));
    assertTrue(check(a, "4863476104"));
    assertTrue(check(a, "5000000028"));
    assertTrue(check(a, "5000000391"));
    assertTrue(check(a, "6450008149"));
    assertTrue(check(a, "6800001016"));
    assertTrue(check(a, "9000100012"));
    assertTrue(check(a, "9000210017"));
    assertTrue(check(a, "3060188103"));
    assertTrue(check(a, "3070402023"));
    assertTrue(check(a, "30000025"));
  }

  @Test
  @Ignore
  public void testC6() throws Exception
  {
    String a = "C6";
    assertTrue(check(a, "0000065516"));
    assertTrue(check(a, "0203178249"));
    assertTrue(check(a, "1031405209"));
    assertTrue(check(a, "1082012201"));
    assertTrue(check(a, "2003455189"));
    assertTrue(check(a, "2004001016"));
    assertTrue(check(a, "3110150986"));
    assertTrue(check(a, "3068459207"));
    assertTrue(check(a, "5035105948"));
    assertTrue(check(a, "5286102149"));
    assertTrue(check(a, "4012660028"));
    assertTrue(check(a, "4100235626"));
    assertTrue(check(a, "6028426119"));
    assertTrue(check(a, "6861001755"));
    assertTrue(check(a, "7008199027"));
    assertTrue(check(a, "7002000023"));
    assertTrue(check(a, "8526080015"));
    assertTrue(check(a, "8711072264"));
    assertTrue(check(a, "9000430223"));
    assertTrue(check(a, "9000781153"));
  }

  @Test
  public void testC7() throws Exception
  {
    String a = "C7";
    assertTrue(check(a, "3500022"));
    assertTrue(check(a, "38150900"));
    assertTrue(check(a, "600103660"));
    assertTrue(check(a, "39101181"));
    assertTrue(check(a, "94012341"));
    assertTrue(check(a, "5073321010"));
  }

  @Test
  public void testC8_01() throws Exception
  {
    assertTrue(check("C8", "3456789019"));
  }

  @Test
  public void testC8_02() throws Exception
  {
    assertTrue(check("C8", "5678901231"));
  }

  @Test
  public void testC8_03() throws Exception
  {
    assertTrue(check("C8", "3456789012"));
  }

  @Test
  public void testC8_04() throws Exception
  {
    assertTrue(check("C8", "0022007130"));
  }

  @Test
  public void testC8_05() throws Exception
  {
    assertTrue(check("C8", "0123456789"));
  }

  @Test
  public void testC8_06() throws Exception
  {
    assertTrue(check("C8", "0552071285"));
  }

  @Test
  @Ignore
  public void testC9() throws Exception
  {
    String a = "C9";
    assertTrue(check(a, "3456789019"));
    assertTrue(check(a, "5678901231"));
    assertTrue(check(a, "0123456789"));
  }

  @Test
  public void testD0() throws Exception
  {
    String a = "D0";
    assertTrue(check(a, "6100272324"));
    assertTrue(check(a, "6100273479"));
  }

  @Test
  public void testD1_01() throws Exception
  {
    assertTrue(check("D1", "0082012203"));
  }

  @Test
  public void testD1_02() throws Exception
  {
    assertTrue(check("D1", "1452683581"));
  }

  @Test
  public void testD1_03() throws Exception
  {
    assertTrue(check("D1", "2129642505"));
  }

  @Test
  public void testD1_04() throws Exception
  {
    assertTrue(check("D1", "3002000027"));
  }

  @Test
  public void testD1_05() throws Exception
  {
    assertTrue(check("D1", "4230001407"));
  }

  @Test
  public void testD1_06() throws Exception
  {
    assertTrue(check("D1", "5000065514"));
  }

  @Test
  public void testD1_07() throws Exception
  {
    assertTrue(check("D1", "6001526215"));
  }

  @Test
  public void testD1_08() throws Exception
  {
    assertTrue(check("D1", "7126502149"));
  }

  @Test
  public void testD1_09() throws Exception
  {
    assertTrue(check("D1", "9000430223"));
  }

  @Test
  @Ignore
  public void testD2() throws Exception
  {
    String a = "D2";
    assertTrue(check(a, "189912137"));
    assertTrue(check(a, "235308215"));
    assertTrue(check(a, "4455667784"));
    assertTrue(check(a, "1234567897"));
    assertTrue(check(a, "51181008"));
    assertTrue(check(a, "71214205"));
  }

  @Test
  @Ignore
  public void testD3() throws Exception
  {
    String a = "D3";
    assertTrue(check(a, "1600169591"));
    assertTrue(check(a, "1600189151"));
    assertTrue(check(a, "1800084079"));
    assertTrue(check(a, "6019937007"));
    assertTrue(check(a, "6021354007"));
    assertTrue(check(a, "6030642006"));
  }

  @Test
  @Ignore
  public void testD4() throws Exception
  {
    String a = "D4";
    assertTrue(check(a, "1112048219"));
    assertTrue(check(a, "2024601814"));
    assertTrue(check(a, "3000005012"));
    assertTrue(check(a, "4143406984"));
    assertTrue(check(a, "5926485111"));
    assertTrue(check(a, "6286304975"));
    assertTrue(check(a, "7900256617"));
    assertTrue(check(a, "8102228628"));
    assertTrue(check(a, "9002364588"));
  }

  @Test
  @Ignore
  public void testD5() throws Exception
  {
    String a = "D5";
    assertTrue(check(a, "5999718138"));
    assertTrue(check(a, "1799222116"));
    assertTrue(check(a, "0099632004"));
    assertTrue(check(a, "0004711173"));
    assertTrue(check(a, "0007093330"));
    assertTrue(check(a, "0000127787"));
    assertTrue(check(a, "0004711172"));
    assertTrue(check(a, "0007093335"));
    assertTrue(check(a, "0000100062"));
    assertTrue(check(a, "0000100088"));
  }

  @Test
  @Ignore
  public void testD6() throws Exception
  {
    String a = "D6";
    assertTrue(check(a, "3409"));
    assertTrue(check(a, "585327"));
    assertTrue(check(a, "1650513"));
    assertTrue(check(a, "3601671056"));
    assertTrue(check(a, "4402001046"));
    assertTrue(check(a, "6100268241"));
    assertTrue(check(a, "7001000681"));
    assertTrue(check(a, "9000111105"));
    assertTrue(check(a, "9001291005"));
    assertTrue(check(a, "0000100088"));
  }

  @Test
  @Ignore
  public void testD7() throws Exception
  {
    String a = "D7";
    assertTrue(check(a, "0500018205"));
    assertTrue(check(a, "0230103715"));
    assertTrue(check(a, "0301000434"));
    assertTrue(check(a, "0330035104"));
    assertTrue(check(a, "0420001202"));
    assertTrue(check(a, "0134637709"));
    assertTrue(check(a, "0201005939"));
    assertTrue(check(a, "0602006999"));
  }

  @Test
  @Ignore
  public void testD8() throws Exception
  {
    String a = "D8";
    assertTrue(check(a, "1403414848"));
    assertTrue(check(a, "6800000439"));
    assertTrue(check(a, "6899999954"));
  }

  @Test
  public void testD9() throws Exception
  {
    String a = "D9";
    assertTrue(check(a, "1234567897"));
    assertTrue(check(a, "0123456782"));
    assertTrue(check(a, "9876543210"));
    assertTrue(check(a, "1234567890"));
    assertTrue(check(a, "0123456789"));
  }

  @Test
  @Ignore
  public void testE0() throws Exception
  {
    String a = "E0";
    assertTrue(check(a, "1234568013"));
    assertTrue(check(a, "1534568010"));
    assertTrue(check(a, "2610015"));
    assertTrue(check(a, "8741013011"));
  }

  @Ignore
  private boolean check(String alg, String konto) throws Exception
  {
    return check(alg, "12345678", konto);
  }

  @Ignore
  private boolean check(String alg, String blz, String konto) throws Exception
  {
    return KontoPruefziffernrechnung.checkAccountCRC(alg, blz, konto).isValid();
  }

}
