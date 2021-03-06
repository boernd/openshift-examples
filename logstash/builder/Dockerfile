# Logstash Builder Image
FROM openshift/base-centos7

MAINTAINER Bernd Hois <bernd.hois@bearingpoint.com>

ENV LOGSTASH_VERSION=5.6.0 \
    JAVA_VERSION=1.8.0 \
    LOGSTASH_HOME=/usr/share/logstash \
    PATH=${LOGSTASH_HOME}/bin:$PATH

LABEL io.k8s.description="Logstash Builder Image" \
      io.k8s.display-name="logstash ${LOGSTASH_VERSION}" \
      io.openshift.expose-services="8080:http" \
      io.openshift.tags="logstash,${LOGSTASH_VERSION},belk"

RUN \
  rpm --rebuilddb && yum clean all && \
  yum install -y tar java-${JAVA_VERSION}-openjdk openssl && \
  mkdir -p ${LOGSTASH_HOME}/pipeline ${LOGSTASH_HOME}/templates ${LOGSTASH_HOME}/logs && \
  chmod a+w ${LOGSTASH_HOME}/logs && \
  cd ${LOGSTASH_HOME} && \
  curl -O https://artifacts.elastic.co/downloads/logstash/logstash-${LOGSTASH_VERSION}.tar.gz && \
  tar zxvf logstash-${LOGSTASH_VERSION}.tar.gz -C ${LOGSTASH_HOME} --strip-components=1 && \
  rm -f logstash-${LOGSTASH_VERSION}.tar.gz && \
  yum clean all -y

# Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./.s2i/bin/ /usr/libexec/s2i

# Drop the root user and make the content of ${LOGTASH_HOME} owned by user 1001
RUN chown -R 1001:1001 ${LOGSTASH_HOME}

# This default user is created in the openshift/base-centos7 image
USER 1001

CMD ["/usr/libexec/s2i/usage"]
