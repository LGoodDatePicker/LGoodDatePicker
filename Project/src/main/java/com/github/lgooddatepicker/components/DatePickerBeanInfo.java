package com.github.lgooddatepicker.components;

import java.beans.*;

public class DatePickerBeanInfo extends SimpleBeanInfo {

    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( com.github.lgooddatepicker.components.DatePicker.class , null ); // NOI18N//GEN-HEADEREND:BeanDescriptor
        // Here you can add code for customizing the BeanDescriptor.

        return beanDescriptor;     }//GEN-LAST:BeanDescriptor

    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_accessibleContext = 0;
    private static final int PROPERTY_actionMap = 1;
    private static final int PROPERTY_alignmentX = 2;
    private static final int PROPERTY_alignmentY = 3;
    private static final int PROPERTY_ancestorListeners = 4;
    private static final int PROPERTY_autoscrolls = 5;
    private static final int PROPERTY_background = 6;
    private static final int PROPERTY_backgroundSet = 7;
    private static final int PROPERTY_baselineResizeBehavior = 8;
    private static final int PROPERTY_border = 9;
    private static final int PROPERTY_bounds = 10;
    private static final int PROPERTY_colorModel = 11;
    private static final int PROPERTY_component = 12;
    private static final int PROPERTY_componentCount = 13;
    private static final int PROPERTY_componentDateTextField = 14;
    private static final int PROPERTY_componentListeners = 15;
    private static final int PROPERTY_componentOrientation = 16;
    private static final int PROPERTY_componentPopupMenu = 17;
    private static final int PROPERTY_components = 18;
    private static final int PROPERTY_componentToggleCalendarButton = 19;
    private static final int PROPERTY_containerListeners = 20;
    private static final int PROPERTY_cursor = 21;
    private static final int PROPERTY_cursorSet = 22;
    private static final int PROPERTY_date = 23;
    private static final int PROPERTY_dateChangeListeners = 24;
    private static final int PROPERTY_dateStringOrEmptyString = 25;
    private static final int PROPERTY_debugGraphicsOptions = 26;
    private static final int PROPERTY_displayable = 27;
    private static final int PROPERTY_doubleBuffered = 28;
    private static final int PROPERTY_dropTarget = 29;
    private static final int PROPERTY_enabled = 30;
    private static final int PROPERTY_focusable = 31;
    private static final int PROPERTY_focusCycleRoot = 32;
    private static final int PROPERTY_focusCycleRootAncestor = 33;
    private static final int PROPERTY_focusListeners = 34;
    private static final int PROPERTY_focusOwner = 35;
    private static final int PROPERTY_focusTraversable = 36;
    private static final int PROPERTY_focusTraversalKeys = 37;
    private static final int PROPERTY_focusTraversalKeysEnabled = 38;
    private static final int PROPERTY_focusTraversalPolicy = 39;
    private static final int PROPERTY_focusTraversalPolicyProvider = 40;
    private static final int PROPERTY_focusTraversalPolicySet = 41;
    private static final int PROPERTY_font = 42;
    private static final int PROPERTY_fontSet = 43;
    private static final int PROPERTY_foreground = 44;
    private static final int PROPERTY_foregroundSet = 45;
    private static final int PROPERTY_graphics = 46;
    private static final int PROPERTY_graphicsConfiguration = 47;
    private static final int PROPERTY_height = 48;
    private static final int PROPERTY_hierarchyBoundsListeners = 49;
    private static final int PROPERTY_hierarchyListeners = 50;
    private static final int PROPERTY_ignoreRepaint = 51;
    private static final int PROPERTY_inheritsPopupMenu = 52;
    private static final int PROPERTY_inputContext = 53;
    private static final int PROPERTY_inputMap = 54;
    private static final int PROPERTY_inputMethodListeners = 55;
    private static final int PROPERTY_inputMethodRequests = 56;
    private static final int PROPERTY_inputVerifier = 57;
    private static final int PROPERTY_insets = 58;
    private static final int PROPERTY_keyListeners = 59;
    private static final int PROPERTY_layout = 60;
    private static final int PROPERTY_lightweight = 61;
    private static final int PROPERTY_locale = 62;
    private static final int PROPERTY_location = 63;
    private static final int PROPERTY_locationOnScreen = 64;
    private static final int PROPERTY_managingFocus = 65;
    private static final int PROPERTY_maximumSize = 66;
    private static final int PROPERTY_maximumSizeSet = 67;
    private static final int PROPERTY_minimumSize = 68;
    private static final int PROPERTY_minimumSizeSet = 69;
    private static final int PROPERTY_mouseListeners = 70;
    private static final int PROPERTY_mouseMotionListeners = 71;
    private static final int PROPERTY_mousePosition = 72;
    private static final int PROPERTY_mouseWheelListeners = 73;
    private static final int PROPERTY_name = 74;
    private static final int PROPERTY_nextFocusableComponent = 75;
    private static final int PROPERTY_opaque = 76;
    private static final int PROPERTY_optimizedDrawingEnabled = 77;
    private static final int PROPERTY_paintingForPrint = 78;
    private static final int PROPERTY_paintingTile = 79;
    private static final int PROPERTY_parent = 80;
    private static final int PROPERTY_peer = 81;
    private static final int PROPERTY_popupOpen = 82;
    private static final int PROPERTY_preferredSize = 83;
    private static final int PROPERTY_preferredSizeSet = 84;
    private static final int PROPERTY_propertyChangeListeners = 85;
    private static final int PROPERTY_registeredKeyStrokes = 86;
    private static final int PROPERTY_requestFocusEnabled = 87;
    private static final int PROPERTY_rootPane = 88;
    private static final int PROPERTY_settings = 89;
    private static final int PROPERTY_showing = 90;
    private static final int PROPERTY_size = 91;
    private static final int PROPERTY_text = 92;
    private static final int PROPERTY_textFieldValid = 93;
    private static final int PROPERTY_toolkit = 94;
    private static final int PROPERTY_toolTipText = 95;
    private static final int PROPERTY_topLevelAncestor = 96;
    private static final int PROPERTY_transferHandler = 97;
    private static final int PROPERTY_treeLock = 98;
    private static final int PROPERTY_UI = 99;
    private static final int PROPERTY_UIClassID = 100;
    private static final int PROPERTY_valid = 101;
    private static final int PROPERTY_validateRoot = 102;
    private static final int PROPERTY_verifyInputWhenFocusTarget = 103;
    private static final int PROPERTY_vetoableChangeListeners = 104;
    private static final int PROPERTY_visible = 105;
    private static final int PROPERTY_visibleRect = 106;
    private static final int PROPERTY_width = 107;
    private static final int PROPERTY_x = 108;
    private static final int PROPERTY_y = 109;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[110];
    
        try {
            properties[PROPERTY_accessibleContext] = new PropertyDescriptor ( "accessibleContext", com.github.lgooddatepicker.components.DatePicker.class, "getAccessibleContext", null ); // NOI18N
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", com.github.lgooddatepicker.components.DatePicker.class, "getActionMap", "setActionMap" ); // NOI18N
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", com.github.lgooddatepicker.components.DatePicker.class, "getAlignmentX", "setAlignmentX" ); // NOI18N
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", com.github.lgooddatepicker.components.DatePicker.class, "getAlignmentY", "setAlignmentY" ); // NOI18N
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", com.github.lgooddatepicker.components.DatePicker.class, "getAncestorListeners", null ); // NOI18N
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", com.github.lgooddatepicker.components.DatePicker.class, "getAutoscrolls", "setAutoscrolls" ); // NOI18N
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", com.github.lgooddatepicker.components.DatePicker.class, "getBackground", "setBackground" ); // NOI18N
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", com.github.lgooddatepicker.components.DatePicker.class, "isBackgroundSet", null ); // NOI18N
            properties[PROPERTY_baselineResizeBehavior] = new PropertyDescriptor ( "baselineResizeBehavior", com.github.lgooddatepicker.components.DatePicker.class, "getBaselineResizeBehavior", null ); // NOI18N
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", com.github.lgooddatepicker.components.DatePicker.class, "getBorder", "setBorder" ); // NOI18N
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", com.github.lgooddatepicker.components.DatePicker.class, "getBounds", "setBounds" ); // NOI18N
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", com.github.lgooddatepicker.components.DatePicker.class, "getColorModel", null ); // NOI18N
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", com.github.lgooddatepicker.components.DatePicker.class, null, null, "getComponent", null ); // NOI18N
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", com.github.lgooddatepicker.components.DatePicker.class, "getComponentCount", null ); // NOI18N
            properties[PROPERTY_componentDateTextField] = new PropertyDescriptor ( "componentDateTextField", com.github.lgooddatepicker.components.DatePicker.class, "getComponentDateTextField", null ); // NOI18N
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", com.github.lgooddatepicker.components.DatePicker.class, "getComponentListeners", null ); // NOI18N
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", com.github.lgooddatepicker.components.DatePicker.class, "getComponentOrientation", "setComponentOrientation" ); // NOI18N
            properties[PROPERTY_componentPopupMenu] = new PropertyDescriptor ( "componentPopupMenu", com.github.lgooddatepicker.components.DatePicker.class, "getComponentPopupMenu", "setComponentPopupMenu" ); // NOI18N
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", com.github.lgooddatepicker.components.DatePicker.class, "getComponents", null ); // NOI18N
            properties[PROPERTY_componentToggleCalendarButton] = new PropertyDescriptor ( "componentToggleCalendarButton", com.github.lgooddatepicker.components.DatePicker.class, "getComponentToggleCalendarButton", null ); // NOI18N
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", com.github.lgooddatepicker.components.DatePicker.class, "getContainerListeners", null ); // NOI18N
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", com.github.lgooddatepicker.components.DatePicker.class, "getCursor", "setCursor" ); // NOI18N
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", com.github.lgooddatepicker.components.DatePicker.class, "isCursorSet", null ); // NOI18N
            properties[PROPERTY_date] = new PropertyDescriptor ( "date", com.github.lgooddatepicker.components.DatePicker.class, "getDate", "setDate" ); // NOI18N
            properties[PROPERTY_date].setPreferred ( true );
            properties[PROPERTY_dateChangeListeners] = new PropertyDescriptor ( "dateChangeListeners", com.github.lgooddatepicker.components.DatePicker.class, "getDateChangeListeners", null ); // NOI18N
            properties[PROPERTY_dateStringOrEmptyString] = new PropertyDescriptor ( "dateStringOrEmptyString", com.github.lgooddatepicker.components.DatePicker.class, "getDateStringOrEmptyString", null ); // NOI18N
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", com.github.lgooddatepicker.components.DatePicker.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" ); // NOI18N
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", com.github.lgooddatepicker.components.DatePicker.class, "isDisplayable", null ); // NOI18N
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", com.github.lgooddatepicker.components.DatePicker.class, "isDoubleBuffered", "setDoubleBuffered" ); // NOI18N
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", com.github.lgooddatepicker.components.DatePicker.class, "getDropTarget", "setDropTarget" ); // NOI18N
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", com.github.lgooddatepicker.components.DatePicker.class, "isEnabled", "setEnabled" ); // NOI18N
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", com.github.lgooddatepicker.components.DatePicker.class, "isFocusable", "setFocusable" ); // NOI18N
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", com.github.lgooddatepicker.components.DatePicker.class, "isFocusCycleRoot", "setFocusCycleRoot" ); // NOI18N
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", com.github.lgooddatepicker.components.DatePicker.class, "getFocusCycleRootAncestor", null ); // NOI18N
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", com.github.lgooddatepicker.components.DatePicker.class, "getFocusListeners", null ); // NOI18N
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", com.github.lgooddatepicker.components.DatePicker.class, "isFocusOwner", null ); // NOI18N
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", com.github.lgooddatepicker.components.DatePicker.class, "isFocusTraversable", null ); // NOI18N
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", com.github.lgooddatepicker.components.DatePicker.class, null, null, null, "setFocusTraversalKeys" ); // NOI18N
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", com.github.lgooddatepicker.components.DatePicker.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", com.github.lgooddatepicker.components.DatePicker.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicyProvider] = new PropertyDescriptor ( "focusTraversalPolicyProvider", com.github.lgooddatepicker.components.DatePicker.class, "isFocusTraversalPolicyProvider", "setFocusTraversalPolicyProvider" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", com.github.lgooddatepicker.components.DatePicker.class, "isFocusTraversalPolicySet", null ); // NOI18N
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", com.github.lgooddatepicker.components.DatePicker.class, "getFont", "setFont" ); // NOI18N
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", com.github.lgooddatepicker.components.DatePicker.class, "isFontSet", null ); // NOI18N
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", com.github.lgooddatepicker.components.DatePicker.class, "getForeground", "setForeground" ); // NOI18N
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", com.github.lgooddatepicker.components.DatePicker.class, "isForegroundSet", null ); // NOI18N
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", com.github.lgooddatepicker.components.DatePicker.class, "getGraphics", null ); // NOI18N
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", com.github.lgooddatepicker.components.DatePicker.class, "getGraphicsConfiguration", null ); // NOI18N
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", com.github.lgooddatepicker.components.DatePicker.class, "getHeight", null ); // NOI18N
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", com.github.lgooddatepicker.components.DatePicker.class, "getHierarchyBoundsListeners", null ); // NOI18N
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", com.github.lgooddatepicker.components.DatePicker.class, "getHierarchyListeners", null ); // NOI18N
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", com.github.lgooddatepicker.components.DatePicker.class, "getIgnoreRepaint", "setIgnoreRepaint" ); // NOI18N
            properties[PROPERTY_inheritsPopupMenu] = new PropertyDescriptor ( "inheritsPopupMenu", com.github.lgooddatepicker.components.DatePicker.class, "getInheritsPopupMenu", "setInheritsPopupMenu" ); // NOI18N
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", com.github.lgooddatepicker.components.DatePicker.class, "getInputContext", null ); // NOI18N
            properties[PROPERTY_inputMap] = new PropertyDescriptor ( "inputMap", com.github.lgooddatepicker.components.DatePicker.class, "getInputMap", null ); // NOI18N
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", com.github.lgooddatepicker.components.DatePicker.class, "getInputMethodListeners", null ); // NOI18N
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", com.github.lgooddatepicker.components.DatePicker.class, "getInputMethodRequests", null ); // NOI18N
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", com.github.lgooddatepicker.components.DatePicker.class, "getInputVerifier", "setInputVerifier" ); // NOI18N
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", com.github.lgooddatepicker.components.DatePicker.class, "getInsets", null ); // NOI18N
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", com.github.lgooddatepicker.components.DatePicker.class, "getKeyListeners", null ); // NOI18N
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", com.github.lgooddatepicker.components.DatePicker.class, "getLayout", "setLayout" ); // NOI18N
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", com.github.lgooddatepicker.components.DatePicker.class, "isLightweight", null ); // NOI18N
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", com.github.lgooddatepicker.components.DatePicker.class, "getLocale", "setLocale" ); // NOI18N
            properties[PROPERTY_location] = new PropertyDescriptor ( "location", com.github.lgooddatepicker.components.DatePicker.class, "getLocation", "setLocation" ); // NOI18N
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", com.github.lgooddatepicker.components.DatePicker.class, "getLocationOnScreen", null ); // NOI18N
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", com.github.lgooddatepicker.components.DatePicker.class, "isManagingFocus", null ); // NOI18N
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", com.github.lgooddatepicker.components.DatePicker.class, "getMaximumSize", "setMaximumSize" ); // NOI18N
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", com.github.lgooddatepicker.components.DatePicker.class, "isMaximumSizeSet", null ); // NOI18N
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", com.github.lgooddatepicker.components.DatePicker.class, "getMinimumSize", "setMinimumSize" ); // NOI18N
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", com.github.lgooddatepicker.components.DatePicker.class, "isMinimumSizeSet", null ); // NOI18N
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", com.github.lgooddatepicker.components.DatePicker.class, "getMouseListeners", null ); // NOI18N
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", com.github.lgooddatepicker.components.DatePicker.class, "getMouseMotionListeners", null ); // NOI18N
            properties[PROPERTY_mousePosition] = new PropertyDescriptor ( "mousePosition", com.github.lgooddatepicker.components.DatePicker.class, "getMousePosition", null ); // NOI18N
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", com.github.lgooddatepicker.components.DatePicker.class, "getMouseWheelListeners", null ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", com.github.lgooddatepicker.components.DatePicker.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", com.github.lgooddatepicker.components.DatePicker.class, "getNextFocusableComponent", "setNextFocusableComponent" ); // NOI18N
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", com.github.lgooddatepicker.components.DatePicker.class, "isOpaque", "setOpaque" ); // NOI18N
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", com.github.lgooddatepicker.components.DatePicker.class, "isOptimizedDrawingEnabled", null ); // NOI18N
            properties[PROPERTY_paintingForPrint] = new PropertyDescriptor ( "paintingForPrint", com.github.lgooddatepicker.components.DatePicker.class, "isPaintingForPrint", null ); // NOI18N
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", com.github.lgooddatepicker.components.DatePicker.class, "isPaintingTile", null ); // NOI18N
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", com.github.lgooddatepicker.components.DatePicker.class, "getParent", null ); // NOI18N
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", com.github.lgooddatepicker.components.DatePicker.class, "getPeer", null ); // NOI18N
            properties[PROPERTY_popupOpen] = new PropertyDescriptor ( "popupOpen", com.github.lgooddatepicker.components.DatePicker.class, "isPopupOpen", null ); // NOI18N
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", com.github.lgooddatepicker.components.DatePicker.class, "getPreferredSize", "setPreferredSize" ); // NOI18N
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", com.github.lgooddatepicker.components.DatePicker.class, "isPreferredSizeSet", null ); // NOI18N
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", com.github.lgooddatepicker.components.DatePicker.class, "getPropertyChangeListeners", null ); // NOI18N
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", com.github.lgooddatepicker.components.DatePicker.class, "getRegisteredKeyStrokes", null ); // NOI18N
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", com.github.lgooddatepicker.components.DatePicker.class, "isRequestFocusEnabled", "setRequestFocusEnabled" ); // NOI18N
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", com.github.lgooddatepicker.components.DatePicker.class, "getRootPane", null ); // NOI18N
            properties[PROPERTY_settings] = new PropertyDescriptor ( "settings", com.github.lgooddatepicker.components.DatePicker.class, "getSettings", "setSettings" ); // NOI18N
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", com.github.lgooddatepicker.components.DatePicker.class, "isShowing", null ); // NOI18N
            properties[PROPERTY_size] = new PropertyDescriptor ( "size", com.github.lgooddatepicker.components.DatePicker.class, "getSize", "setSize" ); // NOI18N
            properties[PROPERTY_text] = new PropertyDescriptor ( "text", com.github.lgooddatepicker.components.DatePicker.class, "getText", "setText" ); // NOI18N
            properties[PROPERTY_text].setPreferred ( true );
            properties[PROPERTY_textFieldValid] = new PropertyDescriptor ( "textFieldValid", com.github.lgooddatepicker.components.DatePicker.class, "isTextFieldValid", null ); // NOI18N
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", com.github.lgooddatepicker.components.DatePicker.class, "getToolkit", null ); // NOI18N
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", com.github.lgooddatepicker.components.DatePicker.class, "getToolTipText", "setToolTipText" ); // NOI18N
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", com.github.lgooddatepicker.components.DatePicker.class, "getTopLevelAncestor", null ); // NOI18N
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", com.github.lgooddatepicker.components.DatePicker.class, "getTransferHandler", "setTransferHandler" ); // NOI18N
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", com.github.lgooddatepicker.components.DatePicker.class, "getTreeLock", null ); // NOI18N
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", com.github.lgooddatepicker.components.DatePicker.class, "getUI", "setUI" ); // NOI18N
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", com.github.lgooddatepicker.components.DatePicker.class, "getUIClassID", null ); // NOI18N
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", com.github.lgooddatepicker.components.DatePicker.class, "isValid", null ); // NOI18N
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", com.github.lgooddatepicker.components.DatePicker.class, "isValidateRoot", null ); // NOI18N
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", com.github.lgooddatepicker.components.DatePicker.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" ); // NOI18N
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", com.github.lgooddatepicker.components.DatePicker.class, "getVetoableChangeListeners", null ); // NOI18N
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", com.github.lgooddatepicker.components.DatePicker.class, "isVisible", "setVisible" ); // NOI18N
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", com.github.lgooddatepicker.components.DatePicker.class, "getVisibleRect", null ); // NOI18N
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", com.github.lgooddatepicker.components.DatePicker.class, "getWidth", null ); // NOI18N
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", com.github.lgooddatepicker.components.DatePicker.class, "getX", null ); // NOI18N
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", com.github.lgooddatepicker.components.DatePicker.class, "getY", null ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties
        // Here you can add code for customizing the properties array.

        return properties;     }//GEN-LAST:Properties

    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_ancestorListener = 0;
    private static final int EVENT_componentListener = 1;
    private static final int EVENT_containerListener = 2;
    private static final int EVENT_focusListener = 3;
    private static final int EVENT_hierarchyBoundsListener = 4;
    private static final int EVENT_hierarchyListener = 5;
    private static final int EVENT_inputMethodListener = 6;
    private static final int EVENT_keyListener = 7;
    private static final int EVENT_mouseListener = 8;
    private static final int EVENT_mouseMotionListener = 9;
    private static final int EVENT_mouseWheelListener = 10;
    private static final int EVENT_propertyChangeListener = 11;
    private static final int EVENT_vetoableChangeListener = 12;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[13];
    
        try {
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorRemoved", "ancestorMoved"}, "addAncestorListener", "removeAncestorListener" ); // NOI18N
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentResized", "componentMoved", "componentShown", "componentHidden"}, "addComponentListener", "removeComponentListener" ); // NOI18N
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" ); // NOI18N
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" ); // NOI18N
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" ); // NOI18N
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" ); // NOI18N
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"inputMethodTextChanged", "caretPositionChanged"}, "addInputMethodListener", "removeInputMethodListener" ); // NOI18N
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyTyped", "keyPressed", "keyReleased"}, "addKeyListener", "removeKeyListener" ); // NOI18N
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited"}, "addMouseListener", "removeMouseListener" ); // NOI18N
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" ); // NOI18N
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" ); // NOI18N
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" ); // NOI18N
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( com.github.lgooddatepicker.components.DatePicker.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Events
        // Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events

    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_action0 = 0;
    private static final int METHOD_add1 = 1;
    private static final int METHOD_add2 = 2;
    private static final int METHOD_add3 = 3;
    private static final int METHOD_add4 = 4;
    private static final int METHOD_add5 = 5;
    private static final int METHOD_add6 = 6;
    private static final int METHOD_addDateChangeListener7 = 7;
    private static final int METHOD_addNotify8 = 8;
    private static final int METHOD_addPropertyChangeListener9 = 9;
    private static final int METHOD_applyComponentOrientation10 = 10;
    private static final int METHOD_areFocusTraversalKeysSet11 = 11;
    private static final int METHOD_bounds12 = 12;
    private static final int METHOD_checkImage13 = 13;
    private static final int METHOD_checkImage14 = 14;
    private static final int METHOD_clear15 = 15;
    private static final int METHOD_closePopup16 = 16;
    private static final int METHOD_computeVisibleRect17 = 17;
    private static final int METHOD_contains18 = 18;
    private static final int METHOD_contains19 = 19;
    private static final int METHOD_convert20 = 20;
    private static final int METHOD_countComponents21 = 21;
    private static final int METHOD_createImage22 = 22;
    private static final int METHOD_createImage23 = 23;
    private static final int METHOD_createToolTip24 = 24;
    private static final int METHOD_createVolatileImage25 = 25;
    private static final int METHOD_createVolatileImage26 = 26;
    private static final int METHOD_deliverEvent27 = 27;
    private static final int METHOD_disable28 = 28;
    private static final int METHOD_dispatchEvent29 = 29;
    private static final int METHOD_doLayout30 = 30;
    private static final int METHOD_enable31 = 31;
    private static final int METHOD_enable32 = 32;
    private static final int METHOD_enableInputMethods33 = 33;
    private static final int METHOD_findComponentAt34 = 34;
    private static final int METHOD_findComponentAt35 = 35;
    private static final int METHOD_firePropertyChange36 = 36;
    private static final int METHOD_firePropertyChange37 = 37;
    private static final int METHOD_firePropertyChange38 = 38;
    private static final int METHOD_firePropertyChange39 = 39;
    private static final int METHOD_firePropertyChange40 = 40;
    private static final int METHOD_firePropertyChange41 = 41;
    private static final int METHOD_firePropertyChange42 = 42;
    private static final int METHOD_firePropertyChange43 = 43;
    private static final int METHOD_getActionForKeyStroke44 = 44;
    private static final int METHOD_getBaseline45 = 45;
    private static final int METHOD_getBounds46 = 46;
    private static final int METHOD_getClientProperty47 = 47;
    private static final int METHOD_getComponentAt48 = 48;
    private static final int METHOD_getComponentAt49 = 49;
    private static final int METHOD_getComponentZOrder50 = 50;
    private static final int METHOD_getConditionForKeyStroke51 = 51;
    private static final int METHOD_getDateStringOrSuppliedString52 = 52;
    private static final int METHOD_getDefaultLocale53 = 53;
    private static final int METHOD_getFocusTraversalKeys54 = 54;
    private static final int METHOD_getFontMetrics55 = 55;
    private static final int METHOD_getInsets56 = 56;
    private static final int METHOD_getListeners57 = 57;
    private static final int METHOD_getLocation58 = 58;
    private static final int METHOD_getMousePosition59 = 59;
    private static final int METHOD_getPopupLocation60 = 60;
    private static final int METHOD_getPropertyChangeListeners61 = 61;
    private static final int METHOD_getSize62 = 62;
    private static final int METHOD_getToolTipLocation63 = 63;
    private static final int METHOD_getToolTipText64 = 64;
    private static final int METHOD_gotFocus65 = 65;
    private static final int METHOD_grabFocus66 = 66;
    private static final int METHOD_handleEvent67 = 67;
    private static final int METHOD_hasFocus68 = 68;
    private static final int METHOD_hide69 = 69;
    private static final int METHOD_imageUpdate70 = 70;
    private static final int METHOD_insets71 = 71;
    private static final int METHOD_inside72 = 72;
    private static final int METHOD_invalidate73 = 73;
    private static final int METHOD_isAncestorOf74 = 74;
    private static final int METHOD_isDateAllowed75 = 75;
    private static final int METHOD_isFocusCycleRoot76 = 76;
    private static final int METHOD_isLightweightComponent77 = 77;
    private static final int METHOD_isTextValid78 = 78;
    private static final int METHOD_keyDown79 = 79;
    private static final int METHOD_keyUp80 = 80;
    private static final int METHOD_layout81 = 81;
    private static final int METHOD_list82 = 82;
    private static final int METHOD_list83 = 83;
    private static final int METHOD_list84 = 84;
    private static final int METHOD_list85 = 85;
    private static final int METHOD_list86 = 86;
    private static final int METHOD_locate87 = 87;
    private static final int METHOD_location88 = 88;
    private static final int METHOD_lostFocus89 = 89;
    private static final int METHOD_minimumSize90 = 90;
    private static final int METHOD_mouseDown91 = 91;
    private static final int METHOD_mouseDrag92 = 92;
    private static final int METHOD_mouseEnter93 = 93;
    private static final int METHOD_mouseExit94 = 94;
    private static final int METHOD_mouseMove95 = 95;
    private static final int METHOD_mouseUp96 = 96;
    private static final int METHOD_move97 = 97;
    private static final int METHOD_nextFocus98 = 98;
    private static final int METHOD_openPopup99 = 99;
    private static final int METHOD_paint100 = 100;
    private static final int METHOD_paintAll101 = 101;
    private static final int METHOD_paintComponents102 = 102;
    private static final int METHOD_paintImmediately103 = 103;
    private static final int METHOD_paintImmediately104 = 104;
    private static final int METHOD_postEvent105 = 105;
    private static final int METHOD_preferredSize106 = 106;
    private static final int METHOD_prepareImage107 = 107;
    private static final int METHOD_prepareImage108 = 108;
    private static final int METHOD_print109 = 109;
    private static final int METHOD_printAll110 = 110;
    private static final int METHOD_printComponents111 = 111;
    private static final int METHOD_putClientProperty112 = 112;
    private static final int METHOD_registerKeyboardAction113 = 113;
    private static final int METHOD_registerKeyboardAction114 = 114;
    private static final int METHOD_remove115 = 115;
    private static final int METHOD_remove116 = 116;
    private static final int METHOD_remove117 = 117;
    private static final int METHOD_removeAll118 = 118;
    private static final int METHOD_removeDateChangeListener119 = 119;
    private static final int METHOD_removeNotify120 = 120;
    private static final int METHOD_removePropertyChangeListener121 = 121;
    private static final int METHOD_repaint122 = 122;
    private static final int METHOD_repaint123 = 123;
    private static final int METHOD_repaint124 = 124;
    private static final int METHOD_repaint125 = 125;
    private static final int METHOD_repaint126 = 126;
    private static final int METHOD_requestDefaultFocus127 = 127;
    private static final int METHOD_requestFocus128 = 128;
    private static final int METHOD_requestFocus129 = 129;
    private static final int METHOD_requestFocusInWindow130 = 130;
    private static final int METHOD_resetKeyboardActions131 = 131;
    private static final int METHOD_reshape132 = 132;
    private static final int METHOD_resize133 = 133;
    private static final int METHOD_resize134 = 134;
    private static final int METHOD_revalidate135 = 135;
    private static final int METHOD_scrollRectToVisible136 = 136;
    private static final int METHOD_setBounds137 = 137;
    private static final int METHOD_setComponentZOrder138 = 138;
    private static final int METHOD_setDateToToday139 = 139;
    private static final int METHOD_setDefaultLocale140 = 140;
    private static final int METHOD_setTextFieldToValidStateIfNeeded141 = 141;
    private static final int METHOD_show142 = 142;
    private static final int METHOD_show143 = 143;
    private static final int METHOD_size144 = 144;
    private static final int METHOD_togglePopup145 = 145;
    private static final int METHOD_toString146 = 146;
    private static final int METHOD_transferFocus147 = 147;
    private static final int METHOD_transferFocusBackward148 = 148;
    private static final int METHOD_transferFocusDownCycle149 = 149;
    private static final int METHOD_transferFocusUpCycle150 = 150;
    private static final int METHOD_unregisterKeyboardAction151 = 151;
    private static final int METHOD_update152 = 152;
    private static final int METHOD_updateUI153 = 153;
    private static final int METHOD_validate154 = 154;
    private static final int METHOD_zDrawTextFieldIndicators155 = 155;
    private static final int METHOD_zEventCustomPopupWasClosed156 = 156;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[157];
    
        try {
            methods[METHOD_action0] = new MethodDescriptor(java.awt.Component.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_action0].setDisplayName ( "" );
            methods[METHOD_add1] = new MethodDescriptor(java.awt.Component.class.getMethod("add", new Class[] {java.awt.PopupMenu.class})); // NOI18N
            methods[METHOD_add1].setDisplayName ( "" );
            methods[METHOD_add2] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_add2].setDisplayName ( "" );
            methods[METHOD_add3] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.lang.String.class, java.awt.Component.class})); // NOI18N
            methods[METHOD_add3].setDisplayName ( "" );
            methods[METHOD_add4] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class, int.class})); // NOI18N
            methods[METHOD_add4].setDisplayName ( "" );
            methods[METHOD_add5] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_add5].setDisplayName ( "" );
            methods[METHOD_add6] = new MethodDescriptor(java.awt.Container.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class, int.class})); // NOI18N
            methods[METHOD_add6].setDisplayName ( "" );
            methods[METHOD_addDateChangeListener7] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("addDateChangeListener", new Class[] {com.github.lgooddatepicker.optionalusertools.DateChangeListener.class})); // NOI18N
            methods[METHOD_addDateChangeListener7].setDisplayName ( "" );
            methods[METHOD_addNotify8] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("addNotify", new Class[] {})); // NOI18N
            methods[METHOD_addNotify8].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener9] = new MethodDescriptor(java.awt.Container.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_addPropertyChangeListener9].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation10] = new MethodDescriptor(java.awt.Container.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class})); // NOI18N
            methods[METHOD_applyComponentOrientation10].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet11] = new MethodDescriptor(java.awt.Container.class.getMethod("areFocusTraversalKeysSet", new Class[] {int.class})); // NOI18N
            methods[METHOD_areFocusTraversalKeysSet11].setDisplayName ( "" );
            methods[METHOD_bounds12] = new MethodDescriptor(java.awt.Component.class.getMethod("bounds", new Class[] {})); // NOI18N
            methods[METHOD_bounds12].setDisplayName ( "" );
            methods[METHOD_checkImage13] = new MethodDescriptor(java.awt.Component.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage13].setDisplayName ( "" );
            methods[METHOD_checkImage14] = new MethodDescriptor(java.awt.Component.class.getMethod("checkImage", new Class[] {java.awt.Image.class, int.class, int.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage14].setDisplayName ( "" );
            methods[METHOD_clear15] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("clear", new Class[] {})); // NOI18N
            methods[METHOD_clear15].setDisplayName ( "" );
            methods[METHOD_closePopup16] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("closePopup", new Class[] {})); // NOI18N
            methods[METHOD_closePopup16].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect17] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_computeVisibleRect17].setDisplayName ( "" );
            methods[METHOD_contains18] = new MethodDescriptor(java.awt.Component.class.getMethod("contains", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_contains18].setDisplayName ( "" );
            methods[METHOD_contains19] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("contains", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_contains19].setDisplayName ( "" );
            methods[METHOD_convert20] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("convert", new Class[] {})); // NOI18N
            methods[METHOD_convert20].setDisplayName ( "" );
            methods[METHOD_countComponents21] = new MethodDescriptor(java.awt.Container.class.getMethod("countComponents", new Class[] {})); // NOI18N
            methods[METHOD_countComponents21].setDisplayName ( "" );
            methods[METHOD_createImage22] = new MethodDescriptor(java.awt.Component.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class})); // NOI18N
            methods[METHOD_createImage22].setDisplayName ( "" );
            methods[METHOD_createImage23] = new MethodDescriptor(java.awt.Component.class.getMethod("createImage", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_createImage23].setDisplayName ( "" );
            methods[METHOD_createToolTip24] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("createToolTip", new Class[] {})); // NOI18N
            methods[METHOD_createToolTip24].setDisplayName ( "" );
            methods[METHOD_createVolatileImage25] = new MethodDescriptor(java.awt.Component.class.getMethod("createVolatileImage", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_createVolatileImage25].setDisplayName ( "" );
            methods[METHOD_createVolatileImage26] = new MethodDescriptor(java.awt.Component.class.getMethod("createVolatileImage", new Class[] {int.class, int.class, java.awt.ImageCapabilities.class})); // NOI18N
            methods[METHOD_createVolatileImage26].setDisplayName ( "" );
            methods[METHOD_deliverEvent27] = new MethodDescriptor(java.awt.Container.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_deliverEvent27].setDisplayName ( "" );
            methods[METHOD_disable28] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("disable", new Class[] {})); // NOI18N
            methods[METHOD_disable28].setDisplayName ( "" );
            methods[METHOD_dispatchEvent29] = new MethodDescriptor(java.awt.Component.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class})); // NOI18N
            methods[METHOD_dispatchEvent29].setDisplayName ( "" );
            methods[METHOD_doLayout30] = new MethodDescriptor(java.awt.Container.class.getMethod("doLayout", new Class[] {})); // NOI18N
            methods[METHOD_doLayout30].setDisplayName ( "" );
            methods[METHOD_enable31] = new MethodDescriptor(java.awt.Component.class.getMethod("enable", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_enable31].setDisplayName ( "" );
            methods[METHOD_enable32] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("enable", new Class[] {})); // NOI18N
            methods[METHOD_enable32].setDisplayName ( "" );
            methods[METHOD_enableInputMethods33] = new MethodDescriptor(java.awt.Component.class.getMethod("enableInputMethods", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_enableInputMethods33].setDisplayName ( "" );
            methods[METHOD_findComponentAt34] = new MethodDescriptor(java.awt.Container.class.getMethod("findComponentAt", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_findComponentAt34].setDisplayName ( "" );
            methods[METHOD_findComponentAt35] = new MethodDescriptor(java.awt.Container.class.getMethod("findComponentAt", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_findComponentAt35].setDisplayName ( "" );
            methods[METHOD_firePropertyChange36] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, byte.class, byte.class})); // NOI18N
            methods[METHOD_firePropertyChange36].setDisplayName ( "" );
            methods[METHOD_firePropertyChange37] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, short.class, short.class})); // NOI18N
            methods[METHOD_firePropertyChange37].setDisplayName ( "" );
            methods[METHOD_firePropertyChange38] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, long.class, long.class})); // NOI18N
            methods[METHOD_firePropertyChange38].setDisplayName ( "" );
            methods[METHOD_firePropertyChange39] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, float.class, float.class})); // NOI18N
            methods[METHOD_firePropertyChange39].setDisplayName ( "" );
            methods[METHOD_firePropertyChange40] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, double.class, double.class})); // NOI18N
            methods[METHOD_firePropertyChange40].setDisplayName ( "" );
            methods[METHOD_firePropertyChange41] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, boolean.class, boolean.class})); // NOI18N
            methods[METHOD_firePropertyChange41].setDisplayName ( "" );
            methods[METHOD_firePropertyChange42] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, int.class, int.class})); // NOI18N
            methods[METHOD_firePropertyChange42].setDisplayName ( "" );
            methods[METHOD_firePropertyChange43] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, char.class, char.class})); // NOI18N
            methods[METHOD_firePropertyChange43].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke44] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getActionForKeyStroke44].setDisplayName ( "" );
            methods[METHOD_getBaseline45] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getBaseline", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_getBaseline45].setDisplayName ( "" );
            methods[METHOD_getBounds46] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_getBounds46].setDisplayName ( "" );
            methods[METHOD_getClientProperty47] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class})); // NOI18N
            methods[METHOD_getClientProperty47].setDisplayName ( "" );
            methods[METHOD_getComponentAt48] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentAt", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_getComponentAt48].setDisplayName ( "" );
            methods[METHOD_getComponentAt49] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentAt", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getComponentAt49].setDisplayName ( "" );
            methods[METHOD_getComponentZOrder50] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentZOrder", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_getComponentZOrder50].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke51] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getConditionForKeyStroke51].setDisplayName ( "" );
            methods[METHOD_getDateStringOrSuppliedString52] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("getDateStringOrSuppliedString", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getDateStringOrSuppliedString52].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale53] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getDefaultLocale", new Class[] {})); // NOI18N
            methods[METHOD_getDefaultLocale53].setDisplayName ( "" );
            methods[METHOD_getFocusTraversalKeys54] = new MethodDescriptor(java.awt.Container.class.getMethod("getFocusTraversalKeys", new Class[] {int.class})); // NOI18N
            methods[METHOD_getFocusTraversalKeys54].setDisplayName ( "" );
            methods[METHOD_getFontMetrics55] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class})); // NOI18N
            methods[METHOD_getFontMetrics55].setDisplayName ( "" );
            methods[METHOD_getInsets56] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getInsets", new Class[] {java.awt.Insets.class})); // NOI18N
            methods[METHOD_getInsets56].setDisplayName ( "" );
            methods[METHOD_getListeners57] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getListeners", new Class[] {java.lang.Class.class})); // NOI18N
            methods[METHOD_getListeners57].setDisplayName ( "" );
            methods[METHOD_getLocation58] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getLocation", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getLocation58].setDisplayName ( "" );
            methods[METHOD_getMousePosition59] = new MethodDescriptor(java.awt.Container.class.getMethod("getMousePosition", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_getMousePosition59].setDisplayName ( "" );
            methods[METHOD_getPopupLocation60] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getPopupLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getPopupLocation60].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners61] = new MethodDescriptor(java.awt.Component.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getPropertyChangeListeners61].setDisplayName ( "" );
            methods[METHOD_getSize62] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getSize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_getSize62].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation63] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipLocation63].setDisplayName ( "" );
            methods[METHOD_getToolTipText64] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipText64].setDisplayName ( "" );
            methods[METHOD_gotFocus65] = new MethodDescriptor(java.awt.Component.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_gotFocus65].setDisplayName ( "" );
            methods[METHOD_grabFocus66] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("grabFocus", new Class[] {})); // NOI18N
            methods[METHOD_grabFocus66].setDisplayName ( "" );
            methods[METHOD_handleEvent67] = new MethodDescriptor(java.awt.Component.class.getMethod("handleEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_handleEvent67].setDisplayName ( "" );
            methods[METHOD_hasFocus68] = new MethodDescriptor(java.awt.Component.class.getMethod("hasFocus", new Class[] {})); // NOI18N
            methods[METHOD_hasFocus68].setDisplayName ( "" );
            methods[METHOD_hide69] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("hide", new Class[] {})); // NOI18N
            methods[METHOD_hide69].setDisplayName ( "" );
            methods[METHOD_imageUpdate70] = new MethodDescriptor(java.awt.Component.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, int.class, int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_imageUpdate70].setDisplayName ( "" );
            methods[METHOD_insets71] = new MethodDescriptor(java.awt.Container.class.getMethod("insets", new Class[] {})); // NOI18N
            methods[METHOD_insets71].setDisplayName ( "" );
            methods[METHOD_inside72] = new MethodDescriptor(java.awt.Component.class.getMethod("inside", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_inside72].setDisplayName ( "" );
            methods[METHOD_invalidate73] = new MethodDescriptor(java.awt.Container.class.getMethod("invalidate", new Class[] {})); // NOI18N
            methods[METHOD_invalidate73].setDisplayName ( "" );
            methods[METHOD_isAncestorOf74] = new MethodDescriptor(java.awt.Container.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isAncestorOf74].setDisplayName ( "" );
            methods[METHOD_isDateAllowed75] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("isDateAllowed", new Class[] {java.time.LocalDate.class})); // NOI18N
            methods[METHOD_isDateAllowed75].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot76] = new MethodDescriptor(java.awt.Container.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class})); // NOI18N
            methods[METHOD_isFocusCycleRoot76].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent77] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isLightweightComponent77].setDisplayName ( "" );
            methods[METHOD_isTextValid78] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("isTextValid", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_isTextValid78].setDisplayName ( "" );
            methods[METHOD_keyDown79] = new MethodDescriptor(java.awt.Component.class.getMethod("keyDown", new Class[] {java.awt.Event.class, int.class})); // NOI18N
            methods[METHOD_keyDown79].setDisplayName ( "" );
            methods[METHOD_keyUp80] = new MethodDescriptor(java.awt.Component.class.getMethod("keyUp", new Class[] {java.awt.Event.class, int.class})); // NOI18N
            methods[METHOD_keyUp80].setDisplayName ( "" );
            methods[METHOD_layout81] = new MethodDescriptor(java.awt.Container.class.getMethod("layout", new Class[] {})); // NOI18N
            methods[METHOD_layout81].setDisplayName ( "" );
            methods[METHOD_list82] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {})); // NOI18N
            methods[METHOD_list82].setDisplayName ( "" );
            methods[METHOD_list83] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {java.io.PrintStream.class})); // NOI18N
            methods[METHOD_list83].setDisplayName ( "" );
            methods[METHOD_list84] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {java.io.PrintWriter.class})); // NOI18N
            methods[METHOD_list84].setDisplayName ( "" );
            methods[METHOD_list85] = new MethodDescriptor(java.awt.Container.class.getMethod("list", new Class[] {java.io.PrintStream.class, int.class})); // NOI18N
            methods[METHOD_list85].setDisplayName ( "" );
            methods[METHOD_list86] = new MethodDescriptor(java.awt.Container.class.getMethod("list", new Class[] {java.io.PrintWriter.class, int.class})); // NOI18N
            methods[METHOD_list86].setDisplayName ( "" );
            methods[METHOD_locate87] = new MethodDescriptor(java.awt.Container.class.getMethod("locate", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_locate87].setDisplayName ( "" );
            methods[METHOD_location88] = new MethodDescriptor(java.awt.Component.class.getMethod("location", new Class[] {})); // NOI18N
            methods[METHOD_location88].setDisplayName ( "" );
            methods[METHOD_lostFocus89] = new MethodDescriptor(java.awt.Component.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_lostFocus89].setDisplayName ( "" );
            methods[METHOD_minimumSize90] = new MethodDescriptor(java.awt.Container.class.getMethod("minimumSize", new Class[] {})); // NOI18N
            methods[METHOD_minimumSize90].setDisplayName ( "" );
            methods[METHOD_mouseDown91] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseDown91].setDisplayName ( "" );
            methods[METHOD_mouseDrag92] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseDrag92].setDisplayName ( "" );
            methods[METHOD_mouseEnter93] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseEnter93].setDisplayName ( "" );
            methods[METHOD_mouseExit94] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseExit94].setDisplayName ( "" );
            methods[METHOD_mouseMove95] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseMove95].setDisplayName ( "" );
            methods[METHOD_mouseUp96] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseUp96].setDisplayName ( "" );
            methods[METHOD_move97] = new MethodDescriptor(java.awt.Component.class.getMethod("move", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_move97].setDisplayName ( "" );
            methods[METHOD_nextFocus98] = new MethodDescriptor(java.awt.Component.class.getMethod("nextFocus", new Class[] {})); // NOI18N
            methods[METHOD_nextFocus98].setDisplayName ( "" );
            methods[METHOD_openPopup99] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("openPopup", new Class[] {})); // NOI18N
            methods[METHOD_openPopup99].setDisplayName ( "" );
            methods[METHOD_paint100] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paint", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paint100].setDisplayName ( "" );
            methods[METHOD_paintAll101] = new MethodDescriptor(java.awt.Component.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintAll101].setDisplayName ( "" );
            methods[METHOD_paintComponents102] = new MethodDescriptor(java.awt.Container.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintComponents102].setDisplayName ( "" );
            methods[METHOD_paintImmediately103] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paintImmediately", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_paintImmediately103].setDisplayName ( "" );
            methods[METHOD_paintImmediately104] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paintImmediately", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_paintImmediately104].setDisplayName ( "" );
            methods[METHOD_postEvent105] = new MethodDescriptor(java.awt.Component.class.getMethod("postEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_postEvent105].setDisplayName ( "" );
            methods[METHOD_preferredSize106] = new MethodDescriptor(java.awt.Container.class.getMethod("preferredSize", new Class[] {})); // NOI18N
            methods[METHOD_preferredSize106].setDisplayName ( "" );
            methods[METHOD_prepareImage107] = new MethodDescriptor(java.awt.Component.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage107].setDisplayName ( "" );
            methods[METHOD_prepareImage108] = new MethodDescriptor(java.awt.Component.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, int.class, int.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage108].setDisplayName ( "" );
            methods[METHOD_print109] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("print", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_print109].setDisplayName ( "" );
            methods[METHOD_printAll110] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("printAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printAll110].setDisplayName ( "" );
            methods[METHOD_printComponents111] = new MethodDescriptor(java.awt.Container.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printComponents111].setDisplayName ( "" );
            methods[METHOD_putClientProperty112] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_putClientProperty112].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction113] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, int.class})); // NOI18N
            methods[METHOD_registerKeyboardAction113].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction114] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, javax.swing.KeyStroke.class, int.class})); // NOI18N
            methods[METHOD_registerKeyboardAction114].setDisplayName ( "" );
            methods[METHOD_remove115] = new MethodDescriptor(java.awt.Component.class.getMethod("remove", new Class[] {java.awt.MenuComponent.class})); // NOI18N
            methods[METHOD_remove115].setDisplayName ( "" );
            methods[METHOD_remove116] = new MethodDescriptor(java.awt.Container.class.getMethod("remove", new Class[] {int.class})); // NOI18N
            methods[METHOD_remove116].setDisplayName ( "" );
            methods[METHOD_remove117] = new MethodDescriptor(java.awt.Container.class.getMethod("remove", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_remove117].setDisplayName ( "" );
            methods[METHOD_removeAll118] = new MethodDescriptor(java.awt.Container.class.getMethod("removeAll", new Class[] {})); // NOI18N
            methods[METHOD_removeAll118].setDisplayName ( "" );
            methods[METHOD_removeDateChangeListener119] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("removeDateChangeListener", new Class[] {com.github.lgooddatepicker.optionalusertools.DateChangeListener.class})); // NOI18N
            methods[METHOD_removeDateChangeListener119].setDisplayName ( "" );
            methods[METHOD_removeNotify120] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("removeNotify", new Class[] {})); // NOI18N
            methods[METHOD_removeNotify120].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener121] = new MethodDescriptor(java.awt.Component.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_removePropertyChangeListener121].setDisplayName ( "" );
            methods[METHOD_repaint122] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {})); // NOI18N
            methods[METHOD_repaint122].setDisplayName ( "" );
            methods[METHOD_repaint123] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {long.class})); // NOI18N
            methods[METHOD_repaint123].setDisplayName ( "" );
            methods[METHOD_repaint124] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_repaint124].setDisplayName ( "" );
            methods[METHOD_repaint125] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("repaint", new Class[] {long.class, int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_repaint125].setDisplayName ( "" );
            methods[METHOD_repaint126] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("repaint", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_repaint126].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus127] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestDefaultFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestDefaultFocus127].setDisplayName ( "" );
            methods[METHOD_requestFocus128] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestFocus128].setDisplayName ( "" );
            methods[METHOD_requestFocus129] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestFocus", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_requestFocus129].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow130] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestFocusInWindow", new Class[] {})); // NOI18N
            methods[METHOD_requestFocusInWindow130].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions131] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("resetKeyboardActions", new Class[] {})); // NOI18N
            methods[METHOD_resetKeyboardActions131].setDisplayName ( "" );
            methods[METHOD_reshape132] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("reshape", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_reshape132].setDisplayName ( "" );
            methods[METHOD_resize133] = new MethodDescriptor(java.awt.Component.class.getMethod("resize", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_resize133].setDisplayName ( "" );
            methods[METHOD_resize134] = new MethodDescriptor(java.awt.Component.class.getMethod("resize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_resize134].setDisplayName ( "" );
            methods[METHOD_revalidate135] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("revalidate", new Class[] {})); // NOI18N
            methods[METHOD_revalidate135].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible136] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_scrollRectToVisible136].setDisplayName ( "" );
            methods[METHOD_setBounds137] = new MethodDescriptor(java.awt.Component.class.getMethod("setBounds", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_setBounds137].setDisplayName ( "" );
            methods[METHOD_setComponentZOrder138] = new MethodDescriptor(java.awt.Container.class.getMethod("setComponentZOrder", new Class[] {java.awt.Component.class, int.class})); // NOI18N
            methods[METHOD_setComponentZOrder138].setDisplayName ( "" );
            methods[METHOD_setDateToToday139] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("setDateToToday", new Class[] {})); // NOI18N
            methods[METHOD_setDateToToday139].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale140] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class})); // NOI18N
            methods[METHOD_setDefaultLocale140].setDisplayName ( "" );
            methods[METHOD_setTextFieldToValidStateIfNeeded141] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("setTextFieldToValidStateIfNeeded", new Class[] {})); // NOI18N
            methods[METHOD_setTextFieldToValidStateIfNeeded141].setDisplayName ( "" );
            methods[METHOD_show142] = new MethodDescriptor(java.awt.Component.class.getMethod("show", new Class[] {})); // NOI18N
            methods[METHOD_show142].setDisplayName ( "" );
            methods[METHOD_show143] = new MethodDescriptor(java.awt.Component.class.getMethod("show", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_show143].setDisplayName ( "" );
            methods[METHOD_size144] = new MethodDescriptor(java.awt.Component.class.getMethod("size", new Class[] {})); // NOI18N
            methods[METHOD_size144].setDisplayName ( "" );
            methods[METHOD_togglePopup145] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("togglePopup", new Class[] {})); // NOI18N
            methods[METHOD_togglePopup145].setDisplayName ( "" );
            methods[METHOD_toString146] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("toString", new Class[] {})); // NOI18N
            methods[METHOD_toString146].setDisplayName ( "" );
            methods[METHOD_transferFocus147] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocus", new Class[] {})); // NOI18N
            methods[METHOD_transferFocus147].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward148] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocusBackward", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusBackward148].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle149] = new MethodDescriptor(java.awt.Container.class.getMethod("transferFocusDownCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusDownCycle149].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle150] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocusUpCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusUpCycle150].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction151] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_unregisterKeyboardAction151].setDisplayName ( "" );
            methods[METHOD_update152] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("update", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_update152].setDisplayName ( "" );
            methods[METHOD_updateUI153] = new MethodDescriptor(javax.swing.JPanel.class.getMethod("updateUI", new Class[] {})); // NOI18N
            methods[METHOD_updateUI153].setDisplayName ( "" );
            methods[METHOD_validate154] = new MethodDescriptor(java.awt.Container.class.getMethod("validate", new Class[] {})); // NOI18N
            methods[METHOD_validate154].setDisplayName ( "" );
            methods[METHOD_zDrawTextFieldIndicators155] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("zDrawTextFieldIndicators", new Class[] {})); // NOI18N
            methods[METHOD_zDrawTextFieldIndicators155].setDisplayName ( "" );
            methods[METHOD_zEventCustomPopupWasClosed156] = new MethodDescriptor(com.github.lgooddatepicker.components.DatePicker.class.getMethod("zEventCustomPopupWasClosed", new Class[] {com.github.lgooddatepicker.zinternaltools.CustomPopup.class})); // NOI18N
            methods[METHOD_zEventCustomPopupWasClosed156].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
        // Here you can add code for customizing the methods array.

        return methods;     }//GEN-LAST:Methods

    private static java.awt.Image iconColor16 = null;//GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;//GEN-END:IconsDef
    private static String iconNameC16 = "/images/DatePickerIcon 16x16.png";//GEN-BEGIN:Icons
    private static String iconNameC32 = "/images/DatePickerIcon 32x32.png";
    private static String iconNameM16 = "/images/DatePickerIcon 16x16.png";
    private static String iconNameM32 = "/images/DatePickerIcon 32x32.png";//GEN-END:Icons

    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx

//GEN-FIRST:Superclass
    // Here you can add code for customizing the Superclass BeanInfo.
//GEN-LAST:Superclass
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable properties of this bean. May return null if
     * the information should be obtained by automatic analysis.
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }

    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable properties supported by this
     * bean. May return null if the information should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will belong to the
     * IndexedPropertyDescriptor subclass of PropertyDescriptor. A client of getPropertyDescriptors
     * can use "instanceof" to check if a given PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }

    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return An array of EventSetDescriptors describing the kinds of events fired by this bean.
     * May return null if the information should be obtained by automatic analysis.
     */
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEdescriptor();
    }

    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return An array of MethodDescriptors describing the methods implemented by this bean. May
     * return null if the information should be obtained by automatic analysis.
     */
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return getMdescriptor();
    }

    /**
     * A bean may have a "default" property that is the property that will mostly commonly be
     * initially chosen for update by human's who are customizing the bean.
     *
     * @return Index of default property in the PropertyDescriptor array returned by
     * getPropertyDescriptors.
     * <P>
     * Returns -1 if there is no default property.
     */
    @Override
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }

    /**
     * A bean may have a "default" event that is the event that will mostly commonly be used by
     * human's when using the bean.
     *
     * @return Index of default event in the EventSetDescriptor array returned by
     * getEventSetDescriptors.
     * <P>
     * Returns -1 if there is no default event.
     */
    @Override
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }

    /**
     * This method returns an image object that can be used to represent the bean in toolboxes,
     * toolbars, etc. Icon images will typically be GIFs, but may in future include other formats.
     * <p>
     * Beans aren't required to provide icons and may return null from this method.
     * <p>
     * There are four possible flavors of icons (16x16 color, 32x32 color, 16x16 mono, 32x32 mono).
     * If a bean choses to only support a single icon we recommend supporting 16x16 color.
     * <p>
     * We recommend that icons have a "transparent" background so they can be rendered onto an
     * existing background.
     *
     * @param iconKind The kind of icon requested. This should be one of the constant values
     * ICON_COLOR_16x16, ICON_COLOR_32x32, ICON_MONO_16x16, or ICON_MONO_32x32.
     * @return An image object representing the requested icon. May return null if no suitable icon
     * is available.
     */
    @Override
    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
            case ICON_COLOR_16x16:
                if (iconNameC16 == null) {
                    return null;
                } else {
                    if (iconColor16 == null) {
                        iconColor16 = loadImage(iconNameC16);
                    }
                    return iconColor16;
                }
            case ICON_COLOR_32x32:
                if (iconNameC32 == null) {
                    return null;
                } else {
                    if (iconColor32 == null) {
                        iconColor32 = loadImage(iconNameC32);
                    }
                    return iconColor32;
                }
            case ICON_MONO_16x16:
                if (iconNameM16 == null) {
                    return null;
                } else {
                    if (iconMono16 == null) {
                        iconMono16 = loadImage(iconNameM16);
                    }
                    return iconMono16;
                }
            case ICON_MONO_32x32:
                if (iconNameM32 == null) {
                    return null;
                } else {
                    if (iconMono32 == null) {
                        iconMono32 = loadImage(iconNameM32);
                    }
                    return iconMono32;
                }
            default:
                return null;
        }
    }

}
