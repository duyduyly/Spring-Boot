package com.alan.entity_listener;

import com.alan.entity_listener.custom.Model2;
import com.alan.entity_listener.custom.Model2Repository;
import com.alan.entity_listener.defualt.Model;
import com.alan.entity_listener.defualt.ModelRepository;
import com.alan.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auditing-entity")
@RequiredArgsConstructor
public class TestController {

    private final ModelRepository modelRepository;
    private final Model2Repository model2Repository;
    private final JsonUtils jsonUtils;

    @GetMapping("/default")
    public String defaultEntity(@RequestParam Long id) {
        Model model = new Model();
        if (id != 0) {
            model = modelRepository.findById(id).get();
            model.setName("Updated");
        }

        Model save = modelRepository.save(model);
        return save.toString();
    }


    @GetMapping("/custom")
    public String custom(@RequestParam Long id,@RequestParam String email) {
        Model2 model = new Model2();


        //case Update
        if (id != 0) {
            Optional<Model2> byId = model2Repository.findById(id);
            model = byId.get();
        }
        model.setEmail(email);
        Model2 save = model2Repository.save(model);
        return save.toString();
    }
}
