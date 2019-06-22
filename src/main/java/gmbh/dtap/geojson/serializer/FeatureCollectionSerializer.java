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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class FeatureCollectionSerializer {

   private static final Logger logger = LoggerFactory.getLogger(FeatureCollectionSerializer.class);

   void serialize(Object object, JsonGenerator gen, SerializerProvider provider) throws IOException {
      gen.writeStartObject();
      gen.writeObjectField("features", findFeatures(object));
      gen.writeEndObject();
   }

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
