CURRENTDATE=`date +%Y%m%d`

all: build

build:
	./gradlew build
	docker build -t whhoesj/sjtekcontrol-core:latest .
	docker build -t whhoesj/sjtekcontrol-core:$(CURRENTDATE) .

push:
	docker push whhoesj/sjtekcontrol-core:latest
	docker push whhoesj/sjtekcontrol-core:$(CURRENTDATE)

clean:
	-docker rmi whhoesj/sjtekcontrol-core:latest
	-docker rmi whhoesj/sjtekcontrol-core:$(CURRENTDATE)
