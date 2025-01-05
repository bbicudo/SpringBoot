# Base image for building java 23.
FROM ubuntu:22.04 as builder

ENV LANG en_US.UTF-8

ENV JAVA_URL=https://download.oracle.com/java/23/latest \
JAVA_HOME=/usr/java/jdk-23

SHELL ["/bin/bash", "-o", "pipefail", "-c"]
RUN set -eux; \
    apt-get update && \
    apt-get install -y curl && \
    ARCH="$(uname -m)" && \
    # Java uses just x64 in the name of the tarball
    if [ "$ARCH" = "x86_64" ]; \
        then ARCH="x64"; \
    fi && \
    JAVA_PKG="$JAVA_URL"/jdk-23_linux-"${ARCH}"_bin.tar.gz ; \
    JAVA_SHA256=$(curl "$JAVA_PKG".sha256) ; \
    curl --output /tmp/jdk.tgz "$JAVA_PKG" && \
    echo "$JAVA_SHA256" */tmp/jdk.tgz | sha256sum -c; \
    mkdir -p "$JAVA_HOME"; \
    tar --extract --file /tmp/jdk.tgz --directory "$JAVA_HOME" --strip-components 1

# Actual image.
FROM ubuntu:22.04

ENV LANG pt_BR.UTF-8
ENV JAVA_HOME=/usr/java/jdk-23
ENV PATH $JAVA_HOME/bin:$PATH

# Copy java installation from builder image
COPY --from=builder $JAVA_HOME $JAVA_HOME

# Install softwares
RUN set -eux; \
    apt-get update && \
    apt-get install -y maven && \
    apt-get install -y iputils-ping && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Link Java Binaries
RUN set -eux; \
    rm -rf /var/lib/apt/lists/* && \
    ln -sfT "$JAVA_HOME" /usr/java/default; \
    ln -sfT "$JAVA_HOME" /usr/java/latest; \
    for bin in "$JAVA_HOME/bin/"*; do \
        base="$(basename "$bin")"; \
        ln -sf "$bin" "/usr/bin/$base"; \
    done;

  # Check versions.
  RUN set -eux; \ 
      echo "Java Version: " && \
      java -version && \
      echo "Javac Version: " && \
      javac -version && \
      echo "Maven Version: " && \
      mvn -version


CMD ["bash"]