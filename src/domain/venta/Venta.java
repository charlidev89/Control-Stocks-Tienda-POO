package domain.venta;

import domain.productos.Producto;
import domain.productosComestibles.ProductoComestible;

import java.util.HashMap;

public class Venta {
    private static final int MAX_PRODUCTOS = 3;
    private static final int MAX_UNIDADES_POR_PRODUCTO = 12;

    private HashMap<Producto, Integer> productosVendidos;
    private double totalVenta;
    private boolean hayStockInsuficiente;
    private StringBuilder detalleVenta;
    private StringBuilder mensajesInformativos;

    public Venta() {
        this.productosVendidos = new HashMap<>();
        this.totalVenta = 0;
        this.hayStockInsuficiente = false;
        this.detalleVenta = new StringBuilder();
        this.mensajesInformativos = new StringBuilder();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        boolean esValido = validarStockYDisponibilidad(producto, cantidad);
        if (esValido) {
            procesarVenta(producto, cantidad);
        }
    }
    private void procesarVenta(Producto producto, int cantidad) {
        int cantidadVendida = Math.min(cantidad, MAX_UNIDADES_POR_PRODUCTO);
        productosVendidos.put(producto, cantidadVendida);
        double precioVenta = producto.calcularPrecioFinalProducto(producto.getDescuento());
        double gananciaUnitaria = (producto.getPorcentajeGanancia() * 100) / precioVenta;
        double gananciaTotalStock = gananciaUnitaria * producto.getStock();

        totalVenta += precioVenta * cantidadVendida;


        detalleVenta.append(String.format("Venta de %s (ID: %s):%n", producto.getDescripcion(), producto.getId()));
        detalleVenta.append(String.format("  Cantidad vendida: %d%n", cantidadVendida));
        detalleVenta.append(String.format("  Precio unitario: $%.2f%n", precioVenta));
        detalleVenta.append(String.format("  Total parcial: $%.2f%n", cantidadVendida*precioVenta));
        detalleVenta.append(String.format("  Porcentaje de ganancia por unidad: %.2f%%%n", producto.getPorcentajeGanancia()));
        System.out.println();
        detalleVenta.append(String.format("  Ganancia real total según stock (%d unidades): $%.2f%n%n", producto.getStock(), gananciaTotalStock));

        actualizarStockProducto(producto, cantidadVendida);
    }

    private boolean validarStockYDisponibilidad(Producto producto, int cantidad) {
        boolean esValido = true;

        if (productosVendidos.size() >= MAX_PRODUCTOS) {
            throw new IllegalStateException("No se pueden vender más de 3 productos diferentes en una venta.");
        }

        if (!producto.isDisponibleVenta()) {
            agregarMensajeInformativo(producto, "no se encuentra disponible para la venta");
            esValido = false;
        }

        if (producto instanceof ProductoComestible) {
            ProductoComestible comestible = (ProductoComestible) producto;
            if (comestible.estaVencido()) {
                agregarMensajeInformativo(producto, "está vencido y no puede ser vendido");
                esValido = false;
            }
        }

        if (producto.getStock() < cantidad) {
            agregarMensajeInformativo(producto, "tiene stock insuficiente");
            hayStockInsuficiente = true;
            esValido = false;
        }

        return esValido;
    }

    private void agregarMensajeInformativo(Producto producto, String mensaje) {
        mensajesInformativos.append("El producto ").append(producto.getId())
                .append(" ").append(producto.getDescripcion())
                .append(" ").append(mensaje).append("\n");
    }



    private void actualizarStockProducto(Producto producto, int cantidadVendida) {
        try {
            producto.retirarStock(cantidadVendida);
        } catch (IllegalStateException e) {
            agregarMensajeInformativo(producto, "no tiene suficiente stock para completar la venta");
            hayStockInsuficiente = true;
        }
    }

    public void finalizarVenta() {
        if (!productosVendidos.isEmpty()) {
            detalleVenta.append(String.format("TOTAL VENTA: %.2f%n", totalVenta));
        }

        if (hayStockInsuficiente) {
            mensajesInformativos.append("Hay productos con stock disponible menor al solicitado\n");
        }
    }

    public String getDetalleVenta() {
        return detalleVenta.toString();
    }

    public String getMensajesInformativos() {
        return mensajesInformativos.toString();
    }

    public double getTotalVenta() {
        return totalVenta;
    }
}