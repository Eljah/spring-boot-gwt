package ru.integration.com.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import ru.integration.com.client.controller.IncidentController;
import ru.integration.com.client.controller.WebAppController;
import ru.integration.com.client.resource.ApplicationResources;
import ru.integration.com.client.ui.MainPanel;
import ru.integration.com.common.model.Incident;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * Point d'entrée du module GWT
 */
public class GwtWebApp implements EntryPoint {

	/**
	 * gin injector
	 */
	private final GwtWebAppGinjector injector = GWT.create(GwtWebAppGinjector.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// ensure resources are injected
		ApplicationResources.INSTANCE.style().ensureInjected();
		// get controler from gin jector
		WebAppController controller = injector.getWebAppController();
		IncidentController incidentController = injector.getIncidentController();
		// bind event handlers
		controller.bindHandlers();
		incidentController.bindHandlers();
		// get main panel
		MainPanel mainPanel = injector.getMainPanel();
		// addCustomer for display
		RootLayoutPanel.get().add(mainPanel);
	}
}
