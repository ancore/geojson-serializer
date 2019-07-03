package gmbh.dtap.geojson.introspection;

import gmbh.dtap.geojson.document.GeometryCollectionDocument;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

public class IntrospectionGeometryCollectionDocument implements GeometryCollectionDocument {

   private final List<Geometry> geometries;

   public IntrospectionGeometryCollectionDocument(List<Geometry> geometries) {
      this.geometries = geometries;
   }

   @Override
   public List<Geometry> getGeometries() {
      return null;
   }
}
