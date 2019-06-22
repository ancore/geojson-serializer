package gmbh.dtap.geojson.serializer;

import gmbh.dtap.geojson.annotation.GeoJsonGeometry;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.removeStart;

class ClassUtils {

   private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

   static Member findOne(Object object, Class<? extends Annotation> annotationClass) {
      List<Member> members = scanFor(object, annotationClass);
      if (members.isEmpty()) {
         return null;
      } else {
         if (members.size() > 1) {
            throw new GeoJsonSerializerException("more than one annotation found: " + annotationClass);
         }
         return members.get(0);
      }
   }

   static Geometry findGeometry(Object object) {
      Member member = ClassUtils.findOne(object, GeoJsonGeometry.class);
      if (member != null) {
         Object value = ClassUtils.getValue(object, member, Geometry.class);
         logger.debug("foundGeometry: {}", value);
         return Geometry.class.cast(value);
      }
      return null;
   }

   static List<Member> scanFor(Object object, Class<? extends Annotation> annotationClass) {
      List<Member> members = new ArrayList<>();
      BeanInfo beanInfo = getBeanInfo(object);
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
         Method method = propertyDescriptor.getReadMethod();
         logger.debug("readMethod: {}", method);
         if (method.isAnnotationPresent(annotationClass)) {
            members.add(method);
         }
      }
      for (Field field : object.getClass().getDeclaredFields()) {
         logger.debug("field: {}", field);
         if (field.isAnnotationPresent(annotationClass)) {
            members.add(field);
         }
      }
      logger.debug("scannedFor: annotation={}, members={}", annotationClass, members);
      return members;
   }

   private static BeanInfo getBeanInfo(Object object) {
      try {
         return Introspector.getBeanInfo(object.getClass());
      } catch (IntrospectionException e) {
         throw new GeoJsonSerializerException("introspection of object failed: " + object, e);
      }
   }


   static String getName(Member member) {
      if (member instanceof Field) {
         return getNameFromField((Field) member);
      } else if (member instanceof Method) {
         return getNameFromMethod((Method) member);
      } else {
         throw new IllegalStateException("unexpected member: " + member);
      }
   }

   static String getNameFromField(Field field) {
      return field.getName();
   }

   static String getNameFromMethod(Method method) {
      return removeStart(method.getName(), "get").toLowerCase();
   }

   static <T> T getValue(Object object, Member member, Class<T> expectedClass) {
      if (member instanceof Field) {
         return getValueFromField(object, (Field) member, expectedClass);
      } else if (member instanceof Method) {
         return getValueFromMethod(object, (Method) member, expectedClass);
      } else {
         throw new IllegalStateException("unexpected member: " + member);
      }
   }

   static <T> T getValueFromField(Object object, Field field, Class<T> expectedClass) {
      Object value;
      try {
         value = FieldUtils.readField(field, object, true);
      } catch (Exception e) {
         throw new GeoJsonSerializerException("value from Field failed: object=" + object + ", field=" + field, e);
      }
      if (!expectedClass.isInstance(value)) {
         throw new GeoJsonSerializerException("value from Field is not of expected type: object=" + object + ", field=" + field + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }

   static <T> T getValueFromMethod(Object object, Method method, Class<T> expectedClass) {
      Object value;
      try {
         value = method.invoke(object);
      } catch (Exception e) {
         throw new GeoJsonSerializerException("value from Method failed: object=" + object + ", method=" + method, e);
      }
      if (!expectedClass.isInstance(value)) {
         throw new GeoJsonSerializerException("value from Method is not of expected type: object=" + object + ", method=" + method + ", expectedType=" + expectedClass);
      }
      return expectedClass.cast(value);
   }
}
