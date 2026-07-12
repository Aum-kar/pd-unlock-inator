# PDF Unlock Tool

A lightweight web application that removes password protection from PDF files. Upload a protected PDF, enter the correct password, and download an unlocked copy directly from your browser.

## Features

* Upload password protected PDF files
* Remove PDF password protection
* Automatic download of the unlocked PDF
* Simple and responsive web interface
* Validates uploaded files
* Handles invalid passwords and common errors
* Unit and MockMvc tests included

## Technologies Used

* Java 24
* Spring Boot 3.5
* Apache PDFBox 3
* Maven
* HTML5
* CSS3
* JUnit 5
* Spring Test (MockMvc)
* SLF4J / Logback

## How It Works

1. Open the application in your browser.
2. Select a password protected PDF.
3. Enter the PDF password.
4. Click **Unlock**.
5. If the password is correct, the unlocked PDF is downloaded automatically.

## Running the Project

### Prerequisites

* Java 24
* Maven 3.9 or later

### Clone the repository

```bash
git clone <repository-url>
cd <repository-folder>
```

### Start the application

```bash
mvn spring-boot:run
```

The application will be available at:

```
http://localhost:8080
```

## Building

Create the project JAR using:

```bash
mvn clean package
```

The generated JAR will be available in the `target` directory.

## Validation

The application checks that:

* A file is selected.
* The uploaded file is a PDF.
* The PDF password is correct.

Appropriate error messages are returned if validation fails.

## Privacy

This application does not require user accounts or collect personal information. Uploaded files are processed only for unlocking and immediately returned to the client.

## Future Improvements

* Drag and drop file upload
* Multiple file support
* Progress indicator
* Docker deployment
* Dark mode
* Improved mobile layout

## License

This project is available under the MIT License.
