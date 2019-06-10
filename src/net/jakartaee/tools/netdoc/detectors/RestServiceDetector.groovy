package net.jakartaee.tools.netdoc.detectors

import com.sun.javadoc.AnnotationDesc
import com.sun.javadoc.ClassDoc
import com.sun.javadoc.MethodDoc
import com.sun.javadoc.Parameter
import com.sun.javadoc.RootDoc
import com.sun.javadoc.AnnotationDesc.ElementValuePair

import net.jakartaee.tools.netdoc.model.RestMethod
import net.jakartaee.tools.netdoc.model.RestService

class RestServiceDetector {
	private static final String JAXRS_PKG = "javax.ws.rs.";
	private static final String JAXRS_PATH = JAXRS_PKG + "Path";
	private static enum JAXRS_VERB {GET, PUT, POST, DELETE};
	
	public Map<String, RestService> findRestServices(RootDoc root){
		ClassDoc[] classDocs = root.classes();
		Map<String, RestService> restServices = new HashMap<>();
		for ( ClassDoc cd : classDocs ) {
			RestService rs = getRestService(cd);
			
			if ( rs == null )  continue;	// Ignore classes that are not RestServices
			
			restServices.put (rs.clazz, rs);
			
		}
		return restServices;
	}

	private RestService getRestService(ClassDoc cd) {
		if ( cd.annotations() == null ) return null;
		String pathAnnotation = getJaxRsPath(cd.annotations());
		if ( pathAnnotation == null ) return null;
		List<RestMethod> rsMethods= new ArrayList<>();
		for ( MethodDoc method: cd.methods() ) {
			String rsVerb = getJaxRsVerb(method.annotations());
			if ( rsVerb != null ) {
				List<String> paramNames = new ArrayList<>();
				for ( Parameter param : method.parameters()) {
					paramNames.add(param.name());
				}
				RestMethod rsm = new RestMethod(verb: rsVerb,method: method.name(), params: paramNames);
				rsMethods.add(rsm);
			}
		}
		return new RestService(clazz: cd.toString(), urlPattern: pathAnnotation, restMethods : rsMethods);
	}

	private String getJaxRsPath(AnnotationDesc[] annotationDescs) {
		if ( annotationDescs == null ) return null;
		String rsPath = null;
		for ( AnnotationDesc ad : annotationDescs) {
			String typeNane = ad.annotationType().qualifiedName();
			if (!JAXRS_PATH.equals(typeNane) ) continue;
			for ( ElementValuePair evp : ad.elementValues() ) {
				rsPath = evp.value().value().toString();
			}		
		}
		return rsPath;
	}
	
	public String getJaxRsVerb( AnnotationDesc[] methodAnnotations ) {
		//System.out.println("Looking for Verbs in annotations: " + methodAnnotations);
		for ( JAXRS_VERB verb : JAXRS_VERB.values()) {
			//System.out.println("Looking for Verb: " + verb);
			for ( AnnotationDesc ad : methodAnnotations ) {
				String typeName = ad.annotationType().qualifiedName();
				if (typeName.equals( JAXRS_PKG + verb)) return verb;
			}
		}
		return null;
	}
}
