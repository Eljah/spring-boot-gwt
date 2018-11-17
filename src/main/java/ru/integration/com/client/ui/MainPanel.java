package ru.integration.com.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;

import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import ru.integration.com.client.event.AddCustomerEvent;
import ru.integration.com.client.event.DeleteAllTodoEvent;
import ru.integration.com.client.event.LoadEvent;
import ru.integration.com.client.model.ModelHandler;
import ru.integration.com.client.ui.component.ImageButton;
import ru.integration.com.client.ui.component.MapPanel;
import ru.integration.com.client.ui.schedule.ReloadCustomerListCommand;
import ru.integration.com.client.ui.schedule.ReloadTodoListCommand;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Todo;

/**
 * Main UI component
 * 
 * @author AGI
 * 
 */
public class MainPanel extends Composite {

	private static MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);

	interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
	}

	/*
	 * UI components
	 */

	@UiField
	ImageButton addButton;

	@UiField
	ImageButton clearButton;

	@UiField
	ImageButton loadButton;


	@UiField
	ImageButton addCustomerButton;

	@UiField
	TextBox nameTextBox;

	@UiField
	TextBox addressTextBox;

	@UiField
	TextBox phoneNumberTextBox;

	@UiField
	TextBox nodeIdTextBox;


	//@UiField
	FlowPanel todoPanel;

	@UiField
	TabPanel tp = new TabPanel();

	@UiField
	FlowPanel customersPanel;

	@UiField
	MapPanel mapPanel;
	/**
	 * todo widgets references
	 */
	Map<String, TodoWidget> _todoWidgets;

	Map<String, CustomerWidget> _customersWidgets;

	/**
	 * event bus
	 */
	private SimpleEventBus _eventBus;

	/**
	 * model
	 */
	private ModelHandler _modelHandler;

	@Inject
	public MainPanel(SimpleEventBus eventBus, ModelHandler modelHandler) {
		_eventBus = eventBus;
		// init display
		initWidget(uiBinder.createAndBindUi(this));
		_todoWidgets = new HashMap<>();
		_customersWidgets = new HashMap<>();
		_modelHandler = modelHandler;
	}

	/**
	 * handle addButton clickevent event
	 * 
	 * @param e
	 */

	@UiHandler("addCustomerButton")
	void onAddCustomerButtonClick(ClickEvent e) {
		// retrieve textbox text
		//String todoText = textBox.getText();
		// send it to controller for handle business event
		_eventBus.fireEvent(new AddCustomerEvent(new Customer(nameTextBox.getText(),addressTextBox.getText(),phoneNumberTextBox.getText(),nodeIdTextBox.getText())));
	}

	@UiHandler("addButton")
	void onAddButtonClick(ClickEvent e) {
		// retrieve textbox text
		//String todoText = textBox.getText();
		// send it to controller for handle business event
		//_eventBus.fireEvent(new AddTodoEvent(todoText));
	}

	@UiHandler("clearButton")
	void onClearButtonClick(ClickEvent e) {
		// ask controller for delete all event
		_eventBus.fireEvent(new DeleteAllTodoEvent());
	}

	@UiHandler("loadButton")
	void onLoadButtonClick(ClickEvent e) {
		// ask controller for load event
		_eventBus.fireEvent(new LoadEvent());
	}

	public void addCustomerToPanel(Customer t) {
		if (_customersWidgets.get(t.getName()) == null) {
			// create a Todo
			CustomerWidget w = new CustomerWidget(t, _eventBus);
			// addCustomer it to panel
			customersPanel.add(w);
			// keep a reference of the widget for later usage (see
			// removeTodoFromPanel)
			_customersWidgets.put(t.getName(), w);
		}
		else{
			// some error handling code here
			Window.alert("К этому узлу уже присоединены, выберите другой узел для "+t.getName());
		}
	}


	public void removeCustomerFromPanel(Customer t) {
		// retrieve from the references
		CustomerWidget customerWidget = _customersWidgets.get(t.getName());
		// remove it from panel
		customersPanel.remove(customerWidget);

		_customersWidgets.remove(t.getName());
	}

	public void removeAllCustomers() {
		// clear todo panel
		customersPanel.clear();
		// clear references
		_customersWidgets.clear();
	}

	public void reloadCustomerList() {
		// clear all todo
		//removeAllCustomers();
		// retrieve new model
		List<Customer> all2 = _modelHandler.getAllCustomers();
		// usae defered command for incremental UI refresh
		if (all2.size() > 0) {
			// create the command
			ReloadCustomerListCommand reloadCustomerListCommand = new ReloadCustomerListCommand(all2, this);
			// schedule the command call
			Scheduler.get().scheduleDeferred(reloadCustomerListCommand);
		}
	}


	public void addTodoToPanel(Todo t) {
		if (_todoWidgets.get(t.getTitle()) == null) {
			// create a Todo
			TodoWidget w = new TodoWidget(t, _eventBus);
			// addCustomer it to panel
			todoPanel.add(w);
			// keep a reference of the widget for later usage (see
			// removeTodoFromPanel)
			_todoWidgets.put(t.getTitle(), w);
		}
		else{
			// some error handling code here
			Window.alert("Already existing Todo : "+t.getTitle());
		}
	}

	public void removeTodoFromPanel(Todo t) {
		// retrieve todo from the references
		TodoWidget todoWidget = _todoWidgets.get(t.getTitle());
		// remove it from panel
		todoPanel.remove(todoWidget);
		
		_todoWidgets.remove(t.getTitle());
	}

	public void removeAllTodo() {
		// clear todo panel
		todoPanel.clear();
		// clear references
		_todoWidgets.clear();
	}

	public void reloadTodoList() {
		// clear all todo
		removeAllTodo();
		// retrieve new model
		List<Todo> all = _modelHandler.getAll();
		// usae defered command for incremental UI refresh
		if (all.size() > 0) {
			// create the command
			ReloadTodoListCommand reloadCommand = new ReloadTodoListCommand(all, this);
			// schedule the command call
			Scheduler.get().scheduleDeferred(reloadCommand);
		}
	}
}
