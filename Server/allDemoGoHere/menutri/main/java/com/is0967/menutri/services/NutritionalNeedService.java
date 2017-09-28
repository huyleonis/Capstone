package com.is0967.menutri.services;

import com.is0967.menutri.dtos.NutritionalNeedDTO;

import java.util.List;

/**
 * Created by phuctran93 on 2/21/2017.
 */
public interface NutritionalNeedService {
   NutritionalNeedDTO create(NutritionalNeedDTO nutritionalNeedDTO);

   List<NutritionalNeedDTO> readByUserId(Long userId);

   NutritionalNeedDTO read(Long id);

   boolean delete(Long id);

   NutritionalNeedDTO update(NutritionalNeedDTO nutritionalNeedDTO);
}
