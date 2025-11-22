package com.ejemplo.tienda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioXTest {

    @Test
    void validarDescuentoAplicable_productoConDescuentoYPorcentajeEnRango_retornaTrue() {
        Producto p = new Producto("Laptop", 1000.0, 5, "SKU-010",
                "Tecnología", true, true);

        boolean resultado = ServicioX.validarDescuentoAplicable(p, 20.0);

        assertTrue(resultado);
    }

    @Test
    void validarDescuentoAplicable_productoSinDescuento_retornaFalse() {
        Producto p = new Producto("Laptop", 1000.0, 5, "SKU-011",
                "Tecnología", true, false);

        boolean resultado = ServicioX.validarDescuentoAplicable(p, 20.0);

        assertFalse(resultado);
    }

    @Test
    void validarDescuentoAplicable_porcentajeFueraDeRango_retornaFalse() {
        Producto p = new Producto("Laptop", 1000.0, 5, "SKU-012",
                "Tecnología", true, true);

        assertFalse(ServicioX.validarDescuentoAplicable(p, -5.0));
        assertFalse(ServicioX.validarDescuentoAplicable(p, 60.0));
    }
}
