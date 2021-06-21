package ch.cordsen.geojson.document;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.introspection.IntrospectionDocumentFactory;

/**
 * Implementing types can create {@link Document documents} from an annotated object.
 * <p>
 * The factory is interchangeable to enable mocking in tests and customizations.
 * The default implementation can replaced with annotation attribute {@link GeoJson#factory()}.
 *
 * @see IntrospectionDocumentFactory as default implementation
 */
public interface DocumentFactory {

   /**
    * Creates a <em>GeoJSON document</em> from {@link GeoJson} annotation and the complementary annotations.
    *
    * @param object the object to introspect
    * @return one of {@link FeatureDocument}, {@link FeatureCollectionDocument}, {@link GeometryCollectionDocument} depending on the attribute {@link GeoJson#type()}
    * @throws DocumentFactoryException for missing annotations, wrong types, invalid combination of annotations and such
    */
   Document from(Object object) throws DocumentFactoryException;
}
