package team6.util.expressions;

import java.util.List;
import java.util.stream.Collectors;

import team6.util.operators.logical.LogicalOperator;

public class LogicalExpression extends BooleanExpression {
    private LogicalOperator operator;
    private List<BooleanExpression> operands;

    public LogicalExpression(LogicalOperator operator, List<BooleanExpression> operands) {
        this.operator = operator;
        this.operands = operands;
    }

    public boolean isTrue() {
        boolean[] results = new boolean[this.operands.size()];
        for (int i = 0; i < this.operands.size(); i++) {
            BooleanExpression expression = this.operands.get(i);
            results[i] = expression.isTrue();
        }
        return operator.isTrue(results);
    }

    public void populate(String symbol, Object value) {
        for (BooleanExpression expression : this.operands) {
            expression.populate(symbol, value);
        }
    }

    public BooleanExpression clone() {
        List<BooleanExpression> clonedOperands = operands.stream()
            .map(operand -> operand.clone())
            .collect(Collectors.toList());
        return new LogicalExpression(operator, clonedOperands);
    }
}
