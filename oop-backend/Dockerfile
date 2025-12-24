# ใช้ Maven image เพื่อ build โปรเจค
FROM maven:3.9-eclipse-temurin-17 AS build

# ตั้งค่า working directory
WORKDIR /app

# คัดลอกไฟล์ pom.xml และ mvnw ก่อน (เพื่อใช้ Docker cache)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# คัดลอก source code
COPY src ./src

# Build โปรเจค (compile และ package)
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine

# ตั้งค่า working directory
WORKDIR /app

# คัดลอก JAR file จาก build stage
COPY --from=build /app/target/*.jar app.jar

# สร้าง directory สำหรับ users.txt
RUN mkdir -p /app/src/main/resources

# เปิด port 8080
EXPOSE 8080

# รัน Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

