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
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the <em>Properties Object</em> of the <em>Feature</em> to be generated.
 * <p>This annotation can be present 0...1 times. The properties object can be any JSON
 * object. The getter or field name is not relevant.
 * The annotations {@link GeoJsonProperties} and {@link GeoJsonProperty} are mutual exclusive.
 * <p>The properties field will be set to JSON <code>null</code> if no property available.
 * <p>Getter annotation example with a {@link java.util.Map}:
 * <pre>
 *    &#064;GeoJsonProperties
 *    public Map&lt;String, Object&gt; getProperties() {
 *       return Collections.singletonMap("key", "value");
 *    }
 * </pre>
 * <p>Field annotation example with a {@link java.util.Map}:
 * <pre>
 *    &#064;GeoJsonProperties
 *    private Map&lt;String, Object&gt; properties;
 * </pre>
 *
 * @see GeoJson
 * @see GeoJsonType#FEATURE
 * @see GeoJsonProperty
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2" target="_blank">RFC 7946 - Feature Object</a>
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonProperties {
}
