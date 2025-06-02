package expressions;

import java.util.Set;

/**
 * Represents a negated boolean expression
 * @version 0.1
 */

/*
 * Class invariants: 
 * The 'expression' cannot be null
 */
class Negation implements Expression {

    private Expression expression;

    /**
     * Constructs a new negated expression
     * @param expression the expression to negate
     * @throws IllegalArgumentException if {@code expression} is {@code null}
     * Precondition: the 'expression' cannot be null
     * Postcondition: the expression is set to the given expression and repOK() is true
     */
    Negation(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expresion' cannot be null");
        }
        this.expression = expression;
        if ( !repOK()){
            throw new IllegalStateException("Postcondition failed: repOK() is false");
        }
    }

    /**
     * Precondition: the 'interpretation' cannot be null
     * Postcondition: the result is the negation of the evaluation of the expression under the given interpretation
     */
    
    @Override
    public boolean evaluate(Interpretation interpretation) {
        if ( interpretation == null ){
            throw new IllegalArgumentException("The 'interpretation' cannot be null");
        }
        return !expression.evaluate(interpretation);
    }

    /**
     * Precondition: the 'expression' cannot be null
     * Postcondition: the result is the set of variables in the negated expression
     */
    
    @Override
    public Set<String> variables() {
        return expression.variables();
    }

    public String toString(){
        return "not " + expression.toString();
    }
    //Check the invariant
    
    public boolean repOK(){
        return expression != null;

    }

}
