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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link IntrospectionFeatureDocument}.
 */
class IntrospectionFeatureDocumentTest {

   @Test
   void shouldReturnNullWhenInitializedWithNull() {
      IntrospectionFeatureDocument document = new IntrospectionFeatureDocument(null, null, null);
      assertThat(document.getId()).isNull();
      Assertions.<Object>assertThat(document.getGeometry()).isNull();
      assertThat(document.getProperties()).isNull();
   }

   @Test
   void shouldReturnValuesWhenInitializedWithValues() {
      Geometry geometry = TestUtils.point(23, 43);
      List<String> properties = new ArrayList<>();
      properties.add("foo");
      IntrospectionFeatureDocument document = new IntrospectionFeatureDocument("23", geometry, properties);
      assertThat(document.getId()).isEqualTo("23");
      Assertions.<Object>assertThat(document.getGeometry()).isEqualTo(geometry);
      assertThat(document.getProperties()).isEqualTo(properties);
   }
}
