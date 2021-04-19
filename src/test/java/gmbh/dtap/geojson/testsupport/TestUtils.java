/*
 * Copyright 2019 DTAP GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gmbh.dtap.geojson.testsupport;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

/**
 * Utility class for tests.
 *
 * @since 0.2.0
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
