FROM openjdk:17-jdk-alpine
WORKDIR /opt/shoplive
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY ffmpeg-release-amd64-static.tar.xz .
RUN tar -xf ffmpeg-release-amd64-static.tar.xz
RUN chown -R root:root /opt/shoplive/ffmpeg-5.1.1-amd64-static
RUN mkdir -p /opt/shoplive/shoplive_resource/upload
RUN mkdir -p /opt/shoplive/shoplive_resource/resized
RUN mkdir -p /opt/shoplive/shoplive_resource/thumbnail
ENTRYPOINT ["java","-jar","app.jar"]
