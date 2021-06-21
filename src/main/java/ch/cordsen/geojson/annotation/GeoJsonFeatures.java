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
 * Indicates the <em>Features Array</em> of the generated <em>FeatureCollection</em>.
 * <p>This annotation is complementary to a {@link GeoJson#type()} of {@link GeoJsonType#FEATURE_COLLECTION}
 * and can be present 0...1 times. The elements of the {@link java.util.Collection} or array will be
 * added to the feature array.
 * <p>
 * The annotations {@link GeoJsonFeatures} and {@link GeoJsonFeature} are mutual exclusive.
 * <p>Example:
 * <pre>
 *    &#064;GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
 *    &#064;JsonSerialize(using = GeoJsonSerializer.class)
 *    public class Attractions {
 *
 *       &#064;GeoJsonFeatures
 *       private List&lt;Attraction&gt; attractions;
 *
 *    }
 * </pre>
 * The output of above example will look like:
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
 * @see GeoJson
 * @see GeoJsonType#FEATURE_COLLECTION
 * @see GeoJsonFeature
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2" target="_blank">RFC 7946 - FeatureCollection Object</a>
 */
@Inherited
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonFeatures {
}
