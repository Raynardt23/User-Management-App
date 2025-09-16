# User-Management-App

User Management Application – Setup Guide
Requirements

Java JDK 17+

Apache Tomcat 9+

MySQL (if not using embedded DB)

Running the Application
Option 1: Deploy WAR (recommended)

Copy UserManagementApp.war into Tomcat’s webapps/ folder.

Start Tomcat (startup.bat on Windows, startup.sh on Linux/macOS).

Open in your browser:

http://localhost:8080/UserManagementApp/login

Option 2: Run via NetBeans

Open the project in Apache NetBeans.

Right-click → Clean and Build.

Right-click → Run.

💡 NetBeans will automatically deploy the project to its configured Tomcat server.
You can then access the app at:

http://localhost:8080/UserManagementApp/login

Default Login

Username: admin

Password: admin123

Notes

The application automatically creates the database, tables, and default admin user on startup.

If your MySQL username/password is different from root / password, update it in:

src/main/resources/config.properties


Example:

db.url=jdbc:mysql://localhost:3306/usermanagement
db.user=root
db.password=password


If port 8080 is already in use, update Tomcat’s port in conf/server.xml.
