package ch.cordsen.geojson.serializer;

import ch.cordsen.geojson.testsupport.TestUtils;
import ch.cordsen.geojson.testsupport.examples.geometrycollection.*;
import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * Tests for <em>GeometryCollection</em>.
 *
 * @see RouteByField
 * @see RouteByGetter
 * @see RouteEmpty
 * @see RouteMissing
 * @see RouteNull
 */
class GeoJsonSerializerGeometryCollectionTest {

   private static ObjectMapper objectMapper;

   @BeforeAll
   public static void setUp() {
      objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JtsModule());
   }

   @Test
   void shouldSerializeByField() throws IOException, URISyntaxException, JSONException {
      RouteByField route = new RouteByField();
      route.add(TestUtils.point(23, 42));
      route.add(TestUtils.point(122, 0));

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteByField.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeByGetter() throws IOException, URISyntaxException, JSONException {
      RouteByGetter route = new RouteByGetter();
      route.add(TestUtils.point(23, 42));
      route.add(TestUtils.point(122, 0));

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteByField.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeEmpty() throws IOException, URISyntaxException, JSONException {
      RouteEmpty route = new RouteEmpty();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteEmpty.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeMissing() throws IOException, URISyntaxException, JSONException {
      RouteMissing route = new RouteMissing();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteMissing.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   void shouldSerializeNull() throws IOException, URISyntaxException, JSONException {
      RouteNull route = new RouteNull();

      String expectedJson = IOUtils.toString(getClass().getResource("/examples/geometrycollection/RouteNull.json").toURI(), UTF_8);
      String actualJson = objectMapper.writeValueAsString(route);
      assertEquals(expectedJson, actualJson, true);
   }
}
