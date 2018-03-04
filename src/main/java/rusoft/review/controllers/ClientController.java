package rusoft.review.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rusoft.review.dto.ClientAddRequest;
import rusoft.review.dto.ClientRemoveRequest;
import rusoft.review.services.ClientService;


@Controller
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "Conflict. Details in message")
    })
    public ResponseEntity<Void> add(@RequestBody ClientAddRequest clientAddRequest) {
        clientService.add(clientAddRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "Conflict. Details in message")
    })
    public ResponseEntity<Void> remove(@RequestBody ClientRemoveRequest clientRemoveRequest) {
        clientService.remove(clientRemoveRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
