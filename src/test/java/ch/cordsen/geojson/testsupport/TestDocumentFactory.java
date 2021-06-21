package ch.cordsen.geojson.testsupport;

import ch.cordsen.geojson.document.Document;
import ch.cordsen.geojson.document.DocumentFactory;

/**
 * This implementation for testing purposes returns the input object as {@link Document}.
 */
public class TestDocumentFactory implements DocumentFactory {

   /**
    * Constructor for reflection only.
    */
   public TestDocumentFactory() {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Document from(Object object) {
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
