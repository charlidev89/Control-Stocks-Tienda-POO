package test;

import domain.productosComestibles.Bebida;
import domain.productosComestibles.Envasado;
import domain.tienda.Tienda;
import domain.productos.Limpieza;
import domain.productos.Producto;
import enums.TipoAplicacion;

import java.util.HashMap;
import java.util.Map;

public class TestTienda {
    private static Tienda tienda;
    private static Envasado cafe;
    private static Envasado leche;
    private static Bebida vino;
    private static Bebida agua;
    private static Limpieza detergente;

    public static void main(String[] args) {

        try {
            // NOTA :  EL STOCK LO AGREGAMOS LUEGO ,EN CADA METODO.
            // CREAMOS LOS PRODUCTOS , PERO LA CANTIDAD DE CADA PRODUCTO LO AGREGAREMOS MAS ADELANTE.
            // IMPLEMENTAMOS BLOQUE TRY CATCH PARA SIMULAR EL INGRESO DE DATOS ( LO IDEAL SERIA APLICARLO PROVENIENTE DE UNA BD O INGRESO POR TECLADO)

            tienda = new Tienda("Charli 24/7", 1000, 50000);
            cafe = new Envasado("AB001", "Café", 0, 100, 15, "Lata", 50, "2022-12-31", true);
            leche = new Envasado("AB002", "Leche", 0, 50, 10, "Cartón", 120, "2026-12-15", true);
            vino = new Bebida("AC001", "Vino Tinto", 0, 200, 15, false, "2025-06-30", 85, 13.5);
            agua = new Bebida("AC002", "Agua Mineral", 0, 30, 15, true, "2020-12-31", 3, 0);
            detergente = new Limpieza("AZ001", "Detergente", 0, 80, 20, TipoAplicacion.ROPA);
        } catch (Exception e) {
            System.out.println("<<< Error durante la inicialización de los objetos: >>>" + e.getMessage());
            System.out.println();

        }

        //probamos cada funcion del sistema de la tienda.

        probarCompraProductos(tienda, cafe, leche, vino, agua, detergente);
        probarObternerMayorDescuento(tienda);
        probarVentaProductosVariados(tienda, cafe, vino, detergente);
        probarVentaProductoVencido(tienda, leche);
        probarAplicacionDescuentos(tienda, cafe, vino, detergente);
        probarCalculoCaloriasBebidas(vino, agua);
        probarVentaConStockInsuficiente(tienda, cafe);
        probarVentaProductoNoDisponible(tienda, agua);


    }

    private static void probarObternerMayorDescuento(Tienda tienda) {
        // muestra todos los productos  comestibles en stock  menor descuento de 10
        System.out.println("Prueba: Obtener mayorDescuento de Producto Comestible /No importado");
        tienda.obtenerComestiblesConMenorDescuento(10);
        tienda.mostrarEstado();

    }

    // ACA AGREGAMOS LA CANTIDAD CORRESPONDIENTE AL STOCK DE LA TIENDA Y LOS PRODUCTOS QUE LA TIENDA VA A ADQUIRIR EN SU STOCK
    public static void probarCompraProductos(Tienda tienda, Envasado cafe, Envasado leche, Bebida vino, Bebida agua, Limpieza detergente) {
        System.out.println("Prueba: Compra de productos");

        comprarProducto(tienda, cafe, 4);
        comprarProducto(tienda, leche, 10);
        comprarProducto(tienda, vino, 30);
        comprarProducto(tienda, agua, 22);
        comprarProducto(tienda, detergente, 11);
        // POR ULTIMO MOSTRAMOS EL ESTADO DE LA TIENDA
        tienda.mostrarEstado();
    }

    private static void comprarProducto(Tienda tienda, Producto producto, int cantidad) {
        tienda.comprarProducto(producto, cantidad);


    }

    public static void probarAplicacionDescuentos(Tienda tienda, Envasado cafe, Bebida vino, Limpieza detergente) {
        System.out.println("Prueba: Aplicar descuento ");
        tienda.aplicarDescuento(cafe, 14);
        tienda.aplicarDescuento(vino, 8);
        tienda.aplicarDescuento(detergente, 12);
        tienda.mostrarEstado();
    }

    public static void probarVentaProductosVariados(Tienda tienda, Envasado cafe, Bebida vino, Limpieza detergente) {
        System.out.println("Prueba: Venta de productos variados");
        Map<String, Integer> ventaVariada = new HashMap<>();
        ventaVariada.put(cafe.getId(), 20);
        ventaVariada.put(vino.getId(), 10);
        ventaVariada.put(detergente.getId(), 5);
        tienda.venderProducto(ventaVariada);
        tienda.mostrarEstado();
    }

    public static void probarVentaProductoVencido(Tienda tienda, Envasado leche) {
        System.out.println("Prueba: Intento de venta de producto vencido");
        leche.actualizarFechaVencimiento("2023-01-01"); // Simular vencimiento
        Map<String, Integer> ventaVencido = new HashMap<>();
        ventaVencido.put(leche.getId(), 10); // Leche vencida
        tienda.venderProducto(ventaVencido);
        tienda.mostrarEstado();
    }


    public static void probarCalculoCaloriasBebidas(Bebida vino, Bebida agua) {
        System.out.println("Prueba: Verificar cálculo de calorías en bebidas");
        // LA LOGICA DE CALCULAR LAS CALORIAS LO TIENE EL METODO DE SU CONSTRUCTOR
        System.out.println("Calorías del vino: " + vino.getCalorias());
        System.out.println("Calorías del agua: " + agua.getCalorias());
    }

    public static void probarVentaConStockInsuficiente(Tienda tienda, Envasado cafe) {
        System.out.println("Prueba: Venta con stock insuficiente");
        Map<String, Integer> ventaExcesiva = new HashMap<>();
        ventaExcesiva.put(cafe.getId(), 100); // Intentar vender más café del disponible
        tienda.venderProducto(ventaExcesiva);
        tienda.mostrarEstado();
    }

    public static void probarVentaProductoNoDisponible(Tienda tienda, Bebida agua) {
        System.out.println("Prueba: Venta de producto no disponible");
        agua.actualizarANoDisponile();
        Map<String, Integer> ventaNoDisponible = new HashMap<>();
        ventaNoDisponible.put(agua.getId(), 5); // Intentar vender agua no disponible
        tienda.venderProducto(ventaNoDisponible);
        tienda.mostrarEstado();
    }

}