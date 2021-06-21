[![Java CI with Maven](https://github.com/ancore/geojson-serializer/actions/workflows/maven.yml/badge.svg)](https://github.com/ancore/geojson-serializer/actions/workflows/maven.yml) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/ch.cordsen/geojson-serializer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ch.cordsen/geojson-serializer)

# geojson-serializer

A library with a `JsonSerializer` and a set of annotations to serialize any PoJo as [GeoJSON](https://tools.ietf.org/html/rfc7946).

Supported so far is [Feature](https://tools.ietf.org/html/rfc7946#section-3.2) and [FeatureCollection](https://tools.ietf.org/html/rfc7946#section-3.3),
[GeometryCollection](https://tools.ietf.org/html/rfc7946#section-3.1.8).

A demo Spring Boot project is available: [demo project](https://github.com/ancore/geojson-serializer-demo)

## Procedure

The PoJo containing the data for the GeoJSON object is annotated with `@GeoJson`.

Whether the GeoJSON is a _Feature_, _FeatureCollection_ or a _GeometryCollection_ is specified by the annotation attribute `type`, for example `GeoJson(type = GeoJsonType.FEATURE)`
.

PoJo annotations on fields or getters indicate the GeoJSON members (_id_, _geometry_, _properties_, _features_) depending on the type.

## Dependencies

- [Jackson Databind](https://github.com/FasterXML/jackson-databind)
- [JTS Topology Suite](https://github.com/locationtech/jts)
- [graphhopper implementation of jackson-datatype-jts](https://github.com/graphhopper/jackson-datatype-jts)

The serialization of Geometry objects from the **JTS Technology Suite** is handled by the **graphopper implemenation**
of **jackson-datatype-jts**. The original **jackson-datatype-jts** project is not maintained anymore and uses an old version of the **JTS Topology Suite**, which can cause a naming
conflict due to the different package name.

This library uses the **org.locationtech.jts.geom** classes.

## Status

The status of this library is something like **beta**. Please let me know if you have any ideas for improvement.

TODO:

* Caching by Type

## Maven Dependency

Check for the current version: https://search.maven.org/artifact/ch.cordsen/geojson-serializer

```xml

<dependency>
   <groupId>ch.cordsen</groupId>
   <artifactId>geojson-serializer</artifactId>
   <version>X.X.X</version>
</dependency>
```

## Example for Feature

The following PoJo (Attraction) is annotated to be serialized as a GeoJSON Feature. It has a field annotation to point out the Feature's id, two String properties and one
geometry (Point).

```java
import ch.cordsen.geojson.annotation.GeoJson;
import ch.cordsen.geojson.annotation.GeoJsonGeometry;
import ch.cordsen.geojson.annotation.GeoJsonId;
import ch.cordsen.geojson.annotation.GeoJsonProperty;
import ch.cordsen.geojson.serializer.GeoJsonSerializer;
import ch.cordsen.geojson.serializer.GeoJsonType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@GeoJson(type = GeoJsonType.FEATURE)
@JsonSerialize(using = GeoJsonSerializer.class)
public class Attraction {

   @GeoJsonId private final UUID id;
   @GeoJsonProperty private final String name;
   @GeoJsonProperty private final String description;
   @GeoJsonGeometry private final Point location;

   public Attraction(UUID id, String name, String description, Point location) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.location = location;
   }

   public UUID getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public Point getLocation() {
      return location;
   }
}
```

Programmatically calling the ObjectMapper to serialize:

```java
import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import static java.util.UUID.randomUUID;

public class Test {

   public static void main(String... args) throws JsonProcessingException {

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JtsModule());

      GeometryFactory geometryFactory = new GeometryFactory();

      Attraction attraction = new Attraction(
         randomUUID(),
         "Eiffel Tower",
         "Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France",
         geometryFactory.createPoint(new Coordinate(2.294527, 48.859092)));

      String geoJson = objectMapper
         .writerWithDefaultPrettyPrinter()
         .writeValueAsString(attraction);
   }
}
```

Output:

```json
{
   "type": "Feature",
   "id": "2e38f4b1-d9ea-459a-ba52-6093b09b6dc0",
   "geometry": {
      "type": "Point",
      "coordinates": [
         2.294527,
         48.859092
      ]
   },
   "properties": {
      "name": "Eiffel Tower",
      "description": "Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France"
   }
}
```

Works with Spring MVC:

```java

@RestController
public class WebController {

   // ...

   @GetMapping("/api/attractions/{id}")
   public ResponseEntity<Attraction> findById(@PathVariable(value = "id") UUID id) {
      return repository.findById(id)
         .map(ResponseEntity::ok)
         .orElse(ResponseEntity.notFound().build());
   }
}
```

Please refer to the [demo project](https://github.com/ancore/geojson-serializer-demo) for details.

## Annotations

The library provides one type annotation `@GeoJson` and several annotations used on fields or methods. Methods are limited to getters ("Bean Property").

**Additionally**, the provided `GeoJsonSerializer` has to be used.

### @GeoJson

This annotation declares a Java class as `Feature`, `FeatureCollection` or `GeometryCollection` by using the respective `GeoJsonType`:

* @GeoJson(type=GeoJsonType.FEATURE)
* @GeoJson(type=GeoJsonType.FEATURE_COLLECTION)
* @GeoJson(type=GeoJsonType.GEOMETRY_COLLECTION)

The class furthermore contains fields or getters with annotations representing attributes of the specific type.

Example: The class 'Attraction' representing a `Feature` can be used in a class 'Attractions' representing a `FeatureCollection`.

```java

@GeoJson(type = GeoJsonType.FEATURE)
@JsonSerialize(using = GeoJsonSerializer.class)
public class Attraction {

   @GeoJsonId private UUID id;
   @GeoJsonProperty private String name;
   @GeoJsonProperty private String description;
   @GeoJsonGeometry private Point location;

   // ...
}

@GeoJson(type = GeoJsonType.FEATURE_COLLECTION)
@JsonSerialize(using = GeoJsonSerializer.class)
public class Attractions {

   @GeoJsonFeatures private List<Attraction> attractions; // using 'Feature' Attraction

   // ...
}
```

### @GeoJsonId

* indicates the ID of a `Feature`
* optional annotation, ID attribute is omitted if missing

### Properties

There are two mutually exclusive annotations to indicate the Properties Object of a `Feature`. One is used for one Java object to represent the Properties Object, the other to
collect all occurrences and combine them to one Properties Object. If no field is annotated with either of them, the Properties Object is set to `null`.

#### @GeoJsonProperties

* indicates the Properties Object of a `Feature`
* optional, but not more than once per class
* mutually exclusive with `@GeoJsonProperty`
* field or getter name is irrelevant
* the field or getter value should serialize to a JSON object

#### @GeoJsonProperty

* indicates one attribute of the Properties Object of a `Feature`
* optional and unlimited
* mutually exclusive with `@GeoJsonProperties`
* field or getter name is used as attribute key, unless overwritten with `@GeoJsonProperty#name()`
* the attribute value should be any serializable object

### Geometries

As with Properties, two mutually exclusive annotations are available. A `Feature` may have one Geometry, a `GeometryCollection` has a Geometries Array. Missing annotations will
lead to a `null` value for `Feature` or an empty Array for a `GeometryCollection`.

Every element is expected to be of type `org.locationtech.jts.geom.Geometry` or one of the standard subtypes like `Point`, `Polygon` and so on. The actual serialization is done by
using `com.graphhopper.external:jackson-datatype-jts`.

#### @GeoJsonGeometries

* indicates the Geometry Array of a `GeometryCollection`
* optional, but not more than once per class
* mutually exclusive with `@GeoJsonGeometry`
* field or getter name is irrelevant
* the field or getter value should serialize to a serializable collection containing Geometry elements

#### @GeoJsonGeometry

* indicates the Geometry Object of a `Feature` or one element of the Geometries Array of a `GeometryCollection`
* optional and unlimited
* mutually exclusive with `@GeoJsonGeometries`
* field or getter name is irrelevant

### Features

Again, two mutually exclusive annotations are used. The values are excepted to be serializable as `Feature`, most likely using Java classes with annotations from this library. If
no field is annotated with either of them, the Feature Array is empty.

#### @GeoJsonFeatures

* indicates the Feature Array of a `FeatureCollection`
* optional, but not more than once per class
* mutually exclusive with `@GeoJsonFeature`

#### @GeoJsonFeature

* indicates one Feature of the Feature Array of a `FeatureCollection`
* optional and unlimited
* mutually exclusive with `@GeoJsonFeatures`

## Credits

Copyright (c) 2019 Andreas Cordsen

Please refer to the LICENSE file for details.
