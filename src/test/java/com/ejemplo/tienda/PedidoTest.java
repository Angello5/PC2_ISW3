package com.ejemplo.tienda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private Producto productoBase;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        productoBase = new Producto(
                "Laptop",
                1200.50,
                10,
                "SKU-001",
                "Tecnología",
                true,
                true
        );
    }

    @Test
    void agregarProducto_cantidadNoValida_retornaFalseYNoAgrega() {
        boolean resultado = pedido.agregarProducto(productoBase, 0);

        assertFalse(resultado);
        assertTrue(pedido.getDetallesPedido().isEmpty());
    }

    @Test
    void agregarProducto_productoDuplicadoPorSku_segundoIntentoRetornaFalse() {
        Producto otroConMismoSku = new Producto(
                "Laptop Gamer",
                2000.0,
                5,
                "SKU-001",
                "Tecnología",
                true,
                false
        );

        boolean primeraVez = pedido.agregarProducto(productoBase, 3);
        boolean segundaVez = pedido.agregarProducto(otroConMismoSku, 2);

        assertTrue(primeraVez);
        assertFalse(segundaVez);
        assertEquals(1, pedido.getDetallesPedido().size());
        assertEquals("SKU-001", pedido.getDetallesPedido().get(0).getSku());
    }

    @Test
    void agregarProducto_productoNuevoCantidadValida_retornalTrueYSeAgregaALista() {
        boolean resultado = pedido.agregarProducto(productoBase, 5);

        assertTrue(resultado);
        assertEquals(1, pedido.getDetallesPedido().size());
        Producto agregado = pedido.getDetallesPedido().get(0);
        assertEquals("SKU-001", agregado.getSku());
        assertEquals(5, agregado.getCantidad());
    }

    @Test
    void agregarProducto_respetaTodosLosAtributosAlAgregar() {
        boolean resultado = pedido.agregarProducto(productoBase, 7);

        assertTrue(resultado);
        Producto agregado = pedido.getDetallesPedido().get(0);

        assertEquals(productoBase.getNombre(), agregado.getNombre());
        assertEquals(productoBase.getPrecio(), agregado.getPrecio());
        assertEquals(productoBase.getSku(), agregado.getSku());
        assertEquals(productoBase.getCategoria(), agregado.getCategoria());
        assertEquals(productoBase.isActivo(), agregado.isActivo());
        assertEquals(productoBase.isDescuentoAplicable(), agregado.isDescuentoAplicable());
        assertEquals(7, agregado.getCantidad());
    }

    @Test
    void agregarProducto_productoInactivo_noSePermiteAgregarYRetornaFalse() {
        Producto inactivo = new Producto(
                "Mouse",
                50.0,
                3,
                "SKU-002",
                "Accesorios",
                false,
                true
        );

        boolean resultado = pedido.agregarProducto(inactivo, 3);

        assertFalse(resultado);
        assertTrue(pedido.getDetallesPedido().isEmpty());
    }

    @Test
    void validarStock_listaVacia_retornaTrue() {
        boolean resultado = pedido.validarStock();
        assertTrue(resultado);
    }

    @Test
    void validarStock_todosConCantidadPositiva_retornaTrue() {
        pedido.agregarProducto(productoBase, 5);
        pedido.agregarProducto(
                new Producto("Teclado", 100.0, 2, "SKU-003", "Accesorios", true, false),
                2
        );

        boolean resultado = pedido.validarStock();
        assertTrue(resultado);
    }

    @Test
    void validarStock_unProductoConCantidadCero_retornaFalse() {
        boolean agregado = pedido.agregarProducto(productoBase, 5);
        assertTrue(agregado);

        pedido.getDetallesPedido().get(0).setCantidad(0);

        boolean resultado = pedido.validarStock();
        assertFalse(resultado);
    }

    @Test
    void constructorProducto_cantidadNegativa_lanzaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Producto("Tablet", 900.0, -1, "SKU-005", "Tecnología", true, false)
        );
    }

    @Test
    void validarStock_valoresLimiteAltos_retornaTrue() {
        pedido.agregarProducto(productoBase, 1);
        pedido.agregarProducto(
                new Producto("Servidor", 10000.0, 999, "SKU-006", "Infraestructura", true, false),
                999
        );

        pedido.getDetallesPedido().get(0).setCantidad(1);
        pedido.getDetallesPedido().get(1).setCantidad(999);

        boolean resultado = pedido.validarStock();
        assertTrue(resultado);
    }
}
