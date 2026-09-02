#Stage - Build
# stage που κάνει compile/build ένα docker environment που έχει Maven κ Java 17
FROM maven:3.9-eclipse-temurin-17 AS builder

#Working directory
WORKDIR /app

# Παίρνει από το GitHub repository και τα βάζει στο Docker build environment /app
COPY pom.xml .
COPY src ./src

# Docker builds the JAR file (skipping tests during build)
RUN mvn clean package -DskipTests

#Stage 2 - Runtime
# Ξεκινάμε από ένα καινούργιο έτοιμο image που έχει Java 17
FROM eclipse-temurin:17-jre

# Ορίζει τον φάκελο εργασίας μέσα στο container
WORKDIR /app

# Copy the built JAR from the builder stage
#Το --from=builder λέει:«Μην ψάξεις στο GitHub build context. Πήγαινε στο προηγούμενο Docker stage που ονομάζεται builder"
COPY --from=builder /app/target/*.jar app.jar

# Λέμε στο container ποια θύρα χρησιμοποιεί το API
EXPOSE 8080

# Η εντολή που τρέχει το API μόλις ξεκινήσει το container
ENTRYPOINT ["java", "-jar", "app.jar"]