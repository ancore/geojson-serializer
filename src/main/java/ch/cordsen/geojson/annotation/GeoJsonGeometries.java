package ch.cordsen.geojson.annotation;

import ch.cordsen.geojson.serializer.GeoJsonType;

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
 */
@Inherited
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonGeometries {
}
