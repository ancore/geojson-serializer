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
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Represents an annotated {@link Field field} by name and value.
 *
 * @since 0.4.0
 */
public class AnnotatedField implements Annotated {

   private final String name;
   private final Field field;
   private final List<Annotation> annotations;

   /**
    * Constructor
    *
    * @since 0.4.0
    */
   AnnotatedField(String name, Field field, List<Annotation> annotations) {
      notEmpty(name, "name is null");
      notNull(field, "field is null");
      notNull(annotations, "annotations is null");
      this.name = name;
      this.field = field;
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
      return field.toString();
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
      notNull(object, "object is null");
      notNull(expectedClass, "expectedClass is null");
      Object value;
      try {
         value = FieldUtils.readField(field, object, true);
      } catch (Exception e) {
         throw new DocumentFactoryException("Value from Field failed: object=" + object + ", field=" + field, e);
      }
      if (value != null && !expectedClass.isInstance(value)) {
         throw new DocumentFactoryException("Value from Field is not of expected type: object=" + object + ", field=" + field + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override public String toString() {
      return "AnnotatedField{" +
         "name='" + name + '\'' +
         ", field=" + field +
         ", annotations=" + annotations +
         '}';
   }
}
