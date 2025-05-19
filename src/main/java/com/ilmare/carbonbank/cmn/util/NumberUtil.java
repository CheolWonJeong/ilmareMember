/*
 * NumberUtil.java    2009. 01. 01
 * 
 * Copyright(c) 2008 by SK Telecom co.ltd all right reserved.
 */
package com.ilmare.carbonbank.cmn.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import io.micrometer.common.util.StringUtils;
/**
* <br> ������ : WCP����
* <br> ��   �� : Number ���� UTIL
* <br> �ۼ��� : 2009. 01. 01
* <br> �ۼ��� : �躴ȭ
* <br> ������ : 2010. 07. 21
* <br> ������ : �躴ȭ
*/
public class NumberUtil {

	private NumberUtil() {
		
	}
    /**
    *
    * <pre>
    * int�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param int i
    * @return String
    */   	
	public static String format(int i) {
		return format( new Integer(i) );
	}
    /**
    *
    * <pre>
    * ������ ������ ����� int�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param int i, String pattern
    * @return String
    */   	
	public static String format(int i, String pattern) {
		return format( new Integer(i), pattern );
	}
    /**
    *
    * <pre>
    * long�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param long l
    * @return String
    */      
	public static String format(long l) {
		return format( new Long(l) );
	}
    /**
    *
    * <pre>
    * ������ ������ ����� long�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param long l, String pattern
    * @return String
    */      	
	public static String format(long l, String pattern) {
		return format( new Long(l), pattern );
	}
    /**
    *
    * <pre>
    * float�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param float f
    * @return String
    */      	
	public static String format(float f) {
		return format( new Float(f) );
	}
    /**
    *
    * <pre>
    * ������ ������ ����� float�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param float f, String pattern
    * @return String
    */     	
	public static String format(float f, String pattern) {
		return format( new Float(f), pattern );
	}
    /**
    *
    * <pre>
    * double�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param double d
    * @return String
    */      	
	public static String format(double d) {
		return format( new Double(d) );
	}
    /**
    *
    * <pre>
    * ������ ������ ����� double�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param double d, String pattern
    * @return String
    */     	
	public static String format(double d, String pattern) {
		return format( new Double(d), pattern );
	}
    /**
    *
    * <pre>
    * Number�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param Number number
    * @return String
    */      	
	public static String format(Number number) {
		return format(number, null);
	}
    /**
    *
    * <pre>
    * ������ ������ ����� Number�� ������ ĳ���� ������ �ۼ��մϴ�.
    * </pre>
    *
    * @param Number number, String pattern
    * @return String
    */     	
	public static String format(Number number, String pattern) {
		NumberFormat nf = getNumberFormat(pattern);
		return nf.format( number );
	}
    /**
    *
    * <pre>
    * String�� NumberŸ������ �����մϴ�.
    * </pre>
    *
    * @param String src
    * @return Number
    */   	
	public static Number createNumber(String src) {
		return createNumber(src, null); 
	}
    /**
    *
    * <pre>
    * ������ ������ ����� String�� ������ NumberŸ�� ��ġ�� �����մϴ�.
    * </pre>
    *
    * @param String src, String pattern
    * @return Number
    */   
	public static Number createNumber(String src, String pattern) {
//		if ( StringUtil.isBlank(src) ) return new Integer(0);
		try {
			NumberFormat nf = getNumberFormat(pattern);
			
			return nf.parse(src);
		} catch (ParseException e) {
			throw new RuntimeException("can not parse string number : " + src);
		}
	}
    /**
    *
    * <pre>
    * String��  IntegerŸ������ �����մϴ�.
    * </pre>
    *
    * @param String src
    * @return Integer
    */   	
	public static Integer createInteger(String src) {
		Number number = createNumber(src);
		if ( Integer.class.isInstance( number ) ) {
			return (Integer) number;
		}
		
		return new Integer( number.intValue() );
	}
    /**
    *
    * <pre>
    * String��  LongŸ������ �����մϴ�.
    * </pre>
    *
    * @param String src
    * @return Long
    */      
	public static Long createLong(String src) {
		Number number = createNumber(src);
		if ( Long.class.isInstance( number ) ) {
			return (Long) number;
		}
		
		return new Long( number.longValue() );
	}
    /**
    *
    * <pre>
    * String��  DoubleŸ������ �����մϴ�.
    * </pre>
    *
    * @param String src
    * @return Double
    */      	
	public static Double createDouble(String src) {
		Number number = createNumber(src);
		if ( Double.class.isInstance( number ) ) {
			return (Double) number;
		}
		
		return new Double( number.doubleValue() );
	}
    /**
    *
    * <pre>
    * String��  FloatŸ������ �����մϴ�.
    * </pre>
    *
    * @param String src
    * @return Float
    */    	
	public static Float createFloat(String src) {
		Number number = createNumber(src);
		if ( Float.class.isInstance( number ) ) {
			return (Float) number;
		}
		
		return new Float( number.floatValue() );
	}
    /**
    *
    * <pre>
    * ������ String ����  int������ �����ݴϴ�..
    * </pre>
    *
    * @param String src
    * @return int
    */      	
	public static int parseInt(String src) {
		return createNumber(src).intValue();
	}
    /**
    *
    * <pre>
    * ������ String ����  int������ �����ְ�, ���� ��� int�� defaultValue�� �����ݴϴ�.
    * </pre>
    *
    * @param String src, int defaultValue
    * @return int
    */  
	public static int parseInt(String src, int defaultValue) {
		if ( StringUtils.isBlank(src) ) return defaultValue;
		return createNumber(src).intValue();
	}
    /**
    *
    * <pre>
    * ������ String ����  long������ �����ݴϴ�..
    * </pre>
    *
    * @param String src
    * @return long
    */     
	public static long parseLong(String src) {
		return createNumber(src).longValue();
	}
    /**
    *
    * <pre>
    * ������ String ����  long������ �����ְ�, ���� ��� long�� defaultValue�� �����ݴϴ�.
    * </pre>
    *
    * @param String src, long defaultValue
    * @return long
    */  
	public static long parseLong(String src, long defaultValue) {
		if ( StringUtils.isBlank(src) ) return defaultValue;
		return createNumber(src).longValue();
	}
    /**
    *
    * <pre>
    * ������ String ����  double������ �����ݴϴ�..
    * </pre>
    *
    * @param String src
    * @return double
    */    
	public static double parseDouble(String src) {
		return createNumber(src).doubleValue();
	}
    /**
    *
    * <pre>
    * ������ String ����  double������ �����ְ�, ���� ��� double�� defaultValue�� �����ݴϴ�.
    * </pre>
    *
    * @param String src, double defaultValue
    * @return double
    */  
	public static double parseDouble(String src, double defaultValue) {
		if ( StringUtils.isBlank(src) ) return defaultValue;
		return createNumber(src).doubleValue();
	}
    /**
    *
    * <pre>
    * ������ String ����  float������ �����ݴϴ�..
    * </pre>
    *
    * @param String src
    * @return float
    */    
	public static float parseFloat(String src) {
		return createNumber(src).floatValue();
	}
    /**
    *
    * <pre>
    * ������ String ����  float������ �����ְ�, ���� ��� float�� defaultValue�� �����ݴϴ�.
    * </pre>
    *
    * @param String src, float defaultValue
    * @return float
    */  
	public static float parseFloat(String src, float defaultValue) {
		if ( StringUtils.isBlank(src) ) return defaultValue;
		return createNumber(src).floatValue();
	}
    /**
    *
    * <pre>
    * ������ Number�� ����  Integer������ �����ݴϴ�..
    * </pre>
    *
    * @param Number number
    * @return Integer
    */    
	public static Integer toInteger(Number number) {
		return toInteger(number, null);
	}
    /**
    *
    * <pre>
    * ������ Number�� ����  Integer������ �����ְ�, ���� ��� Integer�� defaultValue�� �����ݴϴ�.
    * </pre>
    *
    * @param String src, float defaultValue
    * @return float
    */  	
	public static Integer toInteger(Number number, Integer defaultValue) {
		if ( number == null ) return defaultValue;
		
		if ( Integer.class.isInstance(number) ) {
			return (Integer) number;
		}
		
		return new Integer(number.intValue());
	}
    /**
    *
    * <pre>
    * ������ Number�� ����  Long������ �����ݴϴ�..
    * </pre>
    *
    * @param Number number
    * @return Long
    */   
	public static Long toLong(Number number) {
		return toLong(number, null);
	}
    /**
    *
    * <pre>
    * ������ Number�� ����  Long������ �����ְ�, ���� ��� Long�� defaultValue�� �����ݴϴ�.
    * </pre>
    *
    * @param Number number, Long defaultValue
    * @return Long
    */	
	public static Long toLong(Number number, Long defaultValue) {
		if ( number == null ) return defaultValue;
		
		if ( Long.class.isInstance(number) ) {
			return (Long) number;
		}
		
		return new Long(number.longValue());
	}
    /**
    *
    * <pre>
    * Number���� Float������ �����ְ�, null�� ��� null�� �����ش�. 
    * </pre>
    *
    * @param Number number
    * @return Float
    */   
	public static Float toFloat(Number number) {
		return toFloat(number, null);
	}
    /**
    *
    * <pre>
    * Number���� Float������ �����ְ�, null�� ��� ������ Float�� defaultValue�� �����ش�. 
    * </pre>
    *
    * @param Number number
    * @return Float
    */    	
	public static Float toFloat(Number number, Float defaultValue) {
		if ( number == null ) return defaultValue;
		
		if ( Float.class.isInstance(number) ) {
			return (Float) number;
		}
		
		return new Float(number.floatValue());
	}
    /**
    *
    * <pre>
    * Number���� Double������ �����ְ�, null�� ��� null�� �����ش�. 
    * </pre>
    *
    * @param Number number
    * @return toDouble(number, null) - Double��
    */    
	public static Double toDouble(Number number) {
		return toDouble(number, null);
	}
    /**
    *
    * <pre>
    * Number���� Double������ �����ְ�, null�� ��� ������ Double�� defaultValue�� �����ش�. 
    * </pre>
    *
    * @param Number number
    * @return toDouble(number, null) - Double��
    */    
	public static Double toDouble(Number number, Double defaultValue) {
		if ( number == null ) return defaultValue;
		
		if ( Double.class.isInstance(number) ) {
			return (Double) number;
		}
		
		return new Double(number.doubleValue());
	}


	/**
	 * 
	 * int �� ����Ÿ�� ���� �������� ��ȯ�Ͽ� �̸� ��ȯ
	 * 
	 * @param m_sPrice
	 *            ����(ex,10000)
	 * @return String - ��������(10,000)
	 */
	public static String getFormatPrice(int m_sPrice) {
		DecimalFormat df = new DecimalFormat("###,###,###,##0");
		return df.format(m_sPrice);
	}

	/**
	 * 
	 * ���ڿ��� PRICE�������� ��ȯ�ϴ� METHOD
	 * 
	 * @param String sSource
	 * @return String - ���� ����
	 */
	public static String getFormatPrice(String sSource) {
		DecimalFormat decimalformat = null;
		String sFormat = null;

		try {
			decimalformat = new DecimalFormat("###,###,###,##0");
			sFormat = decimalformat.format(Long.parseLong(sSource));
		} catch (Exception e) {
			sFormat = "-1";
		}

		return sFormat;
	}


	/**
	 * 
	 * ���޹��� long Ÿ�� �����͸� ����ǥ�� ���´�� 3�ڸ����� ',' �����Ͽ� ��ȯ��
	 * 
	 * @param long price
	 * @return String - ��������
	 */
	public static String priceStr(long price) {
		DecimalFormat df = new DecimalFormat("###,###");
		return df.format(price);
	}


	/**
	 * 
	 * ���޹��� ���ڿ��� ����ǥ�� ���´�� 3�ڸ����� ',' �����Ͽ� ��ȯ��
	 * 
	 * @param String price
	 * @return String - ��������
	 */
	public static String priceStr(String price) {
		DecimalFormat df = new DecimalFormat("###,###");
		return df.format(Long.parseLong(price));
	}

	/**
	 * 
	 * ������ ������ �ι��ڿ��̶�� KOREA�����Ͽ� ���� ���� ��ġ ������ �����ݴϴ�.
	 * ������ ������ �ι��ڿ��� �ƴ϶�� ������ ������ ������ NumberFormat�� �����ݴϴ�.
	 * @param String pattern
	 * @return df - NumberFormat
	 */
	private static NumberFormat getNumberFormat(String pattern) {
		if ( StringUtils.isBlank( pattern ) ) {
			return NumberFormat.getNumberInstance(Locale.KOREA);
		}
		
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(pattern);
		return df;
	}

    public static void sortSelection(int[] data){
        int size = data.length;
        int min; //최소값을 가진 데이터의 인덱스 저장 변수
        int temp;
        
        for(int i=0; i<size-1; i++){ // size-1 : 마지막 요소는 자연스럽게 정렬됨
            min = i;
            for(int j=i+1; j<size; j++){
                if(data[min] > data[j]){
                    min = j;
                }
            }
            temp = data[min];
            data[min] = data[i];
            data[i] = temp;
        }
    }
	
}
