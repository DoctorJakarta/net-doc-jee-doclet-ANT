package net.jakartaee.tools.netdoc.model
import groovy.transform.ToString


@ToString
class Report {
	String name
	Map<String, Servlet> servlets
	Map<String, RestService> restServices
	Map<String, WebSocket> webSockets
	Map<String, NetConnection> netConnections
}

@ToString
class Servlet {
	private String clazz	// class is a reserved word, as is getClass() method.  Private will hide from JSON
	List parents
	boolean hasAnnotations = false
	boolean hasWebXml = false
	List annotations
	Set urlPatterns
}

@ToString
class RestMethod {
	String verb
	String method
	List<String> params
}

@ToString
class RestService {
	String clazz				// class is a reserved word, as is getClass() method
	String urlPattern			// The single urlPattern may be a regEx suppring multiple patterns
	List<RestMethod> restMethods
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