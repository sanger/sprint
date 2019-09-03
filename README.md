# SPrint simple print service

SPrint is a service for dealing with label printers. It is stateless.
It accepts print requests in its own format using [GraphQL](https://graphql.org/), and translates them into a format suitable for the particular printer.

It reads config files describing the protocols, language and credentials used by different printers.

It accepts a getJobStatus query (for printers that support it).

The first transfer protocol it supports is FTP.

The GraphiQL front end can be used to view the form of requests accepted by the service, and to send queries and print requests.
