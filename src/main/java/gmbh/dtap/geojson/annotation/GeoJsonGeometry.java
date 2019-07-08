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
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the <em>Geometry Object</em> of the <em>Feature</em> to be generated.
 * <p>This annotation is complementary to a {@link GeoJson#type()} of {@link GeoJsonType#FEATURE}
 * or {@link GeoJsonType#GEOMETRY_COLLECTION}  and can be present 0...1 times.
 * <p>The actual serialization of the {@link Geometry} is done with <em>com.graphhopper.external:jackson-datatype-jts</em>.
 * <p>The expected type is {@link Geometry} or one of its standard implementations.
 * <p>Getter annotation example with the {@link Point} implementation of {@link Geometry}:
 * <pre>
 *    &#064;GeoJsonGeometry
 *    public Point getLocation() {
 *       return location;
 *    }
 * </pre>
 * <p>Field annotation example with the {@link Polygon} implementation of {@link Geometry}:
 * <pre>
 *    &#064;GeoJsonGeometry
 *    &#064;JsonSerialize(using = GeoJsonSerializer.class)
 *    private Polygon area;
 * </pre>
 * <p>
 *
 * @see GeoJson
 * @see GeoJsonType#FEATURE
 * @see <a href="https://github.com/locationtech/jts" target="_blank">JTS Topology Suite</a>
 * @see <a href="https://github.com/graphhopper/jackson-datatype-jts">jackson-datatype-jts</a>
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2" target="_blank">RFC 7946 - Feature Object</a>
 * @since 0.5.0
 */
@Inherited
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonGeometry {
}
