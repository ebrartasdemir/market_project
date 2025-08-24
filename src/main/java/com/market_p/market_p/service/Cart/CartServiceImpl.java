package com.market_p.market_p.service.Cart;

import com.market_p.market_p.dto.Cart.CartAdminDto;
import com.market_p.market_p.dto.Cart.CartDto;
import com.market_p.market_p.dto.Cart.Item.CartItemResDto;
import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.CartItemMapper;
import com.market_p.market_p.mapper.CartMapper;
import com.market_p.market_p.repository.CartRepository;
import com.market_p.market_p.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CartAdminDto> getAllCart() {
        logger.info("[CartService] Service Method: getAllCart - ( ) -  ------------");
        logger.info("[CartService] Input: null");
        List<Cart> cartList=cartRepository.findAll();
        List<CartAdminDto>  cartDtoList=new ArrayList<>();
        cartList.forEach(cart->{
            List<CartItemResDto> cartItemResDtoList=cartItemMapper.cartItemListToCartItemResDtoList(cart.getCartItems());
            CartAdminDto cartDto= cartMapper.cartToCartAdminDto(cart,cartItemResDtoList);
            cartDtoList.add(cartDto);
        });
        logger.info("[CartService] Output: DTO List => {}",cartDtoList);
        logger.info("[CartService] Listed Successfully");
        return cartDtoList;
    }

    @Override
    public CartAdminDto getCartById(int id) {
        logger.info("[CartService] Service Method: getCardById - ( cartId ) -  ------------");
        logger.info("[CartService] Input: cartId => {}" ,id);
        Cart cart=cartRepository.findById(id).orElse(null);
        if (cart==null){
            String message=String.format(Messages.Cart.RECORD_NOT_FOUND, id);
            logger.error(message);
            logger.error("[CartService] Error: {}",message);
        throw new RuntimeException(message);
        }
        List<CartItemResDto> cartItemResDtoList=cartItemMapper.cartItemListToCartItemResDtoList(cart.getCartItems());
        CartAdminDto cartDto= cartMapper.cartToCartAdminDto(cart,cartItemResDtoList);
        logger.info("[CartService] Output: Cart => {}",cart);
        logger.info("[CartService] Listed Successfully");
        return cartDto;
    }

    @Override
    public CartDto getCartByUserId(int userId) {
        logger.info("[CartService] Service Method: getCardByUserId - ( userId ) -  ------------");
        logger.info("[CartService] Input: userId => {}" ,userId);
        Cart cart=cartRepository.findByUserId(userId);
        if(cart==null){
            String message=String.format(Messages.Cart.RECORD_NOT_FOUND_BY_USER_ID, userId);
            logger.error(message);
            logger.warn("[CartService] Error: {}",message);
            throw new RuntimeException(message);
        }
        List<CartItemResDto> cartItemResDtoList=cartItemMapper.cartItemListToCartItemResDtoList(cart.getCartItems());
        CartDto cartDto=cartMapper.cartToCartDto(cartItemResDtoList);
        logger.info("[CartService] Output: Cart => {}",cart);
        logger.info("[CartService] Listed Successfully");
        return cartDto;
    }
        @Override
        @Transactional
        public void createCart(int userId) {
            logger.info("[CartService] Service Method: createCart - ( userId ) -  ------------");
            logger.info("[CartService] Input: userId => {}" ,userId);
            Cart cart=cartRepository.findByUserId(userId);
        if(cart!=null){
                String message=String.format(Messages.Cart.RECORD_ALREADY_EXIST, userId);
                logger.error("[CartService] Error: {}",message);
                throw new RuntimeException(message);
            }
        User user=userRepository.findById(userId).orElse(null);
        if(user==null){
            String message=String.format(Messages.Cart.RECORD_NOT_FOUND_BY_USER_ID, userId);
            throw  new RuntimeException(message);
        }
            logger.info("[CartService] Output: null");
            logger.info("[CartService] Created Successfully");
            cartRepository.save(new Cart(user));
        }

    @Override
    @Transactional
    public void updateCart(int userId,Cart newCart) {
        logger.info("[CartService] Service Method: updateCart - ( userId ) -  ------------");
        logger.info("[CartService] Input: userId => {}" ,userId);
        Cart cart=cartRepository.findByUserId(userId);
        if(cart==null){
            String message=Messages.EMPTY_BODY;
            logger.error("[CartService] Error: {}",message);
            throw new RuntimeException(message);}
        if (cart.getCartItems()!=newCart.getCartItems()) {
            cart.setCartItems(newCart.getCartItems());
        }
        logger.info("[CartService] Output: null");
        logger.info("[CartService] Created Successfully");
        cartRepository.save(cart);
    }
    @Override
    @Transactional
    public void deleteCart(int userId) {
        logger.info("[CartService] Service Method: deleteCart - ( userId ) -  ------------");
        logger.info("[CartService] Input: userId => {}" ,userId);
        if(cartRepository.existsByUserId(userId)){
            cartRepository.deleteByUserId(userId);
        }
        else {
            String message=String.format(Messages.Cart.RECORD_NOT_FOUND_BY_USER_ID, userId);
            logger.error("[CartService] Error: {}",message);
            throw new RuntimeException(message);}
        logger.info("[CartService] Output: null");
        logger.info("[CartService] Deleted Successfully");
    }
}
