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
package com.aem.valtech.core.listeners;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
 
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 



import java.util.ArrayList;
import java.util.Arrays;
 



import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
 
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
 
//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory ; 
import org.apache.sling.api.resource.ResourceResolver; 
import org.apache.sling.api.resource.Resource; 
 



import com.aem.valtech.core.service.Services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
 
 


/**
 * A service to demonstrate how changes in the resource tree can be listened
 * for. It registers an event handler service. The component is activated
 * immediately after the bundle is started through the immediate flag. Please
 * note, that apart from EventHandler services, the immediate flag should not be
 * set on a service.
 */

@Component(immediate = true)
@Service
// @Component(immediate = true)
// @Service(value = EventHandler.class)
// @Property(name = EventConstants.EVENT_TOPIC, value =
// "org/apache/sling/api/resource/Resource/*")
public class SimpleResourceListener implements EventListener {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	private Services services ;
	
	@Reference
	private SlingRepository repository;

	private BundleContext bundleContext;
	private Session session;

	private ObservationManager observationManager;

	public void run() {
		log.info("Running...");
	}

	protected void activate(ComponentContext ctx) {
		this.bundleContext = ctx.getBundleContext();

		try {

			// Invoke the adaptTo method to create a Session
			// ResourceResolver resourceResolver =
			// resolverFactory.getAdministrativeResourceResolver(null);
			session = repository.loginAdministrative(null);

			// Setup the event handler to respond to a new claim under
			// content/claim....
			observationManager = session.getWorkspace().getObservationManager();
			final String types =  "cq:page" ;
			final String path = "/content/valtech/en"; // define the path
			observationManager.addEventListener(this, Event.NODE_ADDED, path,
					true, null, null, false);
			log.info("Observing property changes to {} nodes under {}",
					Arrays.asList(types), path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEvent(EventIterator events) {
		try {
		log.info("A new node was added to content/claim ");
		Event event = (Event)events.nextEvent(); 
		
			Node node = session.getNode(event.getPath() ) ;
			 if(services.addPropertyToPage(node))
				 log.info(" ########## TRUEEEEEEEEEEEEEEE ######### ");
			 else
				 log.info(" ########## FALSE ######### ");
		
		// TODO Auto-generated method stub
		} catch (PathNotFoundException e) {
			log.info("path not found" +e.getMessage());
		} catch (RepositoryException e) {
			log.info("repository not found" +e.getMessage()) ;
		}

	}
	//
	// private final Logger logger = LoggerFactory.getLogger(getClass());
	//
	// public void handleEvent(final Event event) {
	// logger.debug("Resource event: {} at: {}", event.getTopic(),
	// event.getProperty(SlingConstants.PROPERTY_PATH));
	// }
}
