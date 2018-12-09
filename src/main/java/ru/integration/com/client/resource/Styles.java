package ru.integration.com.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Container of all CSS styles used in UI application.
 * 
 * @author Andrea "Stock" Stocchero
 *
 */
public interface Styles extends ClientBundle {

	Styles INSTANCE = GWT.create(Styles.class);

	// toast styles
	@Source("Toast.css")
	Toast toast();

	/**
	 * Toast CSS classes
	 * 
	 * @author Andrea "Stock" Stocchero
	 *
	 */
	interface Toast extends CssResource {
		String main();
		String title();
		String message();
		String blue();
		String green();
		String grey();
		String yellow();
		String red();
		String lightGreen();
		String lightBlue();
	}
	
}
