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

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import gmbh.dtap.geojson.annotation.*;
import gmbh.dtap.geojson.document.*;
import org.locationtech.jts.geom.Geometry;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.joining;

/**
 * Default implementation of a {@link DocumentFactory} that introspects the
 * object's class for annotations and retrieves the values of the annotated
 * fields and methods.
 *
 * @since 0.4.0
 */
public class IntrospectionDocumentFactory implements DocumentFactory {

   /**
    * All annotations to search for on fields and methods (bean properties).
    *
    * @since 0.4.0
    */
   private static final Collection<Class<?>> annotationClasses = asList(GeoJsonId.class, GeoJsonProperty.class, GeoJsonProperties.class, GeoJsonGeometry.class, GeoJsonFeatures.class);

   /**
    * For reflection only.
    *
    * @since 0.4.0
    */
   public IntrospectionDocumentFactory() {
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override
   public Document from(Object object) throws DocumentFactoryException {
      if (object == null) {
         throw new DocumentFactoryException("Object is null.");
      }
      GeoJson geoJsonAnnotation = object.getClass().getAnnotation(GeoJson.class);
      if (geoJsonAnnotation == null) {
         throw new DocumentFactoryException("Annotation @GeoJson not present.");
      }
      switch (geoJsonAnnotation.type()) {
         case FEATURE:
            return featureFrom(object);
         case FEATURE_COLLECTION:
            return featureCollectionFrom(object);
         case GEOMETRY_COLLECTION:
            return geometryCollectionFrom(object);
         default:
            throw new DocumentFactoryException("Unsupported GeoJsonType: " + geoJsonAnnotation.type());
      }
   }

   /**
    * Returns a document representation of the object.
    *
    * @param object the object
    * @return the feature document
    * @throws DocumentFactoryException on any error
    * @since 0.4.0
    */
   private FeatureDocument featureFrom(Object object) throws DocumentFactoryException {
      ListMultimap<Class<? extends Annotation>, Annotated> index = index(object);

      IntrospectionFeatureDocument document = new IntrospectionFeatureDocument();

      Annotated idAnnotated = oneOrNull(index, GeoJsonId.class);
      if (idAnnotated != null) {
         document.setId(idAnnotated.getValue(object, Object.class));
      }

      Annotated geometryAnnotated = oneOrNull(index, GeoJsonGeometry.class);
      if (geometryAnnotated != null) {
         document.setGeometry(geometryAnnotated.getValue(object, Geometry.class));
      }

      Annotated propertiesAnnotated = oneOrNull(index, GeoJsonProperties.class);
      List<Annotated> propertyAnnotations = index.get(GeoJsonProperty.class);
      if (propertiesAnnotated != null && !propertyAnnotations.isEmpty()) {
         // both, @GeoJsonProperties and @GeoJsonProperty are present
         String descriptions = propertiesAnnotated.getDescription() + ", " +
               propertyAnnotations.stream().map(Annotated::getDescription).collect(joining(", "));
         throw new DocumentFactoryException("Annotations @GeoJsonProperties and @GeoJsonProperty are mutually exclusive: " + descriptions);
      } else if (propertiesAnnotated != null) {
         // one @GeoJsonProperties
         document.setProperties(toProperties(object, propertiesAnnotated));
      } else if (!propertyAnnotations.isEmpty()) {
         // one or more @GeoJsonProperty
         document.setProperties(toProperties(object, propertyAnnotations));
      }
      return document;
   }

   private Object toProperties(Object object, Annotated annotated) throws DocumentFactoryException {
      // TODO: isNotBlank on the one annotation.name() not annotated!
//      if (isNotBlank(annotated.getName())) {
//         // with name attribute -> map with one entry
//         String name = annotated.getName();
//         Object value = annotated.getValue(object, Object.class);
//         return Collections.singletonMap(name, value);
//      } else {
      // without name attribute -> object
      return annotated.getValue(object, Object.class);
//      }
   }

   private Object toProperties(Object object, List<Annotated> annotateds) throws DocumentFactoryException {
      Map<String, Object> properties = new HashMap<>(annotateds.size());
      for (Annotated annotated : annotateds) {
         String name = annotated.getName();
         // TODO: possibly overwrite from the one annotation.name()
         Object value = annotated.getValue(object, Object.class);
         properties.put(name, value);
      }
      return properties;
   }

   /**
    * Returns a document representation of the object.
    *
    * @param object the object
    * @return the feature collection document
    * @throws DocumentFactoryException on any error
    * @since 0.4.0
    */
   private FeatureCollectionDocument featureCollectionFrom(Object object) throws DocumentFactoryException {
      ListMultimap<Class<? extends Annotation>, Annotated> index = index(object);
      List<Object> features = findFeatures(index, object);
      return new IntrospectionFeatureCollectionDocument(features);
   }

   private List<Object> findFeatures(ListMultimap<Class<? extends Annotation>, Annotated> index, Object object) throws DocumentFactoryException {
      Annotated annotated = oneOrNull(index, GeoJsonFeatures.class);
      if (annotated != null) {
         Object value = annotated.getValue(object, Object.class);
         if (value instanceof Object[]) {
            return asList(((Object[]) value));
         } else if (value instanceof Collection) {
            return new ArrayList<Object>((Collection) value);
         } else if (value != null) {
            return singletonList(value);
         }
      }
      return emptyList();
   }

   /**
    * Returns a document representation of the object.
    *
    * @param object the object
    * @return the geometry collection document
    * @throws DocumentFactoryException on any error
    * @since 0.4.0
    */
   private GeometryCollectionDocument geometryCollectionFrom(Object object) throws DocumentFactoryException {
      ListMultimap<Class<? extends Annotation>, Annotated> index = index(object);

      // TODO: implement
      return new IntrospectionGeometryCollectionDocument(emptyList());
   }

   /* --------------------------------- */

   private Annotated oneOrNull(ListMultimap<Class<? extends Annotation>, Annotated> index, Class<? extends Annotation> annotationClass) throws DocumentFactoryException {
      List<Annotated> annotated = index.get(annotationClass);
      if (annotated.isEmpty()) {
         return null;
      } else if (annotated.size() > 1) {
         String descriptions = annotated.stream().map(Annotated::getDescription).collect(joining(", "));
         throw new DocumentFactoryException("Annotation @" + annotationClass.getSimpleName() + " is present multiple times: " + descriptions);
      } else {
         return annotated.get(0);
      }
   }

   private ListMultimap<Class<? extends Annotation>, Annotated> index(Object object) throws DocumentFactoryException {
      ListMultimap<Class<? extends Annotation>, Annotated> index = MultimapBuilder.hashKeys().arrayListValues().build();
      annotatedFrom(object.getClass())
            .forEach(annotated -> annotated.getAnnotations()
                  .forEach(t -> index.put(t.annotationType(), annotated)));
      return index;
   }

   private static List<Annotated> annotatedFrom(Class<?> clazz) throws DocumentFactoryException {
      List<Annotated> annotated = new ArrayList<>();
      for (Field field : clazz.getDeclaredFields()) {
         from(field).ifPresent(annotated::add);
      }
      BeanInfo beanInfo = getBeanInfo(clazz);
      for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
         from(pd).ifPresent(annotated::add);
      }
      return annotated;
   }

   private static BeanInfo getBeanInfo(Class clazz) throws DocumentFactoryException {
      try {
         return Introspector.getBeanInfo(clazz);
      } catch (IntrospectionException e) {
         throw new DocumentFactoryException("BeanInfo retrieval failed.", e);
      }
   }

   private static Optional<AnnotatedField> from(Field field) {
      if (field != null) {
         List<Annotation> annotations = filter(field.getDeclaredAnnotations(), annotationClasses);
         if (!annotations.isEmpty()) {
            return Optional.of(new AnnotatedField(field.getName(), field, annotations));
         }
      }
      return empty();
   }

   private static Optional<AnnotatedMethod> from(PropertyDescriptor propertyDescriptor) {
      if (propertyDescriptor != null && propertyDescriptor.getReadMethod() != null) {
         List<Annotation> annotations = filter(propertyDescriptor.getReadMethod().getAnnotations(), annotationClasses);
         if (!annotations.isEmpty()) {
            return Optional.of(new AnnotatedMethod(
                  propertyDescriptor.getName(),
                  propertyDescriptor.getReadMethod(),
                  annotations
            ));
         }
      }
      return empty();
   }

   private static List<Annotation> filter(Annotation[] annotations, Collection<Class<?>> permittedTypes) {
      return stream(annotations)
            .filter(a -> permittedTypes.contains(a.annotationType()))
            .collect(Collectors.toList());
   }
}
