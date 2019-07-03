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

import org.locationtech.jts.geom.Geometry;

/**
 * Represents a <em>GeoJSON document</em> for a <em>Feature</em>.
 *
 * @see DocumentFactory
 * @since 0.4.0
 */
public interface FeatureDocument extends Document {

   /**
    * The optional ID.
    *
    * @return the optional ID, may be <tt>null</tt>
    * @since 0.4.0
    */
   Object getId();

   /**
    * The optional Geometry object.
    *
    * @return the optional geometry, may be <tt>null</tt>
    * @since 0.4.0
    */
   Geometry getGeometry();

   /**
    * The optional properties object.
    *
    * @return the optional properties, may be <tt>null</tt>
    * @since 0.4.0
    */
   Object getProperties();
}
