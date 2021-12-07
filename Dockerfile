FROM openjdk:11

RUN echo "deb http://apt.postgresql.org/pub/repos/apt bullseye-pgdg main" > /etc/apt/sources.list.d/pgdg.list
RUN wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add -
RUN apt-get update && apt-get install --no-install-recommends -y \
    wget unzip xvfb libxext6 libxi6 libxtst6 libxrender1 libpangoft2-1.0-0 \
    postgresql
RUN /usr/bin/pg_ctlcluster 14 main start && \
    runuser -l postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD 'postgres'\"" && \
    /usr/bin/pg_ctlcluster 14 main stop

ENV VERSION=21.2.2
ENV WEBSWING_DISTRIBUTION_NAME=webswing-examples-eval-$VERSION \
    WEBSWING_HOME=/opt/webswing \
    WEBSWING_JAVA_OPTS="-Xmx128M -Djava.net.preferIPv4Stack=true -Dwebswing.admin.url=/admin" \
    WEBSWING_OPTS="-h 0.0.0.0 -j jetty.properties -serveradmin -pfa admin/webswing-admin.properties -adminctx /admin -aw admin/webswing-admin-server.war" \
    DISPLAY=:99

COPY $WEBSWING_DISTRIBUTION_NAME-distribution.zip /opt/webswing/webswing.zip

RUN unzip /opt/webswing/webswing.zip -d /opt/webswing && mv /opt/webswing/$WEBSWING_DISTRIBUTION_NAME/* /opt/webswing/ && rm -d /opt/webswing/webswing.zip /opt/webswing/$WEBSWING_DISTRIBUTION_NAME
RUN sed -i 's/http:\/\/localhost:8080\/admin/${webswing.admin.url}/' /opt/webswing/webswing.config

WORKDIR /opt/webswing

COPY /src/io/github/ailtonbsj/apachepoi ./apps/internshipmanager
COPY /src/io/github/ailtonbsj/postgresql ./apps/internshipmanager
COPY InternshipManager.jar ./apps/internshipmanager
COPY modelos ./apps/internshipmanager/modelos
COPY webswing.config .

RUN mkdir -p /etc/service/xvfb && \
    echo "#!/bin/sh\nexec Xvfb :99" > /etc/service/xvfb/run && \
    chmod +x /etc/service/xvfb/run

RUN mkdir /etc/service/webswing && \
    echo "#!/bin/sh\ncd $WEBSWING_HOME\nexec $JAVA_HOME/bin/java \$WEBSWING_JAVA_OPTS \$1 -jar $WEBSWING_HOME/webswing-server.war \$WEBSWING_OPTS" > /etc/service/webswing/run && \
    chmod +x /etc/service/webswing/run

EXPOSE 8080

COPY start.sh /opt/webswing/start.sh
RUN chmod +x /opt/webswing/start.sh
CMD ./start.sh