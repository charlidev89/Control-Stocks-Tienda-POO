package domain.productos;

import enums.TipoAplicacion;

public class Limpieza extends Producto {
    private TipoAplicacion tipoAplicacion;
    private static final String CODIGO_ENVASADO = "AZ";
    private static final int LONGITUD_ID = 5;
    private static final int PORCENTAJE_MIN = 10;
    private static final int PORCENTAJE_MAX = 25;
    private static final int PORCENTAJE_DESCUENTO_MAX = 20;


    public Limpieza(String id, String descripcion, int stock, double precioUnidad, double porcentajeGanancia, TipoAplicacion tipoAplicacion) {
        super(id, descripcion, stock, precioUnidad, porcentajeGanancia);
        this.tipoAplicacion = tipoAplicacion;
        this.validarPorcentajeGanancia(porcentajeGanancia);

    }

    // valida el ID con AZ , método abstracto de la superclase
    @Override
    public void validarIdentificador(String id) {
        if (id.startsWith(CODIGO_ENVASADO) && id.length() == LONGITUD_ID &&
                id.substring(2).chars().allMatch(Character::isDigit)) {
            super.setId(id);
        } else {
            throw new IllegalArgumentException("Identificador inválido para producto Limpieza");
        }

    }

    @Override  // valida el porcentaje de ganancia
    public void validarPorcentajeGanancia(double porcentajeGanancia) {
        if (porcentajeGanancia > PORCENTAJE_MAX) {
            throw new IllegalArgumentException("El porcentaje de ganancia no puede ser mayor al 25%");
        }

        // Solo validamos el mínimo si el producto no es de tipo COCINA o MULTIUSO
        if (tipoAplicacion != TipoAplicacion.COCINA && tipoAplicacion != TipoAplicacion.MULTIUSO
                && porcentajeGanancia < PORCENTAJE_MIN) {
            throw new IllegalArgumentException("El porcentaje de ganancia debe ser al menos del 10% para este tipo de producto");
        }

        // Asignamos el valor solo si llegamos hasta aquí (es decir, si la validación ha pasado)
        this.editarGanancia(porcentajeGanancia);
    }

    @Override  // aplica descuento
    public void aplicarDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento > PORCENTAJE_DESCUENTO_MAX) {
            throw new IllegalArgumentException("El descuento para productos Limpieza no puede superar el 20%");
        } else {
            double descuento = (this.getPrecioUnitario() * porcentajeDescuento) / 100;

            System.out.println("El valor con descuento/precio final | Procucto: " + this.getDescripcion() + " ---> es: $" + this.calcularPrecioFinalProducto(descuento));
            super.setDescuento(porcentajeDescuento);

        }

    }


    @Override
    public void mostrarDetalles() {

        System.out.printf("• %s%n  - Stock: %d | Disponible: %s | Precio: $%.2f%n",
                getDescripcion(), getStock(), isDisponibleVenta() ? "Sí" : "Si", getPrecioUnitario());
        System.out.printf("  - Tipo de aplicación: %s%n", getTipoAplicacion());
    }

    public TipoAplicacion getTipoAplicacion() {
        return this.tipoAplicacion;
    }
}
