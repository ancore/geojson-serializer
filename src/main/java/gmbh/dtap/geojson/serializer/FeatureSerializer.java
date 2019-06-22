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
import com.fasterxml.jackson.databind.SerializerProvider;
import gmbh.dtap.geojson.annotation.GeoJsonId;
import gmbh.dtap.geojson.annotation.GeoJsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class FeatureSerializer {

   private static final Logger logger = LoggerFactory.getLogger(FeatureSerializer.class);

   void serialize(Object object, JsonGenerator gen, SerializerProvider provider) throws IOException {
      gen.writeStartObject();
      gen.writeStringField("type", "Feature");
      Object id = findId(object);
      if (id != null) {
         gen.writeObjectField("id", id);
      }
      gen.writeFieldName("geometry");
      new GeometrySerializer().serialize(ClassUtils.findGeometry(object), gen, provider);
      gen.writeObjectField("properties", findProperties(object));
      gen.writeEndObject();
   }

   private Object findId(Object object) {
      Member member = ClassUtils.findOne(object, GeoJsonId.class);
      if (member != null) {
         return ClassUtils.getValue(object, member, Object.class);
      }
      return null;
   }

   private Object findProperties(Object object) {
      List<Member> members = ClassUtils.scanFor(object, GeoJsonProperty.class);
      if (members.isEmpty()) {
         return null;
      } else if (members.size() == 1) {
         Member member = members.get(0);
         return toProperties(object, member);
      } else {
         return toProperties(object, members);
      }
   }

   private Object toProperties(Object object, Member member) {
      GeoJsonProperty annotation = getPropertyAnnotation(member);
      if (isNotBlank(annotation.name())) {
         // with name attribute -> map with one entry
         String name = annotation.name();
         Object value = ClassUtils.getValue(object, member, Object.class);
         return Collections.singletonMap(name, value);
      } else {
         // without name attribute -> object
         return ClassUtils.getValue(object, member, Object.class);
      }
   }

   private Object toProperties(Object object, List<Member> members) {
      Map<String, Object> properties = new HashMap<>(members.size());
      for (Member member : members) {
         String name = getName(member);
         Object value = ClassUtils.getValue(object, member, Object.class);
         properties.put(name, value);
      }
      return properties;
   }

   private String getName(Member member) {
      GeoJsonProperty annotation = getPropertyAnnotation(member);
      if (isNotBlank(annotation.name())) {
         return annotation.name();
      }
      return ClassUtils.getName(member);
   }

   private GeoJsonProperty getPropertyAnnotation(Member member) {
      if (member instanceof Field) {
         return ((Field) member).getAnnotation(GeoJsonProperty.class);
      } else if (member instanceof Method) {
         return ((Method) member).getAnnotation(GeoJsonProperty.class);
      } else {
         throw new IllegalStateException("unexpected member: " + member);
      }
   }
}
