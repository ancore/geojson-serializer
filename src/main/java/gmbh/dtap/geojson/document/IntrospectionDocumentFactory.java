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

package gmbh.dtap.geojson.document;

/**
 * Default implementation of a {@link DocumentFactory} that introspects the
 * object's class for annotations and retrieves the values of the annotated
 * fields and methods.
 *
 * @since 0.4.0
 */
public class IntrospectionDocumentFactory implements DocumentFactory {

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override
   public Document from(Object object) {
      return null;
   }
}
