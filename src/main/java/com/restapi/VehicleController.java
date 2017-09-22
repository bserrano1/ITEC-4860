package com.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.CharEncoding;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VehicleController {
    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        //Create a FileWriter to write to vehicle.txt and APPEND mode is true
        FileWriter output = new FileWriter("./vehicle.txt", true);

        //Serialize greeting object to JSON and write it to file
        mapper.writeValue(output, newVehicle);

        //Append a new line character to the file
        //The file above FileWriter ("output") is automatically closed by the mapper.
        org.apache.commons.io.FileUtils.writeStringToFile(new File("./vehicle.txt"), System.lineSeparator(), CharEncoding.UTF_8, true);

        return newVehicle;
    }


    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        String v = "";
        LineIterator it = FileUtils.lineIterator(new File("./vehicle.txt"), "UTF-8");
        while (it.hasNext()) {
            String line = it.next();
            Vehicle current = mapper.readValue(line, Vehicle.class);
            if (current.getId() == id) {
                v = line;
            }
        }
        return new ObjectMapper().readValue(v, Vehicle.class);
    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        ArrayList<String> listUpdate = new ArrayList<>();
        LineIterator it = FileUtils.lineIterator(new File("./vehicle.txt"), "UTF-8");

            while (it.hasNext()) {
                String d = it.next();
                Vehicle current = mapper.readValue(d, Vehicle.class);
                if (current.getId() == newVehicle.getId()) {
                    d = mapper.writeValueAsString(newVehicle);
                }
                listUpdate.add(d);
            }

        FileUtils.writeLines(new File("./vehicle.txt"), listUpdate);
        return newVehicle;
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> responseEntity = new ResponseEntity<>("ID not found.", headers, HttpStatus.NOT_FOUND);

        ArrayList<String> listUpdate = new ArrayList<>();
        LineIterator it = FileUtils.lineIterator(new File("./vehicle.txt"), "UTF-8");

            while (it.hasNext()) {
                String d = it.next();
                Vehicle current = mapper.readValue(d, Vehicle.class);
                if (current.getId() == id) {
                    //System.out.println("Delete ID::" + d);
                    responseEntity = new ResponseEntity<>("ID Deleted.", headers, HttpStatus.FOUND);
                }
                listUpdate.add(d);
            }
        FileUtils.writeLines(new File("./vehicle.txt"), listUpdate);
        return responseEntity;
    }
}
