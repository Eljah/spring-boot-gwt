package ru.integration.com.client.ui;

import java.awt.*;
import java.util.*;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.inject.Binder;
import com.google.inject.Inject;

import ru.integration.com.client.event.*;
import ru.integration.com.client.event.incident.NewIncidentEvent;
import ru.integration.com.client.model.ModelHandler;
import ru.integration.com.client.ui.component.ImageButton;
import ru.integration.com.client.ui.component.MapPanel;
import ru.integration.com.client.ui.schedule.ReloadCustomerListCommand;
import ru.integration.com.client.ui.schedule.ReloadTodoListCommand;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;
import ru.integration.com.common.model.Todo;

/**
 * Main UI component
 *
 * @author AGI
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
    ImageButton historyButton;

    @UiField
    ImageButton loadButton;


    @UiField
    ImageButton addCustomerButton;

    @UiField
    ImageButton saveCustomerButton;

    @UiField
    TextBox nameTextBox;

    @UiField
    TextBox addressTextBox;

    @UiField
    TextBox phoneNumberTextBox;

    @UiField
    TextBox nodeIdTextBox;


    @UiField
    FlowPanel incidentPanel;


    //@UiField
    FlowPanel todoPanel;

    @UiField
    TabPanel tp = new TabPanel();

    @UiField
    FlowPanel customersPanel;

    @UiField
    MapPanel mapPanel;

    @UiField
    DataGrid<Incident> dataGrid;

    @UiField
    SimplePager pager;
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

    public static final ProvidesKey<Incident> KEY_PROVIDER = new ProvidesKey<Incident>() {
        @Override
        public Object getKey(Incident item) {
            return item == null ? null : item.getIncedentStart();
        }
    };

    public static interface CwConstants extends Constants {
        String cwDataGridColumnAddress();

        String cwDataGridColumnAge();

        String cwDataGridColumnCategory();

        String cwDataGridColumnFirstName();

        String cwDataGridColumnLastName();

        String cwDataGridDescription();

        String cwDataGridEmpty();

        String cwDataGridName();
    }


    //private final CwConstants constants=GWT.create(CwConstants);

    @Inject
    public MainPanel(SimpleEventBus eventBus, ModelHandler modelHandler) {
        _eventBus = eventBus;
        // init display
        initWidget(uiBinder.createAndBindUi(this));
        _todoWidgets = new HashMap<>();
        _customersWidgets = new HashMap<>();
        _modelHandler = modelHandler;
        saveCustomerButton.setVisible(false);


        tp.addTabListener(new TabListener() {
            @Override
            public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
                return true;
            }

            @Override
            public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
                if (tabIndex == 2) {
                    dataGrid.redraw();
                    //that is needed to draw the DataGrid data, whithout it it looks empty
                    //dataGrid.redrawRow(0);
                    //dataGrid.redrawRow(1);
                }
            }
        });

        //init datagred tot remove to separate method
        //dataGrid = new DataGrid<Incident>(KEY_PROVIDER);
        dataGrid.setWidth("500px");
        //dataGrid.setWidth("500px");
        dataGrid.setHeight("500px");

    /*
     * Do not refresh the headers every time the data is updated. The footer
     * depends on the current data, so we do not disable auto refresh on the
     * footer.
     */
        List<Incident> incidents = new ArrayList<>();
        Incident i = new Incident();
        i.setIncedentStart(new Date());
        i.setPhoneNumber("7904764008");
        i.setDescription("Прорыв канализации, вытекает через колодец на проезжую часть");
        incidents.add(i);
        i = new Incident();
        i.setIncedentStart(new Date("1970-01-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        incidents.add(i);

        //Window.alert(incidents.size()+"");


        dataGrid.setAutoHeaderRefreshDisabled(true);

        // Set the message to display when the table is empty.
        dataGrid.setEmptyTableWidget(new Label("Нет данных"));

        // Attach a column sort handler to the ListDataProvider to sort the list.
        ColumnSortEvent.ListHandler<Incident> sortHandler =
                new ColumnSortEvent.ListHandler<Incident>(incidents) {
                    @Override
                    public void onColumnSort(ColumnSortEvent event) {
                        super.onColumnSort(event);
                        dataGrid.redraw();
                    }

                };

        dataGrid.addColumnSortHandler(sortHandler);

        // Create a Pager to control the table.
        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
        pager.setDisplay(dataGrid);

        // Add a selection model so we can select cells.
        final SelectionModel<Incident> selectionModel =
                new MultiSelectionModel<Incident>(KEY_PROVIDER);
        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
                .<Incident>createCheckboxManager());

        // Initialize the columns.
        initTableColumns(selectionModel, sortHandler);


        dataGrid.setRowData(0, incidents);
        dataGrid.setRowCount(10, true);
        //initTableColumns();
        //dataGrid.redraw();
        // Add the CellList to the adapter in the database.
        //ContactDatabase.get().addDataDisplay(dataGrid);

    }

    private void initTableColumns(final SelectionModel<Incident> selectionModel,
                                  ColumnSortEvent.ListHandler<Incident> sortHandler) {
        // private void initTableColumns () {

//			 Checkbox column. This table will uses a checkbox column for selection.
//		 Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable
//		 mouse selection.

        Column<Incident, Boolean> checkColumn =
                new Column<Incident, Boolean>(new CheckboxCell(true, false)) {
                    @Override
                    public Boolean getValue(Incident object) {
                        // Get the value from the selection model.
                        return selectionModel.isSelected(object);
                    }
                };
        //dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
        dataGrid.addColumn(checkColumn, "Выбор");
        dataGrid.setColumnWidth(checkColumn, 40, Style.Unit.PX);

        // First name.
        TextColumn<Incident> firstNameColumn =
                new TextColumn<Incident>() {
                    @Override
                    public String getValue(Incident object) {
                        //return "Value";
                        return object.getDescription();
                    }
                };
        firstNameColumn.setSortable(true);
        sortHandler.setComparator(firstNameColumn, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });

        dataGrid.addColumn(firstNameColumn, "Описание"); //todo constants
        firstNameColumn.setFieldUpdater(new FieldUpdater<Incident, String>() {
            @Override
            public void update(int index, Incident object, String value) {
                // Called when the user changes the value.
                object.setDescription(value);
                //ContactDatabase.get().refreshDisplays();
            }
        });
        dataGrid.setColumnWidth(firstNameColumn, 20, Style.Unit.PCT);

//		// Last name.
//		Column<ContactInfo, String> lastNameColumn =
//				new Column<ContactInfo, String>(new EditTextCell()) {
//					@Override
//					public String getValue(ContactInfo object) {
//						return object.getLastName();
//					}
//				};
//		lastNameColumn.setSortable(true);
//		sortHandler.setComparator(lastNameColumn, new Comparator<ContactInfo>() {
//			@Override
//			public int compare(ContactInfo o1, ContactInfo o2) {
//				return o1.getLastName().compareTo(o2.getLastName());
//			}
//		});
//		dataGrid.addColumn(lastNameColumn, constants.cwDataGridColumnLastName());
//		lastNameColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
//			@Override
//			public void update(int index, ContactInfo object, String value) {
//				// Called when the user changes the value.
//				object.setLastName(value);
//				ContactDatabase.get().refreshDisplays();
//			}
//		});
//		dataGrid.setColumnWidth(lastNameColumn, 20, Unit.PCT);
//
//		// Age.
//		Column<ContactInfo, Number> ageColumn = new Column<ContactInfo, Number>(new NumberCell()) {
//			@Override
//			public Number getValue(ContactInfo object) {
//				return object.getAge();
//			}
//		};
//		ageColumn.setSortable(true);
//		sortHandler.setComparator(ageColumn, new Comparator<ContactInfo>() {
//			@Override
//			public int compare(ContactInfo o1, ContactInfo o2) {
//				return o1.getBirthday().compareTo(o2.getBirthday());
//			}
//		});
//		Header<String> ageFooter = new Header<String>(new TextCell()) {
//			@Override
//			public String getValue() {
//				List<ContactInfo> items = dataGrid.getVisibleItems();
//				if (items.size() == 0) {
//					return "";
//				} else {
//					int totalAge = 0;
//					for (ContactInfo item : items) {
//						totalAge += item.getAge();
//					}
//					return "Avg: " + totalAge / items.size();
//				}
//			}
//		};
//		dataGrid.addColumn(ageColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant(constants
//				.cwDataGridColumnAge())), ageFooter);
//		dataGrid.setColumnWidth(ageColumn, 7, Unit.EM);
//
//		// Category.
//		final Category[] categories = ContactDatabase.get().queryCategories();
//		List<String> categoryNames = new ArrayList<String>();
//		for (Category category : categories) {
//			categoryNames.add(category.getDisplayName());
//		}
//		SelectionCell categoryCell = new SelectionCell(categoryNames);
//		Column<ContactInfo, String> categoryColumn = new Column<ContactInfo, String>(categoryCell) {
//			@Override
//			public String getValue(ContactInfo object) {
//				return object.getCategory().getDisplayName();
//			}
//		};
//		dataGrid.addColumn(categoryColumn, constants.cwDataGridColumnCategory());
//		categoryColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
//			@Override
//			public void update(int index, ContactInfo object, String value) {
//				for (Category category : categories) {
//					if (category.getDisplayName().equals(value)) {
//						object.setCategory(category);
//					}
//				}
//				ContactDatabase.get().refreshDisplays();
//			}
//		});
//		dataGrid.setColumnWidth(categoryColumn, 130, Unit.PX);
//
//		// Address.
//		Column<ContactInfo, String> addressColumn = new Column<ContactInfo, String>(new TextCell()) {
//			@Override
//			public String getValue(ContactInfo object) {
//				return object.getAddress();
//			}
//		};
//		addressColumn.setSortable(true);
//		sortHandler.setComparator(addressColumn, new Comparator<ContactInfo>() {
//			@Override
//			public int compare(ContactInfo o1, ContactInfo o2) {
//				return o1.getAddress().compareTo(o2.getAddress());
//			}
//		});
//		dataGrid.addColumn(addressColumn, constants.cwDataGridColumnAddress());
//		dataGrid.setColumnWidth(addressColumn, 60, Unit.PCT);
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
        _eventBus.fireEvent(new AddCustomerEvent(new Customer(nameTextBox.getText(), addressTextBox.getText(), nodeIdTextBox.getText(), phoneNumberTextBox.getText())));
    }

    @UiHandler("saveCustomerButton")
    void onEditdCustomerButtonClick(ClickEvent e) {
        // retrieve textbox text
        //String todoText = textBox.getText();
        // send it to controller for handle business event
        _eventBus.fireEvent(new SaveCustomerEvent(new Customer(nameTextBox.getText(), addressTextBox.getText(), nodeIdTextBox.getText(), phoneNumberTextBox.getText())));
    }


    @UiHandler("addButton")
    void onAddButtonClick(ClickEvent e) {

        // retrieve textbox text
        //String todoText = textBox.getText();
        // send it to controller for handle business event
        Incident newIncident = new Incident();
        newIncident.setIncedentStart(new Date());
        _eventBus.fireEvent(new NewIncidentEvent(newIncident));
    }

    @UiHandler("historyButton")
    void onClearButtonClick(ClickEvent e) {
        // ask controller for delete all event
        tp.selectTab(2);
        //_eventBus.fireEvent(new DeleteAllTodoEvent());
    }

    @UiHandler("loadButton")
    void onLoadButtonClick(ClickEvent e) {
        // ask controller for load event
        tp.selectTab(4); //todo comstamt
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
        } else {
            // some error handling code here
            Window.alert("К этому узлу уже присоединены, выберите другой узел для " + t.getName());
        }
    }


    public void removeCustomerFromPanel(Customer t) {
        // retrieve from the references
        CustomerWidget customerWidget = _customersWidgets.get(t.getName());
        // remove it from panel
        customersPanel.remove(customerWidget);

        _customersWidgets.remove(t.getName());
    }

    public void editCustomer(Customer t) {
        nameTextBox.setText(t.getName());
        addressTextBox.setText(t.getLocation());
        phoneNumberTextBox.setText(t.getPhoneNumber());
        nodeIdTextBox.setText(t.getNodeId());

        addCustomerButton.setVisible(false);
        saveCustomerButton.setVisible(true);
    }


    public void saveCustomer(Customer t) {
        addCustomerButton.setVisible(true);
        saveCustomerButton.setVisible(false);
        CustomerWidget customerWidget = _customersWidgets.get(t.getName());
        customerWidget.nameBox.setText(t.getName());
        customerWidget.locationBox.setText(t.getLocation());
        customerWidget.phoneNumberBox.setText(t.getPhoneNumber());
        customerWidget.nodeId.setText(t.getNodeId());
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
        } else {
            // some error handling code here
            Window.alert("Already existing Todo : " + t.getTitle());
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

    public void newIncident(IncidentWidget incidentWidget) {
        incidentPanel.clear();
        incidentPanel.add(incidentWidget);
    }

    public void newIncidentTab() {
        tp.selectTab(0);
    }
}
