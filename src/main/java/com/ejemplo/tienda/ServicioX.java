package com.ejemplo.tienda;

public class ServicioX {

    private ServicioX() {
        // Clase utilitaria, no instanciable
    }

    public static boolean validarDescuentoAplicable(Producto p, double porcentaje) {
        if (p == null) {
            throw new IllegalArgumentException("El producto no puede ser null");
        }
        if (!p.isDescuentoAplicable()) {
            return false;
        }
        return porcentaje >= 0 && porcentaje <= 50;
    }
}
