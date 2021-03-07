#!/bin/sh

cd ../app
chmod +x gradlew

echo "Compilation et test du projet, merci de patienter"

./gradlew test

if [ $? -eq 0 ]; then
	echo "Tous les tests on réussis"
fi

echo ""
echo "Pour plus de détails sur les tests : file://$(pwd)/build/reports/tests/test/index.html"
