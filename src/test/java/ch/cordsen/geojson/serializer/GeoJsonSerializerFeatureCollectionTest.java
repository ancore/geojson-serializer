package ch.cordsen.geojson.serializer;

import ch.cordsen.geojson.testsupport.TestUtils;
import ch.cordsen.geojson.testsupport.examples.feature.AttractionByField;
import ch.cordsen.geojson.testsupport.examples.featurecollection.*;
import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * Tests for <em>Feature Collection</em>.
 *
 * @see AttractionsByField
 * @see AttractionsByGetter
 * @see AttractionsEmpty
 * @see AttractionsMissing
 * @see AttractionsNull
 */
class GeoJsonSerializerFeatureCollectionTest {

   private static final UUID uuid1 = UUID.fromString("f551106e-3180-4aaa-957c-3f8457d3f942");
   private static final UUID uuid2 = UUID.fromString("71c26c20-94ec-11e9-bc42-526af7764f64");
   private static final Point location = TestUtils.point(23, 42);

   private static ObjectMapper objectMapper;

   @BeforeAll
   public static void setUp() {
      objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JtsModule());
   }

   @Test
   void shouldSerializeAttractionsByField() throws IOException, URISyntaxException, JSONException {
      AttractionsByField attractions = new AttractionsByField();
      attractions.add(new AttractionByField(uuid1, "Name 1", "Lorem ipsum 1", location));
      attractions.add(new AttractionByField(uuid2, "Name 2", "Lorem ipsum 2", null));

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/featurecollection/AttractionsByField.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attractions);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeAttractionsByGetter() throws IOException, URISyntaxException, JSONException {
      AttractionsByGetter attractions = new AttractionsByGetter();
      attractions.add(new AttractionByField(uuid1, "Name", "Lorem ipsum", location));

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/featurecollection/AttractionsByGetter.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attractions);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeEmpty() throws IOException, URISyntaxException, JSONException {
      AttractionsEmpty attractions = new AttractionsEmpty();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/featurecollection/AttractionsEmpty.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attractions);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeMissing() throws IOException, URISyntaxException, JSONException {
      AttractionsMissing attractions = new AttractionsMissing();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/featurecollection/AttractionsMissing.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attractions);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeNull() throws IOException, URISyntaxException, JSONException {
      AttractionsNull attractions = new AttractionsNull();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/featurecollection/AttractionsNull.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attractions);
      assertEquals(expectedJson, actualJson, true);
   }
}
