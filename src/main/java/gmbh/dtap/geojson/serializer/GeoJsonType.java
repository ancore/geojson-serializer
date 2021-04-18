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

package gmbh.dtap.geojson.serializer;

import gmbh.dtap.geojson.annotation.GeoJson;

/**
 * The constants of this enumerated type are used to specify the type of <em>GeoJson Object</em> to be generated.
 *
 * @see GeoJson
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-1.4" target="_blank">RFC 7946 - Definitions</a>
 * @since 0.1.0
 */
public enum GeoJsonType {

   /**
    * Geometry type <em>GeometryCollection</em>
    *
    * @since 0.1.0
    */
   GEOMETRY_COLLECTION("GeometryCollection", true),

   /**
    * GeoJson type <em>Feature</em>
    *
    * @since 0.1.0
    */
   FEATURE("Feature", false),

   /**
    * GeoJson type <em>FeatureCollection</em>
    *
    * @since 0.1.0
    */
   FEATURE_COLLECTION("FeatureCollection", false);

   private String name;
   private boolean geometryType;

   GeoJsonType(String name, boolean geometryType) {
      this.name = name;
      this.geometryType = geometryType;
   }

   /**
    * Returns the name of the type according to the <em>GeoJSON</em> specification.
    *
    * @return the name of the type, e.g. "MultiLine" for GeoJsonType.MULTI_LINE
    * @since 0.1.0
    */
   public String getName() {
      return name;
   }

   /**
    * Returns whether the type is a geometry type according to the <em>GeoJSON</em> specification.
    *
    * @return <code>true</code> if the type is a geometry, <code>false</code> otherwise
    * @since 0.1.0
    */
   public boolean isGeometryType() {
      return geometryType;
   }
}
