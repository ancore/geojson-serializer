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

package gmbh.dtap.geojson.introspection;

import gmbh.dtap.geojson.annotation.*;
import gmbh.dtap.geojson.document.Document;

import java.beans.IntrospectionException;
import java.util.Collection;

import static java.util.Arrays.asList;

public class Introspector {

   private static final Collection<Class<?>> memberTypes = asList(GeoJsonId.class, GeoJsonProperty.class, GeoJsonProperties.class, GeoJsonGeometry.class, GeoJsonFeatures.class);

   private Introspector() {
      // static usage only
   }

   public static Document from(Object object) throws IntrospectionException {
      // null check
      GeoJson geoJson = object.getClass().getAnnotation(GeoJson.class);
      if (geoJson == null) {
         throw new IllegalArgumentException("Annotation @GeoJson not present.");
      }
      /*
      GeoJson annotation = clazz.getAnnotation(GeoJson.class);
      AnnotatedClass annotatedClass = new AnnotatedClass(clazz, annotation);
      for (Annotated descriptor : descriptorsFrom(clazz)) {
         annotatedClass.add(descriptor);
      }
      return annotatedClass;
      */
      return null;
   }
   /*
   static List<Annotated> descriptorsFrom(Class<?> clazz) throws IntrospectionException {
      List<Annotated> descriptors = new ArrayList<>();
      for (Field field : clazz.getDeclaredFields()) {
         from(field).ifPresent(descriptors::add);
      }
      BeanInfo beanInfo = java.beans.Introspector.getBeanInfo(clazz);
      for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
         from(pd).ifPresent(descriptors::add);
      }
      return descriptors;
   }

   static Optional<AnnotatedField> from(Field field) {
      if (field != null) {
         List<Annotation> annotations = filter(field.getDeclaredAnnotations(), memberTypes);
         if (!annotations.isEmpty()) {
            return Optional.of(new AnnotatedField(field.getName(), field.getType(), field, annotations));
         }
      }
      return empty();
   }

   static Optional<AnnotatedMethod> from(PropertyDescriptor propertyDescriptor) {
      if (propertyDescriptor != null && propertyDescriptor.getReadMethod() != null) {
         List<Annotation> annotations = filter(propertyDescriptor.getReadMethod().getAnnotations(), memberTypes);
         if (!annotations.isEmpty()) {
            return Optional.of(new AnnotatedMethod(
                  propertyDescriptor.getName(),
                  propertyDescriptor.getPropertyType(),
                  propertyDescriptor.getReadMethod(),
                  annotations
            ));
         }
      }
      return empty();
   }

   static List<Annotation> filter(Annotation[] annotations, Collection<Class<?>> permittedTypes) {
      return stream(annotations)
            .filter(a -> permittedTypes.contains(a.annotationType()))
            .collect(Collectors.toList());
   }
   */
}
