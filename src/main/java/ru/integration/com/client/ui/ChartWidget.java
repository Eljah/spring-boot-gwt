package ru.integration.com.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import ru.integration.com.client.ui.charts.Colors;
import ru.integration.com.client.ui.charts.Toast;


import java.util.List;

/**
 * Created by eljah32 on 12/9/2018.
 */
public class ChartWidget extends Composite {

    private static ChartWidget.ChartWidgetUiBinder uiBinder = GWT
            .create(ChartWidget.ChartWidgetUiBinder.class);

    interface ChartWidgetUiBinder extends UiBinder<Widget, ChartWidget> {
    }

    @UiField
    LineChart chart;

    protected static final String[] MONTHS = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    protected int months = 7;


    public ChartWidget() {
        initWidget(uiBinder.createAndBindUi(this));

        chart.getOptions().setResponsive(true);
        chart.getOptions().getLegend().setPosition(Position.top);
        chart.getOptions().getTitle().setDisplay(true);
        chart.getOptions().getTitle().setText("Charba Bar Chart");

        chart.addHandler(new DatasetSelectionEventHandler() {

            @Override
            public void onSelect(DatasetSelectionEvent event) {
                AbstractChart<?, ?> chart = (AbstractChart<?, ?>) event.getSource();
                Labels labels = chart.getData().getLabels();
                List<Dataset> datasets = chart.getData().getDatasets();
                if (datasets != null && !datasets.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Dataset index: <b>").append(event.getItem().getDatasetIndex()).append("</b><br>");
                    sb.append("Dataset label: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getLabel()).append("</b><br>");
                    sb.append("Dataset data: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getData().get(event.getItem().getIndex())).append("</b><br>");
                    sb.append("Index: <b>").append(event.getItem().getIndex()).append("</b><br>");
                    sb.append("Value: <b>").append(labels.getString(event.getItem().getIndex())).append("</b><br>");
                    new Toast("Dataset Selected!", sb.toString()).show();
                }

            }
        }, DatasetSelectionEvent.TYPE);

        LineDataset dataset1 = chart.newDataset();
        dataset1.setLabel("dataset 1");

        IsColor color1 = Colors.ALL[0];

        dataset1.setBackgroundColor(color1.alpha(0.2));
        dataset1.setBorderColor(color1.toHex());
        dataset1.setBorderWidth(1);
        dataset1.setData(getRandomDigits(months));

        LineDataset dataset2 = chart.newDataset();
        dataset2.setLabel("dataset 2");

        IsColor color2 = Colors.ALL[1];

        dataset2.setBackgroundColor(color2.alpha(0.2));
        dataset2.setBorderColor(color2.toHex());
        dataset2.setBorderWidth(1);
        dataset2.setData(getRandomDigits(months));

        chart.getData().setLabels(getLabels());
        chart.getData().setDatasets(dataset1, dataset2);
    }

    protected String[] getLabels(){
        String[] values = new String[months];
        for (int i=0; i<months; i++){
            values[i] = MONTHS[i];
        }
        return values;
    }

    protected String getLabel(){
        return MONTHS[months];
    }

    protected double[] getRandomDigits(int length){
        return getRandomDigits(length, true);
    }

    protected double[] getRandomDigits(int length, boolean negative){
        double[] values = new double[length];
        for(int i=0;i<length;i++){
            if (negative){
                boolean neg = Math.random() < 0.2 ? true : false;
                values[i] = neg ? Math.round(Math.random()*-100) : Math.round(Math.random()*100);
            } else {
                values[i] = Math.round(Math.random()*100);
            }
        }
        return values;
    }
}
