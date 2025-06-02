package expressions;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a binary boolean expression
 * @version 0.1
 */

/*
 * Class invariants:
 * the 'a', 'b', 'op' cannot be null
 */
class BinaryExpression implements Expression {

    private Expression a;
    private Expression b;
    private BinaryOperator op;

    /* (non-javadoc)
     * An enumeration of all possible binary operator for this
     * kind of expressions
     */
    enum BinaryOperator {
        AND,
        OR,
    }

    /**
     * Constructs a new binary expression
     * @param a the left sub-expression
     * @param b the right sub-expression
     * @param op the operator for this binary expression
     * 
     * Preconditions: 'a' , 'b' , 'op' cannot be null
     * Postconditions:
     * this.a == a, this.b == b, this.op == op
     * repOK() == true
     * 
     */
    BinaryExpression(Expression a, Expression b, BinaryOperator op) {
        if ( a == null || b == null || op == null ){
            throw new IllegalArgumentException("The 'a', 'b', 'op' cannot be null");
        }
        this.a = a;
        this.b = b;
        this.op = op;
        if ( !repOK()){
            throw new IllegalStateException("Posrconditions failed: repOK() is false");
        }
    }
    
    
    /**
     * Preconditions: 
     * the 'interpretation' cannot be null
     * the 'a' , 'b' , 'op' cannot be null
     * the 'a' and 'b' have at least one variable
     * Postconditions:
     * this method returns true if the expression can be satisfied under the given interpretation
     */
    @Override
    public boolean evaluate(Interpretation interpretation) {
        if ( interpretation == null ){
            throw new IllegalArgumentException("The 'interpretation' cannot be null");
        }
        if ( this.a == null || this.b == null || this.op == null ){
            throw new IllegalArgumentException("The 'a' , 'b' , 'op' cannot be null");
        }
        if ( this.a.variables().isEmpty() && this.b.variables().isEmpty() ){
            throw new IllegalArgumentException("The 'a' and 'b' have no variables");
        }
        
        if ( op == BinaryOperator.AND ){
            return this.a.evaluate(interpretation) && this.b.evaluate(interpretation);
        } else if ( op == BinaryOperator.OR ){
            return this.a.evaluate(interpretation) || this.b.evaluate(interpretation);
        } else {
            throw new IllegalArgumentException("The BinaryOperator is unknown");
        }
    }

    /**
     * Preconditions: a and b cannot be null
     * Postconditions: returns a set of all the variables in this expression (a and b)
     */

    @Override
    public Set<String> variables() {
        if ( this.a == null || this.b == null ){
            throw new IllegalArgumentException("The 'a' , 'b' cannot be null");
        }
        Set<String> vars = new HashSet<>();

        for ( String var : a.variables()){
            vars.add(var);
        }
        for ( String var : b.variables()){
            vars.add(var);
        }
        return vars;
    }

    public String toString(){
        return a.toString() + " " + op.toString() + " " + b.toString();
    }
    /*
     * Then we need to check the invariants
     * We create this new method repOK
     */
    
    public boolean repOK(){
        return a != null && b !=null && op != null;
    }
}
