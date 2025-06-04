package com.ilmare.carbonbank.cmn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

/**
* <br> 업무명 : WCP공통
* <br> 설   명 : 파일 관련 UTIL
* <br> 작성일 : 2009. 01. 01
* <br> 작성자 : 김병화
* <br> 수정일 : 2010. 07. 20
* <br> 수정자 : 김병화
*/
@Component
public class FileUtil {
	
	//private static String saveRootForDev = ""; // 운영일 경우.
	//private static String saveRootForDev = "C:\\sts_work\\ilmareMember\\ilmareMember\\src\\main\\resources\\static\\upload\\"; // 로컬일 경우
    
    @Value("${spring.saveRootForDev:defaultValue}")
    private String saveRootForDev;	
	
    /*
	private static HashMap<String, String> hsImgPath = new HashMap<String, String>() {{
		put("StoreBis"      , saveRootForDev+"store_bis/");    //#가맹점 사업자등록증
		put("Store"         , saveRootForDev+"uploadFiles/store/");    //#가맹점 매장
		put("EnvNews"       , saveRootForDev+"uploadFiles/env_news/");    //#환경뉴스
		put("MunicipalNews" , saveRootForDev+"uploadFiles/municipal_news/");    // #시정뉴스
		put("VideoNews"     , saveRootForDev+"uploadFiles/video_news/");    //#영상뉴스
		put("HotNews"       , saveRootForDev+"uploadFiles/hot_news/");    //#기관 핫뉴스
		put("Event"         , saveRootForDev+"uploadFiles/event/");    //#이벤트
		put("Temp"          , saveRootForDev+"temp/");    //#업로드된 파일의 임시 저장 공간
		put("pcTemp"        , saveRootForDev);    //PC 테스트용(분인 PC에 맞는 경로를 설정 해야 함)
       }};
   */

    private Map<String, String> hsImgPath;

    @PostConstruct
    private void init() {
    	 hsImgPath = new HashMap<>();
         hsImgPath.put("StoreBis"      , saveRootForDev+"store_bis/");    //#가맹점 사업자등록증
         hsImgPath.put("Store"         , saveRootForDev+"uploadFiles/store/");    //#가맹점 매장
         hsImgPath.put("EnvNews"       , saveRootForDev+"uploadFiles/env_news/");    //#환경뉴스
         hsImgPath.put("MunicipalNews" , saveRootForDev+"uploadFiles/municipal_news/");    // #시정뉴스
         hsImgPath.put("VideoNews"     , saveRootForDev+"uploadFiles/video_news/");    //#영상뉴스
         hsImgPath.put("HotNews"       , saveRootForDev+"uploadFiles/hot_news/");    //#기관 핫뉴스
         hsImgPath.put("Event"         , saveRootForDev+"uploadFiles/event/");    //#이벤트
         hsImgPath.put("Temp"          , saveRootForDev+"temp/");    //#업로드된 파일의 임시 저장 공간
         hsImgPath.put("pcTemp"        , saveRootForDev);    //PC 테스트용(분인 PC에 맞는 경로를 설정 해야 함)
    	          
    }

    // 안쓰는 듯?       
   	public static String imgServerBasePath = "/app/data/upload/";    //서버 저장 기본경로      예) /app/data/upload/uploadFiles/env_news/20250604/파일명
   	public static String imgUriBasePath = "/img/";					//db에 저장할떄 기본 경로   예) /img/uploadFiles/env_news/20250604/파일명
    
	private static final String DEFAULT_ENCODING = "euc-kr";
	
    /**
     * Logger for this class
     */

//	private FileUtil () {
//		
//	}
	
	//public static String getSaveFilePath(String upcd, String currDt) throws IOException {
	public String getSaveFilePath(String upcd, String currDt) {		
		return hsImgPath.get(upcd) + currDt;
	}
	
	/**
	 * 소스파일을 새로운 파일로 복사한다.<br>
	 * 만약 대상 디렉토리가 존재하지 않으면 디렉토리를
	 * 생성하고, 소스파일을 복사한다.<br>
	 * 만약 대상파일이 존재하면 덮어쓴다.
	 * 
	 * @param srcFile, destFile
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		FileUtils.copyFile(srcFile, destFile);
	}
	
	/**
	 * 소스파일을 대상 디렉토리로 복사한다.<br>
	 * 만약, 대상 디렉토리가 존재하지 않으면 디렉토리를 
	 * 생성하고, 소스파일을 복사한다.<br>
	 * 만약 대상파일이 존재하면 덮어쓴다.
	 * 
	 * @param srcFile, destDir
	 * @throws IOException
	 */
	public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
		FileUtils.copyFileToDirectory(srcFile, destDir);
	}
	
	/**
	 * 소스 URL의 내용을 대상 파일에 저장한다.<br>
	 * 만약, 대상 디렉토리가 존재하지 않으면 디렉토리를 
	 * 생성하고, 소스파일을 복사한다.<br>
	 * 만약 대상파일이 존재하면 덮어쓴다.
	 * 
	 * @param source, destination
	 * @throws IOException
	 */
	public static void copyURLToFile(URL source, File destination) throws IOException {
		FileUtils.copyURLToFile(source, destination);
	}
	
	/**
	 * 디렉토리를 생성한다.
	 * 
	 * @param path
	 * @return 디렉토리 생성여부
	 */
	public static boolean createDirectory(String path) {
		File dir = new File(path);
		return createDirectory(dir);
	}
	
	/**
	 * 디렉토리를 생성한다.
	 *  
	 * @param dir
	 * @return
	 */
	public static boolean createDirectory(File dir) {
		if ( dir.exists() && dir.isDirectory() ) return true;
		if ( dir.exists() && !dir.isDirectory() ) return false;
		return dir.mkdirs();
	}
	
	/**
	 * 대상디렉토리를 재귀적으로 삭제한다.
	 *  
	 * @param directory
	 * @throws IOException
	 */
	public static void deleteDirectory(File directory) throws IOException {
		FileUtils.deleteDirectory(directory);
	}
	
	/**
	 * 대상파일을 삭제한다. 만약 대상이 디렉토리인 경우, 예외를 발생시킨다.
	 *  
	 * @param file
	 * @throws IOException
	 */
	public static void deleteFile(File file) throws IOException {
		if ( file.isDirectory() ) {
			throw new IllegalArgumentException("Destination '"+file.getAbsolutePath()+"' is a directory");
		}
		FileUtils.forceDelete(file);
	}
	
	/**
	 * 소스파일을 대상파일로 이동한다.
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void moveFile(File srcFile, File destFile) throws IOException {
		File destDir = destFile.getParentFile();
		
		if ( !destDir.exists() ) {
			createDirectory( destDir );
		}
		
		if ( srcFile.renameTo( destFile ) ) {
			return;
		}

		copyFileToDirectory(srcFile, destDir);
		deleteFile( srcFile );
	}
	
	/**
	 * 소스파일을 대상디렉토리로 이동한다.
	 * 
	 * @param srcFile
	 * @param destDir
	 * @throws IOException
	 */
	public static void moveFileToDirectory(File srcFile, File destDir) throws IOException {
		File destFile = new File(destDir, srcFile.getName());
		
		if ( !destDir.exists() ) {
			createDirectory( destDir );
		}
		
		if ( srcFile.renameTo( destFile ) ) {
			return;
		}

		copyFileToDirectory(srcFile, destDir);
		deleteFile( srcFile );
	}
	
	/**
	 * 대상파일을 삭제한다. 만약 대상이 디렉토리인 경우, 해당 디펙토리를 재귀적으로 삭제한다.
	 *  
	 * @param file
	 * @throws IOException
	 */
	public static void forceDelete(File file) throws IOException {
		FileUtils.forceDelete(file);
	}
	
	/**
	 * 대상파일의 FileInputStream을 오픈한다. 만약 대상파일이 존재하지 않거나 
	 * 디렉토리인 경우, 예외를 발생시킨다.
	 *  
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		return new FileInputStream(file);
	}
	
	/**
	 * 파일의 내용을 byte array로 읽는다. 읽혀진 파일은 close된다.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToByteArray(File file) throws IOException {
		return FileUtils.readFileToByteArray(file);
	}

	/**
	 * 파일의 내용을 "EUR-KR" 인코딩방식으로 문자열로 읽는다. 읽혀진 파일은 close된다.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(File file) throws IOException {
		return FileUtils.readFileToString(file, DEFAULT_ENCODING);
	}
	
	/**
	 * 파일의 내용을 주어진 인코딩방식으로 문자열로 읽는다. 읽혀진 파일은 close된다.
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(File file, String encoding) throws IOException {
		return FileUtils.readFileToString(file, encoding);
	}
	
	/**
	 * 파일의 내용을 "EUC-KR" 인코딩방식으로 문자열의 List로 읽는다. 읽혀진 파일은 close된다.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List readLines(File file) throws IOException {
		return FileUtils.readLines(file, DEFAULT_ENCODING);
	}

	/**
	 * 파일의 내용을 주어진 인코딩방식으로 문자열의 List로 읽는다. 읽혀진 파일은 close된다.
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static List readLines(File file, String encoding) throws IOException {
		return FileUtils.readLines(file, encoding);
	}
	
	/**
	 * Unix의 'touch' 와 같은 기능을 한다. 만약 파일이 존재하지 않으면, 0 사이즈의 파일을 생성한다.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void touch(java.io.File file) throws IOException {
		FileUtils.touch(file);
	}
	
	/**
	 * byte array의 내용을 대상파일에 저장한다.<br>
	 * 만약 파일이 존재하지 않으면, 새파일을 생성하여 저장한다.
	 * 
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
		FileUtils.writeByteArrayToFile(file, data);
	}
	
	/**
	 * 대상파일에 "EUC-KR" 인코딩 방식으로 Collection의 내용을 저장한다. 
	 * 이때 Collection의 내용은 각 요소의 toString()으로 받은 문자열에 new line을 더하여 저장한다.
	 * 
	 * <pre>
	 * ex) ["AAAA", "BBBB"]
	 * 저장된 파일내용
	 * AAAA
	 * BBBB
	 * </pre>
	 * 
	 * @param file
	 * @param lines
	 * @throws IOException
	 */
	public static void writeLines(java.io.File file, Collection lines) throws IOException {
		FileUtils.writeLines(file, DEFAULT_ENCODING, lines);
	}
	
	/**
	 * 대상파일에 "EUC-KR" 인코딩 방식으로 Collection의 내용을 저장한다. 
	 * 이때 Collection의 내용은 각 요소의 toString()으로 받은 문자열에 lineEnding을 더하여 저장한다.
	 * 
	 * <pre>
	 * ex) ["AAAA", "BBBB"], "|"(lineEnding)
	 * 저장된 파일 내용
	 * AAAA|BBBB|
	 * </pre>
	 * 
	 * @param file
	 * @param lines
	 * @param lineEnding
	 * @throws IOException
	 */
	public static void writeLines(java.io.File file, Collection lines, String lineEnding) throws IOException {
		FileUtils.writeLines(file, DEFAULT_ENCODING, lines, lineEnding);
	}
	
	/**
	 * 대상파일에 주어진 인코딩 방식으로 Collection의 내용을 저장한다. 
	 * 이때 Collection의 내용은 각 요소의 toString()으로 받은 문자열에 new line을 더하여 저장한다.
	 * 
	 * <pre>
	 * ex) ["AAAA", "BBBB"]
	 * 저장된 파일내용
	 * AAAA
	 * BBBB
	 * </pre>
	 * 
	 * @param file
	 * @param encoding
	 * @param lines
	 * @throws IOException
	 */
	public static void writeLines(java.io.File file, String encoding, Collection lines) throws IOException {
		FileUtils.writeLines(file, encoding, lines);
	}
	
	/**
	 * 대상파일에 주어진 인코딩 방식으로 Collection의 내용을 저장한다. 
	 * 이때 Collection의 내용은 각 요소의 toString()으로 받은 문자열에 lineEnding을 더하여 저장한다.
	 * 
	 * <pre>
	 * ex) ["AAAA", "BBBB"], "|"(lineEnding)
	 * 저장된 파일 내용
	 * AAAA|BBBB|
	 * </pre>
	 * 
	 * @param file
	 * @param encoding
	 * @param lines
	 * @param lineEnding
	 * @throws IOException
	 */
	public static void writeLines(java.io.File file, String encoding, Collection lines, String lineEnding) throws IOException {
		FileUtils.writeLines(file, encoding, lines, lineEnding);
	}
	
	/**
	 * 파일에 "EUC-KR" 인코딩방식으로 문자열을 저장한다.
	 * 
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String data) throws IOException {
		FileUtils.writeStringToFile(file, data, DEFAULT_ENCODING);
	}

	/**
	 * 파일에 주어진 인코딩방식으로 문자열을 저장한다.
	 * 
	 * @param file
	 * @param data
	 * @param encoding
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String data, String encoding) throws IOException {
		FileUtils.writeStringToFile(file, data, encoding);
	}
	
    /**
     * 해당 경로에 빈 파일을 생성하여 반환
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File newFile( String filePath ) throws IOException
    {
        File file = createFile( filePath );
        file.createNewFile();

        return file;
    }


    /**
     * 파일 또는 디렉토리를 삭제함
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static boolean remove( String filePath ) throws IOException
    {
        return removeFile( new File( filePath ) );
    }


    /**
     *
     * 파일 또는 디렉토리 제거. exception raise 안 함.
     *
     * @param filePaht
     * @return
     */
    public static boolean removeQuietly( String filePath )
    {
        boolean result = false;

        try {
            result = remove( filePath );
        }
        catch ( IOException ie ) {
            //LOG.warn( "file remove failed", ie );
        }

        return result;
    }


    /**
     * 해당 파일이 존재하는지의 여부를 반환
     *
     * @param filePath
     * @return
     */
    public static boolean exists( String filePath )
    {
        return new File( filePath ).exists();
    }


    /**
     * 특정 경로에 File 객체를 생성하여 반환
     *
     * @param filePath
     * @return
     */
    public static File createFile( String filePath )
    {
        File file = new File( filePath );
        File parentFile = file.getParentFile();

        if ( parentFile != null && !parentFile.exists() ) {
            parentFile.mkdirs();
        }

        return file;
    }


    /**
     * 단일/다중 디렉토리 구조를 생성
     *
     * @param dirPath
     * @return
     */
    public static boolean mkDirs( String dirPath )
    {
        return new File( dirPath ).mkdirs();
    }


    /**
     * 파일 객체를 넘겨 받아 해당 파일 또는 디렉토리를 제거함
     *
     * @param file
     * @return
     */
    public static boolean removeFile( File file )
    {
        boolean result = false;

        if ( file.exists() ) {
            if ( file.isDirectory() ) {
                result = removeDirs( file );
            }
            else {
                result = file.delete();
            }
        }

        return result;
    }


    /**
     * 디렉토리일 경우 하위 디렉토리 및 파일을 포함하여 삭제 함
     *
     * @param file
     * @return
     */
    private static boolean removeDirs( File file )
    {
        boolean result = false;

        if ( file.exists() && file.isDirectory() ) {
            cleanDirs( file );
            result = file.delete();
        }

        return result;
    }


    /**
     * 파일 객체 형태의 디렉토리의 하위 디렉토리와 파일들을 한 번에 제거 함
     *
     * @param file
     */
    public static boolean cleanDirs( File file )
    {
        boolean result = false;
        List fileList = Arrays.asList( file.listFiles() );

        if ( fileList != null && fileList.size() > 0 ) {
            for ( Iterator it = fileList.iterator(); it.hasNext(); ) {
                File listFile = (File) it.next();
                removeFile( listFile );
            }

            result = true;
        }

        return result;
    }


    /**
     * Reader를 닫는다.
     * 
     * @param reader
     */
    public static void close( Reader reader )
    {
        try {
            reader.close();
        }
        catch ( Exception e ) {
        }
    }


    /**
     * Writer를 닫는다.
     * 
     * @param writer
     */
    public static void close( Writer writer )
    {
        try {
            writer.close();
        }
        catch ( Exception e ) {
        }
    }


    /**
     * 스트림을 닫는다.
     * @param is
     */
    public static void close( InputStream is )
    {
        try {
            is.close();
        }
        catch ( Exception e ) {
        }
    }


    /**
     * 스트림을 닫는다.
     * 
     * @param os
     */
    public static void close( OutputStream os )
    {
        try {
            os.close();
        }
        catch ( Exception e ) {
        }
    }

}
