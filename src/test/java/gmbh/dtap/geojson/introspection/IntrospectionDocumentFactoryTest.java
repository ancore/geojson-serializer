package gmbh.dtap.geojson.introspection;

import gmbh.dtap.geojson.document.Document;
import gmbh.dtap.geojson.document.DocumentFactoryException;
import gmbh.dtap.geojson.document.FeatureDocument;
import gmbh.dtap.geojson.testsupport.TestUtils;
import gmbh.dtap.geojson.testsupport.examples.feature.AttractionAltered;
import gmbh.dtap.geojson.testsupport.examples.feature.AttractionByField;
import gmbh.dtap.geojson.testsupport.examples.feature.AttractionByMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link IntrospectionDocumentFactory}.
 *
 * @since 0.5.3
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
   @Disabled("possible bug")
   void shouldIntrospectAttractionByMethod() throws DocumentFactoryException {
      IntrospectionDocumentFactory introspectionDocumentFactory = new IntrospectionDocumentFactory();

      Map<String, String> expectedProperties = new HashMap<>();
      expectedProperties.put("nameKey", "Name");
      expectedProperties.put("descriptionKey", "Lorem ipsum");

      AttractionByMethod attraction = new AttractionByMethod(uuid, "Name", "Lorem ipsum", location);
      Document document = introspectionDocumentFactory.from(attraction);
      assertThat(document).isInstanceOf(FeatureDocument.class);

      FeatureDocument featureDocument = (FeatureDocument) document;
      assertThat(featureDocument.getId()).isEqualTo(uuid);
      Assertions.<Object>assertThat(featureDocument.getGeometry()).isEqualTo(location);
      assertThat(featureDocument.getProperties()).isInstanceOf(Map.class);
      assertThat(featureDocument.getProperties()).isEqualTo(expectedProperties);
   }
}
