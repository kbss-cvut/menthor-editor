package net.menthor.editor.util;

/**
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
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.menthor.editor.model.UmlProject;

public class MenthorStore {

	enum FileType
	{
		MODEL,
		PROJECT,
		SIMULATION,
		SIMULATION_OUTPUT
	}
	
	/**
	 * Returns an input stream to the model file.
	 */
	public static InputStream getFile(String path, FileType type) throws IOException
	{
		ZipFile zf = null;
		if(!path.endsWith(getStoreExtension()))
			throw new IOException("File type not supported");
			
		try {
			
			zf = new ZipFile(path);
			ZipEntry entry = zf.getEntry(getFileName(type));
			InputStream in = zf.getInputStream(entry);
						
			return in;
			
		} finally {
			zf.close();
		}
	}
	
	public static String getFileName(FileType type)
	{
		switch (type) {
		case MODEL: 
			return "model.ontouml";
		case PROJECT: 
			return "project.dat";
		case SIMULATION: 
			return "simulation.als";
		case SIMULATION_OUTPUT: 
			return "simulation_output.xml";
		default:
			return null;
		}
	}
	
	public static String getStoreExtension()
	{
		return ".menthor";
	}

	
	public static void save(UmlProject project, String path) {
		
		
	}
	
}