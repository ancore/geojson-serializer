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
import com.fasterxml.jackson.databind.ObjectMapper;
import gmbh.dtap.geojson.serializer.examples.geometrycollection.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static gmbh.dtap.geojson.serializer.TestUtils.point;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * Tests for <em>GeometryCollection</em>.
 *
 * @see RouteByField
 * @see RouteByGetter
 * @since 0.5.0
 */
class GeoJsonSerializerGeometryCollectionTest {

   private static ObjectMapper objectMapper;

   @BeforeAll
   public static void setUp() {
      objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JtsModule());
   }

   @Test
   void shouldSerializeByField() throws IOException, URISyntaxException, JSONException {
      RouteByField route = new RouteByField();
      route.add(point(23, 42));
      route.add(point(122, 0));

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteByField.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeByGetter() throws IOException, URISyntaxException, JSONException {
      RouteByGetter route = new RouteByGetter();
      route.add(point(23, 42));
      route.add(point(122, 0));

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteByField.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeEmpty() throws IOException, URISyntaxException, JSONException {
      RouteEmpty route = new RouteEmpty();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteEmpty.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeMissing() throws IOException, URISyntaxException, JSONException {
      RouteMissing route = new RouteMissing();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteMissing.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeNull() throws IOException, URISyntaxException, JSONException {
      RouteNull route = new RouteNull();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteNull.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }
}
