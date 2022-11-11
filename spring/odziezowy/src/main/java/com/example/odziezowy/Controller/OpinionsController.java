package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.OpinionsDto;
import com.example.odziezowy.DTOS.UsersDto;
import com.example.odziezowy.Model.Opinions;
import com.example.odziezowy.Service.OpinionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/opinions")
public class OpinionsController {

    private final OpinionsService opinionsService;

    @Autowired
    public OpinionsController(OpinionsService opinionsService) {
        this.opinionsService = opinionsService;
    }

    @GetMapping
    public Page<Opinions> getAllOpinions(@RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return opinionsService.getAllOpinionsService(pageNo, pageSize);
    }

    @GetMapping("/product/{id}")
    public Page<Opinions> getOpinionByProductId(@PathVariable(value = "id") Long id,
                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return opinionsService.getOpinionByProductIdService(id, pageNo, pageSize);
    }

    @PostMapping
    public ResponseEntity<Opinions> createOpinions(@RequestParam Long userId,
                                                   @RequestParam Long productId, @RequestBody OpinionsDto opinionsDto) {
        return opinionsService.createOpinionsService(userId, productId, opinionsDto);
    }

    @GetMapping("/rating")
    public List<Object[]> getAllHighRated() {
        return opinionsService.getAllHighRatedService();
    }

}
