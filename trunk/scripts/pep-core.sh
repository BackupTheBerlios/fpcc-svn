#!/bin/bash

#Starts the PEP at the pep-core

sudo java -classpath ../bin:../lib/log4j-1.2.13.jar:../lib/jchart.jar at.ac.tuwien.ibk.biqini.pep.PEPApplication ../cfg/pep-coreDiameterConfig.xml ../cfg/pep-rule-core.xml
