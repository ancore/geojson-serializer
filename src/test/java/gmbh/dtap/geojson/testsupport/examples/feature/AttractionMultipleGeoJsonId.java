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

package gmbh.dtap.geojson.testsupport.examples.feature;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonGeometry;
import gmbh.dtap.geojson.annotation.GeoJsonId;
import gmbh.dtap.geojson.annotation.GeoJsonProperty;
import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

/**
 * Class with erroneous annotations, {@link GeoJsonId} is present multiple times.
 *
 * @since 0.2.0
 */
@GeoJson(type = GeoJsonType.FEATURE)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionMultipleGeoJsonId {

   @GeoJsonId private UUID id;
   @GeoJsonProperty private String name;
   @GeoJsonProperty private String description;
   @GeoJsonGeometry private Point location;

   public AttractionMultipleGeoJsonId(UUID id, String name, String description, Point location) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.location = location;
   }

   @GeoJsonId
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
