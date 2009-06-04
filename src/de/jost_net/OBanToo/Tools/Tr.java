/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright 2009 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.Tools;

import java.util.ArrayList;

/**
 * Translate Characters <br>
 * Quelle:
 * http://www.utf8-zeichentabelle.de/unicode-utf8-table.pl?number=1024&htmlent=1
 * 
 * @author Heiner Jostkleigrewe
 */
public class Tr
{

  public final static void main(String[] args)
  {
    System.out.println(normalizeUTF8("éèBlaÀÑ\u00ff\u0100"));
  }

  private static ArrayList<String> fromarray = new ArrayList<String>();

  private static ArrayList<String> toarray = new ArrayList<String>();

  static
  {
    add("À", "A");
    add("Á", "A");
    add("Â", "A");
    add("Ã", "A");
    add("Å", "A");
    add("Æ", "Ä");
    add("Ç", "C");
    add("È", "E");
    add("É", "E");
    add("Ê", "E");
    add("Ë", "E");
    add("Ì", "I");
    add("Í", "I");
    add("Î", "I");
    add("Ï", "I");
    add("Ð", "D");
    add("Ñ", "N");
    add("Ò", "O");
    add("Ó", "O");
    add("Ô", "O");
    add("Õ", "O");
    add("Ø", "Ö");
    add("Ù", "U");
    add("Ú", "U");
    add("Û", "U");
    add("Ý", "Y");
    add("à", "a");
    add("á", "a");
    add("â", "a");
    add("ã", "a");
    add("å", "a");
    add("æ", "ä");
    add("ç", "c");
    add("è", "e");
    add("é", "e");
    add("ê", "e");
    add("ë", "e");
    add("ì", "i");
    add("í", "i");
    add("î", "i");
    add("ï", "i");
    add("ñ", "n");
    add("ò", "o");
    add("ó", "o");
    add("ô", "o");
    add("õ", "o");
    add("ø", "ö");
    add("ù", "u");
    add("ú", "u");
    add("û", "u");
    add("ý", "y");
    add("ÿ", "y");
    add("\u0100", "A");
    add("\u0101", "a");
    add("\u0102", "A");
    add("\u0103", "a");
    add("\u0104", "A");
    add("\u0105", "a");
    add("\u0106", "C");
    add("\u0107", "c");
    add("\u0108", "C");
    add("\u0109", "c");
    add("\u010A", "C");
    add("\u010B", "c");
    add("\u010C", "C");
    add("\u010D", "c");
    add("\u010E", "D");
    add("\u010F", "d");

    add("\u0110", "D");
    add("\u0111", "d");
    add("\u0112", "E");
    add("\u0113", "e");
    add("\u0114", "E");
    add("\u0115", "e");
    add("\u0116", "E");
    add("\u0117", "e");
    add("\u0118", "E");
    add("\u0119", "e");
    add("\u011A", "E");
    add("\u011B", "e");
    add("\u011C", "G");
    add("\u011D", "g");
    add("\u011E", "G");
    add("\u011F", "g");
    add("\u0120", "G");
    add("\u0121", "g");
    add("\u0122", "G");
    add("\u0123", "g");
    add("\u0124", "");
    add("\u0125", "e");
    add("\u0126", "E");
    add("\u0127", "e");
    add("\u0128", "E");
    add("\u0129", "e");
    add("\u012A", "E");
    add("\u012B", "e");
    add("\u012C", "G");
    add("\u012D", "g");
    add("\u012E", "G");
    add("\u012F", "g");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0104", "a");
    add("\u0105", "a");
    add("\u0106", "C");
    add("\u0107", "c");// LATIN SMALL LETTER C WITH ACUTE
    add("\u0108", "C"); // LATIN CAPITAL LETTER C WITH CIRCUMFLEX
    add("\u0109", "c"); // LATIN SMALL LETTER C WITH CIRCUMFLEX
    add("\u010A", "C"); // LATIN CAPITAL LETTER C WITH DOT ABOVE
    add("\u010B", "c"); // LATIN SMALL LETTER C WITH DOT ABOVE
    add("\u010C", "C"); // LATIN CAPITAL LETTER C WITH CARON
    add("\u010D", ""); // LATIN SMALL LETTER C WITH CARON
    add("\u010E", "D"); // LATIN CAPITAL LETTER D WITH CARON
    add("\u010F", "d"); // LATIN SMALL LETTER D WITH CARON
    add("\u0110", "D"); // LATIN CAPITAL LETTER D WITH STROKE
    add("\u0111", "d"); // LATIN SMALL LETTER D WITH STROKE
    add("\u0112", "E"); // LATIN CAPITAL LETTER E WITH MACRON
    add("\u0113", "e"); // LATIN SMALL LETTER E WITH MACRON
    add("\u0114", "E"); // LATIN CAPITAL LETTER E WITH BREVE
    add("\u0115", "e"); // LATIN SMALL LETTER E WITH BREVE
    add("\u0116", "E"); // LATIN CAPITAL LETTER E WITH DOT ABOVE
    add("\u0117", "e"); // LATIN SMALL LETTER E WITH DOT ABOVE
    add("\u0118", "E"); // LATIN CAPITAL LETTER E WITH OGONEK
    add("\u0119", "e"); // LATIN SMALL LETTER E WITH OGONEK
    add("\u011A", "E"); // LATIN CAPITAL LETTER E WITH CARON
    add("\u011B", "e"); // LATIN SMALL LETTER E WITH CARON
    add("\u011C", "G"); // LATIN CAPITAL LETTER G WITH CIRCUMFLEX
    add("\u0011D", "g"); // LATIN SMALL LETTER G WITH CIRCUMFLEX
    add("\u011E", "G"); // LATIN CAPITAL LETTER G WITH BREVE
    add("\u011F", "g"); // LATIN SMALL LETTER G WITH BREVE
    add("\u0120", "G"); // LATIN CAPITAL LETTER G WITH DOT ABOVE
    add("\u0121", "G"); // LATIN SMALL LETTER G WITH DOT ABOVE
    add("\u0122", "G"); // LATIN CAPITAL LETTER G WITH CEDILLA
    add("\u0123", "g"); // LATIN SMALL LETTER G WITH CEDILLA
    add("\u0124", "H"); // LATIN CAPITAL LETTER H WITH CIRCUMFLEX
    add("\u0125", "h"); // LATIN SMALL LETTER H WITH CIRCUMFLEX
    add("\u0126", "H"); // LATIN CAPITAL LETTER H WITH STROKE
    add("\u0127", "h"); // LATIN SMALL LETTER H WITH STROKE
    add("\u0128", "I"); // LATIN CAPITAL LETTER I WITH TILDE
    add("\u0129", "i"); // LATIN SMALL LETTER I WITH TILDE
    add("\u012A", "I"); // LATIN CAPITAL LETTER I WITH MACRON
    add("\u012B", "i"); // LATIN SMALL LETTER I WITH MACRON
    add("\u012C", "I"); // LATIN CAPITAL LETTER I WITH BREVE
    add("\u012D", "i"); // LATIN SMALL LETTER I WITH BREVE
    add("\u012E", "I"); // LATIN CAPITAL LETTER I WITH OGONEK
    add("\u012F", "i"); // LATIN SMALL LETTER I WITH OGONEK
    add("\u0130", "I"); // LATIN CAPITAL LETTER I WITH DOT ABOVE
    add("\u0131", "i"); // LATIN SMALL LETTER DOTLESS I
    add("\u0134", "J"); // LATIN CAPITAL LETTER J WITH CIRCUMFLEX
    add("\u0135", "j"); // LATIN SMALL LETTER J WITH CIRCUMFLEX
    add("\u0136", "K"); // LATIN CAPITAL LETTER K WITH CEDILLA
    add("\u0137", "k"); // LATIN SMALL LETTER K WITH CEDILLA
    add("\u0138", "k"); // LATIN SMALL LETTER KRA
    add("\u0139", "L"); // LATIN CAPITAL LETTER L WITH ACUTE
    add("\u013A", "l"); // LATIN SMALL LETTER L WITH ACUTE
    add("\u013B", "L"); // LATIN CAPITAL LETTER L WITH CEDILLA
    add("\u013C", "l"); // LATIN SMALL LETTER L WITH CEDILLA
    add("\u013D", "L"); // LATIN CAPITAL LETTER L WITH CARON
    add("\u013E", "l"); // LATIN SMALL LETTER L WITH CARON
    add("\u013F", "L"); // LATIN CAPITAL LETTER L WITH MIDDLE DOT
    add("\u0140", "l"); // LATIN SMALL LETTER L WITH MIDDLE DOT
    add("\u0141", "L"); // LATIN CAPITAL LETTER L WITH STROKE
    add("\u0142", "l"); // LATIN SMALL LETTER L WITH STROKE
    add("\u0143", "N"); // LATIN CAPITAL LETTER N WITH ACUTE
    add("\u0144", "n"); // LATIN SMALL LETTER N WITH ACUTE
    add("\u0145", "N"); // LATIN CAPITAL LETTER N WITH CEDILLA
    add("\u0146", "n"); // LATIN SMALL LETTER N WITH CEDILLA
    add("\u0147", "N"); // LATIN CAPITAL LETTER N WITH CARON
    add("\u0148", "n"); // LATIN SMALL LETTER N WITH CARON
    add("\u0149", "n"); // LATIN SMALL LETTER N PRECEDED BY APOSTROPHE
    add("\u014C", "O"); // LATIN CAPITAL LETTER O WITH MACRON
    add("\u014D", "o"); // LATIN SMALL LETTER O WITH MACRON
    add("\u014E", "O"); // LATIN CAPITAL LETTER O WITH BREVE
    add("\u014F", "o"); // LATIN SMALL LETTER O WITH BREVE
    add("\u0150", "O"); // LATIN CAPITAL LETTER O WITH DOUBLE ACUTE
    add("\u0151", "o"); // LATIN SMALL LETTER O WITH DOUBLE ACUTE
    add("\u0152", "Ö"); // LATIN CAPITAL LIGATURE OE
    add("\u0153", "ö"); // LATIN SMALL LIGATURE OE
    add("\u0154", "R"); // LATIN CAPITAL LETTER R WITH ACUTE
    add("\u0155", "r"); // LATIN SMALL LETTER R WITH ACUTE
    add("\u0156", "R"); // LATIN CAPITAL LETTER R WITH CEDILLA
    add("\u0157", "r"); // LATIN SMALL LETTER R WITH CEDILLA
    add("\u0158", "R"); // LATIN CAPITAL LETTER R WITH CARON
    add("\u0159", "r"); // LATIN SMALL LETTER R WITH CARON
    add("\u015A", "S"); // LATIN CAPITAL LETTER S WITH ACUTE
    add("\u015B", "s"); // LATIN SMALL LETTER S WITH ACUTE
    add("\u015C", "S"); // LATIN CAPITAL LETTER S WITH CIRCUMFLEX
    add("\u015D", "s"); // LATIN SMALL LETTER S WITH CIRCUMFLEX
    add("\u015E", "S"); // LATIN CAPITAL LETTER S WITH CEDILLA
    add("\u015F", "s"); // LATIN SMALL LETTER S WITH CEDILLA
    add("\u0160", "S"); // LATIN CAPITAL LETTER S WITH CARON
    add("\u0161", "s"); // LATIN SMALL LETTER S WITH CARON
    add("\u0162", "T"); // LATIN CAPITAL LETTER T WITH CEDILLA
    add("\u0163", "t"); // LATIN SMALL LETTER T WITH CEDILLA
    add("\u0164", "T"); // LATIN CAPITAL LETTER T WITH CARON
    add("\u0165", "t"); // LATIN SMALL LETTER T WITH CARON
    add("\u0166", "T"); // LATIN CAPITAL LETTER T WITH STROKE
    add("\u0167", "t"); // LATIN SMALL LETTER T WITH STROKE
    add("\u0168", "U"); // LATIN CAPITAL LETTER U WITH TILDE
    add("\u0169", "u"); // LATIN SMALL LETTER U WITH TILDE
    add("\u016A", "U"); // LATIN CAPITAL LETTER U WITH MACRON
    add("\u016B", "u"); // LATIN SMALL LETTER U WITH MACRON
    add("\u016C", "U"); // LATIN CAPITAL LETTER U WITH BREVE
    add("\u016D", "u"); // LATIN SMALL LETTER U WITH BREVE
    add("\u016E", "U"); // LATIN CAPITAL LETTER U WITH RING ABOVE
    add("\u016F", "u"); // LATIN SMALL LETTER U WITH RING ABOVE
    add("\u0170", "U"); // LATIN CAPITAL LETTER U WITH DOUBLE ACUTE
    add("\u0171", "u"); // LATIN SMALL LETTER U WITH DOUBLE ACUTE
    add("\u0172", "U"); // LATIN CAPITAL LETTER U WITH OGONEK
    add("\u0173", "u"); // LATIN SMALL LETTER U WITH OGONEK
    add("\u0174", "W"); // LATIN CAPITAL LETTER W WITH CIRCUMFLEX
    add("\u0175", "w"); // LATIN SMALL LETTER W WITH CIRCUMFLEX
    add("\u0176", "Y"); // LATIN CAPITAL LETTER Y WITH CIRCUMFLEX
    add("\u0177", "y"); // LATIN SMALL LETTER Y WITH CIRCUMFLEX
    add("\u0178", "Y"); // LATIN CAPITAL LETTER Y WITH DIAERESIS
    add("\u0179", "Z"); // LATIN CAPITAL LETTER Z WITH ACUTE
    add("\u017A", "z"); // LATIN SMALL LETTER Z WITH ACUTE
    add("\u017B", "Z"); // LATIN CAPITAL LETTER Z WITH DOT ABOVE
    add("\u017C", "z"); // LATIN SMALL LETTER Z WITH DOT ABOVE
    add("\u017D", "Z"); // LATIN CAPITAL LETTER Z WITH CARON
    add("\u017E", "z"); // LATIN SMALL LETTER Z WITH CARON
    add("\u017F", "s"); // LATIN SMALL LETTER LONG S
    add("\u0180", "b"); // LATIN SMALL LETTER B WITH STROKE
    add("\u0181", "B"); // LATIN CAPITAL LETTER B WITH HOOK
    add("\u0182", "B"); // LATIN CAPITAL LETTER B WITH TOPBAR
    add("\u0183", "b"); // LATIN SMALL LETTER B WITH TOPBAR
    add("\u0187", "C"); // LATIN CAPITAL LETTER C WITH HOOK
    add("\u0188", "c"); // LATIN SMALL LETTER C WITH HOOK
    add("\u0189", "D"); // LATIN CAPITAL LETTER AFRICAN D
    add("\u018A", "D"); // LATIN CAPITAL LETTER D WITH HOOK
    add("\u018B", "D"); // LATIN CAPITAL LETTER D WITH TOPBAR
    add("\u018C", "d"); // LATIN SMALL LETTER D WITH TOPBAR
    add("\u0191", "F"); // LATIN CAPITAL LETTER F WITH HOOK
    add("\u0192", "f"); // LATIN SMALL LETTER F WITH HOOK
    add("\u0193", "G"); // LATIN CAPITAL LETTER G WITH HOOK
    add("\u0198", "K"); // LATIN CAPITAL LETTER K WITH HOOK
    add("\u0199", "k"); // LATIN SMALL LETTER K WITH HOOK
    add("\u019A", "l"); // LATIN SMALL LETTER L WITH BAR
    add("\u019D", "N"); // LATIN CAPITAL LETTER N WITH LEFT HOOK
    add("\u019E", "n"); // LATIN SMALL LETTER N WITH LONG RIGHT LEG
    add("\u019F", "O"); // LATIN CAPITAL LETTER O WITH MIDDLE TILDE
    add("\u01A0", "O"); // LATIN CAPITAL LETTER O WITH HORN
    add("\u01A1", "o"); // LATIN SMALL LETTER O WITH HORN
    add("\u01A4", "P"); // LATIN CAPITAL LETTER P WITH HOOK
    add("\u01A5", "p"); // LATIN SMALL LETTER P WITH HOOK
    add("\u01AB", "t"); // LATIN SMALL LETTER T WITH PALATAL HOOK
    add("\u01AC", "T"); // LATIN CAPITAL LETTER T WITH HOOK
    add("\u01AD", "t"); // LATIN SMALL LETTER T WITH HOOK
    add("\u01AE", "T"); // LATIN CAPITAL LETTER T WITH RETROFLEX HOOK
    add("\u01AF", "U"); // LATIN CAPITAL LETTER U WITH HORN
    add("\u01B0", "u"); // LATIN SMALL LETTER U WITH HORN
    add("\u01B2", "V"); // LATIN CAPITAL LETTER V WITH HOOK
    add("\u01B3", "Y"); // LATIN CAPITAL LETTER Y WITH HOOK
    add("\u01B4", "y"); // LATIN SMALL LETTER Y WITH HOOK
    add("\u01B5", "Z"); // LATIN CAPITAL LETTER Z WITH STROKE
    add("\u01B6", "z"); // LATIN SMALL LETTER Z WITH STROKE
    add("\u01CE", "a"); // LATIN SMALL LETTER A WITH CARON
    add("\u01CF", "I"); // LATIN CAPITAL LETTER I WITH CARON
    add("\u01D0", "i"); // LATIN SMALL LETTER I WITH CARON
    add("\u01D1", "O"); // LATIN CAPITAL LETTER O WITH CARON
    add("\u01D2", "o"); // LATIN SMALL LETTER O WITH CARON
    add("\u01D3", "U"); // LATIN CAPITAL LETTER U WITH CARON
    add("\u01D4", "u"); // LATIN SMALL LETTER U WITH CARON
    add("\u01D5", "U"); // LATIN CAPITAL LETTER U WITH DIAERESIS AND MACRON
    add("\u01D6", "u"); // LATIN SMALL LETTER U WITH DIAERESIS AND MACRON
    add("\u01D7", "U"); // LATIN CAPITAL LETTER U WITH DIAERESIS AND ACUTE
    add("\u01D8", "u"); // LATIN SMALL LETTER U WITH DIAERESIS AND ACUTE
    add("\u01D9", "U"); // LATIN CAPITAL LETTER U WITH DIAERESIS AND CARON
    add("\u01DA", "u"); // LATIN SMALL LETTER U WITH DIAERESIS AND CARON
    add("\u01DB", "U"); // LATIN CAPITAL LETTER U WITH DIAERESIS AND GRAVE
    add("\u01DC", "u"); // LATIN SMALL LETTER U WITH DIAERESIS AND GRAVE
    add("\u01DD", "e"); // LATIN SMALL LETTER TURNED E
    add("\u01DE", "A"); // LATIN CAPITAL LETTER A WITH DIAERESIS AND MACRON
    add("\u01DF", "a"); // LATIN SMALL LETTER A WITH DIAERESIS AND MACRON
    add("\u01E0", "A"); // LATIN CAPITAL LETTER A WITH DOT ABOVE AND MACRON
    add("\u01E1", "a"); // LATIN SMALL LETTER A WITH DOT ABOVE AND MACRON
    add("\u01E2", "Ä"); // LATIN CAPITAL LETTER AE WITH MACRON
    add("\u01E3", "ä"); // LATIN SMALL LETTER AE WITH MACRON
    add("\u01E4", "G"); // LATIN CAPITAL LETTER G WITH STROKE
    add("\u01E5", "g"); // LATIN SMALL LETTER G WITH STROKE
    add("\u01E6", "G"); // LATIN CAPITAL LETTER G WITH CARON
    add("\u01E7", "g"); // LATIN SMALL LETTER G WITH CARON
    add("\u01E8", "K"); // LATIN CAPITAL LETTER K WITH CARON
    add("\u01E9", "k"); // LATIN SMALL LETTER K WITH CARON
    add("\u01EA", "O"); // LATIN CAPITAL LETTER O WITH OGONEK
    add("\u01EB", "o"); // LATIN SMALL LETTER O WITH OGONEK
    add("\u01EC", "O"); // LATIN CAPITAL LETTER O WITH OGONEK AND MACRON
    add("\u01ED", "o"); // LATIN SMALL LETTER O WITH OGONEK AND MACRON
    add("\u01F0", "J"); // LATIN SMALL LETTER J WITH CARON
    add("\u01F4", "G"); // LATIN CAPITAL LETTER G WITH ACUTE
    add("\u01F5", "g"); // LATIN SMALL LETTER G WITH ACUTE
    add("\u01FA", "A"); // LATIN CAPITAL LETTER A WITH RING ABOVE AND ACUTE
    add("\u01FB", "a"); // LATIN SMALL LETTER A WITH RING ABOVE AND ACUTE
    add("\u01FC", "Ä"); // LATIN CAPITAL LETTER AE WITH ACUTE
    add("\u01FD", "ä"); // LATIN SMALL LETTER AE WITH ACUTE
    add("\u01FE", "O"); // LATIN CAPITAL LETTER O WITH STROKE AND ACUTE
    add("\u01FF", "o"); // LATIN SMALL LETTER O WITH STROKE AND ACUTE
    add("\u0200", "A"); // LATIN CAPITAL LETTER A WITH DOUBLE GRAVE
    add("\u0201", "a"); // LATIN SMALL LETTER A WITH DOUBLE GRAVE
    add("\u0202", "A"); // LATIN CAPITAL LETTER A WITH INVERTED BREVE
    add("\u0203", "A"); // LATIN SMALL LETTER A WITH INVERTED BREVE
    add("\u0204", "E"); // LATIN CAPITAL LETTER E WITH DOUBLE GRAVE
    add("\u0205", "e"); // LATIN SMALL LETTER E WITH DOUBLE GRAVE
    add("\u0206", "E"); // LATIN CAPITAL LETTER E WITH INVERTED BREVE
    add("\u0207", "e"); // LATIN SMALL LETTER E WITH INVERTED BREVE
    add("\u0208", "I"); // LATIN CAPITAL LETTER I WITH DOUBLE GRAVE
    add("\u0209", "i"); // LATIN SMALL LETTER I WITH DOUBLE GRAVE
    add("\u020A", "I"); // LATIN CAPITAL LETTER I WITH INVERTED BREVE
    add("\u020B", "i"); // LATIN SMALL LETTER I WITH INVERTED BREVE
    add("\u020C", "O"); // LATIN CAPITAL LETTER O WITH DOUBLE GRAVE
    add("\u020D", "o"); // LATIN SMALL LETTER O WITH DOUBLE GRAVE
    add("\u020E", "O"); // LATIN CAPITAL LETTER O WITH INVERTED BREVE
    add("\u020F", "o"); // LATIN SMALL LETTER O WITH INVERTED BREVE
    add("\u0210", "R"); // LATIN CAPITAL LETTER R WITH DOUBLE GRAVE
    add("\u0211", "R"); // LATIN SMALL LETTER R WITH DOUBLE GRAVE
    add("\u0212", "R"); // LATIN CAPITAL LETTER R WITH INVERTED BREVE
    add("\u0213", "r"); // LATIN SMALL LETTER R WITH INVERTED BREVE
    add("\u0214", "U"); // LATIN CAPITAL LETTER U WITH DOUBLE GRAVE
    add("\u0215", "u"); // LATIN SMALL LETTER U WITH DOUBLE GRAVE
    add("\u0216", "U"); // LATIN CAPITAL LETTER U WITH INVERTED BREVE
    add("\u0217", "u"); // LATIN SMALL LETTER U WITH INVERTED BREVE

  }

  public static String normalizeUTF8(String string)
  {
    return tr(string);
  }

  private static void add(String from, String to)
  {
    fromarray.add(from);
    toarray.add(to);
  }

  private static String tr(String string)
  {
    if (string == null)
    {
      throw new NullPointerException();
    }
    for (int i = 0; i < fromarray.size(); i++)
    {
      string = string.replace(fromarray.get(i), toarray.get(i));
    }
    return string;
  }
}
/*
 * $Log$
 * Revision 1.2  2009/06/04 08:36:13  jost
 * Bugfix
 * Revision 1.1 2009/06/04 08:30:40 jost Umsetzung
 * Sonderzeichen erweitert.
 * 
 */
