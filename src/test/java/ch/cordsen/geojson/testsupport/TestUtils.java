package ch.cordsen.geojson.testsupport;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

/**
 * Utility class for tests that allows easy creation of geometry objects from {@link GeometryFactory}.
 */
public class TestUtils {

   private static final GeometryFactory geometryFactory = new GeometryFactory();

   private TestUtils() {
      // static usage only
   }

   /**
    * Returns a {@link Point} with <code>x</code> and <code>y</code>.
    *
    * @param x x-coordinate (lon)
    * @param y y-coordinate (lat)
    * @return the point
    */
   public static Point point(double x, double y) {
      return geometryFactory.createPoint(new Coordinate(x, y));
   }
}
