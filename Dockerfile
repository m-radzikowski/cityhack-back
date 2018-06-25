FROM maven:3.5-jdk-8 AS build

WORKDIR /app

# Download wait-for-it
RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
	&& chmod +x wait-for-it.sh  \
	&& mv wait-for-it.sh /usr/local/bin/wait-for-it

# Copy sources
COPY src ./src
COPY pom.xml .

# Build app
RUN mvn clean package -DskipTests

FROM maven:3.5-jdk-8 AS app

# Copy files from first container
COPY --from=build /usr/local/bin/wait-for-it /usr/local/bin/wait-for-it
COPY --from=build /app/target/cityhack-backend.jar .

# Start app
CMD ["java", "-jar", "cityhack-backend.jar"]