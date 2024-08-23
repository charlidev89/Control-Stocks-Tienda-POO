package domain.productosComestibles;



public class Envasado extends ProductoComestible {
    private String tipoEnvase;
    private static final String CODIGO_ENVASADO = "AB";
    private static final int LONGITUD_ID = 5;
    private static final int PORCENTAJE_DESCUENTO_MAX = 15;

    public Envasado(String id, String descripcion, int stock, double precioUnidad, double porcentajeGanancia, String tipoEnvase, int calorias, String fechaVencimiento, boolean importado) {
        super(id, descripcion, stock, precioUnidad, porcentajeGanancia, importado, fechaVencimiento, calorias);
        this.tipoEnvase = tipoEnvase;


    }
    // valida el ID  metodo de la superclase

    @Override
    public void validarIdentificador(String id) {
        if (id.startsWith(CODIGO_ENVASADO) && id.length() == LONGITUD_ID &&
                id.substring(2).chars().allMatch(Character::isDigit)) {
            super.setId(id);
        } else {
            throw new IllegalArgumentException("Identificador inválido para producto envasado");
        }
    }


    // Aplica el descuento segun Envasado.
    @Override
    public void aplicarDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento > PORCENTAJE_DESCUENTO_MAX) {
            throw new IllegalArgumentException("El descuento para productos envasados no puede superar el 15%");
        } else {
            double descuento = (this.getPrecioUnitario() * porcentajeDescuento) / 100;
            System.out.println("El valor con descuento/precio final | Procucto: " + this.getDescripcion() + " ---> es: $" + this.calcularPrecioFinalProducto(descuento));
            super.setDescuento(porcentajeDescuento);

        }

    }

    @Override
    public void mostrarDetalles() {

        System.out.printf("• %s%n  - Stock: %d | Disponible: %s | Precio: $%.2f%n",
                getDescripcion(), getStock(), isDisponibleVenta() ? "Sí" : "No", getPrecioUnitario());
        System.out.printf("  - Tipo de envase: %s | Importado: %s | Vencimiento: %s%n",
                getTipoEnvase(), this.getImportado() ? "Sí" : "No", getFechaVencimiento());
    }


    public String getTipoEnvase() {
        return tipoEnvase;
    }
}