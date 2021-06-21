package ch.cordsen.geojson.introspection;

import ch.cordsen.geojson.document.GeometryCollectionDocument;
import org.locationtech.jts.geom.Geometry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Default implementation of a {@link GeometryCollectionDocument}.
 */
public class IntrospectionGeometryCollectionDocument implements GeometryCollectionDocument {

   private final List<Geometry> geometries;

   /**
    * Constructor
    *
    * @param geometries the geometries
    */
   IntrospectionGeometryCollectionDocument(@Nullable List<Geometry> geometries) {
      this.geometries = geometries;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Geometry> getGeometries() {
      return geometries;
   }
}
