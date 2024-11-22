package mycode.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

public class DataEncrypt {
	
	private DataEncrypt(){}
	
	public static DataEncrypt dataEncrypt;
	
	public static synchronized DataEncrypt getInstance(){
		if(dataEncrypt == null){
			dataEncrypt = new DataEncrypt();
		}
		return dataEncrypt;
	}
	
	public static String encrypt(String message, int segment, String flag) throws UnsupportedEncodingException{
		
		if(StringUtils.isNotBlank(message)){
			message = message.trim();
		}else{
			return message;
		}
		
		//內容是否包含中文
		if(message.getBytes("UTF-8").length != message.length()){
			message = alphanumericEncrypt(message);
		}else{
			message = splitEncrypt(message, segment, flag);
		}
		
		return message;
	}
	
	//純英數：分段加密
	private static String splitEncrypt (String str, int segment, String flag) {

		String newstr = "";
		String substr = "";
		int length = str.length();
		int subLength = 0;
		int subsLength = 0;	
		int beginIndex = 0;
		int endIndex = 0;
				
		for (int i = 0; i < segment; i++) {
			//取得每段長度
			subLength = length / (segment - i);
			//原長扣掉已截取的長度
			length = length - subLength;
			//計算目前已截取總長度
			subsLength += subLength;
			
			beginIndex = newstr.length();
			endIndex = subsLength;

			if ("even".equals(flag)) {
				// 偶數加密
				if ((i + 1) % 2 == 0) {					
					substr = maskMsg(str.substring(beginIndex, endIndex).length());			
				} else {
					substr = str.substring(beginIndex, endIndex);						
				}
			} else if ("odd".equals(flag)){
				// 奇數加密
				if ((i + 1) % 2 != 0) {					
					substr = maskMsg(str.substring(beginIndex, endIndex).length());
				} else {
					substr = str.substring(beginIndex, endIndex);						
				}
			}

			newstr = newstr + substr;
		}
		return newstr;

	}

	//中英數混：英數字加密
	private static String alphanumericEncrypt (String str) {
		return str.replaceAll("\\w", "*");
	}
	
	private static String maskMsg(int length) {
		
		String mask = "";
		for (int i = 0; i < length; i++) {
			mask += "*";
		}
		
		return mask;
		
	}
		

}
