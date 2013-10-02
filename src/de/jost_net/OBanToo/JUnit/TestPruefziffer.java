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
  public void test41_01() throws Exception
  {
    assertTrue(check("41", "4013410024"));
  }

  @Test
  public void test41_02() throws Exception
  {
    assertTrue(check("41", "4016660195"));
  }

  @Test
  public void test41_03() throws Exception
  {
    assertTrue(check("41", "0166805317"));
  }

  @Test
  public void test41_04() throws Exception
  {
    assertTrue(check("41", "4019310079"));
  }

  @Test
  public void test41_05() throws Exception
  {
    assertTrue(check("41", "4019340829"));
  }

  @Test
  public void test41_06() throws Exception
  {
    assertTrue(check("41", "4019151002"));
  }

  @Test
  public void test42_01() throws Exception
  {
    assertTrue(check("42", "59498"));
  }

  @Test
  public void test42() throws Exception
  {
    assertTrue(check("42", "59510"));
  }

  @Test
  public void test43_01() throws Exception
  {
    assertTrue(check("43", "6135244"));
  }

  @Test
  public void test43_02() throws Exception
  {
    assertTrue(check("43", "9516893476"));
  }

  @Test
  public void test44_01() throws Exception
  {
    assertTrue(check("44", "889006"));
  }

  @Test
  public void test44_02() throws Exception
  {
    assertTrue(check("44", "2618040504"));
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
  public void test51_01() throws Exception
  {
    assertTrue(check("51", "0001156071"));
  }

  @Test
  public void test51_02() throws Exception
  {
    assertTrue(check("51", "0001156136"));
  }

  @Test
  public void test51_03() throws Exception
  {
    assertTrue(check("51", "0001156078"));
  }

  @Test
  public void test51_04() throws Exception
  {
    assertTrue(check("51", "0001234567"));
  }

  @Test
  public void test51_05() throws Exception
  {
    assertTrue(check("51", "340968"));
  }

  @Test
  public void test51_06() throws Exception
  {
    assertTrue(check("51", "201178"));
  }

  @Test
  public void test51_07() throws Exception
  {
    assertTrue(check("51", "1009588"));
  }

  @Test
  public void test51_08() throws Exception
  {
    assertTrue(check("51", "0000156071"));
  }

  @Test
  public void test51_09() throws Exception
  {
    assertTrue(check("51", "101356073"));
  }

  @Test
  public void test51_10() throws Exception
  {
    assertTrue(check("51", "199100002"));
  }

  @Test
  public void test51_11() throws Exception
  {
    assertTrue(check("51", "0099100010"));
  }

  @Test
  public void test51_12() throws Exception
  {
    assertTrue(check("51", "2599100002"));
  }

  @Test
  public void test51_13() throws Exception
  {
    assertTrue(check("51", "0199100004"));
  }

  @Test
  public void test51_14() throws Exception
  {
    assertTrue(check("51", "2599100003"));
  }

  @Test
  public void test51_15() throws Exception
  {
    assertTrue(check("51", "3199204090"));
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
  public void test64_01() throws Exception
  {
    assertTrue(check("64", "1206473010"));
  }

  @Test
  public void test64_02() throws Exception
  {
    assertTrue(check("64", "5016511020"));
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
  public void test69_01() throws Exception
  {
    assertTrue(check("69", "1234567900"));
  }

  @Test
  public void test69_02() throws Exception
  {
    assertTrue(check("69", "1234567006"));
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
  public void test74_01() throws Exception
  {
    assertTrue(check("74", "1016"));
  }

  @Test
  public void test74_02() throws Exception
  {
    assertTrue(check("74", "26260"));
  }

  @Test
  public void test74_03() throws Exception
  {
    assertTrue(check("74", "242243"));
  }

  @Test
  public void test74_04() throws Exception
  {
    assertTrue(check("74", "242248"));
  }

  @Test
  public void test74_05() throws Exception
  {
    assertTrue(check("74", "18002113"));
  }

  @Test
  public void test74_06() throws Exception
  {
    assertTrue(check("74", "1821200043"));
  }

  @Test
  public void test76_01() throws Exception
  {
    assertTrue(check("76", "0006543200"));
  }

  @Test
  public void test76_02() throws Exception
  {
    assertTrue(check("76", "9012345600"));
  }

  @Test
  public void test76_03() throws Exception
  {
    assertTrue(check("76", "7876543100"));
  }

  @Test
  public void test78_01() throws Exception
  {
    assertTrue(check("78", "7581499"));
  }

  @Test
  public void test78_02() throws Exception
  {
    assertTrue(check("78", "9999999981"));
  }

  @Test
  public void test81_01() throws Exception
  {
    assertTrue(check("81", "0646440"));
  }

  @Test
  public void test81_02() throws Exception
  {
    assertTrue(check("81", "1359100"));
  }

  @Test
  public void test82_01() throws Exception
  {
    assertTrue(check("82", "123897"));
  }

  @Test
  public void test82_02() throws Exception
  {
    assertTrue(check("82", "3199500501"));
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
  public void test85_01() throws Exception
  {
    assertTrue(check("85", "0001156071"));
  }

  @Test
  public void test85_02() throws Exception
  {
    assertTrue(check("85", "0001156136"));
  }

  @Test
  public void test85_03() throws Exception
  {
    assertTrue(check("85", "0000156078"));
  }

  @Test
  public void test85_04() throws Exception
  {
    assertTrue(check("85", "0000156071"));
  }

  @Test
  public void test85_05() throws Exception
  {
    assertTrue(check("85", "3199100002"));
  }

  @Test
  public void test86_01() throws Exception
  {
    assertTrue(check("86", "340968"));
  }

  @Test
  public void test86_02() throws Exception
  {
    assertTrue(check("86", "1001171"));
  }

  @Test
  public void test86_03() throws Exception
  {
    assertTrue(check("86", "1009588"));
  }

  @Test
  public void test86_04() throws Exception
  {
    assertTrue(check("86", "123897"));
  }

  @Test
  public void test86_05() throws Exception
  {
    assertTrue(check("86", "340960"));
  }

  @Test
  public void test87_01() throws Exception
  {
    assertTrue(check("87", "0000000406"));
  }

  @Test
  public void test87_02() throws Exception
  {
    assertTrue(check("87", "0000051768"));
  }

  @Test
  public void test87_03() throws Exception
  {
    assertTrue(check("87", "0010701590"));
  }

  @Test
  public void test87_04() throws Exception
  {
    assertTrue(check("87", "0010720185"));
  }

  @Test
  public void test87_05() throws Exception
  {
    assertTrue(check("87", "0000100005"));
  }

  @Test
  public void test87_06() throws Exception
  {
    assertTrue(check("87", "0000393814"));
  }

  @Test
  public void test87_07() throws Exception
  {
    assertTrue(check("87", "0000950360"));
  }

  @Test
  public void test87_08() throws Exception
  {
    assertTrue(check("87", "3199500501"));
  }

  @Test
  public void test88_01() throws Exception
  {
    assertTrue(check("88", "2525259"));
  }

  @Test
  public void test88_02() throws Exception
  {
    assertTrue(check("88", "1000500"));
  }

  @Test
  public void test88_03() throws Exception
  {
    assertTrue(check("88", "90013000"));
  }

  @Test
  public void test88_04() throws Exception
  {
    assertTrue(check("88", "92525253"));
  }

  @Test
  public void test88_05() throws Exception
  {
    assertTrue(check("88", "99913003"));
  }

  @Test
  public void test90_01() throws Exception
  {
    assertTrue(check("90", "0001975641"));
  }

  @Test
  public void test90_02() throws Exception
  {
    assertTrue(check("90", "0001988654"));
  }

  @Test
  public void test90_03() throws Exception
  {
    assertTrue(check("90", "0000863530"));
  }

  @Test
  public void test90_04() throws Exception
  {
    assertTrue(check("90", "0000784451"));
  }

  @Test
  public void test90_05() throws Exception
  {
    assertTrue(check("90", "0000654321"));
  }

  @Test
  public void test90_06() throws Exception
  {
    assertTrue(check("90", "0000824491"));
  }

  @Test
  public void test90_07() throws Exception
  {
    assertTrue(check("90", "0000677747"));
  }

  @Test
  public void test90_08() throws Exception
  {
    assertTrue(check("90", "0000840507"));
  }

  @Test
  public void test90_09() throws Exception
  {
    assertTrue(check("90", "0000996663"));
  }

  @Test
  public void test90_10() throws Exception
  {
    assertTrue(check("90", "0000666034"));
  }

  @Test
  public void test90_11() throws Exception
  {
    assertTrue(check("90", "0099100002"));
  }

  @Test
  public void test91_01() throws Exception
  {
    assertTrue(check("91", "2974118000"));
  }

  @Test
  public void test91_02() throws Exception
  {
    assertTrue(check("91", "5281741000"));
  }

  @Test
  public void test91_03() throws Exception
  {
    assertTrue(check("91", "9952810000"));
  }

  @Test
  public void test91_04() throws Exception
  {
    assertTrue(check("91", "2974117000"));
  }

  @Test
  public void test91_05() throws Exception
  {
    assertTrue(check("91", "5281770000"));
  }

  @Test
  public void test91_06() throws Exception
  {
    assertTrue(check("91", "9952812000"));
  }

  @Test
  public void test91_07() throws Exception
  {
    assertTrue(check("91", "8840019000"));
  }

  @Test
  public void test91_08() throws Exception
  {
    assertTrue(check("91", "8840050000"));
  }

  @Test
  public void test91_09() throws Exception
  {
    assertTrue(check("91", "8840087000"));
  }

  @Test
  public void test91_10() throws Exception
  {
    assertTrue(check("91", "8840045000"));
  }

  @Test
  public void test91_11() throws Exception
  {
    assertTrue(check("91", "8840012000"));
  }

  @Test
  public void test91_12() throws Exception
  {
    assertTrue(check("91", "8840012000"));
  }

  @Test
  public void test91_13() throws Exception
  {
    assertTrue(check("91", "8840080000"));
  }

  @Test
  public void test94_01() throws Exception
  {
    assertTrue(check("94", "6782533003"));
  }

  @Test
  public void test95_01() throws Exception
  {
    assertTrue(check("95", "0068007003"));
  }

  @Test
  public void test95_02() throws Exception
  {
    assertTrue(check("95", "0847321750"));
  }

  @Test
  public void test95_03() throws Exception
  {
    assertTrue(check("95", "6450060494"));
  }

  @Test
  public void test95_04() throws Exception
  {
    assertTrue(check("95", "6454000003"));
  }

  @Test
  public void test96_01() throws Exception
  {
    assertTrue(check("96", "0000254100"));
  }

  @Test
  public void test96_02() throws Exception
  {
    assertTrue(check("96", "9421000009"));
  }

  @Test
  public void test96_03() throws Exception
  {
    assertTrue(check("96", "0000000208"));
  }

  @Test
  public void test96_04() throws Exception
  {
    assertTrue(check("96", "0101115152"));
  }

  @Test
  public void test96_05() throws Exception
  {
    assertTrue(check("96", "0301204301"));
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
  public void test99_01() throws Exception
  {
    assertTrue(check("99", "0068007003"));
  }

  @Test
  public void test99_02() throws Exception
  {
    assertTrue(check("99", "0847321750"));
  }

  @Test
  public void testA0_01() throws Exception
  {
    assertTrue(check("A0", "521003287"));
  }

  @Test
  public void testA0_02() throws Exception
  {
    assertTrue(check("A0", "54500"));
  }

  @Test
  public void testA0_03() throws Exception
  {
    assertTrue(check("A0", "3287"));
  }

  @Test
  public void testA0_04() throws Exception
  {
    assertTrue(check("A0", "18761"));
  }

  @Test
  public void testA0_05() throws Exception
  {
    assertTrue(check("A0", "28290"));
  }

  @Test
  public void testA1_01() throws Exception
  {
    assertTrue(check("A1", "0010030005"));
  }

  @Test
  public void testA1_02() throws Exception
  {
    assertTrue(check("A1", "0010030997"));
  }

  @Test
  public void testA1_03() throws Exception
  {
    assertTrue(check("A1", "1010030054"));
  }

  @Test
  public void testA2_01() throws Exception
  {
    assertTrue(check("A2", "3456789019"));
  }

  @Test
  public void testA2_02() throws Exception
  {
    assertTrue(check("A2", "5678901231"));
  }

  @Test
  public void testA2_03() throws Exception
  {
    assertTrue(check("A2", "6789012348"));
  }

  @Test
  public void testA2_04() throws Exception
  {
    assertTrue(check("A2", "3456789012"));
  }

  @Test
  public void testA3_01() throws Exception
  {
    assertTrue(check("A3", "1234567897"));
  }

  @Test
  public void testA3_02() throws Exception
  {
    assertTrue(check("A3", "0123456782"));
  }

  @Test
  public void testA3_03() throws Exception
  {
    assertTrue(check("A3", "9876543210"));
  }

  @Test
  public void testA3_04() throws Exception
  {
    assertTrue(check("A3", "1234567890"));
  }

  @Test
  public void testA3_05() throws Exception
  {
    assertTrue(check("A3", "0123456789"));
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
  public void testA5_01() throws Exception
  {
    assertTrue(check("A5", "9941510001"));
  }

  @Test
  public void testA5_02() throws Exception
  {
    assertTrue(check("A5", "9961230019"));
  }

  @Test
  public void testA5_03() throws Exception
  {
    assertTrue(check("A5", "9380027210"));
  }

  @Test
  public void testA5_04() throws Exception
  {
    assertTrue(check("A5", "9932290910"));
  }

  @Test
  public void testA5_05() throws Exception
  {
    assertTrue(check("A5", "0000251437"));
  }

  @Test
  public void testA5_06() throws Exception
  {
    assertTrue(check("A5", "0007948344"));
  }

  @Test
  public void testA5_07() throws Exception
  {
    assertTrue(check("A5", "0000159590"));
  }

  @Test
  public void testA5_08() throws Exception
  {
    assertTrue(check("A5", "0000051640"));
  }

  @Test
  public void testA6_01() throws Exception
  {
    assertTrue(check("A6", "800048548"));
  }

  @Test
  public void testA6_02() throws Exception
  {
    assertTrue(check("A6", "0855000014"));
  }

  @Test
  public void testA6_03() throws Exception
  {
    assertTrue(check("A6", "17"));
  }

  @Test
  public void testA6_04() throws Exception
  {
    assertTrue(check("A6", "55300030"));
  }

  @Test
  public void testA6_05() throws Exception
  {
    assertTrue(check("A6", "150178033"));
  }

  @Test
  public void testA6_06() throws Exception
  {
    assertTrue(check("A6", "600003555"));
  }

  @Test
  public void testA6_07() throws Exception
  {
    assertTrue(check("A6", "900291823"));
  }

  @Test
  public void testA7_01() throws Exception
  {
    assertTrue(check("A7", "19010008"));
  }

  @Test
  public void testA7_02() throws Exception
  {
    assertTrue(check("A7", "19010438"));
  }

  @Test
  public void testA7_03() throws Exception
  {
    assertTrue(check("A7", "19010660"));
  }

  @Test
  public void testA7_04() throws Exception
  {
    assertTrue(check("A7", "19010876"));
  }

  @Test
  public void testA7_05() throws Exception
  {
    assertTrue(check("A7", "209010892"));
  }

  @Test
  public void testA8_01() throws Exception
  {
    assertTrue(check("A8", "7436661"));
  }

  @Test
  public void testA8_02() throws Exception
  {
    assertTrue(check("A8", "7436670"));
  }

  @Test
  public void testA8_03() throws Exception
  {
    assertTrue(check("A8", "1359100"));
  }

  @Test
  public void testA8_04() throws Exception
  {
    assertTrue(check("A8", "7436660"));
  }

  @Test
  public void testA8_05() throws Exception
  {
    assertTrue(check("A8", "7436678"));
  }

  @Test
  public void testA8_06() throws Exception
  {
    assertTrue(check("A8", "0003503398"));
  }

  @Test
  public void testA8_07() throws Exception
  {
    assertTrue(check("A8", "0001340967"));
  }

  @Test
  public void testA9_01() throws Exception
  {
    assertTrue(check("A9", "5043608"));
  }

  @Test
  public void testA9_02() throws Exception
  {
    assertTrue(check("A9", "86725"));
  }

  @Test
  public void testA9_03() throws Exception
  {
    assertTrue(check("A9", "504360"));
  }

  @Test
  public void testA9_04() throws Exception
  {
    assertTrue(check("A9", "822035"));
  }

  @Test
  public void testA9_05() throws Exception
  {
    assertTrue(check("A9", "32577083"));
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
  public void testB1_01() throws Exception
  {
    assertTrue(check("B1", "1434253150"));
  }

  @Test
  public void testB1_02() throws Exception
  {
    assertTrue(check("B1", "2746315471"));
  }

  @Test
  public void testB1_03() throws Exception
  {
    assertTrue(check("B1", "7414398260"));
  }

  @Test
  public void testB1_04() throws Exception
  {
    assertTrue(check("B1", "8347251693"));
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
  public void testB3_01() throws Exception
  {
    assertTrue(check("B3", "1000000060"));
  }

  @Test
  public void testB3_02() throws Exception
  {
    assertTrue(check("B3", "0000000140"));
  }

  @Test
  public void testB3_03() throws Exception
  {
    assertTrue(check("B3", "0000000019"));
  }

  @Test
  public void testB3_04() throws Exception
  {
    assertTrue(check("B3", "1002798417"));
  }

  @Test
  public void testB3_05() throws Exception
  {
    assertTrue(check("B3", "8409915001"));
  }

  @Test
  public void testB3_06() throws Exception
  {
    assertTrue(check("B3", "9635000101"));
  }

  @Test
  public void testB3_07() throws Exception
  {
    assertTrue(check("B3", "9730200100"));
  }

  @Test
  public void testB5_01() throws Exception
  {
    assertTrue(check("B5", "0159006955"));
  }

  @Test
  public void testB5_02() throws Exception
  {
    assertTrue(check("B5", "2000123451"));
  }

  @Test
  public void testB5_03() throws Exception
  {
    assertTrue(check("B5", "1151043216"));
  }

  @Test
  public void testB5_04() throws Exception
  {
    assertTrue(check("B5", "9000939033"));
  }

  @Test
  public void testB5_05() throws Exception
  {
    assertTrue(check("B5", "0123456782"));
  }

  @Test
  public void testB5_06() throws Exception
  {
    assertTrue(check("B5", "0130098767"));
  }

  @Test
  public void testB5_07() throws Exception
  {
    assertTrue(check("B5", "1045000252"));
  }

  @Test
  public void testB6_01() throws Exception
  {
    assertTrue(check("B6", "9110000000"));
  }

  @Test
  public void testB6_02() throws Exception
  {
    assertTrue(check("B6", "0269876545"));
  }

  @Test
  public void testB6_03() throws Exception
  {
    assertTrue(check("B6", "80053782", "487310018"));
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
  public void testB8_01() throws Exception
  {
    assertTrue(check("B8", "0734192657"));
  }

  @Test
  public void testB8_02() throws Exception
  {
    assertTrue(check("B8", "6932875274"));
  }

  @Test
  public void testB8_03() throws Exception
  {
    assertTrue(check("B8", "3145863029"));
  }

  @Test
  public void testB8_04() throws Exception
  {
    assertTrue(check("B8", "2938692523"));
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
  public void testC0_01() throws Exception
  {
    assertTrue(check("C0", "13051172", "43001500"));
  }

  @Test
  public void testC0_02() throws Exception
  {
    assertTrue(check("C0", "13051172", "48726458"));
  }

  @Test
  public void testC0_03() throws Exception
  {
    assertTrue(check("C0", "0082335729"));
  }

  @Test
  public void testC0_04() throws Exception
  {
    assertTrue(check("C0", "0734192657"));
  }

  @Test
  public void testC0_05() throws Exception
  {
    assertTrue(check("C0", "6932875274"));
  }

  @Test
  public void testC1_01() throws Exception
  {
    assertTrue(check("C1", "0446786040"));
  }

  @Test
  public void testC1_02() throws Exception
  {
    assertTrue(check("C1", "0478046940"));
  }

  @Test
  public void testC1_03() throws Exception
  {
    assertTrue(check("C1", "0701625830"));
  }

  @Test
  public void testC1_04() throws Exception
  {
    assertTrue(check("C1", "0701625840"));
  }

  @Test
  public void testC1_05() throws Exception
  {
    assertTrue(check("C1", "0882095630"));
  }

  @Test
  public void testC1_06() throws Exception
  {
    assertTrue(check("C1", "5432112349"));
  }

  @Test
  public void testC1_07() throws Exception
  {
    assertTrue(check("C1", "5543223456"));
  }

  @Test
  public void testC1_08() throws Exception
  {
    assertTrue(check("C1", "5654334563"));
  }

  @Test
  public void testC1_09() throws Exception
  {
    assertTrue(check("C1", "5765445670"));
  }

  @Test
  public void testC1_10() throws Exception
  {
    assertTrue(check("C1", "5876556788"));
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
  public void testC5_01() throws Exception
  {
    assertTrue(check("C5", "0000301168"));
  }

  @Test
  public void testC5_02() throws Exception
  {
    assertTrue(check("C5", "0000302554"));
  }

  @Test
  public void testC5_03() throws Exception
  {
    assertTrue(check("C5", "0300020050"));
  }

  @Test
  public void testC5_04() throws Exception
  {
    assertTrue(check("C5", "0300566000"));
  }

  @Test
  public void testC5_05() throws Exception
  {
    assertTrue(check("C5", "1000061378"));
  }

  @Test
  public void testC5_06() throws Exception
  {
    assertTrue(check("C5", "1000061412"));
  }

  @Test
  public void testC5_07() throws Exception
  {
    assertTrue(check("C5", "4450164064"));
  }

  @Test
  public void testC5_08() throws Exception
  {
    assertTrue(check("C5", "4863476104"));
  }

  @Test
  public void testC5_09() throws Exception
  {
    assertTrue(check("C5", "5000000028"));
  }

  @Test
  public void testC5_10() throws Exception
  {
    assertTrue(check("C5", "5000000391"));
  }

  @Test
  public void testC5_11() throws Exception
  {
    assertTrue(check("C5", "6450008149"));
  }

  @Test
  public void testC5_12() throws Exception
  {
    assertTrue(check("C5", "6800001016"));
  }

  @Test
  public void testC5_13() throws Exception
  {
    assertTrue(check("C5", "9000100012"));
  }

  @Test
  public void testC5_14() throws Exception
  {
    assertTrue(check("C5", "9000210017"));
  }

  @Test
  public void testC5_15() throws Exception
  {
    assertTrue(check("C5", "3060188103"));
  }

  @Test
  public void testC5_16() throws Exception
  {
    assertTrue(check("C5", "3070402023"));
  }

  @Test
  public void testC5_17() throws Exception
  {
    assertTrue(check("C5", "30000025"));
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
  public void testC7_01() throws Exception
  {
    assertTrue(check("C7", "3500022"));
  }

  @Test
  public void testC7_02() throws Exception
  {
    assertTrue(check("C7", "38150900"));
  }

  @Test
  public void testC7_03() throws Exception
  {
    assertTrue(check("C7", "600103660"));
  }

  @Test
  public void testC7_04() throws Exception
  {
    assertTrue(check("C7", "39101181"));
  }

  @Test
  public void testC7_05() throws Exception
  {
    assertTrue(check("C7", "94012341"));
  }

  @Test
  public void testC7_06() throws Exception
  {
    assertTrue(check("C7", "5073321010"));
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
  public void testC9_01() throws Exception
  {
    assertTrue(check("C9", "3456789019"));
  }

  @Test
  public void testC9_02() throws Exception
  {
    assertTrue(check("C9", "5678901231"));
  }

  @Test
  public void testC9_03() throws Exception
  {
    assertTrue(check("C9", "0123456789"));
  }

  @Test
  public void testD0_01() throws Exception
  {
    assertTrue(check("D0", "6100272324"));
  }

  @Test
  public void testD0_02() throws Exception
  {
    assertTrue(check("D0", "6100273479"));
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
  public void testD2_01() throws Exception
  {
    assertTrue(check("D2", "189912137"));
  }

  @Test
  public void testD2_02() throws Exception
  {
    assertTrue(check("D2", "235308215"));
  }

  @Test
  public void testD2_03() throws Exception
  {
    assertTrue(check("D2", "4455667784"));
  }

  @Test
  public void testD2_04() throws Exception
  {
    assertTrue(check("D2", "1234567897"));
  }

  @Test
  public void testD2_05() throws Exception
  {
    assertTrue(check("D2", "51181008"));
  }

  @Test
  public void testD2_06() throws Exception
  {
    assertTrue(check("D2", "71214205"));
  }

  @Test
  public void testD3_01() throws Exception
  {
    assertTrue(check("D3", "1600169591"));
  }

  @Test
  public void testD3_02() throws Exception
  {
    assertTrue(check("D3", "1600189151"));
  }

  @Test
  public void testD3_03() throws Exception
  {
    assertTrue(check("D3", "1800084079"));
  }

  @Test
  public void testD3_04() throws Exception
  {
    assertTrue(check("D3", "6019937007"));
  }

  @Test
  public void testD3_05() throws Exception
  {
    assertTrue(check("D3", "6021354007"));
  }

  @Test
  public void testD3() throws Exception
  {
    assertTrue(check("D3", "6030642006"));
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
  public void testD6_01() throws Exception
  {
    assertTrue(check("D6", "3409"));
  }

  @Test
  public void testD6_02() throws Exception
  {
    assertTrue(check("D6", "585327"));
  }

  @Test
  public void testD6_03() throws Exception
  {
    assertTrue(check("D6", "1650513"));
  }

  @Test
  public void testD6_04() throws Exception
  {
    assertTrue(check("D6", "3601671056"));
  }

  @Test
  public void testD6_05() throws Exception
  {
    assertTrue(check("D6", "4402001046"));
  }

  @Test
  public void testD6_06() throws Exception
  {
    assertTrue(check("D6", "6100268241"));
  }

  @Test
  public void testD6_07() throws Exception
  {
    assertTrue(check("D6", "7001000681"));
  }

  @Test
  public void testD6_08() throws Exception
  {
    assertTrue(check("D6", "9000111105"));
  }

  @Test
  public void testD6_09() throws Exception
  {
    assertTrue(check("D6", "9001291005"));
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
  public void testD9_01() throws Exception
  {
    assertTrue(check("D9", "1234567897"));
  }

  @Test
  public void testD9_02() throws Exception
  {
    assertTrue(check("D9", "0123456782"));
  }

  @Test
  public void testD9_03() throws Exception
  {
    assertTrue(check("D9", "9876543210"));
  }

  @Test
  public void testD9_04() throws Exception
  {
    assertTrue(check("D9", "1234567890"));
  }

  @Test
  public void testD9_05() throws Exception
  {
    assertTrue(check("D9", "0123456789"));
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
