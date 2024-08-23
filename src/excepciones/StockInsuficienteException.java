package excepciones;

public class StockInsuficienteException extends Throwable {
    public StockInsuficienteException(String message) {
        super(message);
    }
}
