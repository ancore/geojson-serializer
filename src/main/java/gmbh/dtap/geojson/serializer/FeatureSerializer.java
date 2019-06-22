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
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gmbh.dtap.geojson.serializer.ClassUtils.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * This class is used by {@link GeoJsonSerializer} to serialize a <em>Feature</em>.
 *
 * @since 0.2.0
 */
class FeatureSerializer {

   /**
    * Serializes a feature.
    *
    * @param object   the object of which the feature is taken
    * @param gen      {@inheritDoc}
    * @param provider {@inheritDoc}
    * @throws IOException possibly thrown by JsonGenerator
    * @since 0.2.0
    */
   void serialize(Object object, JsonGenerator gen, SerializerProvider provider) throws IOException {
      // start object
      gen.writeStartObject();

      // type
      gen.writeStringField("type", "Feature");
      // id
      Object id = findId(object);
      if (id != null) {
         gen.writeObjectField("id", id);
      }
      // geometry
      gen.writeFieldName("geometry");
      Geometry geometry = findGeometry(object);
      if (geometry != null) {
         new GeometrySerializer().serialize(geometry, gen, provider);
      } else {
         gen.writeNull();
      }
      // properties
      gen.writeObjectField("properties", findProperties(object));

      // end object
      gen.writeEndObject();
   }

   /**
    * Scans the class of the <tt>object</tt> for one annotation of type {@link GeoJsonId}.
    * If present, the value of the annotated getter or field will be returned.
    *
    * @param object the object of which the class should be scanned
    * @return the object, or <tt>null</tt> if annotation is not present
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   private Object findId(Object object) {
      Member member = findOne(object, GeoJsonId.class);
      if (member != null) {
         return getValue(object, member, Object.class);
      }
      return null;
   }

   /**
    * Scans the class of the <tt>object</tt> for all annotations of type {@link GeoJsonProperty}.
    * If present, the values of the annotated getter or field will be returned
    *
    * @param object the object of which the class should be scanned
    * @return the object, or <tt>null</tt> if annotation is not present
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
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
         Object value = getValue(object, member, Object.class);
         return Collections.singletonMap(name, value);
      } else {
         // without name attribute -> object
         return getValue(object, member, Object.class);
      }
   }

   private Object toProperties(Object object, List<Member> members) {
      Map<String, Object> properties = new HashMap<>(members.size());
      for (Member member : members) {
         String name = getName(member);
         Object value = getValue(object, member, Object.class);
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
