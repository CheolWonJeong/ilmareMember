package com.ilmare.carbonbank.cmn.controller;

import org.springframework.stereotype.Component;


@Component
public class ConfigConstants {

    public static String SERVICE_DOMAIN = "http://m.carbonbank.re.kr";
	public static final String  lgnUrl = "/adm/login.do";

	//페이지간련
	public static final int pageSize = 20;    //페이지당 row 건수
	
    public static String SERVICE_PORT = "9000";
    public static String IMG_URL = "/upload/";

    public static String RESPONSE_RESULT_KEY = "_RES_";
    public static String REQUEST_KEY = "_REQ_";

    public static String SERVICE_NAME = "_common_serviceName";
    public static String REQ_CONVERTER = "_common_reqConverter";
    public static String COMPONENT_NAME = "_common_componentName";
    public static String VIEW_PAGE = "_common_view";

    public static String SERVICE_CONFIG = "service-config";
    public static String LANG_KOR = "KOR";
    public static String LANG_CHN = "CHN";
    public static String LOGIN_PAGE = "/adm/login/admLogin.jsp?returnURL=";
    public static String Common_Alert = "/adm/common/commAlert.jsp";
    public static String Common_Save = "/adm/common/commSave.jsp";
    public static String Common_Pote_Save = "/adm/common/commPotoSave.jsp";

}
