/*
 * Copyright 2019 DTAP GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gmbh.dtap.geojson.introspection;

import gmbh.dtap.geojson.testsupport.TestUtils;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link IntrospectionGeometryCollectionDocument}.
 */
class IntrospectionGeometryCollectionDocumentTest {

   @Test
   void shouldReturnElementsList() {
      List<Geometry> list = new ArrayList<>();
      list.add(TestUtils.point(23, 42));
      list.add(TestUtils.point(42, 23));
      IntrospectionGeometryCollectionDocument document = new IntrospectionGeometryCollectionDocument(list);
      assertThat(document.getGeometries()).containsAll(list);
   }

   @Test
   void shouldReturnEmptyListWhenInitializedWithEmptyList() {
      IntrospectionGeometryCollectionDocument document = new IntrospectionGeometryCollectionDocument(Collections.emptyList());
      assertThat(document.getGeometries()).isEmpty();
   }

   @Test
   void shouldReturnNullWhenInitializedWithNull() {
      IntrospectionGeometryCollectionDocument document = new IntrospectionGeometryCollectionDocument(null);
      assertThat(document.getGeometries()).isNull();
   }
}
