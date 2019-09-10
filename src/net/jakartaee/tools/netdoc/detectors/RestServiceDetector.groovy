package net.jakartaee.tools.netdoc.detectors

import com.sun.javadoc.AnnotationDesc
import com.sun.javadoc.ClassDoc
import com.sun.javadoc.MethodDoc
import com.sun.javadoc.Parameter
import com.sun.javadoc.RootDoc
import com.sun.javadoc.AnnotationDesc.ElementValuePair

import net.jakartaee.tools.netdoc.model.RestMethod
import net.jakartaee.tools.netdoc.model.RestMethodMap

class RestServiceDetector {
	private static final String JAXRS_PKG = "javax.ws.rs.";
	private static final String JAXRS_PATH = JAXRS_PKG + "Path";
	private static final String JAXRS_PRODUCES = JAXRS_PKG + "Produces";
	private static enum JAXRS_VERB {GET, PUT, POST, DELETE};
	
	public Map<String, RestMethodMap> findRestServices(RootDoc root){
		ClassDoc[] classDocs = root.classes();
		Map<String, RestMethodMap> restMethodMap = new HashMap<>();
		for ( ClassDoc cd : classDocs ) {
			//if ( pathAnnotation == null ) return null;
			if ( cd.annotations() != null ) {
				return getRestService(cd);
			}		
		}
		return null;
	}
				// TODO: Return MapEntry with the verb as the key
	private Map<String, RestMethodMap> getRestService(ClassDoc cd) {
		Map<String, RestMethodMap> rsMethodMap = [:];
		String basePath = getAnnotationValue(JAXRS_PATH, cd.annotations());
		
		for ( MethodDoc method: cd.methods() ) {
			println("Got REST method: " + method )
			println("Got method.annotations(): " + method.annotations())
			List<Map> params = new ArrayList<>();
			
			String rsVerb = getJaxRsVerb(method.annotations());
			if ( rsVerb != null ) {
				
				for ( Parameter param : method.parameters()) {
					params.add(getParameterMap(param.name()));
					println('Got params: ' + params);
				}
				String relPath = getAnnotationValue(JAXRS_PATH, method.annotations()) 
				String path = basePath
				
				if ( relPath != null && relPath.length() > 1 ) path += '/' + relPath	// openAPI does not add '/' for default basePath.  Not sure if this matters, but I am matching  swagger.json 
				//println("Had basePath: " + basePath + " and now have path: " + path);
				
				List<String> producesList = getAnnotationList(JAXRS_PRODUCES, method.annotations())
				//println("Got producesList: " + producesList)
				
				//List<String> producesList = [ producesString ]
				//RestMethod rsm = new RestMethod(verb: rsVerb,operationId: method.name(), parameters: paramNames);
				Map<String, RestMethod> rsMethod = [:];
				rsMethod.put(rsVerb.toLowerCase(), new RestMethod(operationId: method.name(), parameters: params, produces: producesList, x_class: cd.toString() ));
				
				rsMethodMap.put(path, rsMethod);
			}
		}
		//return new RestService(clazz: cd.toString(), urlPattern: pathAnnotation, restMethods : rsMethods);
		
		return rsMethodMap;		// TODO: Return MapEntry with the verb as the key
	}

	private String getAnnotationValue(String atName, AnnotationDesc[] annotationDescs) {
		if ( annotationDescs == null ) return null;
		String str = "";
		for ( AnnotationDesc ad : annotationDescs) {
			String typeNane = ad.annotationType().qualifiedName();
			if (!atName.equals(typeNane) ) continue;
			for ( ElementValuePair evp : ad.elementValues() ) {
				str = evp.value().value().toString();					// Extract path from String "{bookId}"
			}		
		}
		return str;
	}
	
	private List<String> getAnnotationList(String atName, AnnotationDesc[] annotationDescs) {
		if ( annotationDescs == null ) return null;
		List<String> strings = new ArrayList<>();
		for ( AnnotationDesc ad : annotationDescs) {
			String typeNane = ad.annotationType().qualifiedName();
			if (!atName.equals(typeNane) ) continue;
			for ( ElementValuePair evp : ad.elementValues() ) {
//				String str = evp.value();
//				str = str.substring(1,str.length()-1)		// Strip off quotes "application/json"
//				println("Got trimmed str: " + str)
//				strings.add(str);
				strings = getStrippedTokens(evp.value().value().toString());
				println("Got strings: " + strings)
			}
		}
		return strings;
	}
	
	public List<String> getStrippedTokens(String stringList) {
		List<String> tokens = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(stringList, ",");
		while (tokenizer.hasMoreElements()) {
			String str = tokenizer.nextToken().replace('[','').replace(']','').trim();
			println("Got str: " + str)
			str = str.substring(1,str.length()-1)
			println("Adding str: " + str)
			tokens.add(str);
		}
		println("Got tokens: " + tokens)
		
		return tokens;
	}
	private String getJaxRsVerb( AnnotationDesc[] methodAnnotations ) {
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
	
	private Map getParameterMap(String paramName) {
		def paramMap = [:]
		paramMap.put("name", paramName)
		paramMap.put("in", "path")
		paramMap.put("required", true)
		paramMap.put("type", "integer")
		paramMap.put("format", "int32")		
		return paramMap
	}
}
