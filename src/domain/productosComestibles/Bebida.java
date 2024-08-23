package domain.productosComestibles;


public class Bebida extends ProductoComestible {
    private double graduacionAlcoholica;
    private static final int PORCENTAJE_DESCUENTO_MAX = 10;
    private static final String CODIGO_ENVASADO = "AC";
    private static final int LONGITUD_ID = 5;
    private static final int GRADUACION_ALCOHOLICA_MIN_A = 0;
    private static final int GRADUACION_ALCOHOLICA_MIN_B = 2;
    private static final double GRADUACION_ALCOHOLICA_MED_A = 2.1;
    private static final double GRADUACION_ALCOHOLICA_MED_B = 4.5;
    private static final double GRADUACION_ALCOHOLICA_MULTIPLICADOR_MIN = 1.25;
    private static final double GRADUACION_ALCOHOLICA_MULTIPLICADOR_MAX = 1.5;


    public Bebida(String id, String descripcion, int stock, double precioUnidad, double porcentajeGanancia, boolean importado, String fechaVencimiento, int calorias, double graduacionAlcoholica) {
        super(id, descripcion, stock, precioUnidad, porcentajeGanancia, importado, fechaVencimiento, calorias);
        this.graduacionAlcoholica = graduacionAlcoholica;
        validarCalorias(calorias);  // cada vez que se crea una instancia de un objeto bebida , se  calcula  la caloria segun el grado de alcohol indicado.

    }

    // metodo para validar id
    @Override
    public void validarIdentificador(String id) {
        if (id.startsWith(CODIGO_ENVASADO) && id.length() == LONGITUD_ID &&
                id.substring(2).chars().allMatch(Character::isDigit)) {
            super.setId(id);
        } else {
            throw new IllegalArgumentException("Identificador inválido para producto Bebida");
        }

    }

    // metodo para aplicar descuento
    @Override
    public void aplicarDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento > PORCENTAJE_DESCUENTO_MAX) {
            throw new IllegalArgumentException("El descuento para productos Bebida no puede superar el 10%");
        } else {
            double descuento = (this.getPrecioUnitario() * porcentajeDescuento) / 100;
            System.out.println("El valor con descuento/precio final | Procucto: " + this.getDescripcion() + " ---> es: $" + this.calcularPrecioFinalProducto(descuento));
            super.setDescuento(porcentajeDescuento);


        }

    }

    //  calcula las calorias segun graduacion  de alcohol
    private void validarCalorias(int calorias) {


        if (calorias < GRADUACION_ALCOHOLICA_MIN_A) {
            throw new IllegalArgumentException("Las calorías no pueden ser un valor negativo.");
        } else if (graduacionAlcoholica > GRADUACION_ALCOHOLICA_MIN_A && graduacionAlcoholica <= GRADUACION_ALCOHOLICA_MIN_B) {
            super.setCalorias(calorias);
        } else if (graduacionAlcoholica >= GRADUACION_ALCOHOLICA_MED_A && graduacionAlcoholica <= GRADUACION_ALCOHOLICA_MED_B) {
            super.setCalorias((int) (calorias * GRADUACION_ALCOHOLICA_MULTIPLICADOR_MIN));
        } else if (graduacionAlcoholica > GRADUACION_ALCOHOLICA_MED_B) {
            super.setCalorias((int) (calorias * GRADUACION_ALCOHOLICA_MULTIPLICADOR_MAX));
        }

    }

    @Override
    public void mostrarDetalles() {
        System.out.printf("• %s%n  - Stock: %d | Disponible: %s | Precio: $%.2f%n",
                getDescripcion(), getStock(), isDisponibleVenta() ? "Sí" : "No", getPrecioUnitario());

        System.out.printf("  - Graduación alcohólica: %.1f%% | Calorías: %d kcal | Vencimiento: %s%n",
                this.graduacionAlcoholica, getCalorias(), getFechaVencimiento());
    }


}

