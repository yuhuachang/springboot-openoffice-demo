#FROM amd64/debian
#FROM openjdk:11

FROM public.ecr.aws/lambda/java:11

# Copy function code and runtime dependencies from Gradle layout
#COPY build/classes/java/main ${LAMBDA_TASK_ROOT}
#COPY build/dependency/* ${LAMBDA_TASK_ROOT}/lib/

# Set the CMD to your handler (could also be done as a parameter override outside of the Dockerfile)
#CMD [ "com.example.LambdaHandler::handleRequest" ]

#RUN yum update -y

#RUN yum -y install wget
RUN yum -y install tar
RUN yum -y install gzip

WORKDIR /tmp
#RUN wget https://free.nchc.org.tw/tdf/libreoffice/stable/7.1.4/rpm/x86_64/LibreOffice_7.1.4_Linux_x86-64_rpm_helppack_en-US.tar.gz
RUN curl https://free.nchc.org.tw/tdf/libreoffice/stable/7.1.4/rpm/x86_64/LibreOffice_7.1.4_Linux_x86-64_rpm_helppack_en-US.tar.gz --output LibreOffice_7.1.4_Linux_x86-64_rpm_helppack_en-US.tar.gz
#RUN pwd
#RUN ls -lh
RUN tar -zxvf LibreOffice_7.1.4_Linux_x86-64_rpm_helppack_en-US.tar.gz
#RUN ls -lh
WORKDIR LibreOffice_7.1.4.2_Linux_x86-64_rpm_helppack_en-US/RPMS
#RUN pwd
#RUN ls -lh
RUN yum -y --skip-broken install *.rpm

#RUN apt-get update -y \
#    && apt-get install -y --no-install-recommends libreoffice \
#    && apt-get install -y procps \
#    && apt-get clean
#RUN apt-get update; \
#    apt-get install -y --no-install-recommends libreoffice; \
#    rm -rf /var/lib/apt/lists/*

#########################################
#RUN addgroup spring
#RUN adduser --system --shell=/bin/nologin spring
#USER spring:spring
WORKDIR /
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "/app.jar"]
