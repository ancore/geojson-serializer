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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonGeometry;
import gmbh.dtap.geojson.annotation.GeoJsonId;
import gmbh.dtap.geojson.annotation.GeoJsonProperty;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.UUID.fromString;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * Tests for {@link GeoJsonSerializer}.
 *
 * @since 0.1.0
 */
public class GeoJsonSerializerTest {

   private static final Logger logger = LoggerFactory.getLogger(GeoJsonSerializerTest.class);

   private ObjectMapper objectMapper = new ObjectMapper();
   private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

   @Test
   public void testTestFeatureEntityByFields() throws IOException, URISyntaxException, JSONException {
      TestFeatureEntityByFields object = new TestFeatureEntityByFields(
            fromString("f551106e-3180-4aaa-957c-3f8457d3f942"),
            geometryFactory.createPoint(new Coordinate(23.0, 42.0)),
            "Lorem ipsum");

      String json = objectMapper.writeValueAsString(object);
      logger.info("json: {}", json);

      String expectedJson = IOUtils.toString(getClass().getResource("/TestFeatureEntity.json").toURI(), UTF_8);
      assertEquals(json, expectedJson, true);
   }

   @Test
   public void testTestFeatureEntityByMethods() throws IOException, URISyntaxException, JSONException {
      TestFeatureEntityByMethods object = new TestFeatureEntityByMethods(
            fromString("f551106e-3180-4aaa-957c-3f8457d3f942"),
            geometryFactory.createPoint(new Coordinate(23.0, 42.0)),
            "Lorem ipsum");

      String json = objectMapper.writeValueAsString(object);
      logger.info("json: {}", json);

      String expectedJson = IOUtils.toString(getClass().getResource("/TestFeatureEntity.json").toURI(), UTF_8);
      assertEquals(json, expectedJson, true);
   }

   @GeoJson(type = GeoJsonType.FEATURE)
   @JsonSerialize(using = GeoJsonSerializer.class)
   public class TestFeatureEntityByFields {

      @GeoJsonId private final UUID id;
      @GeoJsonGeometry private final Point location;
      @GeoJsonProperty private final String description;

      TestFeatureEntityByFields(UUID id, Point location, String description) {
         this.id = id;
         this.location = location;
         this.description = description;
      }

      public UUID getId() {
         return id;
      }

      public Point getLocation() {
         return location;
      }

      public String getDescription() {
         return description;
      }
   }

   @GeoJson(type = GeoJsonType.FEATURE)
   @JsonSerialize(using = GeoJsonSerializer.class)
   public class TestFeatureEntityByMethods {

      private final UUID id;
      private final Point location;
      private final String description;

      TestFeatureEntityByMethods(UUID id, Point location, String description) {
         this.id = id;
         this.location = location;
         this.description = description;
      }

      @GeoJsonId public UUID getId() {
         return id;
      }

      @GeoJsonGeometry public Point getLocation() {
         return location;
      }

      @GeoJsonProperty public String getDescription() {
         return description;
      }
   }
}
