package net.jakartaee.tools.netdoc.detectors

import com.sun.javadoc.AnnotationDesc
import com.sun.javadoc.ClassDoc
import com.sun.javadoc.MethodDoc
import com.sun.javadoc.Parameter
import com.sun.javadoc.RootDoc
import com.sun.javadoc.AnnotationDesc.ElementValuePair

import net.jakartaee.tools.netdoc.model.Servlet

class ServletDetector {
	private static final String SERVLET_CLASS = "javax.servlet.GenericServlet";
	
	private static final String space = "           ";
	
	public Map<String, Servlet> findServlets(RootDoc root){
		ClassDoc[] classDocs = root.classes();
		Map<String, Servlet> servlets = new HashMap<>();
		for ( ClassDoc cd : classDocs ) {
			Servlet s = getServlet(cd);
			
			if ( s == null) continue;	// Ignore classes that are not Servlets

			servlets.put (s.clazz, s);
			
		}
		return servlets;
	}

	private Servlet getServlet(ClassDoc cd) {
		ClassDoc cdServlet = cd.findClass(SERVLET_CLASS);

		if ( cd == null || cdServlet == null || !cd.subclassOf(cdServlet) ) return; 	// Ignore classes that are not subclasses of javax.servlet.GenericServlet
		
		List<String> annotationList = getAnnotations(cd);
		boolean hasAnnotations = ( annotationList ? !annotationList.empty : false );
		
		
		//if (annotations == null ) System.out.println("Got null annotations for cd: "+cd);
		
		Servlet servlet = new Servlet(clazz: cd.toString(), annotations: annotationList, hasAnnotations: hasAnnotations);
		return servlet;
	}
	
	private List<String> getAnnotations(ClassDoc cd) {
		List<String> annotations = new ArrayList<>();
		for ( AnnotationDesc ad : Arrays.asList(cd.annotations()) ) {
			annotations.add(ad.toString());
		}
		return annotations;
	}
		

}
