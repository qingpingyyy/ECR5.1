#!/bin/sh
AppPath=/data/ctyue/AmarECR5.1/ecr_sh
cd ${AppPath}
sh startup.sh datacopy
sh startup.sh prepareAccount
sh startup.sh prepareCust
sh startup.sh prepareFinance
sh startup.sh prepareAcctAndGuar
sh startup.sh prepareCredAndMotga
sh startup.sh validate
sh startup.sh transfer
sh startup.sh report
#sh startup.sh exportenc