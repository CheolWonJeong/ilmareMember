/*
 * UploadConfig.java    2009. 01. 01
 * 
 * Copyright(c) 2008 by SK Telecom co.ltd all right reserved.
 */
package com.ilmare.carbonbank.cmn.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
/**
* <br> 업무명 : WCP공통
* <br> 설   명 : UploadConfig 관련 UTIL
* <br> 작성일 : 2009. 01. 01
* <br> 작성자 : 김병화
* <br> 수정일 : 2010. 07. 20
* <br> 수정자 : 김병화
*/
public class UploadConfig
{
    public static final String CONFIG_RESOURCE_NAME = "config/fileupload.properties";
    
    //   업로드허용확장자(이미지업로드)
    public static String CONST_FILE_SERVER_IP = "";
    //   업로드허용확장자(이미지업로드)
    public static String CONST_FILE_SERVER_PORT = "";
 
    //   업로드허용확장자(이미지업로드)
    public static String CONST_FILE_UPLOAD_IMAGE_EXT = "jpg|gif|bmp";
    //   업로드허용확장자(음악업로드)
    public static String CONST_FILE_UPLOAD_MUSIC_EXT = "mp3";
    //   업로드허용확장자(비디오업로드)
    public static String CONST_FILE_UPLOAD_MOVIE_EXT = "avi";
    //   업로드허용확장자(일반파일업로드)
    public static String CONST_FILE_UPLOAD_FILE_EXT = "asf|wmv|pdf|hwp|doc|ppt|txt|xls|swf|zip|ai|psd|eps|jpg|gif|bmp|swf";
 /*
    public static String FILE_TEMP_ROOT_PATH = "/app/zhtuploads/tempupload/";
    public static String FILE_LINK_BIZ_ROOT_PATH = "/app/zhtuploads/webupload/";
    public static String FILE_DOWNLOAD_BIZ_ROOT_PATH = "/app/zhtuploads/upload/";
 */
    public static String FILE_TEMP_ROOT_PATH = "";
    public static String FILE_LINK_BIZ_ROOT_PATH = "";
    public static String FILE_DOWNLOAD_BIZ_ROOT_PATH = "";

    public static int CONST_IMG_UPLOAD_MAXBYTES = 0; //String.valueOf(5*1024*1024);
    public static int CONST_VIDIO_UPLOAD_MAXBYTES  = 0; //String.valueOf(500*1024*1024);
    public static int CONST_FILE_UPLOAD_MAXBYTES = 0; //String.valueOf(50*1024*1024);
    public static String CONST_FILE_UPLOAD_ENCTYPE = "utf-8";

    static
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource(CONFIG_RESOURCE_NAME);
        //URL url = Thread.currentThread().getContextClassLoader().getResource("C:/EclipseForWcp/workspace/WcpCmn/src/main/resources/config/fileupload.properties");
        if ( url == null ) {
            throw new IllegalArgumentException("config file not found in classpath : " + CONFIG_RESOURCE_NAME);
        }
        try {
            InputStream in = url.openStream();
            Properties prop = new Properties();
            prop.load( new BufferedInputStream( in ) );
            CONST_FILE_SERVER_IP = prop.getProperty("file.server.ip");
            CONST_FILE_SERVER_PORT = prop.getProperty("file.server.port");
            FILE_TEMP_ROOT_PATH = prop.getProperty("upload.temp.root");
            FILE_LINK_BIZ_ROOT_PATH = prop.getProperty("upload.biz.root");
            FILE_DOWNLOAD_BIZ_ROOT_PATH = prop.getProperty("download.biz.root");
            in.close();
        } catch ( IOException e ) {
            throw new RuntimeException(e.getMessage(), e);
        }

        CONST_IMG_UPLOAD_MAXBYTES = 5*1024*1024;
        CONST_VIDIO_UPLOAD_MAXBYTES = 500*1024*1024;
        CONST_FILE_UPLOAD_MAXBYTES = 50*1024*1024;

    }
    /**
     * 파일서버IP를 반환한다.
     * @param 
     * @return CONST_FILE_SERVER_IP 파일서버IP
     */
	public static String getServerIp() {
		return CONST_FILE_SERVER_IP;
	}
    /**
     * 파일서버Port를 반환한다.
     * @param 
     * @return CONST_FILE_SERVER_PORT 파일서버Port
     */
	public static String getServerPort() {
		return CONST_FILE_SERVER_PORT;
	}
}

