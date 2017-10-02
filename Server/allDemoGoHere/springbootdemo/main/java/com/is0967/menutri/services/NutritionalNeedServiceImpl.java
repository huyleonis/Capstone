package com.is0967.menutri.services;

import com.is0967.menutri.dtos.NutritionalNeedDTO;
import com.is0967.menutri.entities.NutritionalNeed;
import com.is0967.menutri.entities.NutritionalNeedDetail;
import com.is0967.menutri.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phuctran93 on 2/21/2017.
 */
@Service
public class NutritionalNeedServiceImpl extends AbstractServiceImpl implements NutritionalNeedService {

   @Override
   @Transactional
   public NutritionalNeedDTO create(NutritionalNeedDTO nutritionalNeedDTO) {
      boolean isCreated = false;
      try {
         insertNeed(nutritionalNeedDTO);
         isCreated = true;
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isCreated ? nutritionalNeedDTO : null;
      }
   }

   @Override
   public List<NutritionalNeedDTO> readByUserId(Long userId) {
      List<NutritionalNeedDTO> dtos = null;
      try {
         List<NutritionalNeed> a = nutritionalNeedRepo.findByUserIdAndDeletedFalse(userId);
         dtos = new ArrayList<>();
         for (NutritionalNeed nutritionalNeed : a) {
            dtos.add(transferDataToDTO(nutritionalNeed));
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return dtos;
      }
   }

   private void insertNeed(NutritionalNeedDTO nutritionalNeedDTO) {
      NutritionalNeed nutritionalNeed = NutritionalNeedDTO.convertToEntity(nutritionalNeedDTO);
      User user = userRepo.findOne(nutritionalNeedDTO.getUserId());
      nutritionalNeed.setUser(user);
      nutritionalNeed.setDeleted(false);
      NutritionalNeed entity = nutritionalNeedRepo.save(nutritionalNeed);
      long nutritionalNeedId = entity.getId();

      HashMap<Long, Double> nutrientAmountMap = nutritionalNeedDTO.getNutrientAmountMap();
      for (Map.Entry<Long, Double> nutrientAmount : nutrientAmountMap.entrySet()) {
         Long nutrientId = nutrientAmount.getKey();
         Double amount = nutrientAmount.getValue();
         nutritionalNeedDetailRepo.save(
                 new NutritionalNeedDetail(nutrientId, nutritionalNeedId, amount));
      }
   }


   @Override
   public NutritionalNeedDTO read(Long id) {
      NutritionalNeedDTO dto = null;
      try {
         NutritionalNeed need = nutritionalNeedRepo.findByIdAndDeletedFalse(id);
         dto = transferDataToDTO(need);
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return dto;
      }
   }

   @Override
   public boolean delete(Long id) {
      boolean isDeleted = false;
      try {
         NutritionalNeed need = nutritionalNeedRepo.findByIdAndDeletedFalse(id);
         if (need != null) {
            need.setDeleted(true);
            nutritionalNeedRepo.save(need);
            isDeleted = true;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isDeleted;
      }
   }

   @Override
   public NutritionalNeedDTO update(NutritionalNeedDTO nutritionalNeedDTO) {
      boolean isUpdated = false;
      try {
         deleteNeedDetails(nutritionalNeedDTO);
         insertNeed(nutritionalNeedDTO);
         isUpdated = true;
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isUpdated ? nutritionalNeedDTO : null;
      }

   }

   private void deleteNeedDetails(NutritionalNeedDTO nutritionalNeedDTO) {
      HashMap<Long, Double> nutrientAmountMap = nutritionalNeedDTO.getNutrientAmountMap();
      for (Map.Entry<Long, Double> nutrientAmount : nutrientAmountMap.entrySet()) {
         NutritionalNeedDetail needDetail = nutritionalNeedDetailRepo.
                 findByNutritionalNeedIdAndNutrientId(nutritionalNeedDTO.getId(), nutrientAmount.getKey());
         nutritionalNeedDetailRepo.delete(needDetail);
      }
   }


   private NutritionalNeedDTO transferDataToDTO(NutritionalNeed need) {
      NutritionalNeedDTO dto = NutritionalNeedDTO.convertFromEntity(need);

      List<NutritionalNeedDetail> listDetail =
              nutritionalNeedDetailRepo.findByNutritionalNeedId(need.getId());
      HashMap<Long, Double> nutrientAmountMap = new HashMap<>();
      for (NutritionalNeedDetail nutritionalNeedDetail : listDetail) {
         Long nutrientId = nutritionalNeedDetail.getNutrientId();
         Double amount = nutritionalNeedDetail.getAmount();
         nutrientAmountMap.put(nutrientId, amount);
      }
      dto.setNutrientAmountMap(nutrientAmountMap);
      return dto;
   }
}
