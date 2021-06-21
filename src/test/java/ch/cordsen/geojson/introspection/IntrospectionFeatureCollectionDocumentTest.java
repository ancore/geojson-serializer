package ch.cordsen.geojson.introspection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link IntrospectionFeatureCollectionDocument}.
 */
class IntrospectionFeatureCollectionDocumentTest {

   @Test
   void shouldReturnElementsList() {
      List<Object> list = new ArrayList<>();
      list.add("foo");
      list.add("bar");
      IntrospectionFeatureCollectionDocument document = new IntrospectionFeatureCollectionDocument(list);
      assertThat(document.getFeatures()).containsAll(list);
   }

   @Test
   void shouldReturnEmptyListWhenInitializedWithEmptyList() {
      IntrospectionFeatureCollectionDocument document = new IntrospectionFeatureCollectionDocument(Collections.emptyList());
      assertThat(document.getFeatures()).isEmpty();
   }

   @Test
   void shouldReturnNullWhenInitializedWithNull() {
      IntrospectionFeatureCollectionDocument document = new IntrospectionFeatureCollectionDocument(null);
      assertThat(document.getFeatures()).isNull();
   }
}
