package cn.com.zzwfang.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
/**
 * Files utils
 * @author Zoe
 * @version 1.0.2
 * @since 1.0
 */
public class FileUtils {
	static final int	MAX_BUFFER_SIZE	= 1024;

	/**
	 * 确认目录存在，如果不存在，则创建
	 * @param fileName
	 */
	static public void ensureDirExist(String fileName) {
		File dirFile = new File( fileName.substring( 0, fileName.lastIndexOf( '/' ) ) );
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
	}

	/**
	 * 清除目录内的所有文件夹和文件
	 */
	static public void clearAll(String dirName) {
		File dirFile = new File( dirName );
		if (!dirFile.exists() || !dirFile.isDirectory())
			return;
		String[] chilren = dirFile.list();
		for (String file : chilren) {
			File tempFile = new File( file );
			if (tempFile.isDirectory())
				clearAll( tempFile.toString() );
			else {
				if (tempFile.canWrite())
					tempFile.delete();
			}
		}
	}


	/**
	 * 获取文件字节数组数据
	 * @param filePathName 文件路径
	 * @return
	 */
	static public byte[] getFileByteArray(String filePathName) throws Exception {
		int bytesRead;
		byte[] buffer = new byte[MAX_BUFFER_SIZE];
		File file = new File( filePathName );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream( file );

		//读文件
		bytesRead = fis.read( buffer, 0, MAX_BUFFER_SIZE );
		while (bytesRead > 0) {
			baos.write( buffer, 0, bytesRead );
			bytesRead = fis.read( buffer, 0, MAX_BUFFER_SIZE );
		}

		fis.close();
		return baos.toByteArray();
	}

	//----------------------------------
	// 统计目录大小
	//----------------------------------
	
	/**
	 * 获取目录（包含子目录）或者文件的长度，in bytes,
	 * @param dirOrFile
	 * @return
	 */
	public static long getDirSize(File dirOrFile) {
		if (dirOrFile == null) {
			return 0;
		}
		if (dirOrFile.isFile())
			return dirOrFile.length();
		else if (dirOrFile.isDirectory()) {
			long dirSize = 0;
			File[] files = dirOrFile.listFiles();
			for (File file : files) {
				dirSize += file.length();
				if (file.isDirectory()) {
					dirSize += getDirSize( file );
				}
			}
			return dirSize;
		}
		return 0;
	}
	
	/**
	 *  获取格式化的文件夹或者文件大小(mb或gb或kb)
	 * @return
	 */
	public static String getFormattedDirSize(File dirOrFile){
		double size = getDirSize( dirOrFile );
		double kb = 1024f;
		double mb = 1024 * kb;
		double gb = 1024 * mb;
		String sizeSuffix = "kb";
		double formattedSize = size / kb;
		if(size / mb > 1024){
			formattedSize = size / gb;
			sizeSuffix = "gb";
		}
		else if(size / kb > 1024) {
			formattedSize = size / mb;
			sizeSuffix = "mb";
		}
		DecimalFormat df = new DecimalFormat("0.00");// 以Mb为单位保留两位小数  
	    String filesize = df.format(formattedSize);
		return filesize + sizeSuffix;
	}
	
	
	//----------------------------------
	// 清空目录
	//----------------------------------
	
	/**
	 * 删除目录下的所有文件和子目录
	 * @param dir
	 */
	public static void clearDir(File dir){
		if(!dir.isDirectory() || !dir.exists())
			return;
		File[] files = dir.listFiles();
		for (File file : files) {			
			if (file.isDirectory()) {
				clearDir(file);
			} else if (file.isFile())
				file.delete();
		}
	}

	
	//----------------------------------
	// 拷贝
	//----------------------------------

	/**
	 * 复制单个文件
	 * @param oldPath 旧文件路径
	 * @param newPath 新路径
	 * @param isForceOverride 如果新路径存在同名文件，是否强制覆盖新文件。
	 * @return 1成功, -1失败, 0新路径存在同名文件，跳过。
	 */
	public static int copyFile(String oldPath, String newPath, boolean isForceOverride) {
		int r = 1;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			int len = 0;
			File oldfile = new File( oldPath );
			File newfile = new File( newPath );
			
			// 要复制的文件存在
			if (oldfile.exists()) {
				// 如果新文件不存在, 或存在，并强制覆盖
				if(!newfile.exists() || (newfile.exists() && isForceOverride)){
					fis = new FileInputStream( oldPath );
					fos = new FileOutputStream( newPath );
					byte[] buffer = new byte[1444];
					while ((len = fis.read( buffer )) != -1) {
						fos.write( buffer, 0, len );
					}
					fos.flush();
				} else r = 0;
				
			} else r = -1;
			
		} catch (IOException e) {
			e.printStackTrace();
			r = -1;
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;

	}
	
	


	/**
	 * 拷贝目录
	 * @param oldPath 旧目录
	 * @param newPath 新目录
	 * @param isForceOverride 是否强制覆盖新目录中已经存在的文件, 如果是false，则跳过
	 * @param isCopySubDirectory 是否拷贝子目录
	 */
	public static void copyFolder(String oldPath, String newPath, boolean isForceOverride, boolean isCopySubDirectory ) {

		try {
			(new File( newPath )).mkdirs(); //如果文件夹不存在 则建立新文件夹   
			File f = new File( oldPath );
			String[] files = f.list();
			File temp = null;
			for (int i = 0; i < files.length; i++) {
				if (oldPath.endsWith( File.separator )) {
					temp = new File( oldPath + files[i] );
				}
				else {
					temp = new File( oldPath + File.separator + files[i] );
				}

				// 拷贝文件
				if (temp.isFile()) {
					File newFile = new File(newPath + File.separator + temp.getName());
					if(!isForceOverride && newFile.exists())
						continue;
					
					FileInputStream fis = new FileInputStream( temp );
					FileOutputStream fos = new FileOutputStream( newFile );
					byte[] buffer = new byte[1024 * 5];
					int len;
					while ((len = fis.read( buffer )) != -1) {
						fos.write( buffer, 0, len );
					}
					fos.flush();
					fos.close();
					fis.close();
				}
				// 拷贝子目录
				if (temp.isDirectory() && isCopySubDirectory) {
					copyFolder( oldPath + File.separator + files[i], 
								newPath + File.separator + files[i], 
								isForceOverride, 
								isCopySubDirectory );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**  
     * 复制单个文件  
     * @param sourcePath String 原文件路径 
     * @param distPath String 复制后路径
     * @return   
     */   
   public static void copyFile2(String sourcePath, String distPath) {   
       try {   
           int read = 0;   
           
           File source = new File(sourcePath);   
           if (source.exists()) { 
               InputStream is = new FileInputStream(sourcePath); 
               FileOutputStream fos = new FileOutputStream(distPath);   
               byte[] buffer = new byte[1024];   
               while ( (read = is.read(buffer)) != -1)   
                   fos.write(buffer, 0, read);   
 
               is.close();  
               fos.close();
           }   
       }   
       catch (Exception e) {   
           e.printStackTrace();    
       }     
   } 
   
   /**
    * 获取文件路径名中的文件名，包含扩展名
    * @param path
    * @return
    */
   public static  String getFileNameFromPath(String path) {
		File f = new File(path);
		return f.getName();
	}
  
   /**
    * 获取文件名，不包含扩展名
    * @param fileName
    * @return
    */
   public static  String getFileNameWithoutExtension(String fileName) {
	   	String name;
	   	int dotIndex = fileName.lastIndexOf( '.' );
	   	if(dotIndex > 0) {
	   		name = fileName.substring( 0, dotIndex );
	   		return name;
	   	}
		return fileName;
	}
  
   public static void deleteFile(String pathName) {
	   File f = new File(pathName);
	   f.delete();
   }
   
}
