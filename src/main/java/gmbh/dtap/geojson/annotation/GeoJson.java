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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Enabled the annotated type can be used to generate a <em>GeoJson Object</em> by the {@link GeoJsonSerializer}.
 * <p>Example for {@link GeoJsonType type} <em>Feature</em>:
 * <pre>
 *    &#064;GeoJson(type = GeoJsonType.FEATURE)
 *    public class TestEntity {
 *
 *    }
 * </pre>
 * <p>The output of the {@link GeoJsonSerializer serializer} for the annotation will look like:
 * <pre>
 *    {
 *       "type": "Feature",
 *       "geometry": null,
 *       "properties": null
 *    }
 * </pre>
 *
 * @see GeoJsonType
 * @see GeoJsonSerializer
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3" target="_blank">RFC 7946 - GeoJSON Object</a>
 * @since 0.1.0
 */
@Inherited
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface GeoJson {

   /**
    * Returns the {@link GeoJsonType}.
    *
    * @return the {@link GeoJsonType}
    * @since 0.1.0
    */
   GeoJsonType type();
}
