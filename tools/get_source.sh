#!/bin/sh

# the tool
echo "Source copying time!"

# check to see if the variable MCP_DIR isn't empty
if [ -z "$MCP_DIR" ]
then
	echo "Enter your MCP directory: "
	read MCP_DIR
fi

if [ -z "$1" ]
then
	echo "Are you copying \"frommcp\" or \"fromrepo\"?"
	read type
else
	type=$1
fi

# copying to the repo grabbing source from MCP
if [ `echo $type | tr [:upper:] [:lower:]` = `echo frommcp` ]
then
	echo "Copying source to the repo from MCP..."
	cd ../
	for f in *.java
	do
		cp "$MCP_DIR/src/minecraft/net/minecraft/src/$f" .	
	done
# copying from the repo putting source into MCP	
elif [ `echo $type | tr [:upper:] [:lower:]` = `echo fromrepo` ]
then
	echo "Copying source to MCP from the repo..."
	cd ../
	for f in *.java
	do
		cp $f "$MCP_DIR/src/minecraft/net/minecraft/src/$f"
	done
fi

