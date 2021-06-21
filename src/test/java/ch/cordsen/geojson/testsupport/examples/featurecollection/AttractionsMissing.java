package ch.cordsen.geojson.testsupport.examples.featurecollection;

import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class with correct annotations.
 * <p>This class demonstrates a <em>FeatureCollection</em> based on missing annotation.
 */
@GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class AttractionsMissing {

}
