package ch.cordsen.geojson.testsupport.examples.featurecollection;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.annotation.GeoJsonFeatures;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import ch.cordsen.geojson.testsupport.examples.feature.AttractionByField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>FeatureCollection</em> based on a {@link List} field.
 */
@GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionsByField {

   @GeoJsonFeatures
   private final List<AttractionByField> attractions = new ArrayList<>();

   public void add(AttractionByField attraction) {
      this.attractions.add(attraction);
   }

   public List<AttractionByField> getAttractions() {
      return attractions;
   }

   @Override public String toString() {
      return "AttractionsByField{" +
         "attractions=" + attractions +
         '}';
   }
}
