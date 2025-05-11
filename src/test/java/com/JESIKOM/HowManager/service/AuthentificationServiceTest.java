package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Utilisateur;
import com.JESIKOM.HowManager.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthentificationServiceTest {

    private AuthentificationService authService;
    private UtilisateurRepository utilisateurRepository;

    @BeforeEach
    public void setUp() {
        utilisateurRepository = mock(UtilisateurRepository.class);
        authService = new AuthentificationService();
        authService.setUtilisateurRepository(utilisateurRepository); // setter à implémenter dans le service
    }

    @Test
    public void testAuthentifier_ValidCredentials_ShouldReturnUser() throws Exception {
        // Données
        String email = "test@example.com";
        String motDePasse = "securepass";
        String motDePasseHashe = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(motDePasseHashe);

        // Mocking
        when(utilisateurRepository.findByEmail(email)).thenReturn(Optional.of(utilisateur));

        // Test
        Utilisateur result = authService.authentifier(email, motDePasse);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testAuthentifier_InvalidPassword_ShouldThrowException() {
        // Données
        String email = "test@example.com";
        String motDePasse = "wrongpass";
        String motDePasseHashe = BCrypt.hashpw("correctpass", BCrypt.gensalt());

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(motDePasseHashe);

        // Mocking
        when(utilisateurRepository.findByEmail(email)).thenReturn(Optional.of(utilisateur));

        // Test
        Exception exception = assertThrows(Exception.class, () -> {
            authService.authentifier(email, motDePasse);
        });

        assertEquals("Email ou mot de passe invalide", exception.getMessage());
    }

    @Test
    public void testAuthentifier_UnknownEmail_ShouldThrowException() {
        String email = "unknown@example.com";

        when(utilisateurRepository.findByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            authService.authentifier(email, "anyPassword");
        });

        assertEquals("Email ou mot de passe invalide", exception.getMessage());
    }
}
