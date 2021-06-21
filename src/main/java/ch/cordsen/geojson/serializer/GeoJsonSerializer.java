package ch.cordsen.geojson.serializer;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.document.*;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;

/**
 * A {@link JsonSerializer} implementation for classes annotated by {@link GeoJson}.
 * <p>The {@link GeoJson#type() type attribute} of the annotation defines the
 * output type of the <em>GeoJSON Object</em>.
 * <p>Please refer to {@link GeoJson} for a list of additional annotations per type.
 *
 * @see GeoJson
 * @see GeoJsonType
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3" target="_blank">RFC 7946 - GeoJSON Object</a>
 */
public class GeoJsonSerializer extends StdSerializer<Object> {

   /**
    * {@inheritDoc}
    */
   public GeoJsonSerializer() {
      super(Object.class);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void serialize(Object object, JsonGenerator gen, SerializerProvider provider) throws IOException {

      // find annotation on object's class
      GeoJson geoJsonAnnotation = object.getClass().getAnnotation(GeoJson.class);
      if (geoJsonAnnotation == null) {
         throw new IllegalArgumentException("Annotation @GeoJson is not present.");
      }

      // get document factory from annotation
      Class<? extends DocumentFactory> factory = geoJsonAnnotation.factory();
      DocumentFactory documentFactory;
      try {
         documentFactory = factory.getDeclaredConstructor().newInstance();
      } catch (Exception e) {
         throw new IllegalStateException("Factory instantiation failed for " + factory, e);
      }

      // call document factory to get a document representation of the annotations
      Document document;
      try {
         document = documentFactory.from(object);
      } catch (DocumentFactoryException e) {
         throw new JsonMappingException(gen, e.getMessage(), e);
      }

      // write document
      if (document instanceof FeatureDocument) {
         write((FeatureDocument) document, gen, provider);
      } else if (document instanceof FeatureCollectionDocument) {
         write((FeatureCollectionDocument) document, gen);
      } else if (document instanceof GeometryCollectionDocument) {
         write((GeometryCollectionDocument) document, gen);
      } else {
         throw new IllegalStateException("Unsupported implementation of Document: " + document.getClass());
      }
   }

   /**
    * Writes a feature document.
    *
    * @param document the feature document
    * @param gen      the generator from {@link JsonSerializer}
    * @param provider the provider from {@link JsonSerializer}
    * @throws IOException for exceptions from the generator
    */
   private void write(FeatureDocument document, JsonGenerator gen, SerializerProvider provider) throws IOException {
      gen.writeStartObject();
      gen.writeStringField("type", "Feature");
      Object id = document.getId();
      if (id != null) {
         gen.writeObjectField("id", id);
      }
      gen.writeFieldName("geometry");
      Geometry geometry = document.getGeometry();
      if (geometry != null) {
         new GeometrySerializer().serialize(geometry, gen, provider);
      } else {
         gen.writeNull();
      }
      gen.writeObjectField("properties", document.getProperties());
      gen.writeEndObject();
   }

   /**
    * Writes a feature collection document.
    *
    * @param document the feature collection document
    * @param gen      the generator from {@link JsonSerializer}
    * @throws IOException for exceptions from the generator
    */
   private void write(FeatureCollectionDocument document, JsonGenerator gen) throws IOException {
      gen.writeStartObject();
      gen.writeStringField("type", "FeatureCollection");
      gen.writeObjectField("features", document.getFeatures());
      gen.writeEndObject();
   }

   /**
    * Writes a geometry collection document.
    *
    * @param document the geometry collection document
    * @param gen      the generator from {@link JsonSerializer}
    * @throws IOException for exceptions from the generator
    */
   private void write(GeometryCollectionDocument document, JsonGenerator gen) throws IOException {
      gen.writeStartObject();
      gen.writeStringField("type", "GeometryCollection");
      gen.writeObjectField("geometries", document.getGeometries());
      gen.writeEndObject();
   }
}
