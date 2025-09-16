# User-Management-App

# User Management Application – Setup Guide
# Requirements

Java JDK 17+

Apache Tomcat 9+

MySQL (if not using embedded DB)

# Running the Application
# Option 1: Deploy WAR (recommended)

(1) Copy UserManagementApp.war into Tomcat’s webapps/ folder.

(2) Start Tomcat (startup.bat on Windows, startup.sh on Linux/macOS).

(3) Open in your browser:

http://localhost:8080/UserManagementApp/login

# Option 2: Run via NetBeans

(1) Open the project in Apache NetBeans.

(2) Right-click → Clean and Build.

(3) Right-click → Run.

💡 NetBeans will automatically deploy the project to its configured Tomcat server.
You can then access the app at:

http://localhost:8080/UserManagementApp/login

# Default Login

•Username: admin

•Password: admin123

# Notes

•The application automatically creates the database, tables, and default admin user on startup.

If your MySQL username/password is different from my username and password, update it in:

src/main/resources/config.properties


Example:

•db.url=jdbc:mysql://localhost:3306/usermanagement
•db.user=your_username
•db.password=your_password


If port 8080 is already in use, update Tomcat’s port in conf/server.xml.
