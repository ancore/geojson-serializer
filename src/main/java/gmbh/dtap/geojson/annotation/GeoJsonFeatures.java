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

package gmbh.dtap.geojson.annotation;

import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the <em>features</em> of the generated <em>FeatureCollection</em>.
 * <p>Example for {@link GeoJsonType type} <em>FeatureCollection</em>:
 * <pre>
 *    &#064;GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
 *    public class TestEntities {
 *
 *       &#064;GeoJsonFeatures
 *       private List<TestEntity> entities;
 *
 *    }
 * </pre>
 * <p>The output of the {@link GeoJsonSerializer serializer} for the annotation will look like:
 * <pre>
 *    {
 *       "type": "FeatureCollection",
 *       "features": [{
 *           "id": 123
 *           "type": "Feature",
 *           "geometry": null,
 *           "properties": null
 *        }, {
 *           "id": 321
 *           "type": "Feature",
 *           "geometry": null,
 *           "properties": null
 *       }]
 *    }
 * </pre>
 *
 * @see GeoJsonType
 * @see GeoJsonSerializer
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.3" target="_blank">RFC 7946 - FeatureCollection Object</a>
 * @since 0.1.0
 */
@Inherited
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonFeatures {
}
