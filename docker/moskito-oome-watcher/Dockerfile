# Use an official alpine small-footprint jre runtime as a parent image
FROM openjdk:8-jre-alpine

# Set the working directory to /app
WORKDIR /app

# It is required that the jar file has been placed into the context before execution.
# We do this by building this dockerfile via build_oome-watcher.sh executed from parent directory.
# Alternatively you can copy it manually (from parent directory):
# cp ../moskito-extensions/moskito-outofmemory-watcher/target/*with-dependencies.jar moskito-oome-watcher/moskito-oome-watcher.jar
# Copy the current directory contents into the container at /app
COPY . /app
COPY .profile /root/

# Make port 9451 available to the world outside this container
EXPOSE 9451

# Define environment variable
# ENV is set for ash to read profile.
ENV ENV .profile
ENV SHINIT .profile

# Add vim for convinience in editing if required.
RUN apk add vim

ENTRYPOINT /app/start.sh ; 
