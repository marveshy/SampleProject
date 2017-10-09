package com.aem.valtech.core.service;

import java.io.IOException;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.http.client.ClientProtocolException;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.sun.javafx.collections.MappingChange.Map;

public interface Services {
	JSONObject getPageInformation(String url) throws ClientProtocolException,
			IOException, JSONException;

	Node lastModifedNodeProperty(String resource) throws RepositoryException;
	
	Node getNodeCreatedByAdmin(Node node) throws RepositoryException ;
	
	boolean addPropertyToPage(Node node) throws RepositoryException ;

}
