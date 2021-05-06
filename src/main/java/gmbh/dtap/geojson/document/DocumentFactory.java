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

import gmbh.dtap.geojson.annotation.GeoJson;
import gmbh.dtap.geojson.introspection.IntrospectionDocumentFactory;

/**
 * Implementing types can create {@link Document documents} from an annotated object.
 * <p>
 * The factory is interchangeable to enable mocking in tests and customizations.
 * The default implementation can replaced with annotation attribute {@link GeoJson#factory()}.
 *
 * @see IntrospectionDocumentFactory as default implementation
 */
public interface DocumentFactory {

   /**
    * Creates a <em>GeoJSON document</em> from {@link GeoJson} annotation and the complementary annotations.
    *
    * @param object the object to introspect
    * @return one of {@link FeatureDocument}, {@link FeatureCollectionDocument}, {@link GeometryCollectionDocument} depending on the attribute {@link GeoJson#type()}
    * @throws DocumentFactoryException for missing annotations, wrong types, invalid combination of annotations and such
    */
   Document from(Object object) throws DocumentFactoryException;
}
