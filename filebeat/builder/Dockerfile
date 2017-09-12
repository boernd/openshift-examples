# Filebeat Builder Image
FROM openshift/base-centos7

MAINTAINER Bernd Hois <bernd.hois@bearingpoint.com>

ENV FILEBEAT_VERSION=5.6.0 \
    FILEBEAT_HOME=/usr/share/filebeat \
    PATH=${FILEBEAT_HOME}/bin:$PATH

LABEL io.k8s.description="Filebeat Builder Image" \
      io.k8s.display-name="filebeat ${FILEBEAT_VERSION}" \
      io.openshift.tags="filebeat,${FILEBEAT_VERSION},belk"

RUN \
  rpm --rebuilddb && yum clean all && \
  mkdir -p ${FILEBEAT_HOME} \
  cd ${FILEBEAT_HOME} && \
  curl -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz && \
  tar zxvf filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz -C ${FILEBEAT_HOME} --strip-components=1 && \
  rm -f filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz  

# Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./.s2i/bin/ /usr/libexec/s2i

# Drop the root user and make the content of ${LOGTASH_HOME} owned by user 1001
RUN chown -R 1001:1001 ${FILEBEAT_HOME}

# This default user is created in the openshift/base-centos7 image
USER 1001

CMD ["/usr/libexec/s2i/usage"]
