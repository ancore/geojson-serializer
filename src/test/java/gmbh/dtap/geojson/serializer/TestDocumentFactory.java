package gmbh.dtap.geojson.serializer;

import gmbh.dtap.geojson.document.Document;
import gmbh.dtap.geojson.document.DocumentFactory;
import gmbh.dtap.geojson.document.DocumentFactoryException;

/**
 * This implementation for testing purposes returns the input object as {@link Document}.
 *
 * @since 0.4.0
 */
public class TestDocumentFactory implements DocumentFactory {

   public TestDocumentFactory() {
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override
   public Document from(Object object) throws DocumentFactoryException {
      if (object == null) {
         return null;
      }
      if (object instanceof Document) {
         return (Document) object;
      } else {
         throw new IllegalArgumentException("Object is not of type Document: " + object);
      }
   }
}
