/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.JUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestIBAN.class, TestPruefziffer.class,
    TestSEPALand.class, TestUeberweisung.class })
public class ATestSuite
{
  // Nothing to do
}
