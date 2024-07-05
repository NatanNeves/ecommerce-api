package com.compass.ecommerce.services;

import com.compass.ecommerce.domain.Sale;
import com.compass.ecommerce.repositories.SaleRepository;
import com.compass.ecommerce.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;


    public List<Sale> findAll(){
        return saleRepository.findAll();
    }

    public Sale findById(Long id) {
        Optional<Sale> product = saleRepository.findById(id);
        return product.orElseThrow(() -> new NotFoundException("Venda não encontrada"));
    }
}
