# Riemann Builder Image
FROM openshift/base-centos7

MAINTAINER Bernd Hois <bernd.hois@bearingpoint.com>

ENV RIEMANN_VERSION=0.2.14 \
    JAVA_VERSION=1.8.0 \
    RIEMANN_HOME=/usr/share/riemann \
    PATH=${RIEMANN_HOME}/bin:$PATH

LABEL io.k8s.description="Riemann Builder Image" \
      io.k8s.display-name="riemann ${RIEMANN_VERSION}" \
      io.openshift.expose-services="5555:riemann" \
      io.openshift.tags="riemann,${RIEMANN_VERSION}"

RUN \
  rpm --rebuilddb && yum clean all && \
  yum install -y tar wget java-${JAVA_VERSION}-openjdk openssl && \
  mkdir -p ${RIEMANN_HOME}/etc/streams \
  cd ${RIEMANN_HOME} && \
  wget https://github.com/riemann/riemann/releases/download/${RIEMANN_VERSION}/riemann-${RIEMANN_VERSION}.tar.bz2 && \
  tar xvf riemann-${RIEMANN_VERSION}.tar.bz2 -C ${RIEMANN_HOME} --strip-components=1 && \
  rm -f riemann-${RIEMANN_VERSION}.tar.bz2 && \
  yum clean all -y

# Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./.s2i/bin/ /usr/libexec/s2i

# Drop the root user and make the content of ${LOGTASH_HOME} owned by user 1001
RUN chown -R 1001:1001 ${RIEMANN_HOME}

# This default user is created in the openshift/base-centos7 image
USER 1001

CMD ["/usr/libexec/s2i/usage"]
