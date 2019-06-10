package net.jakartaee.tools.netdoc

import com.sun.javadoc.RootDoc

import net.jakartaee.tools.netdoc.detectors.RestServiceDetector
import net.jakartaee.tools.netdoc.detectors.ServletDetector
import net.jakartaee.tools.netdoc.detectors.WebSocketDetector
import net.jakartaee.tools.netdoc.detectors.NetConnectionDetector
import net.jakartaee.tools.netdoc.model.Report
import net.jakartaee.tools.netdoc.model.RestService
import net.jakartaee.tools.netdoc.model.Servlet
import net.jakartaee.tools.netdoc.model.WebSocket
import groovy.json.JsonOutput

class JeeScannerDoclet {
	public static  boolean start(RootDoc doc) {
		def FILE_OUT = "./net-doc-jee-report.json"
		
		System.out.println("--- Starting NetDoc JEE Doclet ---");
		
		Map<String, Servlet> servletMap = new ServletDetector().findServlets(doc);
		System.out.println("Found Servlets: " + servletMap);
		
		Map<String, RestService> serviceMap = new RestServiceDetector().findRestServices(doc);
		System.out.println("Found REST Service: " + serviceMap);
		
		Map<String, WebSocket> socketMap = new WebSocketDetector().findWebSockets(doc);
		System.out.println("Found Web Sockets: " + socketMap);
		
		Map<String, WebSocket> connectionMap = new NetConnectionDetector().findNetConnections(doc);
		System.out.println("Found Net Connections: " + connectionMap);
		
		Report report = new Report( name: "My Report", 
									servlets: servletMap, 
									restServices: serviceMap, 
									webSockets: socketMap, 
									netConnections: connectionMap );
		def jOut = JsonOutput.toJson(report)
		File fOut = new File(FILE_OUT)
		fOut.write(JsonOutput.prettyPrint(jOut))

		System.out.println("Wrote Report: " + FILE_OUT);
		System.out.println("--- Finished JeeScannerDoclet  ---");
		return true;
	}
}
