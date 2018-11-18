package ru.integration.com.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import ru.integration.com.client.controller.IncidentController;
import ru.integration.com.client.controller.WebAppController;
import ru.integration.com.client.model.IncidentModelHandler;
import ru.integration.com.client.model.ModelHandler;
import ru.integration.com.client.resource.ApplicationResources;
import ru.integration.com.client.resource.Messages;
import ru.integration.com.client.ui.MainPanel;

/**
 * Google gin injector
 * 
 * all components to inject
 * @author AGI
 *
 */
@GinModules(GwtWebAppGinModule.class)
public interface GwtWebAppGinjector extends Ginjector {
	
	public SimpleEventBus getEventBus();
	
	public ApplicationResources getApplicationResources();
	
	public Messages getMessages();
	
	public WebAppController getWebAppController();

	public IncidentController getIncidentController();
	
	public ModelHandler getModelHandler();

	public IncidentModelHandler getIncidentModelHandler();
	
	public MainPanel getMainPanel(); 
}
