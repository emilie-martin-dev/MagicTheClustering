# Présentation et Objectifs de l'application

L'objectif de notre application est de déterminer la similarité entre différents
decks du jeu de carte Magic et de les rassembler dans des clusters pour pouvoir
identifier différentes catégories de decks.

Nous avons réussis à créer une application qui va répartir un ensemble de decks
en un nombre de cluster prédéfinis en se basant sur les noms de cartes présents
dans nos decks grâce à la distance de Jaccard et aux k-medoïdes.  

# Compilation du projet

Pour lancé notre application, nous avons deux commandes possibles:

- Nous pouvons passer par le fichier run.sh qui se trouve dans le répertoire
script pour celà, nous devons ouvrir un terminal dans le répertoire courant et
entre les commandes suivantes:

``` bash
cd scripts
```

``` bash
./run.sh
```

- Nous pouvons également passer par le fichier gradlew présent dans le répertoire
app, en ouvrant un terminal dans le répertoire courant:

``` bash
cd app
```

``` bash
./gradlew run
```

Nous avons également la possibilité de générer une archive.jar exécutable grâce
à un script dist.sh qui génèrera un répertoire dist contenant une archive.jar et
l'ensemble des fichiers nécessaire au lancement de notre application, en ouvrant
 un terminal dans le repertoire courant:

``` bash
cd scripts
```

``` bash
./dist.sh
```

WARNING: il est possible que votre terminal vous informe que vous n'avez pas les
droits pour exécuter les scripts ou le gradlew, pour résoudre ce problème,
lancer la commande suivante:

``` bash
chmod +X <adresse_script>
```

# Structuration du projet

Le projet comporte les dossiers suivants :
- app → Contient le code source de l'application
	- data → ensemble de nos données brutes et données traités
		- extensions → ensemble des fichiers Json représentants les extensions
		magic sortis entre 1993 et 1994
		- tdecks → ensemble des fichiers txt représentant nos decks à analyser
		- cards.json → fichier Json simplifié contenant la liste des cartes de
		toutes les extensions
		- parser.py → fichier python générant notre fichier cards.json
	- gradle → ensemble des fichiers nécessaire à l'utilisation de gradlew
	- src → code de l'application
		- main → code source du projet
			- java → ensemble de nos classes java
				- algo → contient les classes se chargeant de l'algorithme de
				Jaccard et de la gestion des kmedoïdes
				- io → contient les classes s'occupant du parsing des données
				- model → contient les classes qui représentent nos cartes, nos
				decks et une énumération des 6 types majeurs de cartes
		- test → tests unitaires
	- gradlew → script pour utiliser les commandes gradlew
- scripts → Contient différents scripts pour gérer le projet
	- dist.sh → script créant répertoire dist contenant une archive jar et tout
	les fichiers nécessaires à son exécution
	- run.sh → script lançant notre application
	- test.sh → script lançant les test (inutile pour le moment)
	- javadoc.sh → script générant la javadoc (inutile pour le moment)

# Bibliothèques / Framework utilisées

- GSON

# Collaborateurs

Le projet étant réalisé en groupe, voici la liste des personnes affectées sur celui-ci :

- Auréline DEROUIN ([@Norah72](https://github.com/Norah72))
- Paul LEBRANCHU ([@Paul-Lebranchu](https://github.com/Paul-Lebranchu))
- Raphaëlle LEMAIRE ([@seirihiri](https://github.com/seirihiri))
- Justine MARTIN ([@jmartin-pro](https://github.com/jmartin-pro))
