package com.exampleesame.boundary.mapping;

import javax.validation.constraints.NotBlank;

/**
 * @author piny73
 */
public class Credential {
    
    @NotBlank
    public String usr;
    
    @NotBlank
    public String pwd;
    
    
}