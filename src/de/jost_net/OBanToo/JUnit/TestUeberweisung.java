/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */

package de.jost_net.OBanToo.JUnit;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.SEPA.Ueberweisung.Empfaenger;
import de.jost_net.OBanToo.SEPA.Ueberweisung.Ueberweisung;

@RunWith(JUnit4.class)
public class TestUeberweisung
{
  @Test
  public void test()
  {
    try
    {
      Ueberweisung ue = new Ueberweisung();
      ue.setBIC("BELADEBEXXX");
      ue.setIBAN("DE86100500000990021440");
      ue.setMessageID("222");
      ue.setName("OBanToo-Verein");
      ue.setSammelbuchung(false);
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, 5);
      ue.setAusfuehrungsdatum(cal.getTime());

      Empfaenger e1 = new Empfaenger();
      e1.setBetrag(new BigDecimal("0.01"));
      e1.setBic("DRESDEFF265");
      e1.setIban("DE32265800700732502200");
      e1.setName("Meier");
      e1.setVerwendungszweck("Testueberweisung");
      e1.setReferenz("12345");
      ue.add(e1);

      Empfaenger e2 = new Empfaenger();
      e2.setBetrag(new BigDecimal("0.01"));
      e2.setBic("DRESDEFF265");
      e2.setIban("DE32265800700732502200");
      e2.setName("Müller");
      e2.setVerwendungszweck("Testueberweisung");
      e2.setReferenz("67890");

      ue.add(e2);

      ue.write(new File("ueberweisung.xml"));
    }
    catch (SEPAException e1)
    {
      fail();
    }
    catch (DatatypeConfigurationException e)
    {
      fail();
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
      fail();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      fail();
    }
  }
}
