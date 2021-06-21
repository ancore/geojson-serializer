package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.Document;
import ch.cordsen.geojson.document.DocumentFactoryException;
import ch.cordsen.geojson.document.FeatureDocument;
import ch.cordsen.geojson.testsupport.TestUtils;
import ch.cordsen.geojson.testsupport.examples.feature.AttractionAltered;
import ch.cordsen.geojson.testsupport.examples.feature.AttractionByField;
import ch.cordsen.geojson.testsupport.examples.feature.AttractionByGetter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link IntrospectionDocumentFactory}.
 */
class IntrospectionDocumentFactoryTest {

   private static final UUID uuid = UUID.fromString("f551106e-3180-4aaa-957c-3f8457d3f942");
   private static final Point location = TestUtils.point(23, 42);

   @Test
   void shouldIntrospectAttractionAltered() throws DocumentFactoryException {
      IntrospectionDocumentFactory introspectionDocumentFactory = new IntrospectionDocumentFactory();

      Map<String, String> expectedProperties = new HashMap<>();
      expectedProperties.put("name", "Name");
      expectedProperties.put("alteredDescription", "Lorem ipsum");

      AttractionAltered attraction = new AttractionAltered(uuid, "Name", "Lorem ipsum");
      Document document = introspectionDocumentFactory.from(attraction);
      assertThat(document).isInstanceOf(FeatureDocument.class);

      FeatureDocument featureDocument = (FeatureDocument) document;
      assertThat(featureDocument.getId()).isEqualTo(uuid);
      Assertions.<Object>assertThat(featureDocument.getGeometry()).isNull();
      assertThat(featureDocument.getProperties()).isInstanceOf(Map.class);
      assertThat(featureDocument.getProperties()).isEqualTo(expectedProperties);
   }

   @Test
   void shouldIntrospectAttractionByField() throws DocumentFactoryException {
      IntrospectionDocumentFactory introspectionDocumentFactory = new IntrospectionDocumentFactory();

      Map<String, String> expectedProperties = new HashMap<>();
      expectedProperties.put("name", "Name");
      expectedProperties.put("description", "Lorem ipsum");

      AttractionByField attraction = new AttractionByField(uuid, "Name", "Lorem ipsum", location);
      Document document = introspectionDocumentFactory.from(attraction);
      assertThat(document).isInstanceOf(FeatureDocument.class);

      FeatureDocument featureDocument = (FeatureDocument) document;
      assertThat(featureDocument.getId()).isEqualTo(uuid);
      Assertions.<Object>assertThat(featureDocument.getGeometry()).isEqualTo(location);
      assertThat(featureDocument.getProperties()).isInstanceOf(Map.class);
      assertThat(featureDocument.getProperties()).isEqualTo(expectedProperties);
   }

   @Test
   void shouldIntrospectAttractionByMethod() throws DocumentFactoryException {
      IntrospectionDocumentFactory introspectionDocumentFactory = new IntrospectionDocumentFactory();

      Map<String, String> expectedProperties = new HashMap<>();
      expectedProperties.put("nameKey", "Name");
      expectedProperties.put("descriptionKey", "Lorem ipsum");

      AttractionByGetter attraction = new AttractionByGetter(uuid, "Name", "Lorem ipsum", location);
      Document document = introspectionDocumentFactory.from(attraction);
      assertThat(document).isInstanceOf(FeatureDocument.class);

      FeatureDocument featureDocument = (FeatureDocument) document;
      assertThat(featureDocument.getId()).isEqualTo(uuid);
      Assertions.<Object>assertThat(featureDocument.getGeometry()).isEqualTo(location);
      assertThat(featureDocument.getProperties()).isInstanceOf(Map.class);
      assertThat(featureDocument.getProperties()).isEqualTo(expectedProperties);
   }
}
