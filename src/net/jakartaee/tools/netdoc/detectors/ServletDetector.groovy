package net.jakartaee.tools.netdoc.detectors

import com.sun.javadoc.AnnotationDesc
import com.sun.javadoc.ClassDoc
import com.sun.javadoc.MethodDoc
import com.sun.javadoc.Parameter
import com.sun.javadoc.RootDoc
import com.sun.javadoc.AnnotationDesc.ElementValuePair

import net.jakartaee.tools.netdoc.model.*

class ServletDetector {
	private static final String SERVLET_CLASS = "javax.servlet.GenericServlet";
	
	private static final String space = "           ";
	
//	public Map<String, Servlet> findServlets(RootDoc root){
//		ClassDoc[] classDocs = root.classes();
//		Map<String, Servlet> servlets = new HashMap<>();
//		for ( ClassDoc cd : classDocs ) {
//			Servlet s = getServlet(cd);
//			
//			if ( s == null) continue;	// Ignore classes that are not Servlets
//
//			println("Got servlet: "+ s)
//			servlets.put (s.x_class, s);
//			//servlets.put ("GET_OR_POST", s);
//			
//		}
//		return servlets;
//	}

//	private Servlet getServlet(ClassDoc cd) {
//		ClassDoc cdServlet = cd.findClass(SERVLET_CLASS);
//		
//		println("Got Servlet interfaces: " + cdServlet.getMetaPropertyValues());
//
//		if ( cd == null || cdServlet == null || !cd.subclassOf(cdServlet) ) return; 	// Ignore classes that are not subclasses of javax.servlet.GenericServlet
//		
//		List<String> annotationList = getAnnotations(cd);
//		boolean hasAnnotations = ( annotationList ? !annotationList.empty : false );
//		
//		
//		//if (annotations == null ) System.out.println("Got null annotations for cd: "+cd);
//		
//		Servlet servlet = new Servlet(x_class: cd.toString(), x_annotations: annotationList, x_hasAnnotations: hasAnnotations);
//		
//		System.out.println("Printing Servlet methods for CD: " + cd.methods(false));
//		printMethods(cd);
//		return servlet;
//	}

	public Map<String, ServletMethodMap> findServlets(RootDoc root){
		ClassDoc[] classDocs = root.classes();
		Map<String, ServletMethodMap> servletMethodMap = new HashMap<>();
		for ( ClassDoc cd : classDocs ) {
			ClassDoc cdServlet = cd.findClass(SERVLET_CLASS);
			
			println("Got Servlet interfaces: " + cdServlet.getMetaPropertyValues());
	
			if ( cd == null || cdServlet == null || !cd.subclassOf(cdServlet) ) continue; 	// Ignore classes that are not subclasses of javax.servlet.GenericServlet
			List<String> annotationList = getAnnotations(cd);
			boolean hasAnnotations = ( annotationList ? !annotationList.empty : false );
			
			
			//if (annotations == null ) System.out.println("Got null annotations for cd: "+cd);
			
			//Servlet servlet = new Servlet(x_class: cd.toString(), x_annotations: annotationList, x_hasAnnotations: hasAnnotations);

			System.out.println("Printing Servlet methods for CD: " + cd.methods(false));
			printMethods(cd);
			for( MethodDoc method: cd.methods(false)) {
				Map<String, ServletMethod> servletMethod = [:];
				//servletMethod.put(method, new ServletMethod(operationId: method.name(), parameters: params, produces: producesList, x_class: cd.toString() ));
				servletMethod.put(method, new ServletMethod(operationId: method.name(), x_class: cd.toString() ));
				System.out.println("Got Method: "+ method);
				servletMethodMap.put(method, servletMethod);
			}

	
		}

		return servletMethodMap;
	}
	public Map<String, ServletMethodMap> getServletMap(RootDoc root){
		Map<String, ServletMethodMap> servletMethodMap = [:];
		
		for ( MethodDoc method: cd.methods(false) ) {
			servletMethodMap.put(path, rsMethod);
			
		}
		//return new RestService(clazz: cd.toString(), urlPattern: pathAnnotation, restMethods : rsMethods);
		
		return servletMethodMap;		// TODO: Return MapEntry with the verb as the key
	}

	
	private void printMethods(ClassDoc cd) {
		System.out.print(space + "Methods --> " );
		for ( MethodDoc aMethod: Arrays.asList(cd.methods(false)) ) {
			System.out.print(", " + aMethod.name() );
		}
		System.out.println();
	}
	
	private List<String> getAnnotations(ClassDoc cd) {
		List<String> annotations = new ArrayList<>();
		for ( AnnotationDesc ad : Arrays.asList(cd.annotations()) ) {
			annotations.add(ad.toString());
		}
		return annotations;
	}
		

}
