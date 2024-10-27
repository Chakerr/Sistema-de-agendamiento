package laboratorio.lab.services;

import laboratorio.lab.models.Estudiantes;
import laboratorio.lab.models.Reservas;
import laboratorio.lab.repositories.EstudiantesRepository;
import laboratorio.lab.repositories.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratoristaService {
    @Autowired
    private EstudiantesRepository estudiantesRepository;
    @Autowired
    private ReservasRepository reservasRepository;

    public LaboratoristaService(EstudiantesRepository estudiantesRepository, ReservasRepository reservasRepository) {
        this.estudiantesRepository = estudiantesRepository;
        this.reservasRepository = reservasRepository;
    }

    public List<Reservas> ReservasporEstudiante(Integer idCodigo) {
        return estudiantesRepository.findActiveReservationsByStudent(idCodigo);
    }

    public Integer obtenerTotalVisitas() {
        return estudiantesRepository.totalVisitas();
    }

    public Integer obtenerVisitasPorCodigo(Integer idCodigo) {
        Estudiantes estudiante = estudiantesRepository.findByIdCodigo(idCodigo);
        return (estudiante != null) ? estudiante.getVisitas() : 0;
    }

    public List<Reservas> obtenerTodasLasReservas() {
        return reservasRepository.findAll();
    }

    public long contarReservas() {
        return reservasRepository.count();
    }
}
