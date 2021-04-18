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
import gmbh.dtap.geojson.annotation.GeoJsonId;
import gmbh.dtap.geojson.annotation.GeoJsonProperty;
import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;

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
 *
 * @since 0.2.0
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
