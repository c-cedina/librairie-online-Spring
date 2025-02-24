package com.example.librairie_online.controller;

import java.util.List;
import java.util.Map;


import com.example.librairie_online.dto.AuthentificationDTO;
import com.example.librairie_online.entity.Jwt;
import com.example.librairie_online.entity.Validation;

import com.example.librairie_online.security.JwtService;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.service.ClientService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@AllArgsConstructor
@RestController
@RequestMapping(path = "Client")
public class ClientController {

    private ClientService clientService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> create(@RequestBody Client client) {
        if (this.clientService.create(client) != null) {
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping(path = "/Validate")
    public ResponseEntity<Client> validate(@RequestBody Validation validation){
        int code = validation.getCode();
        Client client = this.clientService.validate(code);
        if(client != null){
            return new ResponseEntity<>(client,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<Client> read() {
        return this.clientService.read();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Client> readById(@PathVariable int id) {
        Client client = this.clientService.readById(id);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> update(@PathVariable int id, @RequestBody Client client) {
        Client existingClient = this.clientService.readById(id);
        if (existingClient != null) {
            client.setNAdherent(existingClient.getNAdherent());
            Client clientUpdate = this.clientService.update(id, client);
            return new ResponseEntity<>(clientUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Client> delete(@PathVariable int id) {
        Client client = this.clientService.readById(id);
        if (client != null) {
            this.clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/Connection")
    public Map<String,String> Connect(@RequestBody AuthentificationDTO authentificationDTO){
      Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authentificationDTO.email(),authentificationDTO.password())
       );
      if (authentication.isAuthenticated()){
          logger.info("User logged in successfully{} ", authentication.isAuthenticated());
          return jwtService.generate(authentificationDTO.email());
      }
        logger.info("User logged in successfully{} ", authentication.isAuthenticated());
        return null;

    }
    @PostMapping(path = "/Deconnection")
    public ResponseEntity<Jwt> deconnect(){
        this.jwtService.deconnect();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(path = "/Password/Forget/{token}")
    public ResponseEntity<Client> newPassword(@RequestBody Client client){

        Client clientDb = this.clientService.readById(client.getNAdherent());
        if (clientDb != null){
            if (clientDb.getPassword().equals(client.getPassword())){
                this.clientService.update(client.getNAdherent(),client);
                return new ResponseEntity<>(client,HttpStatus.OK);
            }else {
             new RuntimeException("Password Already exist");
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        new RuntimeException("Client not found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
