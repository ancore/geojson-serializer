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

import gmbh.dtap.geojson.document.FeatureDocument;
import org.locationtech.jts.geom.Geometry;

/**
 * Default implementation of a {@link FeatureDocument}.
 */
public class IntrospectionFeatureDocument implements FeatureDocument {

   private final Object id;
   private final Geometry geometry;
   private final Object properties;

   /**
    * Constructor
    */
   IntrospectionFeatureDocument(Object id, Geometry geometry, Object properties) {
      this.id = id;
      this.geometry = geometry;
      this.properties = properties;
   }

   /**
    * {@inheritDoc}
    */
   @Override public Object getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override public Geometry getGeometry() {
      return geometry;
   }

   /**
    * {@inheritDoc}
    */
   @Override public Object getProperties() {
      return properties;
   }
}
