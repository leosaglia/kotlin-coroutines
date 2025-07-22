package org.example.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.awaitAll
import org.example.model.Product
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import kotlinx.coroutines.reactor.awaitSingle

@Service
class ProductService(
    private val webClientBuilder: WebClient.Builder
) {
    private val produtos = mutableListOf<Product>(
        Product(id = 1, nome = "Produto 1", preco = 10.0),
        Product(id = 2, nome = "Produto 2", preco = 20.0),
        Product(id = 3, nome = "Produto 3", preco = 30.0),
        Product(id = 4, nome = "Produto 4", preco = 10.0),
        Product(id = 5, nome = "Produto 5", preco = 20.0),
        Product(id = 6, nome = "Produto 6", preco = 30.0),
        Product(id = 7, nome = "Produto 7", preco = 30.0)
    )

    suspend fun listar(): List<Product> = withContext(Dispatchers.IO) {
        produtos.map { produto ->
            async {
                val preco = buscarPreco(produto.id)
                produto.copy(preco = preco)
            }
        }.awaitAll()
    }

    private suspend fun buscarPreco(id: Long): Double {
        val webClient = webClientBuilder.baseUrl("http://localhost:3000").build()
        return webClient.get()
            .uri("/product/${id}/preco")
            .retrieve()
            .bodyToMono(Double::class.java)
            .awaitSingle()
    }
}
