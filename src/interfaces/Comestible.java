package interfaces;

public interface Comestible {
    String getFechaVencimiento();
    int getCalorias();
    // podriamos agregar otros metodos si a futuro se agrega otro tipo de implementacion comestible fuera de la jerarquia de productos.
}
