# Kibana Builder Image
FROM openshift/base-centos7

MAINTAINER Bernd Hois <bernd.hois@bearingpoint.com>

ENV KIBANA_VERSION=5.6.0 \
    KIBANA_HOME=/usr/share/kibana \
    PATH=${KIBANA_HOME}/bin:$PATH

LABEL io.k8s.description="Kibana Builder Image" \
      io.k8s.display-name="kibana ${KIBANA_VERSION}" \
      io.openshift.expose-services="5601:http" \
      io.openshift.tags="kibana,${KIBANA_VERSION}"

RUN \
  rpm --rebuilddb && yum clean all && \
  yum install -y tar openssl && \
  mkdir ${KIBANA_HOME} && \
  cd ${KIBANA_HOME} && \
  curl -O https://artifacts.elastic.co/downloads/kibana/kibana-${KIBANA_VERSION}-linux-x86_64.tar.gz && \
  tar xvf kibana-${KIBANA_VERSION}-linux-x86_64.tar.gz -C ${KIBANA_HOME} --strip-components=1 && \
  rm -f kibana-${KIBANA_VERSION}-linux-x86_64.tar.gz && \
  yum clean all -y

# Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./.s2i/bin/ /usr/libexec/s2i

# Drop the root user and make the content of ${LOGTASH_HOME} owned by user 1001
RUN chown -R 1001:1001 ${KIBANA_HOME}

# This default user is created in the openshift/base-centos7 image
USER 1001

CMD ["/usr/libexec/s2i/usage"]
