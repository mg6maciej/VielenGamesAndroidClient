#!/usr/bin/env python

import os
import re
import sys
from subprocess import call

running_file_name = sys.argv[0]
version_name = sys.argv[1]

version_parts = map(lambda part: int(part), version_name.split("."))
version_number = 1000000 * version_parts[0] + 1000 * version_parts[1] + version_parts[2]

call(["git", "stash"])
call(["git", "checkout", "develop", "-b", "release/" + version_name])

build_gradle_name = os.path.dirname(running_file_name) + "/build.gradle"
build_gradle = file(build_gradle_name)
data = build_gradle.read()
build_gradle.close()

data = re.sub("versionCode \d+", "versionCode " + str(version_number), data)
data = re.sub("versionName \S+", "versionName \"" + version_name + "\"", data)

build_gradle = file(build_gradle_name, "w")
build_gradle.write(data)
build_gradle.close()

call(["git", "commit", "-a", "-m", "version " + version_name])
call(["git", "checkout", "master"])
call(["git", "merge", "--no-ff", "release/" + version_name])
call(["git", "tag", version_name])
call(["git", "checkout", "develop"])
call(["git", "merge", "--no-ff", "release/" + version_name])
call(["git", "branch", "-d", "release/" + version_name])
call(["git", "push"])
call(["git", "push", "--tags"])
