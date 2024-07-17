package br.com.gabriel.webflux_app.services.exceptions;

public class ObjectNotFoundException  extends  RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }

}
