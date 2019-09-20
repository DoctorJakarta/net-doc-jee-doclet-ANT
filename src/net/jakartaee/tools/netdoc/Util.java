package net.jakartaee.tools.netdoc;

import com.sun.javadoc.ClassDoc;

public class Util {

	public static String getClassName(ClassDoc cd) {
		return cd.toString().substring( cd.toString().lastIndexOf(".")+1);
	}
	
	public static String getPackageName(ClassDoc cd) {
		return cd.toString().substring(0, cd.toString().lastIndexOf("."));
	}
}
