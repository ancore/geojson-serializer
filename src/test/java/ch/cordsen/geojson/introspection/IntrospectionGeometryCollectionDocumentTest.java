package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.testsupport.TestUtils;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link IntrospectionGeometryCollectionDocument}.
 */
class IntrospectionGeometryCollectionDocumentTest {

   @Test
   void shouldReturnElementsList() {
      List<Geometry> list = new ArrayList<>();
      list.add(TestUtils.point(23, 42));
      list.add(TestUtils.point(42, 23));
      IntrospectionGeometryCollectionDocument document = new IntrospectionGeometryCollectionDocument(list);
      assertThat(document.getGeometries()).containsAll(list);
   }

   @Test
   void shouldReturnEmptyListWhenInitializedWithEmptyList() {
      IntrospectionGeometryCollectionDocument document = new IntrospectionGeometryCollectionDocument(Collections.emptyList());
      assertThat(document.getGeometries()).isEmpty();
   }

   @Test
   void shouldReturnNullWhenInitializedWithNull() {
      IntrospectionGeometryCollectionDocument document = new IntrospectionGeometryCollectionDocument(null);
      assertThat(document.getGeometries()).isNull();
   }
}
