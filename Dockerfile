ARG PYTHON_SECURITY_VERSION=1.1.0
ARG PYCHARM_VERSION=2019.3.2
FROM ubuntu:18.04
ARG PYTHON_SECURITY_VERSION
ARG PYCHARM_VERSION
RUN echo "Building PyCharm $PYCHARM_VERSION with python-security $PYTHON_SECURITY_VERSION"
WORKDIR /sources
RUN apt-get -y update && apt-get -y install wget
RUN wget https://download.jetbrains.com/python/pycharm-community-${PYCHARM_VERSION}.tar.gz && tar xzf pycharm-community-${PYCHARM_VERSION}.tar.gz -C /opt/
RUN apt-get -y install unzip
RUN wget https://github.com/tonybaloney/pycharm-security/releases/download/1.10.0/pycharm-security-${PYTHON_SECURITY_VERSION}.zip && unzip pycharm-security-${PYTHON_SECURITY_VERSION}.zip -d /opt/pycharm-community/plugins
RUN wget https://github.com/tonybaloney/pycharm-security/raw/docker/doc/_static/SecurityInspectionProfile.xml
ENTRYPOINT /opt/pycharm-community/bin/inspect.sh /code /sources/SecurityInspectionProfile.xml out/ -format plain -v1
