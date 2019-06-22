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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import gmbh.dtap.geojson.serializer.examples.AttractionsByField;
import gmbh.dtap.geojson.serializer.examples.AttractionByField;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/**
 * Tests for {@link FeatureCollectionSerializer}.
 *
 * @since 0.2.0
 */
@SuppressWarnings("squid:S1192")
@RunWith(MockitoJUnitRunner.class)
public class FeatureCollectionSerializerTest {

   private SerializerProvider serializerProvider;
   private JsonGenerator jsonGenerator;

   private GeometryFactory geometryFactory = new GeometryFactory();
   private UUID uuid = UUID.fromString("2e38f4b1-d9ea-459a-ba52-6093b09b6dc0");

   @Before
   public void setUp() {
      serializerProvider = mock(SerializerProvider.class);
      jsonGenerator = mock(JsonGenerator.class, withSettings().verboseLogging());
   }

   @Test
   public void shouldReturnFeatureCollection() throws IOException {
      AttractionByField attraction = new AttractionByField(uuid, "Name", "Description", point(2.294527, 48.859092));

      AttractionsByField attractions = new AttractionsByField();
      attractions.add(attraction);

      FeatureCollectionSerializer featureCollectionSerializer = new FeatureCollectionSerializer();
      featureCollectionSerializer.serialize(attractions, jsonGenerator, serializerProvider);
   }

   private Point point(double x, double y) {
      return geometryFactory.createPoint(new Coordinate(x, y));
   }
}
