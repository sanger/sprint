# Adding new printers to SPrint

###Preconditions
Hopefully someone has set up the printers, added them to the network, and told you their network names. If so, you should be able to visit them in your browser at `printer_name.internal.sanger.ac.uk`.

###ACL
Send a ticket to ServiceDesk asking them to update the ACL to contact the new printers over ftp and http from OpenStack.

##### Checking
You can check if the printer is accessible from openstack over ftp as follows:

- ssh to sprint.psd.sanger.ac.uk
- run the ftpcmd program using `java -jar ftpcmd.jar`
- run the following commands inside the Java program:
   * `t 3000`
   * `s printer_name.internal.sanger.ac.uk`
   * `connect`

If the program successfully connects, them the printer is reachable from openstack over ftp.

* Use `help` to see other available commands
* When you are done, `quit`

###Printer setup
For each printer, go to its URL and update its config.

The necessary credentials are in the psd-credentials repo.

- Under `Setup -> Interfaces -> Network Services`, make sure `FTP` and `Web service` are on.
- Under `Security`, make sure the password for `ftpprint` is set to the correct password.
- Under `Security`, make sure `Security web service` is set to `Basic` and the `Password web service` value is correct.

###SPrint config
Add the config for the new printers to the appropriate xml file in the sprint-deployment repo.

<https://gitlab.internal.sanger.ac.uk/psd/sprint-deployment/-/tree/master/ansible/templates>

Put in a merge request if you need someone to review and merge it in. (Whoever reviews it might also proceed with the remaining steps to update SPrint on production.)

###Deploy config
Run the `update_config_prod.sh` script to update SPrint's config production. You need to specify the ansible vault key either in the command or in your environment.

###Reload config
Send a message to SPrint telling it to reload its config.

You can do this on <http://sprint.psd.sanger.ac.uk/graphiql> using a mutation like this:

```graphql
mutation {
  reloadConfig {
    hostname
    labelType {name}
  }
}
```
If it is successful, it will return the updated list of printers.
