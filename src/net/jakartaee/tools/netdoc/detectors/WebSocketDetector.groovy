package net.jakartaee.tools.netdoc.detectors

import com.sun.javadoc.RootDoc
import com.sun.javadoc.AnnotationDesc
import com.sun.javadoc.ClassDoc
import com.sun.javadoc.MethodDoc
import com.sun.javadoc.Parameter
import com.sun.javadoc.AnnotationDesc.ElementValuePair

import net.jakartaee.tools.netdoc.model.Servlet
import net.jakartaee.tools.netdoc.model.WebSocket


class WebSocketDetector {
	private static final String SOCKET_ANNOTAION = "ServerEndpoint";
	
	public Map<String, WebSocket> findWebSockets(RootDoc root){
		ClassDoc[] classDocs = root.classes();
		Map<String, WebSocket> webSockets = new HashMap<>();
		for ( ClassDoc cd : classDocs ) {
			WebSocket ws = getWebSockets(cd);
			
			if ( ws == null )  continue;	// Ignore classes that are not WebSocket
			
			webSockets.put (ws.clazz, ws);			
		}
		return webSockets;
	}
	
	private WebSocket getWebSockets(ClassDoc cd) {
		if ( !isWebSocket(cd) ) return null;
		List<String> annotationList = getAnnotations(cd);
		boolean hasAnnotations = ( annotationList ? !annotationList.empty : false );
		
		WebSocket webSocket = new WebSocket(clazz: cd.toString(), annotations: annotationList, hasAnnotations: hasAnnotations);
		
		return webSocket;
	}
	
	private boolean isWebSocket(ClassDoc cd) {
		boolean hasWebSocket = false;
		for ( String annot : getAnnotations(cd)) {
			if ( annot.contains(SOCKET_ANNOTAION)) hasWebSocket = true;
		}
		return hasWebSocket;
	}
	
	private List<String> getAnnotations(ClassDoc cd) {
		List<String> annotations = new ArrayList<>();
		for ( AnnotationDesc ad : Arrays.asList(cd.annotations()) ) {
			annotations.add(ad.toString());
		}
		return annotations
	}
}
