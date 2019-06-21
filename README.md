# geojson-serializer
This library provides a [JsonSerializer](https://github.com/FasterXML/jackson-databind) and
a set of annotations to serialize any PoJo as [GeoJSON](https://tools.ietf.org/html/rfc7946).

## Status
The only supported GeoJSON type at the moment is "Feature".

## Maven
```xml
<dependency>
    <groupId>gmbh.dtap.geojson</groupId>
    <artifactId>geojson-serializer</artifactId>
    <version>0.1.0-SNAPSHOT</version> 
</dependency>
```

## Example
The PoJo (Attraction) is annotated to be serialized as a GeoJSON Feature: 
```java
    import com.fasterxml.jackson.databind.annotation.JsonSerialize;
    import gmbh.dtap.geojson.annotation.*;
    import gmbh.dtap.geojson.serializer.*;
    import org.locationtech.jts.geom.Point;
    import java.util.UUID;
    
    @GeoJson(type = GeoJsonType.FEATURE)
    @JsonSerialize(using = GeoJsonSerializer.class)
    public class Attraction {
    
       @GeoJsonId private UUID id;
       @GeoJsonProperty private String name;
       @GeoJsonProperty private String description;
       @GeoJsonGeometry private Point location;
    
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
   import com.fasterxml.jackson.core.JsonProcessingException;
   import com.fasterxml.jackson.databind.ObjectMapper;
   import org.locationtech.jts.geom.Coordinate;
   import org.locationtech.jts.geom.GeometryFactory;
   
   import static java.util.UUID.randomUUID;
    
   public static void main(String... args) throws JsonProcessingException {
      
      ObjectMapper objectMapper = new ObjectMapper();
      GeometryFactory geometryFactory = new GeometryFactory();
            
      Attraction attraction = new Attraction(
            randomUUID(),
            "Eiffel Tower",
            "Champ de Mars, 5 Avenue Anatole France, 75007 Paris, Frankreichh",
            geometryFactory.createPoint(new Coordinate(2.294527, 48.859092)));
      
      String geoJson = objectMapper.writerWithDefaultPrettyPrinter()
                                   .writeValueAsString(attraction);
   }

```

Output:
```json
    {
      "type" : "Feature",
      "id" : "2e38f4b1-d9ea-459a-ba52-6093b09b6dc0",
      "geometry" : {
        "type" : "Point",
        "coordinates" : [ 2.294527, 48.859092 ]
      },
      "properties" : {
        "name" : "Eiffel Tower",
        "description" : "Champ de Mars, 5 Avenue Anatole France, 75007 Paris, Frankreichh"
      }
    }
```

Works with Spring MVC as well:

```java
   @GetMapping("/api/attractions/{id}")
   public ResponseEntity<Attraction> findById(@PathVariable(value = "id") UUID id) {
      return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
   }
```

## Credits

Copyright (c) 2019 DTAP GmbH

Please refer to the LICENSE file for details.
