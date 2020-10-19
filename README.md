# SPrint simple print service

SPrint is a service for dealing with label printers. It is stateless.
It accepts print requests in its own format using [GraphQL](https://graphql.org/), and translates them into a format suitable for the particular printer.

It reads config files describing the protocols, language and credentials used by different printers.

It accepts a getJobStatus query (for printers that support it).

The first transfer protocol it supports is FTP. This now uses active mode, since passive mode is apparently impossible for our flexible compute environment to support. For this to work, an `--ipAddress` argument must be passed to the application at startup, so the correct IP address can be specified in FTP requests.

The GraphiQL front end can be used to view the form of requests accepted by the service, and to send queries and print requests.

## Adding new CAB Squix label printers

**Printer Administration**

- Under `Setup -> Interfaces -> Network Services`, make sure `FTP` and `Web service` are on
- Under `Security`, make sure the password for `ftpprint` matches `SPrint` deployment configuration
- Under `Security`, make sure `Security web service` is set to `Basic` and the `Password web service` value matches the value in the `SPrint` deployment configuration.

**OpenStack Administration**

- Ensure ports for FTP (21) and HTTP (80) are open

**SPrint Config**

- Add the new printer in the `cgap.xml` config file (in the `SPrint` deployment project)

**Systems**

- Email Systems to ask for access to the new printer to be allowed from OpenStack over HTTP and FTP

**Docker**

- Added docker for back end which would allow users to set it up without having to install IntelliJ.

Usage:
- `docker-compose build`: builds container
- `docker-compose up`: starts it up
- `docker-compose down`: shuts it down
- TODO: tried doing the same for client. It worked but kept getting proxy error because it was in the same container. Something to come back to with a bit more Docker experience.

