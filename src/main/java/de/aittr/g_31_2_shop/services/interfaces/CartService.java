package de.aittr.g_31_2_shop.services.interfaces;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Product;

import java.util.List;

public interface CartService {

    double getActiveProductTotalPrice();

    double getActiveProductAveragePrice();
}
