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
 * <p>This class demonstrates a <em>FeatureCollection</em> based on an empty {@link List} field.
 */
@GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionsEmpty {

   @GeoJsonFeatures
   private final List<AttractionByField> attractions = new ArrayList<>();

   public List<AttractionByField> getAttractions() {
      return attractions;
   }

   @Override public String toString() {
      return "AttractionsByField{" +
         "attractions=" + attractions +
         '}';
   }
}
