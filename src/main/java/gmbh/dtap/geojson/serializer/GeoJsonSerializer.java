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
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.annotation.GeoJsonGeometry;
import gmbh.dtap.geojson.annotation.GeoJsonId;
import gmbh.dtap.geojson.annotation.GeoJsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link JsonSerializer} implementation for types annotated by {@link GeoJson).
 * <p>The type attribute of the {@link GeoJson} annotation defines the type of the <em>GeoJSON Object</em>.
 * <p><em>GeoJSON objects</em> require and allow additioal fields.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3" target="_blank">RFC 7946 - GeoJSON Object</a>
 * @since 0.1.0
 */
public class GeoJsonSerializer extends StdSerializer<Object> {

   private static final Logger logger = LoggerFactory.getLogger(GeoJsonSerializer.class);

   public GeoJsonSerializer() {
      super(Object.class);
   }

   public GeoJsonSerializer(Class<Object> t) {
      super(t);
   }

   public GeoJsonSerializer(JavaType type) {
      super(type);
   }

   public GeoJsonSerializer(Class<?> t, boolean dummy) {
      super(t, dummy);
   }

   public GeoJsonSerializer(StdSerializer<?> src) {
      super(src);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.1.0
    */
   @Override public void serialize(Object object, JsonGenerator gen, SerializerProvider provider) throws IOException {
      Class<?> clazz = object.getClass();
      logger.debug("clazz: {}", clazz);

      GeoJsonType type = findType(clazz);
      Object id = findId(clazz, object);
      Geometry geometry = findGeometry(clazz, object);
      Map<String, Object> properties = findProperties(clazz, object);
      logger.debug("found: type={}, id={}, geometry={}, properties={}", type, id, geometry, properties);

      gen.writeStartObject();
      gen.writeStringField("type", type.getName());
      gen.writeObjectField("id", id);
      gen.writeFieldName("geometry");
      new GeometrySerializer().serialize(geometry, gen, provider);
      gen.writeObjectField("properties", properties);
      gen.writeEndObject();
   }

   private GeoJsonType findType(Class<?> clazz) {
      GeoJson geoJsonAnnotation = clazz.getAnnotation(GeoJson.class);
      if (geoJsonAnnotation == null) {
         throw new IllegalArgumentException("not annotated with @GeoJson");
      }
      return geoJsonAnnotation.type();
   }

   private Object findId(Class<?> clazz, Object object) throws IOException {
      for (Method method : clazz.getDeclaredMethods()) {
         if (method.isAnnotationPresent(GeoJsonId.class)) {
            try {
               return method.invoke(object);
            } catch (InvocationTargetException | IllegalAccessException e) {
               throw new IOException(e);
            }
         }
      }
      for (Field field : clazz.getDeclaredFields()) {
         if (field.isAnnotationPresent(GeoJsonId.class)) {
            try {
               return FieldUtils.readField(field, object, true);
            } catch (IllegalAccessException e) {
               throw new IOException(e);
            }
         }
      }
      throw new IllegalArgumentException("missing annotation @GeoJsonId: " + clazz.getName());
   }

   private Geometry findGeometry(Class<?> clazz, Object object) throws IOException {
      for (Method method : clazz.getDeclaredMethods()) {
         if (method.isAnnotationPresent(GeoJsonGeometry.class)) {
            if (!(Geometry.class.isAssignableFrom(method.getReturnType()))) {
               throw new IllegalArgumentException("@GeoJsonGeometry method does not return type Geometry");
            }
            try {
               return (Geometry) method.invoke(object);
            } catch (InvocationTargetException | IllegalAccessException e) {
               throw new IOException(e);
            }
         }
      }
      for (Field field : clazz.getDeclaredFields()) {
         if (field.isAnnotationPresent(GeoJsonGeometry.class)) {
            if (!(Geometry.class.isAssignableFrom(field.getType()))) {
               throw new IllegalArgumentException("@GeoJsonGeometry field is not of type Geometry");
            }
            try {
               return (Geometry) FieldUtils.readField(field, object, true);
            } catch (IllegalAccessException e) {
               throw new IOException(e);
            }
         }
      }
      throw new IllegalArgumentException("missing annotation @GeoJsonGeometry: " + clazz.getName());
   }

   private Map<String, Object> findProperties(Class<?> clazz, Object object) throws IOException {
      Map<String, Object> properties = new HashMap<>();
      for (Method method : clazz.getDeclaredMethods()) {
         if (method.isAnnotationPresent(GeoJsonProperty.class)) {
            try {
               GeoJsonProperty annotation = method.getAnnotation(GeoJsonProperty.class);
               String key = annotation.name();
               if (key.isEmpty()) {
                  key = StringUtils.removeStart(method.getName(), "get").toLowerCase();
               }
               Object value = method.invoke(object);
               properties.put(key, value);
            } catch (InvocationTargetException | IllegalAccessException e) {
               throw new IOException(e);
            }
         }
      }
      for (Field field : clazz.getDeclaredFields()) {
         if (field.isAnnotationPresent(GeoJsonProperty.class)) {
            try {
               GeoJsonProperty annotation = field.getAnnotation(GeoJsonProperty.class);
               String key = annotation.name();
               if (key.isEmpty()) {
                  key = field.getName();
               }
               Object value = FieldUtils.readField(field, object, true);
               properties.put(key, value);
            } catch (IllegalAccessException e) {
               throw new IOException(e);
            }
         }
      }
      return properties;
   }
}
