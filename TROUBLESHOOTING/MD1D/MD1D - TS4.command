#!/bin/bash


DIR_RUN=`dirname "$0"`

cd "${DIR_RUN}"
cd ../..
rm -r mytroublemod
rm -r assets/mytroubledimensionmod

## Change directory to ts folder
cd ../../../md1d/ts-src/

## Copy folders to new directory
cp -rv ./TS_4/ ./DestFolder ../../mcp/

osascript -e 'tell application "Terminal" to quit' & exit
