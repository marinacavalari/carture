# Use https://hub.docker.com/_/oracle-serverjre-8
FROM java:8-alpine

# Make a directory
RUN mkdir -p /carture
WORKDIR /carture

# Copy only the target jar over
COPY target/carture-*-standalone.jar /carture 

# Open the port
EXPOSE 3000

# Run the JAR
CMD java -jar /carture/carture-*-standalone.jar  
