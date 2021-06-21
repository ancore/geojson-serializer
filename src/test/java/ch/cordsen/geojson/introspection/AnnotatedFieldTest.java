package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.DocumentFactoryException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link AnnotatedField}.
 */
class AnnotatedFieldTest {

   @Test
   void shouldReturnFieldValue() throws NoSuchFieldException, DocumentFactoryException {
      Field field = TestClass.class.getDeclaredField("text");
      AnnotatedField annotatedField = new AnnotatedField("name", field, Collections.emptyList());

      assertThat(annotatedField.getValue(new TestClass("foo"), String.class)).isEqualTo("foo");
   }

   @Test
   void shouldThrowExceptionWhenFieldTypeIsIncompatible() throws NoSuchFieldException {
      Field field = TestClass.class.getDeclaredField("text");
      AnnotatedField annotatedField = new AnnotatedField("name", field, Collections.emptyList());

      DocumentFactoryException exception = assertThrows(DocumentFactoryException.class, () -> annotatedField.getValue(new TestClass("foo"), Integer.class));
      assertThat(exception).hasMessageStartingWith("Value from Field is not of expected type");
   }

   /**
    * Test class to retrieve a {@link Field} instance from.
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
