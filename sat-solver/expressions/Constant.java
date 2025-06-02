package expressions;

import java.util.Set;

/**
 * Represents a boolean value, i.e.: true, or false
 * @version 0.1
 */

/**
 * Class invariants:
 * the 'value' must be true or false
 */
class Constant implements Expression {

    private boolean value;

    /**
     * Constructs a new boolean value
     * @param value the internal boolean value this expression represents
     * Preconditions: value msut be true or false
     * Postconditions: the value is set to the given value and the class invariants are satisfied
     */
    Constant(boolean value) {
        if ( value != true && value != false ){
            throw new IllegalArgumentException("The 'value' must be true or false");
        }
        this.value = value;
        if ( !repOK()){
            throw new IllegalStateException("Postcondition failed");
        }
    }

    /**
     * Precondiotions: interpretation cannot be null
     * Postconditions: the value is evaluated under the given interpretation and the class in variants are satisfied
     */
    @Override
    public boolean evaluate(Interpretation interpretation) {
        if ( interpretation == null ){
            throw new IllegalArgumentException("The 'interpretation cannot be null");
        }
        return this.value;
    }

    /**
     * Preconditions: none
     * Postconditions: the set of variables is empty
     */
    
    @Override
    public Set<String> variables() {
        return Set.of();
    }   
    
    public String toString(){
        return value ? "True" : "False";
    }
    /*
    * Check the class invariants by repOK.
    */

    public boolean repOK(){
        return value == true || value == false;               
    }
}
