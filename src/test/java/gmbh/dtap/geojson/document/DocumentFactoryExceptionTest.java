package gmbh.dtap.geojson.document;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DocumentFactoryException}.
 */
class DocumentFactoryExceptionTest {

   @Test
   void shouldContainMessageOnly() {
      DocumentFactoryException exception = new DocumentFactoryException("Test");
      assertThat(exception).hasMessage("Test");
      assertThat(exception).hasNoCause();
   }

   @Test
   void shouldContainMessageAndCause() {
      Exception cause = new RuntimeException();
      DocumentFactoryException exception = new DocumentFactoryException("Test", cause);
      assertThat(exception).hasMessage("Test");
      assertThat(exception).hasCause(cause);
   }
}
