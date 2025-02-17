# 🚧 Currency Converter - Backend API

This is the backend service for a **Currency Converter** application, built using **Java Spring Boot**.  
It provides APIs to convert currencies, fetch exchange rates, and perform related operations.  

## 🚀 Getting Started

Follow the steps below to **set up and run the project**.

---

## 📥 Clone the Repository

First, clone the repository and navigate into the project directory:

```sh
git clone https://github.com/Mujtaba52/currency-converter.git
cd currency-converter
```

---

## 🔧 Running the Project

You can run the project in **two ways**: **locally** or using **Docker**.

### **1️⃣ Running Locally**

#### **📌 Prerequisites**
- Java **17** or higher installed

#### **⚡ Commands to Run Locally**
```sh
./mvnw clean package
java -jar target/currency-converter-0.0.1-SNAPSHOT.jar
```

Once started, the application will be accessible at:
```
http://localhost:8080
```

---

### **2️⃣ Running with Docker**
If you don't want to install Java or Maven, you can run the application inside a **Docker container**.

#### **📌 Prerequisites**
- **Docker installed** on your system

#### **⚡ Commands to Run in Docker**
1. **Build the Docker Image**:
   ```sh
   docker build -t currency-converter .
   ```
2. **Run the Application**:
   ```sh
   docker run -p 8080:8080 currency-converter
   ```

Once the container is running, the application will be accessible at:
```
http://localhost:8080
```

---

## 🛠 Running Unit Tests

You can run the **unit test cases** using the following commands:

### **📌 Run Tests Locally**
```sh
./mvnw test
```

### **📌 Run Tests Inside Docker**
1. **Build the container** (if not built already):
   ```sh
   docker build -t currency-converter .
   ```
2. **Run tests inside the container**:
   ```sh
   docker run --rm currency-converter ./mvnw test
   ```

---

## 📜 API Endpoints (Example)

| Method | Endpoint                                | Description                  |
|--------|----------------------------------------|------------------------------|
| GET    | `/api/convert/{fromCurrency}/{toCurrency}?amount=VALUE` | Convert currency from one to another |
| GET    | `/api/currencies`                      | Get the list of supported currencies |

📌 **Example Usage**:
- **Convert 100 EUR to USD**:  
  ```
  GET http://localhost:8080/api/convert/EUR/USD?amount=100
  ```
- **Get all available currencies**:  
  ```
  GET http://localhost:8080/api/currencies
  ```

---

## ❓ Troubleshooting

- **Port Already in Use**  
  If you get a **"Port 8080 is already in use"** error, try running with a different port:
  ```sh
  docker run -p 9090:8080 currency-converter
  ```
  Then access it via `http://localhost:9090`.

- **Docker Image Not Found**  
  If `docker run` fails, ensure the image is built by running:
  ```sh
  docker build -t currency-converter .
  ```

