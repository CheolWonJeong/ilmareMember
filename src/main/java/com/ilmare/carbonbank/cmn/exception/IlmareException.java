package com.ilmare.carbonbank.cmn.exception;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import lombok.extern.slf4j.Slf4j;


/**
 * WCP에서 사용하는 Exception을 정의해 두는 부분
 *
 * @author achess, Lee
 *
 */
@Slf4j
public class IlmareException extends Exception
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5775564396637630822L;

    private String errorCode;
    private String systemMessage;
    private String userMessage;

    private HashMap<String, String> systemMessageMap = new HashMap();
    private HashMap<String, String> userMessageMap = new HashMap();
    
    public IlmareException(Exception cause) {
        super(cause);
        Throwable th = cause.getCause();
        if ( th == null ) th = cause;

        if (th instanceof ClassNotFoundException) {
            this.errorCode = "CS0001";
        } else if(th instanceof CloneNotSupportedException) {
            this.errorCode = "CS0002";
        } else if(th instanceof DataFormatException) {
            this.errorCode = "CS0003";
        } else if(th instanceof IllegalAccessException) {
            this.errorCode = "CS0004";
        } else if(th instanceof InstantiationException) {
            this.errorCode = "CS0005";
        } else if(th instanceof IOException) {
            this.errorCode = "CS0006";
        } else if(th instanceof NoSuchFieldException) {
            this.errorCode = "CS0007";
        } else if(th instanceof NoSuchMethodException) {
            this.errorCode = "CS0008";
        } else if(th instanceof SQLException) {
            this.errorCode = "CS0009";
        } else if(th instanceof BufferOverflowException) {
            this.errorCode = "CS0010";
        } else if(th instanceof ClassCastException) {
            this.errorCode = "CS0011";
        } else if(th instanceof ConcurrentModificationException) {
            this.errorCode = "CS0012";
        } else if(th instanceof IllegalArgumentException) {
            this.errorCode = "CS0013";
        } else if(th instanceof IllegalStateException) {
            this.errorCode = "CS0014";
        } else if(th instanceof IndexOutOfBoundsException) {
            this.errorCode = "CS0015";
        } else if(th instanceof NoSuchElementException) {
            this.errorCode = "CS0016";
        } else if(th instanceof NullPointerException) {
            this.errorCode = "CS0017";
        } else if(th instanceof UnsupportedOperationException) {
            this.errorCode = "CS0018";
        } else if(th instanceof RuntimeException) {
            this.errorCode = "CS0019";
        } else {
            this.errorCode = "CS0020";
        }

        this.userMessage = "시스템이 정상적으로 작동하지 않아 서비스를 이용 하실 수 없습니다";

        StringBuffer temp = new StringBuffer();
        temp.append(cause.toString() + " (StackTrace : "+cause.getStackTrace().length + ")");
        for (int i = 0 ;i<cause.getStackTrace().length;i++) {
              temp.append("\n"+cause.getStackTrace()[i].toString());
        }
        this.systemMessage = temp.toString();

        log.error("==>> IlmareException(Exception cause) Occured! <<==\n[ userMessage : "
                        + getUserMessage() + " ]\n[ systemMessage : " + getSystemMessage() + " ]");
    }

    public IlmareException(String errorCode) {
        this.errorCode = errorCode;
        try {
            this.systemMessage = systemMessageMap.get( errorCode );
            this.userMessage = userMessageMap.get( errorCode );
        }catch(Exception e){
            this.systemMessage = "IlmareException 내부오류 : " + e.toString();
            this.userMessage = "시스템 오류입니다.<br>사이트관리자에게 문의하세요";
        }
        //this(errorCode, "", cause);
        log.error("==>> IlmareException(String errorCode) Occured! <<==\n[ errorCode : " + getErrorCode() 
                + " ]\n[ userMessage : " + getUserMessage() + " ]\n[ systemMessage : " + getSystemMessage() + " ]");
    }

    public IlmareException(String errorCode, String userMessage) {
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        log.error("==>> IlmareException(String errorCode, String userMessage) Occured! <<==\n[ errorCode : "
            + getErrorCode() + " ]\n[ userMessage : " + getUserMessage() + " ]");
    }

    public IlmareException(String errorCode, String[] param) {
        this.errorCode = errorCode;
        try {
            this.systemMessage = systemMessageMap.get( errorCode) +"   "+ param ;
            this.userMessage = userMessageMap.get( errorCode) +"   "+ param ;
        }catch(Exception e){
            this.systemMessage = "IlmareException 내부오류 : " + e.toString();
            this.userMessage = "시스템 오류입니다.";
        }
        log.error("==>> IlmareException(String errorCode, String[] param) Occured! <<==\n[ errorCode : " + getErrorCode() 
            + " ]\n[ userMessage : " + getUserMessage() + " ]\n[ systemMessage : " + getSystemMessage() + " ]");
    }

    public IlmareException(String errorCode, Exception cause) {
        super(cause);
        
        this.errorCode = errorCode;
        try {
            this.systemMessage = systemMessageMap.get( errorCode );
            this.userMessage = userMessageMap.get( errorCode );
        }catch(Exception e){
            this.systemMessage = "IlmareException 내부오류 : " + e.toString();
            this.userMessage = "시스템 오류입니다.";
        }
        //this(errorCode, "", cause);
        log.error("==>> IlmareException(String errorCode, Exception cause) Occured! <<==\n[ errorCode : " + getErrorCode() 
            + " ]\n[ userMessage : " + getUserMessage() + " ]\n[ systemMessage : " + getSystemMessage() + " ]");
    }

    public IlmareException(String errorCode, Exception cause, String[] param) {
        super(cause);
        
        this.errorCode = errorCode;
        try {
            this.systemMessage = systemMessageMap.get( errorCode );
            this.userMessage = userMessageMap.get( errorCode ) +"  "+ param;
        }catch(Exception e){
            this.systemMessage = "IlmareException 내부오류 : " + e.toString();
            this.userMessage = "시스템 오류입니다.";
        }
        log.error("==>> IlmareException(String errorCode, Exception cause, String[] param) Occured! <<==\n[ errorCode : " + getErrorCode() 
            + " ]\n[ userMessage : " + getUserMessage() + " ]\n[ systemMessage : " + getSystemMessage() + " ]");
    }

    public IlmareException(String errorCode, Exception cause, String param){
        String[] arryParam = new String[1];
        arryParam[0] = param;
        new IlmareException(errorCode, cause, arryParam);
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }
    /**
     * @return the System Message
     */
    public String getSystemMessage() {
        return systemMessage;
    }
    /**
     * @return the errorMessage
     */
    public String getUserMessage() {
        return userMessage;
    }
}
