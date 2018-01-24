package com.sysco.app.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class OrderNotFoundException extends RuntimeException {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String orderId;

    public OrderNotFoundException(String id) {
        super("Order not found");
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.NOT_FOUND;
        this.orderId = id;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }
}
