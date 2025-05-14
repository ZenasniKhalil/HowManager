package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.repository.ClientRepository;
import com.JESIKOM.HowManager.repository.LogementRepository;
import com.JESIKOM.HowManager.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private LogementRepository logementRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private Client client;
    private Logement logement;

    @BeforeEach
    void setUp() {
        client = new Client("Doe", "John", "0123456789", "john.doe@example.com", "123 Rue Exemple",
                LocalDate.of(1990, 5, 15), "Française", "123456789", TypeIdentite.CNI, "Aucun");

        logement = new Logement();
        logement.setNumero(1);
        logement.setType(TypeLogement.APPARTEMENT);
        logement.setCapacite(4);
        logement.setDisponible(true);
        logement.setPrix(100.0);

        reservation = new Reservation(client, logement, LocalDateTime.now(), LocalDate.of(2025, 4, 10),
                5, 2,0, StatutReservation.CONFIRMEE, 50.0, "Aucun", ModePaiement.CARTE_BANCAIRE, null, null);
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        List<Reservation> reservations = reservationService.getAllReservations();

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testGetReservationById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> foundReservation = reservationService.getReservationById(1L);

        assertTrue(foundReservation.isPresent());
        assertEquals(reservation.getId(), foundReservation.get().getId());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void testAddReservation_Success() {
        when(reservationRepository.findReservationsByLogement_Numero(logement.getNumero())).thenReturn(List.of());
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation savedReservation = reservationService.addReservation(reservation);

        assertNotNull(savedReservation);
        assertEquals(reservation.getId(), savedReservation.getId());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testAddReservation_Failure_LodgingNotAvailable() {
        when(reservationRepository.findReservationsByLogement_Numero(logement.getNumero()))
                .thenReturn(List.of(new Reservation(client, logement, LocalDateTime.now(),
                        LocalDate.of(2025, 4, 9), 5, 2,0, StatutReservation.CONFIRMEE, 50.0, "Aucun", ModePaiement.CARTE_BANCAIRE, null, null)));

        Reservation savedReservation = reservationService.addReservation(reservation);

        assertNull(savedReservation);
    }

    @Test
    void testUpdateReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(1L, reservation);

        assertNotNull(updatedReservation);
        assertEquals(1L, updatedReservation.getId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testDeleteReservation() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetReservationsByDate_Debut() {
        when(reservationRepository.findReservationByDateDebut(LocalDate.of(2025, 4, 10)))
                .thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.getReservationsByDate_Debut(LocalDate.of(2025, 4, 10));

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1)).findReservationByDateDebut(LocalDate.of(2025, 4, 10));
    }

    @Test
    void testGetReservationsByClient() {
        when(reservationRepository.findReservationByClient_Id(client.getId())).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.getReservationsByClient(client.getId());

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1)).findReservationByClient_Id(client.getId());
    }

    @Test
    void testGetDate_Fin() {
        LocalDate expectedEndDate = reservation.getDateDebut().plusDays(reservation.getNombreNuits());

        LocalDate actualEndDate = reservationService.getDate_Fin(reservation);

        assertEquals(expectedEndDate, actualEndDate);
    }
}
