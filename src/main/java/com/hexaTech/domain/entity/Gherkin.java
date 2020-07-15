/**
 * @file Gherkin
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.entity;

import java.util.ArrayList;

/**
 * Class used to represent a Gherkin structure.
 */

public class Gherkin {
    String scenario;
    String description;
    String given;
    ArrayList<String> when= new ArrayList<>();
    String then;

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns Gherkin's scenario value.
     * @return string - scenario.
     */
    public String getScenario() {
        return scenario;
    }

    /**
     * Sets Gherkin's scenario to the new value.
     * @param scenario string - new scenario.
     */
    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    /**
     * Returns Gherkin's description value.
     * @return string - description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets Gherkin's given to the new value.
     * @param given string - new scenario.
     */
    public void setGiven(String given) {
        this.given = given;
    }

    /**
     * Returns Gherkin's when value.
     * @return string - value.
     */
    public ArrayList<String> getWhen(){
        return when;
    }

    /**
     * Returns Gherkin's then value.
     * @return string - then.
     */
    public String getThen() {
        return then;
    }

    /**
     * Sets Gherkin's then to the new value.
     * @param then string - new then.
     */
    public void setThen(String then) {
        this.then = then;
    }

}//Gherkin
