package ch.cordsen.geojson.document;

import org.locationtech.jts.geom.Geometry;

import javax.annotation.Nullable;

/**
 * Represents a <em>GeoJSON document</em> for a <em>Feature</em>.
 *
 * @see DocumentFactory
 */
public interface FeatureDocument extends Document {

   /**
    * The optional ID.
    *
    * @return the optional ID
    */
   @Nullable Object getId();

   /**
    * The optional Geometry object.
    *
    * @return the optional geometry
    */
   @Nullable Geometry getGeometry();

   /**
    * The optional properties object.
    *
    * @return the optional properties
    */
   @Nullable Object getProperties();
}
