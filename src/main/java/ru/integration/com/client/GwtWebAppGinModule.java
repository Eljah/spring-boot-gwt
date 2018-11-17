package ru.integration.com.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import ru.integration.com.client.controller.WebAppController;
import ru.integration.com.client.model.ModelHandler;
import ru.integration.com.client.resource.ApplicationResources;
import ru.integration.com.client.resource.Messages;
import ru.integration.com.client.ui.MainPanel;

/**
 * Google gin module configuration
 * 
 * @author AGI
 *
 */
public class GwtWebAppGinModule extends AbstractGinModule{

	@Override
	protected void configure() {
		// Resources
		bind(Messages.class).in(Singleton.class);
		bind(ApplicationResources.class).in(Singleton.class);
		
		// Core
		bind(SimpleEventBus.class).in(Singleton.class);
		bind(WebAppController.class).in(Singleton.class);
		bind(ModelHandler.class).in(Singleton.class);
		
		// UI
		bind(MainPanel.class).in(Singleton.class);
	}

}
