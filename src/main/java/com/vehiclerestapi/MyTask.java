package com.vehiclerestapi;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MyTask {
    private String url = "http://localhost:8080/";
    RestTemplate restTemplate = new RestTemplate();
    private int id = 1;

    @Scheduled(cron="*/1 * * * * *")
    public void addVehicle(){
        String urlFull = this.url + "addVehicle";
        String [] makeModel = {"Honda", "Ford", "Toyota", "Hyndai", "Dodge", "Nissan"};
        int ranModel = new Random().nextInt(makeModel.length);
        int year = new Random().nextInt(2016 - 1986) + 1986;
        double retailPrice = ThreadLocalRandom.current().nextDouble(15000, 45000);
        Vehicle v = new Vehicle(id, makeModel[ranModel], year, retailPrice);
        id++;
        Vehicle v2 = restTemplate.postForObject(urlFull, v, Vehicle.class);

        System.out.println("Add Vehicle: " + v2.toString());
    }

    @Scheduled(cron = "*/4 * * * * *")
    public void deleteVehicle() {
        String urlFull = url + "deleteVehicle/" + new Random().nextInt(100);
        restTemplate.delete(urlFull);
    }

    @Scheduled(cron="*/4 * * * * *")
    public void updateVehicle() {
        String urlFull = url + "updateVehicle";
        String [] makeModel = {"Honda", "Ford", "Toyota", "Hyndai", "Dodge", "Nissan"};
        int ranModel = new Random().nextInt(makeModel.length);
        int year = new Random().nextInt(2016 - 1986) + 1986;
        double retailPrice = ThreadLocalRandom.current().nextDouble(15000, 45000);

        Vehicle v = new Vehicle(new Random().nextInt(), makeModel[ranModel], year, retailPrice);
        restTemplate.put(urlFull, v);
    }
}
