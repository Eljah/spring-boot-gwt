package ru.integration.com.client.ui.component;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import org.gwtopenmaps.openlayers.client.*;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Collection;
import org.gwtopenmaps.openlayers.client.geometry.Geometry;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import ru.integration.com.client.ui.MainPanel;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class MapPanel extends FlowPanel {
    String height;
    String width;
    MapWidget mapWidget;
    Vector vectorLayer;

    private static final Projection DEFAULT_PROJECTION = new Projection(
            "EPSG:4326");
    MapPanel() {
        this("100%", "100%");
    }


    MapPanel(String height, String width) {
        this.height = height;
        this.width = width;
        MapOptions defaultMapOptions = new MapOptions();
        mapWidget = new MapWidget(width + "", height + "", defaultMapOptions);
        OSM osmMapnik = OSM.Mapnik("Mapnik");
        OSM osmCycle = OSM.CycleMap("CycleMap");

        osmMapnik.setIsBaseLayer(true);
        osmCycle.setIsBaseLayer(true);

        mapWidget.getMap().addLayer(osmMapnik);
        mapWidget.getMap().addLayer(osmCycle);

        LonLat lonLat = new LonLat(49.049637, 55.836717);
        lonLat.transform("EPSG:4326", mapWidget.getMap().getProjection()); //transform lonlat (provided in EPSG:4326) to OSM coordinate system (the map projection)
        mapWidget.getMap().setCenter(lonLat, 16);
        vectorLayer = new Vector("Vectorlayer");
        mapWidget.getMap().addLayer(vectorLayer);

        mapWidget.getMap().addControl(new LayerSwitcher());
        this.add(mapWidget);
    }

    public void showFakeObjects() {

        //Point point = new Point(49.049637, 55.836717);
        //55.764749, 49.145908
        Point point1 = new Point(49.145908, 55.764749);
        Point point2 = new Point(49.145608, 55.764749);
        Point point3 = new Point(49.145308, 55.764749);
        Style pointStyle = new Style();
        pointStyle.setFillColor("red");
        pointStyle.setStrokeColor("green");
        pointStyle.setStrokeWidth(2);
        pointStyle.setFillOpacity(0.9);
        pointStyle.setLabelYOffset(15);
        //externalStyle.setFontWeight("bold");
        pointStyle.setFontFamily("arial");
        pointStyle.setFontSize("10pt");


        Style externalStyle1 = new Style();
        externalStyle1.setLabelYOffset(15);
        Style externalStyle2 = new Style();
        externalStyle2.setLabelYOffset(15);
        Style externalStyle3 = new Style();
        externalStyle3.setLabelYOffset(15);

        Collection point=new Collection(new Point[]{point1,point2,point3});
        VectorFeature pointFeature1 = new VectorFeature(point1, externalStyle1);
        pointFeature1.getStyle().setLabel("Узел 245");
        VectorFeature pointFeature2 = new VectorFeature(point2, externalStyle2);
        pointFeature2.getStyle().setLabel("Узел 247");
        VectorFeature pointFeature3 = new VectorFeature(point3, externalStyle3);
        pointFeature3.getStyle().setLabel("Узел 249");
        VectorFeature pointFeature = new VectorFeature(point,pointStyle);
        vectorLayer.destroyFeatures();
        LonLat newLonLat=new LonLat(49.145608, 55.764749);
        newLonLat.transform("EPSG:4326", mapWidget.getMap().getProjection()); //transform lonlat (provided in EPSG:4326) to OSM coordinate system (the map projection)
        point.transform(DEFAULT_PROJECTION,
                new Projection(mapWidget.getMap().getProjection()));
        mapWidget.getMap().setCenter(newLonLat, 16);
        vectorLayer.addFeature(pointFeature);
        vectorLayer.addFeature(pointFeature1);
        vectorLayer.addFeature(pointFeature2);
        vectorLayer.addFeature(pointFeature3);


    }
}
