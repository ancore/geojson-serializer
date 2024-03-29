package ch.cordsen.geojson.serializer;

import ch.cordsen.geojson.testsupport.TestUtils;
import ch.cordsen.geojson.testsupport.examples.feature.*;
import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * Tests for <em>Feature</em>.
 *
 * @see AttractionAltered
 * @see AttractionByField
 * @see AttractionByGetter
 * @see AttractionWithoutId
 */

class GeoJsonSerializerFeatureTest {

   private static final UUID uuid = UUID.fromString("f551106e-3180-4aaa-957c-3f8457d3f942");
   private static final Point location = TestUtils.point(23, 42);

   private static ObjectMapper objectMapper;

   @BeforeAll
   public static void setUp() {
      objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JtsModule());
   }

   @Test
   void shouldSerializeAttractionAltered() throws IOException, URISyntaxException, JSONException {
      AttractionAltered attraction = new AttractionAltered(uuid, "Name", "Lorem ipsum");
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/feature/AttractionAltered.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeAttractionByField() throws IOException, URISyntaxException, JSONException {
      AttractionByField attraction = new AttractionByField(uuid, "Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/feature/AttractionByField.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeAttractionByGetter() throws IOException, URISyntaxException, JSONException {
      AttractionByGetter attraction = new AttractionByGetter(uuid, "Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/feature/AttractionByGetter.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeAttractionWithoutId() throws IOException, URISyntaxException, JSONException {
      AttractionWithoutId attraction = new AttractionWithoutId("Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/feature/AttractionWithoutId.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldThrowExceptionWhenMultipleGeoJsonId() {
      AttractionMultipleGeoJsonId attraction = new AttractionMultipleGeoJsonId(uuid, "Name", "Description", location);
      JsonMappingException jsonMappingException = assertThrows(JsonMappingException.class, () -> objectMapper.writeValueAsString(attraction));
      assertThat(jsonMappingException.getMessage()).startsWith("Annotation @GeoJsonId is present multiple times");
   }
}
