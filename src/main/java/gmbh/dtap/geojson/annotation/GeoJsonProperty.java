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
 * Indicates one attribute of the <em>Properties Object</em> for the <em>Feature</em> to be generated.
 * <p>This annotation an be present 0...n times. The key of an attribute is taken from the annotated
 * getter or field and can be overwritten by {@link GeoJsonProperty#name()}. The attribute values
 * can be any JSON object.
 * <p>This annotation is mutually exclusive with {@link GeoJsonProperties}.
 * <p>The properties field will be set to JSON <code>null</code> if no property is available.
 * <p>Getter annotations example with the {@link String}:
 * <pre>
 *    &#064;GeoJsonProperty
 *    public String getCityName() {
 *       return cityName;
 *    }
 *
 *    &#064;GeoJsonProperty(name="localization")
 *    public Locale getLocale() {
 *       return locale;
 *    }
 * </pre>
 *
 * @see GeoJson
 * @see GeoJsonType#FEATURE
 * @see GeoJsonProperties
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2" target="_blank">RFC 7946 - Feature Object</a>
 * @since 0.1.0
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonProperty {

   /**
    * Returns the name of the property attribute. Allows to overwrite the default behaviour.
    * <p>If not set, the field name or getter name (without "get", lower case) wil be used.
    *
    * @return the optional name of the property
    * @since 0.1.0
    */
   String name() default "";
}
