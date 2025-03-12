-- Insertions des clients dans H2 Database

-- Client 1
INSERT INTO client (nom, prenom, telephone, email, adresse, date_naissance, nationalite, num_identite, type_identite, remarque)
VALUES ('Dupont', 'Jean', '0612345678', 'jean.dupont@email.com', '12 rue de Paris, 75001 Paris',
        '1980-05-15', 'Française', '980512AB1234', 'CIN', 'Client fidèle depuis 2015');

-- Client 2
INSERT INTO client (nom, prenom, telephone, email, adresse, date_naissance, nationalite, num_identite, type_identite, remarque)
VALUES ('Martin', 'Sophie', '0723456789', 'sophie.martin@email.com', '25 avenue Victor Hugo, 69002 Lyon',
        '1992-08-22', 'Française', '12BC34567', 'PASSEPORT', 'Préfère être contactée par email');

-- Client 3
INSERT INTO client (nom, prenom, telephone, email, adresse, date_naissance, nationalite, num_identite, type_identite, remarque)
VALUES ('Rodriguez', 'Carlos', '0734567890', 'carlos.rodriguez@email.com', '8 rue des Lilas, 31000 Toulouse',
        '1975-03-10', 'Espagnole', 'ESP75031012345', 'PASSEPORT', 'Parle espagnol et français');

-- Client 4
INSERT INTO client (nom, prenom, telephone, email, adresse, date_naissance, nationalite, num_identite, type_identite, remarque)
VALUES ('Dubois', 'Marie', '0645678901', 'marie.dubois@email.com', '42 boulevard Haussmann, 75009 Paris',
        '1988-11-05', 'Française', '881105CD5678', 'CIN', 'Allergique aux arachides');

-- Client 5
INSERT INTO client (nom, prenom, telephone, email, adresse, date_naissance, nationalite, num_identite, type_identite, remarque)
VALUES ('Schmidt', 'Hans', '0756789012', 'hans.schmidt@email.com', '15 rue de la Paix, 67000 Strasbourg',
        '1965-07-20', 'Allemande', 'DEU6572012345', 'PASSEPORT', 'Client VIP, traitement prioritaire');

-- Insertion des logements dans H2 Database

-- Logement 1
INSERT INTO logement (logement_id, type, capacite, disponible, propre, commentaire, prix)
VALUES (1, 'APPARTEMENT', 4, 1, 1, 'Appartement moderne avec balcon et vue sur la ville.', 120.0);

-- Logement 2
INSERT INTO logement (logement_id, type, capacite, disponible, propre, commentaire, prix)
VALUES (2, 'MAISON', 6, 1, 0, 'Maison spacieuse avec jardin, en attente de nettoyage.', 200.0);

-- Logement 3
INSERT INTO logement (logement_id, type, capacite, disponible, propre, commentaire, prix)
VALUES (3, 'STUDIO', 2, 0, 1, 'Studio cosy, actuellement réservé pour la semaine.', 80.0);

-- Logement 4
INSERT INTO logement (logement_id, type, capacite, disponible, propre, commentaire, prix)
VALUES (4, 'BUNGALOW', 5, 1, 1, 'Bungalow en bord de plage, idéal pour des vacances relaxantes.', 150.0);

-- Insertion des réservations dans H2 Database

-- Réservation 1 : Client 1, Logement 1
INSERT INTO reservation (client_id, logement_id, date_reservation, date_debut, nombre_nuits, nombre_personnes, statut, acompte, remarque, mode_paiement, check_in, check_out)
VALUES (1, 1, '2025-03-12T14:30:00', '2025-04-01', 5, 2, 'CONFIRMEE', 150.0, 'Besoin d''un lit bébé.', 'CARTE_BANCAIRE', NULL, NULL);

-- Réservation 2 : Client 2, Logement 3
INSERT INTO reservation (client_id, logement_id, date_reservation, date_debut, nombre_nuits, nombre_personnes, statut, acompte, remarque, mode_paiement, check_in, check_out)
VALUES (2, 3, '2025-03-12T15:00:00', '2025-04-15', 3, 1, 'EN_ATTENTE', 50.0, 'Arrivée tardive prévue.', 'MOBILE_MONEY', NULL, NULL);
