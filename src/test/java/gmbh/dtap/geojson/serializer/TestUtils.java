package gmbh.dtap.geojson.serializer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class TestUtils {

   private static GeometryFactory geometryFactory = new GeometryFactory();

   static Point point(double x, double y) {
      return geometryFactory.createPoint(new Coordinate(x, y));
   }
}
