#!/bin/bash
if [ $# != 1 ]; then
    echo "usage: $0 <filename>"
    exit;
fi

branches=`git for-each-ref --format='%(refname:short)' refs/heads/\*`
curr_branch=`git rev-parse --abbrev-ref HEAD`
echo "curr_branch:"$curr_branch

filename=$1
file_in_repo=`git ls-files ${filename}`

if [ ! "$file_in_repo" ]; then
    echo "file not added in current branch"
    exit
fi

for branch in "${branches[@]}"; do
  if [[ "${branch}" != "master" ]]; then
    git checkout "${branch}"
    git checkout master build.gradle
	#git checkout master app/build.gradle
	#git checkout master gradle/wrapper/gradle-wrapper.properties
	git add . 
	git commit -m "Update Gradle scripts"
	git push
  fi
done