package ru.abtank.geek.springmvc.repositories;

import org.springframework.stereotype.Component;
import ru.abtank.geek.springmvc.model.Box;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BoxRepository {

    private List<Box> boxes;

    @PostConstruct
    public void init() {
        boxes = new ArrayList<>();
        boxes.add(new Box(1L, "white", 5));
        boxes.add(new Box(2L, "Red", 15));
        boxes.add(new Box(3L, "Yellow", 25));
        boxes.add(new Box(4L, "Yellow", 35));
    }

    public BoxRepository() {
    }

    public List<Box> getAllBox() {
        return Collections.unmodifiableList(boxes);
    }

    public void save(Box box) {
        boxes.add(box);
    }

}
