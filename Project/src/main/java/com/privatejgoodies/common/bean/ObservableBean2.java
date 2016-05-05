/*
 * Copyright (c) 2002-2013 JGoodies Software GmbH. All Rights Reserved.
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
package com.privatejgoodies.common.bean;

import java.beans.PropertyChangeListener;

/**
 * In addition to its super interface ObservableBean, this interface describes the optional support
 * for registering PropertyChangeListeners for an individual property name. This interface is
 * primarily intended to ensure compile-time safety for beans that shall be observed.
 *
 * @author Karsten Lentzsch
 *
 * @see PropertyChangeListener
 * @see java.beans.PropertyChangeEvent
 * @see java.beans.PropertyChangeSupport
 */
public interface ObservableBean2 extends ObservableBean {

    /**
     * Adds a PropertyChangeListener to the listener list for a specific property. The specified
     * property may be user-defined.<p>
     *
     * Note that if this bean is inheriting a bound property, then no event will be fired in
     * response to a change in the inherited property.<p>
     *
     * If listener is null, no exception is thrown and no action is performed.
     *
     * @param propertyName one of the property names listed above
     * @param listener the PropertyChangeListener to be added
     *
     * @see #removePropertyChangeListener(PropertyChangeListener)
     * @see #removePropertyChangeListener(String, PropertyChangeListener)
     * @see #addPropertyChangeListener(PropertyChangeListener)
     * @see #getPropertyChangeListeners(String)
     */
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * Removes a PropertyChangeListener from the listener list for a specific property. This method
     * should be used to remove PropertyChangeListeners that were registered for a specific bound
     * property.<p>
     *
     * If listener is null, no exception is thrown and no action is performed.
     *
     * @param propertyName a valid property name
     * @param listener the PropertyChangeListener to be removed
     *
     * @see #addPropertyChangeListener(PropertyChangeListener)
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     * @see #removePropertyChangeListener(PropertyChangeListener)
     * @see #getPropertyChangeListeners(String)
     */
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * Returns an array of all the property change listeners registered on this component.
     *
     * @return all of this component's {@code PropertyChangeListener}s or an empty array if no
     * property change listeners are currently registered
     *
     * @see #addPropertyChangeListener(PropertyChangeListener)
     * @see #removePropertyChangeListener(PropertyChangeListener)
     * @see #getPropertyChangeListeners(String)
     * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners()
     */
    PropertyChangeListener[] getPropertyChangeListeners();

    /**
     * Returns an array of all the listeners which have been associated with the named property.
     *
     * @param propertyName the name of the property to lookup listeners
     * @return all of the {@code PropertyChangeListeners} associated with the named property or an
     * empty array if no listeners have been added
     *
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     * @see #removePropertyChangeListener(String, PropertyChangeListener)
     * @see #getPropertyChangeListeners()
     */
    PropertyChangeListener[] getPropertyChangeListeners(String propertyName);

}
