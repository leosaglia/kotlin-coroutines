package org.example.config

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    @Around("within(org.example.controller..*)")
    fun logAround(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            val result = joinPoint.proceed()
            println("Tipo de resposta do controller: ${result?.javaClass?.name}")
            result
        } catch (ex: Throwable) {
            println("Erro no controller: ${ex.message}")
            throw ex
        }
    }
}
