package rusoft.review.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rusoft.review.dao.CarRepository;
import rusoft.review.dao.ClientRepository;
import rusoft.review.dto.ClientAddRequest;
import rusoft.review.dto.ClientRemoveRequest;
import rusoft.review.exception.CarNotFreeException;
import rusoft.review.exception.ClientAlreadyExistException;
import rusoft.review.exception.ClientNotExistException;
import rusoft.review.exception.ClientNotOwnerCarException;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ClientServiceTest {

    private final static String NAME = "name";
    private final static String MODEL = "model";

    @Autowired
    private ClientService clientService;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ClientRepository clientRepository;


    /*
    * Проверка на наличие клиента
    */
    @Test
    public void addClientExist() {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        clientService.add(clientAddRequest);

        boolean result = false;
        try {
            clientService.add(clientAddRequest);
        } catch (ClientAlreadyExistException e) {
            result = true;
        }
        assertTrue(result);
    }

    /*
    * Автомобиль свободен
    */
    @Test
    public void addCarNotFree() {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        clientService.add(clientAddRequest);

        clientAddRequest.setName("name1");
        boolean result = false;
        try {
            clientService.add(clientAddRequest);
        } catch (CarNotFreeException e) {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void addCarUpdateClient() {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        clientService.add(clientAddRequest);

        clientService.remove(clientAddRequest);

        clientAddRequest.setName("name1");
        clientService.add(clientAddRequest);

        assertTrue(carRepository.selectByModel(clientAddRequest.getModel()).get(0).getName()
                .equalsIgnoreCase(clientAddRequest.getName()));
        assertTrue(clientRepository.selectByName(clientAddRequest.getName()).size() == 1);
    }

    @Test
    public void removeClientNotExist() {
        ClientRemoveRequest clientRemoveRequest = new ClientRemoveRequest();
        clientRemoveRequest.setModel(MODEL);
        clientRemoveRequest.setName(NAME);

        boolean result = false;
        try {
            clientService.remove(clientRemoveRequest);
        } catch (ClientNotExistException e) {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void removeClientNotOwnerCar() {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        clientService.add(clientAddRequest);

        clientAddRequest.setModel("model2");

        boolean result = false;
        try {
            clientService.remove(clientAddRequest);
        } catch (ClientNotOwnerCarException e) {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void removeAllValid() {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        clientService.add(clientAddRequest);

        clientService.remove(clientAddRequest);

        assertTrue(carRepository.selectByModel(clientAddRequest.getModel()).get(0).getName() == null);
        assertTrue(clientRepository.selectByName(clientAddRequest.getName()).size() == 0);
    }
}
