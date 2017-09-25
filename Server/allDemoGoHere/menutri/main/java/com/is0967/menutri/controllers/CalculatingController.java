package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.ResponseDTO;
import com.is0967.menutri.dtos.BodyData;
import com.is0967.menutri.dtos.Recommendation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by NBL.Huyen on 01-03-17.
 */
@Controller
@RequestMapping("/calculate")
public class CalculatingController extends AbstractController
{

    @RequestMapping("/rda")
    @ResponseBody
    public Object calculateRda(@RequestBody BodyData bodyDataData)
    {
        logger.info("\nCALCULATE RDA:\n " + bodyDataData);
        List<Recommendation> result = recommendationService.recommend(bodyDataData);
//        logger.info(result);
        Map<Long, Double> rdaMap = result.stream()
                .collect(Collectors.toMap(Recommendation::getId, Recommendation::getValue));
        Map<Long, Integer> upperLimitMap = result.stream()
                .filter(r -> r.getUpperLimit() != null)
                .collect(Collectors.toMap(Recommendation::getId, Recommendation::getUpperLimit));
        if (!rdaMap.isEmpty()) {
            ResponseDTO responseDTO =
                    new ResponseDTO(Constant.Code.CALCULATE_SUCCESS, "tính nhu cầu thành công");
            responseDTO.addObject(Constant.JsonName.CALCULATED_MAP, rdaMap);
            responseDTO.addObject(Constant.JsonName.UPPER_LIMIT_MAP, upperLimitMap);
            return responseDTO;
        } else {
            return new ResponseDTO(Constant.Code.CALCULATE_FAIL, "tính nhu cầu thất bại");
        }
    }

}
