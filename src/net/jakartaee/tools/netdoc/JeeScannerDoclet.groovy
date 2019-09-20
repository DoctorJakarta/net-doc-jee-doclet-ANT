package net.jakartaee.tools.netdoc

import com.sun.javadoc.RootDoc

import net.jakartaee.tools.netdoc.detectors.ServletDetector
import net.jakartaee.tools.netdoc.detectors.WebSocketDetector
import net.jakartaee.tools.netdoc.detectors.NetConnectionDetector
import net.jakartaee.tools.netdoc.detectors.RestServiceDetector
import net.jakartaee.tools.netdoc.model.*
import groovy.json.JsonOutput

class JeeScannerDoclet {
	public static  boolean start(RootDoc doc) {
				
		System.out.println("--- Starting NetDoc JEE Doclet ---");
		
		List<Servlet> servlets = new ServletDetector().findServlets(doc);
		System.out.println("Found Servlets: " + servlets);

		List<Service> services = new RestServiceDetector().findRestServices(doc);
		System.out.println("Found REST Service: " + services);
		
		List<WebSocket> sockets = new WebSocketDetector().findWebSockets(doc);
		System.out.println("Found Web Sockets: " + sockets);
		
		List<NetConnection> connections = new NetConnectionDetector().findNetConnections(doc);
		System.out.println("Found Net Connections: " + connections);
		
		Info myInfo = new Info(title: "MyAppName", version: "0.2")
		
		Report report = new Report( info: myInfo, 
									servlets: servlets, 
									services: services, 
									sockets: sockets, 
									connections: connections );
								

		def FILE_OUT = "D:/dev/tools/NetDoc/net-doc-jee-report_"+myInfo.getTitle()+".json"
		
		def jOut = JsonOutput.toJson(report)
		File fOut = new File(FILE_OUT)
		fOut.write(JsonOutput.prettyPrint(jOut))

		System.out.println("Wrote Report: " + FILE_OUT);
		System.out.println("--- Finished JeeScannerDoclet  ---");
		return true;
	}
}
