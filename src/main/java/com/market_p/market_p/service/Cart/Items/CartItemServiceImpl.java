package com.market_p.market_p.service.Cart.Items;

import com.market_p.market_p.dto.Cart.Item.CartItemReqDto;
import com.market_p.market_p.dto.Cart.Item.CartItemResDto;
import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.CartItem;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.CartItemMapper;
import com.market_p.market_p.repository.CartItemRepository;
import com.market_p.market_p.repository.CartRepository;
import com.market_p.market_p.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static final Logger logger= LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Override
    public List<CartItemResDto> getAllCartItems() {
        logger.info("[CartItemService] Service Method: getAllProducts - ( ) -  ------------");
        logger.info("[CartItemService] Input: null");
        List<CartItem> cartItemList = cartItemRepository.findAll();
        List<CartItemResDto> cartItemResDtoList = cartItemMapper.cartItemListToCartItemResDtoList(cartItemList);
        logger.info("[CartItemService] Output: DTO List => {}",cartItemResDtoList);
        logger.info("[CartItemService] Listed Successfully");
        return cartItemResDtoList;
    }

    @Override
    public List<CartItemResDto> getCartItemsByUserId(int userId) {
        logger.info("[CartItemService] Service Method: getCartItemsByUserId - ( userId ) -  ------------");
        logger.info("[CartItemService] Input: userId => {}",userId);
        List<CartItem> cartItemList=cartItemRepository.findCartItemsByCartUserId(userId);
        List<CartItemResDto> cartItemResDtoList=cartItemMapper.cartItemListToCartItemResDtoList(cartItemList);
        logger.info("[CartItemService] Output: DTO List => {}",cartItemResDtoList);
        logger.info("[CartItemService] Listed Successfully");
        return cartItemResDtoList;
    }

    @Override
    public List<CartItemResDto> getCartItemByCartId(int cartId) {
        logger.info("[CartItemService] Service Method: getCartItemsByCartId - ( cartId ) -  ------------");
        logger.info("[CartItemService] Input: cartId => {}",cartId);
        List<CartItem> cartItemList=cartItemRepository.findCartItemsByCartId(cartId);
        List<CartItemResDto> cartItemResDtoList=cartItemMapper.cartItemListToCartItemResDtoList(cartItemList);
        logger.info("[CartItemService] Output: DTO List => {}",cartItemResDtoList);
        logger.info("[CartItemService] Listed Successfully");
        return cartItemResDtoList;
    }

    @Override
    public CartItemResDto getCartItemById(int id) {
        logger.info("[CartItemService] Service Method: getCartItemById - ( id ) -  ------------");
        logger.info("[CartItemService] Input: id => {}",id);
        Optional<CartItem> optionalCartItem=cartItemRepository.findById(id);
        CartItem cartItem=optionalCartItem.orElse(null);
        if(cartItem==null){
            String message=String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id);
            logger.error("[CartItemService] Error: {}",message);
            logger.warn("[CartItemService] Get Failed");
            throw new RuntimeException(message);
        }
        CartItemResDto cartItemResDto=cartItemMapper.cartItemToCartItemResDto(cartItem);
        logger.info("[CartItemService] Output: DTO List => {}",cartItemResDto);
        logger.info("[CartItemService] Found Successfully");
        return cartItemResDto;
    }
    @Override
    @Transactional
    public void createCartItem(CartItemReqDto cartItemReqDto) {
        logger.info("[CartItemService] Service Method: createCartItem - ( cartItemReqDto ) -  ------------");
        logger.info("[CartItemService] Input: DTO => {}",cartItemReqDto);
        Cart cart=cartRepository.findById(cartItemReqDto.getCartId()).orElse(null);
        if(cart==null){
            String message=String.format(Messages.Cart.RECORD_NOT_FOUND,cartItemReqDto.getCartId());
            logger.error("[CartItemService] Error: {}",message);
            logger.warn("[CartItemService] Create Failed");
            throw new RuntimeException(message);
        }
        Product product=productRepository.findById(cartItemReqDto.getProductId()).orElse(null);
        if(product==null){
            String message=String.format(Messages.Product.RECORD_NOT_FOUND,cartItemReqDto.getProductId());
            logger.error("[CartItemService] Error: {}",message);
            logger.warn("[CartItemService] Create Failed");
            throw new RuntimeException();}
        CartItem cartItem=cartItemMapper.cartItemDtoToCart(cartItemReqDto);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartItemRepository.save(cartItem);
        logger.info("[CartItemService] Output: null");
        logger.info("[CartItemService] Created Successfully");
    }

    @Override
    @Transactional
    public void updateCartItem(int id, CartItemReqDto newCartItem) {
        logger.info("[CartItemService] Service Method: updateCartItem - ( id, cartItemReqDto ) -  ------------");
        logger.info("[CartItemService] Input: id => {}, cartItemReqDto => {}",id,newCartItem);
        CartItem cartItem=cartItemRepository.findById(id).orElse(null);
        if (cartItem==null) {
            String message=String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id);
            logger.error("[CartItemService] Error: {}",message);
            logger.warn("[CartItemService] Update Failed");
            throw new RuntimeException(message);
        }
        if(newCartItem.getQuantity()!=0) {
            cartItem.setQuantity(newCartItem.getQuantity());
        }
        if(newCartItem.getCartId()!=0) {
            Cart cart=cartRepository.findById(newCartItem.getCartId()).orElse(null);
            if (cart==null) {
                String message=String.format(Messages.Cart.Item.RECORD_NOT_FOUND,newCartItem.getCartId());
                logger.error("[CartItemService] Error: {}",message);
                logger.warn("[CartItemService] Update Failed");
                throw new RuntimeException(message);
            }
            cartItem.setCart(cart);
        }
        if(newCartItem.getProductId()!=0) {
            Product product=productRepository.findById(newCartItem.getProductId()).orElse(null);
            if(product==null) {
                String message=String.format(Messages.Cart.Item.RECORD_NOT_FOUND,newCartItem.getProductId());
                logger.error("[CartItemService] Error: {}",message);
                logger.warn("[CartItemService] Update Failed");
                throw new RuntimeException(message);}
            cartItem.setProduct(product);
        }
        cartItemRepository.save(cartItem);
        logger.info("[CartItemService] Output: null");
        logger.info("[CartItemService] Updated Successfully");
    }

    @Override
    @Transactional
    public void updateQuantityCartItem(int id, int quantity) {
        logger.info("[CartItemService] Service Method: updateQuantityCartItem - ( id, quantity ) -  ------------");
        logger.info("[CartItemService] Input: id => {}, quantity => {}",id,quantity);
        CartItem cartItem=cartItemRepository.findById(id).orElse(null);
        if (cartItem==null) {
            String message=String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id);
            logger.error("[CartItemService] Error: {}",message);
            logger.warn("[CartItemService] Update Failed");
            throw new RuntimeException(message);
        }
        int newQuantity=cartItem.getQuantity()+quantity;
        if(newQuantity==0) {
            cartItemRepository.deleteById(id);
            logger.info("[CartItemService] Record deleted. ");
            return;
        }
        cartItem.setQuantity(cartItem.getQuantity()+quantity);
        logger.info("[CartItemService] Output: null");
        cartItemRepository.save(cartItem);
        logger.info("[CartItemService] Output: null");
        logger.info("[CartItemService] Updated Successfully");
    }

    @Override
    @Transactional
    public void deleteCartItem(int id) {
        logger.info("[CartItemService] Service Method: deleteCartItem - ( id ) -  ------------");
        logger.info("[CartItemService] Input: id => {}",id);
        if(!cartItemRepository.existsById(id)) {
            String message=String.format(Messages.Cart.Item.RECORD_NOT_FOUND,id);
            logger.error("[CartItemService] Error: {}",message);
            logger.warn("[CartItemService] Delete Failed");
            throw new RuntimeException(message);
        }
        cartItemRepository.deleteById(id);
        logger.info("[CartItemService] Output: null.");
        logger.info("[CartItemService] Deleted Successfully.");
    }
}
