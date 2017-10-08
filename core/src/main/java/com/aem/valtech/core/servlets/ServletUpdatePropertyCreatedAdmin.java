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

@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/koukou")
public class ServletUpdatePropertyCreatedAdmin extends SlingAllMethodsServlet {

	private static final String VALTECH_PATH = "http://localhost:4502/content/valtech.2.json";

	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private ResourceNode resourceNode;

	@Reference
	private Services services;

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		response.setHeader("Content-type", "application/json");
		// JSONObject jsonObject = new JSONObject();
		// JSONArray jsonArray = new JSONArray();
		// PageManager pageManager =
		// request.getResource().adaptTo(PageManager.class);
		try {

			// Node currentNode = request.getResource().adaptTo(Node.class);
			Node queryRoot = services
					.lastModifedNodeProperty("/content/valtech"); // resourceNode
																	// .getNodeForResource("/content/valtech");
			// Node queryRoot = pageManager.getContainingPage(
			// currentNode.getPath()).adaptTo(Node.class);
			// List<String> list = new ArrayList<String>();
			// NodeIterator nodeIterator = queryRoot.getNodes();
			// if (nodeIterator != null) {
			// while (nodeIterator.hasNext()) {
			// Node node = nodeIterator.nextNode();
			// if (String.valueOf(
			// node.getProperty("jcr:primaryType").getValue())
			// .equals("cq:Page")
			// && String.valueOf(
			// node.getProperty("jcr:createdBy")
			// .getValue()).equals("admin")) {
			// list.add(node.getPath());
			// Node nodeJcrContent = node.getNodes("jcr:content")
			// .nextNode();
			//
			// nodeJcrContent.setProperty("jcr:lastModifed",
			// Calendar.getInstance());
			// // nodeJcrContent.getSession().save();
			// }
			// }
		//	queryRoot.getSession().save();
			JSONObject jsonObject = services.getPageInformation(VALTECH_PATH);
			response.getOutputStream().print(jsonObject.toString());

		} catch (Exception e) {
			response.getOutputStream().println(e.getMessage());
			e.printStackTrace();
		}

	}
}
