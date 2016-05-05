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
package com.privatejgoodies.common.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

/**
 * An abstract superclass that minimizes the effort required to provide change support for bound and
 * constrained Bean properties. This class follows the conventions and recommendations as described
 * in the <a href="http://java.sun.com/products/javabeans/docs/spec.html"
 * >Java Bean Specification</a>.<p>
 *
 * This class uses the standard {@link PropertyChangeSupport} to notify registered listeners about
 * changes. Subclasses can use different change support implementations by overriding
 * {@code createPropertyChangeSupport}, for example to ensure that notifications are sent in the
 * Event dispatch thread, or to compare old and new values with {@code ==} not {@code equals}.
 *
 * @author Karsten Lentzsch
 *
 * @see PropertyChangeEvent
 * @see PropertyChangeListener
 * @see PropertyChangeSupport
 * @see VetoableChangeListener
 * @see VetoableChangeSupport
 */
public abstract class Bean implements Serializable, ObservableBean2 {

    /**
     * If any{@code PropertyChangeListeners} have been registered, the {@code changeSupport} field
     * describes them.
     *
     * @see #addPropertyChangeListener(PropertyChangeListener)
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     * @see #removePropertyChangeListener(PropertyChangeListener)
     * @see #removePropertyChangeListener(String, PropertyChangeListener)
     * @see PropertyChangeSupport
     */
    protected transient PropertyChangeSupport changeSupport;

    /**
     * If any {@code VetoableChangeListeners} have been registered, the {@code vetoSupport} field
     * describes them.
     *
     * @see #addVetoableChangeListener(VetoableChangeListener)
     * @see #addVetoableChangeListener(String, VetoableChangeListener)
     * @see #removeVetoableChangeListener(VetoableChangeListener)
     * @see #removeVetoableChangeListener(String, VetoableChangeListener)
     * @see #fireVetoableChange(String, Object, Object)
     */
    private transient VetoableChangeSupport vetoSupport;

    // Managing Property Change Listeners **********************************
    /**
     * Adds a PropertyChangeListener to the listener list. The listener is registered for all bound
     * properties of this class.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param listener the PropertyChangeListener to be added
     *
     * @see #removePropertyChangeListener(PropertyChangeListener)
     * @see #removePropertyChangeListener(String, PropertyChangeListener)
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     * @see #getPropertyChangeListeners()
     */
    @Override
    public final synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (changeSupport == null) {
            changeSupport = createPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener from the listener list. This method should be used to remove
     * PropertyChangeListeners that were registered for all bound properties of this class.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param listener the PropertyChangeListener to be removed
     * @see #addPropertyChangeListener(PropertyChangeListener)
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     * @see #removePropertyChangeListener(String, PropertyChangeListener)
     * @see #getPropertyChangeListeners()
     */
    @Override
    public final synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        if (listener == null || changeSupport == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Adds a PropertyChangeListener to the listener list for a specific property. The specified
     * property may be user-defined.<p>
     *
     * Note that if this Model is inheriting a bound property, then no event will be fired in
     * response to a change in the inherited property.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param propertyName one of the property names listed above
     * @param listener the PropertyChangeListener to be added
     *
     * @see #removePropertyChangeListener(String, PropertyChangeListener)
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     * @see #getPropertyChangeListeners(String)
     */
    @Override
    public final synchronized void addPropertyChangeListener(
            String propertyName,
            PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (changeSupport == null) {
            changeSupport = createPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a PropertyChangeListener from the listener list for a specific property. This method
     * should be used to remove PropertyChangeListeners that were registered for a specific bound
     * property.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param propertyName a valid property name
     * @param listener the PropertyChangeListener to be removed
     *
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     * @see #removePropertyChangeListener(PropertyChangeListener)
     * @see #getPropertyChangeListeners(String)
     */
    @Override
    public final synchronized void removePropertyChangeListener(
            String propertyName,
            PropertyChangeListener listener) {
        if (listener == null || changeSupport == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    // Managing Vetoable Change Listeners ***********************************
    /**
     * Adds a VetoableChangeListener to the listener list. The listener is registered for all bound
     * properties of this class.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param listener the VetoableChangeListener to be added
     *
     * @see #removeVetoableChangeListener(String, VetoableChangeListener)
     * @see #addVetoableChangeListener(String, VetoableChangeListener)
     * @see #getVetoableChangeListeners()
     */
    public final synchronized void addVetoableChangeListener(
            VetoableChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (vetoSupport == null) {
            vetoSupport = new VetoableChangeSupport(this);
        }
        vetoSupport.addVetoableChangeListener(listener);
    }

    /**
     * Removes a VetoableChangeListener from the listener list. This method should be used to remove
     * VetoableChangeListeners that were registered for all bound properties of this class.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param listener the VetoableChangeListener to be removed
     *
     * @see #addVetoableChangeListener(String, VetoableChangeListener)
     * @see #removeVetoableChangeListener(String, VetoableChangeListener)
     * @see #getVetoableChangeListeners()
     */
    public final synchronized void removeVetoableChangeListener(
            VetoableChangeListener listener) {
        if (listener == null || vetoSupport == null) {
            return;
        }
        vetoSupport.removeVetoableChangeListener(listener);
    }

    /**
     * Adds a VetoableChangeListener to the listener list for a specific property. The specified
     * property may be user-defined.<p>
     *
     * Note that if this Model is inheriting a bound property, then no event will be fired in
     * response to a change in the inherited property.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param propertyName one of the property names listed above
     * @param listener the VetoableChangeListener to be added
     *
     * @see #removeVetoableChangeListener(String, VetoableChangeListener)
     * @see #addVetoableChangeListener(String, VetoableChangeListener)
     * @see #getVetoableChangeListeners(String)
     */
    public final synchronized void addVetoableChangeListener(
            String propertyName,
            VetoableChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (vetoSupport == null) {
            vetoSupport = new VetoableChangeSupport(this);
        }
        vetoSupport.addVetoableChangeListener(propertyName, listener);
    }

    /**
     * Removes a VetoableChangeListener from the listener list for a specific property. This method
     * should be used to remove VetoableChangeListeners that were registered for a specific bound
     * property.<p>
     *
     * If listener is {@code null}, no exception is thrown and no action is performed.
     *
     * @param propertyName a valid property name
     * @param listener the VetoableChangeListener to be removed
     *
     * @see #addVetoableChangeListener(String, VetoableChangeListener)
     * @see #removeVetoableChangeListener(VetoableChangeListener)
     * @see #getVetoableChangeListeners(String)
     */
    public final synchronized void removeVetoableChangeListener(
            String propertyName,
            VetoableChangeListener listener) {
        if (listener == null || vetoSupport == null) {
            return;
        }
        vetoSupport.removeVetoableChangeListener(propertyName, listener);
    }

    // Requesting Listener Sets ***********************************************
    /**
     * Returns an array of all the property change listeners registered on this component.
     *
     * @return all of this component's {@code PropertyChangeListener}s or an empty array if no
     * property change listeners are currently registered
     *
     * @see #addPropertyChangeListener(PropertyChangeListener)
     * @see #removePropertyChangeListener(PropertyChangeListener)
     * @see #getPropertyChangeListeners(String)
     * @see PropertyChangeSupport#getPropertyChangeListeners()
     */
    @Override
    public final synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        if (changeSupport == null) {
            return new PropertyChangeListener[0];
        }
        return changeSupport.getPropertyChangeListeners();
    }

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
    @Override
    public final synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        if (changeSupport == null) {
            return new PropertyChangeListener[0];
        }
        return changeSupport.getPropertyChangeListeners(propertyName);
    }

    /**
     * Returns an array of all the property change listeners registered on this component.
     *
     * @return all of this component's {@code VetoableChangeListener}s or an empty array if no
     * property change listeners are currently registered
     *
     * @see #addVetoableChangeListener(VetoableChangeListener)
     * @see #removeVetoableChangeListener(VetoableChangeListener)
     * @see #getVetoableChangeListeners(String)
     * @see VetoableChangeSupport#getVetoableChangeListeners()
     */
    public final synchronized VetoableChangeListener[] getVetoableChangeListeners() {
        if (vetoSupport == null) {
            return new VetoableChangeListener[0];
        }
        return vetoSupport.getVetoableChangeListeners();
    }

    /**
     * Returns an array of all the listeners which have been associated with the named property.
     *
     * @param propertyName the name of the property to lookup listeners
     * @return all of the {@code VetoableChangeListeners} associated with the named property or an
     * empty array if no listeners have been added
     *
     * @see #addVetoableChangeListener(String, VetoableChangeListener)
     * @see #removeVetoableChangeListener(String, VetoableChangeListener)
     * @see #getVetoableChangeListeners()
     */
    public final synchronized VetoableChangeListener[] getVetoableChangeListeners(String propertyName) {
        if (vetoSupport == null) {
            return new VetoableChangeListener[0];
        }
        return vetoSupport.getVetoableChangeListeners(propertyName);
    }

    /**
     * Creates and returns a PropertyChangeSupport for the given bean. Invoked by the first call to
     * {@link #addPropertyChangeListener} when lazily creating the sole change support instance used
     * throughout this bean.<p>
     *
     * This default implementation creates a {@code PropertyChangeSupport}. Subclasses may override
     * to return other change support implementations. For example to ensure that listeners are
     * notified in the Event dispatch thread (EDT change support). The JGoodies Binding uses an
     * extended change support that allows to configure whether the old and new value are compared
     * with {@code ==} or {@code equals}.
     *
     * @param bean the bean to create a change support for
     * @return the new change support
     */
    protected PropertyChangeSupport createPropertyChangeSupport(final Object bean) {
        return new PropertyChangeSupport(bean);
    }

    // Firing Changes for Bound Properties **********************************
    /**
     * General support for reporting bound property changes. Sends the given PropertyChangeEvent to
     * any registered PropertyChangeListener.<p>
     *
     * Most bean setters will invoke the fireXXX methods that get a property name and the old and
     * new value. However some frameworks and setters may prefer to use this general method. Also,
     * this method allows to fire IndexedPropertyChangeEvents that have been introduced in Java 5.
     *
     * @param event describes the property change
     *
     * @since 1.3
     */
    protected final void firePropertyChange(PropertyChangeEvent event) {
        PropertyChangeSupport aChangeSupport = this.changeSupport;
        if (aChangeSupport == null) {
            return;
        }
        aChangeSupport.firePropertyChange(event);
    }

    /**
     * Support for reporting bound property changes for Object properties. This method can be called
     * when a bound property has changed and it will send the appropriate PropertyChangeEvent to any
     * registered PropertyChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     */
    protected final void firePropertyChange(String propertyName,
            Object oldValue,
            Object newValue) {
        PropertyChangeSupport aChangeSupport = this.changeSupport;
        if (aChangeSupport == null) {
            return;
        }
        aChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Support for reporting bound property changes for boolean properties. This method can be
     * called when a bound property has changed and it will send the appropriate PropertyChangeEvent
     * to any registered PropertyChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     */
    protected final void firePropertyChange(String propertyName,
            boolean oldValue,
            boolean newValue) {
        PropertyChangeSupport aChangeSupport = this.changeSupport;
        if (aChangeSupport == null) {
            return;
        }
        aChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Support for reporting bound property changes for integer properties. This method can be
     * called when a bound property has changed and it will send the appropriate PropertyChangeEvent
     * to any registered PropertyChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     */
    protected final void firePropertyChange(String propertyName,
            double oldValue,
            double newValue) {
        firePropertyChange(propertyName, Double.valueOf(oldValue), Double.valueOf(newValue));
    }

    /**
     * Support for reporting bound property changes for integer properties. This method can be
     * called when a bound property has changed and it will send the appropriate PropertyChangeEvent
     * to any registered PropertyChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     */
    protected final void firePropertyChange(String propertyName,
            float oldValue,
            float newValue) {
        firePropertyChange(propertyName, Float.valueOf(oldValue), Float.valueOf(newValue));
    }

    /**
     * Support for reporting bound property changes for integer properties. This method can be
     * called when a bound property has changed and it will send the appropriate PropertyChangeEvent
     * to any registered PropertyChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     */
    protected final void firePropertyChange(String propertyName,
            int oldValue,
            int newValue) {
        PropertyChangeSupport aChangeSupport = this.changeSupport;
        if (aChangeSupport == null) {
            return;
        }
        aChangeSupport.firePropertyChange(propertyName,
                Integer.valueOf(oldValue), Integer.valueOf(newValue));
    }

    /**
     * Support for reporting bound property changes for integer properties. This method can be
     * called when a bound property has changed and it will send the appropriate PropertyChangeEvent
     * to any registered PropertyChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     */
    protected final void firePropertyChange(String propertyName,
            long oldValue,
            long newValue) {
        firePropertyChange(propertyName, Long.valueOf(oldValue), Long.valueOf(newValue));
    }

    /**
     * Indicates that an arbitrary set of bound properties have changed. Sends a PropertyChangeEvent
     * with property name, old and new value set to {@code null} to any registered
     * PropertyChangeListeners.
     *
     * @see java.beans.PropertyChangeEvent
     *
     * @since 1.0.3
     */
    protected final void fireMultiplePropertiesChanged() {
        firePropertyChange(null, null, null);
    }

    // Firing Indexed Changes *************************************************
    /**
     * Report a bound indexed property update to any registered listeners.<p>
     *
     * No event is fired if old and new values are equal and non-null.
     *
     * @param propertyName The programmatic name of the property that was changed.
     * @param index index of the property element that was changed.
     * @param oldValue The old value of the property.
     * @param newValue The new value of the property.
     *
     * @since 2.0
     */
    protected final void fireIndexedPropertyChange(String propertyName, int index,
            Object oldValue, Object newValue) {
        PropertyChangeSupport aChangeSupport = this.changeSupport;
        if (aChangeSupport == null) {
            return;
        }
        aChangeSupport.fireIndexedPropertyChange(propertyName, index,
                oldValue, newValue);
    }

    /**
     * Report an {@code int} bound indexed property update to any registered listeners.<p>
     *
     * No event is fired if old and new values are equal and non-null.<p>
     *
     * This is merely a convenience wrapper around the more general fireIndexedPropertyChange method
     * which takes Object values.
     *
     * @param propertyName The programmatic name of the property that was changed.
     * @param index index of the property element that was changed.
     * @param oldValue The old value of the property.
     * @param newValue The new value of the property.
     *
     * @since 2.0
     */
    protected final void fireIndexedPropertyChange(String propertyName, int index,
            int oldValue, int newValue) {
        if (oldValue == newValue) {
            return;
        }
        fireIndexedPropertyChange(propertyName, index,
                Integer.valueOf(oldValue),
                Integer.valueOf(newValue));
    }

    /**
     * Report a {@code boolean} bound indexed property update to any registered listeners.<p>
     *
     * No event is fired if old and new values are equal and non-null.<p>
     *
     * This is merely a convenience wrapper around the more general fireIndexedPropertyChange method
     * which takes Object values.
     *
     * @param propertyName The programmatic name of the property that was changed.
     * @param index index of the property element that was changed.
     * @param oldValue The old value of the property.
     * @param newValue The new value of the property.
     *
     * @since 2.0
     */
    protected final void fireIndexedPropertyChange(String propertyName, int index,
            boolean oldValue, boolean newValue) {
        if (oldValue == newValue) {
            return;
        }
        fireIndexedPropertyChange(propertyName, index,
                Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
    }

    // Firing Changes for Constrained Properties ****************************
    /**
     * General support for reporting constrained property changes. Sends the given
     * PropertyChangeEvent to any registered PropertyChangeListener.<p>
     *
     * Most bean setters will invoke the fireXXX methods that get a property name and the old and
     * new value. However some frameworks and setters may prefer to use this general method. Also,
     * this method allows to fire IndexedPropertyChangeEvents that have been introduced in Java 5.
     *
     * @param event describes the property change
     * @throws PropertyVetoException if a constrained property change is rejected
     *
     * @since 1.3
     */
    protected final void fireVetoableChange(PropertyChangeEvent event)
            throws PropertyVetoException {
        VetoableChangeSupport aVetoSupport = this.vetoSupport;
        if (aVetoSupport == null) {
            return;
        }
        aVetoSupport.fireVetoableChange(event);
    }

    /**
     * Support for reporting changes for constrained Object properties. This method can be called
     * before a constrained property will be changed and it will send the appropriate
     * PropertyChangeEvent to any registered VetoableChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     * @throws PropertyVetoException if a constrained property change is rejected
     */
    protected final void fireVetoableChange(String propertyName,
            Object oldValue,
            Object newValue)
            throws PropertyVetoException {
        VetoableChangeSupport aVetoSupport = this.vetoSupport;
        if (aVetoSupport == null) {
            return;
        }
        aVetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
    }

    /**
     * Support for reporting changes for constrained boolean properties. This method can be called
     * before a constrained property will be changed and it will send the appropriate
     * PropertyChangeEvent to any registered VetoableChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     * @throws PropertyVetoException if a constrained property change is rejected
     */
    protected final void fireVetoableChange(String propertyName,
            boolean oldValue,
            boolean newValue)
            throws PropertyVetoException {
        VetoableChangeSupport aVetoSupport = this.vetoSupport;
        if (aVetoSupport == null) {
            return;
        }
        aVetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
    }

    /**
     * Support for reporting changes for constrained integer properties. This method can be called
     * before a constrained property will be changed and it will send the appropriate
     * PropertyChangeEvent to any registered VetoableChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     * @throws PropertyVetoException if a constrained property change is rejected
     */
    protected final void fireVetoableChange(String propertyName,
            double oldValue,
            double newValue)
            throws PropertyVetoException {
        fireVetoableChange(propertyName, Double.valueOf(oldValue), Double.valueOf(newValue));
    }

    /**
     * Support for reporting changes for constrained integer properties. This method can be called
     * before a constrained property will be changed and it will send the appropriate
     * PropertyChangeEvent to any registered VetoableChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     * @throws PropertyVetoException if a constrained property change is rejected
     */
    protected final void fireVetoableChange(String propertyName,
            int oldValue,
            int newValue)
            throws PropertyVetoException {
        VetoableChangeSupport aVetoSupport = this.vetoSupport;
        if (aVetoSupport == null) {
            return;
        }
        aVetoSupport.fireVetoableChange(propertyName,
                Integer.valueOf(oldValue), Integer.valueOf(newValue));
    }

    /**
     * Support for reporting changes for constrained integer properties. This method can be called
     * before a constrained property will be changed and it will send the appropriate
     * PropertyChangeEvent to any registered VetoableChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     * @throws PropertyVetoException if a constrained property change is rejected
     */
    protected final void fireVetoableChange(String propertyName,
            float oldValue,
            float newValue)
            throws PropertyVetoException {
        fireVetoableChange(propertyName, Float.valueOf(oldValue), Float.valueOf(newValue));
    }

    /**
     * Support for reporting changes for constrained integer properties. This method can be called
     * before a constrained property will be changed and it will send the appropriate
     * PropertyChangeEvent to any registered VetoableChangeListeners.
     *
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     * @throws PropertyVetoException if a constrained property change is rejected
     */
    protected final void fireVetoableChange(String propertyName,
            long oldValue,
            long newValue)
            throws PropertyVetoException {
        fireVetoableChange(propertyName, Long.valueOf(oldValue), Long.valueOf(newValue));
    }

}
