/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA.Basislastschrift;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.jost_net.OBanToo.Dtaus.HeaderFooter;
import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.Tools.Util;

/**
 * Ausgabe von SEPA-Basislastschrift-Dateien im PDF-Format <br>
 * <p>
 * Mit dieser Klasse können SEPA-Basislastschrift-Dateien ins PDF-Format
 * konvertiert werden.
 * </p>
 * <p>
 * Die Klasse verfügt über eine Main-Methode und kann daher auch stand-alone
 * genutzt werden. Der Aufruf erfolgt mit <i>java -cp ...
 * de.jost_net.OBanToo.SEPA.Basislastschrift.Basislastschrift2Pdf sepafile
 * pdffile</i>
 * </p>
 * <p>
 * Hinweis! Für die PDF-Generierung wird iText benötigt. iText kann <a
 * href="http://www.lowagie.com/iText/download.html" target=blank>hier</a>
 * bezogen werden. Das iText.jar muß sich im Classpath befinden.
 * </p>
 * 
 * 
 * 
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
public class Basislastschrift2Pdf
{

  public Basislastschrift2Pdf(String sepafile, String pdffile)
      throws IOException, SEPAException, DocumentException, JAXBException
  {
    Basislastschrift bl = new Basislastschrift();
    bl.read(new File(sepafile));
    new Basislastschrift2Pdf(bl, pdffile);
  }

  public Basislastschrift2Pdf(Basislastschrift bl, String pdffile)
      throws IOException, SEPAException, DocumentException
  {
    Document doc = new Document();
    FileOutputStream out = new FileOutputStream(pdffile);

    PdfWriter writer = PdfWriter.getInstance(doc, out);
    doc.setMargins(80, 30, 20, 30); // links, rechts, oben, unten
    doc.addAuthor("OBanToo");
    doc.addTitle("Basislastschrift2PDF");

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String fuss = "Ausgegeben am " + sdf.format(new Date())
        + "           Seite:  ";
    HeaderFooter hf = new HeaderFooter();
    hf.setFooter(fuss);
    writer.setPageEvent(hf);

    doc.open();

    Paragraph pTitle = new Paragraph("SEPA-Basislastschrift-Datei",
        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13));
    pTitle.setAlignment(Element.ALIGN_CENTER);

    outputHeader(bl, doc);
    PdfPTable table = new PdfPTable(5);
    float[] widths = { 80, 95, 85, 40, 30 };
    table.setWidths(widths);
    table.setWidthPercentage(100);
    table.setSpacingBefore(10);
    table.setSpacingAfter(0);

    table.addCell(getDetailCell("Name", Element.ALIGN_CENTER,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell("Verwendungszweck", Element.ALIGN_CENTER,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell("Bankverbindung", Element.ALIGN_CENTER,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell("Mandat", Element.ALIGN_CENTER,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell("Betrag", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.setHeaderRows(1);

    ArrayList<Zahler> zahler = bl.getZahler();
    for (Zahler z : zahler)
    {
      table.addCell(getDetailCell(z.getName(), Element.ALIGN_LEFT));
      table.addCell(getDetailCell(z.getVerwendungszweck(), Element.ALIGN_LEFT));
      table.addCell(getDetailCell(z.getBic() + "\n" + z.getIban(),
          Element.ALIGN_LEFT));
      table.addCell(getDetailCell(
          z.getMandatid() + " " + z.getMandatsequence().getTxt() + "\n"
              + Util.DateTTMMJJJJ(z.getMandatdatum()), Element.ALIGN_LEFT));
      table.addCell(getDetailCell(Util.formatCurrency(z.getBetrag()),
          Element.ALIGN_RIGHT));
    }

    doc.add(table);

    doc.close();
    out.close();
  }

  private void outputHeader(Basislastschrift bl, Document doc)
      throws DocumentException, SEPAException
  {
    PdfPTable table = new PdfPTable(2);
    float[] widths = { 200, 170 };
    table.setWidths(widths);
    table.setWidthPercentage(65);
    table.setSpacingBefore(10);
    table.setSpacingAfter(0);
    table.setHorizontalAlignment(Element.ALIGN_RIGHT);

    table.addCell(getDetailCell("Name", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(bl.getName(), Element.ALIGN_LEFT));

    table.addCell(getDetailCell("Gläubiger-ID", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(bl.getGlaeubigerID(), Element.ALIGN_LEFT));

    table.addCell(getDetailCell("BIC", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(bl.getBIC(), Element.ALIGN_LEFT));

    table.addCell(getDetailCell("IBAN", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(bl.getIBAN(), Element.ALIGN_LEFT));

    // table.addCell(getDetailCell("Fälligkeitsdatum", Element.ALIGN_RIGHT,
    // BaseColor.LIGHT_GRAY));
    // table.addCell(getDetailCell(Util.DateTTMMJJJJ(bl.getFaelligkeitsdatum()),
    // Element.ALIGN_LEFT));

    table.addCell(getDetailCell("Erstellungsdatum", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(Util.DateTTMMJJJJ(bl.getCreationDateTime()),
        Element.ALIGN_LEFT));

    table.addCell(getDetailCell("Anzahl Buchungen", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(bl.getAnzahlBuchungen() + "",
        Element.ALIGN_RIGHT));

    table.addCell(getDetailCell("Kontrollsumme", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(Util.formatCurrency(bl.getKontrollsumme()),
        Element.ALIGN_RIGHT));

    table.addCell(getDetailCell("Message-ID", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(bl.getMessageID(), Element.ALIGN_RIGHT));

    doc.add(table);
  }

  /**
   * Erzeugt eine Zelle der Tabelle.
   * 
   * @param text
   *          der anzuzeigende Text.
   * @param align
   *          die Ausrichtung.
   * @return die erzeugte Zelle.
   */
  private PdfPCell getDetailCell(String text, int align)
  {
    return getDetailCell(text, align, BaseColor.WHITE);
  }

  /**
   * Erzeugt eine Zelle der Tabelle.
   * 
   * @param text
   *          der anzuzeigende Text.
   * @param align
   *          die Ausrichtung.
   * @param backgroundcolor
   *          die Hintergundfarbe.
   * @return die erzeugte Zelle.
   */
  private PdfPCell getDetailCell(String text, int align,
      BaseColor backgroundcolor)
  {
    PdfPCell cell = new PdfPCell(new Phrase(notNull(text), FontFactory.getFont(
        FontFactory.HELVETICA, 8)));
    cell.setHorizontalAlignment(align);
    cell.setBackgroundColor(backgroundcolor);
    return cell;
  }

  /**
   * Erzeugt eine Zelle fuer die uebergebene Zahl.
   * 
   * @param value
   *          die Zahl.
   * @return die erzeugte Zelle.
   */
  // private PdfPCell getDetailCell(double value)
  // {
  // Font f = null;
  // if (value >= 0)
  // f = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL,
  // Color.BLACK);
  // else
  // f = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, Color.RED);
  // PdfPCell cell = new PdfPCell(new Phrase(new DecimalFormat("###,###,##0.00")
  // .format(value), f));
  // cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
  // return cell;
  // }

  /**
   * Gibt einen Leerstring aus, falls der Text null ist.
   * 
   * @param text
   *          der Text.
   * @return der Text oder Leerstring - niemals null.
   */
  private String notNull(String text)
  {
    return text == null ? "" : text;
  }

  public static void main(String[] args)
  {
    if (args.length != 2)
    {
      System.err
          .println("Usage: java -cp ... de.jost_net.OBanToo.Dtaus dtausfile pdffile");
      System.exit(1);
    }
    try
    {
      new Basislastschrift2Pdf(args[0], args[1]);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.exit(2);
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
      System.exit(3);
    }
    catch (DocumentException e)
    {
      e.printStackTrace();
      System.exit(4);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
      System.exit(5);
    }
  }
}
