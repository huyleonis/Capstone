package com.is0967.menutri.rda.expression;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Use bottom-up
 * Created by phuctran93 on 4/2/2017.
 */
public class ExpressionTree
{

    private static final Logger logger = Logger.getLogger(ExpressionTree.class.getName());

    private final ExpressionTreeNode root;
    private final StreamTokenizer tokenizer;
    private final Map<String, List<ExpressionTreeNode>> variables;
    private final OperationExecutor executor;

    public ExpressionTree(ExpressionTreeBuilder builder)
    {
        this.root = builder.root;
        this.tokenizer = builder.tokenizer;
        this.variables = builder.variables;
        this.executor = builder.executor;
    }

    public ExpressionTree(String expression) throws IOException
    {
        tokenizer = new StreamTokenizer(new CharArrayReader(expression.toCharArray()));
        tokenizer.ordinaryChar('/');
        tokenizer.ordinaryChar('-');
        variables = new HashMap<>(); // initialize variables before invoke expression(...)
        root = expression(0);
        executor = ExecutorFactory.BIG_EXECUTOR;
    }

    private static void log(Object msg, int level)
    {
        String levelIndent = new String(new char[level]).replace("\0", "    ");
        logger.trace(levelIndent + msg);
    }

    private ExpressionTreeNode expression(int level) throws IOException
    {
        level++;
        log("->e", level);
        ExpressionTreeNode p1 = term(level);
        ExpressionTreeNode p2;
        tokenizer.nextToken();
        while (tokenizer.ttype == '+' || tokenizer.ttype == '-') {
            String operation = Character.toString((char) tokenizer.ttype);
            p2 = term(level);
            p1 = new ExpressionTreeNode(operation, p1, p2);
            tokenizer.nextToken();
        }
        tokenizer.pushBack();
        log("e->" + toPolishNotation(p1), level);
        return p1;
    }

    private ExpressionTreeNode term(int level) throws IOException
    {
        level++;
        log("->t", level);
        ExpressionTreeNode p1 = factor(level);
        ExpressionTreeNode p2;
        tokenizer.nextToken();
        while (tokenizer.ttype == '*' || tokenizer.ttype == '/') {
            String operation = Character.toString((char) tokenizer.ttype);
            p2 = factor(level);
            p1 = new ExpressionTreeNode(operation, p1, p2);
            tokenizer.nextToken();
        }
        tokenizer.pushBack();
        log("t->" + toPolishNotation(p1), level);
        return p1;
    }

    private ExpressionTreeNode factor(int level) throws IOException
    {
        level++;
        log("->f", level);
        ExpressionTreeNode p1 = exponent(level);
        ExpressionTreeNode p2;
        tokenizer.nextToken();
        while (tokenizer.ttype == '^') {
            String operation = Character.toString((char) tokenizer.ttype);
            p2 = exponent(level);
            p1 = new ExpressionTreeNode(operation, p1, p2);
            tokenizer.nextToken();
        }
        tokenizer.pushBack();
        log("f->" + toPolishNotation(p1), level);
        return p1;
    }

    private ExpressionTreeNode exponent(int level) throws IOException
    {
        level++;
        log("->exp", level);
        tokenizer.nextToken();
        log(tokenizer, level);

        // take all '+'s and '-'s;
        double minus = 1.0;
        while (tokenizer.ttype == '+' || tokenizer.ttype == '-') {
            if (tokenizer.ttype == '-') { minus *= -1.0; }
            tokenizer.nextToken();
        }
        ExpressionTreeNode p;
        if (tokenizer.ttype == StreamTokenizer.TT_NUMBER
                || tokenizer.ttype == StreamTokenizer.TT_WORD) {
            String key = tokenizer.sval != null
                    ? tokenizer.sval
                    : String.valueOf(tokenizer.nval);
            p = new ExpressionTreeNode(key, null, null);
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) { addVariableNode(p); }
            if (minus == -1) p = new ExpressionTreeNode("-", null, p); // unary operation
            log("exp->" + toPolishNotation(p), level);
            return p;
        } else if (tokenizer.ttype == '(') {
            p = expression(level);
            tokenizer.nextToken();
            if (tokenizer.ttype == ')') {
                log("exp->" + toPolishNotation(p), level);
                return p;
            } else { throw new MissingFormatArgumentException("Missing end parenthesis ')'"); }
        } else if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
            return null;
        } else { throw new IllegalArgumentException("Parse Error for string: " + tokenizer.toString()); }
    }

    public String toPolishNotation()
    {
        return toPolishNotation(root);
    }

    public String toPolishNotation(boolean pretty)
    {
        return toPolishNotation(root, 0);
    }

    private String toPolishNotation(ExpressionTreeNode p)
    {
        if (p == null) return "";
        if (p.key.matches("[\\w.]+")) {
            return p.key;
        } else {
            return " " + p.key // operation
                    + " " + toPolishNotation(p.left)
                    + " " + toPolishNotation(p.right);
        }
    }

    private String toPolishNotation(ExpressionTreeNode p, int level)
    {
        if (p == null) return "";
        String indent = new String(new char[level]).replace("\0", "    ");
        if (p.key.matches("[\\w.]+")) {
            return indent + p.key + "\n";
        } else {
            return indent + p.key + "\n"
                    + toPolishNotation(p.left, level + 1)
                    + toPolishNotation(p.right, level + 1);
        }
    }

    public double execute(OperationExecutor executor, Map<String, Double> variables)
    {
        if (root == null || executor == null) return 0.0;
        return executor.execute(root, variables, 0);
    }


    public double execute(Map<String, Double> variables)
    {
        return this.execute(this.executor, variables);
    }

    public void put(String key, double value)
    {
        List<ExpressionTreeNode> nodes = variables.get(key);
        for (ExpressionTreeNode p : nodes) {
            p.value = value;
        }
    }

    private void addVariableNode(ExpressionTreeNode p)
    {
        List<ExpressionTreeNode> nodes = variables.computeIfAbsent(p.key, k -> new ArrayList<>());
        nodes.add(p);
    }

    public Set<String> getVariableKeySet()
    {
        return variables.keySet();
    }

    public OperationExecutor getExecutor()
    {
        return executor;
    }

    public boolean isExecutable(Map<String, Double> variables)
    {
        return variables.keySet().containsAll(this.variables.keySet());
    }

    public static class ExpressionTreeBuilder
    {

        private final String expression;
        private final double defaultValue;
        public ExpressionTreeNode root;
        public StreamTokenizer tokenizer;
        public Map<String, List<ExpressionTreeNode>> variables;
        public OperationExecutor executor;

        public ExpressionTreeBuilder(String expression, double defaultValue)
        {
            this.expression = expression;
            this.defaultValue = defaultValue;
        }

        public ExpressionTree build()
        {
            if (expression == null || expression.isEmpty())
                return getDummyExpression();
            try {
                return new ExpressionTree(expression);
            } catch (IOException | NullPointerException e) {
                logger.warn("Cannot evaluate expression: '" + this.expression + "'", e);
                return getDummyExpression();
            }
        }

        private ExpressionTree getDummyExpression()
        {
            this.variables = Collections.EMPTY_MAP;
            this.executor = (p, variables, level) -> this.defaultValue;
            return new ExpressionTree(this);
        }
    }
}
