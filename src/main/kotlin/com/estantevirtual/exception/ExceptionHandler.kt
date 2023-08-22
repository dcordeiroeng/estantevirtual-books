package com.estantevirtual.exception

import com.estantevirtual.model.ErrorMessage
import com.estantevirtual.model.ErrorMessages
import org.hibernate.QueryException
import org.slf4j.Logger
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.function.Consumer
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ExceptionHandler(
    private val logger: Logger
) {
    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: ResourceNotFoundException): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAlreadyExists(ex: ResourceAlreadyExistsException): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgument(ex: IllegalArgumentException, req: WebRequest?): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(NumberFormatException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNumberFormatException(ex: NumberFormatException, req: WebRequest?): ErrorMessage {
        return ErrorMessage("Only numbers are allowed")
    }

    @ExceptionHandler(PropertyReferenceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePropertyReferenceException(ex: PropertyReferenceException, req: WebRequest?): ErrorMessage {
        return ErrorMessage("No property reference found: ${ex.message}")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(ex: MethodArgumentNotValidException): ErrorMessages {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.fieldErrors.forEach(Consumer { e: FieldError -> errors["message"] = e.defaultMessage!! })
        return ErrorMessages(errors)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolation(ex: ConstraintViolationException, req: WebRequest?): ErrorMessages {
        val errors: MutableMap<String, String> = HashMap()
        ex.constraintViolations.forEach { e -> errors["message"] = e.message!! }
        return ErrorMessages(errors)
    }

    @ExceptionHandler(IsbnException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIsbnException(ex: IsbnException, req: WebRequest?): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(OrderByException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleOrderByException(ex: OrderByException, req: WebRequest?): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(QueryException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleQueryException(ex: QueryException, req: WebRequest?): ErrorMessage {
        return ErrorMessage("Error in the query parameter: ${ex.queryString}")
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception, req: WebRequest?): ErrorMessage {
        logger.error(ex.message)
        return ErrorMessage("An unexpected error occurred")
    }
}
