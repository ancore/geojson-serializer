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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import gmbh.dtap.geojson.annotation.GeoJson;

import java.io.IOException;

/**
 * A {@link JsonSerializer} implementation for types annotated by {@link GeoJson}.
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
    * Checks for a class level annotation of type {@link GeoJson}.
    * Throws an exception if not present.
    *
    * @param object the object of which the class should be ckecked
    * @return the annotation
    * @throws GeoJsonSerializerException if annotation is not present
    * @since 0.2.0
    */
   private static GeoJsonType findType(Object object) {
      GeoJson geoJsonAnnotation = object.getClass().getAnnotation(GeoJson.class);
      if (geoJsonAnnotation == null) {
         throw new IllegalArgumentException("not annotated with @GeoJson");
      }
      return geoJsonAnnotation.type();
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
    */
   @Override public void serialize(Object object, JsonGenerator gen, SerializerProvider provider) throws IOException {
      GeoJsonType type = findType(object);
      switch (type) {
         case FEATURE:
            new FeatureSerializer().serialize(object, gen, provider);
            break;
         case FEATURE_COLLECTION:
            new FeatureCollectionSerializer().serialize(object, gen, provider);
            break;
         case GEOMETRY_COLLECTION:
            throw new UnsupportedOperationException("not implemented: " + type);
         default:
            throw new IllegalStateException("unexpected type: " + type);
      }
   }
}
