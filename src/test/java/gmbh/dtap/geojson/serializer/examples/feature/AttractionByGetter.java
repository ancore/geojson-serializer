/*
 * Copyright 2019 DTAP GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gmbh.dtap.geojson.serializer.examples.feature;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonGeometry;
import gmbh.dtap.geojson.annotation.GeoJsonId;
import gmbh.dtap.geojson.annotation.GeoJsonProperty;
import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;
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
 *          "name": "...",
 *          "description": "..."
 *       }
 *    }
 * </pre>
 *
 * @since 0.2.0
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

   @GeoJsonProperty
   public Map<String, String> getProperties() {
      return properties;
   }

   @GeoJsonGeometry
   public Point getLocation() {
      return location;
   }
}
