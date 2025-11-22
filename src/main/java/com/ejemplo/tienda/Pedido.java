package com.ejemplo.tienda;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final List<Producto> detallesPedido;

    public Pedido() {
        this.detallesPedido = new ArrayList<>();
    }

    public List<Producto> getDetallesPedido() {
        return detallesPedido;
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser null");
        }
        if (cantidad <= 0) {
            System.err.println("Error: La cantidad a agregar debe ser positiva.");
            return false;
        }

        if (!producto.isActivo()) {
            return false;
        }

        boolean productoYaExiste = detallesPedido.stream()
                .anyMatch(p -> p.getSku().equals(producto.getSku()));

        if (productoYaExiste) {
            return false;
        }

        Producto copia = new Producto(
                producto.getNombre(),
                producto.getPrecio(),
                cantidad,
                producto.getSku(),
                producto.getCategoria(),
                producto.isActivo(),
                producto.isDescuentoAplicable()
        );

        detallesPedido.add(copia);
        return true;
    }

    public boolean validarStock() {
        if (detallesPedido.isEmpty()) {
            return true;
        }
        for (Producto p : detallesPedido) {
            if (p.getCantidad() <= 0) {
                return false;
            }
        }
        return true;
    }
}
