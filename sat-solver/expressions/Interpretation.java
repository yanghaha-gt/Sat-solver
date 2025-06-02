package expressions;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an interpretation of a boolean expression
 */

/*
 * Class invariants: 
 * The interpretation cannot be null
 * The keys in interpretation cannot be null or empty
 * The values in interpretation cannot be null
 */
public class Interpretation implements Cloneable {

    private Map<String, Boolean> interpretation;

    /**
     * Constructs a new empty interpretation.
     * Precondition: none
     * Postcondition: the interpretation is empty, and the class invariants are satisfied
     */
    public Interpretation() {
        interpretation = new TreeMap<>((String a, String b) -> {return a.compareTo(b);});
        if ( !repOK()){
            throw new IllegalStateException("Postcondition failed");
        }
    }

    /**
     * Construct a new interpretation from a list of variable and a bit set of their corresponding boolean values
     * @param variables the variables for this interpretation
     * @param booleanValues the boolean values for each variable in {@code variables} in the corresponding order.
     * @throws IllegalArgumentException if {@code variables} is {@code null}
     * @throws IllegalArgumentException if {@code booleanValues} is {@code null}
     * @throws IllegalArgumentException if there is a variable in {@code variables} such that:
     * <ul>
     * <li>is {@code null}</li>
     * <li>is empty</li>
     * <li>doesn't follows the format <letter>(<letter-or-number>)*, i.e.: a letter followed by zero or more letters or numbers</li>
     * <li>is repeated</li>
     * </ul>
     * <hr>
     * For every index {@code i} between {@code 0} and {@code variables.size() - 1} variable
     * {@code v} at {@code variables.get(i)} will be mapped to boolean value {@code booleanValues.get(i)}
     * Preconditions: as above
     * Postconditions: the interpretation is constructed with the given variables and their corresponding boolean values,
     * and the class invariants are satisfied
     */
    public Interpretation(List<String> variables, BitSet booleanValues) {
        if ( variables == null && booleanValues == null ){
            throw new IllegalArgumentException("The 'variables' and 'booleanValues' cannot be null");
        }
        this.interpretation = new TreeMap<>((String a, String b) -> {return a.compareTo(b);});
        for ( int i=0 ; i < variables.size() ; i++ ){
            String v = variables.get(i);
            if ( v == null || v.isEmpty() ){
                throw new IllegalArgumentException("At least one element in 'variables' cannot be null or empty");
            }
            if ( !Variable.checkFormat(v) ){
                throw new IllegalArgumentException("At least one element in 'variables' do not satisfy the given format");
            }
            if( interpretation.containsKey(v) ){
                throw new IllegalArgumentException("The element in 'variables' cannot be repeated");
            }
            interpretation.put( v, Boolean.valueOf(booleanValues.get(i)));
            if ( !repOK() ){
                throw new IllegalStateException("Postcondition failed: repOK() is false");
            }
        }
    }

    /**
     * Adds a variable with an associated boolean value.
     * If the variable is already in the interpretation, it's value will be modified.
     * @param var the variable
     * @param value the associated boolean value
     * @throws IllegalArgumentException if {@code var} doesn't follow the following conditions
     * <ul>
     * <li>is not {@code null}</li>
     * <li>is not empty</li>
     * <li>follows the format <letter>(<letter-or-number>)*, i.e.: a letter followed by zero or more letters or numbers</li>
     * </ul>
     * Preconditions: the 'var' cannot be null or empty and must follow the format
     * Postconditions: the variable is added to the interpretation with the given value
     */
    public void add(String var, boolean value) {
        if ( var == null || var.isEmpty() ){
            throw new IllegalArgumentException("The 'var' cannot be null or empty");
        }
        if ( !Variable.checkFormat(var) ){
            throw new IllegalArgumentException("The 'var'doesnot satisfy the format");
        }
        interpretation.put(var, Boolean.valueOf(value));
        if ( !repOK()){
            throw new IllegalStateException("Postcondition failed: repOK() is false");
        }
    }

    /**
     * Checks if a variable is part of this interpretation
     * @param var the variable to check
     * @return {@code true} iff {@code var} is part of this interpretation
     * @throws IllegalArgumentException if {@code var} doesn't follow the following conditions
     * <ul>
     * <li>is not {@code null}</li>
     * <li>is not empty</li>
     * <li>follows the format <letter>(<letter-or-number>)*, i.e.: a letter followed by zero or more letters or numbers</li>
     * </ul>
     * Preconditions: as above
     * Postconditions: the method returns true if the variable exists in the interpretation, and return false otherwise
     */
    public boolean exists(String var) {
        if ( var == null || var.isEmpty() ){
            throw new IllegalArgumentException("The 'var' cannot be null or empty");
        }
        if ( !Variable.checkFormat(var) ){
            throw new IllegalArgumentException("The 'var' doesnot satisfy the format");
        }
        return interpretation.containsKey(var);
    }

    /**
     * Returns the associated boolean value of a variable in this interpretation
     * @param var the variable to check
     * @return the associated boolean value for {@code var} under this interpretation
     * @throws IllegalArgumentException if {@code var} doesn't follow the following conditions
     * <ul>
     * <li>is not {@code null}</li>
     * <li>is not empty</li>
     * <li>follows the format <letter>(<letter-or-number>)*, i.e.: a letter followed by zero or more letters or numbers</li>
     * </ul>
     * @throws IllegalArgumentException if {@code var} doesn't exist in this interpretation
     * Preconditions: as above
     * Postconditions: the method returns the boolean value associated with the variable
     */
    public boolean valueOf(String var) {
        if ( var == null || var.isEmpty() ){
            throw new IllegalArgumentException("The 'var' cannot be null or empty");
        }
        if ( !Variable.checkFormat(var) ){
            throw new IllegalArgumentException("The 'var' doesnot satisfy the format");
        }
        if ( !interpretation.containsKey(var) ){
            throw new IllegalArgumentException("The 'var' doesnot exist in this intertation");
        }
        return interpretation.get(var);
    }

    /**
     * Preconditions: none
     * Postconditons: return a set of all the variables in this interpretation and invariants are satisfied
     */
    @Override
    public Object clone() {
        Interpretation clone = new Interpretation();
        for (Map.Entry<String, Boolean> entry : interpretation.entrySet()) {
            clone.interpretation.put(new String(entry.getKey()), Boolean.valueOf(entry.getValue()));
        }
        return clone;
    }
    

    /**
     * Preconditions: none 
     * Postconditions: returns a string rep of the interpretation
     */
    public String toString(){
        ArrayList<String> vars = new ArrayList<>();
        for ( Map.Entry<String, Boolean> entry : interpretation.entrySet()){
            vars.add(entry.getKey() + " = " + entry.getValue());
        }
        return String.join("|", vars);
    }
    //Check the invariants

    public boolean repOK(){
        if ( interpretation == null ){
            return false;
        }
        for ( Map.Entry<String, Boolean> entry : interpretation.entrySet() ){
            if ( entry.getKey() == null || entry.getValue() == null || entry.getKey().isEmpty() ){
                return false;
            }
        }
        return true;
    }
}
