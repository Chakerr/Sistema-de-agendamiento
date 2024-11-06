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
import java.util.Optional;

@Service
public class ReservasService {

    private ReservasRepository reservasRepository;
    private EstudiantesRepository estudiantesRepository;
    private AreaEstudiosRepository areaEstudiosRepository;
    private HorariosInvRedesRepository horariosInvRepository;
    private InventariosRepository inventariosRepository;
    private HorariosRedesRepository horariosRedesRepository;
    private EquiposRepository equiposRepository;
    public boolean bool=false;
    public String equipo="";

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

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    @Transactional
    public Reservas crearReserva(Reservas reserva, List<Estudiantes> estudiantesL, Integer idAreaEstudio) throws ExceptionReserva {

        // Obtener el área de estudio primero
        AreaEstudios areaEstudio = areaEstudiosRepository.findById(idAreaEstudio)
                .orElseThrow(() -> new EntityNotFoundException("Área de estudio no encontrada"));
        reserva.setId_areaEstudio(areaEstudio);

        // Verificar disponibilidad
        if (!verificarDisponibilidad(reserva)) {
            return null;
        }

        // Procesar estudiantes y establecer relaciones
        System.out.println(estudiantesL);
        List<Estudiantes> estudiantes = procesarEstudiantes(estudiantesL);

        if (estudiantes != null) {
            System.out.println(estudiantes.size());
            // Establecer relaciones bidireccionales
            for (Estudiantes estudiante : estudiantes) {
                Optional<Estudiantes> existingEstudianteOpt = estudiantesRepository.findById(estudiante.getId_codigo());
                Estudiantes existingEstudiante;
                if (existingEstudianteOpt.isPresent()) {
                    existingEstudiante = existingEstudianteOpt.get();
                    // Actualiza la relación
                    manejarRelacionEstudianteReserva(existingEstudiante, reserva);
                    // No necesitas volver a guardarlo si ya está gestionado
                } else {
                    existingEstudiante = estudiante; // Nueva instancia
                    manejarRelacionEstudianteReserva(existingEstudiante, reserva);
                    //estudiantesRepository.save(existingEstudiante);
                }
                estudiantesRepository.save(existingEstudiante);
                System.out.println("RESERVAS ESTUDIANTE");
                System.out.println(existingEstudiante.getReservasList());
            }
            System.out.println("ESTUDIANTES EN LA RESERVA");

            System.out.println(reserva.getEstudiantesList());

        } else {
            reserva.setNumero_personas(1);
        }

        // Guardar la reserva
        reserva = reservasRepository.save(reserva);

        // Actualizar horarios y equipos
        actualizarHorariosYEquipos(reserva);

        // Actualizar relación con área de estudio
        if (areaEstudio.getReservasList() == null) {
            areaEstudio.setReservasList(new ArrayList<>());
        }
        areaEstudio.getReservasList().add(reserva);
        areaEstudiosRepository.save(areaEstudio);


        return reserva;

    }

    private void manejarRelacionEstudianteReserva(Estudiantes estudiante, Reservas reserva) {

        System.out.println("ENTRA");
        System.out.println(estudiante);
        System.out.println(reserva.getId());

        if (estudiante.getReservasList() == null) {
            estudiante.setReservasList(new ArrayList<>());
        }
        if (reserva.getEstudiantesList() == null) {
            reserva.setEstudiantesList(new ArrayList<>());
        }

        // Agregar la relación en ambos sentidos si no existe
        if (!estudiante.getReservasList().contains(reserva)) {
            estudiante.getReservasList().add(reserva);
        }
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
                    System.out.println(eq.getNombre());
                    setEquipo(eq.getNombre());
                    return false;
                }
                horaux = new Time(horaux.getTime() + 3600000);
            }
            horaux = reserva.getHora_inicio();
        }
        for (int i = horaI; i < horaF; i++) {
            HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux2, reserva.getFecha());
            if ((horRed.getCapacidadParcial() + reserva.getNumero_personas() > 24)){
                setBool(true);
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
                if (est.getId_codigo() != 0) {
                    estudiante = estudiantesRepository.findById(est.getId_codigo())
                            .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con id: " + est.getId_codigo()));
                    //   estudiante = estudiantesRepository.estId(est.getId_codigo());
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
            horaux =  reserva.getHora_inicio();
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
    public void borrarReserva(Integer id){
        Reservas reserva = reservasRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada con el ID: " + id));
        borrarHorarios(reserva);
        equiposRepository.deleteId(id);
        reservasRepository.deleteRela(id);
        reservasRepository.deleteById(id);
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
            horaux = reserva.getHora_inicio();
        }
        for (int i= horaI; i < horaF; i++){
            HorariosRedes horRed = horariosRedesRepository.findHorariosLab(horaux2, reserva.getFecha());
            horRed.setCapacidadParcial(horRed.getCapacidadParcial()-reserva.getNumero_personas());
            horariosRedesRepository.save(horRed);
            horaux2 = new Time(horaux2.getTime() + 3600000);
        }
    }
    public String obtenerElCorreo(int id){
        Estudiantes est = estudiantesRepository.findByIdCodigo(id);
        return est.getCorreo();
    }
}