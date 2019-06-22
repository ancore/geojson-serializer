package gmbh.dtap.geojson.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import gmbh.dtap.geojson.serializer.examples.AttractionAltered;
import gmbh.dtap.geojson.serializer.examples.AttractionByField;
import gmbh.dtap.geojson.serializer.examples.AttractionByMethod;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Test;
import org.locationtech.jts.geom.Point;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class GeoJsonSerializer_Attraction {

   private static final UUID uuid = UUID.fromString("f551106e-3180-4aaa-957c-3f8457d3f942");
   private static final Point location = TestUtils.point(23, 42);

   @Test
   public void shouldSerializeAttractionByField() throws IOException, URISyntaxException, JSONException {
      AttractionByField attraction = new AttractionByField(uuid, "Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/AttractionByField.json").toURI(), UTF_8);
      String actualJson = new ObjectMapper().writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   public void shouldSerializeAttractionByMethod() throws IOException, URISyntaxException, JSONException {
      AttractionByMethod attraction = new AttractionByMethod(uuid, "Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/AttractionByMethod.json").toURI(), UTF_8);
      String actualJson = new ObjectMapper().writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }

   @Test
   public void shouldSerializeAttractionAltered() throws IOException, URISyntaxException, JSONException {
      AttractionAltered attraction = new AttractionAltered(uuid, "Name", "Lorem ipsum", location);
      String expectedJson = IOUtils.toString(getClass().getResource("/examples/AttractionAltered.json").toURI(), UTF_8);
      String actualJson = new ObjectMapper().writeValueAsString(attraction);
      assertEquals(expectedJson, actualJson, true);
   }
}
