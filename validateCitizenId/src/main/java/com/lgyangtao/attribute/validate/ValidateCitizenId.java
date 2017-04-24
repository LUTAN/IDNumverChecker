package com.lgyangtao.attribute.validate;

import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;
import java.io.IOException;
import java.util.*;
/**
 * @author: Lutan
 *
 */
public class ValidateCitizenId 
{
    public static void main( String[] args )
    {
    	if(validate_citizen_id(args[0])){
    		System.out.println("It is a citizen id");
    	}
    	else{
    		System.out.println( "It is not a citizen id" );
    	}
    }

    private static String cityCode[] = {"11", "12", "13", "14", "15", "21",  
            "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",  
            "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",  
            "63", "64", "65", "71", "81", "82", "91"};  
  
        /** 
        * 每位加权因子 
        */  
    private static int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    public static Boolean validate_citizen_id(String cit_id){
            if (cit_id.length() == 18){
                if(validate18Idcard(cit_id) && checkLastNumber(cit_id)){
                    return true;
                }                
            }
            else if(cit_id.length() == 15){
                if(validate15IDCard(cit_id)){
                    return true;
                }
            }
            
            return false;
        }

        public static boolean isCJK(String str){
            int length = str.length();
            boolean isChinese = true;
            for (int i = 0; i < length; i++){
                char ch = str.charAt(i);
                Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
                if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(block)|| 
                    Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(block)|| 
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(block)){
                    isChinese = true;
                }
                else{
                    return false;
                }
            }
            return isChinese;
        }
        

        public static boolean validate18Idcard(String idcard) {  
            if (idcard == null) {  
                return false;  
            }  
            // 非18位为假  
            if (idcard.length() != 18) {  
                return false;  
            }  
            // 获取前17位  
            String idcard17 = idcard.substring(0, 17);  
  
            // 前17位全部为数字  
            if (!isDigital(idcard17)) {  
                return false;  
            }  
  
            String provinceid = idcard.substring(0, 2);  
            // 校验省份  
            if (!checkProvinceid(provinceid)) {  
                return false;  
            }  
  
            // 校验出生日期  
            String birthday = idcard.substring(6, 14);  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            try {  
                Date birthDate = sdf.parse(birthday);  
                String tmpDate = sdf.format(birthDate);  
                if (!tmpDate.equals(birthday)) {// 出生年月日不正确  
                    return false;  
                }  
  
            } catch (ParseException e1) {  
                return false;  
            }  
  
            return true;  
        }  

        private static Boolean checkLastNumber(String idcard){
            // 获取第18位  
            String idcard17 = idcard.substring(0, 17);
            String idcard18Code = idcard.substring(17, 18);  
            char c[] = idcard17.toCharArray();  
            int bit[] = converCharToInt(c);  
            int sum17 = 0;  
            sum17 = getPowerSum(bit);  
  
            // 将和值与11取模得到余数进行校验码判断  
            String checkCode = getCheckCodeBySum(sum17);  
            if (null == checkCode) {  
                return false;  
            }  
            // 将身份证的第18位与算出来的校码进行匹配，不相等就为假  
            if (!idcard18Code.equalsIgnoreCase(checkCode)) {  
                return false;  
            }  
            return true;
        }
        
        private static int[] converCharToInt(char[] c) throws NumberFormatException {  
            int[] a = new int[c.length];  
            int k = 0;  
            for (char temp : c) {  
                a[k++] = Integer.parseInt(String.valueOf(temp));  
            }  
            return a;  
        }  

        private static int getPowerSum(int[] bit) {  
            int sum = 0;  
            if (power.length != bit.length) {  
                return sum;  
            }  
  
            for (int i = 0; i < bit.length; i++) {  
                for (int j = 0; j < power.length; j++) {  
                    if (i == j) {  
                        sum = sum + bit[i] * power[j];  
                    }  
                }  
            }  
            return sum;  
        }  

        private static String getCheckCodeBySum(int sum17) {  
            String checkCode = null;  
            switch (sum17 % 11) {  
            case 10:  
                checkCode = "2";  
                break;  
            case 9:  
                checkCode = "3";  
                break;  
            case 8:  
                checkCode = "4";  
                break;  
            case 7:  
                checkCode = "5";  
                break;  
            case 6:  
                checkCode = "6";  
                break;  
            case 5:  
                checkCode = "7";  
                break;  
            case 4:  
                checkCode = "8";  
                break;  
            case 3:  
                checkCode = "9";  
                break;  
            case 2:  
                checkCode = "x";  
                break;  
            case 1:  
                checkCode = "0";  
                break;  
            case 0:  
                checkCode = "1";  
                break;  
            }  
            return checkCode;  
        }  

        public static boolean validate15IDCard(String idcard) {  
            if (idcard == null) {  
                return false;  
            }  
            // 非15位为假  
            if (idcard.length() != 15) {  
                return false;  
            }  
  
            // 15全部为数字  
            if (!isDigital(idcard)) {  
                return false;  
            }  
  
            String provinceid = idcard.substring(0, 2);  
            // 校验省份  
            if (!checkProvinceid(provinceid)) {  
                return false;  
            }  
  
            String birthday = idcard.substring(6, 12);  
  
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");  
  
            try {  
                Date birthDate = sdf.parse(birthday);  
                String tmpDate = sdf.format(birthDate);  
                if (!tmpDate.equals(birthday)) {// 身份证日期错误  
                    return false;  
                }  
  
            } catch (ParseException e1) {  
                return false;  
            }  
            return true;  
        }  

        private static boolean checkProvinceid(String provinceid) {  
            for (String id : cityCode) {  
                if (id.equals(provinceid)) {  
                    return true;  
                }  
            }  
            return false;  
        }  

        private static boolean isDigital(String str) {  
            return str.matches("^[0-9]*$");  
        }

}
