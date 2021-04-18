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

package gmbh.dtap.geojson.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.document.DocumentFactoryException;
import gmbh.dtap.geojson.document.FeatureDocument;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

class GeoJsonSerializerTest {

   @BeforeAll
   public static void setUp() throws DocumentFactoryException {
   }

   @Test
   void test() throws JsonProcessingException, JSONException {
      String value = new ObjectMapper().writeValueAsString(new TestFeature());
      assertEquals("{\"type\":\"Feature\", \"id\": \"23\", \"geometry\": null, \"properties\": \"foo\"}", value, true);
   }

   @JsonSerialize(using = GeoJsonSerializer.class)
   @GeoJson(type = GeoJsonType.FEATURE, factory = TestDocumentFactory.class)
   static class TestFeature implements FeatureDocument {

      @Override public Object getId() {
         return "23";
      }

      @Override public Geometry getGeometry() {
         return null;
      }

      @Override public Object getProperties() {
         return "foo";
      }
   }
}
