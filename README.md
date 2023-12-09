# CRUD API for treatment app

## 📑 Table of content:
1. [About this Project](#about-this-project)
2. [Requirements](#requirements)
3. [How to install](#how-to-install)
4. [Usage](#usage)
5. [Contributing](#contributing)

## 📚 About this Project
A very basic implementation of CRUD API for treatment app, where you have a patient with curtain disease and you can 
perform 4 basic action: add, create, change and delete.

## ⚙️ Requirements
* Java 8 or later
* Lombok
* Vavr
* [Spark](https://sparkjava.com/)
* Jackson
* Junit
* PostgreSQL
* PostgreSQL JDBC Driver
* [java-dotenv](https://github.com/cdimascio/java-dotenv)
* [ScriptRunner](https://mybatis.org/mybatis-3/jacoco/org.apache.ibatis.jdbc/ScriptRunner.java.html)

## 🗺️ How To Install
1. Clone repository from gitHub:
    ~~~
   https://github.com/MasoNord/backend-for-threatment-app.git
    ~~~

2. Import it in Intellij IDE or any IDE/text editor of you flavor


3. Create _.env_ file, you can use the following template:
    ~~~
    APP_PORT=<your app's port>
    
    DB_URL="jdbc:postgresql://localhost/treatment_app"
    DB_USER=<your db user>
    DB_PASSWORD=<your db password>
    ~~~
4. Run the application ;)


5. In case you decided to make some changes don't forget to run `tests` to ensures that all is good 
## Usage
### Get
* Request:
    ~~~
    GET http://localhost:4000/api/patient
    ~~~
* Response:
    ~~~
    200 - OK
    500 - Internal Server Error 
    ~~~   
### Get By id
* Request:
    ~~~
    GET http://localhost:4000/api/patient/{patientId}
    ~~~
* Response:
    ~~~
    200 - OK
    400 - Bad Request (id is not uuid)
    404 - Not Found (patient is not found)
    500 - Internal Server Error
    ~~~
### Post
* Request:
    ~~~
    POST http://localhost:4000/api/patient/
    ~~~
* Body:
    ~~~JSON
    {
      "name": "Denial",
      "sex": "M",
      "dob": "12.01.1955",
      "hp": {
          "name": "Dementia",
          "degree": 4
      }
    }      
    ~~~
* Response:
    ~~~
    200 - OK
    400 - Bad Request (id is not uuid, lack of required fields or wrong data type)
    404 - Not Found (patient is not found)
    500 - Internal Server Error
    ~~~
### Put
* Request:
    ~~~
    POST http://localhost:4000/api/patient/{patientId}
    ~~~
* Body:
    
    Here you can choose which field to change, untouched one will be set by a field's current value
    ~~~JSON
    {
      "name": "Denial"
    }      
    ~~~
  
* Response:
    ~~~
    200 - OK
    400 - Bad Request (id is not uuid or wrong data type)
    404 - Not Found (patient is not found)
    500 - Internal Server Error
  
### Delete
* Request:
    ~~~
    Delete http://localhost:4000/api/patient/{patientId}
    ~~~

* Response:
    ~~~
    204 - No Content
    400 - Bad Request (id is not uuid)
    404 - Not Found (patient is not found)
    500 - Internal Server Error
   ~~~

# 😺 Contributing
If you inspired by project and have an interest to make this better with new ideas, your pull request is very welcomed :)
