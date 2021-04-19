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

package gmbh.dtap.geojson.testsupport.examples.geometrycollection;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonGeometries;
import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

@GeoJson(type = GeoJsonType.GEOMETRY_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class RouteEmpty {

   @GeoJsonGeometries
   private List<Geometry> list = new ArrayList<>();

   public List<Geometry> getList() {
      return list;
   }

   @Override public String toString() {
      return "RouteByField{" +
         "list=" + list +
         '}';
   }
}
