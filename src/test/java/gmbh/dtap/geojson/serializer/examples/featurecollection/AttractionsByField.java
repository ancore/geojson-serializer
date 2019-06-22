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

package gmbh.dtap.geojson.serializer.examples.featurecollection;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonFeatures;
import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;
import gmbh.dtap.geojson.serializer.examples.feature.AttractionByField;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>FeatureCollection</em> based on a {@link List} field.
 *
 * @since 0.2.0
 */
@GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionsByField {

   @GeoJsonFeatures
   private final List<AttractionByField> attractions = new ArrayList<>();

   public void add(AttractionByField attraction) {
      this.attractions.add(attraction);
   }

   public List<AttractionByField> getAttractions() {
      return attractions;
   }

   @Override public String toString() {
      return "AttractionsByField{" +
            "attractions=" + attractions +
            '}';
   }
}
