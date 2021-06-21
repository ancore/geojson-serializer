package ch.cordsen.geojson.testsupport.examples.feature;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.annotation.GeoJsonGeometry;
import ch.cordsen.geojson.annotation.GeoJsonId;
import ch.cordsen.geojson.annotation.GeoJsonProperty;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Point;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>Feature</em> based on field annotations without {@link GeoJsonId}.
 */
@GeoJson(type = GeoJsonType.FEATURE)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionWithoutId {

   @GeoJsonProperty private String name;
   @GeoJsonProperty private String description;
   @GeoJsonGeometry private Point location;

   public AttractionWithoutId(String name, String description, Point location) {
      this.name = name;
      this.description = description;
      this.location = location;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Point getLocation() {
      return location;
   }

   public void setLocation(Point location) {
      this.location = location;
   }
}
