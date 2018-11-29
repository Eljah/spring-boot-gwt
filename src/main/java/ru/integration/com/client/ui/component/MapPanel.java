package ru.integration.com.client.ui.component;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import org.gwtopenmaps.openlayers.client.*;
import org.gwtopenmaps.openlayers.client.control.*;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Collection;
import org.gwtopenmaps.openlayers.client.geometry.Geometry;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.*;
import org.gwtopenmaps.openlayers.client.protocol.WFSProtocol;
import org.gwtopenmaps.openlayers.client.protocol.WFSProtocolOptions;
import org.gwtopenmaps.openlayers.client.strategy.BBoxStrategy;
import org.gwtopenmaps.openlayers.client.strategy.Strategy;
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

        OpenLayers.setProxyHost("olproxy?targetURL=");
//
//        //create some MapOptions
//        MapOptions defaultMapOptions = new MapOptions();
//        defaultMapOptions.setNumZoomLevels(16);
//
//        //Create a MapWidget
//        MapWidget mapWidget = new MapWidget("500px", "500px", defaultMapOptions);
//        //Create a WMS layer as base layer
//        WMSParams wmsParams = new WMSParams();
//        wmsParams.setFormat("image/png");
//        wmsParams.setLayers("topp:tasmania_state_boundaries");
//        wmsParams.setStyles("");
//
//        WMSOptions wmsLayerParams = new WMSOptions();
//        wmsLayerParams.setUntiled();
//        wmsLayerParams.setTransitionEffect(TransitionEffect.RESIZE);
//
//        String wmsUrl = "http://localhost:8181/geoserver/wms";
//
//        WMS wmsLayer = new WMS("Basic WMS", wmsUrl, wmsParams, wmsLayerParams);
//
//        //Add the WMS to the map
//        Map map = mapWidget.getMap();
//        map.addLayer(wmsLayer);
//
//        //Create a WFS layer
//        WFSProtocolOptions wfsProtocolOptions = new WFSProtocolOptions();
//        wfsProtocolOptions.setUrl("http://localhost:8181/geoserver/wfs");
//        wfsProtocolOptions.setFeatureType("tasmania_roads");
//        wfsProtocolOptions.setFeatureNameSpace("http://www.openplans.org/topp");
//        //if your wms is in a different projection use wfsProtocolOptions.setSrsName(LAMBERT72);
//
//        WFSProtocol wfsProtocol = new WFSProtocol(wfsProtocolOptions);
//
//        VectorOptions vectorOptions = new VectorOptions();
//        vectorOptions.setProtocol(wfsProtocol);
//        vectorOptions.setStrategies(new Strategy[]{new BBoxStrategy()});
//        //if your wms is in a different projection use vectorOptions.setProjection(LAMBERT72);
//
//
//
//        Vector wfsLayer = new Vector("wfsExample", vectorOptions);
//        map.addLayer(wfsLayer);
//
//        //Create some styles for the wfs
//        final Style normalStyle = new Style(); //the normal style
//        normalStyle.setStrokeWidth(3);
//        normalStyle.setStrokeColor("#FF0000");
//        normalStyle.setFillColor("#FFFF00");
//        normalStyle.setFillOpacity(0.8);
//        normalStyle.setStrokeOpacity(0.8);
//        final Style selectedStyle = new Style(); //the style when a feature is selected, or temporaly selected
//        selectedStyle.setStrokeWidth(3);
//        selectedStyle.setStrokeColor("#FFFF00");
//        selectedStyle.setFillColor("#FF0000");
//        selectedStyle.setFillOpacity(0.8);
//        selectedStyle.setStrokeOpacity(0.8);
//        final StyleMap styleMap = new StyleMap(normalStyle, selectedStyle,
//                selectedStyle);
//        wfsLayer.setStyleMap(styleMap);
//
//        //Create a ModifyFeature control that enables WFS modification
//        final ModifyFeatureOptions modifyFeatureControlOptions = new ModifyFeatureOptions();
//        modifyFeatureControlOptions.setMode(ModifyFeature.RESHAPE); //options are RESHAPE, RESIZE, ROTATE and DRAG
//        final ModifyFeature modifyFeatureControl = new ModifyFeature(wfsLayer,
//                modifyFeatureControlOptions);
//
//        map.addControl(modifyFeatureControl);
//        modifyFeatureControl.activate();

        /*
         * Note that for saving back to the WFS you can use
         * final SaveStrategy saveStrategy = new SaveStrategy();
         * saveStrategy.setAuto(true);
         * vectorOptions.setStrategies(new Strategy[] {new BBoxStrategy(), saveStrategy }); // (instead of only BBOXStrategy)
         */

//        //Lets add some default controls to the map
//        map.addControl(new LayerSwitcher()); //+ sign in the upperright corner to display the layer switcher
//        map.addControl(new OverviewMap()); //+ sign in the lowerright to display the overviewmap
//        map.addControl(new ScaleLine()); //Display the scaleline
//
//        //Center and zoom to a location
//        map.setCenter(new LonLat(146.7, -41.8), 6);
//
//
//        mapWidget.getElement().getFirstChildElement().getStyle().setZIndex(0); //force the map to fall behind popups


        MapOptions defaultMapOptions = new MapOptions();
        mapWidget = new MapWidget(width + "", height + "", defaultMapOptions);
        OSM osmMapnik = OSM.Mapnik("Mapnik");
        OSM osmCycle = OSM.CycleMap("CycleMap");

        //WMS etomesto=new WMS("http://map.etomesto.ru/kazan/1887/");
        //http://map.etomesto.ru/kazan/1887/

        osmMapnik.setIsBaseLayer(true);
        osmCycle.setIsBaseLayer(true);

        mapWidget.getMap().addLayer(osmMapnik);
        mapWidget.getMap().addLayer(osmCycle);


        WMSParams wmsParams = new WMSParams();
        wmsParams.setFormat("image/png");
        wmsParams.setLayers("topp:tasmania_state_boundaries");
        wmsParams.setStyles("");

        WMSOptions wmsLayerParams = new WMSOptions();
        wmsLayerParams.setUntiled();
        wmsLayerParams.setTransitionEffect(TransitionEffect.RESIZE);
        wmsLayerParams.setIsBaseLayer(false);
        wmsLayerParams.setDisplayOutsideMaxExtent(true);

        String wmsUrl = "http://localhost:8181/geoserver/topp/wms";
        //String wmsUrl = "http://map.etomesto.ru/kazan/1887/";

        WMS wmsLayer = new WMS("Basic WMS", wmsUrl, wmsParams, wmsLayerParams);

        //Add the WMS to the map
        Map map = mapWidget.getMap();
        map.addLayer(wmsLayer);

        //Create a WFS layer
        WFSProtocolOptions wfsProtocolOptions = new WFSProtocolOptions();
        wfsProtocolOptions.setUrl("http://localhost:8181/geoserver/wfs");
        wfsProtocolOptions.setFeatureType("tasmania_roads");
        wfsProtocolOptions.setFeatureNameSpace("http://www.openplans.org/topp");
        //if your wms is in a different projection use wfsProtocolOptions.setSrsName(LAMBERT72);

        WFSProtocol wfsProtocol = new WFSProtocol(wfsProtocolOptions);

        VectorOptions vectorOptions = new VectorOptions();
        vectorOptions.setProtocol(wfsProtocol);
        vectorOptions.setStrategies(new Strategy[]{new BBoxStrategy()});
        //if your wms is in a different projection use vectorOptions.setProjection(LAMBERT72);



        Vector wfsLayer = new Vector("wfsExample", vectorOptions);
        map.addLayer(wfsLayer);



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
