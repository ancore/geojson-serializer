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

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gmbh.dtap.geojson.serializer.examples.feature.AttractionAltered;
import gmbh.dtap.geojson.serializer.examples.feature.AttractionByField;
import gmbh.dtap.geojson.serializer.examples.feature.AttractionByGetter;
import gmbh.dtap.geojson.serializer.examples.feature.AttractionMultipleGeoJsonId;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * Tests for <em>Feature</em>.
 *
 * @see AttractionAltered
 * @see AttractionByField
 * @see AttractionByGetter
 * @since 0.2.0
 */

class GeoJsonSerializerFeatureTest {

   private static final UUID uuid = UUID.fromString("f551106e-3180-4aaa-957c-3f8457d3f942");
   private static final Point location = TestUtils.point(23, 42);

   private static ObjectMapper objectMapper;

   @BeforeAll
   public static void setUp() {
      objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JtsModule());
   }

   @Test
   void shouldSerializeAttractionAltered() throws IOException, URISyntaxException, JSONException {
      AttractionAltered attraction = new AttractionAltered(uuid, "Name", "Lorem ipsum");
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/feature/AttractionAltered.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeAttractionByField() throws IOException, URISyntaxException, JSONException {
      AttractionByField attraction = new AttractionByField(uuid, "Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/feature/AttractionByField.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeAttractionByMethod() throws IOException, URISyntaxException, JSONException {
      AttractionByGetter attraction = new AttractionByGetter(uuid, "Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/feature/AttractionByMethod.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldThrowExceptionWhenMultipleGeoJsonId() throws IOException {
      AttractionMultipleGeoJsonId attraction = new AttractionMultipleGeoJsonId(uuid, "Name", "Description", location);
      JsonMappingException jsonMappingException = assertThrows(JsonMappingException.class, () -> objectMapper.writeValueAsString(attraction));
      assertThat(jsonMappingException.getMessage()).startsWith("Annotation @GeoJsonId is present multiple times");
   }
}
