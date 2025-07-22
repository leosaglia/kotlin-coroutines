package org.example.service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.example.model.Product
import org.springframework.stereotype.Service

@Service
class ProductService {
    private val produtos = mutableListOf<Product>(
        Product(nome = "Produto 1", preco = 10.0),
        Product(nome = "Produto 2", preco = 20.0),
        Product(nome = "Produto 3", preco = 30.0)
    )

    suspend fun listar(): List<Product> = coroutineScope {
        produtos.map { produto ->
            val nomeDeferred = async { buscarNome(produto) }
            val precoDeferred = async { buscarPreco(produto) }
            Product(
                nome = nomeDeferred.await(),
                preco = precoDeferred.await()
            )
        }
    }

    private suspend fun buscarNome(produto: Product): String {
        delay(200)
        return produto.nome
    }

    private suspend fun buscarPreco(produto: Product): Double {
        delay(200)
        return produto.preco
    }
}
