package org.example.controller

import org.example.model.Product
import org.example.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    suspend fun listarProdutos(): ResponseEntity<List<Product>> {
        val produtos = productService.listar()

        return ResponseEntity.ok(produtos)
    }
}
