/*
 * The MIT License
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.lgooddatepicker;

import com.github.lgooddatepicker.zinternaltools.Pair;
import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TestHelpers {

  static boolean isClassAvailable(String className) {
    try {
      Class.forName(className);
    } catch (ClassNotFoundException ex) {
      return false;
    }
    return true;
  }

  public static Object readPrivateField(Class<?> clazz, Object instance, String field)
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    java.lang.reflect.Field private_field = clazz.getDeclaredField(field);
    private_field.setAccessible(true);
    return private_field.get(instance);
  }

  public static java.lang.reflect.Method accessPrivateMethod(
      Class<?> clazz, String method, Class<?>... argclasses)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    java.lang.reflect.Method private_method = clazz.getDeclaredMethod(method, argclasses);
    private_method.setAccessible(true);
    return private_method;
  }

  public static Clock getClockFixedToInstant(
      int year, Month month, int day, int hours, int minutes) {
    LocalDateTime fixedInstant =
        LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hours, minutes));
    return Clock.fixed(fixedInstant.toInstant(ZoneOffset.UTC), ZoneId.of("Z"));
  }

  // detect funcionality of UI, which is not available on most CI systems
  public static boolean isUiAvailable() {
    return !java.awt.GraphicsEnvironment.isHeadless();
  }

  public static void registerUncaughtExceptionHandlerToAllThreads(
      Thread.UncaughtExceptionHandler handler) {
    Thread.setDefaultUncaughtExceptionHandler(handler);
    // activeCount is only an estimation
    int activeCountOversize = 1;
    Thread[] threads;
    do {
      threads = new Thread[Thread.activeCount() + activeCountOversize];
      Thread.enumerate(threads);
      activeCountOversize++;
    } while (threads[threads.length - 1] != null);
    for (Thread thread : threads) {
      if (thread != null) {
        thread.setUncaughtExceptionHandler(handler);
      }
    }
  }

  public static class ExceptionInfo {
    Pair<String, Throwable> info = new Pair<>("", null);

    synchronized boolean wasSet() {
      return !info.first.isEmpty() || info.second != null;
    }

    synchronized void set(String threadname, Throwable ex) {
      info.first = threadname;
      info.second = ex;
    }

    synchronized String getThreadName() {
      return info.first;
    }

    synchronized String getExceptionMessage() {
      return info.second != null ? info.second.getMessage() : "";
    }

    synchronized String getStackTrace() {
      String result = "";
      if (info.second != null) {
        for (StackTraceElement elem : info.second.getStackTrace()) {
          result += elem.toString() + "\n";
        }
      }
      return result;
    }
  }
}
