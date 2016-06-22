# voodoo

Rest Services artifacts.

## modules

1. voodoo-core : Helpers and components to build more complex services.
2. voodoo-rest : Common rest services and components (includes voodoo-core).
3. voodoo-undertow : Simple test/mock rest service using other components (embedded undertow app server).
4. voodoo-war : Same as above, but using war packaging, and excluding embedded server.

## To be pre-configured: 

- reactor.
- actuator.
- crash.
- embedded DB (H2/HSQL).
- automatic online rest api doc generator.

## Ideas

- sample service mocks for testing.
- log receiver/viewer.
