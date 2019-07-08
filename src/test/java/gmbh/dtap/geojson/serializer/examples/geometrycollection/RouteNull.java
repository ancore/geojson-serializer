package gmbh.dtap.geojson.serializer.examples.geometrycollection;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonGeometries;
import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@GeoJson(type = GeoJsonType.GEOMETRY_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class RouteNull {

   private List<Geometry> list = null;

   @GeoJsonGeometries
   public List<Geometry> getList() {
      return list;
   }

   @Override public String toString() {
      return "RouteByField{" +
            "list=" + list +
            '}';
   }
}
