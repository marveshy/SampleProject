package com.aem.valtech.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.felix.scr.annotations.Component;
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

@Service
@Component(metatype = false)
public  class ServicesImplement implements Services {
	
	
//	private InputStream getContent(final String url) ;
//    HttpClient httpClient = new HttpClient();
//    httpClient.getParams().setAuthenticationPreemptive(true);
//    httpClient.getState().setCredentials(new AuthScope(null, -1, null),
//        new UsernamePasswordCredentials("admin", "admin"));
//    try {
//        GetMethod get = new GetMethod(url);
//        httpClient.executeMethod(get);
//        if (get.getStatusCode() == HttpStatus.SC_OK) {
//            return get.getResponseBodyAsStream();
//        } else {
//            LOGGER.error("HTTP Error: ", get.getStatusCode());
//        }
//    } catch (HttpException e) {
//        LOGGER.error("HttpException: ", e);
//    } catch (IOException e) {
//        LOGGER.error("IOException: ", e);
//    }
//  }
	
	@Override
	public JSONObject getPageInformation( String url) throws ClientProtocolException, IOException, JSONException {
		
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials
		 = new UsernamePasswordCredentials("admin", "admin");
		provider.setCredentials(AuthScope.ANY, credentials);
		
		HttpClient httpClient =  HttpClientBuilder.create()
				  .setDefaultCredentialsProvider(provider)
				  .build();   //HttpClientBuilder.create().build();
  
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");		
		HttpResponse responsee = httpClient. execute(getRequest);
		BufferedReader br = new BufferedReader(new InputStreamReader((responsee.getEntity().getContent())));
		String output;
		StringBuilder responseStrBuilder = new StringBuilder();
		while ((output = br.readLine()) != null) {
			responseStrBuilder.append(output);
		}
		br.close();
		httpClient.getConnectionManager().shutdown();
		JSONObject result = new JSONObject(responseStrBuilder.toString());
		return result;
	}
	
	
	
	
	
	
	
	

}
