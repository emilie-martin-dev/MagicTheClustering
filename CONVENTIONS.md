# Code source

L'utilisation du CamelCase est obligatoire.

L'indentation se fait en utilisant des tabulations et non des espaces.

De manière globale, tous les termes techniques doivent être en anglais (ex. "Controller").

Les mots métiers restent dans leur langue dont le terme est issu (bien souvent en français, ex. "Lieu").

# Git

## Branches :

La branche ``master`` est toujours la branche avec la version la plus récente et la plus stable. Il est __INTERDIT DE COMMIT DESSUS__.

La branche ``develop`` est une branche en cours de développement. Les différentes évolutions (une fois entièrement fonctionnelles) se retrouvent fusionnées à celle-ci. De ce fait, elle est plus instable que ``master``. Cependant, elle permet aussi à cette dernière de monter en version (par fast-forward) une fois les différentes fonctionnalités satisfaisantes et l'absence de bug confirmé.

Les évolutions se font dans une branche ``features/nom-evolution`` (en partant de ``develop`` de préférence), où ``nom-evolution`` est composé de mots décrivant le sujet de l'évolution et sont séparés par des tirets.

## Format du message :

Contenu en français et explicite, sous la forme suivante :

    <type> [type2] : <sujet>

    [body]

    [footer]

Où ``type`` correspond à un label permettant de comprendre rapidement  le type de changement effectué, ``type2`` peut permettre une précision sur le type de changement (ex : ``chore fix``). Les différents labels possibles sont listés plus loin dans le document.

``sujet`` est un résumé court du changement apporté.

``body`` permet de détailler le sujet de la modification (ce qu'il reste à faire, les bugs qui seront corrigés dans les prochains commits, ...)

``footer`` lui, permet de donner des références vers une ressource qui peut s'avérer utile (un lien vers une carte Trello, un forum, ...)

``body`` tout comme ``footer`` sont optionnels.

La première ligne ne doit pas excéder les 50 caractères. Les détails seront à placer dans le ``body``. Ainsi, il faut éviter les ``sujet`` qui commencent par "Ajout de la ...", "Possibilité de ...", "Correction d'un bug qui ...", etc.

Exemple :

    feat : Gestion des lieux pour les admins

	Permet aux administrateurs :
	- D'ajouter des lieux
	- D'en supprimer

	Elle ajoute aussi un lien dans la barre de navigation afin d'accéder aux pages prévues à cet effet.

    cf : www.un-site-web.com

### Valeurs de ``type`` possibles :

* __feat__ : Nouvelle fonctionnalité pour l’utilisateur, n’est pas une nouvelle fonctionnalité pour les scripts de compilation
* __fix__ : Correction de bug pour l’utilisateur, n’est pas une correction pour les scripts de compilation
* __doc__ : Modification de la documentation
* __sql__ : Ajout ou modification d'un script SQL
* __style__ : Mise à jour du css, style de l'application
* __refactor__ : Refactoring du code de production, ex. renommer une variable
* __test__ : Ajout de tests manquants, refactoring de tests; pas de changement du code de production
* __chore__ : Mise à jour des tâches Gradle, etc; pas de changement du code de production
