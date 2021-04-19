package gmbh.dtap.geojson.serializer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link GeoJsonType}.
 *
 * @since 0.5.3
 */
class GeoJsonTypeTest {

   @Test
   void shouldReturnTrueIfTypeIsGeometryType() {
      assertThat(GeoJsonType.GEOMETRY_COLLECTION.isGeometryType()).isTrue();
      assertThat(GeoJsonType.FEATURE.isGeometryType()).isFalse();
      assertThat(GeoJsonType.FEATURE_COLLECTION.isGeometryType()).isFalse();
   }

   @Test
   void shouldReturnNameMatchingGeoJsonName() {
      assertThat(GeoJsonType.GEOMETRY_COLLECTION.getName()).isEqualTo("GeometryCollection");
      assertThat(GeoJsonType.FEATURE.getName()).isEqualTo("Feature");
      assertThat(GeoJsonType.FEATURE_COLLECTION.getName()).isEqualTo("FeatureCollection");
   }

   @Test
   void shouldReturnToStringMatchingConstantName() {
      assertThat(GeoJsonType.GEOMETRY_COLLECTION).hasToString("GEOMETRY_COLLECTION");
      assertThat(GeoJsonType.FEATURE).hasToString("FEATURE");
      assertThat(GeoJsonType.FEATURE_COLLECTION).hasToString("FEATURE_COLLECTION");
   }
}
