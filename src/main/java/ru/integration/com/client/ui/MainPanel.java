package ru.integration.com.client.ui;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.*;
import com.google.inject.Binder;
import com.google.inject.Inject;

import ru.integration.com.client.event.*;
import ru.integration.com.client.event.incident.NewIncidentEvent;
import ru.integration.com.client.model.ModelHandler;
import ru.integration.com.client.resource.ApplicationResources;
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

    ListDataProvider<Incident> dataProvider;
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
       // dataGrid.setWidth("500px");
        dataGrid.setWidth("100%");
        //dataGrid.setWidth("500px");
        dataGrid.setHeight("620px");

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
        i.setConversationEnd(new Date());
        i.setReactionStart(new Date());
        i.setReactionEnd(new Date());
        i.setInformingStart(new Date());
        i.setInformingEnd(new Date());
        i.setRepairEnd(new Date());

        incidents.add(i);
        i = new Incident();
        i.setIncedentStart(new Date("1970-01-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-01-01"));
        i.setReactionStart(new Date("1970-01-02"));
        i.setReactionEnd(new Date("1970-01-02"));
        i.setInformingStart(new Date("1970-01-03"));
        i.setInformingEnd(new Date("1970-01-03"));
        i.setRepairEnd(new Date("1970-01-04"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-01-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-02-01"));
        i.setReactionStart(new Date("1970-02-02"));
        i.setReactionEnd(new Date("1970-02-02"));
        i.setInformingStart(new Date("1970-02-03"));
        i.setInformingEnd(new Date("1970-02-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-03-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-03-01"));
        i.setReactionStart(new Date("1970-03-02"));
        i.setReactionEnd(new Date("1970-03-02"));
        i.setInformingStart(new Date("1970-03-03"));
        i.setInformingEnd(new Date("1970-03-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-04-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-04-01"));
        i.setReactionStart(new Date("1970-04-02"));
        i.setReactionEnd(new Date("1970-04-02"));
        i.setInformingStart(new Date("1970-04-03"));
        i.setInformingEnd(new Date("1970-04-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-05-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-05-01"));
        i.setReactionStart(new Date("1970-05-02"));
        i.setReactionEnd(new Date("1970-05-02"));
        i.setInformingStart(new Date("1970-05-03"));
        i.setInformingEnd(new Date("1970-05-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-06-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-06-01"));
        i.setReactionStart(new Date("1970-06-02"));
        i.setReactionEnd(new Date("1970-06-02"));
        i.setInformingStart(new Date("1970-06-03"));
        i.setInformingEnd(new Date("1970-06-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-07-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-07-01"));
        i.setReactionStart(new Date("1970-07-02"));
        i.setReactionEnd(new Date("1970-07-02"));
        i.setInformingStart(new Date("1970-07-03"));
        i.setInformingEnd(new Date("1970-07-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-08-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-08-01"));
        i.setReactionStart(new Date("1970-08-02"));
        i.setReactionEnd(new Date("1970-08-02"));
        i.setInformingStart(new Date("1970-08-03"));
        i.setInformingEnd(new Date("1970-08-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-09-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-09-01"));
        i.setReactionStart(new Date("1970-09-02"));
        i.setReactionEnd(new Date("1970-09-02"));
        i.setInformingStart(new Date("1970-09-03"));
        i.setInformingEnd(new Date("1970-09-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-10-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-10-01"));
        i.setReactionStart(new Date("1970-10-02"));
        i.setReactionEnd(new Date("1970-10-02"));
        i.setInformingStart(new Date("1970-10-03"));
        i.setInformingEnd(new Date("1970-10-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-11-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-11-01"));
        i.setReactionStart(new Date("1970-11-02"));
        i.setReactionEnd(new Date("1970-11-02"));
        i.setInformingStart(new Date("1970-11-03"));
        i.setInformingEnd(new Date("1970-11-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-12-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-12-01"));
        i.setReactionStart(new Date("1970-12-02"));
        i.setReactionEnd(new Date("1970-12-02"));
        i.setInformingStart(new Date("1970-12-03"));
        i.setInformingEnd(new Date("1970-12-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-01-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-01-01"));
        i.setReactionStart(new Date("1970-01-02"));
        i.setReactionEnd(new Date("1970-01-02"));
        i.setInformingStart(new Date("1970-01-03"));
        i.setInformingEnd(new Date("1970-01-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-01-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-01-01"));
        i.setReactionStart(new Date("1970-01-02"));
        i.setReactionEnd(new Date("1970-01-02"));
        i.setInformingStart(new Date("1970-01-03"));
        i.setInformingEnd(new Date("1970-01-03"));
        incidents.add(i);

        i = new Incident();
        i.setIncedentStart(new Date("1970-01-01"));
        i.setPhoneNumber("7904764008");
        i.setDescription("Канализационные воды затопили смотровой колодец элетросетей");
        i.setConversationEnd(new Date("1970-01-01"));
        i.setReactionStart(new Date("1970-01-02"));
        i.setReactionEnd(new Date("1970-01-02"));
        i.setInformingStart(new Date("1970-01-03"));
        i.setInformingEnd(new Date("1970-01-03"));
        incidents.add(i);



        //Window.alert(incidents.size()+"");


        dataGrid.setAutoHeaderRefreshDisabled(true);

        // Set the message to display when the table is empty.
        dataGrid.setEmptyTableWidget(new Label("Нет данных"));

        dataProvider = new ListDataProvider<Incident>();
        dataProvider.setList(incidents);

        // Attach a column sort handler to the ListDataProvider to sort the list.
        ColumnSortEvent.ListHandler<Incident> sortHandler =
                new ColumnSortEvent.ListHandler<Incident>(dataProvider.getList()) {
                    @Override
                    public void onColumnSort(ColumnSortEvent event) {
                        super.onColumnSort(event);
                        dataGrid.redraw();
                    }

                };
        dataGrid.addColumnSortHandler(sortHandler);

        // Create a Pager to control the table.
        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        //SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);

        // Add a selection model so we can select cells.
        final SelectionModel<Incident> selectionModel =
                new MultiSelectionModel<Incident>(KEY_PROVIDER);
        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
                .<Incident>createCheckboxManager());

        // Initialize the columns.
        initTableColumns(selectionModel, sortHandler);


        dataGrid.setRowData(0, dataProvider.getList());
        //dataGrid.setRowCount(10, true);
        //pager.setPageSize(10);
        dataGrid.setPageSize(5);
        pager.setDisplay(dataGrid);
        //pager.setPageStart(1);
        //initTableColumns();
        //dataGrid.redraw();
        // Add the CellList to the adapter in the database.
        dataProvider.addDataDisplay(dataGrid);

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
        dataGrid.addColumn(checkColumn, "x");
        dataGrid.setColumnWidth(checkColumn, 30, Style.Unit.PX);


        DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        // Decription
        Column<Incident,Date> dateStarted =
                new Column<Incident, Date>(new DateCell(dtf)) {
                    @Override
                    public Date getValue(Incident object) {
                        //return "Value";
                        return object.getIncedentStart();
                    }
                };
        dateStarted.setSortable(true);
        sortHandler.setComparator(dateStarted, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getIncedentStart().compareTo(o2.getIncedentStart());
            }
        });

        //dataGrid.addColumn(dateStarted, "Поступление информации о проблеме"); //todo constants
        dataGrid.addColumn(dateStarted, SafeHtmlUtils.fromSafeConstant("Поступление<br/> информации<br/>о проблеме"));
//        dateStarted.setFieldUpdater(new FieldUpdater<Incident, Date>() {
//            @Override
//            public void update(int index, Incident object, Date value) {
//                // Called when the user changes the value.
//                object.setIncedentStart(value);
//                //ContactDatabase.get().refreshDisplays();
//            }
//        });
        dataGrid.setColumnWidth(dateStarted, 15, Style.Unit.PCT);

        // Decription
        Column<Incident,String> description =
                new Column<Incident,String>(new EditTextCell()) {
                    @Override
                    public String getValue(Incident object) {
                        //return "Value";
                        return object.getDescription();
                    }
                };
        description.setSortable(true);
        sortHandler.setComparator(description, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });

        dataGrid.addColumn(description, "Описание проблемы"); //todo constants
        description.setFieldUpdater(new FieldUpdater<Incident, String>() {
            @Override
            public void update(int index, Incident object, String value) {
                // Called when the user changes the value.
                object.setDescription(value);
                dataProvider.refresh();
                dataGrid.redraw();
                //ContactDatabase.get().refreshDisplays();
            }
        });
        dataGrid.setColumnWidth(description, 30, Style.Unit.PCT);

        // End
        Column<Incident,Date> conversationEnd =
                new Column<Incident, Date>(new DateCell(dtf)) {
                    @Override
                    public Date getValue(Incident object) {
                        //return "Value";
                        return object.getConversationEnd();
                    }
                };
        conversationEnd.setSortable(true);
        sortHandler.setComparator(conversationEnd, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getConversationEnd().compareTo(o2.getConversationEnd());
            }
        });

        dataGrid.addColumn(conversationEnd, SafeHtmlUtils.fromSafeConstant("Окончание<br/> приема<br/>информации <br/>о проблеме"));
        dataGrid.setColumnWidth(conversationEnd, 15, Style.Unit.PCT);

        // ReactionStart
        Column<Incident,Date> reactionStart =
                new Column<Incident, Date>(new DateCell(dtf)) {
                    @Override
                    public Date getValue(Incident object) {
                        //return "Value";
                        return object.getReactionStart();
                    }
                };
        reactionStart.setSortable(true);
        sortHandler.setComparator(reactionStart, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getReactionStart().compareTo(o2.getReactionStart());
            }
        });

        dataGrid.addColumn(reactionStart, SafeHtmlUtils.fromSafeConstant("Начало<br/> информирования<br/>бригады <br/>о проблеме"));
        dataGrid.setColumnWidth(reactionStart, 15, Style.Unit.PCT);

// ReactionStart
        Column<Incident,Date> reactionEnd =
                new Column<Incident, Date>(new DateCell(dtf)) {
                    @Override
                    public Date getValue(Incident object) {
                        //return "Value";
                        return object.getReactionEnd();
                    }
                };
        reactionEnd.setSortable(true);
        sortHandler.setComparator(reactionEnd, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getReactionEnd().compareTo(o2.getReactionEnd());
            }
        });

        dataGrid.addColumn(reactionEnd, SafeHtmlUtils.fromSafeConstant("Конец<br/> информирования<br/>бригады <br/>о проблеме"));
        dataGrid.setColumnWidth(reactionEnd, 15, Style.Unit.PCT);

        // InformingStart
        Column<Incident,Date> informingStart =
                new Column<Incident, Date>(new DateCell(dtf)) {
                    @Override
                    public Date getValue(Incident object) {
                        //return "Value";
                        return object.getInformingStart();
                    }
                };
        informingStart.setSortable(true);
        sortHandler.setComparator(informingStart, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getInformingStart().compareTo(o2.getInformingStart());
            }
        });

        dataGrid.addColumn(informingStart, SafeHtmlUtils.fromSafeConstant("Начало<br/> информирования<br/>потребителей<br/> о проблеме"));
        dataGrid.setColumnWidth(informingStart, 15, Style.Unit.PCT);

// ReactionStart
        Column<Incident,Date> informingEnd =
                new Column<Incident, Date>(new DateCell(dtf)) {
                    @Override
                    public Date getValue(Incident object) {
                        //return "Value";
                        return object.getInformingEnd();
                    }
                };
        informingEnd.setSortable(true);
        sortHandler.setComparator(informingEnd, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getInformingEnd().compareTo(o2.getInformingEnd());
            }
        });

        dataGrid.addColumn(informingEnd, SafeHtmlUtils.fromSafeConstant("Конец<br/> информирования<br/>потребителей<br/> о проблеме"));
        dataGrid.setColumnWidth(informingEnd, 15, Style.Unit.PCT);



        Column<Incident,String> nodesAffected =
                new Column<Incident, String>(new EditTextCell()) {
                    @Override
                    public String getValue(Incident object) {
                        String toReturn=object.getNodesAffected().stream().map(Object::toString).collect(Collectors.joining(", "));
                        if (toReturn!=null)
                        {
                            return  toReturn;
                        }
                        else
                        {
                            return "Нет списка узлов для инцидента";
                        }
                    }
                };
        nodesAffected.setSortable(true);
        sortHandler.setComparator(nodesAffected, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return new Integer(o1.getNodesAffected().size()).compareTo(new Integer(o2.getNodesAffected().size()));
            }
        });

        dataGrid.addColumn(nodesAffected, SafeHtmlUtils.fromSafeConstant("Узлы"));
        dataGrid.setColumnWidth(nodesAffected, 10, Style.Unit.PCT);


        Column<Incident,String> customersAffected =
                new Column<Incident, String>(new EditTextCell()) {
                    @Override
                    public String getValue(Incident object) {
                        //return "Value";
                        String toReturn=object.getCutomersAffected().stream().map(Customer::getName).collect(Collectors.joining(", "));
                        if (toReturn!=null)
                        {
                            return toReturn;
                        }
                        else
                        {
                            return "Нет списка потребителей для инцидента";
                        }
                    }
                };
        customersAffected.setSortable(true);
        sortHandler.setComparator(customersAffected, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return new Integer(o1.getCutomersAffected().size()).compareTo(new Integer(o2.getCutomersAffected().size()));
            }
        });

        dataGrid.addColumn(customersAffected, SafeHtmlUtils.fromSafeConstant("Потребители"));
        dataGrid.setColumnWidth(customersAffected, 15, Style.Unit.PCT);

        // RepairEnd
        Column<Incident,Date> repairEnd =
                new Column<Incident, Date>(new DatePickerCell(dtf)) {
                    @Override
                    public Date getValue(Incident object) {
                        //return "Value";
                        return object.getRepairEnd();
                    }
                };
        repairEnd.setSortable(true);
        sortHandler.setComparator(repairEnd, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                return o1.getRepairEnd().compareTo(o2.getRepairEnd());
            }
        });

        dataGrid.addColumn(repairEnd, SafeHtmlUtils.fromSafeConstant("Окончание<br/> работ"));
        dataGrid.setColumnWidth(repairEnd, 10, Style.Unit.PCT);


        // RepairEnd
        Column<Incident,ImageResource> wordExport =
                new Column<Incident, ImageResource>(new ImageResourceCell())
                {
                    @Override
                    public ImageResource getValue(Incident contact) {
                        ImageResource imageResource = ApplicationResources.INSTANCE.msWordIcon();
                        return imageResource;
                    }
                };

        wordExport.setSortable(false);
        dataGrid.addColumn(wordExport, SafeHtmlUtils.fromSafeConstant("Выгрузка<br/> акта"));
        dataGrid.setColumnWidth(wordExport, 10, Style.Unit.PCT);


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
