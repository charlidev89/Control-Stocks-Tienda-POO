package domain.tienda;

import domain.productosComestibles.ProductoComestible;
import domain.venta.Venta;
import excepciones.*;
import domain.productos.Producto;

import java.util.*;

public class Tienda {
    private String nombre;
    private int numMaxProdStock;
    private double saldoCaja;
    private ArrayList<Producto> stockProductos;

    public Tienda(String nombre, int numMaxProdStock, double saldoCaja) {
        this.nombre = nombre;
        this.numMaxProdStock = numMaxProdStock;
        this.saldoCaja = saldoCaja;
        this.stockProductos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) throws StockSuperaLimiteException, SaldoInsuficienteException {
        double costoTotal = producto.getPrecioUnitario() * cantidad;

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad no puede ser Cero o menor a Cero");

        } else if (stockProductos.size() + cantidad > numMaxProdStock) {
            throw new StockSuperaLimiteException("No se pueden agregar nuevos productos a la tienda ya que se excedería el máximo de stock");

        } else if (saldoCaja < costoTotal) {
            throw new SaldoInsuficienteException("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja");

        } else {
            agregar(producto, cantidad);
        }

    }

    public void aplicarDescuento(Producto prod, double descuento) {
        Producto productoEncontrado = this.buscarProducto(prod.getId());
        productoEncontrado.aplicarDescuento(descuento);

    }

    private void agregar(Producto producto, int cantidad) {
        //si el producto ya existe en el stockProductos, se actualiza su stock sumando la nueva cantidad ,   Sino existe....  lo agregamos el producto con el stock inicial.
        if (producto == null || cantidad <= 0) {
            throw new IllegalArgumentException("Producto no válido o cantidad inválida");
        }

        Producto existente = buscarProducto(producto.getId());
        if (existente != null) {
            existente.agregarStock(cantidad);
        } else {
            producto.agregarStock(cantidad);
            this.stockProductos.add(producto);
        }
        actualizarSaldoCaja(producto.getPrecioUnitario() * cantidad);
    }

    private void actualizarSaldoCaja(double importeTotalProducto) {
        this.saldoCaja -= importeTotalProducto;
    }


    public void obtenerComestiblesConMenorDescuento(double porcentajeDescuento) {
        //Filtramos los productos comestibles  no importados con descuuento menor al ingresado por parametro y lo ordena por precioUnitario.
        List<String> productosFiltrados = stockProductos.stream()
                .filter(p -> p instanceof ProductoComestible)
                .map(p -> (ProductoComestible) p)
                .filter(p -> !p.getImportado())
                .filter(p -> p.getDescuento() < porcentajeDescuento)
                .sorted(Comparator.comparingDouble(Producto::getPrecioUnitario))
                .map(p -> p.getDescripcion().toUpperCase())
                .toList();

        // Mostrar la lista ordenada por pantalla
        System.out.println("Productos comestibles no importados con descuento menor a: " + porcentajeDescuento + "%");
        productosFiltrados.forEach(System.out::println);
    }


    private Producto buscarProducto(String id) {
        return stockProductos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }


    public void comprarProducto(Producto producto, int cantidad) {

        if (producto != null) {
            try {
                agregarProducto(producto, cantidad);
                System.out.printf("Compra exitosa: %d unidades de %s%n", cantidad, producto.getDescripcion() + "------>  precioUnitario:  $" + producto.getPrecioUnitario());
            } catch (StockSuperaLimiteException | SaldoInsuficienteException e) {
                System.out.println(e.getMessage());
            }


        }
    }

    // modularizamos  mostrar el stock de producto
    private void mostrarStockProducto(ArrayList<Producto> productos) {
        for (Producto producto : productos) {
            producto.mostrarDetalles();
            System.out.println("------------------------------------------------------");
        }
    }


    public void mostrarEstado() {
        System.out.println("------------------------------------------------------");
        System.out.printf("            Estado Actual de la Tienda: %s%n", this.nombre);
        System.out.println("------------------------------------------------------");
        System.out.printf("Saldo en caja: $%.2f%n", this.saldoCaja);
        System.out.println("------------------------------------------------------");
        System.out.println("Productos en stock:");
        this.mostrarStockProducto(stockProductos);
        System.out.println();

    }

    public void venderProducto(Map<String, Integer> ventaVariada) {
        Venta venta = new Venta();

        try {
            for (Map.Entry<String, Integer> entrada : ventaVariada.entrySet()) {
                String id = entrada.getKey();
                int cantidad = entrada.getValue();

                Producto producto = buscarProducto(id);
                if (producto == null) {
                    System.out.println("Producto con ID " + id + " no encontrado en el inventario.");
                    continue;
                }
                venta.agregarProducto(producto, cantidad);
            }
            venta.finalizarVenta();

            saldoCaja += venta.getTotalVenta(); // actualizamos el saldo de la caja
            System.out.println(venta.getDetalleVenta()); // mstramos el detalle de la venta

            String mensajes = venta.getMensajesInformativos();
            if (!mensajes.isEmpty()) {
                System.out.println(mensajes);
            }

        } catch (IllegalStateException e) {
            System.out.println("Error en la venta: " + e.getMessage());
        }
    }


}