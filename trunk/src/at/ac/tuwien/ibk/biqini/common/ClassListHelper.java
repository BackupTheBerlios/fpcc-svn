/* 
* ClassListHelper.java
* Christoph Egger
* $Revision$
* 
* Copyright (C) 2010 FTW (Telecommunications Research Center Vienna)
* 
*
* This file is part of BIQINI, a free Policy and Charging Control Function
* for session-based services.
*
* BIQINI is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version
*
* For a license to use the BIQINI software under conditions
* other than those described here, or to purchase support for this
* software, please contact FTW by e-mail at the following addresses:
* fpcc@ftw.at   
*
* BIQINI is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package at.ac.tuwien.ibk.biqini.common;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/***
 * 
 * @author Christoph Egger
 *
 */

public class ClassListHelper {
	
	public static ArrayList<String> getClassNamesInJarPackage(String _JarName, String _PackageName){
		ArrayList<String> nameList = new ArrayList<String>();
		_PackageName = _PackageName.replaceAll("\\." , "/");
		System.out.println("Jar " + _JarName + " for " + _PackageName);
		try{
			JarInputStream jarFile = new JarInputStream(new FileInputStream (_JarName));
			JarEntry jarEntry;
			while(true) {
				jarEntry=jarFile.getNextJarEntry ();
				if(jarEntry == null){
					break;
				}
				if((jarEntry.getName ().startsWith (_PackageName)) &&
						(jarEntry.getName ().endsWith (".class")) ) {
					nameList.add (jarEntry.getName().replaceAll("/", "\\."));
				}
			}
		}
		catch( Exception e){
			e.printStackTrace ();
		}
		return nameList;
	}
	public static ArrayList<String> getClassNamesInPackage(String _PackageName) throws ClassNotFoundException {
		ArrayList<String> classes = new ArrayList<String>();
		// Get a File object for the package
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = _PackageName.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(resource.getFile());
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(_PackageName + " (" + directory
					+ ") does not appear to be a valid package");
		}
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension 
					classes.add(_PackageName + '.' + files[i].substring(0, files[i].length() - 6));
				}
			}
		} else {
			throw new ClassNotFoundException(_PackageName
					+ " does not appear to be a valid package");
		}
		
		return classes;
	}


}
