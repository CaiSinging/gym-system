#!/bin/bash

function contrib {
	echo $1
	git log --shortstat --author="$1" | grep -E "fil(e|es) changed" | awk '{files+=$1; inserted+=$4; deleted+=$6; delta+=$4-$6} END {printf "  Files changed (total)    %s\n  Lines added (total)      %s\n  Lines deleted (total)    %s\n  Total lines (delta)      %s\n", files, inserted, deleted, delta }' -
}
export -f contrib

git log --format="%aN" | sort -u | xargs -I@ bash -c 'contrib "@"'
