This TODO list application is licensed under GPL:

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Code taken from (see source code for specific locations):
	Dr. Hindle - StudentPicker - https://www.youtube.com/watch?v=gmNfc6u1qk0 (GPL2)
	Stackoverflow (Creative Commons)
	 -http://stackoverflow.com/questions/10407159/how-to-manage-start-activity-for-result-on-android
         -http://stackoverflow.com/questions/9361744/dynamically-add-ui-content-in-android
         -http://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android
	 -http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application

=================================================

This application is used via the menu button located in the action bar.
It'll pop up a menu with various options. Selecting one of these options 
will lead you to the next activity.

I assumed that archived items could not be edited, and when an email is
sent out that the user can fill in the recipient themselves.

Limitations:
	I refresh the UI entirely when something is added or deleted.
	When something is added, deleted, or edited I resave the entire structure.


