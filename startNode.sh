#!/bin/sh

# Provides: startNode.sh
# Short-Description: Start startNode.sh at boot time
# Description: Enable service provided by startNode.sh

### BEGIN INIT INFO
# Provides:          startNode.sh
# Required-Start:    $local_fs $remote_fs $network $syslog
# Required-Stop:     $local_fs $remote_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start startNode.sh at boot time
# Description:       Enable service provided by startNode.sh.
### END INIT INFO

service mongodb stop
service mongodb start
node /var/www/nodeJS/lovedogs-server/server.js