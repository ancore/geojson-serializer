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

import gmbh.dtap.geojson.annotation.GeoJsonGeometry;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.locationtech.jts.geom.Geometry;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

/**
 * Utility class for the serializers.
 *
 * @see GeoJsonSerializer
 * @see FeatureSerializer
 * @see FeatureCollectionSerializer
 * @since 0.2.0
 */
class ClassUtils {

   private ClassUtils() {
      // static usage only
   }

   /**
    * Scans the class of the <tt>object</tt> for one annotation of type {@link GeoJsonGeometry}.
    * If present, the value of the annotated getter or field will be returned if it's an
    * implementation of {@link Geometry}.
    *
    * @param object the object of which the class should be scanned
    * @return the geometry object, or <tt>null</tt> if annotation is not present
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   static Geometry findGeometry(Object object) {
      Member member = findOne(object, GeoJsonGeometry.class);
      if (member != null) {
         Object value = getValue(object, member, Geometry.class);
         return Geometry.class.cast(value);
      }
      return null;
   }

   /**
    * Scans the class of the <tt>object</tt> for one annotation of a given type.
    *
    * @param object          the object of which the class should be scanned
    * @param annotationClass the annotation to scan for
    * @return the annotated field or getter, or <tt>null</tt> if not present
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   static Member findOne(Object object, Class<? extends Annotation> annotationClass) {
      List<Member> members = scanFor(object, annotationClass);
      if (members.isEmpty()) {
         return null;
      } else {
         if (members.size() > 1) {
            throw new GeoJsonSerializerException("annotation " + annotationClass + " found more than once in object " + object);
         }
         return members.get(0);
      }
   }

   /**
    * Scans the class of the <tt>object</tt> for all annotations of a given type.
    *
    * @param object          the object of which the class should be scanned
    * @param annotationClass the annotation to scan for
    * @return the annotated fields and getters, maybe empty but never <tt>null</tt>
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   static List<Member> scanFor(Object object, Class<? extends Annotation> annotationClass) {
      List<Member> members = new ArrayList<>();
      for (Field field : object.getClass().getDeclaredFields()) {
         if (field.isAnnotationPresent(annotationClass)) {
            members.add(field);
         }
      }
      for (PropertyDescriptor propertyDescriptor : getBeanInfo(object).getPropertyDescriptors()) {
         Method method = propertyDescriptor.getReadMethod();
         if (method.isAnnotationPresent(annotationClass)) {
            members.add(method);
         }
      }
      return members;
   }

   /**
    * Returns the name of the given field or getter.
    *
    * @param member the field or getter
    * @return the field name as is, or the getter name without get and uncapitalized
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   static String getName(Member member) {
      if (member instanceof Field) {
         return member.getName();
      } else if (member instanceof Method) {
         return uncapitalize(removeStart(member.getName(), "get"));
      } else {
         throw new IllegalStateException("unexpected member: " + member);
      }
   }

   /**
    * Returns the value of a field or getter the returned value of a getter.
    *
    * @param object        the object of which the value should be returned
    * @param member        the field getter to invoke
    * @param expectedClass the expected type of value
    * @param <T>           the class of the expected type
    * @return the value, may be <tt>null</tt>
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   static <T> T getValue(Object object, Member member, Class<T> expectedClass) {
      if (member instanceof Field) {
         return getValueFromField(object, (Field) member, expectedClass);
      } else if (member instanceof Method) {
         return getValueFromMethod(object, (Method) member, expectedClass);
      } else {
         throw new IllegalStateException("unexpected member: " + member);
      }
   }

   /**
    * Returns the value of a field.
    *
    * @param object        the object of which the value should be returned
    * @param field         the field to get the value from
    * @param expectedClass the expected type of value
    * @param <T>           the class of the expected type
    * @return the value, may be <tt>null</tt>
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   static <T> T getValueFromField(Object object, Field field, Class<T> expectedClass) {
      Object value;
      try {
         value = FieldUtils.readField(field, object, true);
      } catch (Exception e) {
         throw new GeoJsonSerializerException("value from Field failed: object=" + object + ", field=" + field, e);
      }
      if (value != null && !expectedClass.isInstance(value)) {
         throw new GeoJsonSerializerException("value from Field is not of expected type: object=" + object + ", field=" + field + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }

   /**
    * Returns the value of a getter.
    *
    * @param object        the object of which the value should be returned
    * @param method        the getter to invoke
    * @param expectedClass the expected type of value
    * @param <T>           the class of the expected type
    * @return the value, may be <tt>null</tt>
    * @throws GeoJsonSerializerException on any error
    * @since 0.2.0
    */
   static <T> T getValueFromMethod(Object object, Method method, Class<T> expectedClass) {
      Object value;
      try {
         value = method.invoke(object);
      } catch (Exception e) {
         throw new GeoJsonSerializerException("value from Method failed: object=" + object + ", method=" + method, e);
      }
      if (!expectedClass.isInstance(value)) {
         throw new GeoJsonSerializerException("value from Method is not of expected type: object=" + object + ", method=" + method + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }

   private static BeanInfo getBeanInfo(Object object) {
      try {
         return Introspector.getBeanInfo(object.getClass());
      } catch (IntrospectionException e) {
         throw new GeoJsonSerializerException("introspection of object failed: " + object, e);
      }
   }
}
