package ru.integration.com.client.ui.component;

import com.google.gwt.user.client.ui.FlowPanel;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import ru.integration.com.client.ui.MainPanel;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class MapPanel extends FlowPanel {
    String height;
    String width;

    MapPanel(){
        this("100%","100%");
    }


    MapPanel(String height, String width)
    {
        this.height=height;
        this.width=width;
        MapOptions defaultMapOptions = new MapOptions();
        MapWidget mapWidget = new MapWidget(width+"", height+"", defaultMapOptions);
        OSM osmMapnik = OSM.Mapnik("Mapnik");
        OSM osmCycle = OSM.CycleMap("CycleMap");

        osmMapnik.setIsBaseLayer(true);
        osmCycle.setIsBaseLayer(true);

        mapWidget.getMap().addLayer(osmMapnik);
        mapWidget.getMap().addLayer(osmCycle);

        LonLat lonLat = new LonLat(6.95, 50.94);
        lonLat.transform("EPSG:4326", mapWidget.getMap().getProjection()); //transform lonlat (provided in EPSG:4326) to OSM coordinate system (the map projection)
        mapWidget.getMap().setCenter(lonLat, 12);

        this.add(mapWidget);
    }
}
