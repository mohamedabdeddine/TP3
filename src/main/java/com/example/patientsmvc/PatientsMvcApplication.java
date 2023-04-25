package com.example.patientsmvc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.patientsmvc.entities.Patient;
import com.example.patientsmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;


import java.util.Date;

@SpringBootApplication

public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    //Pour que ça s'execute au démarrage
   //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args ->{
            patientRepository.save(new Patient(null,"ahmed",new Date(),false,12));
            patientRepository.save(new Patient(null,"mohamed",new Date(),true,26));
            patientRepository.save(new Patient(null,"hamza",new Date(),false,59));
            patientRepository.save(new Patient(null,"soukaina",new Date(),true,45));
            patientRepository.findAll().forEach(p -> {
                    System.out.println(p.getNom());
                });
    };
    }
}
