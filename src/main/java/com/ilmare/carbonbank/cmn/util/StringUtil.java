package com.ilmare.carbonbank.cmn.util;

/**
 * @(#)StringUtil.java 2005/06/23
 *
 * Copyright 2005 by Joo-hong Kim, Co. All rights reserved.
 *
 * 본 Program은 문자열 처리를 편리하게 하기 위해서 만들어졌다.
 *
 * replaceStr(String org, String from, String to)		:	스트링중에서 특정스트링을 대소문자 구별하여 변경
 * replace(String org, String from, String to)			:	스트링중에서 특정스트링을 대소문자 구분없이 변경
 * convert2JSP(String val)					:	백스페이스문자 -> '
 * convert2DB(String val)					:	' -> 백스페이스문자
 * strNull(String str)						:	null 유무를 판단해서 null 이면 공백으로 반환
 * strDate(String str,int flag)					:	날짜 표시(flag -> 1:YYYY.MM.DD, 2:YY.MM.DD, 3:yyyy년mm월dd일, 4:yy년mm월dd일)
 * strTime(String str, int flag)				:	시간 표시(flag -> 1: HH:MM, 2: HH시MM분)
 * Comma(int|Double|String i)					:	단위환산 콤마 ###,###
 * unCommaToLong(String s)					:	컴마 있는 문자를 long으로 변환
 * nl2br(String s)						:	엔터문자를 br 태그로 변환
 * br2nl(String s)						:	br 태그를 엔터문자로 변환
 * html2text(String contents)					:	html tag를 text형식에 맞춰 변환
 * isNull(String s)						:	null check
 * cutString(String str, int MaxLen)				:	글자를 MaxLen 길이만큼 잘라서 "..."을 붙인다. 변환(한영모두)
 * explode(String str, String delim)				:	문자열를 입력 받아 delim 의 문자로 나누어 String 배열로 반환
 * toKor(String s)						:	문자열(8859_1) -> KSC5601로 변환
 * toKor(String[] s)						:	문자배열(8859_1) -> KSC5601로 변환
 * toEng(String s)						:	문자열(KSC5601) -> 8859_1로 변환
 * toEng(String[] s)						:	문자배열(KSC5601) -> 8859_1로 변환
 * EtoUTF(String s)						:	문자열(8859_1) -> UTF-8로 변환
 * KtoUTF(String s)						:	문자열(KSC5601) -> UTF-8로 변환
 *
 * @version 1.0, 2005/06/23
 * @since   JDK 1.4.2
 * @modify date / content :
 * 2005/07/12 - Add method : explode
 * 2005/09/22 - Add method : toKor, toEng, EtoUTF, KtoUTF
 */

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author
 *
 *         TODO 생성된 유형 주석에 대한 템플리트를 변경하려면 다음으로 이동하십시오. 창 - 환경 설정 - Java - 코드 스타일
 *         - 코드 템플리트
 */
public class StringUtil {

	/**
	 * -자동 loopback 실명인증 : NameCheckUtil.java 기변실명인증 : NameCheckAction.java
	 * 개인신용정보조회동의 : NameCheckAction.java 카드인증 : SelfAuthenticationAction.java
	 * M-Safer : SelfAuthenticationAction.java 작성완료 SMS : ScrbAction.java 작성완료
	 * Email : ScrbAction.java 기기변경 SMS인증 : SelfAuthenticationAction.java 기기변경
	 * SMS발송 : comm_PhoneView04.jsp 은행/카드 유효성검사 : BankCardCheckAction.java
	 *
	 * -수동 loopback 번호확인 : common.js 실명확인 : common_realName.js
	 */
	// 서버의 spring.profiles.active 값에 따라 설정
	public static boolean loopback = false;// true 인증 pass, false 정상로직
	public static void setLoopback(final boolean loop) {
		loopback = loop;
	}
	public static boolean smsLoopback = false; // SMS 만 컨트롤
	public static void setSmsLoopback(final boolean smsLoop) {
		smsLoopback = smsLoop;
	}

	private static int maskDayCnt = 1; //마스킹처리일 1 : 5==>1로 변경(개인정보 보호관련수정 2021-11-25

	/**
	 * replaceStr : 스트링중에서 특정스트링을 대소문자 구별하여 변경한다.
	 *
	 * @param org
	 *            <code>java.lang.String</code> : 바꾸려는 문자열을 가진 원본
	 * @param from
	 *            <code>java.lang.String</code> : 찾을 문자열
	 * @param to
	 *            <code>java.lang.String</code> : 바꿔줄 문자열
	 */
	public static String replaceStr(final String org, String from, final String to) {
		int last = 0, next = 0;
		StringBuffer result = new StringBuffer();
		String uppper_str = org.toUpperCase();
		from = from.toUpperCase();

		while (true) {
			next = uppper_str.indexOf(from, last);
			if (next >= 0) {
				result.append(org.substring(last, next));
				result.append(to);
				last = next + from.length();
			} else {
				result.append(org.substring(last));
				break;
			}
		}
		return result.toString();
	}

	/**
	 * replace : 스트링중에서 특정스트링을 대소문자 구분없이 변경한다.
	 *
	 * @param org
	 *            <code>java.lang.String</code> : 바꾸려는 문자열을 가진 원본
	 * @param from
	 *            <code>java.lang.String</code> : 찾을 문자열
	 * @param to
	 *            <code>java.lang.String</code> : 바꿔줄 문자열
	 */
	public static String replace(final String org, final String from, final String to) {
		int last = 0, next = 0;
		StringBuffer result = new StringBuffer();

		while (true) {
			next = org.indexOf(from, last);
			if (next >= 0) {
				result.append(org.substring(last, next));
				result.append(to);
				last = next + from.length();
			} else {
				result.append(org.substring(last));
				break;
			}
		}
		return result.toString();
	}

	/**
	 * convert2JSP : TAG 변환 프로세스 - 백스페이스문자 -> 작은따옴표로
	 *
	 * @param val
	 *            <code>java.lang.String</code> : 바꾸려는 문자열
	 * @return val <code>java.lang.String</code> : 변경된 문자열
	 */
	/*public String convert2JSP(String val) {
		if (val != null) {
			val = val.replace('\b', '\'');
		}
		return (val);
	}*/

	/**
	 * convert2DB : TAG 변환 프로세스 - 작은따옴표 -> 백스페이스문자로
	 *
	 * @param val
	 *            <code>java.lang.String</code> : 바꾸려는 문자열
	 * @return val <code>java.lang.String</code> : 변경된 문자열
	 */
	/*public String convert2DB(String val) {
		if (val != null) {
			val = val.replace('\'', '\b');
		}
		return (val);
	}*/

	/**
	 * strNull : NULL값이면 빈문자열로 변경
	 *
	 * @param str
	 *            <code>java.lang.String</code> : 문자열
	 * @return str <code>java.lang.String</code> : NULL이면 ""를 아니면 기존 문자열 반환
	 */
	public static String strNull(final String str) {
		return (str == null) ? "" : str.trim();
	}

	/**
	 * strNull : [2002-01-23 추가] NULL값 오브젝트
	 *
	 * @param str
	 *            <code>java.lang.String</code> : 문자열
	 * @return str <code>java.lang.String</code> : NULL이면 ""를 아니면 기존 문자열 반환
	 */
	public static String strNull(final Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * strNull : NULL값이면 빈문자열로 변경
	 *
	 * @param str
	 *            <code>java.lang.String</code> : 문자열
	 * @param text
	 *            <code>java.lang.String</code>: 문자열
	 * @return str <code>java.lang.String</code> : NULL이면 text 문자열을 아니면 기존 문자열
	 *         반환
	 */
	public static String strNull(final String str, final String text) {
		return (str == null) ? text : str.trim();
	}

	/**
	 * NulltoSpace : NULL 값이면 &nbsp; 로 변환
	 *
	 * @param str
	 * @return
	 */
	/*public static String NulltoSpace(String str) {

		str = strNull(str);

		return (str.equals("")) ? "&nbsp;" : str;

	}*/

	/**
	 * strNull : NULL값 오브젝트
	 *
	 * @param str
	 *            <code>java.lang.String</code> : 문자열
	 * @param text
	 *            <code>java.lang.String</code>: 문자열
	 * @return str <code>java.lang.String</code> : NULL이면 text 문자열을 아니면 기존 문자열
	 *         반환
	 */
	public static String strNull(final Object obj, final String text) {
		return (obj == null) ? text : obj.toString();
	}

	public static String todayTimesec2() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new java.util.Date());
	}

	public static String randomNum() {
		String str = "";
		Random r = new Random();

		int i = r.nextInt(10);

		return i + "";

	}

	/**
	 * strDate : 날짜 표시(Format :1:YYYY.MM.DD, 2:YY.MM.DD, 3:yyyy년mm월dd일,
	 * 4:yy년mm월dd일)
	 *
	 * @param str
	 *            <code>java.lang.String</code>
	 * @param flag
	 *            <code>java.lang.Integer</code>
	 * @return strnew <code>java.lang.String</code>
	 */
	public static String strDate(final String str, final int flag) {
		String strnew = "";

		if (!(strNull(str).trim().length() == 8)) {
			return strNull(str);
		}

		if (flag == 1) {
			strnew = str.substring(0, 4) + "." + str.substring(4, 6) + "."
					+ str.substring(6, 8);
		} else if (flag == 2) {
			strnew = str.substring(2, 4) + "." + str.substring(4, 6) + "."
					+ str.substring(6, 8);
		} else if (flag == 3) {
			strnew = str.substring(0, 4) + "년" + str.substring(4, 6) + "월"
					+ str.substring(6, 8) + "일";
		} else if (flag == 4) {
			strnew = str.substring(2, 4) + "년" + str.substring(4, 6) + "월"
					+ str.substring(6, 8) + "일";
		} else if (flag == 5) {
			strnew = str.substring(0, 4) + "/" + str.substring(4, 6) + "/"
					+ str.substring(6, 8);
		} else if (flag == 6) {
			strnew = str.substring(0, 4) + "-" + str.substring(4, 6) + "-"
					+ str.substring(6, 8);
		}
		return strnew;
	}

	public static String strDate2(final String str, final int flag) {
		String strnew = "";

		if (!(strNull(str).trim().length() == 4)) {
			return strNull(str);
		}

		if (flag == 1) {
			strnew = str.substring(0, 2) + "/" + str.substring(2, 4);
		} else if (flag == 2) {
			strnew = str.substring(0, 2) + "월" + str.substring(2, 4) + "일";
		}

		return strnew;
	}

	/**
	 * strTime : 시간 표시(Format : 1: HH:MM, 2: HH시MM분)
	 *
	 * @param str
	 *            <code>java.lang.String</code>
	 * @param flag
	 *            <code>java.lang.Integer</code>
	 * @return str <code>java.lang.String</code>
	 */
	public static String strTime(final String str, final int flag) {
		String strnew = "";

		if (strNull(str).trim().length() == 0) {
			return strNull(str);
		}

		if (flag == 1) {
			strnew = str.substring(0, 2) + ":" + str.substring(2, 4);
		} else if (flag == 2) {
			strnew = str.substring(0, 2) + "시" + str.substring(2, 4) + "분";
		}
		return strnew;
	}

	/**
	 * strDateTime : 날짜시간 표시
	 */
	public static String strDateTime(final String str) {
		String strnew = "";

		if (strNull(str).trim().length() < 12) {
			return strNull(str);
		}

		if (str.length() == 12) {
			strnew = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10) + ":" +  str.substring(10, 12);
		} else if (str.length() == 14){
			strnew = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10) + ":" +  str.substring(10, 12) + ":" +  str.substring(12, 14);
		}

		return strnew;
	}

	/**
	 * Comma : 숫자 Comma변환 int
	 *
	 * @param i
	 *            <code>java.lang.Integer</code>
	 * @return str <code>java.lang.String</code>
	 */
	public static String Comma(final int i) {
		DecimalFormat fmt1 = new DecimalFormat("#,###,###,###");
		String str = fmt1.format(i);
		return str;
	}

	/**
	 * Comma : 숫자 double
	 *
	 * @param d
	 *            <code>java.lang.Double</code>
	 * @return str <code>java.lang.String</code>
	 */
	public static String Comma(final double d) {
		DecimalFormat fmt1 = new DecimalFormat("#,###,###,###,###,###");
		String str = fmt1.format(d);
		return str;
	}

	/**
	 * Comma : 숫자 String
	 *
	 * @param s
	 *            <code>java.lang.String</code>
	 * @return str <code>java.lang.String</code>
	 */
	public static String Comma(String s) {
		String str = "";
		s = strNull(s);
		if (isNull(s)) {
			str = s;
		} else {
			int i = Integer.parseInt(s);
			DecimalFormat fmt1 = new DecimalFormat("#,###,###,###,###,###");
			str = fmt1.format(i);

		}
		return str;
	}

	/**
	 * unCommaToLong : 컴마 있는 문자를 long으로 변환
	 *
	 * @param s
	 *            <code>java.lang.String</code>
	 * @return myNum <code>java.lang.Long</code>
	 */
	/*public static long unCommaToLong(String s) throws Exception {
		NumberFormat nf = NumberFormat.getInstance();
		Number myNum = nf.parse(s);

		return myNum.longValue();
	}*/

	/**
	 * nl2br : 엔터 문자를 br 태그 로 변환
	 *
	 * @param str
	 *            <code>java.lang.String</code>
	 * @return strnew <code>java.lang.String</code>
	 */
	/*public static String nl2br(String str) {
		//
		// str = strNull (str); int i = str.indexOf("\n"); StringBuffer strnew =
		// new StringBuffer(); while (i > -1) {
		// strnew.append(str.substring(0,i)); strnew.append("<br>");
		// strnew.append(str.substring(i+1)); i = str.indexOf("\n"); }
		// strnew.append(str); return strnew.toString();
		//
		str = strNull(str);
		str = replace(str, "\n", "<br>");
		str = replace(str, "\r", "");
		return str;
	}*/

	/**
	 * nl2br : 엔터 문자를 br 태그 로 변환
	 *
	 * @param str
	 *            <code>java.lang.String</code>
	 * @return strnew <code>java.lang.String</code>
	 */
	/*public static String br2nl(String s) {
		s = replace(s, "<br>", "\n");
		return s;
	}*/

	/**
	 * html2text : html tag를 text형식에 맞춰 변환 &lt; 를 &amp;lt; 로 변경, &gt; 를 &amp;gt;
	 * 로 변경, 공백을 &amp;nbsp; 로 변경 엔터문자를 br 태그로 변경
	 *
	 * @param contents
	 *            <code>java.lang.String</code> : 내용
	 * @return contents <code>java.lang.String</code> : 변환된 내용
	 */
	/*public static String html3text(String contents) {
		contents = contents.replaceAll("<", "&lt;");
		contents = contents.replaceAll(">", "&gt;");
		contents = contents.replaceAll(" ", "&nbsp;");

		return contents;
	}*/

	/**
	 * html2text : html tag를 text형식에 맞춰 변환 &lt; 를 &amp;lt; 로 변경, &gt; 를 &amp;gt;
	 * 로 변경, 공백을 &amp;nbsp; 로 변경 엔터문자를 br 태그로 변경
	 *
	 * @param contents
	 *            <code>java.lang.String</code> : 내용
	 * @return contents <code>java.lang.String</code> : 변환된 내용
	 */
	/*public static String html2text(String contents) {
		contents = contents.replaceAll("<", "&lt;");
		contents = contents.replaceAll(">", "&gt;");
		contents = contents.replaceAll(" ", "&nbsp;");
		contents = contents.replaceAll("\n", "<br>");

		return contents;
	}*/

	/**
	 * isNull : 인자값이 null 인지를 체크
	 *
	 * 입력된 값이 <code>null</code> 혹은 빈 문자열인지를 확인한다.
	 *
	 * @param str
	 *            <code>null</code> 혹은 빈 문자열인지 확인할 문자열
	 * @return boolean
	 */
	public static boolean isNull(final String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * cutSting : 글자를 MaxLen 길이만큼 잘라서 "..."을 붙인다. 변환(한영모두)
	 *
	 * @param str
	 *            <code>java.lang.String</code>
	 * @param MaxLen
	 *            <code>java.lang.Integer</code>
	 * @return strnew <code>java.lang.String</code>
	 */
	/*public static String cutString(String str, int MaxLen) {
		String strnew = "";
		int a = 0;

		if (str == null || str.length() == 0) {
			return strnew;
		}

		char c;

		if (str.length() > MaxLen) {
			for (int i = 0; i < MaxLen - 1; i++) {
				if (i == str.length()) {
					break;
				}

				c = str.charAt(i);

				strnew = strnew + c;

				if (!(Character.isLetter(c) && !(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z'))) {
					a += 1;
					if (a == 2) {
						a = 0;
						MaxLen += 1;
					}
				}
			}

			if (strnew.length() != str.length()) {
				strnew = strnew + "...";
			}
		} else {
			strnew = str;
		}
		return strnew;
	}*/

	/**
	 * explode : 문자열를 입력 받아 delim 의 문자로 나누어 String 배열에 저장한다.
	 *
	 * @param str
	 *            <code>java.lang.String</code>
	 * @return delim <code>java.lang.String</code>
	 */
	/*public static String[] explode(String str, String delim) {
		String[] reStr = null;

		if (!str.equals("")) {
			StringTokenizer st = new StringTokenizer(str, delim);
			reStr = new String[st.countTokens()];

			int i = 0;
			while (st.hasMoreTokens()) {
				reStr[i] = st.nextToken();
				i++;
			}
		}
		return reStr;
	}*/

	/**
	 * toKor : 문자열(8859_1) -> KSC5601로 변환
	 *
	 * @param s
	 *            <code>java.lang.String</code> : 문자열
	 * @return s <code>java.lang.String</code> : 변환된 문자열
	 */
	public static String toKor(final String s) {
		try {
			if (s != null) {
				return (new String(s.getBytes("8859_1"), "KSC5601"));
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			return "Encoding Error";
		}
	}

	/**
	 * toKor : 문자열 배열(8859_1) -> KSC5601로 변환
	 *
	 * @param s
	 *            <code>java.lang.String</code> : 문자열 배열
	 * @return s <code>java.lang.String</code> : 변환된 문자열 배열
	 */
	public static String[] toKor(final String[] s) {
		try {
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					s[i] = new String(s[i].getBytes("8859_1"), "KSC5601");
				}
				return (s);
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * toEng : 문자열(KSC5601) -> 8859_1로 변환
	 *
	 * @param s
	 *            <code>java.lang.String</code> : 문자열
	 * @return s <code>java.lang.String</code> : 변환된 문자열
	 */
	public static String toEng(final String s) {
		try {
			if (s != null) {
				return (new String(s.getBytes("KSC5601"), "8859_1"));
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			return "Encoding Error";
		}
	}

	/**
	 * toEng : 문자열 배열(KSC5601) -> 8859_1로 변환
	 *
	 * @param s
	 *            <code>java.lang.String</code> : 문자열 배열
	 * @return s <code>java.lang.String</code> : 변환된 문자열 배열
	 */
	public static String[] toEng(final String[] s) {
		try {
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					s[i] = new String(s[i].getBytes("KSC5601"), "8859_1");
				}
				return (s);
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * EtoUTF : 문자열(8859_1) -> UTF-8로 변환
	 *
	 * @param s
	 *            <code>java.lang.String</code> : 문자열
	 * @return s <code>java.lang.String</code> : 변환된 문자열
	 */
	public static String EtoUTF(final String s) {
		try {
			if (s != null) {
				return (new String(s.getBytes("8859_1"), "UTF-8"));
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			return "Encoding Error";
		}
	}

	/**
	 * EtoUTF : UTF-8로 변환 -> 문자열(8859_1)
	 *
	 * @param s
	 *            <code>java.lang.String</code> : 문자열
	 * @return s <code>java.lang.String</code> : 변환된 문자열
	 */
	public static String UTFtoE(final String s) {
		try {
			if (s != null) {
				return (new String(s.getBytes("UTF-8"), "8859_1"));
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			return "Encoding Error";
		}
	}

	/**
	 * KtoUTF : 문자열(KSC5601) -> UTF-8로 변환
	 *
	 * @param s
	 *            <code>java.lang.String</code> : 문자열
	 * @return s <code>java.lang.String</code> : 변환된 문자열
	 */
	/*public static String KtoUTF(String s) {
		try {
			if (s != null) {
				return (new String(s.getBytes("KSC5601"), "UTF-8"));
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			return "Encoding Error";
		}
	}*/

	/**
	 * 게시판
	 *
	 * @param title
	 *            String
	 * @param len
	 *            int
	 * @return String
	 */
	/*public static String getTitle(String title, int len) {
		String shortTitle = null;
		try {
			if (title != null) {
				if (title.length() > len) {
					shortTitle = title.substring(0, len) + "...";
				} else {
					shortTitle = title;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return shortTitle;
	}*/

	/**
	 * delimeter기준으로 idx번째 문자열을 가져온다.
	 *
	 * @param str
	 *            String
	 * @param deli
	 *            String
	 * @param idx
	 *            int
	 * @return String
	 */
	/*public static String splitStr(String str, String deli, int idx) {

		try {
			if (!StringUtil.strNull(str).equals("")) {
				String[] result = str.split(deli);

				return result[idx];
			} else {
				return str.trim();
			}
		} catch (Exception e) {
			return "";
		}
	}*/

	/**
	 * 빈 문자열인지를 확인한다
	 *
	 * @author leaf74
	 * @param str
	 *            빈 문자열인지 확인할 문자열
	 * @return true/false
	 */

	public static boolean isEmpty(final String str) {

		if (str == null || str.trim().length() == 0) {
			return true;
		}

		else {
			return false;
		}

	}

	/**
	 * 오늘 날짜 리턴한다.
	 *
	 * @return String
	 */
	public static String today() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new java.util.Date());
	}

	public static String todayTimesec() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(new java.util.Date());
	}

	public static String todayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new java.util.Date());
	}

	public static String strDate(final String str) {
		String ret = strNull(str);

		if (ret.trim().equals("")) {
			return strNull(str);
		}

		// JSP뿌릴때
		if (ret.length() == 8) {
			ret = ret.substring(0, 4) + "-" + ret.substring(4, 6) + "-"
					+ ret.substring(6, 8);
			// 디비 입력시
		} else if (ret.length() == 10) {
			ret = replace(ret, "-", "");
		}
		return ret;
	}

	/*public static String convertHtmlBr(String comment) {
		int length = comment.length();
		StringBuffer buffer = new StringBuffer();
		if (comment.equals(null)) {
			buffer.append("");
			return buffer.toString();
		}

		for (int i = 0; i < length; ++i) {
			String comp = comment.substring(i, i + 1);
			if ("\r".compareTo(comp) == 0) {
				comp = comment.substring(++i, i + 1);
				if ("\n".compareTo(comp) == 0)
					buffer.append("<br/>");
			} else {
				buffer.append(comp);
			}
		}
		return buffer.toString();
	}

	public static String text2html(String contents) {
		contents = contents.replaceAll("&lt;", "<");
		contents = contents.replaceAll("&gt;", ">");
		contents = contents.replaceAll("&nbsp;", " ");
		return contents;
	}

	public static String strCut(String str, int size) {
		// Vector returnsStr = new Vector();

		// String ctmp = str.replaceAll("'", "''");

		int cntlen = str.getBytes().length;
		int bylen = 0, strlen = str.length();

		char c;
		String ct = "";
		if (cntlen > size) {
			for (int i = 0; i < strlen; i++) {
				c = str.charAt(i);
				bylen++;
				if (c > 127) {
					bylen++; // 한글이다..
				}
				if (size < bylen) {
					ct = str.substring(0, i);
					break;
				}
			}
			ct = ct + "...";
		} else {
			ct = str;
		}

		return ct;
	}*/

	public static String strSmsCut(final String str, final int size) {
		// Vector returnsStr = new Vector();

		// String ctmp = str.replaceAll("'", "''");s

		int cntlen = str.getBytes().length;
		int bylen = 0, strlen = str.length();

		char c;
		String ct = "";
		if (cntlen > size) {
			for (int i = 0; i < strlen; i++) {
				c = str.charAt(i);
				bylen++;
				if (c > 127) {
					bylen++; // 한글이다..
				}
				if (size < bylen) {
					ct = str.substring(0, i);
					break;
				}
			}
			ct = ct;
		} else {
			ct = str;
		}

		return ct;
	}

	/**
	 * HTML 태그를 지운다.
	 *
	 * @param s
	 * @return
	 */
	/*public static String removeTag(String s) {
		if (s == null || s.equals("")) {
			s = "";
		} else {
			// s =
			// s.replaceAll("(?:<!.*?(?:--.*?--\\s*)*.*?>)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)","");

			Pattern p = Pattern.compile("\\<(\\/?)(\\w+)*([^<>]*)>");
			Matcher m = p.matcher(s);
			s = m.replaceAll("");

			// s = s.replaceAll("&nbsp;", "");
		}
		return s;

	}*/

	/**
	 * script style 등을 지운다.
	 *
	 * @param s
	 * @return
	 */
	/*public static String removeScript(String s) {
		if (s == null || s.equals("")) {
			s = "";
		} else {

			Pattern p = Pattern
					.compile("\\<(\\/?)(script|style|SCRIPT|STYLE)([^<>]*)>");
			Matcher m = p.matcher(s);
			s = m.replaceAll("");
		}
		return s;

	}

	public static String getURL(HttpServletRequest request) {
		Enumeration param = request.getParameterNames();
		String name = ""; // Tip : 선언할때 빈문자열을 넣어두면...null point 익셉션이 잘 안나온답니다 ^^
		String value = "";
		String strParam = "";
		String URL;
		while (param.hasMoreElements()) {
			name = (String) param.nextElement();
			value = request.getParameter(name);
			strParam = name + "=" + value + "&" + strParam;
		}
		URL = request.getRequestURL() + "?" + strParam;
		return URL;
	}*/

	/**
	 * 왼쪽 '0'을 채운다.
	 *
	 * @param s
	 * @return
	 */
	public static String lpad(final String s, final int len) {
		String reVal = "";

		reVal = s;

		if (s.length() < len) {
			for (int i = 0; i < len - s.length(); i++) {
				reVal = "0" + reVal;
			}
		}

		return reVal;
	}

	/**
	 * 오른쪽 '0'을 채운다.
	 *
	 * @param s
	 * @return
	 */
	public static String rpad(final String s, final int len) {
		String rtnVal = "";

		rtnVal = s;

		if (s.length() < len) {
			for (int i = 0; i < len - s.length(); i++) {
				rtnVal = rtnVal + "0";
			}
		}

		return rtnVal;
	}

	/**
	 * file을 읽어서 string으로 변환한다.
	 *
	 * @param s
	 * @return
	 */
	/*public static String readFile(String file_path) {

		String r = "";

		FileInputStream fin;

		try {
			fin = new FileInputStream(file_path);

			while (true) {
				String file_read = new DataInputStream(fin).readLine();
				if (file_read == null)
					break;

				r = r + toKor(file_read) + ";";
			}

			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
			// gLw.writeln(e.toString());
		}

		return r;
	}*/

	/**
	 * 스크립트 실행.
	 *
	 * @param s
	 * @return
	 */
	/*public static String alertMsg(String msg, String t) {

		String str = "";

		str = "<script language='JavaScript'>\n" + "<!--\n" + "alert('" + msg
				+ "');\n";

		if (t.equals("B"))
			str = str + "history.back();\n";
		else if (t.equals("C"))
			str = str + "window.close();\n";
		else if (t.equals("N"))
			str = str + "" + "\n";
		else
			str = str + "location.href = '" + t + "'\n";

		str = str + "//-->\n</script>";

		return str;

	}*/

	/**
	 * 핸드폰 번호 숫자만 리턴
	 */
	/*public static String mobileChk(String mobile) {

		String mobileNo = "";

		for (int i = 0; i < mobile.length(); i++) {

			char c = mobile.charAt(i);

			if (c >= '0' && c <= '9') {
				mobileNo += c;
			}
		}

		return mobileNo;
	}*/

	/**
	 *
	 * @param StartDate
	 *            , EndDate, aDayOfTheWeek
	 * @param 시작일자
	 *            , 종료일자, 강의 요일
	 *
	 */
	/*public static String daySchedule(String startDt, String endDt, String weeks) {

		String allDay = "";
		int year = 0;
		int month = 0;
		int dy = 0;

		Calendar myCalendar = Calendar.getInstance();

		try {

			String[] tempWeeks = weeks.split(";");

			year = Integer.parseInt(startDt.substring(0, 4));
			month = Integer.parseInt(startDt.substring(5, 7));
			dy = Integer.parseInt(startDt.substring(8, 10));

			myCalendar.set(myCalendar.YEAR, year);
			myCalendar.set(myCalendar.MONTH, month - 1);
			myCalendar.set(myCalendar.DAY_OF_MONTH, dy);

			while (true) {

				for (int i = 0; i < tempWeeks.length; i++) {

					if (myCalendar.get(myCalendar.DAY_OF_WEEK) == Integer
							.parseInt(tempWeeks[i])) {

						String tmpYear = Integer.toString(myCalendar
								.get(myCalendar.YEAR));
						String tmpMonth = Integer.toString(myCalendar
								.get(myCalendar.MONTH) + 1);
						String tmpDay = Integer.toString(myCalendar
								.get(myCalendar.DAY_OF_MONTH));

						allDay += tmpYear + "-" + tmpMonth + "-" + tmpDay + ";";
					}
				}

				myCalendar.add(Calendar.DATE, 1);

				if (myCalendar.get(myCalendar.YEAR) == Integer.parseInt(endDt
						.substring(0, 4))
						&& (myCalendar.get(myCalendar.MONTH) + 1) == Integer
								.parseInt(endDt.substring(5, 7))
						&& (myCalendar.get(myCalendar.DAY_OF_MONTH)) == Integer
								.parseInt(endDt.substring(8, 10))) {

					for (int i = 0; i < tempWeeks.length; i++) {

						if (myCalendar.get(myCalendar.DAY_OF_WEEK) == Integer
								.parseInt(tempWeeks[i])) {

							String tmpYear = Integer.toString(myCalendar
									.get(myCalendar.YEAR));
							String tmpMonth = Integer.toString(myCalendar
									.get(myCalendar.MONTH) + 1);
							String tmpDay = Integer.toString(myCalendar
									.get(myCalendar.DAY_OF_MONTH));

							allDay += tmpYear + "-" + tmpMonth + "-" + tmpDay
									+ ";";
						}
					}

					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			allDay = "fail";
		} finally {
			return allDay;
		}

	}*/

	/**
	 * 인증키 생성 MD5
	 *
	 */
	/*public static String makeMD5(String input) throws Exception {
		byte[] digestBuf = null;
		StringBuffer strBuf = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			digestBuf = md.digest();
			for (int i = 0; i < digestBuf.length; i++) {
				int c = digestBuf[i] & 0xff;
				if (c <= 15) {
					strBuf.append("0");
				}
				strBuf.append(Integer.toHexString(c));
			}
			return strBuf.toString();
		} catch (Exception e) {
			throw e;
		}
	}*/

	// String을 delimeter 단위로 잘라 ArrayList로 반환
	/*public static ArrayList positionToken(String input, String delimiter)
			throws Exception {

		ArrayList objArray = new ArrayList();
		String token = null;
		int pos;

		do {
			pos = input.indexOf(delimiter);
			if (pos >= 0) {
				token = input.substring(0, pos);
				input = input.substring(pos + 1);
			} else {
				token = input;
				input = "";
			}

			objArray.add(token);
		} while (pos >= 0);

		return objArray;
	}*/

	public static String[] splite(final String st, final String key) {
		if (st == null) {
			return new String[0];
		}

		StringTokenizer token = new StringTokenizer(st, key);
		int tokenCount = token.countTokens();
		String[] arr = new String[tokenCount];
		for (int i = 0; i < tokenCount; i++) {
			arr[i] = token.nextToken();
		}
		return arr;
	}

	/*public static String[] CheckUnique(String[] str) {
		if (str == null)
			return str;
		String[] unique = null;

		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < str.length; j++) {
				if (str[i] != str[j]) {
				}
			}
		}
		return unique;
	}

	public static ArrayList CheckUnique(ArrayList str) {
		if (str == null)
			return str;
		ArrayList unique = null;
		ArrayList temp = null;

		Collections.sort(str);
		for (int m = 0; m < str.size(); m++) {

		}

		for (int i = 0; i < str.size(); i++) {
			for (int j = i + 1; j < str.size(); j++) {
				if (!(str.get(i)).equals(str.get(j))) {
					// for(int k=0;k<temp.size();k++){
					// temp.add(str.get(i));
					// }

				} else {

				}

			}
		}
		// if(temp != null){
		// }
		// for(int i = 0; i<temp.size();i++){
		// }
		return unique;
	}

	public static boolean isHangle(String str) {
		// Vector returnsStr = new Vector();

		// String ctmp = str.replaceAll("'", "''");
		boolean tfHangle = false;

		int strlen = str.length();

		char c;
		for (int i = 0; i < strlen; i++) {
			c = str.charAt(i);
			if (c > 127) {
				tfHangle = true;
			}
		}

		return tfHangle;
	}

	public static boolean isFiltering(String ctt) {
		boolean isError = false;
		String[] filter = new String[] { "String", "javascript", "function",
				"onload", "onclick", "script", "alert" };
		for (int i = 0; i < filter.length; i++) {
			if (ctt.indexOf(filter[i]) > 0) {
				isError = true;
				log.info("filter[" + i + "] : " + filter[i]);
				break;
			}
		}

		return isError;
	}*/

	// 주민등록번호 숨기기
	public static String scrbrCtz(final String ctz) {
		String tmp1 = "";

		if (ctz == null || ctz.length() != 13) {
			return null;
		} else {
			tmp1 = ctz.substring(0, 6);
		}

		return tmp1.concat("-*******");
	}

	public static String scrbrCtz2(final String ctz) {
		String tmp1 = "";
		String tmp2 = "";

		if (ctz == null || ctz.length() != 13) {
			return null;
		} else {
			tmp1 = ctz.substring(0, 6);
			tmp2 = ctz.substring(6, 13);

		}

		return tmp1.concat("-").concat(tmp2);
	}

	public static String zeroClean(final String str) {

		String TempStr = "";
		int strlen = str.length();
		int zeroCnt = 0;
		char c;
		for (int i = 0; i < strlen; i++) {
			c = str.charAt(i);
			if (c == 48 || c == 32) {
				zeroCnt += 1;
				if (zeroCnt == strlen) {
					TempStr = "0";
				}
			} else {
				TempStr = str.substring(zeroCnt);
				return TempStr;
			}
		}
		return TempStr;
	}

	public static String callNumFormat(final String phonNum) {
		String phon_num = "";
		String mov_1 = "";
		String mov_2 = "";
		String mov_3 = "";

		if ("".equals(phonNum) || phonNum == null) {
			return phonNum;
		}

		if (phonNum.length() == 12) { // ex>000206764832
			mov_1 = phonNum.substring(0, 4);
			mov_2 = phonNum.substring(4, 8);
			mov_3 = phonNum.substring(8, 12);

			// log.info("mov_1 : " + mov_1);
			// log.info("mov_2 : " + mov_2);
			// log.info("mov_3 : " + mov_3);

			// 국번이 4자리로 들어오므로 최초자릿수를 자른 후 세팅한다.
			if ("000".equals(phonNum.substring(0, 3))) {
				mov_1 = mov_1.substring(2, 4);
			} else {
				mov_1 = mov_1.substring(1, 4);
			}

			// 두번째 번호가 0으로 시작 될 때는 버린다.
			if (mov_2.startsWith("0")) {
				mov_2 = mov_2.substring(1);
				// log.info("mov_2 : " + mov_2);
			}
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 11) {
			mov_1 = phonNum.substring(0, 3);
			mov_2 = "";
			mov_3 = "";

			if ("00".equals(phonNum.substring(0, 2))) {
				mov_1 = phonNum.substring(1, 3);
			} else {
				mov_1 = phonNum.substring(0, 3);
			}

			if (phonNum.substring(3).startsWith("0")) {
				mov_2 = phonNum.substring(4, 7);
			} else {
				mov_2 = phonNum.substring(3, 7);
			}
			mov_3 = phonNum.substring(7);
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 10) {
			if ("02".equals(phonNum.substring(0, 2))) {
				mov_1 = phonNum.substring(0, 2);
				mov_2 = phonNum.substring(2, 6);
				mov_3 = phonNum.substring(6);
			} else {
				mov_1 = phonNum.substring(0, 3);
				mov_2 = phonNum.substring(3, 6);
				mov_3 = phonNum.substring(6);
			}
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 9) {
			mov_1 = phonNum.substring(0, 2);
			mov_2 = phonNum.substring(2, 5);
			mov_3 = phonNum.substring(5);
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 8) {
			mov_1 = phonNum.substring(0, 4);
			mov_2 = phonNum.substring(4, 8);
			return mov_1 + "-" + mov_2;
		} else {
			phon_num = phonNum;
		}

		return phon_num;
	}

	/*public static String[] strSmsCutt(String str, int maxSize) {
		str = str.replaceAll("\\|", "");

		int cntlen = str.getBytes().length;
		int bylen = 0, strlen = str.length();

		char c; // 한글자씩 자르기
		String tmpstr = "";

		String strSMS[] = null;
		int j = 1; // 배수구하기
		if (cntlen > maxSize) {
			for (int i = 0; i < strlen; i++) {
				c = str.charAt(i);
				bylen++;
				// log.info(" === "+c+" : "+bylen);
				if (c > 127) {
					bylen++; // 한글이다..
				}
				tmpstr += c;

				if (j * maxSize < bylen) {
					tmpstr += "|";
					j++;
				}
			}

			strSMS = tmpstr.split("\\|");
			// log.info("strSMS size " + strSMS.length);

		} else {
			strSMS[0] = str;
		}

		return strSMS;
	}*/

	/*public static void mangerSms(String strMsg) {

		try {
			String currentTime = DateUtil.getCurrentTime().toString();

			for (int i = 1; i < 9; i++) {
				// String sCode = "CMN_PLT_IFM_SMS"; // 업무코드
				String sCode = "FWD_TGT_JOINIFM_INFO"; // 업무코드
				log.info("업무코드 : " + "FWD_TGT_JOINIFM_INFO");
				String call = "";
				String user = "";

				if (i == 1) {
                    call = "01027612793"; // 한인규
                    user = currentTime + "A";
                } else if (i == 2) {
                    call = "01087526582"; // 이혜진
                    user = currentTime + "B";
                } else if (i == 3) {
                    call = "01038603256"; // 김성득
                    user = currentTime + "C";
                } else if (i == 4) {
                    call = "01067311472"; // 김용일
                    user = currentTime + "D";
                } else if (i == 5) {
                    call = "01020074550"; // 이종길
                    user = currentTime + "E";
                }
				String sCallNum = call;
				String sUserID = user;
				String sSenderNum = "0264008885"; // 발신번호 - 판매자 전화번호
				String sCallMsg = strMsg;

				// * Sms 발송하는 메소드
				// * @param sCallNum 수신번호
				// * @param sUserID 사용자아이디(SEQ_NUM 신청서 번호)
				// * @param sSenderNum 발신번호
				// * @param sCallMsg SMS 메시지
				com.sktel.tginterface.util.SmsUtil.sendSms(sCallNum, sUserID,
						sSenderNum, sCallMsg);
			}// for
		} catch (Exception e) {
			// TODO: handle exception
		}

	}*/

	/*public static String convertStr(String var) {
		String rtnVal = "0";
		if (var != null && var.trim().length() != 0) {
			int varInt = Integer.parseInt(var.trim());
			rtnVal = varInt + "";
		}
		return rtnVal;
	}*/

	// index 자리수를 절삭 시킴
	// ex. 99999999를 index 3으로 절삭 하면, 99999000 으로 변환.
	/*public static String roundDown(String str, int index) {
		String rtnVal = str;

		if (rtnVal == null) {
			return "0";
		}
		if ("".equals(rtnVal)) {
			return "0";
		}
		if (rtnVal.length() < index) {
			return "0";
		}

		int length = rtnVal.length();

		rtnVal = rtnVal.substring(0, length - index);

		return rpad(rtnVal, length);
	}*/

	public static String niceChangeGender(final String str) {
		String birth = str.substring(0, 6);
		String gender = str.substring(6, 7);
		if ("1".equals(gender) || "3".equals(gender) || "5".equals(gender)
				|| "7".equals(gender) || "9".equals(gender)) {
			gender = "1";
		} else {
			gender = "0";
		}
		return birth + gender;
	}

	/**
	 * 주민번호를 인자로 받아, 생년월일+성별 or 성별 값을 리턴한다.
	 *
	 * <pre>
	 * 01 : 생년월일+성별 (YYYYMMDD+M/F)
	 * 02 : 성별(M/F)
	 * </pre>
	 * @param ssn
	 * @param rtnTyp
	 * @return String
	 */
	public static String daouChangeBirthGender(final String ssn, final String rtnTyp) {
		String birth = ssn.substring(0,6);
		String gender = ssn.substring(6,7);
		if ("1".equals(gender) || "3".equals(gender) || "5".equals(gender)
				|| "7".equals(gender) || "9".equals(gender)) {
			gender = "M";
		} else {
			gender = "F";
		}

		if("01".equals(rtnTyp)) {
			return birth + gender;
		} else {
			return gender;
		}
	}

	/**
	 * 주민번호로 만 나이 반환
	 *
	 * @param jumin
	 *            (주민번호 앞자리+성별 or 주민번호13자리)
	 * @return
	 */
	public static String getAgeAvrage(final String jumin) {
		String birthday = "";
		String today = "";
		int nowage = 0; // 만 나이 계산

		try {
			today = DateUtil.getCurrDate();
			if (jumin.length() == 13) {
				birthday = juminToBirthday(jumin);
			} else if (jumin.length() == 7) {
				birthday = juminToBirthday(jumin.substring(0, 6),
						jumin.substring(6, 7));
			}

			int curYear = Integer.parseInt(today.substring(0, 4));
			int birthDay = Integer.parseInt(birthday.substring(0, 4));
			int curDate = Integer.parseInt(today.substring(4, 8));
			int birthDate = Integer.parseInt(birthday.substring(4, 8));

			nowage = (curDate >= birthDate) ? curYear - birthDay : curYear
					- birthDay - 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return String.valueOf(nowage);
	}

	/**
	 * 주민번호로 나이체크
	 *
	 * @param jumin
	 *            (주민번호 앞자리+성별 or 주민번호13자리)
	 * @param aau
	 *            (이상, 이하 구분)
	 * @param age
	 *            (기준 나이)
	 * @return
	 */
	/*public static String ageAvrageAboveUnderChk(String jumin, String aau,
			String age) {
		String birthday = "";
		String today = "";
		int ageChk = Integer.parseInt(age);
		int nowage = 0; // 만 나이 계산
		String result = "S"; // S : 가능, F : 불가능

		try {
			today = DateUtil.getToday();
			if (jumin.length() == 13) {
				birthday = juminToBirthday(jumin);
			} else if (jumin.length() == 7) {
				birthday = juminToBirthday(jumin.substring(0, 6),
						jumin.substring(6, 7));
			}

			int curYear = Integer.parseInt(today.substring(0, 4));
			int birthDay = Integer.parseInt(birthday.substring(0, 4));
			int curDate = Integer.parseInt(today.substring(4, 8));
			int birthDate = Integer.parseInt(birthday.substring(4, 8));

			nowage = (curDate >= birthDate) ? curYear - birthDay : curYear
					- birthDay - 1;
			log.info("만 " + nowage + "세");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("이상".equals(aau)) {
			if (nowage >= ageChk) {
				log.info("가능");
				result = "S";
			} else {
				log.info("불가능");
				result = "F";
			}
		} else if ("이하".equals(aau)) {
			if (nowage > ageChk) {
				log.info("불가능");
				result = "F";
			} else {
				log.info("가능");
				result = "S";
			}
		}

		return result;
	}*/

	public static String juminToBirthday(final String juminFront, final String gender) {
		String jumin = juminFront;
		String genda = gender.substring(0, 1);
		if ("1".equals(genda) || "2".equals(genda) || "5".equals(genda)
				|| "6".equals(genda)) {
			jumin = "19" + jumin;
		} else if ("9".equals(genda) || "0".equals(genda)) {
			jumin = "18" + jumin;
		} else {
			jumin = "20" + jumin;
		}
		return jumin;
	}

	public static String juminToBirthday(final String juminFull) {
		String jumin = juminFull.substring(0, 6).toString();
		String genda = juminFull.substring(6, 7).toString();
		if ("1".equals(genda) || "2".equals(genda) || "5".equals(genda)
				|| "6".equals(genda)) {
			jumin = "19" + jumin;
		} else if ("9".equals(genda) || "0".equals(genda)) {
			jumin = "18" + jumin;
		} else {
			jumin = "20" + jumin;
		}
		return jumin;
	}

	public static String bizNumBar(String bizNum) {
		if (bizNum.length() != 10) {
			return bizNum;
		}

		String biznum1 = bizNum.substring(0, 3).toString();
		String biznum2 = bizNum.substring(3, 5).toString();
		String biznum3 = bizNum.substring(5, 10).toString();

		bizNum = biznum1 + "-" + biznum2 + "-" + biznum3;
		return bizNum;
	}

	/**
	 * 일반전화번호, 휴대폰전화번호를 자리별로 분리한다.
	 *
	 * @param phoneNum
	 */
	public static String[] getSplitPhoneNum(final String phoneNum) {
		String[] resultArr = new String[] { "", "", "" };
		String regExp = "^(01\\d{1}|02|0502|0505|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})";
		Matcher matcher = Pattern.compile(regExp).matcher(phoneNum);
		if (matcher.matches()) {
			resultArr[0] = matcher.group(1);
			resultArr[1] = matcher.group(2);
			resultArr[2] = matcher.group(3);
		}
		return resultArr;
	}

	/**
	 * 랜덤숫자를 반환한다.
	 *
	 * @param length
	 */
	public static String getRandomNumber(final int length) {
		StringBuilder sNumber = new StringBuilder();
		int iNumber;

		if (length < 1) {
			return null;
		}

		for(int i=1; i<=length;i++) {
			iNumber = (int)(Math.random() * 10);			//그다음부터는 0-9사이
			sNumber.append(iNumber);
		}

		return sNumber.toString();
	}

	public static List<String> split(final String delimStr, final String delim) {
		String[]		strArr;
		List<String>	strList = new ArrayList<>();
		if (delimStr != null) {
			strArr = delimStr.split(delim);
			if (strArr != null) {
				for (int i = 0; i < strArr.length; i++) {
					strList.add(strArr[i]);
				}
			}
		}
		return strList;
	}

	public static String join(final List<String> strList, final String delim) {
		StringBuilder	strBuilder = new StringBuilder();
		int strSize = strList.size();
		for (int i = 0; i < strSize; i++) {
			strBuilder.append(strList.get(i));
			if (i < strSize - 1) {
				strBuilder.append(delim);
			}
		}
		return strBuilder.toString();
	}

	public static String join(final String[] strArr, final String delim) {
		StringBuilder	strBuilder = new StringBuilder();
		int strSize = strArr.length;
		for (int i = 0; i < strSize; i++) {
			strBuilder.append(strArr[i]);
			if (i < strSize - 1) {
				strBuilder.append(delim);
			}
		}
		return strBuilder.toString();
	}

	public static boolean isIncluded(final String inStr, final String delimStr, final String delim) {
		List<String> strList = split(delimStr, delim);
		for (String str : strList) {
			if (inStr.equals(str)) {
				return true;
			}
		}
		return false;
	}

    public static String defaultString(String str) {
        if ( str == null)
        	return "";
        else
        	return str;
    }


	public static void main(final String[] args) throws Exception {


		Random rnd = new Random();
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < 10; i++) {
			System.out.println(rnd.nextBoolean());
			if (rnd.nextBoolean()) {
				buf.append((char) ((rnd.nextInt(26)) + 97));
			} else {
				buf.append((rnd.nextInt(10)));
			}
		}

		System.out.println(buf);

		//		StringUtil kkk = new StringUtil();
		//
		//		System.out.println(ageAvrageAboveUnderChk("5101311057117", "이상", "65"));
		//		System.out.println(ageAvrageAboveUnderChk("5102011057117", "이상", "65"));
		//		System.out.println(ageAvrageAboveUnderChk("5102022057117", "이상", "65"));
		//		System.out.println(ageAvrageAboveUnderChk("9701311057117", "이하", "18"));// 불가능
		//		System.out.println(ageAvrageAboveUnderChk("9702011057117", "이하", "18"));// 불가능
		//		System.out.println(ageAvrageAboveUnderChk("9702021057117", "이하", "18"));// 가능
		//		System.out.println(ageAvrageAboveUnderChk("9703031057117", "이하", "18"));// 가능
		//		System.out.println(ageAvrageAboveUnderChk("9802011057117", "이하", "18"));// 가능
		//
		//		System.out.println(bizNumBar("10182611171"));
		// System.out.println(("5112301057117".substring(0, 6)));
		// System.out.println(("5112301057117".substring(6, 8)));
		// System.out.println(("5112301057117".substring(8, 10)));

		// System.out.println( "is null check 난값있당 : " + kkk.isNull("박영민"));
		// System.out.println( "is null check 난값없당 : " + kkk.isNull(""));
		//
		// String str = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라만세 무궁화 삼천리 화려강산";
		//
		// System.out.println("str 내문자열 : " + str);
		// System.out.println("str 난 조금만 보여줘 : " + kkk.cutString(str, 10));
		//
		//
		// System.out.println("convertStr[]" + kkk.convertStr("     000000"));
		// System.out.println("convertStr[]" + kkk.convertStr("           "));

		// System.out.println(roundDown("50999", 10));

		/*
		 *
		 * String str =
		 * "가나다라마바사아유키대리점 대리점테스트01(DTEST01)가나다라마바사아유키대리점 대리|점테스트01(DTEST01)가나다라마바사아유키대리점 대리점테스트01(DTEST01)가나다라마바사아유키대리점 대리|점테스트01(DTEST01)가나다라마바사아유키대리점 대리점테스트01(DTEST01)가나다라마바사아유키대리점 대리|점테스트01(DTEST01)가나다라마바사아유키대리점 대리점테스트01(DTEST01)가나다라마바사아유키대리점 대리|점테스트01(DTEST01)가나다라마바사아유키대리점 대리점테스트01(DTEST01)가나다라마바사아유키대리점 대리|점테스트01(DTEST01)"
		 * ;
		 *
		 * String sms1[]=a.strSmsCutt(str, 80);
		 *
		 * for (int i=0;i<sms1.length;i++){ System.out.println("sms["+i+"] : " +
		 * sms1[i]); }
		 *
		 * System.out.println("str :" + str); String strSMS[]= str.split("\\|");
		 * for (int i=0;i<strSMS.length;i++){
		 * System.out.println("strSMS["+i+"] : " + strSMS[i]); }
		 */
		/*
		 * String txt=""; String txt1=""; String txt2=""; String txt3=""; String
		 * txt4="";
		 *
		 * String txt7=""; String txt9=""; String txt8="";
		 *
		 *
		 * //txt = page.callValue(
		 * "http://localhost:8088/applform/SearchScrbLayout.do?appl_form_seq="
		 * +"D0000003006");
		 *
		 *
		 * txt = a.callNumFormat("15663532"); //8
		 *
		 * txt2 = a.callNumFormat("0232964070"); //10
		 *
		 * // //000226828012 txt3 = a.callNumFormat("00203029300"); //11 txt4 =
		 * a.callNumFormat("001104660709"); //12
		 *
		 *
		 * txt7 = a.callNumFormat("025412011"); //9 txt8 =
		 * a.callNumFormat("000226828012"); //12
		 *
		 * txt9 = a.callNumFormat(null); //12
		 *
		 *
		 * System.out.println(txt); System.out.println(txt1);
		 * System.out.println(txt2); System.out.println(txt3);
		 * System.out.println(txt4);
		 *
		 * System.out.println(txt7); System.out.println(txt8);
		 * System.out.println(txt9 +"//123123123123");
		 */
	}

	//날짜 차이
	public static long diffDays(final String date1, final String date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.set(Integer.parseInt(date1.substring(0,4)), Integer.parseInt(date1.substring(4,6))-1, Integer.parseInt(date1.substring(6,8)));
		cal2.set(Integer.parseInt(date2.substring(0,4)), Integer.parseInt(date2.substring(4,6))-1, Integer.parseInt(date2.substring(6,8)));
		Date newDate1 = cal1.getTime();
		Date newDate2 = cal2.getTime();
		long mill = Math.abs(newDate2.getTime() - newDate1.getTime());
		long days = TimeUnit.MILLISECONDS.toDays(mill);
		return days;
	}

	//오늘과 날짜 차이(일자까지의 차이)
	public static long diffDaysToday(final String date) {
		long  SwingSmsSndDtm = Long.parseLong(date);
		long  Dtoday = Long.parseLong(today());
		long days = Dtoday - SwingSmsSndDtm;
		return days;

	}

	//오늘과 날짜 차이(시분초 포함 차이)
	public static long diffDaystodayTime(final String SwingSmsSndDtm) {
		Double  SmsSndDtm = Double.parseDouble(SwingSmsSndDtm);
		Double  DtodayTime = Double.parseDouble(todayTime());
		long mill = Double.doubleToLongBits((DtodayTime - SmsSndDtm));
		return mill;
	}

	//고객명 마스킹처리
	public static String maskCustNm(final String custNm, final String applFormSt, String commReqDt) {
		String result = "";
		int len = 0;

		if (commReqDt.length() >= 8) {
			commReqDt = commReqDt.substring(0, 8);
		}

		//완료거나 오류인데 10일 지난 건인경우 마스킹처리
		if ("07".equals(applFormSt) || diffDaysToday(commReqDt) > maskDayCnt) {
			if (custNm == null || "".equals(custNm)) {
				return "";
			} else {
				len = custNm.length();
				if (len == 1) {
					return custNm;
				} else if (len == 2) {
					return custNm.substring(0,1) + '*';
				} else {
					for (int i=1; i<len-1; i++) {
						result = result + '*';
					}
					return custNm.substring(0,1) + result + custNm.substring(len-1);
				}
			}
		} else {
			return custNm;
		}
	}

	//서비스번호 마스킹처리
	public static String maskSvcNum(final String phonNum, final String applFormSt, String commReqDt) {
		String phon_num = "";
		String mov_1 = "";
		String mov_2 = "";
		String mov_3 = "";

		if ("".equals(phonNum) || phonNum == null) {
			return "";
		}

		if (commReqDt.length() >= 8) {
			commReqDt = commReqDt.substring(0, 8);
		}

		if (phonNum.length() == 12) { // ex>000206764832
			mov_1 = phonNum.substring(0, 4);
			mov_2 = phonNum.substring(4, 8);
			mov_3 = phonNum.substring(8, 12);

			// 국번이 4자리로 들어오므로 최초자릿수를 자른 후 세팅한다.
			if ("000".equals(phonNum.substring(0, 3))) {
				mov_1 = mov_1.substring(2, 4);
			} else {
				mov_1 = mov_1.substring(1, 4);
			}

			// 두번째 번호가 0으로 시작 될 때는 버린다.
			if (mov_2.startsWith("0")) {
				mov_2 = mov_2.substring(1);
			}
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 11) {
			mov_1 = phonNum.substring(0, 3);
			mov_2 = "";
			mov_3 = "";

			if ("00".equals(phonNum.substring(0, 2))) {
				mov_1 = phonNum.substring(1, 3);
			} else {
				mov_1 = phonNum.substring(0, 3);
			}

			if (phonNum.substring(3).startsWith("0")) {
				mov_2 = phonNum.substring(4, 7);
			} else {
				mov_2 = phonNum.substring(3, 7);
			}
			mov_3 = phonNum.substring(7);
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 10) {
			if ("02".equals(phonNum.substring(0, 2))) {
				mov_1 = phonNum.substring(0, 2);
				mov_2 = phonNum.substring(2, 6);
				mov_3 = phonNum.substring(6);
			} else {
				mov_1 = phonNum.substring(0, 3);
				mov_2 = phonNum.substring(3, 6);
				mov_3 = phonNum.substring(6);
			}
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 9) {
			mov_1 = phonNum.substring(0, 2);
			mov_2 = phonNum.substring(2, 5);
			mov_3 = phonNum.substring(5);
			phon_num = mov_1 + "-" + mov_2 + "-" + mov_3;
		} else if (phonNum.length() == 8) {
			mov_1 = phonNum.substring(0, 4);
			mov_2 = phonNum.substring(4, 8);
			phon_num = mov_1 + "-" + mov_2;
		} else {
			phon_num = phonNum;
		}

		if ("07".equals(applFormSt) || diffDaysToday(commReqDt) > maskDayCnt) {
			if (!"".equals(mov_2) && mov_2.length() > 2) {
				mov_2 = mov_2.substring(0,mov_2.length()-2) + "**";
			}
			if (!"".equals(mov_3) && mov_3.length() > 3) {
				mov_3 = mov_3.substring(0,mov_3.length()-3) + "***";
			}

			if (!"".equals(mov_2) || !"".equals(mov_3)) {
				phon_num = mov_1 + '-' + mov_2 + '-' + mov_3;
			}
		}

		return phon_num;
	}

	//생년월일 마스킹
	public static String maskBirth(final String birth) {
		if (birth == null || "".equals(birth)) {
			return "";
		} else if (birth.length() >= 7) {
			return birth.substring(0,6) + "-" + birth.substring(6,7) + "******";
		} else if (birth.length() == 6) {
			return birth.substring(0,6) + "-" + "*******";
		} else {
			return birth;
		}
	}

	public static String maskCustInfo(String value, final String applFormSt, String commReqDt, final String infoCl) {

		int len = 0;
		int baseLen = 0;
		String result = "";

		if (value == null || "".equals(value)) {
			value = "";
		}
		len = value.length();

		if (commReqDt.length() >= 8) {
			commReqDt = commReqDt.substring(0,8);
		}

		if ("email".equals(infoCl) || "addr".equals(infoCl)){
			baseLen = 4;
		} else if ("zip".equals(infoCl)) {
			baseLen = 3;
		}

		if ("07".equals(applFormSt) || diffDaysToday(commReqDt) > maskDayCnt) {
			if (value == null || "".equals(value)) {
				result = value;
			} else if (len > baseLen) {
				result = value.substring(0, baseLen);
				for (int i=baseLen; i<len; i++) {
					result = result + "*";
				}
			} else {
				result = value;
			}
		} else {
			result = value;
		}

		return result;
	}
}
