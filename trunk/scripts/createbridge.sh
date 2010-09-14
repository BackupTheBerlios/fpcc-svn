#!/bin/sh

#Remove the IP addresses 
sudo ifconfig eth0 0.0.0.0 sudo ifconfig eth1 0.0.0.0

#Create a bridge 
sudo brctl addbr bridge

#Add interfaces to the bridge
sudo brctl addif bridge eth0 
sudo brctl addif bridge eth1

#Configure an IP address for the bridge
sudo ifconfig bridge x.x.x.x netmask y.y.y.y

#Bring up the bridge 
interface sudo ifconfig bridge up

