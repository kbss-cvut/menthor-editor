package net.menthor.editor.v2.util;

/*
 * ============================================================================================
 * Menthor Editor -- Copyright (c) 2015 
 *
 * This file is part of Menthor Editor. Menthor Editor is based on TinyUML and as so it is 
 * distributed under the same license terms.
 *
 * Menthor Editor is free software; you can redistribute it and/or modify it under the terms 
 * of the GNU General Public License as published by the Free Software Foundation; either 
 * version 2 of the License, or (at your option) any later version.
 *
 * Menthor Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Menthor Editor; 
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA  02110-1301  USA
 * ============================================================================================
 * 
 * @author John Guerson
 */

import java.awt.Window;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/** A helper class which provides settings and file management facilities. */
public class Util {
	
	public static boolean onMac() {
		return System.getProperty("mrj.version") != null || System.getProperty("os.name").toLowerCase(Locale.US).startsWith("mac ");
	}

	public static String getFileName(String filePath) {
		return new File(filePath).getName();
	}

	public static String getUserDir() {
		return canon(System.getProperty("user.name"));
	}

	public static String getCanonPath(String dir, String fileName) {
		return canon(dir + File.separatorChar + fileName);
	}
	
	public static boolean onWindows() {
		return System.getProperty("os.name").toLowerCase(Locale.US).startsWith("windows");
	};

	public static String getOSx(){ 
		if (onWindows()) return "win"; else if (onMac()) return "mac"; else return "linux"; 
	}
			
	public static String getArch() { 
		String osArch = System.getProperty("os.arch").toLowerCase();
		String arch = osArch.contains("64") ? "x64" : "x86";                
        return arch;
	}

	public static int getScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}
	
    public static int getScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

    public static String getCompilationDateMessage(){
		DateFormat dateFormat = new SimpleDateFormat("d, yyyy");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		return c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) + " " + dateFormat.format(date);
	}
	
	public static void enableFullScreenMode(Window window) {
        String className = "com.apple.eawt.FullScreenUtilities";
        String methodName = "setWindowCanFullScreen"; 
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName, new Class<?>[] { Window.class, boolean.class });
            method.invoke(null, window, true);
        } catch (Throwable t) {
            System.err.println("Full screen mode is not supported");
            t.printStackTrace();
        }
    }	
	
    /** Helper method for constructing an always-on-top modal dialog. */
    public static Object show(String title, int type, Object message, Object[] options, Object initialOption) {
		if (options == null) { options = new Object[]{"Ok"};  initialOption = "Ok"; }
		JOptionPane p = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION, null, options, initialOption);
		p.setInitialValue(initialOption);
		JDialog d = p.createDialog(null, title);
		p.selectInitialValue();
		d.setAlwaysOnTop(true);
		d.setVisible(true);
		d.dispose();
		return p.getValue();
	}
	
    
    
	/** 
	 * This returns the constant prefix to denote whether Util.readAll() should read from a JAR or read from the file system.
	 * (The reason we made this into a "method" rather than a constant String is that it is used
	 * by Util.canon() which is called by many static initializer blocks... so if we made this into a static field
	 * of Util, then it may not be initialized yet when we need it!) 
	 */
	public static String jarPrefix() { 
		return File.separator + "$menthor$" + File.separator; 
	}
	
	/** Returns the canonical absolute path for a file.
	  * If an IO error occurred, or if the file doesn't exist yet,
	  * we will at least return a non-canonical but absolute path for it.
	  * <p> Note: if filename=="", we return "".
	  */
	public static final String canon(String filename) {
		if (filename==null || filename.length()==0) return "";
		if (filename.startsWith(jarPrefix())) {
			char sep = File.separatorChar, other = (sep=='/' ? '\\' : '/');
			return filename.replace(other, sep);
		}
		File file = new File(filename);
		try { 
			return file.getCanonicalPath(); 
		} catch(IOException ex) { 
			return file.getAbsolutePath(); 
		}
	}
	
	/**
	 * Inspects the name of the specified file and checks if it ends with the
	 * specified suffix. If not, a new file will be created, appending the suffix
	 * to the file name, otherwise the original file object will be returned. 
	 */
	public static File getFileWithExtension(File file, String extension) {
	    String path = file.getPath();
	    File result = file;
	    Pattern pattern = Pattern.compile(".*\\" + extension);
	    if (!pattern.matcher(path).matches()) {
	      path = path + extension;
	      result = new File(path);
	    }
	    return result;
	}
	
	/**
	 * Attempt to close the file/stream/reader/writer and return true if and
	 * only if we successfully closed it. (If object==null, we return true right away)
	 */
	public static boolean close(Closeable object) {
		if (object == null) return true;
		boolean ans = true;
		try {
			if (object instanceof PrintStream && ((PrintStream) object).checkError()) ans = false;
			if (object instanceof PrintWriter && ((PrintWriter) object).checkError()) ans = false;
			object.close();
			return ans;
		} catch (Throwable ex) {
			return false;
		}
	}
	
	/** Extract a file from menthor's /lib class path */
	public static File extractLib(String fileNameWithExtension) throws IOException{
		String destFolderPath = Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();	
		if(destFolderPath.lastIndexOf("/") < destFolderPath.lastIndexOf(".")){
			int lastBar = destFolderPath.lastIndexOf("/");
			destFolderPath = destFolderPath.substring(0, lastBar+1);
		}
		destFolderPath += fileNameWithExtension;
		String alloyPath = URLDecoder.decode(destFolderPath, "UTF-8");		
		File alloyJarFile = new File(alloyPath);
		if (alloyJarFile.exists()) return alloyJarFile;				
		InputStream is = Util.class.getClassLoader().getResourceAsStream(fileNameWithExtension);		
		if(is == null) is = new FileInputStream("lib/"+fileNameWithExtension);
		OutputStream out = new FileOutputStream(alloyJarFile);				
		// copy data flow -> MB x MB
		byte[] src = new byte[1024];
		int read = 0;
		while ((read = is.read(src)) != -1){
			out.write(src, 0, read);
		}
		is.close();
		out.flush();
		out.close();		
		return alloyJarFile;
	}
}