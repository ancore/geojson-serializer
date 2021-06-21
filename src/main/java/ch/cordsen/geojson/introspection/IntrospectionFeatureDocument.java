package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.FeatureDocument;
import org.locationtech.jts.geom.Geometry;

/**
 * Default implementation of a {@link FeatureDocument}.
 */
public class IntrospectionFeatureDocument implements FeatureDocument {

   private final Object id;
   private final Geometry geometry;
   private final Object properties;

   /**
    * Constructor
    */
   IntrospectionFeatureDocument(Object id, Geometry geometry, Object properties) {
      this.id = id;
      this.geometry = geometry;
      this.properties = properties;
   }

   /**
    * {@inheritDoc}
    */
   @Override public Object getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override public Geometry getGeometry() {
      return geometry;
   }

   /**
    * {@inheritDoc}
    */
   @Override public Object getProperties() {
      return properties;
   }
}
