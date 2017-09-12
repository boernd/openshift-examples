# Elasticsearch Builder Image
FROM openshift/base-centos7

MAINTAINER Bernd Hois <bernd.hois@bearingpoint.com>

ENV ELASTICSEARCH_VERSION=5.6.0 \
    JAVA_VERSION=1.8.0 \
    ELASTICSEARCH_HOME=/usr/share/elasticsearch \
    PATH=${ELASTICSEARCH_HOME}/bin:$PATH

LABEL io.k8s.description="Elasticsearch Builder Image" \
      io.k8s.display-name="elasticsearch ${ELASTICSEARCH_VERSION}" \
      io.openshift.expose-services="9200:http" \
      io.openshift.tags="elasticsearch,${ELASTICSEARCH_VERSION},belk"

RUN \
  rpm --rebuilddb && yum clean all && \
  yum install -y tar java-${JAVA_VERSION}-openjdk openssl && \
  mkdir -p ${ELASTICSEARCH_HOME}/conf.d \
  cd ${ELASTICSEARCH_HOME} && \
  curl -O https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-${ELASTICSEARCH_VERSION}.tar.gz && \
  tar zxvf elasticsearch-${ELASTICSEARCH_VERSION}.tar.gz -C ${ELASTICSEARCH_HOME} --strip-components=1 && \
  rm -f elasticserach-${ELASTICSEARCH_VERSION}.tar.gz && \
  yum clean all -y

# Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./.s2i/bin/ /usr/libexec/s2i

# Drop the root user and make the content of ${ELASTICSEARCH_HOME} owned by user 1001
RUN chown -R 1001:1001 ${ELASTICSEARCH_HOME}

# This default user is created in the openshift/base-centos7 image
USER 1001

CMD ["/usr/libexec/s2i/usage"]
