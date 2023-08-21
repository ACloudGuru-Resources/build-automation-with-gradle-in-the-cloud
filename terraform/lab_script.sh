#!/bin/bash

curl -s "https://get.sdkman.io" | bash
source "/home/cloud_user/.sdkman/bin/sdkman-init.sh"
sdk install java
sdk install gradle
