package com.is0967.menutri.rda.expression;

import com.is0967.menutri.rda.expression.ExpressionTreeNode.Operation;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * Created by phuctran93 on 4/8/2017.
 */
public enum ExecutorFactory implements OperationExecutor
{
    PRIMITIVE {
        public double execute(Operation operation, double left, double right)
        {
            switch (operation) {
                case ADD:
                    return left + right;
                case SUBTRACT:
                    return left - right;
                case MULTIPLY:
                    return left * right;
                case DIVIDE:
                    return left / right;
                case POW:
                    return Math.pow(left, right);
                default:
                    return 0;
            }
        }
    },

    BIG_EXECUTOR {
        @Override
        protected double execute(Operation operation, double left, double right)
        {
            switch (operation) {
                case ADD:
                    return BigDecimal.valueOf(left).add(BigDecimal.valueOf(right)).doubleValue();
                case SUBTRACT:
                    return BigDecimal.valueOf(left).subtract(BigDecimal.valueOf(right)).doubleValue();
                case MULTIPLY:
                    return BigDecimal.valueOf(left).multiply(BigDecimal.valueOf(right), MathContext.DECIMAL32).doubleValue();
                case DIVIDE:
                    return BigDecimal.valueOf(left).divide(BigDecimal.valueOf(right), MathContext.DECIMAL32).doubleValue();
                case POW:
                    return Math.pow(left, right);
                default:
                    return 0;
            }
        }
    };

    private static final Logger logger = Logger.getLogger(ExecutorFactory.class);

    public static double extractValue(ExpressionTreeNode p, Map<String, Double> variables)
    {
        if (variables != null) {
            if (variables.get(p.key) == null) {
                logger.error("No variable for key: " + p.key + ", apply default value: " + p.value);
                return p.value;
            } else {
                return variables.get(p.key);
            }
        } else {
            logger.debug("Use default value: " + p.key + " = " + p.value);
            return p.value;
        }
    }

    public static void log(int level, String msg, Operation operation, double left, double right, double result)
    {
        String indent = new String(new char[level]).replace("\0", "    ");
        logger.trace(indent + msg + left + operation.symbol + right + "=" + result);
    }

    @Override
    public double execute(ExpressionTreeNode p, Map<String, Double> variables, int level)
    {
        Objects.requireNonNull(p);

        double left = p.left != null ? this.execute(p.left, variables, level + 1) : 0;
        double right = p.right != null ? this.execute(p.right, variables, level + 1) : 0;

        double result;
        switch (p.operation) {
            case VARIABLE:
                result = extractValue(p, variables);
                break;
            case NUMBER:
                result = p.value;
                break;
            default:
                result = this.execute(p.operation, left, right);
        }
        log(level, "", p.operation, left, right, result);
        return result;
    }

    protected abstract double execute(Operation operation, double left, double right);
}
