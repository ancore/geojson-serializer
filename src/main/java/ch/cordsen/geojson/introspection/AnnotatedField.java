package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.DocumentFactoryException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Represents an annotated {@link Field field} by name and value.
 */
public class AnnotatedField implements Annotated {

   private final String name;
   private final Field field;
   private final List<Annotation> annotations;

   /**
    * Constructor
    */
   AnnotatedField(String name, Field field, List<Annotation> annotations) {
      this.name = name;
      this.field = field;
      this.annotations = annotations;
   }

   /**
    * {@inheritDoc}
    */
   @Override public String getName() {
      return name;
   }

   /**
    * {@inheritDoc}
    */
   @Override public String getDescription() {
      return field.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override public List<Annotation> getAnnotations() {
      return annotations;
   }

   /**
    * {@inheritDoc}
    */
   @Override public <T> T getValue(Object object, Class<T> expectedClass) throws DocumentFactoryException {
      Object value;
      try {
         value = FieldUtils.readField(field, object, true);
      } catch (Exception e) {
         throw new DocumentFactoryException("Value from Field failed: object=" + object + ", field=" + field, e);
      }
      if (value != null && !expectedClass.isInstance(value)) {
         throw new DocumentFactoryException("Value from Field is not of expected type: object=" + object + ", field=" + field + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }
}
