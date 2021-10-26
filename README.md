# QSeal example application [![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

In this application we connect
to [Česká spořitelna's Payment Initiation (PISP) PSD2 API](https://developers.erstegroup.com/docs/apis/bank.csas/bank.csas.v4%2Fpayment-initiation)
and we demonstrate how to sign your payment request with Qualified Seal Certificate for PSD2 compliance (QSeal).

For a detailed tutorial on how to use the QSeal certificate, please
visit [this tutorial on our developers portal](https://developers.erstegroup.com/docs/tutorial/how-to-use-certificate-qseal#how-to-use-certificate-qseal).

## Add your certificates into the JKS

We have prepared Java keystores (JKS) that you can add your certificates into.

Add your QSeal certificate into `qseal.jks` under alias `qseal` and your QWAC into `qwac.jks` under alias `qwac`. You
can modify the `truststore.jks` if needed.

All three stores have the same password: `password`.

If you want to use your own stores, you need to add their alias and password to ``configuration.properties``.

## Initial configuration

In `Main.java` you need to fill in your web-api-key (from your application in https://developers.erstegroup.com/) and
access token.

If you want to use a proxy, you can enable and configure it in ``configuration.properties``.

## Run the project

Once you have configured your project in your IDE you can build it from there. However if you prefer you can use maven
from the command line. In that case you could be interested in this short list of commands:

* `mvn compile`: it will just compile the code of your application and tell you if there are errors
* `mvn test`: it will compile the code of your application and your tests. It will then run your tests (if you wrote
  any) and let you know if some fails
* `mvn install`: it will do everything `mvn test` does and then if everything looks file it will install the library or
  the application into your local maven repository (typically under <USER FOLDER>/.m2). In this way you could use this
  library from other projects you want to build on the same machine

If you need more information please take a look at
this [quick tutorial](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

### License

This application is [MIT licensed](./LICENSE).
