package gmbh.dtap.geojson.introspection;

import gmbh.dtap.geojson.document.DocumentFactoryException;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link AnnotatedMethod}.
 *
 * @since 0.5.3
 */
class AnnotatedMethodTest {

   @Test
   void shouldReturnMethodValue() throws NoSuchMethodException, DocumentFactoryException {
      Method method = AnnotatedMethodTest.TestClass.class.getDeclaredMethod("getText");
      AnnotatedMethod annotatedMethod = new AnnotatedMethod("name", method, Collections.emptyList());
      assertThat(annotatedMethod.getValue(new AnnotatedMethodTest.TestClass("foo"), String.class)).isEqualTo("foo");
   }

   @Test
   void shouldThrowExceptionWhenAnyConstructorParameterIsNull() throws NoSuchMethodException {
      String name = "name";
      Method method = AnnotatedMethodTest.TestClass.class.getDeclaredMethod("getText");
      List<Annotation> annotations = Collections.emptyList();
      assertThrows(Exception.class, () -> new AnnotatedMethod(null, method, annotations));
      assertThrows(Exception.class, () -> new AnnotatedMethod(name, null, annotations));
      assertThrows(Exception.class, () -> new AnnotatedMethod(name, method, null));
   }

   @Test
   void shouldThrowExceptionWhenAnyGetValueParameterIsNull() throws NoSuchMethodException {
      Method method = AnnotatedMethodTest.TestClass.class.getDeclaredMethod("getText");
      AnnotatedMethod annotatedMethod = new AnnotatedMethod("name", method, Collections.emptyList());
      assertThrows(Exception.class, () -> annotatedMethod.getValue(null, String.class));
      assertThrows(Exception.class, () -> annotatedMethod.getValue(new AnnotatedMethodTest.TestClass("foo"), null));
   }

   /**
    * Test class to retrieve a {@link Method} instance from.
    */
   static class TestClass {

      private String text;

      public TestClass(String text) {
         this.text = text;
      }

      public String getText() {
         return text;
      }

      public void setText(String text) {
         this.text = text;
      }
   }
}
