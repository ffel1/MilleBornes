# Mille Bornes -

Bienvenue dans le projet **Mille Borne - Pixel Art Edition**, une version réinventée du célèbre jeu de cartes Mille Borne, implémentée en Java avec des graphismes en pixel art. Ce projet met en œuvre des concepts avancés de programmation Java, une interface graphique immersive et une ambiance retro.

---

## Table des Matières

1. [Introduction](#introduction)
2. [Fonctionnalités](#fonctionnalités)
3. [Prérequis](#prérequis)
4. [Installation](#installation)
5. [Utilisation](#utilisation)
6. [Structure du Projet](#structure-du-projet)
7. [Crédits](#crédits)
8. [Licence](#licence)

---

## Introduction

Le but de ce projet est de proposer une manière de jouer seul au jeu du Mille Bornes en suivant le plus fidèlement possible les mécaniques du jeu original.
Les joueurs gagnent les manches en étant les premiers à parcourir les 700 bornes du plateau ou lorsque la pioche se vide. A la fin de chaque manche, les points sont comptés et la manche suivante commence.
Le premier joueur arrivé à 5000 points gagne la partie !

---

## Fonctionnalités

- **Gameplay classique** : Toutes les règles originales de Mille Borne sont respectées, à l'exception des "coup fourrés"
- **Graphismes pixel art** : Chaque carte, joueur et élément de l'interface sont dessinés en pixel art.
- **Mode solo** : Jouez contre deux joueurs contrôlés par l'ordinateur, chacun avec une personnalité bien distincte.
- **Animations immersives** : Les actions en jeu sont accompagnées d’animations ludiques ainsi que de messages dans une fenêtre pour suivre la partie dans tous ses détails.
- **Sauvegarde et chargement** : Reprenez votre partie à tout moment et accédez aux parties précédentes depuis le menu des historiques. Il est possible d'effacer l'historique pour tout recommencer à 0.

---

## Prérequis

- **Java 17 ou version supérieure**
- Une machine avec un système d'exploitation Windows, macOS ou Linux.
- (Optionnel) **IDE recommandé** : IntelliJ IDEA ou Eclipse.

---

## Installation

1. Clonez ce dépôt Git :
   ```bash
   git clone https://github.com/ffel1/MilleBornes.git
   ```
2. Naviguez dans le répertoire du projet :
   ```bash
   cd MilleBornes
   ```
3. Exécutez le fichier JAR :
   ```bash
   java -jar MilleBornes.jar
   ```

---

## Utilisation

1. Lancez le programme via la commande ou l'IDE de votre choix.
2. Sélectionnez Jouer pour lancer la partie. Si c'est votre premier lancement, un fichier de sauvegarde va être créé.
3. Profitez du jeu !

---

## Structure du Projet

Images/ : Contient les sprites et les graphismes en pixel art.

assets/ : Contient les fichiers piskel permettant de modifier les sprites

lib/ : Bibliothèques externes utilisées par le projet.

son/ : Fichiers audio pour les effets sonores et la musique.

src/ : Code source Java organisé en plusieurs packages (main et test)

MilleBornes.jar : Fichier exécutable du jeu.

README.txt : Ce fichier de documentation.

.gitignore : Fichier listant les fichiers et dossiers à ignorer par Git

---

## Crédits

- **Développement** : Xavier DEGRAEVE, Enzo DERO, Max M'BEY, Felix CLOEREC
- **Graphismes** : Xavier DEGRAEVE
- **Tests** : Xavier DEGRAEVE, Enzo DERO, Max M'BEY, Felix CLOEREC

---


