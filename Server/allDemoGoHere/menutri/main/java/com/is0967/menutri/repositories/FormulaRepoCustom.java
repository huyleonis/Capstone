package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.BodyCondition;
import com.is0967.menutri.entities.Formula;
import com.is0967.menutri.entities.Nutrient;
import java.util.List;

/**
 * Created by phuctran93 on 4/5/2017.
 */
public interface FormulaRepoCustom
{

    List<Formula> find(Nutrient nutrient, Formula formula, Iterable<BodyCondition> bodyConditions);
}
