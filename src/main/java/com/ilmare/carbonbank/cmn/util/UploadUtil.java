/*
 * UploadUtil.java    2009. 01. 01
 * 
 * Copyright(c) 2008 by SK Telecom co.ltd all right reserved.
 */
package com.ilmare.carbonbank.cmn.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import io.micrometer.common.util.StringUtils;


/**
* <br> 업무명 : WCP공통
* <br> 설   명 : Upload 관련 UTIL
* <br> 작성일 : 2009. 01. 01
* <br> 작성자 : 김병화
* <br> 수정일 : 2010. 07. 20
* <br> 수정자 : 김병화
*/
public class UploadUtil {

	//
    private static String DEFAULT_USERID = "anonymous";
    
    public static final String BIZ_DIR_DEFAULT = "default";
    private static final HashMap hsBizDir = new HashMap();
    
    public static final String BIZ_DIR_ADVT     = "advt";           // 광고
    public static final String BIZ_DIR_CLTR     = "cltr";           // 문화
    public static final String BIZ_DIR_HOME     = "home";           // 홈ㄴ
    public static final String BIZ_DIR_KOR      = "korea";    // 오락시상(한류)
    public static final String BIZ_DIR_MUSC     = "musc";   // 뮤직
    public static final String BIZ_DIR_NEWS     = "news";       // 뉴스
    public static final String BIZ_DIR_PTVD     = "ptvd";       // 포토%동영상
    public static final String BIZ_DIR_RDIO     = "rdio";        // 라디오
    public static final String BIZ_DIR_TRVL     = "trvl";           // 여행
    public static final String BIZ_DIR_INFO     = "info";       //문서에 들어가는 이미지
 
    public static final int LINK_TYPE = 0;      // 타입 : 바로열기
    public static final int DOWNLOAD_TYPE = 1;  // 타입 : 다운로드
    
    public static final int width640 	= 640; 	// big image size
    public static final int height400 	= 400; 	// big image size
    public static final int width180 	= 180; 	// thumbnail image size
    public static final int height120 	= 120; 	// thumbnail image size

    /**
     * 생성자
     */
	private UploadUtil() {}

	/*[start] 사용자 제공 */
    /**
     * <pre>
     * userId에 맞는 해당 디렉토리로 이동한다.
     * </pre>
     *
     * @param userId
     * @param bizDir
     * @param values
     * @return
     */
	public static String[][] moveToLinkDir(String userId, String bizDir, String[] values) throws IOException {
		if ( StringUtils.isBlank( bizDir ) ) {
			return moveToBizDir(0, userId, BIZ_DIR_DEFAULT, values);
		}

		return moveToBizDir(0, userId, bizDir, values);
	}
    /**
     * <pre>
     * 해당 디렉토리로 이동한다.
     * </pre>
     *
     * @param bizDir
     * @param values
     * @return
     */
	public static String[][] moveToLinkDir(String bizDir, String[] values) throws IOException {
		if ( StringUtils.isBlank( bizDir ) ) {
			return moveToBizDir(0, "", BIZ_DIR_DEFAULT, values);
		}

		return moveToBizDir(0, "", bizDir, values);
	}
    /**
     * <pre>
     * userId에 맞는 해당 다운로드 디렉토리로 이동한다.
     * </pre>
     *
     * @param userId
     * @param bizDir
     * @param values
     * @return
     */
	public static String[][] moveToDownloadDir(String userId, String bizDir, String[] values) throws IOException {
		if ( StringUtils.isBlank( bizDir ) ) {
			return moveToBizDir(1, userId, BIZ_DIR_DEFAULT, values);
		}

		return moveToBizDir(1, userId, bizDir, values);
	}
    /**
     * <pre>
     * 파일을 해당 디렉토리로 이동한다.
     * </pre>
     *
     * @param srcName
     * @param destName
     * @return
     */
	public static void moveToLinkFile(String srcName, String destName) throws IOException {
		moveToBizFile(0, srcName, destName);
	}
    /**
     * <pre>
     * 해당 디렉토리로 다운로드 파일을 이동한다.
     * </pre>
     *
     * @param srcName
     * @param destName
     * @return
     */
	public static void moveToDownloadFile(String srcName, String destName) throws IOException {
		moveToBizFile(1, srcName, destName);
	}
    /**
     * <pre>
     * 해당 디렉토리로 파일을 카피한다.
     * </pre>
     *
     * @param srcName
     * @param destName
     * @return
     */
	public static void copyLinkFile(String srcName, String destName) throws IOException {
		copyBizFile(0, srcName, destName);
	}
    /**
     * <pre>
     * 해당 디렉토리로 다운로드 파일을 카피한다.
     * </pre>
     *
     * @param srcName
     * @param destName
     * @return
     */
	public static void copyDownloadFile(String srcName, String destName) throws IOException {
		copyBizFile(1, srcName, destName);
	}

	/**
	 * 원본 파일명과 임시파일명을 분리한다.
	 *
	 * <pre>
	 * 인자 : String[] { "원본파일명:임시파일명", "원본파일명2:임시파일명2" };
	 * 결과 : String[][] { {"원본파일명", "임시파일명"} , {"원본파일명2", "임시파일명2"} }
	 *       String[i][0] : 원본파일명이고,
	 *       String[i][1] : 임시파일명
	 * </pre>
	 *
	 * @param values
	 * @return
	 */
	public static String[][] splitFiles(String[] values) {
		String[][] result = new String[values.length][2];

		for(int i = 0; i<values.length; i++) {
			result[i] = StringUtil.splite(values[i],":");
		}

		return result;
	}
    /**
     * <pre>
     * 해당 디렉토리에서 파일을 삭제한다.
     * </pre>
     *
     * @param filepath
     * @return
     */
	public static void deleteFileFromLink(String filepath) throws Exception{
		String fullPath = getBizRoot(0) + filepath;
		if(FileUtil.exists(fullPath)){
			FileUtil.deleteFile(new File(fullPath));
		}
	}
    /**
     * <pre>
     * 해당 디렉토리에서 다운로드 파일을 삭제한다.
     * </pre>
     *
     * @param filepath
     * @return
     */
	public static void deleteFileFromDownload(String filepath) throws Exception{
		String fullPath = getBizRoot(1) + filepath;
		if(FileUtil.exists(fullPath)){
			FileUtil.deleteFile(new File(fullPath));
		}
	}

	/*[end] 사용자 제공 */
    /**
     * <pre>
     * DEFAULT_USERID로 임시 디렉토리를 생성한다.
     * </pre>
     *
     * @param fileName
     * @return
     */
	public static String getTempPath(String fileName) {
		return getTempPath(DEFAULT_USERID, fileName);
	}
    /**
     * <pre>
     * userId로 임시 디렉토리를 생성한다.
     * </pre>
     *
     * @param userId
     * @param fileName
     * @return
     */	
	public static String getTempPath(String userId, String fileName) {
		String path = createTempPath(0, userId, fileName);
		FileUtil.createDirectory(path);
		return path;
	}
    /**
     * <pre>
     * userId로 디렉토리를 생성한다.
     * </pre>
     *
     * @param userId
     * @param fileName
     * @return
     */ 
	public static String getBizPath(String userId, String fileName) {
		UploadConfig.getServerIp();
		return UploadConfig.FILE_LINK_BIZ_ROOT_PATH;
	}
    /**
     * <pre>
     * 해당 디렉토리로 이동한다.
     * </pre>
     *
     * @param type
     * @param bizDir
     * @param fileName
     * @return
     */ 
	public static String moveToBizDir(int type, String bizDir, String fileName) throws IOException {
		return moveToBizDir(type, DEFAULT_USERID, bizDir, fileName);
	}
    /**
     * <pre>
     * userId에 맞는 해당 디렉토리로 이동한다. 
     * </pre>
     *
     * @param type
     * @param userId
     * @param bizDir
     * @param values
     * @return
     */ 
	public static String[][] moveToBizDir(int type, String userId, String bizDir, String[] values) throws IOException {
		String[][] result = new String[values.length][2];

		for(int i = 0; i<values.length; i++) {
			result[i] = StringUtil.splite(values[i],":");
		}

		for(int i = 0; i<result.length; i++) {
			if(type == 0) result[i][1] = UploadUtil.moveToLinkDir(userId, bizDir, result[i][1]);
			else result[i][1] = UploadUtil.moveToDownloadDir(userId, bizDir, result[i][1]);
		}
		return result;
	}

    /**
     * <pre>
     * userId에 맞는 임시 디렉토리로 이동한다. 
     * </pre>
     *
     * @param userId
     * @param bizDir
     * @param tempFilePath
     * @return
     */ 
	public static String moveToLinkDir(String userId, String bizDir, String tempFilePath) throws IOException {
		return moveToBizDir(0, userId, bizDir, tempFilePath);
	}

    /**
     * <pre>
     * userId에 맞는 임시 다운로드 디렉토리로 이동한다. 
     * </pre>
     *
     * @param userId
     * @param bizDir
     * @param tempFilePath
     * @return
     */ 
	public static String moveToDownloadDir(String userId, String bizDir, String tempFilePath) throws IOException {
		return moveToBizDir(1, userId, bizDir, tempFilePath);
	}
    /**
     * <pre>
     * 파일 이름을 변경한다.
     * </pre>
     *
     * @param fullPath
     * @param removeChars
     * @return
     */ 
	public static String renameFile(String fullPath, String removeChars) {
		File orgFile = new File(fullPath);
		File newFile = new File(removeChars(fullPath, removeChars));

		if ( orgFile.equals( newFile ) ) return orgFile.getName();

		orgFile.renameTo( newFile );

		return newFile.getName();
	}
    /**
     * <pre>
     * 파일을 카피한다.
     * </pre>
     *
     * @param type
     * @param srcName
     * @param destName
     * @return
     */ 
	private static void copyBizFile(int type, String srcName, String destName) throws IOException {
		String sourceFilename = getBizRoot(type) + srcName;
		String destFilename = getBizRoot(type) + destName;

		copyFile(sourceFilename, destFilename);
	}
    /**
     * <pre>
     * 파일을 카피한다.
     * </pre>
     *
     * @param sourceFilename
     * @param destFilename
     * @return
     */ 
	private static void copyFile(String sourceFilename, String destFilename) throws IOException {
		File sourceFile = new File(sourceFilename);
		File destFile = new File(destFilename);

		FileUtil.createDirectory(destFile.getParentFile());

		FileUtil.copyFile(sourceFile, destFile);
	}
    /**
     * <pre>
     * 해당 chars를 삭제한다.
     * </pre>
     *
     * @param str
     * @param chars
     * @return
     */
	private static String removeChars(String str, String chars) {
		String[] rt = StringUtil.splite(str, chars);
		StringBuffer sb = new StringBuffer();
		for( int i = 0; i<rt.length; i++ ) {
			sb.append(rt[i]);
		}
		return sb.toString();
	}

    /**
     * <pre>
     * 파일이 올려진후 아래에서 해당경로,파일명을 찾아온다.
     * </pre>
     *
     * @param type
     * @param userId
     * @param bizDir
     * @param tempFilePath
     * @return
     */
	private static String moveToBizDir(int type, String userId, String bizDir, String tempFilePath) throws IOException {
		// 1.파일이 올려진후 아래에서 해당경로,파일명을 찾아온다.
		//String tempPath = createTempAbsolutePath(userId, fileName);
		//String bizPath = createBizAbsolutePath(type, userId, fileName);

	    String replaceFilePath = tempFilePath.replaceAll( "\\.\\./", "" );    // 상위 파일 접근 금지
		String tempPath = getTempRoot() + replaceFilePath;
		String bizHashDir = bizDir + File.separator + getBizHashDir(userId);
		bizHashDir = (type == 0) ? "uploads/"+bizHashDir : bizHashDir;

		// 수정 20071012   내용:"upload/" 추가
		String bizDirPath = getBizRoot(type) +bizHashDir;
		FileUtil.createDirectory(bizDirPath);

		File tempFile = new File(tempPath);
		File destDir = new File(bizDirPath);

		FileUtil.moveFileToDirectory(tempFile, destDir);

		String fileName = replaceFilePath.substring(replaceFilePath.lastIndexOf(File.separator)+1);
		return "/"+bizHashDir + fileName;

	}
    /**
     * <pre>
     * 해당 파일을 이동한다.
     * </pre>
     *
     * @param type
     * @param srcName
     * @param destName
     * @return
     */
	private static void moveToBizFile(int type, String srcName, String destName) throws IOException {
		String sourceFilename = getTempRoot() + srcName;
		String destFilename = getBizRoot(type) + destName;

		moveFile(sourceFilename, destFilename);
	}
    /**
     * <pre>
     * 해당 파일을 이동한다.
     * </pre>
     *
     * @param sourceFilename
     * @param destFilename
     * @return
     */	
	private static void moveFile(String sourceFilename, String destFilename) throws IOException {
		File sourceFile = new File(sourceFilename);
		File destFile = new File(destFilename);

		FileUtil.createDirectory(destFile.getParentFile());

		FileUtil.moveFile(sourceFile, destFile);
	}

	/*[END] Ψ */
    /**
     * <pre>
     * 임시 디렉토리를 생성한다.
     * </pre>
     *
     * @param type
     * @param userId
     * @param fileName
     * @return
     */
	public static String createTempPath(int type, String userId, String fileName) {
		// TODO Auto-generated method stub
		String currDt = DateUtil.getCurrDate();

		String tempPath = getTempRoot()+currDt;

		String hashDir = getTempHashDir(userId);

		return tempPath+hashDir;
	}

    /**
     * <pre>
     * TempRoot Path를 반환한다.
     * </pre>
     *
     * @param 
     * @return
     */
	public static String getTempRoot() {
		return UploadConfig.FILE_TEMP_ROOT_PATH;
	}
    /** 
     * <pre>
     * file download Root Path를 반환한다.
     * </pre>
     *
     * @param type
     * @return
     */	
	public static String getBizRoot(int type) {
		if ( type == 0 ) return UploadConfig.FILE_LINK_BIZ_ROOT_PATH;
		return UploadConfig.FILE_DOWNLOAD_BIZ_ROOT_PATH;
	}

    /**
     * <pre>
     * 객체의 유일한 참조 값 디렉토리 구조로 리턴한다.
     * </pre>
     *
     * @param userId
     * @return
     */
	//	 객체의 유일한 참조 값 디렉토리 구조로 리턴
	public static String getTempHashDir(String userId){
		int hashCode = userId.hashCode();
		hashCode = (hashCode<0) ? -1 * hashCode : hashCode;

		StringBuffer rt = new StringBuffer();
		//rt.append(File.pathSeparator);
		rt.append(DateUtil.getCurrentDateString());
		rt.append(File.separator);
		rt.append(StringUtil.replace(NumberUtil.format(hashCode, "#,##"), ",", File.separator));
		rt.append(File.separator);

		return rt.toString();
	}
    /**
     * <pre>
     * 객체의 유일한 참조 값 디렉토리 구조로 리턴한다.
     * </pre>
     *
     * @param userId
     * @return
     */
	public static String getBizHashDir(String userId){

		String hashSrc = DateUtil.format(DateUtil.getCurrentDateTime(), "yyyyMM");
		hashSrc += StringUtils.isBlank( userId ) ? DEFAULT_USERID : userId;

		int hashCode = hashSrc.hashCode();

		hashCode = (hashCode<0) ? -1 * hashCode : hashCode;

		String hashStr = String.valueOf(hashCode);

		int half = (hashStr.length()+1) / 2;
		StringBuffer rt = new StringBuffer();
		rt.append(hashStr.substring(0 , half));
		rt.append(File.separator);
		rt.append(hashStr.substring(half));
		rt.append(File.separator);

		return rt.toString();
	}

    /**
     * <pre>
     * 객체의 유일한 참조 값 디렉토리 구조로 리턴한다.
     * </pre>
     *
     * @param userId
     * @return
     */
	public static String getBizfilePath(String userId){

		String filePath = userId +"/"+ DateUtil.formatDate(DateUtil.getCurrentDateTime()) +"/";
		return filePath;
	}

    /**
     * <pre>
     * 객체의 유일한 참조 값 디렉토리 구조로 리턴한다.
     * </pre>
     *
     * @param userId
     * @return
     */
	public static String getBizHashfileNm(String userId){

		String hashSrc = DateUtil.formatDateTime( DateUtil.getCurrentDateTime() );

		hashSrc += ((int)(Math.random()*100) +1) + "";
		return hashSrc;
	}


    /**
     * <pre>
     * 이미지 및 비디오의 upload 사이즈를 리턴한다.
     * </pre>
     *
     * @param userId
     * @return
     */
	public static int getUploadSize(String ind){

		if ( "P".equals(ind) ) 
			return UploadConfig.CONST_IMG_UPLOAD_MAXBYTES;
		else if ( "V".equals(ind) )
			return UploadConfig.CONST_VIDIO_UPLOAD_MAXBYTES;
		else
			return UploadConfig.CONST_FILE_UPLOAD_MAXBYTES;
	}


}
