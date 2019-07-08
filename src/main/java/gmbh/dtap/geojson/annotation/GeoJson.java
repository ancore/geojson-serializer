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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gmbh.dtap.geojson.document.DocumentFactory;
import gmbh.dtap.geojson.introspection.IntrospectionDocumentFactory;
import gmbh.dtap.geojson.serializer.GeoJsonSerializer;
import gmbh.dtap.geojson.serializer.GeoJsonType;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Enables the annotated type to be serialized as a <em>GeoJson Object</em> by the {@link GeoJsonSerializer}.
 * <p>Please note that the {@link GeoJsonSerializer} needs to be set with the {@link JsonSerialize} annotation.
 * <p>Depending on the type, further annotations on fields or getters are complementary.
 * <p>{@link GeoJsonType#FEATURE}
 * <ul>
 * <li>{@link GeoJsonId} {0,1}</li>
 * <li>{@link GeoJsonGeometry} {0,1}</li>
 * <li>{@link GeoJsonProperties} {0,1} <strong>or</strong> {@link GeoJsonProperty} {0,}</li>
 * </ul>
 * <p>{@link GeoJsonType#FEATURE_COLLECTION}
 * <ul>
 * <li>{@link GeoJsonFeatures} {0,1} <strong>or</strong> {@link GeoJsonFeature} {0,}</li>
 * </ul>
 * <p>{@link GeoJsonType#GEOMETRY_COLLECTION} is not supported yet.
 * <p>Example for type <em>Feature</em>:
 * <pre>
 *    &#064;GeoJson(type = GeoJsonType.FEATURE)
 *    &#064;JsonSerialize(using = GeoJsonSerializer.class)
 *    public class TestEntity {
 *
 *    }
 * </pre>
 * The output of the above example will look like:
 * <pre>
 *    {
 *       "type": "Feature",
 *       "geometry": null,
 *       "properties": null
 *    }
 * </pre>
 *
 * @see GeoJsonType
 * @see JsonSerialize
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
    * Returns the {@link GeoJsonType} to serialize to.
    *
    * @return the {@link GeoJsonType}
    * @since 0.1.0
    */
   GeoJsonType type();

   /**
    * Return the {@link DocumentFactory} to use.
    *
    * @return the {@link DocumentFactory}, or the default if not specified
    * @see IntrospectionDocumentFactory
    * @since 0.4.0
    */
   Class<? extends DocumentFactory> factory() default IntrospectionDocumentFactory.class;
}
