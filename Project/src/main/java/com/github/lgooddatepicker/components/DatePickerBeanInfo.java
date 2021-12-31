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
package com.github.lgooddatepicker.components;

import com.github.lgooddatepicker.zinternaltools.Pair;
import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * DatePickerBeanInfo, This class returns JavaBean information for the matching bean class.
 *
 * <p>The class methods will return default BeanInfo Introspector information for properties,
 * methods, and events, plus any customizations that are applied on top of the default Introspector
 * data set.
 */
public class DatePickerBeanInfo extends SimpleBeanInfo {

  ///////////////////////////////////////////////////////////////////////////////////////////////
  // Start: Custom settings.
  ///////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * iconInformation, This holds all the information for the image icons. The map key is the
   * BeanInfo icon type. The pair string is the path to the matching icon resource. The pair image
   * will hold the matching icon image, if it has been loaded. (The image will be null until the
   * first time it is loaded.)
   */
  private static HashMap<Integer, Pair<String, Image>> iconInformation =
      new HashMap<Integer, Pair<String, Image>>() {
        {
          put(ICON_MONO_16x16, new Pair<>("/images/DatePickerIcon 16x16.png", null));
          put(ICON_COLOR_16x16, new Pair<>("/images/DatePickerIcon 16x16.png", null));
          put(ICON_MONO_32x32, new Pair<>("/images/DatePickerIcon 32x32.png", null));
          put(ICON_COLOR_32x32, new Pair<>("/images/DatePickerIcon 32x32.png", null));
        }
      };

  /**
   * preferredProperties, This lists all the properties that should be marked as preferred, as
   * lowercase strings. Any properties that are not in this list, will be marked as "not preferred".
   * (All of the defaults for preferred properties are overwritten.)
   */
  private static HashSet<String> preferredProperties = new HashSet<>(Arrays.asList("date", "text"));

  /**
   * propertyDescriptions, These are the descriptions to add to the properties. The key is the
   * property name in all lowercase. The value is the property description.
   */
  private static HashMap<String, String> propertyDescriptions =
      new HashMap<String, String>() {
        {
          put("date", "The last valid date.");
          put("text", "The date picker text.");
        }
      };

  /** targetClass, This is the class for which we are providing the BeanInfo. */
  private static Class targetClass = DatePicker.class;

  ///////////////////////////////////////////////////////////////////////////////////////////////
  // End: Custom settings.
  ///////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * zDefaultEventIndex, This holds the BeanInfo defaultEventIndex. This is initialized when the
   * class is first loaded, and not changed afterwards.
   */
  private static int zDefaultEventIndex = -1;

  /**
   * zDefaultPropertyIndex, This holds the BeanInfo defaultPropertyIndex. This is initialized when
   * the class is first loaded, and not changed afterwards.
   */
  private static int zDefaultPropertyIndex = -1;

  /**
   * zPropertyDescriptorArray, This holds the array of BeanInfo property descriptors. This is
   * initialized when the class is first loaded, and not changed afterwards.
   */
  private static PropertyDescriptor[] zPropertyDescriptorArray;

  /**
   * Static Initializer, This code is run when the class is first accessed or instantiated. This
   * initializes all the needed BeanInfo arrays.
   */
  static {
    try {
      BeanInfo info = Introspector.getBeanInfo(targetClass);
      zPropertyDescriptorArray = info.getPropertyDescriptors();
    } catch (IntrospectionException ex) {
      throw new RuntimeException(ex.getMessage());
    }
    for (PropertyDescriptor propertyDescriptor : zPropertyDescriptorArray) {
      String propertyName = propertyDescriptor.getName().toLowerCase();
      propertyDescriptor.setPreferred(preferredProperties.contains(propertyName));
      if (propertyDescriptions.containsKey(propertyName)) {
        propertyDescriptor.setShortDescription(propertyDescriptions.get(propertyName));
      }
    }
  }

  /**
   * getIcon, Returns the requested BeanInfo icon type, or null if an icon does not exist or cannot
   * be retrieved.
   */
  @Override
  public Image getIcon(int iconType) {
    Pair<String, Image> pair = iconInformation.get(iconType);
    String imagePath = pair.first;
    Image imageOrNull = pair.second;
    if ((imageOrNull == null) && (imagePath != null)) {
      imageOrNull = loadImage(imagePath);
    }
    return imageOrNull;
  }

  /**
   * getBeanDescriptor, Returns the BeanInfo BeanDescriptor, or null to use automatic introspection.
   */
  @Override
  public BeanDescriptor getBeanDescriptor() {
    return null;
  }

  /**
   * getPropertyDescriptors, Returns the BeanInfo PropertyDescriptor array, or null to use automatic
   * introspection. Each array element may be a PropertyDescriptor, or the IndexedPropertyDescriptor
   * subclass of PropertyDescriptor.
   */
  @Override
  public PropertyDescriptor[] getPropertyDescriptors() {
    return zPropertyDescriptorArray;
  }

  /**
   * getEventSetDescriptors, Returns the BeanInfo EventSetDescriptor array, or null to use automatic
   * introspection.
   */
  @Override
  public EventSetDescriptor[] getEventSetDescriptors() {
    return null;
  }

  /**
   * getMethodDescriptors, Returns the BeanInfo MethodDescriptor array, or null to use automatic
   * introspection.
   */
  @Override
  public MethodDescriptor[] getMethodDescriptors() {
    return null;
  }

  /**
   * getDefaultPropertyIndex, Returns the BeanInfo defaultPropertyIndex, or -1 if there is no
   * default property.
   */
  @Override
  public int getDefaultPropertyIndex() {
    return zDefaultPropertyIndex;
  }

  /**
   * getDefaultEventIndex, Returns the BeanInfo defaultEventIndex, or -1 if there is no default
   * property.
   */
  @Override
  public int getDefaultEventIndex() {
    return zDefaultEventIndex;
  }
}
