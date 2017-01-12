#!/bin/bash


DIR_RUN=`dirname "$0"`

cd "${DIR_RUN}"
cd ../..
rm -r mytroublemod
rm -r assets/mytroubledimensionmod

osascript -e 'tell application "Terminal" to quit' & exit
