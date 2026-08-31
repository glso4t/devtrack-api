# Ξεκινάμε από ένα έτοιμο image που έχει Java 17
FROM eclipse-temurin:17-jre

# Ορίζει τον φάκελο εργασίας μέσα στο container
WORKDIR /app

# Αντιγράφουμε το έτοιμο .jar από τον υπολογιστή μας στο container
COPY target/*.jar app.jar

# Λέμε στο container ποια θύρα χρησιμοποιεί το API
EXPOSE 8080

# Η εντολή που τρέχει το API μόλις ξεκινήσει το container
ENTRYPOINT ["java", "-jar", "app.jar"]