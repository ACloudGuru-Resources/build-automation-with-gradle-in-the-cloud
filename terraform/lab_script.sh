#!/bin/bash

curl -s "https://get.sdkman.io" | bash
source "/home/cloud_user/.sdkman/bin/sdkman-init.sh"
sdk install java
sdk install gradle
mkdir -p gradle_tf_lab
git clone https://github.com/pluralsight-cloud/build-automation-with-gradle-in-the-cloud.git /home/cloud_user/gradle_tf_lab
mv gradle_tf_lab/terraform/* gradle_tf_lab/
rm -rf gradle_tf_lab/AWS
rm -rf gradle_tf_lab/terraform
