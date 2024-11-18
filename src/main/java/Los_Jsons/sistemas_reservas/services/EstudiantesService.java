package Los_Jsons.sistemas_reservas.services;

import Los_Jsons.sistemas_reservas.dtos.EstudiantesDto;
import Los_Jsons.sistemas_reservas.mappers.EstudiantesMapper;
import Los_Jsons.sistemas_reservas.models.Estudiantes;
import Los_Jsons.sistemas_reservas.models.Reservas;
import Los_Jsons.sistemas_reservas.repositories.CarrerasRepository;
import Los_Jsons.sistemas_reservas.repositories.EstudiantesRepository;
import Los_Jsons.sistemas_reservas.repositories.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigTreeConfigDataResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstudiantesService {
    private EstudiantesRepository estudiantesRepository;
    private ReservasRepository reservasRepository;
    private EstudiantesMapper estudiantesMapper;
    private CarrerasRepository carrerasRepository;

    @Autowired
    public EstudiantesService(EstudiantesRepository estudiantesRepository, ReservasRepository reservasRepository, EstudiantesMapper estudiantesMapper, CarrerasRepository carrerasRepository) {
        this.estudiantesRepository = estudiantesRepository;
        this.estudiantesMapper = estudiantesMapper;
        this.carrerasRepository = carrerasRepository;
        this.reservasRepository = reservasRepository;
    }

    public Estudiantes saveEstudiante(Estudiantes estudiante){
        estudiantesRepository.eliminarToken(estudiante.getCorreo());
        return estudiantesRepository.save(estudiante);
    }

    public boolean autenticarCarnet(String codCarnet) {
        Optional<Estudiantes> estudianteEnc = estudiantesRepository.findByCodigoCarnet(codCarnet);
        if (estudianteEnc.isPresent()) {
            Estudiantes estudiante = estudianteEnc.get();
            Date fechaActual = new Date(System.currentTimeMillis());
            Time horaActual = new Time(System.currentTimeMillis());
            LocalTime horaFin2 = horaActual.toLocalTime();
            Time horaFinMenos15Min = Time.valueOf(horaFin2.minusMinutes(15));
            Time horaFinMas15Min = Time.valueOf(horaFin2.plusMinutes(15));
            List<Reservas> reservasEnRango = reservasRepository.findReservasEntreHoras(fechaActual, horaFinMenos15Min, horaFinMas15Min);
            if (!reservasEnRango.isEmpty()) {
                Reservas reserva = new Reservas();
                for (Reservas e: reservasEnRango){
                    if(reservasRepository.perteneceReservaAEstudiante(e.getId(), estudiante.getId_codigo())){
                        reserva = e;
                    }
                }
                LocalTime horaFin = reserva.getHora_fin().toLocalTime();
                LocalTime limiteHoraFin = horaFin.plusMinutes(15);
                LocalTime horaActualLocal = horaActual.toLocalTime();
                if (horaActualLocal.isAfter(horaFin) && horaActualLocal.isBefore(limiteHoraFin)) {
                    boolean esReservaDelEstudiante = reservasRepository.perteneceReservaAEstudiante(reserva.getId(), estudiante.getId_codigo());
                    if (esReservaDelEstudiante) {
                        estudiantesRepository.incrementarVisitas(estudiante.getId_codigo());
                        reservasRepository.actualizarEstadoReserva(true, reserva.getId());
                        return true;
                    }else{
                        return false;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean autenticarVisitaCedula(int cedula) {
        Optional<Estudiantes> estudianteEnc = estudiantesRepository.findByCedula(cedula);
        if (estudianteEnc.isPresent()) {
            Estudiantes estudiante = estudianteEnc.get();
            Date fechaActual = new Date(System.currentTimeMillis());
            Time horaActual = new Time(System.currentTimeMillis());
            LocalTime horaFin2 = horaActual.toLocalTime();
            Time horaFinMenos15Min = Time.valueOf(horaFin2.minusMinutes(15));
            Time horaFinMas15Min = Time.valueOf(horaFin2.plusMinutes(15));
            List<Reservas> reservasEnRango = reservasRepository.findReservasEntreHoras(fechaActual, horaFinMenos15Min, horaFinMas15Min);
            if (!reservasEnRango.isEmpty()) {
                Reservas reserva = new Reservas();
                for (Reservas e: reservasEnRango){
                    if(reservasRepository.perteneceReservaAEstudiante(e.getId(), estudiante.getId_codigo())){
                        reserva = e;
                    }
                }
                LocalTime horaFin = reserva.getHora_fin().toLocalTime();
                LocalTime limiteHoraFin = horaFin.plusMinutes(15);
                LocalTime horaActualLocal = horaActual.toLocalTime();
                if (horaActualLocal.isAfter(horaFin) && horaActualLocal.isBefore(limiteHoraFin)) {
                    boolean esReservaDelEstudiante = reservasRepository.perteneceReservaAEstudiante(reserva.getId(), estudiante.getId_codigo());
                    if (esReservaDelEstudiante) {
                        estudiantesRepository.incrementarVisitas(estudiante.getId_codigo());
                        reservasRepository.actualizarEstadoReserva(true, reserva.getId());
                        return true;
                    }else{
                        return false;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public List<EstudiantesDto> saveEstudiantes(@RequestBody List<Estudiantes> estudiantesList) {
        return estudiantesRepository.saveAll(estudiantesList).stream().map(estudiantesMapper::EstudiantesToDto).collect(Collectors.toList());
    }

    public boolean encontrarCorreo(String correo){
        Estudiantes estudiante = estudiantesRepository.findByCorreo(correo);
        return estudiante != null;
    }

    public boolean encontrarCedula(Integer cedula){
        Estudiantes estudiante = estudiantesRepository.findByIdCedula(cedula);
        return estudiante != null;
    }

    public boolean encontrarCodigo(Integer codigo){
        Estudiantes estudiante = estudiantesRepository.findByIdCodigo(codigo);
        return estudiante != null;
    }

    public boolean actualizarContrasena(String correo, String contrasena) {
        Estudiantes estudiante= estudiantesRepository.findByCorreo(correo);
        if (estudiante != null) {
            estudiante.setContrasena(contrasena);
            estudiantesRepository.save(estudiante);
            return true;
        }
        return false;
    }
}
