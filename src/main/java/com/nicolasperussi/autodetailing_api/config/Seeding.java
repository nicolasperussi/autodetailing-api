package com.nicolasperussi.autodetailing_api.config;

import com.nicolasperussi.autodetailing_api.domain.*;
import com.nicolasperussi.autodetailing_api.domain.enums.UserRole;
import com.nicolasperussi.autodetailing_api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Configuration
@Profile("dev")
public class Seeding implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobServiceRepository jobItemRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {


        // CREATING USERS
        User manager = new User("Nicolas Admin", "admin@estetica.com", passwordEncoder.encode("admin123"),
                                // Senha criptografada
                                UserRole.MANAGER
        );

        User employee = new User(
                "Marcos Detailer",
                "marcos@estetica.com",
                passwordEncoder.encode("123456"),
                UserRole.DETAILER
        );

        userRepository.saveAll(List.of(manager, employee));


        // CREATING CUSTOMERS AND VEHICLES
        Customer client1 = new Customer("Murilo Felix", "11966081511", "rillofelix@gmail.com", "12345678901");

        Vehicle car1 = new Vehicle("XYZ9W87", "Peugeot", "206", 2011, "Chumbo");

        client1.addVehicle(car1);

        Customer client2 = new Customer(
                "Nicolas Perussi",
                "11984157697",
                "nicolasperussi12@hotmail.com",
                "12335678999"
        );

        Vehicle car2 = new Vehicle("EIH9I50", "Ford", "Fiesta", 2011, "Preto");

        client2.addVehicle(car2);

        customerRepository.saveAll(List.of(client1, client2));

        Service s1 = new Service("Lavagem Detalhada", "Limpeza profunda com pincéis", new BigDecimal("150.00"), 120);
        Service s2 = new Service("Polimento Técnico", "Correção de pintura", new BigDecimal("450.00"), 360);
        Service s3 = new Service("Higienização Interna", "Limpeza de bancos e teto", new BigDecimal("200.00"), 180);
        serviceRepository.saveAll(List.of(s1, s2, s3));

        Job j1 = new Job(client1, car1, employee, Instant.parse("2026-04-10T10:00:00Z"), s1.getCurrentPrice());

        BigDecimal totalNicolas = s2.getCurrentPrice().add(s3.getCurrentPrice());
        Job j2 = new Job(client1, car2, manager, Instant.parse("2026-04-12T09:00:00Z"), totalNicolas);

        jobRepository.saveAll(List.of(j1, j2));

        JobService ji1 = new JobService(j1, s1, s1.getCurrentPrice());
        JobService ji2 = new JobService(j2, s2, s2.getCurrentPrice());
        JobService ji3 = new JobService(j2, s3, s3.getCurrentPrice());

        jobItemRepository.saveAll(List.of(ji1, ji2, ji3));

        System.out.println("Banco de dados populado com sucesso!");
    }
}