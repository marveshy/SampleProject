package com.aem.valtech.core.util.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.valtech.core.util.inter.ResourceNode;

@Service
@Component
public class ResourceNodeImpl implements ResourceNode {
	@Reference
	private ResourceResolverFactory resolverFactory;

	private final Logger log = LoggerFactory.getLogger(getClass());

	public Node getNodeForResource(String resource) {
		ResourceResolver resourceResolver;

		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ResourceResolverFactory.SUBSERVICE, "writeService");
			resourceResolver = resolverFactory.getServiceResourceResolver(param); // getAdministrativeResourceResolver(null);
																					// @Deprecated
			Resource ressource = resourceResolver.getResource(resource);
			if (ressource == null)
				return null;

			Node node = ressource.adaptTo(Node.class);
			return node;
		} catch (LoginException e) {

			return null;
		}

	}
}
