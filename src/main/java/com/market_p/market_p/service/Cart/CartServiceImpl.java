package com.market_p.market_p.service.Cart;

import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAllCart() {
        List<Cart> CartList=cartRepository.findAll();
        return CartList;
    }

    @Override
    public Cart getCartById(int cartId) {
        Cart cart=cartRepository.findById(cartId).orElse(null);
        if (cart!=null)return cart;
        throw new NullPointerException("Cart is null");
    }

    @Override
    public Cart getCartByUserId(int userId) {
        Cart cart=cartRepository.findByUserId(userId);
        if(cart==null){
            throw new RuntimeException("");
        }
        return cart;
    }

    @Override
    public void createCart(Cart cart) {
        if(cart==null){
        cartRepository.save(cart);}
    }

    @Override
    public void updateCart(int id,Cart newCart) {
        Optional<Cart> optionalCart=cartRepository.findById(id);
        Cart cart=optionalCart.orElse(null);
        if(cart==null){ throw new RuntimeException("");}
        if (cart.getCartItems()!=newCart.getCartItems()) {
            cart.setCartItems(newCart.getCartItems());
        }
        cartRepository.save(cart);
    }
    @Override
    public void deleteCart(int cartId) {
        if(cartRepository.existsById(cartId)){
            cartRepository.deleteById(cartId);
        }
        else throw new RuntimeException("");
    }
}
