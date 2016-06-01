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
* remove mutations from `snap.message-safe` namespace - value of some vars are set when the namespace is loaded and it reads from ENV; I don't like it and would like to find some more elegant solution

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed. The application uses Redis as storage,
so access to a Redis server is needed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

### Web server

To start a web server for the application, run:

    REDIS_URL=redis://redis-url:6379 HOSTNAME=snap.mydomain.com ENCRYPTION_KEY=ideally-something-generated lein ring server

`REDIS_URL` specifies the URL of the Redis instance that will be used for storage.
If you don't define this variable it will fall back to `127.0.0.1` as host and `6379` as port.
If you only specify host it will fall back to default `6379` port.

`ENCRYPTION_KEY` is mandatory and the application will throw exception while loading unless
you define this variable. It could instead use some weak key, but I find that unfair and prefer
to be explicit about the runtime requirements.

`HOSTNAME` is optional and contains the name of the host (for absolute HTTP url generation).
App will fall back to `localhost:3000` if that variable is not defined.

### Tests

To run tests, run:

    ENCRYPTION_KEY=snap lein test

## License

Copyright © 2016 FIXME
