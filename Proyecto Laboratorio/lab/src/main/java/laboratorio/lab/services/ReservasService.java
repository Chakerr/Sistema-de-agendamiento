package laboratorio.lab.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import laboratorio.lab.exceptions.ExceptionReserva;
import laboratorio.lab.models.*;
import laboratorio.lab.repositories.*;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservasService {
    private ReservasRepository reservasRepository;
    private EstudiantesRepository estudiantesRepository;
    private AreaEstudiosRepository areaEstudiosRepository;
    private HorariosInvRedesRepository horariosInvRepository;
    private InventariosRepository inventariosRepository;
    private HorariosRedesRepository horariosRedesRepository;
    private EquiposRepository equiposRepository;

    public ReservasService(ReservasRepository reservasRepository, EstudiantesRepository estudiantesRepository, AreaEstudiosRepository areaEstudiosRepository, HorariosInvRedesRepository horariosInvRepository, InventariosRepository inventariosRepository,HorariosRedesRepository horariosRedesRepository,EquiposRepository equiposRepository) {
        this.reservasRepository = reservasRepository;
        this.estudiantesRepository = estudiantesRepository;
        this.areaEstudiosRepository = areaEstudiosRepository;
        this.horariosInvRepository = horariosInvRepository;
        this.inventariosRepository = inventariosRepository;
        this.horariosRedesRepository = horariosRedesRepository;
        this.equiposRepository = equiposRepository;
    }

    public List<Reservas> verReservas(){
        return reservasRepository.findAll();
    }

    @Transactional
    public Reservas crearReserva(Reservas reserva, List<Estudiantes> estudiantesL, Integer idAreaEstudio) throws ExceptionReserva {

        AreaEstudios areaEstudio = areaEstudiosRepository.findById(idAreaEstudio)
                .orElseThrow(() -> new EntityNotFoundException("Área de estudio no encontrada"));
        reserva.setId_areaEstudio(areaEstudio);

        if (!verificarDisponibilidad(reserva)) {
            throw new ExceptionReserva("No se pudo crear la reserva: no hay disponibilidad");
        }

        List<Estudiantes> estudiantes = procesarEstudiantes(estudiantesL);
        if(estudiantes!=null) {
            reserva.setEstudiantesList(estudiantes);
            reserva.setNumero_personas(estudiantes.size());
        }else{
            reserva.setNumero_personas(1);
        }

        reserva = reservasRepository.save(reserva);

        actualizarHorariosYEquipos(reserva);

        areaEstudio.getReservasList().add(reserva);
        if(estudiantes!=null){
            for (Estudiantes estudiante : estudiantes) {
                estudiante.getReservasList().add(reserva);
                estudiantesRepository.save(estudiante);
            }
        }

        return reserva;
    }

    private boolean verificarDisponibilidad(Reservas reserva) {
        int horaI = (int) (reserva.getHora_inicio().getTime() / (1000 * 60 * 60));
        int horaF = (int) (reserva.getHora_fin().getTime() / (1000 * 60 * 60));
        Time horaux = reserva.getHora_inicio();
        Time horaux2 = reserva.getHora_inicio();

        for (Equipos eq : reserva.getEquiposList()) {
            for (int i = horaI; i < horaF; i++) {
                HorariosInvRedes obj = horariosInvRepository.findHorarios(eq.getNombre(), horaux, reserva.getFecha());
                if ((obj.getCantidadParcial() + eq.getCantidad() > inventariosRepository.findByEquipo(eq.getNombre()).getCantidad())) {
                    return false;
                }
                horaux = new Time(horaux.getTime() + 3600000);
            }
        }
        for (int i = horaI; i < horaF; i++) {
            HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux2, reserva.getFecha());
            if ((horRed.getCapacidadParcial() + reserva.getNumero_personas() > 24)){
                return false;
            }
            horaux2 = new Time(horaux2.getTime() + 3600000);
        }
        return true;
    }

    private List<Estudiantes> procesarEstudiantes(List<Estudiantes> estudiantesL) {
        if(estudiantesL!=null){
            List<Estudiantes> estudiantes = new ArrayList<>();
            for (Estudiantes est : estudiantesL) {
                Estudiantes estudiante;
                if (est.getCedula() != 0) {
                    estudiante = estudiantesRepository.findById(est.getCedula())
                            .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con id: " + est.getCedula()));
                } else {
                    estudiante = new Estudiantes();
                    estudiante.setNombre(est.getNombre());
                    estudiante.setVisitas(0);
                    estudiante = estudiantesRepository.save(estudiante);
                }
                estudiantes.add(estudiante);
            }
            return estudiantes;}
        else {
            return null;
        }
    }

    private void actualizarHorariosYEquipos(Reservas reserva) {
        int horaI = (int) (reserva.getHora_inicio().getTime() / (1000 * 60 * 60));
        int horaF = (int) (reserva.getHora_fin().getTime() / (1000 * 60 * 60));
        Time horaux = reserva.getHora_inicio();
        Time horaux2 = reserva.getHora_inicio();

        for (Equipos eq : reserva.getEquiposList()) {
            for (int i = horaI; i < horaF; i++) {
                HorariosInvRedes obj = horariosInvRepository.findHorarios(eq.getNombre(), horaux, reserva.getFecha());
                obj.setCantidadParcial(obj.getCantidadParcial() + eq.getCantidad());
                horariosInvRepository.save(obj);
                horaux = new Time(horaux.getTime() + 3600000);
            }
            eq.setReserva(reserva);
            equiposRepository.save(eq);
        }

        for (int i = horaI; i < horaF; i++) {
            HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux2, reserva.getFecha());
            horRed.setCapacidadParcial(horRed.getCapacidadParcial() + reserva.getNumero_personas());
            horariosRedesRepository.save(horRed);
            horaux2 = new Time(horaux2.getTime() + 3600000);
        }

    }

    @Transactional
    public Reservas borrarReserva(Integer id){
        // modificar capacidad parcial de horarios_inv_redes, horarios_redes y toca borrar los registros de equipos
        Reservas reserva = reservasRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada con el ID: " + id));
        borrarHorarios(reserva);
        equiposRepository.deleteId(id);
        reservasRepository.deleteById(id);
        return reserva;
    }

    private void borrarHorarios(Reservas reserva){

        List<Equipos> equiposList = reserva.getEquiposList();
        int horaI = (int) (reserva.getHora_inicio().getTime() / (1000 * 60 * 60));
        int horaF = (int) (reserva.getHora_fin().getTime() / (1000 * 60 * 60));
        Time horaux = reserva.getHora_inicio();
        Time horaux2 = reserva.getHora_inicio();

        for(Equipos e :equiposList){
            for (int i= horaI; i < horaF; i++) {
                HorariosInvRedes temp = horariosInvRepository.findHorarios(e.getNombre(),horaux,reserva.getFecha());
                temp.setCantidadParcial(temp.getCantidadParcial() - e.getCantidad());
                horariosInvRepository.save(temp);
                horaux = new Time(horaux.getTime() + 3600000);
            }
        }
        for (int i= horaI; i < horaF; i++){
            HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux2, reserva.getFecha());
            horRed.setCapacidadParcial(horRed.getCapacidadParcial()-reserva.getNumero_personas());
            horariosRedesRepository.save(horRed);
            horaux2 = new Time(horaux2.getTime() + 3600000);
        }
    }
}
