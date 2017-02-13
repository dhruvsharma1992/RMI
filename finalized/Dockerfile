FROM java:7
# Install Java.
# Define working directory.
RUN apt-get update
RUN apt-get install  -y build-essential 
WORKDIR /root/RMI
COPY ./ /root/RMI
# Define commonly used JAVA_HOME variable

# Define default command.
CMD ["bash"]
