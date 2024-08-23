package domain.productosComestibles;

import interfaces.Comestible;
import domain.productos.Producto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public abstract class ProductoComestible extends Producto implements Comestible {
    private boolean importado;
    private String fechaVencimiento;
    private int calorias;
    private static final int PORCENTAJE_MAX = 20;
    private static final int PORCENTAJE_MIN = 0;
    private static final double IMPUESTO = 12.0;


    public ProductoComestible(String id, String descripcion, int stock, double precioUnidad, double porcentajeGanancia, boolean importado, String fechaVencimiento, int calorias) {
        super(id, descripcion, stock, precioUnidad, porcentajeGanancia);
        this.importado = importado;
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calorias;
        aplicarImpuesto();
        valorarDisponibilidad(fechaVencimiento);


    }

    public void valorarDisponibilidad(String fechaVencimiento) {
        LocalDate fecha = LocalDate.parse(fechaVencimiento);
        LocalDate hoy = LocalDate.now();
        this.setDisponibleVenta(fecha.isAfter(hoy) || fecha.isEqual(hoy));
    }

    protected void aplicarImpuesto() {
        if (this.importado) {
            this.setPrecioUnitario(this.getPrecioUnitario() + (this.getPrecioUnitario() * IMPUESTO / 100));
        }
    }

    public void actualizarFechaVencimiento(String nuevaFecha) {
        try {
            // Validamos el formato de la fecha
            LocalDate fecha = LocalDate.parse(nuevaFecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.fechaVencimiento = nuevaFecha;
            System.out.println("La fecha de vencimiento ha sido actualizada correctamente a: " + nuevaFecha);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido. Por favor, ingrese una fecha válida.");
        }
    }


    // valida el porcentaje de ganancia  de los comestibles

    @Override
    public void validarPorcentajeGanancia(double porcentajeGanancia) {
        if (porcentajeGanancia > PORCENTAJE_MIN && porcentajeGanancia <= PORCENTAJE_MAX) {
            this.editarGanancia(porcentajeGanancia);
        } else {
            throw new IllegalArgumentException("El porcentaje de ganancia debe ser mayor que 0 y menor o igual a 20 para productos comestibles");
        }

    }

    public boolean estaVencido() {
        LocalDate fechaVencimiento = LocalDate.parse(this.getFechaVencimiento());
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    //getter y setters

    public boolean getImportado() {
        return importado;
    }


    public int getCalorias() {
        return calorias;
    }

    protected void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    @Override
    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }

}
