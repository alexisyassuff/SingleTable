package com.example.joinTable;

import com.example.joinTable.entidades.Alumno;
import com.example.joinTable.entidades.Profesor;
import com.example.joinTable.enuneraciones.Especialidades;
import com.example.joinTable.enuneraciones.Titulos;
import com.example.joinTable.repositorios.AlumnoRepository;
import com.example.joinTable.repositorios.PersonaRepository;
import com.example.joinTable.repositorios.ProfesorRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class JoinTableApplication {

	private static final Logger logger = LoggerFactory.getLogger(JoinTableApplication.class);

	@Autowired
	private PersonaRepository personaRepository;
	@Autowired
	private ProfesorRepository profesorRepository;
	@Autowired
	private AlumnoRepository alumnoRepository;

	public static void main(String[] args) {
		SpringApplication.run(JoinTableApplication.class, args);
		System.out.println("funcionando");
	}

	@Bean
	@Transactional
	CommandLineRunner init() {
		return args -> {
			// Creo dos objetos profesor
			Profesor pro1 = Profesor.builder()
					.nombre("Alberto")
					.apellido("Cortez")
					.fechaIngreso(LocalDate.of(2022, 1, 1))
					.cantHijos(2)
					.titulo(Titulos.MASTER)
					.sueldo(new BigDecimal("1234.56"))
					.build();


			Profesor pro2 = Profesor.builder()
					.nombre("María")
					.apellido("Fernández")
					.fechaIngreso(LocalDate.of(2021, 5, 15))
					.cantHijos(1)
					.titulo(Titulos.INGENIERO)
					.sueldo(new BigDecimal("1500.00"))
					.build();

			// Creo tres objetos alumno
			Alumno alu1 = Alumno.builder()
					.nombre("Juan")
					.apellido("Pérez")
					.legajo(1001)
					.especialidad(Especialidades.BACHILLER)
					.build();

			Alumno alu2 = Alumno.builder()
					.nombre("Lucía")
					.apellido("Gómez")
					.legajo(1002)
					.especialidad(Especialidades.PERITO_MERCANTIL)
					.build();

			Alumno alu3 = Alumno.builder()
					.nombre("Pedro")
					.apellido("Martínez")
					.legajo(1003)
					.especialidad(Especialidades.BACHILLER)
					.build();

			// Guardamos los profesores
			profesorRepository.save(pro1);
			profesorRepository.save(pro2);

			// Guardamos los alumnos
			alumnoRepository.save(alu1);
			alumnoRepository.save(alu2);
			alumnoRepository.save(alu3);

			// Asociamos alumnos a los profesores
			pro1.getAlumnos().add(alu1);
			pro1.getAlumnos().add(alu2);

			pro2.getAlumnos().add(alu3);

			// Actualizamos los profesores con sus alumnos
			profesorRepository.save(pro1);
			profesorRepository.save(pro2);

			// Log para verificar
			logger.info("Profesores y alumnos guardados exitosamente.");
		};
	}
}
