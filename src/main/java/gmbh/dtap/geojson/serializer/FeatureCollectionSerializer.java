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
import com.fasterxml.jackson.databind.SerializerProvider;
import gmbh.dtap.geojson.annotation.GeoJsonFeatures;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Collection;

import static gmbh.dtap.geojson.serializer.ClassUtils.findOne;
import static gmbh.dtap.geojson.serializer.ClassUtils.getValue;

/**
 * This class is used by {@link GeoJsonSerializer} to serialize a <em>FeatureCollection</em>.
 *
 * @since 0.2.0
 */
@SuppressWarnings("squid:S1199")
class FeatureCollectionSerializer {

   /**
    * Serializes the features to a feature collection.
    *
    * @param object   the object of which the features are taken
    * @param gen      {@inheritDoc}
    * @param provider {@inheritDoc}
    * @throws IOException possibly thrown by JsonGenerator
    * @since 0.2.0
    */
   void serialize(Object object, JsonGenerator gen, SerializerProvider provider) throws IOException {
      // start object
      gen.writeStartObject();

      // type
      gen.writeStringField("type", "FeatureCollection");
      // features
      gen.writeObjectField("features", findFeatures(object));

      // end object
      gen.writeEndObject();
   }

   /**
    * Scans the class of the <tt>object</tt> for one annotation of type {@link GeoJsonFeatures}.
    * If present, the value of the annotated getter or field will be returned. If the value is
    * not an array or {@link Collection}, a new array with one element is created.
    *
    * @param object the object of which the class should be scanned
    * @return the array of objects, empty if no annotation is preset, never <tt>null</tt>
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   private Object[] findFeatures(Object object) {
      Member member = findOne(object, GeoJsonFeatures.class);
      if (member != null) {
         Object value = getValue(object, member, Object.class);
         if (value instanceof Object[]) {
            return (Object[]) value;
         } else if (value instanceof Collection) {
            Collection c = (Collection) value;
            return c.toArray();
         } else if (value != null) {
            return new Object[]{value};
         }
      }
      return new Object[0];
   }
}
