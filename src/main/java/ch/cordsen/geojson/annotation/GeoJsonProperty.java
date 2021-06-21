package ch.cordsen.geojson.annotation;

import ch.cordsen.geojson.serializer.GeoJsonType;

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
    */
   String name() default "";
}
