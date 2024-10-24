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
        // Obtener el área de estudio primero
        AreaEstudios areaEstudio = areaEstudiosRepository.findById(idAreaEstudio)
                .orElseThrow(() -> new EntityNotFoundException("Área de estudio no encontrada"));
        reserva.setId_areaEstudio(areaEstudio);

        // Verificar disponibilidad
        if (!verificarDisponibilidad(reserva)) {
            throw new ExceptionReserva("No se pudo crear la reserva: no hay disponibilidad");
        }

        // Procesar estudiantes
        List<Estudiantes> estudiantes = procesarEstudiantes(estudiantesL);
        reserva.setEstudiantesList(estudiantes);
        reserva.setNumero_personas(estudiantes.size());

        // Guardar la reserva primero
        reserva = reservasRepository.save(reserva);

        // Actualizar horarios y equipos
        actualizarHorariosYEquipos(reserva);

        // Actualizar relaciones
        areaEstudio.getReservasList().add(reserva);
        for (Estudiantes estudiante : estudiantes) {
            estudiante.getReservasList().add(reserva);
            estudiantesRepository.save(estudiante);
        }

        return reserva;
    }

    private boolean verificarDisponibilidad(Reservas reserva) {
        int horaI = (int) (reserva.getHora_inicio().getTime() / (1000 * 60 * 60));
        int horaF = (int) (reserva.getHora_fin().getTime() / (1000 * 60 * 60));
        Time horaux = reserva.getHora_inicio();

        for (Equipos eq : reserva.getEquiposList()) {
            for (int i = horaI; i < horaF; i++) {
                HorariosInvRedes obj = horariosInvRepository.findHorarios(eq.getNombre(), horaux, reserva.getFecha());
                HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux, reserva.getFecha());
                if ((obj.getCantidadParcial() + eq.getCantidad() > inventariosRepository.findByEquipo(eq.getNombre()).getCantidad())
                        || (horRed.getCapacidadParcial() + reserva.getNumero_personas() > 24)) {
                    return false;
                }
                horaux = new Time(horaux.getTime() + 3600000);
            }
        }
        return true;
    }

    private List<Estudiantes> procesarEstudiantes(List<Estudiantes> estudiantesL) {
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
        return estudiantes;
    }

    private void actualizarHorariosYEquipos(Reservas reserva) {
        int horaI = (int) (reserva.getHora_inicio().getTime() / (1000 * 60 * 60));
        int horaF = (int) (reserva.getHora_fin().getTime() / (1000 * 60 * 60));
        Time horaux = reserva.getHora_inicio();

        for (Equipos eq : reserva.getEquiposList()) {
            for (int i = horaI; i < horaF; i++) {
                HorariosInvRedes obj = horariosInvRepository.findHorarios(eq.getNombre(), horaux, reserva.getFecha());
                HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux, reserva.getFecha());
                horRed.setCapacidadParcial(horRed.getCapacidadParcial() + reserva.getNumero_personas());
                obj.setCantidadParcial(obj.getCantidadParcial() + eq.getCantidad());
                horariosInvRepository.save(obj);
                horariosRedesRepository.save(horRed);
                horaux = new Time(horaux.getTime() + 3600000);
            }
            eq.setReserva(reserva);
            equiposRepository.save(eq);
        }
    }

//    @Transactional
//    public Reservas crearReserva(Reservas reserva,List<Estudiantes> estudiantesL, Integer idAreaEstudio) throws ExceptionReserva{
//        List<Equipos> equipos = reserva.getEquiposList();
//        System.out.println(reserva.getFecha());
//        int horaI = (int) (reserva.getHora_inicio().getTime() / (1000 * 60 * 60));
//        int horaF = (int) (reserva.getHora_fin().getTime() / (1000 * 60 * 60));
//        Time horaux = reserva.getHora_inicio();
//        boolean todoDisp = true;
//        for (Equipos eq : equipos){
//            for (int i = horaI; i < horaF; i++) {
//                HorariosInvRedes obj = horariosInvRepository.findHorarios(eq.getNombre(),horaux,reserva.getFecha());
//                HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux,reserva.getFecha());
//                if (((obj.getCantidadParcial()+eq.getCantidad()) > inventariosRepository.findByEquipo(eq.getNombre()).getCantidad()) && ((horRed.getCapacidadParcial()+reserva.getNumero_personas())> 24) ) {
//                    todoDisp=false;
//                    break;
//                }
//                long horauxM = horaux.getTime();
//                horaux = new Time(horauxM + 3600000);
//            }
//            if (!todoDisp) {
//                break;
//            }
//        }
//        if (todoDisp) {
//            horaux = reserva.getHora_inicio();
//            for (Equipos eq : equipos) {
//                for (int i = horaI; i < horaF; i++) {
//                    HorariosInvRedes obj = horariosInvRepository.findHorarios(eq.getNombre(),horaux,reserva.getFecha());
//                    HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux,reserva.getFecha());
//                    horRed.setCapacidadParcial(horRed.getCapacidadParcial()+reserva.getNumero_personas());
//                    obj.setCantidadParcial(obj.getCantidadParcial()+eq.getCantidad());
//                    horariosInvRepository.save(obj);
//                    horariosRedesRepository.save(horRed);
//                    long horauxM = horaux.getTime();
//                    horaux = new Time(horauxM + 3600000);
//                }
//                eq.setReserva(reserva);
//                equiposRepository.save(eq);
//            }
//        }
//        if(todoDisp) {
//            AreaEstudios areaEstudio = areaEstudiosRepository.findById(idAreaEstudio)
//                    .orElseThrow(() -> new EntityNotFoundException("Área de estudio no encontrada"));
//
//            areaEstudio.getReservasList().add(reserva);
//            List<Estudiantes> estudiantes = new ArrayList<>();
//            for (Estudiantes ests : estudiantesL) {
//                Estudiantes estudiante;
//                if (ests.getCedula() != 0) {
//                    estudiante = estudiantesRepository.findById(ests.getCedula())
//                            .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con id: " + ests.getCedula()));
//                } else {
//                    estudiante = new Estudiantes();
//                    estudiante.setNombre(ests.getNombre());
//                    estudiante.setVisitas(0);
//                    estudiante = estudiantesRepository.save(estudiante);
//                }
//
//                estudiantes.add(estudiante);
//                estudiante.getReservasList().add(reserva);
//            }
//
//            reserva.setEstudiantesList(estudiantes);
//            reserva.setId_areaEstudio(areaEstudio);
//            reserva.setNumero_personas(estudiantes.size());
//
//            return reservasRepository.save(reserva);
//        }else{
//           throw new ExceptionReserva("no se pudo crear la reserva");
//        }
//    }


}
