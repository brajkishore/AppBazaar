package com.ussdoffers.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import CentrumLogger.model.DataHolder;
import CentrumLogger.model.MsisdnSessionData;
import CentrumLogger.model.Offer;
import CentrumLogger.util.CommonUtil;
import CentrumLogger.util.CustomPropertyPlaceHolderConfigurer;

import com.one97.ussd.flytext.model.Event;
import com.one97.ussd.flytext.model.FlyTxtOffer;
import com.one97.ussd.flytext.model.Status;
import com.one97.ussd.flytext.service.IOfferProvider;
import com.ussdoffers.common.BalanceRetriver;
import com.ussdoffers.common.Constants;
import com.ussdoffers.manager.FlyTxt121Manager;
import com.ussdoffers.manager.KOL121vasManager;
import com.ussdoffers.model.CustomMsisdnSessionData;
import com.ussdoffers.util.LogUtil;

@Produces( { "text/plain" })
@Service("kol121precust_flytext")
@Path("/kol121precust_flytext")
public class Kol121PrepaidCustomer_Flytext implements Constants {
	private static final LogUtil logger = LogUtil
			.getLogger(Kol121PrepaidCustomer_Flytext.class);
	private static final String SESSION_EXPIRED = "ERROR_100:Session Expired";
	private static final String SESSION_ERROR = "ERROR_100:Some technical Error";
	@Resource(name = "dataHolder")
	private DataHolder dataHolder;
	@Resource(name = "kol121vasManager")
	private KOL121vasManager kol121vasManager;
	@Resource(name = "offerProvider")
	IOfferProvider offerProvider;

	
	@Resource(name="flyTxt121Manager")
	FlyTxt121Manager flyTxt121Manager;
	
	
	
	public String flyTextoffers(Long msisdn, Long altUserId) 
	{
		CustomMsisdnSessionData msisdnSessionData = (CustomMsisdnSessionData) dataHolder.getSession().get(msisdn);
		String campaignId = null;
		if (msisdnSessionData != null) 
		{
			int optionValue = 0;
			try {
				optionValue = Integer.valueOf(dataHolder.getOfferMap().get("FLY_TEXT_OPTION").getText());
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("Offer text is not available for FLY_TEXT_OPTION in tbl_default_offers "+ e.toString() + "  " + msisdn);
				optionValue = 0;
			}
			
			logger.info("FLY_TEXT_OPTION value is "+optionValue+" "+msisdn);
			
			if (optionValue == 1||optionValue == 2) 
			{
				int i=0;
				if(optionValue==1){
					 i=flyTxt121Manager.checkInFlyTxtBase(msisdn);				
					logger.info("User "+msisdn+" in flyTxt121Manager "+i);
					if(i==0)
						return campaignId;
				}
				
				/*
				 * Get flytxt offers
				 */
				
				
				
				logger.info("User is prepaid and getting prepaid flytext offers " + msisdn);
				
				offerProvider.getNSetCustOffers(msisdn, msisdnSessionData);
				//offerProvider.getNSetRetOffers(altUserId, msisdn, msisdnSessionData);
				List<FlyTxtOffer> offers = msisdnSessionData.getFlyTxtOffers();
				
				if (offers != null && offers.size() > 0) 
				{
					logger.info("Valid flytxt offer exists "+msisdn);
					campaignId = "1211181";
					
				}
				else{
					logger.info("No valid flytxt offers found setting default offers "+msisdn);
					
					  msisdnSessionData.setIntParam1(0);			          
			          msisdnSessionData.setCampaignId(campaignId);
			          List<String> tmpList=new ArrayList<String>();
			          tmpList.add("D1");
			          tmpList.add("D2");
			          msisdnSessionData.setOfferList(tmpList);
			          logger.info("default case for customer return 52 campaign for msisdn:" + msisdn);
			          campaignId = "52";
			          
			          
				}
				msisdnSessionData.setCampaignId(campaignId);		        
				return campaignId;

			}
		}
		return campaignId;
	}
	
	
	@GET
	@Path("/fetchValidOffers")
	public String fetchValidOffers(@QueryParam("msisdn") Long msisdn,@QueryParam("key") String key) {
		logger.info("Entering inside fetchValidOffers method for msisdn = " + msisdn);
		CustomMsisdnSessionData msisdnSessionData = (CustomMsisdnSessionData) dataHolder.getSession().get(msisdn);
		if (msisdnSessionData != null)
		{
			List<FlyTxtOffer> flyTxtOfferList = msisdnSessionData.getFlyTxtOffers();
			String offerText = "";
			Map<String, Object> offerMap = new HashMap<String, Object>();
			int default_menu_size = 5;
			if (flyTxtOfferList != null && flyTxtOfferList.size() > 0) {
				int seq = 1;
				msisdnSessionData.setOfferCount(default_menu_size);
				Map<String,Object> validoffer = new HashMap<String, Object>();
				for (FlyTxtOffer flyTxtOfferObj : flyTxtOfferList) {
					if (flyTxtOfferObj != null && StringUtils.isNotBlank(flyTxtOfferObj.getShortMessage())) {
						if (seq <= default_menu_size) {
							if(seq==1)
								offerText=seq+"."+flyTxtOfferObj.getShortMessage();
							else
							offerText = offerText+"\n" +seq+"."+flyTxtOfferObj.getShortMessage();
						} 
						
						validoffer.put(""+seq, flyTxtOfferObj);
						msisdnSessionData.setSecondConsentVasPackMap(validoffer);
						seq++;
					}
				}
				if(flyTxtOfferList.size() > default_menu_size)
				{
					String more_offer_txt="";
					try {
						more_offer_txt=dataHolder.getOfferMap().get("MORE_OPTION_123").getText();
					} catch (Exception e) {
						// TODO: handle exception
						logger.info("Offer text is not available for MORE_OPTION_123 in tbl_default_offers "+e.toString()+"  "+msisdn);
						more_offer_txt="more 9";
					}
					offerText = offerText+"\n"+more_offer_txt; 	
				}
			
			}
			CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "VISTED_VALIDOFFER_NODE", 
					"VISTED_VALIDOFFER_NODE","VISTED_VALIDOFFER_NODE","", key,0, "", "", 0, 0,
					"", "",offerText, "");
		
			return offerText;
			
		}
		logger.debug("No session exists for " + msisdn);
		return SESSION_EXPIRED;
	
	}
	@GET
	@Path("check_subscription")
	public String check_subscription(@QueryParam("msisdn") Long msisdn,@QueryParam("key") String key)
	{
		logger.info("Entering in to check_subscription  method: for msisdn: " + msisdn);
		CustomMsisdnSessionData msisdnSessionData = (CustomMsisdnSessionData) dataHolder.getSession().get(msisdn);
		
		if(msisdnSessionData != null)
		{
			
		
			List<FlyTxtOffer> flyTxtOfferList = msisdnSessionData.getFlyTxtOffers();
			String offerText="";
			int menu_default_size = 5;
			
			if(!StringUtils.isNumeric(key) ||  flyTxtOfferList.size()<Integer.parseInt(key) ||  menu_default_size < Integer.parseInt(key) || Integer.parseInt(key) < 1 )
			{
			
				CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "invalid input", 
						"check_subscription","invalid input","", key,0, "", "", 0, 0,
						"", "",offerText, "");
				offerText = "invalid input";
				
				return "ERROR_120:" +offerText;
				
			}
			String offerType = "";
			FlyTxtOffer flyTxtOffer =  null;
			
			if(msisdnSessionData.getSecondConsentVasPackMap() != null){
				flyTxtOffer = ((FlyTxtOffer) msisdnSessionData.getSecondConsentVasPackMap().get(key));
				offerType = flyTxtOffer.getOfferType().toUpperCase();
			}
			if(ifSubscriptiveOffer(msisdn, flyTxtOffer) && flyTxtOffer != null)
				offerText = flyTxtOffer.getMessage() +"\n Press 5 to Subscribe\n Previous 0" ;
			else
				offerText = flyTxtOffer.getMessage() +"\n Previous 0" ;
		
			
			msisdnSessionData.setCurrentFlyTextOffer(flyTxtOffer);
			
			CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "VISTED_LAST_NODE", 
					"VISTED_SUBSCRIBE_NODE","VISTED_SUBSCRIBE_NODE","", key,0, "", "", 0, 0,
					"", "",offerText, "");
			return offerText;
			
		}
		
		return SESSION_EXPIRED;
	}
	
	@GET
	@Path("next_offer")
	public String next_offer(@QueryParam("msisdn") Long msisdn,@QueryParam("key") String key)
	{
		logger.info("Entering in to next_offer  method: for msisdn: " + msisdn);
		CustomMsisdnSessionData  msisdnSessionData = (CustomMsisdnSessionData)dataHolder.getSession().get(msisdn);
		
		if(msisdnSessionData != null)
		{
			List<FlyTxtOffer> flyTxtOfferList = msisdnSessionData.getFlyTxtOffers();
			String offerText="";
			String offerId="PRODUCT_NEXT_MENU";
			int index = 1;
			int offercount  = msisdnSessionData.getOfferCount();
			int currentscrsize = 0;
			int menu_default_size = 5;
			int next_count = offercount + menu_default_size;//need default count
			if(flyTxtOfferList.size() < next_count){
				next_count = flyTxtOfferList.size();
			}
			msisdnSessionData.setOfferCount(next_count);
			Map<String,Object> validoffer = new HashMap<String, Object>();		
			
			StringBuilder sb=new StringBuilder();
			
			for (int i = offercount; i < next_count; i++) {
				if (flyTxtOfferList != null && flyTxtOfferList.size() > 0 &&
						StringUtils.isNotBlank(flyTxtOfferList.get(i).getShortMessage()))
				{
					if(sb.length()==0)
						sb.append(index).append(".").append(flyTxtOfferList.get(i).getShortMessage());
					else
						sb.append("\n").append(index).append(".").append(flyTxtOfferList.get(i).getShortMessage());
					
					currentscrsize++;					
					validoffer.put(""+index, flyTxtOfferList.get(i));
					index++;
				}	
			}
			msisdnSessionData.setSecondConsentVasPackMap(validoffer);
			msisdnSessionData.setIntParam2(currentscrsize);
			if( flyTxtOfferList.size() <= offercount)
			{
			
				CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "invalid input", 
						"next_offer_script","invalid input","", key,0, "", "", 0, 0,
						"", "",offerText, "");
				offerText = "invalid input";
				
				return "ERROR_120:" +offerText;
				
			}
			if(flyTxtOfferList.size()>next_count)
			{
				String more_offer_txt="";
				try {
					more_offer_txt=dataHolder.getOfferMap().get("MORE_OPTION_123").getText();
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("Offer text is not available for MORE_OPTION_123 in tbl_default_offers "+e.toString()+"  "+msisdn);
					more_offer_txt="more 9";
				}
				sb.append("\n").append(more_offer_txt); 	
			}
	
			String previous_offer_txt="";
			try {
				previous_offer_txt =dataHolder.getOfferMap().get("PRIVOUS_OPTION_123").getText();
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("Offer text is not available for MORE_OPTION_123 in tbl_default_offers "+e.toString()+"  "+msisdn);
				previous_offer_txt = "0 for prev";
			}
			
			sb.append("\n").append(previous_offer_txt);
			
			offerText = sb.toString();
			
			CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, offerId, 
					"next_offer_script","next_offer_script","", key,0, "", "", 0, 0,
					"", "",offerText, "");
			
			return offerText;
			
		}
		
		logger.debug("No session exists for " + msisdn);
		return SESSION_EXPIRED;
	}
	
	@GET
	@Path("previous_offer")
	public String previous_offer(@QueryParam("msisdn") Long msisdn,@QueryParam("key") String key)
	{
		logger.info("Entering in to previous_offer method: for msisdn: " + msisdn);
		CustomMsisdnSessionData  msisdnSessionData = (CustomMsisdnSessionData)dataHolder.getSession().get(msisdn);
		
		if(msisdnSessionData != null)
		{
			int offercount =  msisdnSessionData.getOfferCount();
			List<FlyTxtOffer> flyTxtOfferList = msisdnSessionData.getFlyTxtOffers();
			
			int currtsrcsize = msisdnSessionData.getIntParam2();
			int newcurrentscrsize = 0;
			int menu_default_size = 5;
			String offerText = "";
			try {
				menu_default_size = Integer.parseInt(dataHolder.getOfferMap().get("menu_default_size").getText());
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("Offer text is not available for MORE_OPTION_123 in tbl_default_offers "+e.toString()+"  "+msisdn);
				menu_default_size= 5;
			}
			int startindex = offercount - menu_default_size - currtsrcsize;
			int index = 1;
			if( offercount <= menu_default_size )
			{
			
				CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "invalid input", 
						"previous_offer_script","invalid input","", key,0, "", "", 0, 0,
						"", "",offerText, "");
				offerText = "invalid input";
				
				return "ERROR_120:" +offerText;
				
			}
			Map<String,Object> validoffer = new HashMap<String, Object>();
			
			
			StringBuilder sb=new StringBuilder();
			
			for (int i = startindex; i < offercount-currtsrcsize; i++) {
				if (flyTxtOfferList != null && flyTxtOfferList.size() > 0 && StringUtils.isNotBlank(flyTxtOfferList.get(i).getShortMessage()))
				{
					newcurrentscrsize++;
					
					if(sb.length()==0)
						sb.append(index).append(".").append( flyTxtOfferList.get(i).getShortMessage());
					else
						sb.append("\n").append(index).append(".").append( flyTxtOfferList.get(i).getShortMessage());
					
					validoffer.put(""+index, flyTxtOfferList.get(i));
					index++;
				}
					
			}
			msisdnSessionData.setIntParam2(newcurrentscrsize);
			msisdnSessionData.setSecondConsentVasPackMap(validoffer);
			msisdnSessionData.setOfferCount(offercount-currtsrcsize);
			if(flyTxtOfferList.size()>startindex)
			{
				String more_offer_txt="";
				try {
					more_offer_txt=dataHolder.getOfferMap().get("MORE_OPTION_123").getText();
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("Offer text is not available for MORE_OPTION_123 in tbl_default_offers "+e.toString()+"  "+msisdn);
					more_offer_txt="For more 9";
				}
				sb.append("\n").append(more_offer_txt);
				 	
			}
			if(startindex >= menu_default_size)
			{
			String previous_offer_txt="";
			try {
				previous_offer_txt =dataHolder.getOfferMap().get("PRIVOUS_OPTION_123").getText();
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("Offer text is not available for MORE_OPTION_123 in tbl_default_offers "+e.toString()+"  "+msisdn);
				previous_offer_txt = "* for prev";
	
			}
			sb.append("\n").append(previous_offer_txt);
			}
			
			offerText=sb.toString();
			
			CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "VISTED_PRIVOUS_NODE", 
					"previous_offer_script","previous_offer_script","", key,0, "", "", 0, 0,
					"", "",offerText, "");
			
			return offerText;
										
			
		}
		
		return SESSION_EXPIRED;
	}

	@GET
	@Path("sub_mode")
	public String sub_mode(@QueryParam("msisdn") Long msisdn,@QueryParam("key") String key)
	{
		logger.info("Entering in to sub_mode method: for msisdn: " + msisdn);
		CustomMsisdnSessionData  msisdnSessionData = (CustomMsisdnSessionData)dataHolder.getSession().get(msisdn);
		
		if(msisdnSessionData != null)
		{

			
			String offerText="";
				
			FlyTxtOffer flyTxtOffer =  msisdnSessionData.getCurrentFlyTextOffer();
			
			
			
			
			
			
			try {
				
				if(!ifSubscriptiveOffer(msisdn, flyTxtOffer)){
					
					offerText="Invalid Input";
					CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "Invalid_Input", 
							"Invalid_Input","Invalid_Input","", key,0, "", "", 0, 0,
							"", "",offerText, "");
					
					return "ERROR_120:" +offerText;
				}
								
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("Offer text is not available for MORE_OPTION_123 in tbl_default_offers "+e.toString()+"  "+msisdn);
	
			}
			
				String displayBalanceFlag = "0";
				try {
				displayBalanceFlag = dataHolder.getOfferMap().get( "CUST_121_BALANCE_FLAG" ).getText();
				} catch ( Exception ex ) {
					displayBalanceFlag = "0";
				logger.info( "Exception occured in getMainMenu method while getting balance flag: displayBalanceFlag: " + displayBalanceFlag + " for msisdn= " + msisdn );
				}
				
				
				if ( StringUtils.equals( displayBalanceFlag, "1" ) )
				{
					String displayBalanceText = "";
					try {
					displayBalanceText = dataHolder.getOfferMap().get( "CUST_121_BALANCE_TEXT" ).getText();
					} catch ( Exception ex ) {
					displayBalanceText = "Your account balance is Rs.<BAL> , your best fit offers are,";
					}
					double balance = BalanceRetriver.getBalanceFor121Cust( msisdn );
					String offerMrpstr = flyTxtOffer.getPrice();
					String keyword = flyTxtOffer.getAdditionalParams().getPromo_name()[0];
					double offerMrp  = Double.parseDouble(offerMrpstr);
					//check usr balance
					if(keyword != null && !"".equalsIgnoreCase(keyword) )
					{
						if(balance > offerMrp+1)
						{
							if (kol121vasManager.insertintoUssdDbServices(msisdn, keyword, CommonUtil.getSystemDateAndTime(), "121", "USSD_FLYTXT_SUB", msisdnSessionData.getTransactionId()) >0){
									try {
										logger.info("updating flytxt event "+msisdn);
										Status status=offerProvider.updateCustAcceptEvent(msisdn, msisdnSessionData, new Event(flyTxtOffer.getId()+",,accept"));
										logger.info("updated status flytxt event "+msisdn+" "+status);
										
										offerText = dataHolder.getOfferMap().get( "SUBSCRIBE_SUCCESS_TEXT" ).getText();
										
									} catch ( Exception ex ) {
										offerText = "Thanks for subscribing this offer and this will get activated on your number very soon,";
									}
								
							}else{
								offerText  = SESSION_ERROR;
							}
						}
						else
						{
							try {
								offerText = dataHolder.getOfferMap().get( "SUBSCRIBE_FAIL_TEXT" ).getText();
							} catch ( Exception ex ) {
								offerText = "Sorry you do not have sufficient balance to subscribe this";
							}
						
							offerText = "";
						}
					}else
					{
						offerText  = SESSION_ERROR;
					}
				
				}
				else if(StringUtils.equals( displayBalanceFlag, "0" ) )
				{
					String keyword = flyTxtOffer.getAdditionalParams().getPromo_name()[0];
					if(keyword != null && !"".equalsIgnoreCase(keyword) )
					{
						
						if (kol121vasManager.insertintoUssdDbServices(msisdn, keyword, CommonUtil.getSystemDateAndTime(), "121", "USSD_FLYTXT_SUB", msisdnSessionData.getTransactionId()) >0) {
							try {
								logger.info("updating flytxt event "+msisdn);
								Status status=offerProvider.updateCustAcceptEvent(msisdn, msisdnSessionData, new Event(flyTxtOffer.getId()+",,accept"));
								logger.info("updated status flytxt event "+msisdn+" "+status);
								
								offerText = dataHolder.getOfferMap().get( "SUBSCRIBE_SUCCESS_TEXT" ).getText();
								
							} catch ( Exception ex ) {
								offerText = "Thanks for subscribing this offer and this will get activated on your number very soon,";
							}
							
						}
					}
					else
					{
						offerText  = SESSION_ERROR;
					}
					
				}else{
					offerText  = SESSION_ERROR;;
				}
				
					
			
			CommonUtil.addEventlogBeanWithExtraParam(msisdnSessionData, "VISTED_PRIVOUS_NODE", 
					"previous_offer_script","previous_offer_script","", key,0, "", "", 0, 0,
					"", "",offerText, "");
			
			return offerText;
		}
		
		return SESSION_EXPIRED;
	}
private boolean ifSubscriptiveOffer(Long msisdn,FlyTxtOffer flyTxtOffer){
		
		try {
			
			String offerType="FLY_CAT_"+flyTxtOffer.getOfferType().toUpperCase().trim()+"_2";
			if(dataHolder.getOfferMap().containsKey(offerType))
				return true;			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error in checking if offer is subscrptive "+msisdn+" "+flyTxtOffer.getId());
		}
	
		return false;
	}
}