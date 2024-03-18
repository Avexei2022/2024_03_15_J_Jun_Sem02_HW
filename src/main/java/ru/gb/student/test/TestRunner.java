package ru.gb.student.test;

import ru.gb.student.test.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
    private final Class<?> testClass;
    private final Object testObj;

    public TestRunner(Class<?> testClass) {
        this.testClass = testClass;
        testObj = initTestObj(testClass);
    }

    public void run() {
        runMethods(BeforeAll.class);
        runMethods(Test.class);
        runMethods(AfterAll.class);
    }

    private Object initTestObj(Class<?> testClass) {
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать объект тест класса");
        }
    }

    private void runMethods(Class<? extends Annotation> annotationClass) {
        List<Method> methods = filterMethods(annotationClass);
        for(Method method: methods) {
            setAccessibleTrue(method);
            invokeFilteredMethod(method, annotationClass);
        }
    }

    private List<Method> filterMethods(Class<? extends Annotation> annotationClass) {
        List<Method> methods = Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .toList();
        List<Method> sortedMethods;
        if(annotationClass.equals(Test.class)) {
            sortedMethods = methods.stream()
                    .sorted(Comparator.comparingInt(m -> m.getAnnotation(Test.class).order()))
                    .toList();
        } else sortedMethods = methods;
        return sortedMethods;
    }

    private void setAccessibleTrue(Method method) {
        if(method.accessFlags()
                .contains(AccessFlag.PRIVATE))
            method.setAccessible(true);
    }

    private void invokeFilteredMethod(Method method, Class<? extends Annotation> annotationClass) {
        if(method.isAnnotationPresent(Test.class)) {
            runMethods(BeforeEach.class);
            invokeMethod(method, Test.class);
            runMethods(AfterEach.class);
        } else invokeMethod(method, annotationClass);
    }

    private void invokeMethod(Method method, Class<? extends Annotation> annotationClass) {
        if(method.isAnnotationPresent(annotationClass)) {
            try {
                method.invoke(testObj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
