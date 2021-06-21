package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.DocumentFactoryException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link AnnotatedMethod}.
 */
class AnnotatedMethodTest {

   @Test
   void shouldReturnMethodValue() throws NoSuchMethodException, DocumentFactoryException {
      Method method = AnnotatedMethodTest.TestClass.class.getDeclaredMethod("getText");
      AnnotatedMethod annotatedMethod = new AnnotatedMethod("name", method, Collections.emptyList());
      assertThat(annotatedMethod.getValue(new AnnotatedMethodTest.TestClass("foo"), String.class)).isEqualTo("foo");
   }

   @Test
   void shouldThrowExceptionWhenAnyGetValueParameterIsNull() throws NoSuchMethodException {
      Method method = AnnotatedMethodTest.TestClass.class.getDeclaredMethod("getText");
      AnnotatedMethod annotatedMethod = new AnnotatedMethod("name", method, Collections.emptyList());
      DocumentFactoryException exception = assertThrows(DocumentFactoryException.class, () -> annotatedMethod.getValue(new AnnotatedMethodTest.TestClass("foo"), Integer.class));
      assertThat(exception).hasMessageStartingWith("Value from Method is not of expected type");
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
