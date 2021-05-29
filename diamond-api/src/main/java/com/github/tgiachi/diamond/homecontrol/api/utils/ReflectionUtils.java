package com.github.tgiachi.diamond.homecontrol.api.utils;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ReflectionUtils {

    public static Set<Class<?>> getAnnotation(Class<? extends Annotation> type) {
        return getAnnotation(type, "com.github");
    }

    public static Class<?>[] getAnnotationArray(Class<? extends Annotation> type) {
        var set = getAnnotation(type);

        return set.toArray(new Class<?>[0]);
    }

    public static Set<Class<?>> getAnnotation(Class<? extends Annotation> type, String pkg) {
        return new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(pkg))).getTypesAnnotatedWith(type);
    }

    public static Set<Class<?>> getAnnotation(Class<? extends Annotation> type, boolean scanJcl) {
        return getAnnotation(type);
    }
}
