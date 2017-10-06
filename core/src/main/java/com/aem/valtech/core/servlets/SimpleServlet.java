/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.aem.valtech.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.aem.valtech.core.service.Services;

// import com.aem.valtech.core.service.Services;
// import com.aem.valtech.core.util.inter.ResourceNode;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/hello")
public class SimpleServlet extends SlingSafeMethodsServlet {

	private static final String VALTECH_PATH = "http://localhost:4502/content/content.1.json";

	// @Reference
	// private ResourceNode resourceNode ;
	//
	// @Reference
	// private ResourceResolverFactory resolverFactory;

	@Reference
	private Services services;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// Map<String, Object> param = new HashMap<String, Object>();
			// param.put(ResourceResolverFactory.SUBSERVICE, "writeService");
			// ResourceResolver resourceResolver =
			// resolverFactory.getServiceResourceResolver(param);
			// final Resource resource = req.getResource();
			JSONObject jsonObject;

			jsonObject = services.getPageInformation(VALTECH_PATH);
			jsonObject.remove("jcr:primaryType");
			jsonObject.remove("jcr:created");
			jsonObject.remove("jcr:createdBy");
			jsonObject.remove("jcr:content");
			resp.getOutputStream().print(jsonObject.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
