package expressions;

import java.util.Set;

/**
 * Represents a boolean variable.
 * The variable must follow the format {@code <letter>(<letter-or-number>)*},
 * i.e.: a letter followed by zero or more letters or numbers
 * @version 0.1
 */

/*
 * Class invariants: 
 * The 'var' cannot be null or empty or just blank
 */
class Variable implements Expression {

    private String var;

    /**
     * Constructs a new variable expression
     * @param var the variable associated with this expression
     * @throws IllegalArgumentException if {@code var} doesn't follow the following conditions
     * <ul>
     * <li>is not {@code null}</li>
     * <li>is not empty</li>
     * <li>follows the format {@code <letter>(<letter-or-number>)*}, i.e.: a letter followed by zero or more letters or numbers</li>
     * </ul>
     * Precondition :as above and the 'var' cannot be just blank
     * Postcondition: the var is set to the given value and repOK() holds
     */
    Variable(String var) {
        if ( var == null || var.isEmpty() || var.isBlank()){
            throw new IllegalArgumentException("The 'var' cannot be null or empty or just blank");
        }
        if ( !checkFormat(var)){
            throw new IllegalArgumentException("The 'var' does not satisy the format");
        }
        this.var = var;
        if ( !repOK()){
            throw new IllegalStateException("Postcondition failed: repOK() failed");
        }
    }

    /* (non-javadoc)
     * @param var the variable to check
     * @return {@code true} iff {@code var} complies with all these conditions:
     * <ul>
     * <li>is not {@code null}</li>
     * <li>is not empty</li>
     * <li>follows the format {@code <letter>(<letter-or-number>)*}, i.e.: a letter followed by zero or more letters or numbers</li>
     * </ul>
     * Precondition: as above and the 'var' cannot be just blank
     * Postcondition: the invariants hold
     */
    static boolean checkFormat(String var) {
        if ( var == null || var.isEmpty() || var.isBlank()){
            return false;
        }
        if ( !Character.isLetter(var.charAt(0)) ){
            return false;
        }
        for ( int i = 1 ; i < var.length() ; i++ ){          ///We start from 1 because the first character is already checked
            char index = var.charAt(i);
            if ( !Character.isDigit(index) && !Character.isLetter(index) ){
                return false;
            }
        }
        return true;
        
    }

    /**
     * Precondition: the 'interpretation' cannot be null, and the 'var' must exist in the interpretation
     * Postconditions: the result is true if the variable is true in the interpretation, and is false otherwise
     */
    @Override
    public boolean evaluate(Interpretation interpretation) {
        if ( interpretation == null ){
            throw new IllegalArgumentException("The 'interpretation' cannot be null");
        }
        if ( !interpretation.exists(var) ){
            throw new IllegalArgumentException("This variable is not exist in the interpretation");
        }
        return interpretation.valueOf(this.var);
    
    }

    /**
     * Precondition: none
     * Postcondition: the result is a set containing the variable name of the expression
     */
    @Override
    public Set<String> variables() {
        return Set.of(this.var);
    }
    

    public String toString(){
        return this.var;
    }
    //Check invaariant

    public boolean repOK(){
        return var != null && !var.isEmpty() && !var.isBlank() && checkFormat(var);
    }
}
