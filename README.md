# How to run this

If you want to run the application, the easiest way is to use [Leiningen](https://leiningen.org/):

`lein run <params>`

where params may be:

- --port Port to start http server on (default 58080)
- --max-http-connections Max number of open outgoing http connections (default 10)
- --domain-level Domain level to calculate stats for (default 2)

For example, `lein run --port 80 --max-http-connections 3 --domain-level 3`

To run with defaults just `lein run`