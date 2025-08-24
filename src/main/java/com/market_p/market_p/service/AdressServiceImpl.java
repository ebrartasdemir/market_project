package com.market_p.market_p.service;

import com.market_p.market_p.dto.AdressDto;
import com.market_p.market_p.entity.Adress;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.repository.AdressRepository;
import com.market_p.market_p.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressServiceImpl implements AdressService {
    private String message;
    private static final Logger logger= LoggerFactory.getLogger(AdressService.class);
    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Adress> getAllAdresses(){
        logger.info("[AdressService] Service Method: getAllAdress - ( ) -  ------------");
        logger.info("[AdressService] Input: null");
        List<Adress> adressList=adressRepository.findAll();
        logger.info("[AdressService] Output: {}",adressList);
        logger.info("[AdressService] Listed Successfully");
        return adressList;
    }
    @Override
    public List<Adress> getAdressByUser(int userId){
        logger.info("[AdressService] Service Method: getAllAdressByUser - ( userId ) -  ------------");
        logger.info("[AdressService] Input: id => {}",userId);
        List<Adress> adressList=adressRepository.findAllByUserId(userId);
        logger.info("[AdressService] Output: {}",adressList);
        logger.info("[AdressService] Listed Successfully");
        return adressList;
    }
    public Adress getAdressById(int id) {
        logger.info("[AdressService] Service Method: getAdressById - ( id ) -  ------------");
        logger.info("[AdressService] Input: id => {}",id);
        Adress adress=adressRepository.findById(id).orElse(null);
        if(adress==null){
            message= String.format(Messages.Adress.RECORD_NOT_FOUND,id);
            logger.error("[AdressService] Error : {} ",message);
            throw new RuntimeException(message);
        }
        logger.info("[AdressService] Output: {}",adress);
        logger.info("[AdressService] Found Successfully");
        return adress;
    }

    @Override
    public Adress getAdressByTitle(String title) {
        logger.info("[AdressService]  Service Method: getAdressByTitle - ( title ) -  ------------");
        logger.info("[AdressService] Input: title => {}",title);
        Adress adress=adressRepository.findByTitle(title);
        if(adress==null){
            message= String.format(Messages.Adress.RECORD_NOT_FOUND_TITLE,title);
            logger.error("[AdressService] Error : {} ",message);
            throw new RuntimeException(message);
        }
        logger.info("[AdressService] Output: {}",adress);
        logger.info("[AdressService] Found Successfully");
        return adress;
    }

    @Override
    public void addAdress(int userId, AdressDto adressDto) {
        logger.info("[AdressService] Service Method: addAdress - ( adress ) -  ------------");
        logger.info("[AdressService] Input: adress => {}",adressDto);
        Adress adress=new Adress();
        adress.setEno(adressDto.getEno());
        adress.setIno(adressDto.getIno());
        adress.setTitle(adressDto.getTitle());
        adress.setCity(adressDto.getCity());
        adress.setDistrict(adressDto.getDistrict());
        adress.setStreet(adressDto.getStreet());
        adress.setAvunue(adressDto.getAvunue());
        adress.setUser(userRepository.findById(userId).get());
        adressRepository.save(adress);
        logger.info("[AdressService] Output: {}",adress);
        logger.info("[AdressService] Created Successfully");
    }

    @Override
    public void updateAdress(int id,AdressDto adressDto) {
        logger.info("[AdressService] Service Method: updateAdress - ( id , adress ) -  ------------");
        logger.info("[AdressService] Input: id => {} adress => {}",id,adressDto);
        Adress oldAdress=adressRepository.findById(id).orElse(null);
        if(oldAdress==null){
            message= String.format(Messages.Adress.RECORD_NOT_FOUND,id);
            logger.error("[AdressService] Error : {} ",message);
        }
        if(!adressDto.getTitle().isEmpty()){
            oldAdress.setTitle(adressDto.getTitle());
        }
        if(!adressDto.getDistrict().isEmpty()){
            oldAdress.setDistrict(adressDto.getDistrict());
        }
        if(!adressDto.getAvunue().isEmpty()){
            oldAdress.setAvunue(adressDto.getAvunue());
        }
        if(!adressDto.getCity().isEmpty()){
            oldAdress.setCity(adressDto.getCity());
        }
        if(!adressDto.getStreet().isEmpty()){
            oldAdress.setStreet(adressDto.getStreet());
        }
        if ((adressDto.getIno() !=0)){
            oldAdress.setIno(adressDto.getIno());
        }
        if (adressDto.getEno()!=0){
            oldAdress.setEno(adressDto.getEno());
        }
        adressRepository.save(oldAdress);
        logger.info("[AdressService] Output: null");
        logger.info("[AdressService] Updated Successfully");
    }

    @Override
    public void deleteAdress(int id) {
        logger.info("[AdressService] Service Method: deleteAdress - ( id ) -  ------------");
        logger.info("[AdressService] Input: id => {}",id);
        if(adressRepository.existsById(id)){
            adressRepository.deleteById(id);
            logger.info("[AdressService] Output: null");
            logger.info("[AdressService] Deleted Successfully");
        }
        message=String.format(Messages.Adress.RECORD_NOT_FOUND,id);
        logger.error("[AdressService] Error : {} ",message);
    }
}




