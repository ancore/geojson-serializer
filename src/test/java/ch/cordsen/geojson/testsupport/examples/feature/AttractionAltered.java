package ch.cordsen.geojson.testsupport.examples.feature;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.annotation.GeoJsonId;
import ch.cordsen.geojson.annotation.GeoJsonProperty;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>Feature</em> based on a mix of field and method annotations
 * with one altered property name.
 * <p>The fact that the {@link GeoJsonProperty} annotation is present multiple times,
 * causes the serializer to create a JSON object as <em>properties</em>.
 * <p>The GeoJSON will be:
 * <pre>
 *    {
 *       "type": "Feature",
 *       "id": "...",
 *       "geometry": null,
 *       "properties": {
 *          "name": "...",
 *          "alteredDescription": "..."
 *       }
 *    }
 * </pre>
 */
@GeoJson(type = GeoJsonType.FEATURE)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionAltered {

   @GeoJsonId private UUID id;
   @GeoJsonProperty private String name;
   private String description;

   public AttractionAltered(UUID id, String name, String description) {
      this.id = id;
      this.name = name;
      this.description = description;
   }

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @GeoJsonProperty(name = "alteredDescription")
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }
}
