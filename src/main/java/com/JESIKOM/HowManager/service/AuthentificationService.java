package com.JESIKOM.HowManager.service;


import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.Utilisateur;
import com.JESIKOM.HowManager.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;


@Service
public class AuthentificationService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /*
    public Utilisateur authentifier(String email, String motDePasse) throws Exception {
        return utilisateurRepository.findByEmail(email)
                .filter(u -> BCrypt.checkpw(motDePasse, u.getMotDePasse()))
                .orElseThrow(() -> new Exception("Email ou mot de passe invalide"));
    }

     */
    public Utilisateur authentifier(String email, String motDePasse) throws Exception {
        return utilisateurRepository.findByEmail(email)
                .map(u -> {
                    System.out.println("Mot de passe entré : " + motDePasse);
                    System.out.println("Mot de passe stocké : " + u.getMotDePasse());
                    boolean match = BCrypt.checkpw(motDePasse, u.getMotDePasse());
                    System.out.println("Correspondance : " + match);
                    return match ? u : null;
                })
                .filter(u -> u != null)
                .orElseThrow(() -> new Exception("Email ou mot de passe invalide"));
    }


    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        // Hachage du mot de passe s'il est modifié (attention à ne pas le hacher plusieurs fois !)
        Utilisateur ancienUtilisateur = utilisateurRepository.findById(utilisateur.getId()).orElse(null);

        if (ancienUtilisateur != null) {
            String ancienMotDePasse = ancienUtilisateur.getMotDePasse();
            String nouveauMotDePasse = utilisateur.getMotDePasse();

            // Si le mot de passe a changé, on le re-hash
            if (!BCrypt.checkpw(nouveauMotDePasse, ancienMotDePasse)) {
                String motDePasseHache = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
                utilisateur.setMotDePasse(motDePasseHache);
            } else {
                // Sinon, on garde l'ancien hash pour éviter un double hashage
                utilisateur.setMotDePasse(ancienMotDePasse);
            }

            utilisateurRepository.save(utilisateur);
        }
    }




}