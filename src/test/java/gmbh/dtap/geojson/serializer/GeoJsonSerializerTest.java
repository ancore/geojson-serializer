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
import gmbh.dtap.geojson.annotation.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.UUID.fromString;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * Tests for {@link GeoJsonSerializer}.
 *
 * @since 0.1.0
 */
@SuppressWarnings("squid:S1192")
public class GeoJsonSerializerTest {

   private static final Logger logger = LoggerFactory.getLogger(GeoJsonSerializerTest.class);

   private ObjectMapper objectMapper = new ObjectMapper();
   private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

   // TestFeatureEntityByFields

   @GeoJson(type = GeoJsonType.FEATURE)
   @JsonSerialize(using = GeoJsonSerializer.class)
   public class TestFeatureEntityByFields {

      @GeoJsonId private final UUID id;
      @GeoJsonGeometry private final Point location;
      @GeoJsonProperty(name = "description")
      private final String description;

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

   @Test
   public void testFeatureEntityByFields() throws IOException, URISyntaxException, JSONException {
      TestFeatureEntityByFields object = new TestFeatureEntityByFields(
            fromString("f551106e-3180-4aaa-957c-3f8457d3f942"),
            geometryFactory.createPoint(new Coordinate(23.0, 42.0)),
            "Lorem ipsum");

      String json = objectMapper.writeValueAsString(object);
      logger.info("json: {}", json);

      String expectedJson = IOUtils.toString(getClass().getResource("/TestFeatureEntity.json").toURI(), UTF_8);
      assertEquals(json, expectedJson, true);
   }

   // TestFeatureEntityByMethods

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

      @GeoJsonProperty(name = "description")
      public String getDescription() {
         return description;
      }
   }

   @Test
   public void testFeatureEntityByMethods() throws IOException, URISyntaxException, JSONException {
      TestFeatureEntityByMethods object = new TestFeatureEntityByMethods(
            fromString("f551106e-3180-4aaa-957c-3f8457d3f942"),
            geometryFactory.createPoint(new Coordinate(23.0, 42.0)),
            "Lorem ipsum");

      String json = objectMapper.writeValueAsString(object);
      logger.info("json: {}", json);

      String expectedJson = IOUtils.toString(getClass().getResource("/TestFeatureEntity.json").toURI(), UTF_8);
      assertEquals(json, expectedJson, true);
   }

   // TestFeatureCollectionByField

   @GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
   @JsonSerialize(using = GeoJsonSerializer.class)
   public class TestFeatureCollectionByField {

      @GeoJsonFeatures
      private final List<TestFeatureEntityByFields> entities;

      TestFeatureCollectionByField() {
         this.entities = new ArrayList<>();
      }

      public void add(TestFeatureEntityByFields entity) {
         this.entities.add(entity);
      }

      public List<TestFeatureEntityByFields> getEntities() {
         return entities;
      }
   }

   @Test
   public void testFeatureCollectionByField() throws IOException, URISyntaxException, JSONException {
      TestFeatureEntityByFields object1 = new TestFeatureEntityByFields(
            fromString("f551106e-3180-4aaa-957c-3f8457d3f942"),
            geometryFactory.createPoint(new Coordinate(23.0, 42.0)),
            "Lorem ipsum 1");
      TestFeatureEntityByFields object2 = new TestFeatureEntityByFields(
            fromString("a0afaa49-f19d-419f-8f24-85a45029bac0"),
            geometryFactory.createPoint(new Coordinate(42.0, 23.0)),
            "Lorem ipsum 2");

      TestFeatureCollectionByField collection = new TestFeatureCollectionByField();
      collection.add(object1);
      collection.add(object2);

      String json = objectMapper.writeValueAsString(collection);
      logger.info("json: {}", json);

      String expectedJson = IOUtils.toString(getClass().getResource("/TestFeatureCollectionEntity.json").toURI(), UTF_8);
      assertEquals(json, expectedJson, true);
   }

}
