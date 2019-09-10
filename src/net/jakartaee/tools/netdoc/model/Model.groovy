package net.jakartaee.tools.netdoc.model
import groovy.transform.ToString


@ToString
class Report {
	String swagger = '2.0'
	Info info
	String host = 'localhost:8080'
	String basePath = '/JEE8-Sample/api/v1.0'
	List<String> schemes = ["http"]
	
	Map<String, Servlet> servletPaths
	Map<String, RestMethodMap> paths				// OpenAPI3	
//	Map<String, WebSocket> webSockets
//	Map<String, NetConnection> netConnections
	
}

@ToString
class Info {
	String title
	String version
}



@ToString
class Servlet {
	String x_class		// Not sure how to easily handle x- extensions to openAPI.  Who thought a negative sign was a good character for a naming standard?
	List x_parents
	boolean x_hasAnnotations = false
	boolean x_hasWebXml = false
	List x_annotations
	Set x_urlPatterns
	
	String operationId
	List<String> produces
	List<Map> parameters
	Map responses = ['default' : new Response() ]

}

@ToString
class ServletMethodMap {
	String clazz				// class is a reserved word, as is getClass() method
	String urlPattern			// The single urlPattern may be a regEx suppring multiple patterns
	Map<String, ServletMethod> servletMethods
}

@ToString
class ServletMethod {
	//String verb				// This is the map KEY
	String x_class				// Not sure how to easily handle x- extensions to openAPI.  Who thought a negative sign was a good character for a naming standard?
	String operationId
	List<String> produces
	List<Map> parameters
	Map responses = ['default' : new Response() ]
}


@ToString
class RestMethodMap {
	String clazz				// class is a reserved word, as is getClass() method
	String urlPattern			// The single urlPattern may be a regEx suppring multiple patterns
	Map<String, RestMethod> restMethods
}

@ToString
class RestMethod {
	//String verb				// This is the map KEY
	String x_class				// Not sure how to easily handle x- extensions to openAPI.  Who thought a negative sign was a good character for a naming standard?
	String operationId
	List<String> produces			
	List<Map> parameters
	Map responses = ['default' : new Response() ]
}

@ToString
class Response {
	String description = 'successful operation';
}

@ToString
class WebSocket{
	String clazz				// class is a reserved word, as is getClass() method
	List parents
	boolean hasAnnotations = false
	boolean hasWebXml = false
	List annotations
}

@ToString
class NetConnection {
	String clazz				// class is a reserved word, as is getClass() method
	List parents
	List imports
	boolean hasUrlConnection = false
	boolean hasServerSockets = false
	boolean hasSockets = false
}