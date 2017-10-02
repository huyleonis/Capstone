package com.is0967.menutri.rda.expression;

import java.util.Map;

/**
 * Created by phuctran93 on 4/8/2017.
 */
public interface OperationExecutor
{
    double execute(ExpressionTreeNode p, Map<String, Double> variables, int level);
}
