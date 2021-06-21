package ch.cordsen.geojson.testsupport.examples.geometrycollection;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.annotation.GeoJsonGeometries;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>GeometryCollection</em> based on an <code>null</code> field.
 */
@GeoJson(type = GeoJsonType.GEOMETRY_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
@SuppressWarnings("ConstantConditions")
public class RouteNull {

   private final List<Geometry> list = null;

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
