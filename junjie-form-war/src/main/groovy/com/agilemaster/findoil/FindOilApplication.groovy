package com.agilemaster.findoil


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.orm.jpa.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.agilemaster.findoil.repository")
@EntityScan(basePackages = "com.agilemaster.findoil.domain")
class FindOilApplication {

    static void main(String[] args) {
        SpringApplication.run FindOilApplication, args
    }
}
