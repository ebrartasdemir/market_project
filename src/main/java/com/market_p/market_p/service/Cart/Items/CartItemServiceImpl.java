package com.market_p.market_p.service.Cart.Items;

import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.CartItem;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.CartItemMapper;
import com.market_p.market_p.repository.CartItemRepository;
import com.market_p.market_p.repository.CartRepository;
import com.market_p.market_p.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartItemMapper cartItemMapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartItem> getAllCartItems() {
        List<CartItem> cartItemList = cartItemRepository.findAll();
        return cartItemList;
    }

    @Override
    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItemList=cartItemRepository.getCartItemsByCartUserId(userId);
        return cartItemList;
    }

    @Override
    public List<CartItem> getCartItemByCartId(int cartId) {
        List<CartItem> cartItemList=cartItemRepository.getCartItemsByCartId(cartId);
        return cartItemList;
    }

    @Override
    public CartItem getCartItemById(int id) {
        Optional<CartItem> cartItemList=cartItemRepository.findById(id);
        CartItem cartItem=cartItemList.orElse(null);
        if(cartItem==null){throw new RuntimeException(String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id));}
        return cartItem;
    }

    @Override
    public void createCartItem(CartItemReqDto cartItemReqDto) {
        Cart cart=cartRepository.findById(cartItemReqDto.getCartId()).orElse(null);
        if(cart==null){throw new RuntimeException(String.format(Messages.Cart.RECORD_NOT_FOUND,cartItemReqDto.getCartId()));}
        Product product=productRepository.findById(cartItemReqDto.getProductId()).orElse(null);
        if(product==null){throw new RuntimeException(String.format(Messages.Product.RECORD_NOT_FOUND,product.getId()));}
        CartItem cartItem=cartItemMapper.cartItemDtoToCart(cartItemReqDto);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateCartItem(int id, CartItemReqDto newCartItem) {
        CartItem cartItem=cartItemRepository.findById(id).orElse(null);
        if (cartItem==null) {
            throw new RuntimeException(String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id));
        }
        if(newCartItem.getQuantity()!=0) {
            cartItem.setQuantity(newCartItem.getQuantity());
        }
        if(newCartItem.getCartId()!=0) {
            Cart cart=cartRepository.findById(newCartItem.getCartId()).orElse(null);
            if (cart==null) { throw new RuntimeException(String.format(Messages.Cart.RECORD_NOT_FOUND,newCartItem.getCartId()));}
            cartItem.setCart(cart);
        }
        if(newCartItem.getProductId()!=0) {
            Product product=productRepository.findById(newCartItem.getProductId()).orElse(null);
            if(product==null) { throw new RuntimeException(String.format(Messages.Product.RECORD_NOT_FOUND,newCartItem.getProductId())); }
            cartItem.setProduct(product);
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateQuantityCartItem(int id, int quantity) {
        CartItem cartItem=cartItemRepository.findById(id).orElse(null);
        if (cartItem==null) {throw new RuntimeException(String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id));}
        int newQuantity=cartItem.getQuantity()+quantity;
        if(newQuantity==0) {cartItemRepository.deleteById(id); return;}
        cartItem.setQuantity(cartItem.getQuantity()+quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(int id) {
        if(!cartItemRepository.existsById(id)) {
            throw new RuntimeException(String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id));
        }
        cartItemRepository.deleteById(id);
    }
}
