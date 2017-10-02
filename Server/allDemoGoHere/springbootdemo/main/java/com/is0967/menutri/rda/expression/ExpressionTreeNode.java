package com.is0967.menutri.rda.expression;

import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * a node contains either an operator or and operand,
 * the latter being either and identifier or a number.
 * To simplify the task, all of them can be represented
 * as string in an instance of the class.
 * Created by phuctran93 on 4/2/2017.
 */
public class ExpressionTreeNode
{

    private static final Logger logger = Logger.getLogger(ExpressionTreeNode.class.getName());

    public final String key;
    public final ExpressionTreeNode left;
    public final ExpressionTreeNode right;
    public final Operation operation;
    public double value;

    public ExpressionTreeNode(String key, ExpressionTreeNode left, ExpressionTreeNode right)
    {
        this.key = key;
        this.left = left;
        this.right = right;
        value = key.matches("\\d*\\.?\\d*") ? Double.parseDouble(key) : 0.0;

        operation = Operation.parse(key);
        if (operation == null) throw new Error("Unknown operation: " + key);
    }

    public enum Operation
    {
        ADD("+", "[+]"),
        SUBTRACT("-", "[-]"),
        MULTIPLY("*", "[*]"),
        DIVIDE("/", "[/]"),
        POW("^", "[\\^]"),
        VARIABLE("@", "\\w+"),
        NUMBER("#", "\\d+\\.?\\d+"),;
        public final String symbol;
        private final String regex;

        Operation(String symbol, String regex)
        {
            this.symbol = symbol;
            this.regex = regex;
        }

        public static Operation parse(String string)
        {
            Objects.requireNonNull(string);
            for (Operation o : Operation.values()) {
                if (string.matches(o.regex)) { return o; }
            }
            return null;
        }

    }

}
