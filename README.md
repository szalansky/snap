# snap

Snap securely transmits secrets via self-destructing messages.

Fill in a secret (AWS credentials, password, love letter etc.) and hit the button. Snap will encrypt your message
(AES-256), store it in Redis and generate a one-time URL to access it.

Secrets might still be intercepted somehow, but the receiving party will be aware of the fact that the secret was intercepted and can act accordingly.

## Todo

Things it'd be nice to have:

* enforce SSL
* set auto-expiration time for messages
* specify recipient of the message and send them message URL via email

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2016 FIXME
