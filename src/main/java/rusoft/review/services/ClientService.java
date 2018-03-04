package rusoft.review.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rusoft.review.dao.CarRepository;
import rusoft.review.dao.ClientRepository;
import rusoft.review.dto.CarEntity;
import rusoft.review.dto.ClientEntity;
import rusoft.review.dto.ClientAddRequest;
import rusoft.review.dto.ClientRemoveRequest;
import rusoft.review.exception.CarNotFreeException;
import rusoft.review.exception.ClientAlreadyExistException;
import rusoft.review.exception.ClientNotExistException;
import rusoft.review.exception.ClientNotOwnerCarException;

import java.util.List;


@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final CarRepository carRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, CarRepository carRepository) {
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
    }

    public void add(ClientAddRequest clientAddRequest) {
        List<ClientEntity> clientList = clientRepository.selectByName(clientAddRequest.getName());
        if (clientList.size() > 0) {
            throw new ClientAlreadyExistException();
        }
        List<CarEntity> carList = carRepository.selectByModel(clientAddRequest.getModel());
        if (carList.size() > 0) {
            String name = carList.get(0).getName();
            if (name != null && name.length() > 0) {
                throw new CarNotFreeException();
            } else {
                carRepository.clientUpdate(clientAddRequest);
            }
        } else {
            carRepository.insert(clientAddRequest);
        }
        clientRepository.insert(clientAddRequest);
    }


    public void remove(ClientRemoveRequest clientRemoveRequest) {
        List<ClientEntity> clientList = clientRepository.selectByName(clientRemoveRequest.getName());
        if (clientList.size() == 0) {
            throw new ClientNotExistException();
        } else {
            if (clientList.get(0).getModel().equalsIgnoreCase(clientRemoveRequest.getModel())) {
                carRepository.clientRemove(clientRemoveRequest.getModel());
                clientRepository.delete(clientRemoveRequest.getName());
            } else {
                throw new ClientNotOwnerCarException();
            }
        }
    }

}
