package com.aem.valtech.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;

import com.aem.valtech.core.service.Services;
import com.aem.valtech.core.util.inter.ResourceNode;

@SlingServlet(resourceTypes = "valtech/components/structure/page", methods="GET", selectors="valtechLastModifed")
public class ServletUpdatePropertyCreatedAdmin extends SlingAllMethodsServlet {
	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private ResourceNode resourceNode;

	@Reference
	private Services services;
	
	private static final String VALTECH_HOST = "http://localhost:4502";
	private static final String VALTECH_SELECTOR=".2.json";

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		response.setHeader("Content-type", "application/json");
		try {
			Node currentNode = request.getResource().adaptTo(Node.class);
			Node parentNode= currentNode.getParent().getParent() ;			
			Node node = services.lastModifedNodeProperty(parentNode.getPath());
			JSONObject jsonObject = services.getPageInformation(VALTECH_HOST + node.getPath()+VALTECH_SELECTOR); // lastModifedNodeProperty(VALTECH_PATH);
			response.getOutputStream().print(jsonObject.toString());

		} catch (Exception e) {
			response.getOutputStream().println(e.getMessage());
			e.printStackTrace();
		}

	}
}
