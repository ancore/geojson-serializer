package gmbh.dtap.geojson.serializer;

/**
 * Exception class for everything that can go wrong.
 *
 * @since 0.2.0
 */
public class GeoJsonSerializerException extends RuntimeException {

   /**
    * {@inheritDoc}
    *
    * @since 0.2.0
    */
   public GeoJsonSerializerException(String message) {
      super(message);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.2.0
    */
   public GeoJsonSerializerException(String message, Throwable cause) {
      super(message, cause);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.2.0
    */
   public GeoJsonSerializerException(Throwable cause) {
      super(cause);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.2.0
    */
   public GeoJsonSerializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
