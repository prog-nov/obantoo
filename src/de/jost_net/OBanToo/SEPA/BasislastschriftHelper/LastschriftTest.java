package de.jost_net.OBanToo.SEPA.BasislastschriftHelper;

import java.io.File;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXB;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import de.jost_net.OBanToo.SEPA.Basislastschrift.AccountIdentificationSEPA;
import de.jost_net.OBanToo.SEPA.Basislastschrift.BranchAndFinancialInstitutionIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Basislastschrift.CashAccountSEPA1;
import de.jost_net.OBanToo.SEPA.Basislastschrift.ChargeBearerTypeSEPACode;
import de.jost_net.OBanToo.SEPA.Basislastschrift.CustomerDirectDebitInitiationV02;
import de.jost_net.OBanToo.SEPA.Basislastschrift.Document;
import de.jost_net.OBanToo.SEPA.Basislastschrift.FinancialInstitutionIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Basislastschrift.GroupHeaderSDD;
import de.jost_net.OBanToo.SEPA.Basislastschrift.OrganisationIdentificationSEPAChoice;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PartyIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PartyIdentificationSEPA3;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PartyIdentificationSEPA5;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PartySEPA2;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PartySEPAChoice;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PaymentInstructionInformationSDD;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PersonIdentificationSEPA1Choice;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PersonIdentificationSEPA2;
import de.jost_net.OBanToo.SEPA.Basislastschrift.PostalAddressSEPA;
import de.jost_net.OBanToo.SEPA.Basislastschrift.RestrictedPersonIdentificationSEPA;

public class LastschriftTest
{
  public LastschriftTest() throws DatatypeConfigurationException
  {
    Document doc = new Document();
    doc.setCstmrDrctDbtInitn(getCustumerDirectDebitInitiationV02());

    JAXB.marshal(doc, new File("test.xml"));
    JAXB.marshal(doc, System.out);
  }

  private CustomerDirectDebitInitiationV02 getCustumerDirectDebitInitiationV02()
      throws DatatypeConfigurationException
  {
    CustomerDirectDebitInitiationV02 init = new CustomerDirectDebitInitiationV02();
    init.setGrpHdr(getGroupHeader());
    init.getPmtInf().add(getPaymentInstructionInformationSDD());
    return init;
  }

  private GroupHeaderSDD getGroupHeader() throws DatatypeConfigurationException
  {
    GroupHeaderSDD groupHeader = new GroupHeaderSDD();
    // aktuelles Datum und Uhrzeit
    XMLGregorianCalendar creDtTm = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(new GregorianCalendar());
    groupHeader.setCreDtTm(creDtTm);
    // Kontrollsumme
    groupHeader.setCtrlSum(new BigDecimal("12345.23"));
    groupHeader.setInitgPty(getPartyIdentificationSEPA1());
    groupHeader.setMsgId("msgid");
    groupHeader.setNbOfTxs("1234");

    return groupHeader;
  }

  private PartyIdentificationSEPA1 getPartyIdentificationSEPA1()
  {
    PartyIdentificationSEPA1 partyid1 = new PartyIdentificationSEPA1();
    partyid1.setId(getPartySEPAChoice());
    return partyid1;
  }

  private PartySEPAChoice getPartySEPAChoice()

  {
    PartySEPAChoice partysepachoice = new PartySEPAChoice();

    OrganisationIdentificationSEPAChoice organisationidentificationsepachoice = new OrganisationIdentificationSEPAChoice();
    organisationidentificationsepachoice.setBICOrBEI("Hier kommt die BIC rein");
    partysepachoice.setOrgId(organisationidentificationsepachoice);

    PersonIdentificationSEPA1Choice personidentificationsepa1choice = new PersonIdentificationSEPA1Choice();
    // TODO prüfen, was hier bei juristischen Personen rein kommt.
    partysepachoice.setPrvtId(personidentificationsepa1choice);

    return partysepachoice;
  }

  private PaymentInstructionInformationSDD getPaymentInstructionInformationSDD()
  {
    PaymentInstructionInformationSDD pii = new PaymentInstructionInformationSDD();
    pii.setBtchBookg(true); // TODO prüfen
    pii.setCdtr(getPartyIdentificationSEPA5());
    pii.setCdtrAcct(getCashAccountSEPA1());
    pii.setCdtrAgt(getBranchAndFinancialInstitutionIdentificationSEPA1());
    pii.setCdtrSchmeId(getPartyIdentificationSEPA3());
    // pii.setChrgBr(getChargeBearerTypeSEPACode());
    return pii;
  }

  private PartyIdentificationSEPA5 getPartyIdentificationSEPA5()
  {
    PartyIdentificationSEPA5 pi5 = new PartyIdentificationSEPA5();
    pi5.setNm("44444"); // TODO prüfen
    pi5.setPstlAdr(getPostalAddressSEPA());
    return pi5;
  }

  private PostalAddressSEPA getPostalAddressSEPA()
  {
    PostalAddressSEPA pa = new PostalAddressSEPA();
    pa.setCtry("DE"); // TODO prüfen
    return pa;
  }

  private CashAccountSEPA1 getCashAccountSEPA1()
  {
    CashAccountSEPA1 ca1 = new CashAccountSEPA1();
    ca1.setCcy("EUR"); // TODO prüfen
    ca1.setId(getAccountIdentificationSEPA());
    return ca1;
  }

  private BranchAndFinancialInstitutionIdentificationSEPA1 getBranchAndFinancialInstitutionIdentificationSEPA1()
  {
    BranchAndFinancialInstitutionIdentificationSEPA1 bafii = new BranchAndFinancialInstitutionIdentificationSEPA1();
    FinancialInstitutionIdentificationSEPA1 fii = new FinancialInstitutionIdentificationSEPA1();
    fii.setBIC("Hier kommt die BIC rein");
    bafii.setFinInstnId(fii);
    return bafii;
  }

  private PartyIdentificationSEPA3 getPartyIdentificationSEPA3()
  {
    PartyIdentificationSEPA3 pi3 = new PartyIdentificationSEPA3();
    PartySEPA2 p2 = new PartySEPA2();
    PersonIdentificationSEPA2 pi2 = new PersonIdentificationSEPA2();
    RestrictedPersonIdentificationSEPA rpi = new RestrictedPersonIdentificationSEPA();
    rpi.setId("id11111"); // prüfen
    pi2.setOthr(rpi);
    p2.setPrvtId(pi2);
    pi3.setId(p2);
    return pi3;
  }

  private AccountIdentificationSEPA getAccountIdentificationSEPA()
  {
    AccountIdentificationSEPA ai = new AccountIdentificationSEPA();
    ai.setIBAN("Hier kommt die IBAN rein");
    return ai;
  }

  public static void main(String[] args) throws DatatypeConfigurationException
  {
    new LastschriftTest();
  }
}
