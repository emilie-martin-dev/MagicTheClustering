#!/bin/sh

cd ../app
chmod +x gradlew

echo "Compilation et lancement du projet, merci de patienter"

./gradlew run
