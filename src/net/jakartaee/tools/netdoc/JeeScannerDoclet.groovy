package net.jakartaee.tools.netdoc

import com.sun.javadoc.RootDoc

import net.jakartaee.tools.netdoc.detectors.RestServiceDetector
import net.jakartaee.tools.netdoc.detectors.ServletDetector
import net.jakartaee.tools.netdoc.detectors.WebSocketDetector
import net.jakartaee.tools.netdoc.detectors.NetConnectionDetector
import net.jakartaee.tools.netdoc.model.*
import groovy.json.JsonOutput

class JeeScannerDoclet {
	public static  boolean start(RootDoc doc) {
		//def FILE_OUT = "./net-doc-jee-report.json"
		def FILE_OUT = "D:/dev/tools/NetDoc/sample/net-doc-jee-report.json"
		
		System.out.println("--- Starting NetDoc JEE Doclet ---");
		
		//Map<String, Servlet> servletMap = new ServletDetector().findServlets(doc);
		Map<String, ServletMethodMap> servletMap = new ServletDetector().findServlets(doc);
		System.out.println("Found Servlets: " + servletMap);
		
		Map<String, RestMethodMap> serviceMap = new RestServiceDetector().findRestServices(doc);
		System.out.println("Found REST Service: " + serviceMap);
		
		Map<String, WebSocket> socketMap = new WebSocketDetector().findWebSockets(doc);
		System.out.println("Found Web Sockets: " + socketMap);
		
		Map<String, WebSocket> connectionMap = new NetConnectionDetector().findNetConnections(doc);
		System.out.println("Found Net Connections: " + connectionMap);
		
		Info myInfo = new Info(title: "My App Name", version: "0.2")
		
//		Report report = new Report( info: myInfo, 
//									servlets: servletMap, 
//									paths: serviceMap, 
//									webSockets: socketMap, 
//									netConnections: connectionMap );
		Report report = new Report( info: myInfo,
 									servletPaths: servletMap, 
									paths: serviceMap );

		def jOut = JsonOutput.toJson(report)
		File fOut = new File(FILE_OUT)
		fOut.write(JsonOutput.prettyPrint(jOut))

		System.out.println("Wrote Report: " + FILE_OUT);
		System.out.println("--- Finished JeeScannerDoclet  ---");
		return true;
	}
}
