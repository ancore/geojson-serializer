package gmbh.dtap.geojson.introspection;

import gmbh.dtap.geojson.testsupport.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link IntrospectionFeatureDocument}.
 *
 * @since 0.5.3
 */
class IntrospectionFeatureDocumentTest {

   @Test
   void shouldReturnNullWhenInitializedWithNull() {
      IntrospectionFeatureDocument document = new IntrospectionFeatureDocument(null, null, null);
      assertThat(document.getId()).isNull();
      Assertions.<Object>assertThat(document.getGeometry()).isNull();
      assertThat(document.getProperties()).isNull();
   }

   @Test
   void shouldReturnValuesWhenInitializedWithValues() {
      Geometry geometry = TestUtils.point(23, 43);
      List<String> properties = new ArrayList<>();
      properties.add("foo");
      IntrospectionFeatureDocument document = new IntrospectionFeatureDocument("23", geometry, properties);
      assertThat(document.getId()).isEqualTo("23");
      Assertions.<Object>assertThat(document.getGeometry()).isEqualTo(geometry);
      assertThat(document.getProperties()).isEqualTo(properties);
   }
}
