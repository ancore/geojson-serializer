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

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonId;
import gmbh.dtap.geojson.document.Document;
import gmbh.dtap.geojson.document.DocumentFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link GeoJsonSerializer}.
 */
class GeoJsonSerializerTest {

   @Test
   void shouldThrowExceptionWhenAnnotationIsMissing() {
      JsonMappingException exception = assertThrows(JsonMappingException.class,
         () -> new ObjectMapper().writeValueAsString(new ClassWithoutGeoJson()));
      assertThat(exception).hasMessage("Annotation @GeoJson is not present.");
   }

   @Test
   void shouldThrowExceptionWhenDocumentFactoryIsNotInstantiable() {
      JsonMappingException exception = assertThrows(JsonMappingException.class,
         () -> new ObjectMapper().writeValueAsString(new ClassWithInvalidDocumentFactory()));
      assertThat(exception).hasMessage("Factory instantiation failed for class gmbh.dtap.geojson.serializer.GeoJsonSerializerTest$NotInstantiableDocumentFactory");
   }

   @Test
   void shouldThrowExceptionWhenDocument() {
      JsonMappingException exception = assertThrows(JsonMappingException.class,
         () -> new ObjectMapper().writeValueAsString(new ClassWithMockedDocumentFactory()));
      assertThat(exception).hasMessageStartingWith("Unsupported implementation of Document:");
   }

   @JsonSerialize(using = GeoJsonSerializer.class)
   static class ClassWithoutGeoJson {

      @GeoJsonId private UUID id;

      public UUID getId() {
         return id;
      }

      public void setId(UUID id) {
         this.id = id;
      }
   }

   @GeoJson(type = GeoJsonType.FEATURE, factory = NotInstantiableDocumentFactory.class)
   @JsonSerialize(using = GeoJsonSerializer.class)
   static class ClassWithInvalidDocumentFactory {

      @GeoJsonId private UUID id;

      public UUID getId() {
         return id;
      }

      public void setId(UUID id) {
         this.id = id;
      }
   }

   static class NotInstantiableDocumentFactory implements DocumentFactory {

      public NotInstantiableDocumentFactory(String reflectionNotPossible) {
      }

      @Override public Document from(Object object)  {
         return null;
      }
   }


   @GeoJson(type = GeoJsonType.FEATURE, factory = MockedDocumentFactory.class)
   @JsonSerialize(using = GeoJsonSerializer.class)
   static class ClassWithMockedDocumentFactory {

      @GeoJsonId private UUID id;

      public UUID getId() {
         return id;
      }

      public void setId(UUID id) {
         this.id = id;
      }
   }

   static class MockedDocumentFactory implements DocumentFactory {

      @Override public Document from(Object object)  {
         return mock(Document.class);
      }
   }

}
