package com.is0967.menutri.services;

import com.is0967.menutri.dtos.BodyData;
import com.is0967.menutri.dtos.Recommendation;
import java.util.List;

/**
 * Created by phuctran93 on 4/4/2017.
 */
public interface RecommendationService
{

    List<Recommendation> recommend(BodyData bodyData);
}
