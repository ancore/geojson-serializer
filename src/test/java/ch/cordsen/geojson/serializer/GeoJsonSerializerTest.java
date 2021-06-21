package ch.cordsen.geojson.serializer;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.annotation.GeoJsonId;
import ch.cordsen.geojson.document.Document;
import ch.cordsen.geojson.document.DocumentFactory;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
      assertThat(exception).hasMessage("Factory instantiation failed for class ch.cordsen.geojson.serializer.GeoJsonSerializerTest$NotInstantiableDocumentFactory");
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

      @Override public Document from(Object object) {
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

      @Override public Document from(Object object) {
         return mock(Document.class);
      }
   }

}
