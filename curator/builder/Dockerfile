# Curator Builder Image
FROM openshift/base-centos7

MAINTAINER Bernd Hois <bernd.hois@bearingpoint.com>

ENV CURATOR_VERSION=5.2.0 \
    CURATOR_HOME=/usr/share/curator \
    PATH=${CURATOR_HOME}/bin:$PATH

LABEL io.k8s.description="Curator Builder Image" \
      io.k8s.display-name="curator ${CURATOR_VERSION}" \
      io.openshift.tags="curator,${CURATOR_VERSION},belk"

RUN \
  rpm --rebuilddb && yum clean all && \
  mkdir -p ${CURATOR_HOME} \
  cd ${CURATOR_HOME} && \
  curl -O https://packages.elastic.co/curator/5/centos/7/Packages/elasticsearch-curator-${CURATOR_VERSION}-1.x86_64.rpm && \
  rpm -i elasticsearch-curator-${CURATOR_VERSION}-1.x86_64.rpm && \
  rm -f elasticsearch-curator-${CURATOR_VERSION}-1.x86_64.rpm

# Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./.s2i/bin/ /usr/libexec/s2i

# Drop the root user and make the content of ${LOGTASH_HOME} owned by user 1001
RUN chown -R 1001:1001 ${CURATOR_HOME}

# This default user is created in the openshift/base-centos7 image
USER 1001

CMD ["/usr/libexec/s2i/usage"]
