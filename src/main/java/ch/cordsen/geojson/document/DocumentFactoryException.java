package ch.cordsen.geojson.document;

/**
 * For exceptions thrown by a {@link DocumentFactory} implementation.
 */
public class DocumentFactoryException extends Exception {

   /**
    * {@inheritDoc}
    */
   public DocumentFactoryException(String message) {
      super(message);
   }

   /**
    * {@inheritDoc}
    */
   public DocumentFactoryException(String message, Throwable cause) {
      super(message, cause);
   }
}
