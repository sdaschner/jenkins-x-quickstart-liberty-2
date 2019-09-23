FROM open-liberty:javaee8-java12

#COPY src/main/liberty/config/ /config/
# Kaniko doesn't preserve symlinks (/config/ is a symlink)
COPY src/main/liberty/config/ /opt/ol/wlp/usr/servers/defaultServer/

#COPY target/*.war /config/apps/
COPY target/*.war /opt/ol/wlp/usr/servers/defaultServer/apps/
