package homework;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static helper.ReflectionHelper.instantiate;

public class JUnitKiller {

    public static void main(String[] args) {
        runClassTests(SomeMeaninglessTest.class.getCanonicalName());
    }

    public static void runClassTests(String testClassName) {
        Class<?> testClass;
        try {
            testClass = Class.forName(testClassName);
        } catch (ClassNotFoundException e) {
            System.out.printf("Class with following name: %s not found", testClassName);
            return;
        }

        int totalAmount = 0;
        int passedAmount = 0;

        List<Method> failedTests = new ArrayList<>();

        System.out.printf("Start testing class %s\n", testClass.getCanonicalName());
        System.out.println("-".repeat(50));

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                totalAmount++;
                if (runTest(method)) {
                    passedAmount++;
                } else {
                    failedTests.add(method);
                }
            }
        }
        System.out.println("-".repeat(50));
        System.out.println(passedAmount == totalAmount ? "SUCCESS" : "FAILURE");
        System.out.printf("Passed: %d, Failed: %d, Total: %d\n", passedAmount, totalAmount - passedAmount, totalAmount);
        System.out.println("\nFailed tests:");
        for (Method method : failedTests) {
            System.out.printf("%s.%s\n", method.getDeclaringClass().getCanonicalName(), method.getName());
        }
    }

    /**
     * @param testMethod method annotated with @Test annotation
     * @return true if test passed, false if test failed
     */
    private static boolean runTest(Method testMethod) {
        boolean result = true;

        Class<?> testClass = testMethod.getDeclaringClass();
        Object testObject = instantiate(testClass);

        List<Method> testMethods = Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Before.class)).collect(Collectors.toList());
        testMethods.add(testMethod);

        List<Method> afterMethods = Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(After.class)).collect(Collectors.toList());

        try {
            for (Method method : testMethods) {
                System.out.println(getMethodExecutingMessage(method));
                method.invoke(testObject);
            }
        } catch (Exception e) {
            System.out.println(getExceptionMessage(e.getCause()));
            result = false;
        } finally {
            try {
                for (Method method : afterMethods) {
                        System.out.println(getMethodExecutingMessage(method));
                        method.invoke(testObject);
                }
            } catch (Exception ex) {
                System.out.println(getExceptionMessage(ex.getCause()));
                result = false;
            }
        }
        return result;
    }

    private static String getMethodExecutingMessage(Method method) {
        return String.format(
                "Executing %s.%s",
                method.getDeclaringClass().getCanonicalName(),
                method.getName());
    }

    private static String getExceptionMessage(Throwable ex) {
        return String.format(
                "Method has thrown an exception %s with following message: %s",
                ex.getClass().getCanonicalName(),
                ex.getMessage());
    }
}
