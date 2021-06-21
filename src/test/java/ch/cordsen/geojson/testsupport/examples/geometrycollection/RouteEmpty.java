package ch.cordsen.geojson.testsupport.examples.geometrycollection;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.annotation.GeoJsonGeometries;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>GeometryCollection</em> based on an empty {@link List}.
 */
@GeoJson(type = GeoJsonType.GEOMETRY_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class RouteEmpty {

   @GeoJsonGeometries
   private final List<Geometry> list = new ArrayList<>();

   public List<Geometry> getList() {
      return list;
   }

   @Override public String toString() {
      return "RouteByField{" +
         "list=" + list +
         '}';
   }
}
