package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.DocumentFactoryException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Describes a {@link Method method} by name, type and annotations.
 */
public class AnnotatedMethod implements Annotated {

   private final String name;
   private final Method method;
   private final List<Annotation> annotations;

   /**
    * Constructor
    */
   AnnotatedMethod(String name, Method method, List<Annotation> annotations) {
      this.name = name;
      this.method = method;
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
      return method.toString();
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
         value = method.invoke(object);
      } catch (Exception e) {
         throw new DocumentFactoryException("Value from Method failed: object=" + object + ", method=" + method, e);
      }
      if (value != null && !expectedClass.isInstance(value)) {
         throw new DocumentFactoryException("Value from Method is not of expected type: object=" + object + ", method=" + method + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }
}
