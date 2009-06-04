package de.jost_net.OBanToo.Test;

import java.io.FileOutputStream;

import de.jost_net.OBanToo.Dtaus.CSatz;
import de.jost_net.OBanToo.Dtaus.DtausDateiWriter;

public class DtausWriterTest
{
  public DtausWriterTest() throws Exception
  {
    FileOutputStream fos = new FileOutputStream("dtaus");
    DtausDateiWriter dtausDateiWriter = new DtausDateiWriter(fos);
    // Jetzt wird der ASatz gefüllt und geschrieben:
    dtausDateiWriter.setAGutschriftLastschrift("GK");
    dtausDateiWriter.setABLZBank(40050060);
    dtausDateiWriter.setAKundenname("Donald Duck GmbH und Co");
    dtausDateiWriter.setAKonto(123456);
    dtausDateiWriter.writeASatz();
    // Ab hier werden die eigentlichen Zahlungssätze erstellt:
    dtausDateiWriter.setCBLZEndbeguenstigt(10020030);
    dtausDateiWriter.setCKonto(444444);
    dtausDateiWriter.setCTextschluessel(CSatz.TS_UEBERWEISUNGSGUTSCHRIFT);
    dtausDateiWriter.setCInterneKundennummer(1);
    dtausDateiWriter.setCBetragInEuro(1000);
    dtausDateiWriter.setCName("Donald Duck");
    dtausDateiWriter.setCName2("Entenhausen");
    dtausDateiWriter.addCVerwendungszweck("bekannt");
    dtausDateiWriter.addCVerwendungszweck("bla");

    dtausDateiWriter.writeCSatz();

    dtausDateiWriter.setCBLZEndbeguenstigt(80077711);
    dtausDateiWriter.setCKonto(666);
    dtausDateiWriter.setCTextschluessel(CSatz.TS_UEBERWEISUNGSGUTSCHRIFT);
    dtausDateiWriter.setCInterneKundennummer(2);
    dtausDateiWriter.setCBetragInEuro(1000);
    dtausDateiWriter.setCName("Micky Maus");
    dtausDateiWriter.setCName2("Bla Bla Bla");
    dtausDateiWriter.addCVerwendungszweck("la li lu");
    dtausDateiWriter.addCVerwendungszweck("nur der Mann im Mond ");
    dtausDateiWriter.addCVerwendungszweck("schaut zu ");
    dtausDateiWriter.addCVerwendungszweck("wenn die kleinen Kinder");
    dtausDateiWriter.addCVerwendungszweck("schlafen");
    dtausDateiWriter.addCVerwendungszweck("11111111111111");
    dtausDateiWriter.addCVerwendungszweck("22222222222222");
    dtausDateiWriter.addCVerwendungszweck("33333333333333");
    dtausDateiWriter.writeCSatz();
    
    dtausDateiWriter.setCBLZEndbeguenstigt(80077711);
    dtausDateiWriter.setCKonto(666);
    dtausDateiWriter.setCTextschluessel(CSatz.TS_UEBERWEISUNGSGUTSCHRIFT);
    dtausDateiWriter.setCInterneKundennummer(2);
    dtausDateiWriter.setCBetragInEuro(1000);
    dtausDateiWriter.setCName("Andrè-Gérard du Rhône-Hüslü");
    dtausDateiWriter.setCName2("Bla Bla Bla");
    dtausDateiWriter.addCVerwendungszweck("la li lu");
    dtausDateiWriter.addCVerwendungszweck("nur der Mann im Mond ");
    dtausDateiWriter.addCVerwendungszweck("schaut zu ");
    dtausDateiWriter.addCVerwendungszweck("wenn die kleinen Kinder");
    dtausDateiWriter.addCVerwendungszweck("schlafen");
    dtausDateiWriter.addCVerwendungszweck("11111111111111");
    dtausDateiWriter.addCVerwendungszweck("22222222222222");
    dtausDateiWriter.addCVerwendungszweck("33333333333333");
    dtausDateiWriter.writeCSatz();

    // E-Satz schreiben = Ende einer logischen Datei.<br>
    dtausDateiWriter.writeESatz();

    dtausDateiWriter.close();

  }

  public static void main(String[] args)
  {
    try
    {
      new DtausWriterTest();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

}
