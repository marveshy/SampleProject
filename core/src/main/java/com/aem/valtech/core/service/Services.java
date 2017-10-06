package com.aem.valtech.core.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public interface Services {	
	 JSONObject getPageInformation(String url) throws ClientProtocolException, IOException, JSONException ;

}
