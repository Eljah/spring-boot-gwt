package ru.integration.com.client.ui.schedule;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import ru.integration.com.client.ui.MainPanel;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Todo;

import java.util.List;

/**
 * Deferred command to do incremental UI refresh 
 * for reloading Todo in the UI
 * 
 * @author AGI
 *
 */
public class ReloadCustomerListCommand implements ScheduledCommand{

	private List<Customer> _customerList;

	private MainPanel _mainPanel;

	private int _index;

	public ReloadCustomerListCommand(List<Customer> list, MainPanel mainPanel){
		_customerList = list;
		_mainPanel = mainPanel;
		_index = 0;
	}
	
	/**
	 * incremental addCustomer todo to the panel
	 * executed after each call of Scheduler.get().scheduleDeferred(this)
	 */
	@Override
	public void execute() {
		if (_index < _customerList.size()){
			Customer customer = _customerList.get(_index);
			_mainPanel.addCustomerToPanel(customer);
			// schedule for next iteration
			Scheduler.get().scheduleDeferred(this);
			_index++;
		}
	}

}
