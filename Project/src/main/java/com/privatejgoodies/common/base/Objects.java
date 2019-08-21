/*
 * Copyright (c) 2009-2013 JGoodies Software GmbH. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Software GmbH nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.privatejgoodies.common.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Provides static methods that operate on objects.
 *
 * @author Karsten Lentzsch
 */
public final class Objects {

    private Objects() {
        // Override default constructor; prevents instantiation.
    }

    // API ********************************************************************
    /**
     * Provides a means to copy objects that do not implement Cloneable. Performs a deep copy where
     * the copied object has no references to the original object for any object that implements
     * Serializable. If the original is {@code null}, this method just returns {@code null}.
     *
     * @param <T> the type of the object to be cloned
     * @param original the object to copied, may be {@code null}
     * @return the copied object
     *
     * @since 1.1.1
     */
    public static <T extends Serializable> T deepCopy(T original) {
        if (original == null) {
            return null;
        }
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            final ObjectOutputStream oas = new ObjectOutputStream(baos);
            oas.writeObject(original);
            oas.flush();
            // close is unnecessary
            final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            final ObjectInputStream ois = new ObjectInputStream(bais);            
            @SuppressWarnings("unchecked") 
            T readObject = (T) ois.readObject();
            return readObject;
        } catch (Throwable e) {
            throw new RuntimeException("Deep copy failed", e);
        }
    }

    /**
     * Checks and answers if the two objects are both {@code null} or equal.
     *
     * <pre>
     * Objects.equals(null, null) == true
     * Objects.equals("Hi", "Hi") == true
     * Objects.equals("Hi", null) == false
     * Objects.equals(null, "Hi") == false
     * Objects.equals("Hi", "Ho") == false
     * </pre>
     *
     * @param o1 the first object to compare
     * @param o2 the second object to compare
     * @return boolean {@code true} if and only if both objects are {@code null} or equal according
     * to {@link Object#equals(Object) equals} invoked on the first object
     */
    public static boolean equals(Object o1, Object o2) {
        return o1 == o2
                || o1 != null && o1.equals(o2);
    }

}
