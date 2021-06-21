package ch.cordsen.geojson.annotation;

import ch.cordsen.geojson.serializer.GeoJsonType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.UUID;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the <em>id</em> of the generated <em>Feature</em>.
 * <p>This annotation is complementary to a {@link GeoJson#type()} of {@link GeoJsonType#FEATURE}
 * and can be present 0...1 times. No <em>id field</em> is set if missing.
 * <p>Getter annotation example with type {@link UUID}:
 * <pre>
 *    &#064;GeoJsonId
 *    public UUID getId() {
 *       return id;
 *    }
 * </pre>
 * <p>Field annotation example with type {@link Long}:
 * <pre>
 *    &#064;GeoJsonId
 *    private Long id;
 * </pre>
 *
 * @see GeoJson
 * @see GeoJsonType#FEATURE
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2" target="_blank">RFC 7946 - Feature Object</a>
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface GeoJsonId {
}
