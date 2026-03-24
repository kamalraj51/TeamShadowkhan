package com.vastpro.service;


import java.util.Map;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;
public class SiginupService {

	public static Timestamp getDateTime() {
		//it will get current date with time in this format yyyy-MM-dd HH:mm:ss.SSS
		System.out.println("0");
        LocalDateTime now = LocalDateTime.now();
  

        return Timestamp.valueOf(LocalDateTime.now());
        
	}
    public static Map<String, Object> checkSiginupService(DispatchContext dctx, Map<String, ? extends Object> context) {

        Delegator delegator = dctx.getDelegator();

        try {
        	System.out.println("1");
            //unique user id
        	String partyId ="SPX_"+delegator.getNextSeqId("Party");
        	System.out.println("2");
        	//insert to party table it stores role and so on
        	GenericValue party = delegator.makeValue("Party");
        	System.out.println("3");
            party.set("partyId",partyId);
            System.out.println("4");
            party.set("partyTypeId", "PERSON");
            System.out.println("5");
            party.set("statusId", "PARTY_ENABLED");
            System.out.println("6");
            
            delegator.create(party);
            System.out.println("7");
            //insert to person table basic info
            GenericValue person = delegator.makeValue("Person");
            System.out.println("8");
            person.set("partyId", partyId);
            System.out.println("9");
            person.set("firstName", context.get("firstName"));
            System.out.println("10");
            person.set("lastName", context.get("lastName"));
            System.out.println("11");
            
            delegator.create(person);
            System.out.println("12");
            //insert to user login id table stores login credential
            GenericValue userLoginId = delegator.makeValue("UserLogin");
            System.out.println("13");
            
            userLoginId.set("userLoginId", context.get("username"));
            System.out.println("14");
            userLoginId.set("currentPassword", context.get("password"));
            System.out.println("15");
            userLoginId.set("partyId", partyId);
            System.out.println("16");
            userLoginId.set("enabled", "N");//N - user currently not attending exam, Y - currently attending
            System.out.println("17");
            delegator.create(userLoginId);
            System.out.println("18");
            //insert to party role table party id with role for identity
            GenericValue partyRole = delegator.makeValue("PartyRole");
            System.out.println("19");
            partyRole.set("partyId", partyId);
            System.out.println("20");
            partyRole.set("roleTypeId", "SPX_EXAMINEE");
            System.out.println("21");
            delegator.create(partyRole);
            System.out.println("22");
            //contact Mech id unique sequence
            String contactMechId ="SPX_CONTACT_"+delegator.getNextSeqId("ContactMech");
            System.out.println("23");
            //insert to contact mech table for it store contact detail of user
            GenericValue contactMech = delegator.makeValue("ContactMech");
            System.out.println("24");
            contactMech.set("contactMechId", contactMechId);
            System.out.println("25");
            contactMech.set("contactMechTypeId", "EMAIL_ADDRESS");
            System.out.println("26");
            contactMech.set("infoString", context.get("email"));
            System.out.println("27");
            
           
            
            delegator.create(contactMech);
            System.out.println("28");
            //insert to party contact mech table for join contact detail with user
            GenericValue partyContactMech = delegator.makeValue("PartyContactMech");
            System.out.println("29");
            partyContactMech.set("contactMechId", contactMechId);
            System.out.println("30");
            partyContactMech.set("partyId", partyId);
            System.out.println("31");
            partyContactMech.set("fromDate", getDateTime());
            System.out.println("32");
            delegator.create(partyContactMech);
            
            
          //contact Mech id unique sequence
            String teleContactMechIds = "SPX_CONTACT_" + delegator.getNextSeqId("ContactMech");
            System.out.println("33");
            //inserting telecom details
            GenericValue contactMechTele = delegator.makeValue("ContactMech");
            System.out.println("34");
            contactMechTele.set("contactMechId", teleContactMechIds);
            System.out.println("35");
            contactMechTele.set("contactMechTypeId", "TELECOM_NUMBER");
            System.out.println("36");
            delegator.create(contactMechTele);
            System.out.println("37");
            String teleContactMechId = "SPX_CONTACT_" + delegator.getNextSeqId("ContactMech");
            System.out.println("38");
            //insert to telecom number store number
            GenericValue telecomNumber = delegator.makeValue("TelecomNumber");
            System.out.println("39");
            telecomNumber.set("contactMechId", teleContactMechIds);
            System.out.println("40");
            telecomNumber.set("contactNumber", context.get("phNo"));
            System.out.println("41");
            delegator.create(telecomNumber);
            System.out.println("42");
            //insert to party contact mech table for join contact detail with user
            GenericValue partyContactMechTele = delegator.makeValue("PartyContactMech");
            System.out.println("43");
            partyContactMechTele.set("contactMechId", teleContactMechIds);
            System.out.println("44");
            partyContactMechTele.set("partyId", partyId);
            System.out.println("45");
            partyContactMechTele.set("fromDate", getDateTime());
            System.out.println("45");
            delegator.create(partyContactMechTele);
            System.out.println("46");

            return ServiceUtil.returnSuccess("Employee created successfully");

        } catch (Exception e) {
//        	return ServiceUtil.returnSuccess("Employee created successfully");
        	System.out.println("catch");
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        	return ServiceUtil.returnError("Error while creating employee: " + e.getMessage());
        }
    }
    

}