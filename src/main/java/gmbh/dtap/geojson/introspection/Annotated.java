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
import java.util.List;

/**
 * Represents an annotated field or method found in introspection.
 *
 * @since 0.4.0
 */
public interface Annotated {

   /**
    * Returns the name of the field or method. In case of methods,
    * the name is according to the {@link java.beans.PropertyDescriptor}.
    *
    * @return the name
    * @since 0.4.0
    */
   String getName();

   /**
    * Returns a description to be identified in exception messages.
    *
    * @return a description
    * @since 0.4.0
    */
   String getDescription();

   /**
    * Returns all GeoJson annotations from this member.
    *
    * @return all GeoJson annotations, may be empty but never <tt>null</tt>
    * @since 0.4.0
    */
   List<Annotation> getAnnotations();

   /**
    * Returns the value of a the annotated member.
    *
    * @param object        the object of which the value should be returned
    * @param expectedClass the expected type of value
    * @param <T>           the class of the expected type
    * @return the value, may be <tt>null</tt>
    * @throws DocumentFactoryException on any error
    * @since 0.4.0
    */
   <T> T getValue(Object object, Class<T> expectedClass) throws DocumentFactoryException;
}
