package com.cmr.dea.clientapi.demo.security;


public class Websecurity{
        /*

        extends WebSecurityConfigurerAdapter {
    private Environment environement;

    @Autowired
    public Websecurity(Environment env){
        this.environement = env;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").hasIpAddress(environement.getProperty("gateway.ip"))
                .and().addFilter(new AuthentificationFilter());
        http.headers().frameOptions().disable();
    }

    private AuthentificationFilter getAuthentifcationFilter()

    */
}
