#!/bin/bash

#Starts the PEP at the pep-user

sudo java -classpath ../bin:../lib/log4j-1.2.13.jar:../lib/jchart.jar at.ac.tuwien.ibk.biqini.pep.PEPApplication ../cfg/pep-userDiameterCore.xml ../cfg/pep-rule-user.xml
