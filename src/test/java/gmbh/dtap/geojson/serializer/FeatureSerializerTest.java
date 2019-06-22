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
import gmbh.dtap.geojson.serializer.examples.AttractionByField;
import gmbh.dtap.geojson.serializer.examples.AttractionMultipleGeoJsonId;
import gmbh.dtap.geojson.serializer.examples.AttractionNoGeoJsonId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static gmbh.dtap.geojson.serializer.TestUtils.point;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Tests for {@link FeatureSerializerTest}.
 *
 * @since 0.2.0
 */
@SuppressWarnings("squid:S1192")
public class FeatureSerializerTest {

   @Rule public ExpectedException exceptionRule = ExpectedException.none();

   @Captor private ArgumentCaptor<String> fieldNameCaptor;
   @Captor private ArgumentCaptor<Object> pojoCaptor;

   private SerializerProvider serializerProvider;
   private JsonGenerator jsonGenerator;

   private UUID uuid = UUID.fromString("2e38f4b1-d9ea-459a-ba52-6093b09b6dc0");

   @Before
   public void setUp() {
      serializerProvider = mock(SerializerProvider.class);
      jsonGenerator = mock(JsonGenerator.class, withSettings().verboseLogging());
   }

   @Test
   public void shouldReturnFeatureWithIdAndGeometryAndProperties() throws IOException {
      AttractionByField attraction = new AttractionByField(uuid, "Name", "Description", point(2.294527, 48.859092));

      FeatureSerializer featureSerializer = new FeatureSerializer();
      featureSerializer.serialize(attraction, jsonGenerator, serializerProvider);

      verify(jsonGenerator, times(3)).writeObjectField(fieldNameCaptor.capture(), pojoCaptor.capture());

      assertThat(fieldNameCaptor.getAllValues()).containsExactly("id", "geometry", "proerties");
      assertThat(pojoCaptor.getAllValues()).containsExactly(uuid, attraction.getLocation(), Collections.emptyMap());
   }

   @Test
   public void shouldReturnFeatureWithNullIdGeometryAndProperties() throws IOException {
      AttractionNoGeoJsonId attraction = new AttractionNoGeoJsonId(uuid, "Name", "Description", point(2.294527, 48.859092));
      FeatureSerializer featureSerializer = new FeatureSerializer();
      featureSerializer.serialize(attraction, jsonGenerator, serializerProvider);
   }

   @Test
   public void shouldThrowExceptionWhenMultipleGeoJsonId() throws IOException {
      exceptionRule.expect(GeoJsonSerializerException.class);
      exceptionRule.expectMessage("more than one annotation found: interface gmbh.dtap.geojson.annotation.GeoJsonId");

      AttractionMultipleGeoJsonId attraction = new AttractionMultipleGeoJsonId(uuid, "Name", "Description", point(2.294527, 48.859092));
      FeatureSerializer featureSerializer = new FeatureSerializer();
      featureSerializer.serialize(attraction, jsonGenerator, serializerProvider);
   }
}
