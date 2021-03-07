#!/bin/sh

cd ../app
chmod +x gradlew

echo "Compilation et lancement du projet, merci de patienter"
echo "Note: Le lancement par ce mode pour jouer au livre n'est pas préconisé. Merci d'utiliser le script pour générer l'application: ./dist.sh"

./gradlew run
