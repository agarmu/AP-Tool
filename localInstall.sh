#!/bin/bash

./gradlew build &&
cd ./build/distributions/ &&
rm -rf aptool $HOME/.utils/ap $HOME/.utils/aptool &&
tar -xvf aptool.tar &&
cp -R aptool $HOME/.utils/ap &&
rm -rf aptool &&
cd $HOME/.utils &&
ln -s aptool/bin/aptool ap &&
echo "Finished!" || echo "Error. Terminating..." && exit 1

