package ru.abtank.geek.springmvc.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.abtank.geek.springmvc.model.Box;
import ru.abtank.geek.springmvc.repositories.BoxRepository;

import java.util.List;

@Component
public class BoxService {
    private BoxRepository boxRepository;

    @Autowired
    public void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public List<Box> getAllBoxes(){
        return boxRepository.getAllBox();
    }

    public void save(Box box){
        boxRepository.save(box);
    }
}
