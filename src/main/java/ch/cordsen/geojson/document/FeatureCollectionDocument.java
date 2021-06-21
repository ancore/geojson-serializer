package ch.cordsen.geojson.document;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents a <em>GeoJSON document</em> for a <em>FeatureCollection</em>.
 *
 * @see DocumentFactory
 */
public interface FeatureCollectionDocument extends Document {

   /**
    * The features of the collection.
    *
    * @return the features
    */
   @Nullable List<Object> getFeatures();
}
