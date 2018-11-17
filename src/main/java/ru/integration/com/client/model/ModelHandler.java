package ru.integration.com.client.model;

import java.util.ArrayList;
import java.util.List;

import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Todo;

/**
 * Simple model handler
 * manage todo list
 * 
 * @author AGI
 *
 */
public class ModelHandler {

	List<Todo> todoList;
	List<Customer> customersList;
	
	public ModelHandler(){
		todoList = new ArrayList<>();
		customersList = new ArrayList<>();
	}

	public void add(Todo t) {
		todoList.add(t);
	}

	public void remove(Todo t) {
		todoList.remove(t);
	}

	public void removeAll() {
		todoList.clear();
	}

	public List<Todo> getAll() {
		return todoList;
	}

	public void reloadAll(List<Todo> list) {
		todoList.clear();
		for(Todo t : list){
			add(t);
		}
	}

	public List<Customer> getAllCustomers() {
		return customersList;
	}

	public void addCustomer(Customer t) {
		customersList.add(t);
	}

	public void removeCustomer(Customer t) {
		customersList.remove(t);
	}

	public void reloadAllCustomers(List<Customer> list) {
		customersList.clear();
		for(Customer t : list){
			addCustomer(t);
		}
	}

}
