package com.ejemplo.tienda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegracionTest {

    private Pedido pedido;
    private Producto prodConDescuento;
    private Producto prodSinDescuento;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();

        prodConDescuento = new Producto(
                "Laptop",
                1500.0,
                10,
                "SKU-100",
                "Tecnología",
                true,
                true
        );

        prodSinDescuento = new Producto(
                "Mouse",
                50.0,
                5,
                "SKU-101",
                "Accesorios",
                true,
                false
        );
    }

    @Test
    void flujoExitoso_agregaProductos_validaStockYDescuento() {
        boolean agregado1 = pedido.agregarProducto(prodConDescuento, 3);
        boolean agregado2 = pedido.agregarProducto(prodSinDescuento, 2);

        assertTrue(agregado1);
        assertTrue(agregado2);
        assertTrue(pedido.validarStock());

        boolean descuentoValido = ServicioX.validarDescuentoAplicable(
                prodConDescuento, 15.0);

        assertTrue(descuentoValido);
    }

    @Test
    void flujoErrorPorDuplicado_noSeEvaluaDescuentoDelDuplicado() {
        boolean agregado1 = pedido.agregarProducto(prodConDescuento, 3);
        assertTrue(agregado1);

        Producto duplicado = new Producto(
                "Laptop Pro",
                1600.0,
                2,
                "SKU-100",
                "Tecnología",
                true,
                true
        );
        boolean agregadoDuplicado = pedido.agregarProducto(duplicado, 2);

        assertFalse(agregadoDuplicado);
        assertEquals(1, pedido.getDetallesPedido().size());

        assertTrue(pedido.validarStock());

        boolean descuentoValido = ServicioX.validarDescuentoAplicable(
                prodConDescuento, 10.0);
        assertTrue(descuentoValido);
    }

    @Test
    void flujoConStockInvalido_validarStockFalseYServicioSeUsaSoloEnProductosValidos() {
        boolean agregadoValido = pedido.agregarProducto(prodSinDescuento, 2);
        assertTrue(agregadoValido);

        pedido.getDetallesPedido().get(0).setCantidad(0);

        boolean stockValido = pedido.validarStock();
        assertFalse(stockValido);

        boolean descuentoValido = ServicioX.validarDescuentoAplicable(
                prodSinDescuento, 10.0);

        assertFalse(descuentoValido);
    }
}
