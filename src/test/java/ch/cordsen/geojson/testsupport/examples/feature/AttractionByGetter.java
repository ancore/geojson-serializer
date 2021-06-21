package ch.cordsen.geojson.testsupport.examples.feature;

import ch.cordsen.geojson.annotation.*;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>Feature</em> based on method annotations.
 * <p>The fact that the {@link GeoJsonProperty} annotation is present once,
 * causes the serializer to create this value as JSON object.
 * <p>The GeoJSON will be:
 * <pre>
 *    {
 *       "type": "Feature",
 *       "id": "...",
 *       "geometry": {
 *          "type": "Point",
 *          "coordinates: [ ..., ...]
 *       },
 *       "properties": {
 *          "nameKey": "...",
 *          "descriptionKey": "..."
 *       }
 *    }
 * </pre>
 */
@GeoJson(type = GeoJsonType.FEATURE)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionByGetter {

   private UUID id;
   private Map<String, String> properties = new HashMap<>();
   private Point location;

   public AttractionByGetter(UUID id, String name, String description, Point location) {
      this.id = id;
      this.properties.put("nameKey", name);
      this.properties.put("descriptionKey", description);
      this.location = location;
   }

   @GeoJsonId
   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   @GeoJsonProperties
   public Map<String, String> getProperties() {
      return properties;
   }

   public void setProperties(Map<String, String> properties) {
      this.properties = properties;
   }

   @GeoJsonGeometry
   public Point getLocation() {
      return location;
   }

   public void setLocation(Point location) {
      this.location = location;
   }
}
