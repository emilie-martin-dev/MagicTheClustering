#!/bin/sh

cd ../app
chmod +x gradlew

echo "Compilation et lancement de la javadoc, merci de patienter"

./gradlew javadoc

echo "Javadoc générée"
echo "Lien vers la Javadoc : file://$(pwd)/build/docs/javadoc/index.html"
