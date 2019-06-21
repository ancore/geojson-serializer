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

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates one entry of the <em>Properties</em> for the <em>Feature</em> to be generated.
 * <p>This annotation an be present 0...n times. The properties field will be set to
 * JSON <tt>null</tt> if not available.
 * <p>A property field can be any JSON object. If this annotation is present once, the
 * annotated return value or field value is used as properties field.
 * <p>If this annotation is present multiple times, a JSON object is created where
 * every annotation is added by key and value.
 * <p>Getter annotation example with the {@link String}:
 * <p><pre>
 *    &#064;GeoJsonProperty
 *    public String getCityName() {
 *       return cityName;
 *    }
 * </pre>
 * <p>Field annotation example with the {@link int}:
 * <pre>
 *    &#064;GeoJsonProperty
 *    private int amount;
 * </pre>
 * <p></p>
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2" target="_blank">RFC 7946 - Feature Object</a>
 * @since 0.1.0
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonProperty {

   /**
    * Returns the name of the property in the <em>Feature</em>.
    * <p>If not set, the field name or getter name (without "get", lower case) wil be used.
    *
    * @return the optional name of the property
    * @since 0.1.0
    */
   String name() default "";
}
