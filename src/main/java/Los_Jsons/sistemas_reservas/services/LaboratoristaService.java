package Los_Jsons.sistemas_reservas.services;

import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.Laboratorista;
import Los_Jsons.sistemas_reservas.models.Reservas;
import Los_Jsons.sistemas_reservas.repositories.EstudiantesRepository;
import Los_Jsons.sistemas_reservas.repositories.LaboratoristaRepository;
import Los_Jsons.sistemas_reservas.repositories.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class LaboratoristaService {
    @Autowired
    private EstudiantesRepository estudiantesRepository;
    @Autowired
    private ReservasRepository reservasRepository;
    @Autowired
    private LaboratoristaRepository laboratoristaRepository;

    public LaboratoristaService(EstudiantesRepository estudiantesRepository, ReservasRepository reservasRepository, LaboratoristaRepository laboratoristaRepository) {
        this.estudiantesRepository = estudiantesRepository;
        this.reservasRepository = reservasRepository;
        this.laboratoristaRepository = laboratoristaRepository;
    }

    public List<Reservas> ReservasporEstudiante(Integer idCodigo) {
        return estudiantesRepository.findActiveReservationsByStudent(idCodigo);
    }

    public List<Reservas> ReservasporFecha(Date fecha) {
        return laboratoristaRepository.reservasPorFecha(fecha);
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

    public boolean actualizarContrasena(String correo, String contrasena) {
        Laboratorista laboratorista = laboratoristaRepository.findCorreo(correo);
        if (laboratorista != null) {
            laboratorista.setContrasena(contrasena);
            laboratoristaRepository.save(laboratorista);
            return true;
        }
        return false;
    }
}
