package ch.cordsen.geojson.serializer;

import ch.cordsen.geojson.annotation.GeoJson;

/**
 * The constants of this enumerated type are used to specify the type of <em>GeoJson Object</em> to be generated.
 *
 * @see GeoJson
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-1.4" target="_blank">RFC 7946 - Definitions</a>
 */
public enum GeoJsonType {

   /**
    * Geometry type <em>GeometryCollection</em>
    */
   GEOMETRY_COLLECTION("GeometryCollection", true),

   /**
    * GeoJson type <em>Feature</em>
    */
   FEATURE("Feature", false),

   /**
    * GeoJson type <em>FeatureCollection</em>
    */
   FEATURE_COLLECTION("FeatureCollection", false);

   private final String name;
   private final boolean geometryType;

   GeoJsonType(String name, boolean geometryType) {
      this.name = name;
      this.geometryType = geometryType;
   }

   /**
    * Returns the name of the type according to the <em>GeoJSON</em> specification.
    *
    * @return the name of the type, e.g. "FeatureCollection" for GeoJsonType.FEATURE_COLLECTION
    */
   public String getName() {
      return name;
   }

   /**
    * Returns whether the type is a geometry type according to the <em>GeoJSON</em> specification.
    *
    * @return <code>true</code> if the type is a geometry, <code>false</code> otherwise
    */
   public boolean isGeometryType() {
      return geometryType;
   }
}
