package ru.integration.com.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.*;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.options.scales.CartesianTimeAxis;
import ru.integration.com.client.ui.charts.Colors;
import ru.integration.com.client.ui.charts.Toast;


import java.util.Date;
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
    private static final long DAY = 1000 * 60 * 60 * 24;
    private static final long MINUTE = 1000 * 60;

    private static final int AMOUNT_OF_POINTS = 600;


    protected int months = 7;


    public ChartWidget() {
        initWidget(uiBinder.createAndBindUi(this));

        chart.getOptions().setResponsive(true);
        chart.getOptions().getLegend().setPosition(Position.top);
        chart.getOptions().getTitle().setDisplay(true);
        chart.getOptions().getTitle().setText("Показания приборов ТСЖ Ипподром");

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
        dataset1.setLabel("Расход электроэнергии");

        IsColor color1 = Colors.ALL[0];

        dataset1.setBackgroundColor(color1.alpha(0.2));
        dataset1.setBorderColor(color1.toHex());
        dataset1.setBorderWidth(1);
        //dataset1.setData(getRandomDigits(months));

        long time = System.currentTimeMillis();

        double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false, 900);
        DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];

        double[] xs2 = getRandomDigits(AMOUNT_OF_POINTS, false, 1200);
        DataPoint[] dp2 = new DataPoint[AMOUNT_OF_POINTS];

        for (int i=0; i<AMOUNT_OF_POINTS; i++){
            dp1[i] = new DataPoint();
            if (i == 8 || i == 7 || i == 11 || i == 12 || i == 13) {
                dp1[i].setY(Double.NaN);
            } else {
                dp1[i].setY(xs1[i]);
            }
            dp1[i].setT(new Date(time));
            dp2[i] = new DataPoint();
            if (i == 8 || i == 7 || i == 11 || i == 12 || i == 13) {
                dp2[i].setY(Double.NaN);
            } else {
                dp2[i].setY(xs2[i]);
            }
            dp2[i].setT(new Date(time));
            time = time - MINUTE;
        }
        dataset1.setDataPoints(dp1);

        LineDataset dataset2 = chart.newDataset();
        dataset2.setLabel("Расход воды");

        IsColor color2 = Colors.ALL[1];

        dataset2.setBackgroundColor(color2.alpha(0.2));
        dataset2.setBorderColor(color2.toHex());
        dataset2.setBorderWidth(1);
        //dataset2.setData(getRandomDigits(months));

        dataset2.setDataPoints(dp2);

        //chart.getData().setLabels(getLabels());

        CartesianTimeAxis axis = new CartesianTimeAxis(chart);
        axis.setDistribution(ScaleDistribution.series);
        axis.getTicks().setSource(TickSource.auto);
        //axis.getTicks().set
        //axis.getTime().setUnit(TimeUnit.day);
        axis.getTime().setUnit(false);
        axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.day, "D-MM-YYYY H:mm");
        axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.minute, "D-MM-YYYY H:mm");
        axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.month, "D-MM-YYYY H:mm");
        axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.hour, "D-MM-YYYY H:mm");
        axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.second, "D-MM-YYYY H:mm:ss");
        //axis.getTime().setStepSize(20);
        chart.getOptions().getScales().setXAxes(axis);
        chart.getData().setDatasets(dataset1, dataset2);
        Defaults.getScale().getTicks().setMin(0);
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
        return getRandomDigits(length, true, 100);
    }

    protected double[] getRandomDigits(int length, boolean negative, int base){
        double[] values = new double[length];
        for(int i=0;i<length;i++){
            if (negative){
                boolean neg = Math.random() < 0.2 ? true : false;
                values[i] = neg ? base+Math.round(Math.random()*-100) : base+Math.round(Math.random()*100);
            } else {
                values[i] = base+Math.round(Math.random()*100);
            }
        }
        return values;
    }
}
