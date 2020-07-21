FROM java:8

COPY *.jar /message.jar

CMD ["--server.port=9522"]

EXPOSE 9522

ENTRYPOINT ["java","-jar","/message.jar"]