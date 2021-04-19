package gmbh.dtap.geojson.introspection;

import gmbh.dtap.geojson.document.DocumentFactoryException;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link AnnotatedField}.
 *
 * @since 0.5.3
 */
class AnnotatedFieldTest {

   @Test
   void shouldReturnFieldValue() throws NoSuchFieldException, DocumentFactoryException {
      Field field = TestClass.class.getDeclaredField("text");
      AnnotatedField annotatedField = new AnnotatedField("name", field, Collections.emptyList());
      assertThat(annotatedField.getValue(new TestClass("foo"), String.class)).isEqualTo("foo");
   }

   @Test
   void shouldThrowExceptionWhenAnyConstructorParameterIsNull() throws NoSuchFieldException {
      String name = "name";
      Field field = TestClass.class.getDeclaredField("text");
      List<Annotation> annotations = Collections.emptyList();
      assertThrows(Exception.class, () -> new AnnotatedField(null, field, annotations));
      assertThrows(Exception.class, () -> new AnnotatedField(name, null, annotations));
      assertThrows(Exception.class, () -> new AnnotatedField(name, field, null));
   }

   @Test
   void shouldThrowExceptionWhenAnyGetValueParameterIsNull() throws NoSuchFieldException {
      Field field = TestClass.class.getDeclaredField("text");
      AnnotatedField annotatedField = new AnnotatedField("name", field, Collections.emptyList());
      assertThrows(Exception.class, () -> annotatedField.getValue(null, String.class));
      assertThrows(Exception.class, () -> annotatedField.getValue(new TestClass("foo"), null));
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
