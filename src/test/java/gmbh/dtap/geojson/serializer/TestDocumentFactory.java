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

import gmbh.dtap.geojson.document.Document;
import gmbh.dtap.geojson.document.DocumentFactory;
import gmbh.dtap.geojson.document.DocumentFactoryException;

/**
 * This implementation for testing purposes returns the input object as {@link Document}.
 *
 * @since 0.4.0
 */
public class TestDocumentFactory implements DocumentFactory {

   /**
    * Constructor for reflection only.
    *
    * @since 0.4.0
    */
   public TestDocumentFactory() {
   }

   /**
    * {@inheritDoc}
    *
    * @since 0.4.0
    */
   @Override
   public Document from(Object object) throws DocumentFactoryException {
      if (object == null) {
         return null;
      }
      if (object instanceof Document) {
         return (Document) object;
      } else {
         throw new IllegalArgumentException("Object is not of type Document: " + object);
      }
   }
}
