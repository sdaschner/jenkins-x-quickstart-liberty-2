CHART_REPO := http://jenkins-x-chartmuseum:8080
CURRENT=$(pwd)
NAME := jenkins-x-quickstart-liberty-2
OS := $(shell uname)
RELEASE_VERSION := $(shell cat ../../VERSION)
DB_VERSION := $(shell cat ../../DB_VERSION)

build: clean
  echo doing a build
	rm -rf requirements.lock
	helm dependency build
	helm lint

install: clean build
  echo doing an install
	helm install . --name ${NAME}

upgrade: clean build
  echo doing an upgrade
	helm upgrade ${NAME} .

delete:
  echo doing a delete
	helm delete --purge ${NAME}

clean:
  echo doing a clean
	rm -rf charts
	rm -rf ${NAME}*.tgz

release: clean
  echo doing a release
	helm dependency build
	helm lint
	helm init --client-only
	helm package .
	curl --fail -u $(CHARTMUSEUM_CREDS_USR):$(CHARTMUSEUM_CREDS_PSW) --data-binary "@$(NAME)-$(shell sed -n 's/^version: //p' Chart.yaml).tgz" $(CHART_REPO)/api/charts
	rm -rf ${NAME}*.tgz%

tag:
  echo doing a tag
ifeq ($(OS),Linux)
	sed -i -e "s/version:.*/version: $(RELEASE_VERSION)/" Chart.yaml
	sed -i -e "s|repository:.*|repository: $(DOCKER_REGISTRY)\/testing-215306\/jenkins-x-quickstart-liberty-2|" values.yaml
	sed -i -e "s/tag:.*/tag: $(RELEASE_VERSION)/" values.yaml
	sed -i -e "s|dbRepository:.*|dbRepository: $(DOCKER_REGISTRY)\/testing-215306\/jenkins-x-quickstart-liberty-2-db-migration|" values.yaml
	sed -i -e "s/dbTag:.*/dbTag: $(DB_VERSION)/" values.yaml
else
	echo "platfrom $(OS) not supported to release from"
	exit -1
endif
  echo updated versions, values.yaml is:
  cat values.yaml
	git add --all
	git commit -m "release $(RELEASE_VERSION)" --allow-empty # if first release then no verion update is performed
	git tag -fa v$(RELEASE_VERSION) -m "Release version $(RELEASE_VERSION)"
	git push origin v$(RELEASE_VERSION)
