# ESB School Project

##Service Design Lab

### The Company

- Offre de création de bières personalisé (on choisit le céréale, et les différents gouts, niveau de fermentation).
- L'idée est que nous on fait massérer la bière, et le client a le role de customisation de sa bère.
- Rajouter une dimension sociale.
- Notre expertise, votre bière.

### Services

- Customer account (ressources)
- product catalogue (ressources)
- Avancement de fabrication de sa bière (ressources).
- Customisation de sa bière (RPC).
- Partage de recettes crées de bières (Document et ressource ?).
- Notifications de changement d'étapes dans la fabrication (changement effectué par un administrateur) (RPC).
- Lister les commandes côté administration (ressource).

### Scenarios

1)  
- Le client souhaite commander une bière pour l'anniversaire d'un ami.
- Il se connecte (après avoir créé son compte) et sélectionne le céréale de la bière, le niveau de massération, et les gouts (ingérdients) qu'il veut mettre dans sa bière
- La commande est envoyée à l'entreprise, celle-ci indique au client dans combien de temps elle sera prete, et lui fournit des informations en temps réel (étapes de massération, degré de la bière ...). 

2)  
- Le client souhaite faire un bon repas, il ne sait pas quoi servir comme bière avec !
- Il se rend sur le site de l'entreprise et rentre son repas, le site lui conseille un type de bière pour le repas souhaité.
- Le site indique dans combien de temps elle est prete.
- Le client valide ou non, peut retoucher la bière proposée etc ...

3) 
- Le client souhaite une bière dans x jours.
- L'entreprise lui propose quelle type de bières et ingrédients sont disponibles pour que sa bière soit créée à la date choisie par l'utilisateur.
- Le client choisit les ingrédients, le céréale, et la massération parmi les choix renvoyées par le site internet et valide sa commande.