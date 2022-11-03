package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.OpinionsDto;
import com.example.odziezowy.Model.Opinions;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.OpinionsRepository;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpinionsService {

    private final OpinionsRepository opinionsRepository;
    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public OpinionsService(OpinionsRepository opinionsRepository, UsersRepository usersRepository, ProductsRepository productsRepository) {
        this.opinionsRepository = opinionsRepository;
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
    }

    public Page<Opinions> getAllOpinionsService(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return opinionsRepository.findAll(paging);
    }
    public ResponseEntity<Opinions> createOpinionsService(Long userId, Long productId, OpinionsDto opinionsDto) {
        Users users = usersRepository.findById(userId).get();
        Products products = productsRepository.findById(productId).get();
        Opinions opinions = new Opinions();
        opinions.setUsersOpinion(users);
        opinions.setProductsId(products);
        opinions.setRating(opinionsDto.getRating());
        opinions.setComment(opinionsDto.getComment());
        opinionsRepository.save(opinions);
        return new ResponseEntity<>(opinions, HttpStatus.CREATED);
    }

    public Page<Opinions> getOpinionByProductIdService(Long id, Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Products products = productsRepository.findById(id).get();
        return opinionsRepository.findAllByProductsId(paging, products);
    }

}
