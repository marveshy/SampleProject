package com.aem.valtech.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.jackrabbit.oak.commons.json.JsonObject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.aem.valtech.core.util.inter.ResourceNode;
import com.sun.javafx.collections.MappingChange.Map;

@Service
@Component(metatype = false)
public class ServicesImplement implements Services {

	@Reference
	private ResourceNode resourceNode;

	@Override
	public JSONObject getPageInformation(String url)
			throws ClientProtocolException, IOException, JSONException {

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				"admin", "admin");
		provider.setCredentials(AuthScope.ANY, credentials);

		HttpClient httpClient = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();

		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
		HttpResponse responsee = httpClient.execute(getRequest);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(responsee.getEntity().getContent())));
		String output;
		StringBuilder responseStrBuilder = new StringBuilder();
		while ((output = br.readLine()) != null) {
			responseStrBuilder.append(output);
		}
		br.close();
		httpClient.getConnectionManager().shutdown();
		JSONObject result = new JSONObject(responseStrBuilder.toString());
		result.remove("jcr:primaryType");
		result.remove("jcr:created");
		result.remove("jcr:createdBy");
		result.remove("jcr:content");
		return result;
	}

	@Override
	public Node lastModifedNodeProperty(String resource)
			throws RepositoryException {
		Node queryRoot = resourceNode.getNodeForResource(resource);
		NodeIterator nodeIterator = queryRoot.getNodes();
		if (nodeIterator != null) {
			while (nodeIterator.hasNext()) {
				Node node = nodeIterator.nextNode();
				if (getNodeCreatedByAdmin(node) != null) {
					Node nodeJcrContent = getNodeCreatedByAdmin(node).getNodes(
							"jcr:content").nextNode();
					nodeJcrContent.setProperty("jcr:lastModifed",
							Calendar.getInstance());
					
				}
			}

		}
		queryRoot.getSession().save();
		return queryRoot;

	}

	@Override
	public Node getNodeCreatedByAdmin(Node node) throws RepositoryException {
		if (node != null) {
			if (String.valueOf(node.getProperty("jcr:primaryType").getValue())
					.equals("cq:Page")
					&& String.valueOf(
							node.getProperty("jcr:createdBy").getValue())
							.equals("admin")) {
				return node;
			}
		}
		return null;

	}
}
