package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.FeatureCollectionDocument;

import java.util.List;

/**
 * Default implementation of a {@link FeatureCollectionDocument}.
 */
public class IntrospectionFeatureCollectionDocument implements FeatureCollectionDocument {

   private final List<Object> features;

   /**
    * Constructor
    *
    * @param features the features, may be empty but not <code>null</code>
    */
   IntrospectionFeatureCollectionDocument(List<Object> features) {
      this.features = features;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Object> getFeatures() {
      return features;
   }
}
