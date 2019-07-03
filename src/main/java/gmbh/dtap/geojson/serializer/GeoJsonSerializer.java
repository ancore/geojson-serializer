/*
 * Copyright 2019 DTAP GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gmbh.dtap.geojson.serializer;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.document.*;
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
 * @since 0.1.0
 */
public class GeoJsonSerializer extends StdSerializer<Object> {

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
    */
   public GeoJsonSerializer() {
      super(Object.class);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
    */
   public GeoJsonSerializer(Class<Object> t) {
      super(t);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
    */
   public GeoJsonSerializer(JavaType type) {
      super(type);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
    */
   public GeoJsonSerializer(Class<?> t, boolean dummy) {
      super(t, dummy);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
    */
   public GeoJsonSerializer(StdSerializer<?> src) {
      super(src);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
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
         documentFactory = factory.newInstance();
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
    * @since 0.4.0
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
    * @since 0.4.0
    */
   private void write(FeatureCollectionDocument document, JsonGenerator gen) throws IOException {
      gen.writeStartObject();
      gen.writeStringField("type", "FeatureCollection");
      gen.writeObjectField("features", document.getFeatures());
      gen.writeStartObject();
   }

   /**
    * Writes a geometry collection document.
    *
    * @param document the geometry collection document
    * @param gen      the generator from {@link JsonSerializer}
    * @throws IOException for exceptions from the generator
    * @since 0.4.0
    */
   private void write(GeometryCollectionDocument document, JsonGenerator gen) throws IOException {
      gen.writeStartObject();
      gen.writeStringField("type", "GeometryCollection");
      gen.writeObjectField("geometries", document.getGeometries());
      gen.writeStartObject();
   }
}
