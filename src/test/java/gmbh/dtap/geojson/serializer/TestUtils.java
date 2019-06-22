package gmbh.dtap.geojson.serializer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

/**
 * Utility class for tests.
 *
 * @since 0.2.0
 */
class TestUtils {

   private static GeometryFactory geometryFactory = new GeometryFactory();

   private TestUtils() {
      // static usage only
   }

   static Point point(double x, double y) {
      return geometryFactory.createPoint(new Coordinate(x, y));
   }
}
