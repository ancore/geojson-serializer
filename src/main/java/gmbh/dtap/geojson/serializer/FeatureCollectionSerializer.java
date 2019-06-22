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
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by {@link GeoJsonSerializer} to serialize a <em>FeatureCollection</em>.
 *
 * @since 0.2.0
 */
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
      gen.writeStartObject();
      gen.writeStringField("type", "FeatureCollection");
      gen.writeStartObject();
      gen.writeObjectField("features", findFeatures(object));
      gen.writeEndObject();
      gen.writeEndObject();
   }

   /**
    * TODO: refactor
    * Should scan for one occurrence only.
    * Multiple should be implemented according to GeoJsonProperty
    *
    * @param object the object of which the features are taken
    * @return the features, may be empty but never <tt>null</tt>
    * @since 0.2.0
    */
   private Object[] findFeatures(Object object) {
      List<Member> members = ClassUtils.scanFor(object, GeoJsonFeatures.class);
      List<Object> features = new ArrayList<>(members.size());
      for (Member member : members) {
         Object value = ClassUtils.getValue(object, member, Object.class);
         if (value != null) {
            features.add(value);
         }
      }
      return features.toArray();
   }
}
