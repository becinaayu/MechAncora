package Service;


import Entity.Product;
import Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void createProduct(Product product){
        productRepository.save(product);
    }

    public void updateProduct(Product product, Long id){
        productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Produto com Id"+ product.getId()+" não foi encontrado."));
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto com Id"+ id +" não foi encontrado."));
        productRepository.deleteById(id);
    }


}
