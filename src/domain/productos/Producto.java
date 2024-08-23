package domain.productos;

public abstract class Producto {
    private String id;
    private String descripcion;
    private int stock;
    private double precioUnidad;
    private double porcentajeGanancia;
    private boolean disponibleVenta;
    private double descuento;


    public Producto(String id, String descripcion, int stock, double precioUnidad, double porcentajeGanancia) {
        validarIdentificador(id);
        this.descripcion = descripcion;
        this.stock = stock;
        this.precioUnidad = precioUnidad;
        validarPorcentajeGanancia(porcentajeGanancia);
        this.disponibleVenta = true;

    }

    public abstract void validarIdentificador(String id);   // valida el ID en cada clase

    public abstract void validarPorcentajeGanancia(double porcentajeGanancia);  // validamos el porcentaje de ganancia

    public abstract void aplicarDescuento(double porcentajeDescuento);

    // cada instancia o clase muestra su detalle de productos.
    public abstract void mostrarDetalles();

    public double calcularPrecioFinalProducto(double descuento) {
        setDescuento(descuento);
        this.setPrecioUnitario(this.getPrecioUnitario() - descuento);

        return this.getPrecioUnitario();
    }

    protected void editarGanancia(double porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public void agregarStock(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("No se puede añadir una cantidad negativa al stock");
        }
        this.stock += cantidad;
    }

    public void retirarStock(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("No se puede retirar una cantidad negativa del stock");
        }
        if (cantidad > this.stock) {
            throw new IllegalStateException("No hay suficiente stock para retirar");
        }
        this.stock -= cantidad;
        if (this.stock == 0) {
            this.actualizarANoDisponile();
        }
    }

    public boolean isDisponibleVenta() {
        return disponibleVenta;
    }

    public void actualizarANoDisponile() {
        this.disponibleVenta = false;
    }


    // GETTER &  SETTERS
    protected void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public int getStock() {
        return this.stock;
    }


    public double getPrecioUnitario() {
        return precioUnidad;
    }

    protected void setPrecioUnitario(double precioUnitario) {
        this.precioUnidad = precioUnitario;
    }


    protected void setDisponibleVenta(boolean disponibleVenta) {
        this.disponibleVenta = disponibleVenta;
    }


    public double getPorcentajeGanancia() {
        return this.porcentajeGanancia;
    }




}
