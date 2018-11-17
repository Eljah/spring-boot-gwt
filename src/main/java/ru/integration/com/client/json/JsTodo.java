package ru.integration.com.client.json;

import com.google.gwt.core.client.JavaScriptObject;

public class JsTodo extends JavaScriptObject {

	protected JsTodo() {
	}

	public native final String title() /*-{
		return this.title;
	}-*/;
}
