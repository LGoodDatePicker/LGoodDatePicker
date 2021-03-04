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

import java.lang.reflect.InvocationTargetException;

public class TestHelpers
{

    static boolean isClassAvailable(String className)
    {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException ex) {
            return false;
        }
        return true;
    }

    public static Object readPrivateField(Class<?> clazz, Object instance, String field)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        java.lang.reflect.Field private_field = clazz.getDeclaredField(field);
        private_field.setAccessible(true);
        return private_field.get(instance);
    }

    public static java.lang.reflect.Method accessPrivateMethod(Class<?> clazz, String method, Class<?>... argclasses)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        java.lang.reflect.Method private_method = clazz.getDeclaredMethod(method, argclasses);
        private_method.setAccessible(true);
        return private_method;
    }

}
