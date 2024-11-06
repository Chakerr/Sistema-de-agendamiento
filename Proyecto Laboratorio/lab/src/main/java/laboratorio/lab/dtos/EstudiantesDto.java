package laboratorio.lab.dtos;

import laboratorio.lab.models.Carreras;

public record EstudiantesDto(String nombre, int cedula, Carreras carrera) {
}
