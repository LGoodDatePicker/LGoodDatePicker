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

    static void suppressIllegalReflectiveAccessWarning(Class<?> targetClass, Class<?> accessingClass)
    {
        if (!isClassAvailable("java.lang.Module")) {
            // we are running in JDK without modules (JDK < 9) no need
            // to suppress warnings as only modules restrict reflective access
            return;
        }
        /* We are using the following trick here to suppress warnings regarding illegal access:
           - With the introduction of modules in JDK 9 all named modules are implicitly open for
            deep reflective access to all unnamed modules, but every access will issue an
            "illegal reflective access operation" warning
            (jvm option --illegal-access=warn is set as default)
           - Since every named module is implicitly open we can modify the named module to
             open explicitly to our unnamed module, which switches off the warning.
             This is done using the Module.addOpens() method
           - As we still support JDK 8 all code regarding modules must use reflection
             and simply does nothing if modules are not present in the JDK
           - if any future JDK sets the jvm option --illegal-access=deny as default
             then named modules are not implicitly open any more and the call to Module.addOpens()
             will fail with an IllegalCallerException. If this happens we will have to drop
             JDK 8 support and add explicit JVM arguments to our tests, which JDK 8 will fail
             to recognize. This can be done via the following Maven plugin:
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.22.2</version>
                    <configuration>
                         <argLine>--illegal-access=deny --add-opens java.desktop/java.awt=ALL-UNNAMED</argLine>
                    </configuration>
               </plugin>
        */
        try {
            final Class<?> ModuleClass = Class.forName("java.lang.Module");
            final java.lang.reflect.Method getModuleMethod = Class.class.getDeclaredMethod("getModule");
            final Object callerModule = getModuleMethod.invoke(TestHelpers.class);
            final boolean callerModuleIsNamed = (boolean) ModuleClass.getDeclaredMethod("isNamed").invoke(callerModule);
            if (callerModuleIsNamed) {
                 throw new RuntimeException("Class TestHelpers is not part of an unnamed module, "
                         + "reflective access is only implicitly granted to classes in an unnamed module");
            }
            final Object accessingModule = getModuleMethod.invoke(accessingClass);
            openClassForDeepReflectionAccessFromModule(targetClass, accessingModule);
        } catch ( ClassNotFoundException | NoSuchMethodException | SecurityException |
                 IllegalAccessException | IllegalArgumentException ex ) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

     static void openClassForDeepReflectionAccessFromModule(Class<?> target, Object accessingModule)
    {
        try {
            final Object targetModule = Class.class.getDeclaredMethod("getModule").invoke(target);
            final String targetPackageName = (String) Class.class.getMethod("getPackageName").invoke(target);
            final Class<?> ModuleClass = Class.forName("java.lang.Module");
            ModuleClass.getMethod("addOpens", String.class, ModuleClass).invoke(targetModule,
                    targetPackageName, accessingModule);
        } catch ( ClassNotFoundException | NoSuchMethodException | SecurityException |
                 IllegalAccessException | IllegalArgumentException ex ) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    static Object readPrivateField(Class<?> clazz, Object instance, String field)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        java.lang.reflect.Field private_field = clazz.getDeclaredField(field);
        private_field.setAccessible(true);
        return private_field.get(instance);
    }

    static java.lang.reflect.Method accessPrivateMethod(Class<?> clazz, String method, Class<?>... argclasses)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        java.lang.reflect.Method private_method = clazz.getDeclaredMethod(method, argclasses);
        private_method.setAccessible(true);
        return private_method;
    }

}
