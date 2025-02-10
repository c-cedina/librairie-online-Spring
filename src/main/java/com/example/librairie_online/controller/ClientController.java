package com.example.librairie_online.controller;

import java.util.List;
import java.util.Map;

import com.example.librairie_online.dto.AuthentificationDTO;
import com.example.librairie_online.entity.Validation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@NoArgsConstructor
@RestController
@RequestMapping(path = "Client")
public class ClientController {
    private ClientService clientService;
    private AuthenticationManager authenticationManager;



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
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authentificationDTO.email(),authentificationDTO.password())
       );
        return null;

    }

}
