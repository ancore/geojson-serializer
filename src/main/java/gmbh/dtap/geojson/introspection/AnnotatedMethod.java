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

import gmbh.dtap.geojson.document.DocumentFactoryException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Describes a {@link Method method} by name, type and annotations.
 *
 * @since 0.4.0
 */
public class AnnotatedMethod implements Annotated {

   private final String name;
   private final Method method;
   private List<Annotation> annotations;

   /**
    * Contructor
    *
    * @since 0.4.0
    */
   AnnotatedMethod(String name, Method method, List<Annotation> annotations) {
      this.name = name;
      this.method = method;
      this.annotations = annotations;
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */

   @Override public String getName() {
      return name;
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override public String getDescription() {
      return method.toString();
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override public List<Annotation> getAnnotations() {
      return annotations;
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override public <T> T getValue(Object object, Class<T> expectedClass) throws DocumentFactoryException {
      Object value;
      try {
         value = method.invoke(object);
      } catch (Exception e) {
         throw new DocumentFactoryException("Value from Method failed: object=" + object + ", method=" + method, e);
      }
      if (!expectedClass.isInstance(value)) {
         throw new DocumentFactoryException("Value from Method is not of expected type: object=" + object + ", method=" + method + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override public String toString() {
      return "AnnotatedMethod{" +
            "name='" + name + '\'' +
            ", method=" + method +
            ", annotations=" + annotations +
            '}';
   }
}
