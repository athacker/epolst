package gov.utah.epolst.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UtilityServiceImpl implements UtilityService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UtilityServiceImpl.class);
	private static final DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	
	/**
	 * Formats StringDate before saving into database
	 */
 	public Date formatDate(String strDate ) {
	 	
		//CLEAN UP STRING IF BADLY FORMATTED
		strDate = strDate.replace("/", "-");
		
		//input mask sends unformatted string
		if (strDate.indexOf("-") == -1 && strDate.length()== 8 ){
		    StringBuffer sb =  new StringBuffer(strDate);
		    sb.insert(2, "-");
		    sb.insert(5, "-");
		    strDate = sb.toString();
	 	}
	 
		Date returnDate = new Date();
		try {
			returnDate =formatter.parse( strDate )  ;
		}  catch (ParseException pe) {
			// do nothing -- just return formatted current date
			LOG.error("Parse Exception caught formatting date prior to database save. Will default to current date.", pe);
	 	}
		return returnDate;
	}
 	
 	@Override
	public String formatDateToString(Date date) {
  		String formattedDate = "";
	    if (null != date) {
		   formattedDate = formatter.format(date);
		}
	   return formattedDate;
	}

 	 

	@Override
	public String formatPhone(String telephoneNumber) {
		String formattedPhone = "";
		//clean out all bad formatting
	
		if (null != telephoneNumber  ) {
			 telephoneNumber = StringUtils.trimAllWhitespace( telephoneNumber.replace("(","").replace(")","").replace("-","")  );
			 formattedPhone = "(" +  telephoneNumber.substring(0, 3) + ")"
					+ telephoneNumber.substring(3, 6) + "-"
					+ telephoneNumber.substring(6, 10);
		}
		return formattedPhone;
	}

	
	
	@Override
	public String toProperCase(String stringToFormat) {
		
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(stringToFormat);
		while (st.hasMoreTokens()) {
			String text = st.nextToken();
			sb.append(text.substring(0,1).toUpperCase());
			sb.append(text.substring(1,text.length()).toLowerCase());
			sb.append(" ");
		}
		return sb.toString().trim();
	}

	
}
