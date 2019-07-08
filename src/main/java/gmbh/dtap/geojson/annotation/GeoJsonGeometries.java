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

import gmbh.dtap.geojson.serializer.GeoJsonType;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the <em>Geometry Array</em> of the generated <em>GeometryCollection</em>.
 * <p>This annotation is complementary to a {@link GeoJson#type()} of {@link GeoJsonType#GEOMETRY_COLLECTION}
 * and can be present 0...1 times. The elements of the {@link java.util.Collection} or array will be
 * added to the geometries array.
 * <p>
 * The annotations {@link GeoJsonGeometries} and {@link GeoJsonGeometry} are mutual exclusive.
 * <p>Example:
 * <pre>
 *    &#064;GeoJson(type = GeoJsonType.GEOMETRY_COLLECTION)
 *    &#064;JsonSerialize(using = GeoJsonSerializer.class)
 *    public class Attractions {
 *
 *       &#064;GeoJsonGeometries
 *       private List&lt;Point&gt; points;
 *
 *    }
 * </pre>
 *
 * @see GeoJson
 * @see GeoJsonType#GEOMETRY_COLLECTION
 * @see GeoJsonGeometry
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.8" target="_blank">RFC 7946 - GeometryCollections</a>
 * @since 0.1.0
 */
@Inherited
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonGeometries {
}
