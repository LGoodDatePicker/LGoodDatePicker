
# Code style

This project uses a consistent code style and provides definitions for use in both IntelliJ and Eclipse IDEs.

##IntelliJ IDEA instructions:

For IntelliJ IDEA the process for importing the code style is:

* Select *Settings* in the *File* menu
* Select *Editor*
* Select *Code Style*
* Expand the menu item and select *Java*
* Go to *Scheme* at the top, click on the setting button by the side of the drop-down list
* Select *Import Scheme*
* Select *IntelliJ IDE code style XML*
* Navigate to the location of the file which relative to the project root is:  `ide-settings/idea/idea-code-style-configuration.xml`
* Select *OK*
* At this point the code style is stored as part of the IDE and is used for **all** projects opened in the editor.  To restrict the settings to just this project again select the setting button by the side of the *Scheme* list and select *Copy to Project...*. If successful a notice appears in the window that reads: *For current project*.

##Eclipse instructions

For Eclipse the process requires loading the formatting style and the import order separately.

* Select *Preferences* in the *Window* menu
* Select *Java*
* Select *Code Style* and expand the menu
* Select *Formatter*
* Select the *Import...* button
* Navigate to the location of the file which relative to the project root is:  `ide-settings/eclipse/Preferences-Java-CodeStyle-Formatter.xml`
* Select *Open*
* At this point the code style is stored and is applicable to all projects opened in the IDE.  To restrict the settings just to this project select *Configure Project Specific Settings...* in the upper right.  In the next dialog select the *openhospital* repository and select *OK*.  In the next dialog select the *Enable project specific settings* checkbox.  Finally select *Apply and Close*.
* Back in the *Code Style* menu area, select *Organize Imports*
* Select *Import...*
* Navigate to the location of the file which relative to the project root is:  `ide-settings/eclipse/LGoodDatePicker.importorder`
* Select *Open*
* As with the formatting styles the import order is applicable to all projects.  In order to change it just for this project repeat the same steps as above for *Configure Project Specific Settings...*

