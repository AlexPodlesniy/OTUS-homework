package ru.otus;

import com.google.common.base.CharMatcher;

/**
 *
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/gradleHelloOtus-0.1.jar
 *
 * To unzip the jar:
 * unzip -l hw01-gradle.jar
 * unzip -l gradleHelloOtus-0.1.jar
 *
 */
public class HelloOtus {
    public static void main(String... args) {
        String input = "Helр!!!..l_(~o~)_! (O_0)..2_(TUТ)_S";
        CharMatcher matcher = CharMatcher.inRange('A', 'Z').or(CharMatcher.inRange('a', 'z')).or(CharMatcher.breakingWhitespace());
        String result = matcher.retainFrom(input);
        System.out.println(result);
    }
}
